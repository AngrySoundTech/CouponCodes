package com.drevelopment.couponcodes.api.economy;


public interface EconomyHandler {

	/**
	 * Gives money to the specified player.
	 * <p> This will not work if there is no economy handler installed
	 * @param uuid The unique identifier of the player to give the money to
	 * @param amount The amount of money to give to the player
	 */
	public abstract void giveMoney(String uuid, int amount);

}
