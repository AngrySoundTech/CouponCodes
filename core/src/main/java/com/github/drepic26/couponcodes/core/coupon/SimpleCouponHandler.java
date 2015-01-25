package com.github.drepic26.couponcodes.core.coupon;

import java.util.HashMap;

import com.github.drepic26.couponcodes.api.command.CommandSender;
import com.github.drepic26.couponcodes.api.coupon.CouponHandler;

public abstract class SimpleCouponHandler implements CouponHandler {

	public abstract HashMap<Integer, Integer> convertStringToHash(String args, CommandSender sender);

	public abstract String convertHashToString2(HashMap<String, Boolean> hash);

	public abstract String convertHashToString(HashMap<Integer, Integer> hash);
}
