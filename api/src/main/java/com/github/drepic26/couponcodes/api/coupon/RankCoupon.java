package com.github.drepic26.couponcodes.api.coupon;

public interface RankCoupon extends Coupon {
	
	/**
	 * @return String The group the coupon is for
	 */
	public String getGroup();
	
	/**
	 * @param group The group to set the coupon for
	 */
	public void setGroup(String group);
	

}
