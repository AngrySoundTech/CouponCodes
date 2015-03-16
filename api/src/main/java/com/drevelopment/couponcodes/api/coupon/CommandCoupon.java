package com.drevelopment.couponcodes.api.coupon;

public interface CommandCoupon extends Coupon {

	/**
	 * Gets the command the coupon will execute
	 * @return The command the coupon will execute
	 */
	public String getCmd();

	/**
	 * Sets the command the coupon will execute
	 * @param cmd The command the coupon will execute
	 */
	public void setCmd(String cmd);

}
