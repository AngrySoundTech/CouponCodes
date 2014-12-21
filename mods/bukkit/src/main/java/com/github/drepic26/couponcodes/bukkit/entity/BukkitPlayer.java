package com.github.drepic26.couponcodes.bukkit.entity;

import com.github.drepic26.couponcodes.bukkit.BukkitPlugin;
import com.github.drepic26.couponcodes.core.entity.Player;

public class BukkitPlayer extends Player {

	private final BukkitPlugin plugin;
	private final org.bukkit.entity.Player bukkitPlayer;

	public BukkitPlayer(BukkitPlugin plugin, org.bukkit.entity.Player bukkitPlayer) {
		this.plugin = plugin;
		this.bukkitPlayer = bukkitPlayer;
	}

	@Override
	public void sendMessage(String message) {
		for (String line : message.split("\n")) {
			bukkitPlayer.sendMessage(line);
		}
	}

	@Override
	public String getName() {
		return bukkitPlayer.getName();
	}

}
