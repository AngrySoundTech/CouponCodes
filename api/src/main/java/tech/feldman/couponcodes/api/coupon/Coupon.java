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

import java.util.HashMap;

public interface Coupon {

    /**
     * Adds this coupon to the database.
     * @return True if this coupon was added to the database
     */
    boolean addToDatabase();

    /**
     * Removes this coupon from the database.
     * @return True if this coupon was removed from the database
     */
    boolean removeFromDatabase();

    /**
     * Checks if this coupon is in the database.
     * @return True if this coupon is in the database
     */
    boolean isInDatabase();

    /**
     * Updates this coupon with the database.
     */
    void updateWithDatabase();

    /**
     * Updates this coupon's time with the database.
     */
    void updateTimeWithDatabase();

    /**
     * Gets the current name of this coupon.
     * @return The name of this coupon
     */
    String getName();

    /**
     * Sets the name of this coupon.
     * @param name The name to set this coupon to
     */
    void setName(String name);

    /**
     * Gets the amount of times this coupon can be used.
     * @return The amount of times this coupon can be used
     */
    int getUseTimes();

    /**
     * Sets the amount of times this coupon can be used.
     * @param usetimes The amount of times this coupon can be used
     */
    void setUseTimes(int usetimes);

    /**
     * Gets the amount of time before this coupon expires.
     * <p> Time is measured in seconds
     * @return Time before this coupon expires
     */
    int getTime();

    /**
     * Sets the time before this coupon expires.
     * <p> Time is measured in seconds
     * @param time Time to set this coupon to before it expires
     */
    void setTime(int time);

    /**
     * Gets the players who have used this coupon.
     * <p> The the key is the UUID of the player, and the value is whether they have used it
     * @return Players who have used this coupon
     */
    HashMap<String, Boolean> getUsedPlayers();

    /**
     * Sets the players who have used this coupon.
     * <p> The key should be the UUID of a player, and the value should be whether they have used it
     * @param usedplayers HashMap of players and whether they have used this coupon
     */
    void setUsedPlayers(HashMap<String, Boolean> usedplayers);

    /**
     * Gets the type of coupon this is.
     * <p> This will return as follows:
     * <br><code>item</code> for an {@link ItemCoupon}
     * <br><code>econ</code> for an {@link EconomyCoupon}
     * <br><code>rank</code> for an {@link RankCoupon}
     * <br><code>xp</code> for an {@link XpCoupon}
     * @return The type of coupon this is
     */
    String getType();

    /**
     * Gets whether or not the coupon is expired.
     * @return True if the coupon is expired
     */
    boolean isExpired();

    /**
     * Sets the coupon as expired or not.
     * @param expired Whether the coupon is expired or not
     */
    void setExpired(boolean expired);

}
