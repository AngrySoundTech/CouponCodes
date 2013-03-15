package net.Drepic.CouponCodes.api.events.database;

import java.sql.ResultSet;

import net.Drepic.CouponCodes.sql.options.DatabaseOptions;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

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
	
	public String getQuery() {
		return query;
	}
	
	public DatabaseOptions getDatabaseOptions() {
		return dop;
	}
	
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
