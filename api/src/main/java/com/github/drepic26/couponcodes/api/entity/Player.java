package com.github.drepic26.couponcodes.api.entity;

import com.github.drepic26.couponcodes.api.command.CommandSender;

public interface Player extends CommandSender {

	/**
	 * Gets the name of the player.
	 * @return The name of the player
	 */
	public String getName();

	/**
	 * Gets the unique identifier of a player.
	 * @return the unique identifier of the player
	 */
	public String getUUID();

	/**
	 * Sets the level of the player.
	 * @param xp The level of the player to set to
	 */
	public void setLevel(int xp);

	/**
	 * Gets the current level of the player.
	 * @return The current level of the player
	 */
	public int getLevel();

	/**
	 * Gives the player the specified item and amount.
	 * @param item The id of the item to give the player
	 * @param amount The amount of the item to give the player
	 */
	public void giveItem(int item, int amount);

	public String getLocale();

}
