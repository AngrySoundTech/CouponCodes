package com.drevelopment.couponcodes.api.event.coupon;

import com.drevelopment.couponcodes.api.coupon.Coupon;

/**
 * Fired when a coupon is removed from the database.
 */
public class CouponRemoveFromDatabaseEvent extends CouponEvent {

	public CouponRemoveFromDatabaseEvent(Coupon coupon) {
		super(coupon);
	}
}
