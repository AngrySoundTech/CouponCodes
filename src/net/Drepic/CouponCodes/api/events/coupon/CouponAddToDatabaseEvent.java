package net.Drepic.CouponCodes.api.events.coupon;

import net.Drepic.CouponCodes.api.coupon.Coupon;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when coupon is added to the database
 */
public class CouponAddToDatabaseEvent extends Event {

	private static final HandlerList h = new HandlerList();
	
	private Coupon coupon;
	
	public CouponAddToDatabaseEvent(Coupon coupon) {
		this.coupon = coupon;
	}
	
	/**
	 * Gets the coupon that was added in this event
	 * @return The coupon that was added in this event
	 */
	public Coupon getCoupon() {
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
