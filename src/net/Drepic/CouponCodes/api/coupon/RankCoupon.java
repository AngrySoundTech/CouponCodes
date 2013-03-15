package net.Drepic.CouponCodes.api.coupon;

import java.util.HashMap;

public class RankCoupon extends Coupon {

	private String group;
	
	public RankCoupon(String name, String group, int usetimes, int time, HashMap<String, Boolean> usedplayers) {
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
