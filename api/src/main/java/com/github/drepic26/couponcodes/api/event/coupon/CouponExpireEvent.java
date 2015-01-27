package com.github.drepic26.couponcodes.api.event.coupon;

import com.github.drepic26.couponcodes.api.coupon.Coupon;

/**
 * Fired when a coupon expires.
 */
public class CouponExpireEvent extends CouponEvent {

	public CouponExpireEvent(Coupon coupon) {
		super(coupon);
	}

}
