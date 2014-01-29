package net.Drepic.CouponCodes.api.events.database;

import java.sql.ResultSet;

import net.Drepic.CouponCodes.sql.options.DatabaseOptions;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when the database is queried
 */
public class DatabaseQueryEvent extends Event {

	private static final HandlerList h = new HandlerList();
	
	private DatabaseOptions dop;
	private String query;
	private ResultSet rs;
	
	public DatabaseQueryEvent(DatabaseOptions dop, String query, ResultSet rs) {
		this.dop = dop;
		this.query = query;
		this.rs = rs;
	}
	
	/**
	 * Gets the query that was sent to the database
	 * @return The query that was sent to the database
	 */
	public String getQuery() {
		return query;
	}
	
	/**
	 * Gets the database options
	 * @return The database options
	 */
	public DatabaseOptions getDatabaseOptions() {
		return dop;
	}
	
	/**
	 * Gets the resultSet of the query
	 * @return The resultSet of the query
	 */
	public ResultSet getResultSet() {
		return rs;
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
