package com.drevelopment.couponcodes.canary.entity;

import net.canarymod.Canary;

import com.drevelopment.couponcodes.core.entity.SimplePlayer;

public class CanaryPlayer extends SimplePlayer {

	private final net.canarymod.api.entity.living.humanoid.Player canaryPlayer;

	public CanaryPlayer(net.canarymod.api.entity.living.humanoid.Player canaryPlayer) {
		this.canaryPlayer = canaryPlayer;
	}

	@Override
	public boolean hasPermission(String node) {
		return canaryPlayer.hasPermission(node);
	}

	@Override
	public void sendMessage(String message) {
		canaryPlayer.message(message);
	}

	@Override
	public void setLevel(int level) {
		canaryPlayer.setLevel(level);
	}

	@Override
	public int getLevel() {
		return canaryPlayer.getLevel();
	}

	@Override
	public void giveItem(int item, int amount) {
		canaryPlayer.giveItem(Canary.factory().getItemFactory().newItem(item, 0, amount));
	}

	@Override
	public String getLocale() {
		return canaryPlayer.getLocale();
	}

	@Override
	public String getUUID() {
		return canaryPlayer.getUUIDString();
	}

}
