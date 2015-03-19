/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (Drepic26)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.drevelopment.couponcodes.bukkit;

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

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.entity.Player;
import com.drevelopment.couponcodes.bukkit.config.BukkitConfigHandler;
import com.drevelopment.couponcodes.bukkit.coupon.BukkitCouponHandler;
import com.drevelopment.couponcodes.bukkit.coupon.BukkitCouponTimer;
import com.drevelopment.couponcodes.bukkit.database.SQLDatabaseHandler;
import com.drevelopment.couponcodes.bukkit.database.options.MySQLOptions;
import com.drevelopment.couponcodes.bukkit.database.options.SQLiteOptions;
import com.drevelopment.couponcodes.bukkit.economy.VaultEconomyHandler;
import com.drevelopment.couponcodes.bukkit.listeners.BukkitListener;
import com.drevelopment.couponcodes.bukkit.metrics.CustomDataSender;
import com.drevelopment.couponcodes.bukkit.metrics.Metrics;
import com.drevelopment.couponcodes.bukkit.permission.SuperPermsPermissionHandler;
import com.drevelopment.couponcodes.bukkit.permission.VaultPermissionHandler;
import com.drevelopment.couponcodes.bukkit.updater.Updater;
import com.drevelopment.couponcodes.core.commands.SimpleCommandHandler;
import com.drevelopment.couponcodes.core.event.SimpleEventHandler;
import com.drevelopment.couponcodes.core.util.LocaleHandler;

public class BukkitPlugin extends JavaPlugin implements Listener {

	private Logger logger = null;
	private Metrics metrics;

	private Economy econ = null;
	private Permission perm = null;

	@Override
	public void onEnable() {
		logger = this.getLogger();

		CouponCodes.setEventHandler(new SimpleEventHandler());
		CouponCodes.setModTransformer(new BukkitServerModTransformer(this));
		CouponCodes.setConfigHandler(new BukkitConfigHandler(this));
		CouponCodes.setCommandHandler(new SimpleCommandHandler());

		//SQL
		if (((BukkitConfigHandler)CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("MYSQL")) {
			CouponCodes.setDatabaseHandler(new SQLDatabaseHandler(this, new MySQLOptions(((BukkitConfigHandler)CouponCodes.getConfigHandler()).getHostname(), ((BukkitConfigHandler)CouponCodes.getConfigHandler()).getPort(), ((BukkitConfigHandler)CouponCodes.getConfigHandler()).getDatabase(), ((BukkitConfigHandler)CouponCodes.getConfigHandler()).getUsername(), ((BukkitConfigHandler)CouponCodes.getConfigHandler()).getPassword())));
		} else
		if (((BukkitConfigHandler)CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("SQLite")) {
			CouponCodes.setDatabaseHandler(new SQLDatabaseHandler(this, new SQLiteOptions(new File(getDataFolder()+"/coupon_data.db"))));
		} else
		if (!((BukkitConfigHandler)CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("MYSQL") && !((BukkitConfigHandler)CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("SQLite")) {
			logger.severe(LocaleHandler.getString("Console.SQL.UnknownValue", ((BukkitConfigHandler)CouponCodes.getConfigHandler()).getSQLValue()));
			logger.severe(LocaleHandler.getString("Console.SQL.SetupFailed"));
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		try {
			((SQLDatabaseHandler)CouponCodes.getDatabaseHandler()).open();
			((SQLDatabaseHandler)CouponCodes.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS couponcodes (name VARCHAR(24), ctype VARCHAR(10), usetimes INT(10), usedplayers TEXT(1024), ids VARCHAR(255), money INT(10), groupname VARCHAR(20), timeuse INT(100), xp INT(10), command VARCHAR(255))");
			// Compatability for moving to version 3.1->3.2+
			if (!((SQLDatabaseHandler)CouponCodes.getDatabaseHandler()).getConnection().getMetaData().getColumns(null, null, "couponcodes", "command").next())
				((SQLDatabaseHandler)CouponCodes.getDatabaseHandler()).query("ALTER TABLE couponcodes ADD COLUMN command VARCHAR(255)");
			CouponCodes.setCouponHandler(new BukkitCouponHandler(this, ((SQLDatabaseHandler)CouponCodes.getDatabaseHandler())));
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
			CouponCodes.setEconomyHandler(new VaultEconomyHandler(econ));
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
				CouponCodes.getModTransformer().scheduleRunnable(new CustomDataSender(metrics));
				metrics.start();
			} catch (IOException e) {}
		}

		//Updater
		if (CouponCodes.getConfigHandler().getAutoUpdate()) {
			new Updater(this, 53833, this.getFile(), Updater.UpdateType.DEFAULT, false);
		}
	}

	@Override
	public void onDisable() {
		CouponCodes.setModTransformer(null);

		try {
			((SQLDatabaseHandler)CouponCodes.getDatabaseHandler()).close();
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
		return CouponCodes.getModTransformer().getPlayer(player.getUniqueId().toString());
	}

}
