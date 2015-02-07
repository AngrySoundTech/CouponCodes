package com.drevelopment.couponcodes.core.coupon;

import java.util.HashMap;

import com.drevelopment.couponcodes.api.coupon.XpCoupon;

public class SimpleXpCoupon extends SimpleCoupon implements XpCoupon {

	private int xp;

	public SimpleXpCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, int xp) {
		super(name, usetimes, time, usedplayers);
		this.xp = xp;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

}
