package com.drevelopment.couponcodes.api.event.coupon;

import com.drevelopment.couponcodes.api.coupon.Coupon;

/**
 * Fired when a coupon is added to the database.
 */
public class CouponAddToDatabaseEvent extends CouponEvent {

	public CouponAddToDatabaseEvent(Coupon coupon) {
		super(coupon);
	}

}
