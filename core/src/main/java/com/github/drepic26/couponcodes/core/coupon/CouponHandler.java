package com.github.drepic26.couponcodes.core.coupon;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.drepic26.couponcodes.core.commands.CommandSender;

public abstract class CouponHandler {

	public abstract boolean addCouponToDatabase(Coupon coupon);

	public abstract boolean removeCouponFromDatabase(Coupon coupon);

	public abstract boolean removeCouponFromDatabase(String coupon);

	public abstract boolean couponExists(Coupon coupon);

	public abstract boolean couponExists(String coupon);

	public abstract ArrayList<String> getCoupons();

	public abstract void updateCoupon(Coupon coupon);

	public abstract void updateCouponTime(Coupon coupon);

	public abstract Coupon getCoupon(String coupon);

	public abstract Coupon getBasicCoupon(String coupon);

	public abstract int getAmountOf(String type);

	public abstract ItemCoupon createNewItemCoupon(String name, int usetimes, int time, HashMap<Integer, Integer> ids, HashMap<String, Boolean> usedplayers);

	public abstract EconomyCoupon createNewEconomyCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, int money);

	public abstract RankCoupon createNewRankCoupon(String name, String group, int usetimes, int time, HashMap<String, Boolean> usedplayers);

	public abstract XpCoupon createNewXpCoupon(String name, int xp, int usetimes, int time, HashMap<String, Boolean> usedplayers);

	public abstract HashMap<Integer, Integer> convertStringToHash(String args, CommandSender sender);

	public abstract String convertHashToString2(HashMap<String, Boolean> hash);

	public abstract String convertHashToString(HashMap<Integer, Integer> hash);
}
