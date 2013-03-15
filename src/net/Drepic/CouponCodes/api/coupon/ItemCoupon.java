package net.Drepic.CouponCodes.api.coupon;

import java.util.HashMap;

public class ItemCoupon extends Coupon {

	private HashMap<Integer, Integer> ids;
	
	public ItemCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, HashMap<Integer, Integer> ids) {
		super(name, usetimes, time, usedplayers);
		this.ids = ids;
	}
	
	public HashMap<Integer, Integer> getIDs() {
		return ids;
	}
}
