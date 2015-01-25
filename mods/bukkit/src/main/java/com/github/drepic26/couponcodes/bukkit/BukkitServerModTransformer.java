package com.github.drepic26.couponcodes.bukkit;

import java.util.UUID;

import org.bukkit.Bukkit;

import com.github.drepic26.couponcodes.api.entity.Player;
import com.github.drepic26.couponcodes.bukkit.entity.BukkitPlayer;
import com.github.drepic26.couponcodes.core.ServerModTransformer;

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
	protected Player getModPlayer(String uuid) {
		org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(UUID.fromString(uuid));

		if (bukkitPlayer == null)
			return null;

		return new BukkitPlayer(plugin, bukkitPlayer);
	}

}
