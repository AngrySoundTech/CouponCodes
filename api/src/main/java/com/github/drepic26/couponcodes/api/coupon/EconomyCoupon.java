package com.github.drepic26.couponcodes.api.coupon;

public interface EconomyCoupon extends Coupon {

	/**
	 * @return The amount of money the coupon is for
	 */
	public int getMoney();

	/**
	 * @param money The amount of money to set the coupon for
	 */
	public void setMoney(int money);

}
