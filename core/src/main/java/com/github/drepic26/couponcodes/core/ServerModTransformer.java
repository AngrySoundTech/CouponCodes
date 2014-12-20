package com.github.drepic26.couponcodes.core;

import java.util.HashMap;
import java.util.Map;

import com.github.drepic26.couponcodes.core.entity.Player;

public abstract class ServerModTransformer {
	
	/**
	 * Map of Players
	 */
	protected final Map<String, Player> players = new HashMap<String, Player>();
	
	/**
	 * Gets the player from the server mod
	 */
	protected abstract Player getModPlayer(String name);
	
	/**
	 * Gets a player. If we have not gotten them yet, we get them from the server
	 */
	public Player getPlayer(String name){ 
		if (players.containsKey(name)) return players.get(name);
		Player player = getModPlayer(name);
		if (player != null) {
			players.put(name, player);
			return player;
		}
		return player;
	}

}
