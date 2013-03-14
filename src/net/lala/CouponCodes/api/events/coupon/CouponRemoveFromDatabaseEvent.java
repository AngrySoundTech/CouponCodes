package net.lala.CouponCodes.api.events.coupon;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CouponRemoveFromDatabaseEvent extends Event {

	private static final HandlerList h = new HandlerList();
	
	private String coupon;
	
	public CouponRemoveFromDatabaseEvent(String coupon) {
		this.coupon = coupon;
	}
	
	public String getCoupon() {
		return coupon;
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
