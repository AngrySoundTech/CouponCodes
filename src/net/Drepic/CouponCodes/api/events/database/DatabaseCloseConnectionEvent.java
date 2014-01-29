package net.Drepic.CouponCodes.api.events.database;

import java.sql.Connection;

import net.Drepic.CouponCodes.sql.options.DatabaseOptions;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when the connection to the database is closed
 */
public class DatabaseCloseConnectionEvent extends Event {

	private static final HandlerList h = new HandlerList();
	
	private Connection con;
	private DatabaseOptions dop;
	
	public DatabaseCloseConnectionEvent(Connection con, DatabaseOptions dop) {
		this.con = con;
		this.dop = dop;
	}
	
	/**
	 * Gets the connection
	 * @return The connection
	 */
	public Connection getConnection() {
		return con;
	}
	
	/**
	 * Gets the database options
	 * @return The database options
	 */
	public DatabaseOptions getDatabaseOptions() {
		return dop;
	}
	
	public HandlerList getHandlers() {
		return h;
	}
	
	public static HandlerList getHandlerList() {
		return h;
	}
	
	public void call() {
		Bukkit.getServer().getPluginManager().callEvent(this);
	}
}
