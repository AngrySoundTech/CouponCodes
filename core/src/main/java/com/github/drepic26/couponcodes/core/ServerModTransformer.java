package com.github.drepic26.couponcodes.core;

import java.util.HashMap;
import java.util.Map;

import com.github.drepic26.couponcodes.core.entity.Player;
import com.github.drepic26.couponcodes.core.permission.PermissionHandler;

public abstract class ServerModTransformer {
	
	private static ServerModTransformer instance = null;

	private PermissionHandler permissionHandler = null;

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

	public abstract void scheduleRunnable(Runnable runnable);

	/**
	 * Gets the current instance
	 */
	public static ServerModTransformer getInstance() {
		return instance;
	}

	/**
	 * Sets the instance
	 */
	protected static void setInstance(ServerModTransformer inst) {
		instance = inst;
	}

	public PermissionHandler getPermissionHandler() {
		return permissionHandler;
	}

	public void setPermissionHandler(PermissionHandler permissionHandler) {
		this.permissionHandler = permissionHandler;
	}
}
