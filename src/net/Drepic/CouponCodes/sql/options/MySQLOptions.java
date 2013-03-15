package net.Drepic.CouponCodes.sql.options;


public class MySQLOptions implements DatabaseOptions {
	
	private String hostname = null;
	private String pn = null;
	private String database = null;
	private String username = null;
	private String password = null;
	
	public MySQLOptions(String hostname, String portnumber, String database, String username, String password) {
		this.hostname = hostname;
		this.pn = portnumber;
		this.database = database;
		this.username = username;
		this.password = password;
	}
	
	public String getHostname() {
		return hostname;
	}
	
	public String getPort() {
		return pn;
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