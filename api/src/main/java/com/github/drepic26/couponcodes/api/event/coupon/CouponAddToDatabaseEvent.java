package com.github.drepic26.couponcodes.api.event.coupon;

import com.github.drepic26.couponcodes.api.coupon.Coupon;
import com.github.drepic26.couponcodes.api.event.Event;

public class CouponAddToDatabaseEvent extends Event {

	private Coupon coupon;

	public CouponAddToDatabaseEvent(Coupon coupon) {
		this.coupon = coupon;
	}

	public Coupon getCoupon() {
		return coupon;
	}

}
