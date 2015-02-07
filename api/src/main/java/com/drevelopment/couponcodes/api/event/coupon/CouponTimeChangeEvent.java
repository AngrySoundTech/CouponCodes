package com.drevelopment.couponcodes.api.event.coupon;

import com.drevelopment.couponcodes.api.coupon.Coupon;

/**
 * Fired when the time changes on a coupon.
 */
public class CouponTimeChangeEvent extends CouponEvent {

	public CouponTimeChangeEvent(Coupon coupon) {
		super(coupon);
	}

}
