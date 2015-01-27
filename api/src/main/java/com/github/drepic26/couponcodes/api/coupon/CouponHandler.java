package com.github.drepic26.couponcodes.api.coupon;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.drepic26.couponcodes.api.command.CommandSender;

public interface CouponHandler {

	/**
	 * Adds a coupon to the database.
	 * @param coupon The coupon to add to the database
	 * @return True if the coupon was successfully added
	 */
	public boolean addCouponToDatabase(Coupon coupon);

	/**
	 * Removes a coupon from the database.
	 * @param coupon The coupon to remove from the database
	 * @return True if the coupon was successfully removed
	 */
	public boolean removeCouponFromDatabase(Coupon coupon);

	/**
	 * Removes a coupon from the database by name
	 * @param coupon The name of the {@link Coupon} to remove from the database
	 * @return True if the coupon was successfully removed
	 */
	public boolean removeCouponFromDatabase(String coupon);

	/**
	 * Checks whether the coupon is in the database.
	 * @param coupon The coupon to check
	 * @return True if the coupon is in the database.
	 */
	public boolean couponExists(Coupon coupon);

	/**
	 * Checks whether the coupon is in the database by name.
	 * @param coupon The name of the coupon to check
	 * @return True if the coupon is in the database.
	 */
	public boolean couponExists(String coupon);

	/**
	 * Gets a list of all the coupons in the database.
	 * @return a list of all the coupons in the database
	 */
	public ArrayList<String> getCoupons();

	/**
	 * Updates a coupon in the database.
	 * @param coupon The coupon to update
	 */
	public void updateCoupon(Coupon coupon);

	/**
	 * Updates the time of a coupon with the database.
	 * @param coupon The coupon to update the time of
	 */
	public void updateCouponTime(Coupon coupon);

	/**
	 * Gets a coupon from the database.
	 * @param coupon The name of the coupon to get
	 * @return The coupon
	 */
	public Coupon getCoupon(String coupon);

	/**
	 * Gets the basic {@link Coupon} from the database.
	 * @param coupon The name of the coupon to get
	 * @return The basic coupon
	 */
	public Coupon getBasicCoupon(String coupon);

	/**
	 * Gets the amount of a certain type of coupon in the databaes
	 * <p> Type can be <code>item</code>, <code>econ</code>, <code>rank</code>, or <code>xp</code>,
	 * @param type The type of coupon to get the amount of
	 * @return The amount of the specified coupon type
	 */
	public int getAmountOf(String type);

	/**
	 * Creates a new {@link ItemCoupon}
	 * @param name The name of the coupon
	 * @param usetimes The amount of times the coupon can be used
	 * @param time The time the coupon has to be redeemed
	 * @param ids The ids the coupon is for
	 * @param usedplayers The players who have used this coupon
	 * @return The newly created coupon
	 */
	public ItemCoupon createNewItemCoupon(String name, int usetimes, int time, HashMap<Integer, Integer> ids, HashMap<String, Boolean> usedplayers);

	/**
	 * Creates a new {@link EconomyCoupon}
	 * @param name The name of the coupon
	 * @param usetimes The amount of times the coupon can be used
	 * @param time The time the coupon has to be redeemed
	 * @param money The money the coupon is for
	 * @param usedplayers The players who have used this coupon
	 * @return The newly created coupon
	 */
	public EconomyCoupon createNewEconomyCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, int money);

	/**
	 * Creates a new {@link RankCoupon}
	 * @param name The name of the coupon
	 * @param usetimes The amount of times the coupon can be used
	 * @param time The time the coupon has to be redeemed
	 * @param group The group the coupon is for
	 * @param usedplayers The players who have used this coupon
	 * @return The newly created coupon
	 */
	public RankCoupon createNewRankCoupon(String name, String group, int usetimes, int time, HashMap<String, Boolean> usedplayers);

	/**
	 * Creates a new {@link XpCoupon}
	 * @param name The name of the coupon
	 * @param usetimes The amount of times the coupon can be used
	 * @param time The time the coupon has to be redeemed
	 * @param xp The amount of xp the coupon is for
	 * @param usedplayers The players who have used this coupon
	 * @return The newly created coupon
	 */
	public XpCoupon createNewXpCoupon(String name, int xp, int usetimes, int time, HashMap<String, Boolean> usedplayers);

	public String itemHashToString(HashMap<Integer, Integer> hash);

	public HashMap<Integer, Integer> itemStringToHash(String args, CommandSender sender);

	public String playerHashToString(HashMap<String, Boolean> hash);

	public HashMap<String, Boolean> playerStringToHash(String args);

}
