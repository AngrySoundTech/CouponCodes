package net.Drepic.CouponCodes.api.events.coupon;

import net.Drepic.CouponCodes.api.coupon.Coupon;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when the time changes on a coupon
 */
public class CouponTimeChangeEvent extends Event {

	private static final HandlerList h = new HandlerList();
	
	private Coupon coupon;
	
	public CouponTimeChangeEvent(Coupon coupon) {
		this.coupon = coupon;
	}
	
	/**
	 * Gets the coupon whose time changed
	 * @return The coupon whose time changed
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
