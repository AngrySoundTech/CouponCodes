package com.github.drepic26.couponcodes.core.coupon;

import java.util.HashMap;

import com.github.drepic26.couponcodes.api.coupon.RankCoupon;

public class SimpleRankCoupon extends SimpleCoupon implements RankCoupon {

	private String group;

	public SimpleRankCoupon(String name, String group, int usetimes, int time, HashMap<String, Boolean> usedplayers) {
		super(name, usetimes, time, usedplayers);
		this.group = group;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
