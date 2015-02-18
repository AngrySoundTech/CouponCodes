package com.drevelopment.couponcodes.api;

import com.drevelopment.couponcodes.api.entity.Player;

public interface ModTransformer {

	/**
	 * Schedules a runnable to run immediately.
	 * <p> Schedules a delayed task on the server
	 * @param runnable Runnable to schedule
	 */
	public void scheduleRunnable(Runnable runnable);

	/**
	 * Gets the Player.
	 * <p> If the player has not been gotten before, gets and wraps the player from the server.
	 * @param UUID The unique identifier of the player to get
	 * @return The player
	 */
	public Player getPlayer(String UUID);

	/**
	 * <b>{@link #getPlayer(String)} should be used instead</b><p>
	 * Gets the player from the server software, wrapped by {@link Player}
	 * @param UUID The UUID of the player to get
	 * @return Player A new instance of the player
	 */
	public Player getModPlayer(String UUID);

	/**
	 * Removes a player wrapper from the plugin.
	 * <p>A player should be removed when they log out, so that they can be wrapped again when
	 * they next log in</p>
	 * @param player The player to remove
	 */
	public void removePlayer(Player player);

	/**
	 * This should be used in case the player you want to get the name of may be offline
	 * @param UUID The UUID of the player's name to get
	 * @return The name of the player
	 */
	public String getPlayerName(String UUID);

}
