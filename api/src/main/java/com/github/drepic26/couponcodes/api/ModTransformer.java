package com.github.drepic26.couponcodes.api;

import com.github.drepic26.couponcodes.api.entity.Player;

public interface ModTransformer {

	/**
	 * Schedules a runnable.
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

}
