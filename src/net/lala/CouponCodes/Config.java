package net.lala.CouponCodes;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
	
	private FileConfiguration config;
	
	public Config(Plugin plugin) {
		this.config = plugin.getConfig();
		plugin.saveDefaultConfig();
	}
	
	public boolean getUseThread() {
		return config.getBoolean("use-thread", true);
	}
	
	public boolean getVault() {
		return config.getBoolean("use-vault", false);
	}
	
	public boolean getDebug() {
		return config.getBoolean("debug", false);
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