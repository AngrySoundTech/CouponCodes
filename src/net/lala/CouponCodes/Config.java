package net.lala.CouponCodes;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Config.java - Custom config handling
 * @author mike101102
 */
public class Config {
	
	private FileConfiguration config;
	
	public Config(Plugin plugin) {
		this.config = plugin.getConfig();
		copyDefaults(true);
		plugin.saveConfig();
	}
	
	public void copyDefaults(boolean copy) {
		config.options().copyDefaults(copy);
	}
	
	public boolean getVault() {
		return config.getBoolean("use-vault");
	}
	
	public boolean getDebug() {
		return config.getBoolean("debug");
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