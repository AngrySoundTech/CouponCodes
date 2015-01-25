package com.github.drepic26.couponcodes.api.coupon;

import java.util.HashMap;

public interface Coupon {
	
	/**
	 * @return True if the coupon was added to the database
	 */
	public boolean addToDatabase();
	
	/**
	 * @return True if the coupon was removed from the database
	 */
	public boolean removeFromDatabase();
	
	/**
	 * @return True if the coupon is in the database
	 */
	public boolean isInDatabase();
	
	/**
	 * Updates the coupon with the database
	 */
	public void updateWithDatabase();
	
	/**
	 * Updates the coupon's time with the database
	 */
	public void updateTimeWithDatabase();
	
	/**
	 * @return The name of the coupon
	 */
	public String getName();
	
	/**
	 * @param name The name to set
	 */
	public void setName(String name);
	
	/**
	 * @return The amount of times the coupon can be used
	 */
	public int getUseTimes();
	
	/**
	 * @param usetimes the amount of times the coupon can be used
	 */
	public void setUseTimes(int usetimes);
	
	/**
	 * @return Time before the coupon expires, in seconds
	 */
	public int  getTime();
	
	/**
	 * @param Time to set the coupon before it expires
	 */
	public void setTime(int time);
	
	/**
	 * The the key is the UUID of the player (as a string)
	 * @return Players who have used this coupon
	 */
	public HashMap<String, Boolean> getUsedPlayers();
	
	/**
	 * The key should be the UUID of a player (as a string)
	 * @param usedplayers HashMap of players and whether they have used the coupon 
	 */
	public void setUsedPlayers(HashMap<String, Boolean> usedplayers);
	
	/**
	 * @return the type of coupon
	 */
	public String getType();
	
	/**
	 * @return True if the coupon is expired
	 */
	public boolean isExpired();
	
	/**
	 * @param expired Whether the coupon is expired or not
	 */
	public void setExpired(boolean expired);
	

}
