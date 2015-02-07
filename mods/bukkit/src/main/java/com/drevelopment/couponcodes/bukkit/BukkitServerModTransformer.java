package com.drevelopment.couponcodes.bukkit;

import java.util.UUID;

import org.bukkit.Bukkit;

import com.drevelopment.couponcodes.api.entity.Player;
import com.drevelopment.couponcodes.bukkit.entity.BukkitPlayer;
import com.drevelopment.couponcodes.core.ServerModTransformer;

public class BukkitServerModTransformer extends ServerModTransformer {

	private BukkitPlugin plugin;

	public BukkitServerModTransformer(BukkitPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void scheduleRunnable(Runnable runnable) {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, runnable);
	}

	@Override
	public Player getModPlayer(String uuid) {
		org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(UUID.fromString(uuid));

		if (bukkitPlayer == null)
			return null;

		return new BukkitPlayer(plugin, bukkitPlayer);
	}

	@Override
	public String getPlayerName(String uuid) {
		return Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
	}

}
