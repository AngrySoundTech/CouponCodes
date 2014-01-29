package net.Drepic.CouponCodes.api.events.database;

import java.sql.Connection;

import net.Drepic.CouponCodes.sql.options.DatabaseOptions;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when the connection is opened to the database
 */
public class DatabaseOpenConnectionEvent extends Event {

	private static final HandlerList h = new HandlerList();
	
	private DatabaseOptions dop;
	private Connection con;
	private Boolean success = false;
	
	public DatabaseOpenConnectionEvent(Connection con, DatabaseOptions dop, Boolean success) {
		this.dop = dop;
		this.con = con;
		this.success = success;
	}
	
	/**
	 * Gets the database options
	 * @return The database options
	 */
	public DatabaseOptions getDatabaseOptions() {
		return dop;
	}
	
	/**
	 * Gets the connection
	 * @return The connection
	 */
	public Connection getConnection() {
		return con;
	}
	
	/**
	 * Returns true if the connection was successful
	 * @return true if the connection was successful
	 */
	public Boolean getSuccess() {
		return success;
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
