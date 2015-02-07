package com.drevelopment.couponcodes.api.coupon;

public interface XpCoupon extends Coupon {

	/**
	 * Gets the amount of xp this coupon is for
	 * <p> Amount of xp is measured in levels
	 * @return The amount of XP this coupon is for
	 */
	public int getXp();

	/**
	 * Sets the amount of xp this coupon is for
	 * <p> Amount of xp is measured in levels
	 * @param xp The amount of xp to set this coupon worth
	 */
	public void setXp(int xp);

}
