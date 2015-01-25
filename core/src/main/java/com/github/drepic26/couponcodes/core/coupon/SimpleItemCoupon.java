package com.github.drepic26.couponcodes.core.coupon;

import java.util.HashMap;

import com.github.drepic26.couponcodes.api.coupon.ItemCoupon;

public class SimpleItemCoupon extends SimpleCoupon implements ItemCoupon {

	private HashMap<Integer, Integer> ids;

	public SimpleItemCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, HashMap<Integer, Integer> ids) {
		super(name, usetimes, time, usedplayers);
		this.ids = ids;
	}

	public HashMap<Integer, Integer> getIDs() {
		return ids;
	}
}
