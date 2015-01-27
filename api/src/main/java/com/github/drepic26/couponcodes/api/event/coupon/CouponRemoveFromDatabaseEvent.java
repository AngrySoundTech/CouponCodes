package com.github.drepic26.couponcodes.api.event.coupon;

import com.github.drepic26.couponcodes.api.coupon.Coupon;

/**
 * Fired when a coupon is removed from the database.
 */
public class CouponRemoveFromDatabaseEvent extends CouponEvent {

	public CouponRemoveFromDatabaseEvent(Coupon coupon) {
		super(coupon);
	}
}
