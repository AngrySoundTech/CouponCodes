package net.Drepic.CouponCodes.api.coupon;

import java.util.HashMap;

public class XpCoupon extends Coupon {

	private int xp;
	
	public XpCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, int xp) {
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
