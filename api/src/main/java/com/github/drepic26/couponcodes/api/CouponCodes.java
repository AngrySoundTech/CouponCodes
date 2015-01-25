package com.github.drepic26.couponcodes.api;

import com.github.drepic26.couponcodes.api.coupon.CouponHandler;
import com.github.drepic26.couponcodes.api.economy.EconomyHandler;
import com.github.drepic26.couponcodes.api.permission.PermissionHandler;

public class CouponCodes {

	private static ModTransformer modTransformer;
	private static PermissionHandler permissionHandler;
	private static EconomyHandler economyHandler;
	private static CouponHandler couponHandler;

	public static ModTransformer getModTransformer() { 
		return modTransformer;
	}

	public static PermissionHandler getPermissionHandler() {
		return permissionHandler;
	}

	public static EconomyHandler getEconomyHandler() {
		return economyHandler;
	}

	public static CouponHandler getCouponHandler() {
		return couponHandler;
	}

	public static void setModTransformer(ModTransformer mt) {
		modTransformer = mt;
	}

	public static void setPermissionHandler(PermissionHandler ph) {
		permissionHandler = ph;
	}

	public static void setEconomyHandler(EconomyHandler eh) {
		economyHandler = eh;
	}

	public static void setCouponHandler(CouponHandler ch) {
		couponHandler = ch;
	}

}
