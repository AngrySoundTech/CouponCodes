package com.github.drepic26.couponcodes.api;

import com.github.drepic26.couponcodes.api.coupon.CouponHandler;
import com.github.drepic26.couponcodes.api.entity.Player;

public interface ModTransformer {
	
	/**
	 * Gets the current instance of the CouponHandler
	 * @return the current instance of the CouponHandler
	 */
	public CouponHandler getCouponHandler();
	
	public void scheduleRunnable(Runnable runnable);
	
	public Player getPlayer();

}
