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
package tech.feldman.couponcodes.api.database;

import tech.feldman.couponcodes.api.coupon.Coupon;

import java.util.List;

public interface DatabaseHandler {

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
    List<String> getCoupons();

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
     * <p> Type can be <code>item</code>, <code>econ</code>, <code>rank</code>, <code>xp</code>, or <code>cmd</code>
     * @param type The type of coupon to get the amount of
     * @return The amount of the specified coupon type
     */
    int getAmountOf(String type);

}
