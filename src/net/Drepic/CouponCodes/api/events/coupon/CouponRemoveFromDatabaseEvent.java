package net.Drepic.CouponCodes.api.events.coupon;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a coupon is removed from the database
 */
public class CouponRemoveFromDatabaseEvent extends Event {

	private static final HandlerList h = new HandlerList();
	
	private String coupon;
	
	public CouponRemoveFromDatabaseEvent(String coupon) {
		this.coupon = coupon;
	}
	
	/**
	 * Gets the coupon that was removed from the database
	 * @return The coupon that was removed from the database
	 */
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
