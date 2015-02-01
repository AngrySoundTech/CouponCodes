package com.github.drepic26.couponcodes.canary.coupon;

import java.util.ArrayList;

import net.canarymod.tasks.ServerTask;
import net.canarymod.tasks.TaskOwner;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.coupon.Coupon;
import com.github.drepic26.couponcodes.api.event.coupon.CouponTimeChangeEvent;

public class CanaryCouponTimer extends ServerTask {

	public CanaryCouponTimer(TaskOwner owner, long delay) {
		super(owner, delay);
	}

	private ArrayList<String> cl = new ArrayList<String>();
	private Coupon c;

	@Override
	public void run() {
		cl = CouponCodes.getCouponHandler().getCoupons();
		if (cl == null) return;

		for (String name : cl) {
			c = CouponCodes.getCouponHandler().getBasicCoupon(name);
			if (c == null) continue;
			if (c.isExpired() || c.getTime() == -1) continue;

			if (c.getTime()-10 < 0) {
				if (c.getTime()-5 < 0 || c.getTime()-5 == 0) {
					c.setTime(0);
				} else {
					c.setTime(5);
				}
			} else {
				c.setTime(c.getTime()-10);
			}
			CouponCodes.getCouponHandler().updateCouponTime(c);
			CouponCodes.getEventHandler().post(new CouponTimeChangeEvent(c));
		}
	}

}
