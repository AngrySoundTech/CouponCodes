package com.github.drepic26.couponcodes.sponge.entity;

import com.github.drepic26.couponcodes.core.entity.Player;
import com.github.drepic26.couponcodes.core.util.Color;
import com.github.drepic26.couponcodes.sponge.SpongePlugin;

public class SpongePlayer extends Player {

	private final SpongePlugin plugin;
	private final org.spongepowered.api.entity.player.Player spongePlayer;

	public SpongePlayer(SpongePlugin plugin, org.spongepowered.api.entity.player.Player player) {
		this.plugin = plugin;
		this.spongePlayer = player;
	}

	@Override
	public void sendMessage(String message) {
		for (String line : message.split("\n")) {
			spongePlayer.sendMessage(Color.replaceColors(line));
		}
	}

	@Override
	public String getName() {
		return spongePlayer.getName();
	}

	@Override
	public void setLevel(int level) {
		//TODO Set level once Sponge implements it
	}

	@Override
	public int getLevel() {
		//TODO Get level once Sponge implements it
		return 0;
	}

	@Override
	public void giveItem(int item, int amount) {
		//TODO Give item once Sponge implements it
	}
}
