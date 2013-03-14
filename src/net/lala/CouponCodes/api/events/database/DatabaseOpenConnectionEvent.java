package net.lala.CouponCodes.api.events.database;

import java.sql.Connection;

import net.lala.CouponCodes.sql.options.DatabaseOptions;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

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
	
	public DatabaseOptions getDatabaseOptions() {
		return dop;
	}
	
	public Connection getConnection() {
		return con;
	}
	
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
