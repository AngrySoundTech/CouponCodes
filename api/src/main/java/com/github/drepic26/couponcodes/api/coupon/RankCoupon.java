package com.github.drepic26.couponcodes.api.coupon;

public interface RankCoupon extends Coupon {

	/**
	 * Gets the group this coupon is for
	 * @return String The group this coupon is for
	 */
	public String getGroup();

	/**
	 * Sets the group this coupon is for
	 * @param group The group to set this coupon for
	 */
	public void setGroup(String group);

}
