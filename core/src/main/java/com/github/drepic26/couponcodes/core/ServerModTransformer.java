package com.github.drepic26.couponcodes.core;

import java.util.HashMap;
import java.util.Map;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.ModTransformer;
import com.github.drepic26.couponcodes.api.coupon.CouponHandler;
import com.github.drepic26.couponcodes.core.economy.EconomyHandler;
import com.github.drepic26.couponcodes.core.entity.SimplePlayer;
import com.github.drepic26.couponcodes.core.permission.SimplePermissionHandler;

public abstract class ServerModTransformer implements ModTransformer {

	private SimplePermissionHandler permissionHandler;
	private CouponHandler couponHandler;

	private EconomyHandler economyHandler;

	/**
	 * Map of Players
	 */
	protected final Map<String, SimplePlayer> players = new HashMap<String, SimplePlayer>();
	
	public ServerModTransformer() {
		CouponCodes.setModTransformer(this);
	}

	/**
	 * Gets the player from the server mod
	 */
	protected abstract SimplePlayer getModPlayer(String name);

	/**
	 * Gets a player. If we have not gotten them yet, we get them from the server
	 */
	public SimplePlayer getPlayer(String name){ 
		if (players.containsKey(name)) return players.get(name);
		SimplePlayer player = getModPlayer(name);
		if (player != null) {
			players.put(name, player);
			return player;
		}
		return player;
	}

	public abstract void scheduleRunnable(Runnable runnable);

	public SimplePermissionHandler getPermissionHandler() {
		return permissionHandler;
	}

	public void setPermissionHandler(SimplePermissionHandler permissionHandler) {
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
