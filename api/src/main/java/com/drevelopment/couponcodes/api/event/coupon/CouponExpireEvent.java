package com.drevelopment.couponcodes.api.event.coupon;

import com.drevelopment.couponcodes.api.coupon.Coupon;

/**
 * Fired when a coupon expires.
 */
public class CouponExpireEvent extends CouponEvent {

	public CouponExpireEvent(Coupon coupon) {
		super(coupon);
	}

}
