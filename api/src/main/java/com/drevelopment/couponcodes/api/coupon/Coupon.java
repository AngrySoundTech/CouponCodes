package com.drevelopment.couponcodes.api.coupon;

import java.util.HashMap;

public interface Coupon {

	/**
	 * Adds this coupon to the database.
	 * @return True if this coupon was added to the database
	 */
	public boolean addToDatabase();

	/**
	 * Removes this coupon from the database.
	 * @return True if this coupon was removed from the database
	 */
	public boolean removeFromDatabase();

	/**
	 * Checks if this coupon is in the database.
	 * @return True if this coupon is in the database
	 */
	public boolean isInDatabase();

	/**
	 * Updates this coupon with the database.
	 */
	public void updateWithDatabase();

	/**
	 * Updates this coupon's time with the database.
	 */
	public void updateTimeWithDatabase();

	/**
	 * Gets the current name of this coupon.
	 * @return The name of this coupon
	 */
	public String getName();

	/**
	 * Sets the name of this coupon.
	 * @param name The name to set this coupon to
	 */
	public void setName(String name);

	/**
	 * Gets the amount of times this coupon can be used.
	 * @return The amount of times this coupon can be used
	 */
	public int getUseTimes();

	/**
	 * Sets the amount of times this coupon can be used.
	 * @param usetimes The amount of times this coupon can be used
	 */
	public void setUseTimes(int usetimes);

	/**
	 * Gets the amount of time before this coupon expires.
	 * <p> Time is measured in seconds
	 * @return Time before this coupon expires
	 */
	public int getTime();

	/**
	 * Sets the time before this coupon expires.
	 * <p> Time is measured in seconds
	 * @param time Time to set this coupon to before it expires
	 */
	public void setTime(int time);

	/**
	 * Gets the players who have used this coupon.
	 * <p> The the key is the UUID of the player, and the value is whether they have used it
	 * @return Players who have used this coupon
	 */
	public HashMap<String, Boolean> getUsedPlayers();

	/**
	 * Sets the players who have used this coupon.
	 * <p> The key should be the UUID of a player, and the value should be whether they have used it
	 * @param usedplayers HashMap of players and whether they have used this coupon
	 */
	public void setUsedPlayers(HashMap<String, Boolean> usedplayers);

	/**
	 * Gets the type of coupon this is.
	 * <p> This will return as follows:
	 * <br><code>item</code> for an {@link ItemCoupon}
	 * <br><code>econ</code> for an {@link EconomyCoupon}
	 * <br><code>rank</code> for an {@link RankCoupon}
	 * <br><code>xp</code> for an {@link XpCoupon}
	 * @return The type of coupon this is
	 */
	public String getType();

	/**
	 * Gets whether or not the coupon is expired.
	 * @return True if the coupon is expired
	 */
	public boolean isExpired();

	/**
	 * Sets the coupon as expired or not.
	 * @param expired Whether the coupon is expired or not
	 */
	public void setExpired(boolean expired);

}
