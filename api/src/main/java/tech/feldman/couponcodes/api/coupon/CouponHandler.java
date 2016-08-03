/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (AngrySoundTech)
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tech.feldman.couponcodes.api.coupon;

import tech.feldman.couponcodes.api.command.CommandSender;
import tech.feldman.couponcodes.api.exceptions.UnknownMaterialException;

import java.util.ArrayList;
import java.util.HashMap;

public interface CouponHandler {

    /**
     * Adds a coupon to the database.
     * @param coupon The coupon to add to the database
     * @return True if the coupon was successfully added
     */
    boolean addCouponToDatabase(Coupon coupon);

    /**
     * Removes a coupon from the database.
     * @param coupon The coupon to remove from the database
     * @return True if the coupon was successfully removed
     */
    boolean removeCouponFromDatabase(Coupon coupon);

    /**
     * Removes a coupon from the database by name
     * @param coupon The name of the {@link Coupon} to remove from the database
     * @return True if the coupon was successfully removed
     */
    boolean removeCouponFromDatabase(String coupon);

    /**
     * Checks whether the coupon is in the database.
     * @param coupon The coupon to check
     * @return True if the coupon is in the database.
     */
    boolean couponExists(Coupon coupon);

    /**
     * Checks whether the coupon is in the database by name.
     * @param coupon The name of the coupon to check
     * @return True if the coupon is in the database.
     */
    boolean couponExists(String coupon);

    /**
     * Gets a list of all the coupons in the database.
     * @return a list of all the coupons in the database
     */
    ArrayList<String> getCoupons();

    /**
     * Updates a coupon in the database.
     * @param coupon The coupon to update
     */
    void updateCoupon(Coupon coupon);

    /**
     * Updates the time of a coupon with the database.
     * @param coupon The coupon to update the time of
     */
    void updateCouponTime(Coupon coupon);

    /**
     * Gets a coupon from the database.
     * @param coupon The name of the coupon to get
     * @return The coupon
     */
    Coupon getCoupon(String coupon);

    /**
     * Gets the amount of a certain type of coupon in the databaes
     * <p> Type can be <code>item</code>, <code>econ</code>, <code>rank</code>, or <code>xp</code>,
     * @param type The type of coupon to get the amount of
     * @return The amount of the specified coupon type
     */
    int getAmountOf(String type);

    /**
     * Creates a new {@link ItemCoupon}
     * @param name The name of the coupon
     * @param usetimes The amount of times the coupon can be used
     * @param time The time the coupon has to be redeemed
     * @param items The items the coupon is for
     * @param usedplayers The players who have used this coupon
     * @return The newly created coupon
     */
    ItemCoupon createNewItemCoupon(String name, int usetimes, int time, HashMap<String, Integer> items, HashMap<String, Boolean> usedplayers);

    /**
     * Creates a new {@link EconomyCoupon}
     * @param name The name of the coupon
     * @param usetimes The amount of times the coupon can be used
     * @param time The time the coupon has to be redeemed
     * @param money The money the coupon is for
     * @param usedplayers The players who have used this coupon
     * @return The newly created coupon
     */
    EconomyCoupon createNewEconomyCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, int money);

    /**
     * Creates a new {@link RankCoupon}
     * @param name The name of the coupon
     * @param usetimes The amount of times the coupon can be used
     * @param time The time the coupon has to be redeemed
     * @param group The group the coupon is for
     * @param usedplayers The players who have used this coupon
     * @return The newly created coupon
     */
    RankCoupon createNewRankCoupon(String name, String group, int usetimes, int time, HashMap<String, Boolean> usedplayers);

    /**
     * Creates a new {@link XpCoupon}
     * @param name The name of the coupon
     * @param usetimes The amount of times the coupon can be used
     * @param time The time the coupon has to be redeemed
     * @param xp The amount of xp the coupon is for
     * @param usedplayers The players who have used this coupon
     * @return The newly created coupon
     */
    XpCoupon createNewXpCoupon(String name, int xp, int usetimes, int time, HashMap<String, Boolean> usedplayers);

    /**
     * Creates a new {@link CommandCoupon}
     * @param name The name of the coupon
     * @param cmd The command the coupon will execute
     * @param usetimes The amount of times the coupon can be used
     * @param time The time the coupon has to be redeemed
     * @param usedplayers The players who have used this coupon
     * @return The newly created coupon
     */
    CommandCoupon createNewCommandCoupon(String name, String cmd, int usetimes, int time, HashMap<String, Boolean> usedplayers);

    String itemHashToString(HashMap<String, Integer> hash);

    HashMap<String, Integer> itemStringToHash(String args, CommandSender sender) throws UnknownMaterialException;

    String playerHashToString(HashMap<String, Boolean> hash);

    HashMap<String, Boolean> playerStringToHash(String args);

}
