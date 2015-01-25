package com.github.drepic26.couponcodes.bukkit.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import com.github.drepic26.couponcodes.api.config.ConfigHandler;

public class BukkitConfigHandler implements ConfigHandler {

	private FileConfiguration config;

	public BukkitConfigHandler(Plugin plugin) {
		this.config = plugin.getConfig();

		if (!(new File("plugins/CouponCodes/config.yml").exists()))
			plugin.saveDefaultConfig();
		if (!config.options().copyDefaults(true).configuration().equals(config))
			plugin.saveConfig();
	}

	public boolean getUseThread() {
		return config.getBoolean("use-thread", true);
	}

	public boolean getDebug() {
		return config.getBoolean("debug", false);
	}

	public boolean getUseMetrics() {
		return config.getBoolean("use-metrics", true);
	}

	public boolean getAutoUpdate() {
		return config.getBoolean("auto-update", true);
	}

	public String getSQLValue() {
		return config.getString("sql-type");
	}

	public String getHostname() {
		return config.getString("MySQL-options.hostname");
	}

	public String getPort() {
		return config.getString("MySQL-options.port");
	}

	public String getDatabase() {
		return config.getString("MySQL-options.database");
	}

	public String getUsername() {
		return config.getString("MySQL-options.username");
	}

	public String getPassword() {
		return config.getString("MySQL-options.password");
	}
}
