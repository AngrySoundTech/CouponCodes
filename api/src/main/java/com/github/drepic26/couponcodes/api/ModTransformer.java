package com.github.drepic26.couponcodes.api;

import com.github.drepic26.couponcodes.api.entity.Player;

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
	 * <b>*{@link #getPlayer(String)} should be used instead*</b><p>
	 * Gets the player from the server software, wrapped by {@link Player}
	 * @param UUID The UUID of the player to get
	 * @return Player A new instance of the player
	 */
	public Player getModPlayer(String UUID);

}
