package com.drevelopment.couponcodes.api.event.coupon;

import com.drevelopment.couponcodes.api.coupon.Coupon;
import com.drevelopment.couponcodes.api.event.Event;

/**
 * The base class for all coupon related events
 */
public abstract class CouponEvent extends Event {
	
	private Coupon coupon;
	
	public CouponEvent(Coupon coupon) {
		this.coupon = coupon;
	}
	
	public Coupon getCoupon() {
		return coupon;
	}

}
