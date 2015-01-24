package com.github.drepic26.couponcodes.core;

import java.util.HashMap;
import java.util.Map;

import com.github.drepic26.couponcodes.core.coupon.CouponHandler;
import com.github.drepic26.couponcodes.core.economy.EconomyHandler;
import com.github.drepic26.couponcodes.core.entity.Player;
import com.github.drepic26.couponcodes.core.permission.PermissionHandler;

public abstract class ServerModTransformer {
	
	private static ServerModTransformer instance = null;

	private PermissionHandler permissionHandler = null;
	private CouponHandler couponHandler = null;

	private EconomyHandler economyHandler = null;

	/**
	 * Map of Players
	 */
	protected final Map<String, Player> players = new HashMap<String, Player>();

	/**
	 * Gets the player from the server mod
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

	public CouponHandler getCouponHandler() {
		return couponHandler;
	}

	public void setCouponHandler(CouponHandler couponHandler) {
		this.couponHandler = couponHandler;
	}

	public EconomyHandler getEconomyHandler() {
		return economyHandler;
	}

	public void setEconomyHandler(EconomyHandler economyHandler) {
		this.economyHandler = economyHandler;
	}
}
