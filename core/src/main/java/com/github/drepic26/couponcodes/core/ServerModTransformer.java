package com.github.drepic26.couponcodes.core;

import java.util.HashMap;
import java.util.Map;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.ModTransformer;
import com.github.drepic26.couponcodes.api.entity.Player;
import com.github.drepic26.couponcodes.core.event.SimpleEventHandler;

public abstract class ServerModTransformer implements ModTransformer {

	/**
	 * Map of Players
	 */
	protected final Map<String, Player> players = new HashMap<String, Player>();
	
	public ServerModTransformer() {
		CouponCodes.setModTransformer(this);
		CouponCodes.setEventHandler(new SimpleEventHandler());
	}

	/**
	 * Gets the player from the server software, wrapped by {@link Player}
	 * @param UUID The UUID of the player to get
	 * @return Player A new instance of the player
	 */
	protected abstract Player getModPlayer(String UUID);

	/**
	 * Gets a player. If we have not gotten them yet, we get them from the server
	 */
	public Player getPlayer(String UUID) {
		if (players.containsKey(UUID)) return players.get(UUID);
		Player player = getModPlayer(UUID);
		if (player != null) {
			players.put(UUID, player);
			return player;
		}
		return player;
	}

	public abstract void scheduleRunnable(Runnable runnable);
}
