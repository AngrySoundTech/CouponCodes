package com.github.drepic26.couponcodes.api.event.coupon;

import com.github.drepic26.couponcodes.api.coupon.Coupon;

/**
 * Fired when a coupon is added to the database.
 */
public class CouponAddToDatabaseEvent extends CouponEvent {

	public CouponAddToDatabaseEvent(Coupon coupon) {
		super(coupon);
	}

}
