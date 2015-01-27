package com.github.drepic26.couponcodes.bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.ModTransformer;
import com.github.drepic26.couponcodes.api.entity.Player;
import com.github.drepic26.couponcodes.bukkit.commands.BukkitCommandHandler;
import com.github.drepic26.couponcodes.bukkit.config.BukkitConfigHandler;
import com.github.drepic26.couponcodes.bukkit.coupon.BukkitCouponHandler;
import com.github.drepic26.couponcodes.bukkit.coupon.BukkitCouponTimer;
import com.github.drepic26.couponcodes.bukkit.database.SQLDatabaseHandler;
import com.github.drepic26.couponcodes.bukkit.database.options.MySQLOptions;
import com.github.drepic26.couponcodes.bukkit.database.options.SQLiteOptions;
import com.github.drepic26.couponcodes.bukkit.economy.VaultEconomyHandler;
import com.github.drepic26.couponcodes.bukkit.listeners.BukkitListener;
import com.github.drepic26.couponcodes.bukkit.metrics.CustomDataSender;
import com.github.drepic26.couponcodes.bukkit.metrics.Metrics;
import com.github.drepic26.couponcodes.bukkit.permission.SuperPermsPermissionHandler;
import com.github.drepic26.couponcodes.bukkit.permission.VaultPermissionHandler;
import com.github.drepic26.couponcodes.bukkit.updater.Updater;
import com.github.drepic26.couponcodes.core.commands.CommandHandler;
import com.github.drepic26.couponcodes.core.util.LocaleHandler;

public class BukkitPlugin extends JavaPlugin implements Listener {

	private Logger logger = null;
	private Metrics metrics;

	private ModTransformer transformer = new BukkitServerModTransformer(this);
	private final CommandHandler commandHandler = new BukkitCommandHandler();
	private SQLDatabaseHandler databaseHandler;

	private Economy econ = null;
	private Permission perm = null;

	@Override
	public void onEnable() {
		logger = this.getLogger();

		CouponCodes.setConfigHandler(new BukkitConfigHandler(this));

		//SQL
		if (((BukkitConfigHandler)CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("MYSQL")) {
			databaseHandler = new SQLDatabaseHandler(this, new MySQLOptions(((BukkitConfigHandler)CouponCodes.getConfigHandler()).getHostname(), ((BukkitConfigHandler)CouponCodes.getConfigHandler()).getPort(), ((BukkitConfigHandler)CouponCodes.getConfigHandler()).getDatabase(), ((BukkitConfigHandler)CouponCodes.getConfigHandler()).getUsername(), ((BukkitConfigHandler)CouponCodes.getConfigHandler()).getPassword()));
		} else
		if (((BukkitConfigHandler)CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("SQLite")) {
			databaseHandler = new SQLDatabaseHandler(this, new SQLiteOptions(new File(getDataFolder()+"/coupon_data.db")));
		} else
		if (!((BukkitConfigHandler)CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("MYSQL") && !((BukkitConfigHandler)CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("SQLite")) {
			logger.severe(LocaleHandler.getString("Console.SQL.UnknownValue", ((BukkitConfigHandler)CouponCodes.getConfigHandler()).getSQLValue()));
			logger.severe(LocaleHandler.getString("Console.SQL.SetupFailed"));
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		try {
			databaseHandler.open();
			databaseHandler.createTable("CREATE TABLE IF NOT EXISTS couponcodes (name VARCHAR(24), ctype VARCHAR(10), usetimes INT(10), usedplayers TEXT(1024), ids VARCHAR(255), money INT(10), groupname VARCHAR(20), timeuse INT(100), xp INT(10))");
			CouponCodes.setCouponHandler(new BukkitCouponHandler(this, databaseHandler));
		} catch (SQLException e) {
			e.printStackTrace();
			logger.severe(LocaleHandler.getString("Console.SQL.SetupFailed"));
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		// Vault
		if (!setupVault()) {
			logger.info(LocaleHandler.getString("Console.Vault.Disabled"));
		} else {
			logger.info(LocaleHandler.getString("Console.Vault.Enabled"));
			CouponCodes.setEconomyHandler(new VaultEconomyHandler(econ, perm));
		}

		// Events
		getServer().getPluginManager().registerEvents(new BukkitListener(this), this);

		// Permissions
		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			CouponCodes.setPermissionHandler(new VaultPermissionHandler());
		} else {
			CouponCodes.setPermissionHandler(new SuperPermsPermissionHandler());
		}

		// Timer
		if (CouponCodes.getConfigHandler().getUseThread()) {
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new BukkitCouponTimer(), 200L, 200L);
		}

		// Metrics
		if (CouponCodes.getConfigHandler().getUseMetrics()) {
			try {
				metrics = new Metrics(this);
			getServer().getScheduler().scheduleSyncDelayedTask(this, new CustomDataSender(this, metrics));
				metrics.start();
			} catch (IOException e) {}
		}

		//Updater
		if (CouponCodes.getConfigHandler().getAutoUpdate()) {
			Updater updater = new Updater(this, 53833, this.getFile(), Updater.UpdateType.DEFAULT, false);
		}
	}

	@Override
	public void onDisable() {
		transformer = null;

		try {
			databaseHandler.close();
		} catch (SQLException e) {
			logger.severe(LocaleHandler.getString("Console.SQL.CloseFailed"));
		}
	}

	private boolean setupVault() {
		if (getServer().getPluginManager().getPlugin("Vault") == null)
			return false;
		try {
			RegisteredServiceProvider<Economy> ep = getServer().getServicesManager().getRegistration(Economy.class);
			RegisteredServiceProvider<Permission> pe = getServer().getServicesManager().getRegistration(Permission.class);
			if (ep == null || pe == null) {
				return false;
			}
			else {
				econ = ep.getProvider();
				perm = pe.getProvider();
				return true;
			}
		} catch (NoClassDefFoundError e) {
			return false;
		}
	}

	public Player wrapPlayer(org.bukkit.entity.Player player) {
		return transformer.getPlayer(player.getUniqueId().toString());
	}

	public ModTransformer getTransformer() {
		return transformer;
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

}
