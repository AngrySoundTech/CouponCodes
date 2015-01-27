package com.github.drepic26.couponcodes.api.economy;

import com.github.drepic26.couponcodes.api.entity.Player;

public interface EconomyHandler {

	/**
	 * Gives money to the specified player.
	 * <p> This will not work if there is no economy handler installed
	 * @param uuid The unique identifier of the player to give the money to
	 * @param amount The amount of money to give to the player
	 */
	public abstract void giveMoney(String uuid, int amount);

	/**
	 * Sets the group of the specified player.
	 * This will not work if there is no permissions handler installed
	 * @param player The player to set the group of
	 * @param group The group to set the player to
	 */
	public abstract void setPlayerGroup(Player player, String group);

}
