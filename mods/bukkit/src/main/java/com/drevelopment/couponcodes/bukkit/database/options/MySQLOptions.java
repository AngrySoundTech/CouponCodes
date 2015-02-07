package com.drevelopment.couponcodes.bukkit.database.options;

public class MySQLOptions implements DatabaseOptions {

	private final String hostname;
	private final String port;
	private final String database;
	private final String username;
	private final String password;

	public MySQLOptions(String hostname, String port, String database, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;

	}

	public String getHostname() {
		return hostname;
	}

	public String getPort() {
		return port;
	}

	public String getDatabase() {
		return database;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
