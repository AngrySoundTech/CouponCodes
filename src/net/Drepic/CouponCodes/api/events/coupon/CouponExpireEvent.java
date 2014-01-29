package net.Drepic.CouponCodes.api.events.coupon;

import net.Drepic.CouponCodes.api.coupon.Coupon;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called ehen a coupon expires
 */
public class CouponExpireEvent extends Event {

	private static final HandlerList h = new HandlerList();
	
	private Coupon coupon;
	
	public CouponExpireEvent(Coupon coupon) {
		this.coupon = coupon;
	}
	
	/**
	 * Gets the coupon that expired
	 * @return The coupon that expired
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
