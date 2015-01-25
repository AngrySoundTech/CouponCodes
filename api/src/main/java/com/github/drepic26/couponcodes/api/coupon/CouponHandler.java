package com.github.drepic26.couponcodes.api.coupon;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.drepic26.couponcodes.api.command.CommandSender;

public interface CouponHandler {

	public boolean addCouponToDatabase(Coupon coupon);

	public boolean removeCouponFromDatabase(Coupon coupon);

	public boolean removeCouponFromDatabase(String coupon);

	public boolean couponExists(Coupon coupon);

	public boolean couponExists(String coupon);

	public ArrayList<String> getCoupons();

	public void updateCoupon(Coupon coupon);

	public void updateCouponTime(Coupon coupon);

	public Coupon getCoupon(String coupon);

	public Coupon getBasicCoupon(String coupon);

	public int getAmountOf(String type);

	public ItemCoupon createNewItemCoupon(String name, int usetimes, int time, HashMap<Integer, Integer> ids, HashMap<String, Boolean> usedplayers);

	public EconomyCoupon createNewEconomyCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, int money);

	public RankCoupon createNewRankCoupon(String name, String group, int usetimes, int time, HashMap<String, Boolean> usedplayers);

	public XpCoupon createNewXpCoupon(String name, int xp, int usetimes, int time, HashMap<String, Boolean> usedplayers);
	
	public HashMap<Integer, Integer> convertStringToHash(String args, CommandSender sender);

	public String convertHashToString2(HashMap<String, Boolean> hash);

	public String convertHashToString(HashMap<Integer, Integer> hash);

}
