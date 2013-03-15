package net.Drepic.CouponCodes.listeners;

import net.Drepic.CouponCodes.CouponCodes;
import net.Drepic.CouponCodes.api.events.coupon.CouponAddToDatabaseEvent;
import net.Drepic.CouponCodes.api.events.coupon.CouponExpireEvent;
import net.Drepic.CouponCodes.api.events.coupon.CouponRemoveFromDatabaseEvent;
import net.Drepic.CouponCodes.api.events.coupon.CouponTimeChangeEvent;
import net.Drepic.CouponCodes.api.events.database.DatabaseCloseConnectionEvent;
import net.Drepic.CouponCodes.api.events.database.DatabaseOpenConnectionEvent;
import net.Drepic.CouponCodes.api.events.database.DatabaseQueryEvent;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class DebugListen implements Listener {

	private CouponCodes plugin;
	
	public DebugListen(CouponCodes plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCouponAddToDatabase(CouponAddToDatabaseEvent event) {
		plugin.debug("Coupon added to database. Name: "+event.getCoupon().getName());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCouponRemoveFromDatabase(CouponRemoveFromDatabaseEvent event) {
		plugin.debug("Coupon removed from database. Name: "+event.getCoupon());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCouponExpireEvent(CouponExpireEvent event) {
		plugin.debug("Coupon expired. Name: "+event.getCoupon().getName());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCouponTimeChangeEvent(CouponTimeChangeEvent event) {
		plugin.debug("Coupon time changed. Name: "+event.getCoupon().getName()+" New time: "+event.getCoupon().getTime());
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDatabaseOpenConnection(DatabaseOpenConnectionEvent event) {
		plugin.debug("Connection opened to database");
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDatabaseCloseConnection(DatabaseCloseConnectionEvent event) {
		plugin.debug("Connection closed to database");
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onDatabaseQuery(DatabaseQueryEvent event) {
		plugin.debug("Query sent to database: "+event.getQuery());
	}
 }
