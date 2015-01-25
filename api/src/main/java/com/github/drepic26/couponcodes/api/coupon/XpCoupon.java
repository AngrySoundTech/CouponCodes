package com.github.drepic26.couponcodes.api.coupon;

public interface XpCoupon extends Coupon {
	
	/**
	 * @return The amount of XP (in levels) the coupon is for
	 */
	public int getXp();
	
	/**
	 * @param xp The amount of xp to set the coupon worth
	 */
	public void setXp(int xp);

}
