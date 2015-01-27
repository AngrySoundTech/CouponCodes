package com.github.drepic26.couponcodes.api.event.coupon;

import com.github.drepic26.couponcodes.api.coupon.Coupon;
import com.github.drepic26.couponcodes.api.event.Event;

public class CouponExpireEvent extends Event {

	private Coupon coupon;

	public CouponExpireEvent(Coupon coupon) {
		this.coupon = coupon;
	}

	public Coupon getCoupon() {
		return coupon;
	}

}
