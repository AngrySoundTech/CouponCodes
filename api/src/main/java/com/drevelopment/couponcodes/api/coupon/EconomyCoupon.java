package com.drevelopment.couponcodes.api.coupon;

public interface EconomyCoupon extends Coupon {

	/**
	 * Gets the amount of money the coupon is for
	 * @return The amount of money the coupon is for
	 */
	public int getMoney();

	/**
	 * Sets the amount of money the coupon is for
	 * @param money The amount of money to set the coupon for
	 */
	public void setMoney(int money);

}
