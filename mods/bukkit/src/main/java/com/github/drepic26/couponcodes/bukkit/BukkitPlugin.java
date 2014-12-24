package com.github.drepic26.couponcodes.bukkit;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.drepic26.couponcodes.bukkit.commands.BukkitCommandHandler;
import com.github.drepic26.couponcodes.bukkit.config.BukkitConfigHandler;
import com.github.drepic26.couponcodes.bukkit.coupon.BukkitCouponHandler;
import com.github.drepic26.couponcodes.bukkit.database.SQLDatabaseHandler;
import com.github.drepic26.couponcodes.bukkit.database.options.MySQLOptions;
import com.github.drepic26.couponcodes.bukkit.database.options.SQLiteOptions;
import com.github.drepic26.couponcodes.bukkit.listeners.BukkitListener;
import com.github.drepic26.couponcodes.bukkit.permission.SuperPermsPermissionHandler;
import com.github.drepic26.couponcodes.bukkit.permission.VaultPermissionHandler;
import com.github.drepic26.couponcodes.core.ServerModTransformer;
import com.github.drepic26.couponcodes.core.commands.CommandHandler;
import com.github.drepic26.couponcodes.core.entity.Player;

public class BukkitPlugin extends JavaPlugin implements Listener {

	private Logger logger = null;

	private ServerModTransformer transformer = new BukkitServerModTransformer(this);
	private final CommandHandler commandHandler = new BukkitCommandHandler();
	private final BukkitConfigHandler configHandler = new BukkitConfigHandler(this);
	private SQLDatabaseHandler databaseHandler;

	@Override
	public void onEnable() {
		logger = this.getLogger();

		//SQL
		if (configHandler.getSQLValue().equalsIgnoreCase("MYSQL")) {
			databaseHandler = new SQLDatabaseHandler(this, new MySQLOptions(configHandler.getHostname(), configHandler.getPort(), configHandler.getDatabase(), configHandler.getUsername(), configHandler.getPassword()));
		} else
		if (configHandler.getSQLValue().equalsIgnoreCase("SQLite")) {
			databaseHandler = new SQLDatabaseHandler(this, new SQLiteOptions(new File(getDataFolder()+"/coupon_data.db")));
		} else
		if (!configHandler.getSQLValue().equalsIgnoreCase("MYSQL") && !configHandler.getSQLValue().equalsIgnoreCase("SQLite")) {
			logger.severe("The SQLType has the unknown value of: "+configHandler.getSQLValue());
			logger.severe("Database could not be setup. CouponCodes will now disable");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		try {
			databaseHandler.open();
			databaseHandler.createTable("CREATE TABLE IF NOT EXISTS couponcodes (name VARCHAR(24), ctype VARCHAR(10), usetimes INT(10), usedplayers TEXT(1024), ids VARCHAR(255), money INT(10), groupname VARCHAR(20), timeuse INT(100), xp INT(10))");
			transformer.setCouponHandler(new BukkitCouponHandler(this, databaseHandler));
		} catch (SQLException e) {
			e.printStackTrace();
			logger.severe("Database could not be setup. CouponCodes will now disable");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		// Events
		getServer().getPluginManager().registerEvents(new BukkitListener(this), this);

		// Permissions
		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			transformer.setPermissionHandler(new VaultPermissionHandler());
		} else {
			transformer.setPermissionHandler(new SuperPermsPermissionHandler());
		}
	}

	@Override
	public void onDisable() {
		transformer = null;
	}

	public Player wrapPlayer(org.bukkit.entity.Player player) {
		return transformer.getPlayer(player.getName());
	}

	public ServerModTransformer getTransformer() {
		return transformer;
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

}
