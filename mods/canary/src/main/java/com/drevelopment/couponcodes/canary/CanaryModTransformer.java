package com.drevelopment.couponcodes.canary;


import java.util.UUID;

import net.canarymod.Canary;
import net.canarymod.tasks.TaskOwner;

import com.drevelopment.couponcodes.api.entity.Player;
import com.drevelopment.couponcodes.canary.entity.CanaryPlayer;
import com.drevelopment.couponcodes.canary.runnable.CanaryRunnable;
import com.drevelopment.couponcodes.core.ServerModTransformer;

public class CanaryModTransformer extends ServerModTransformer {

	private CanaryPlugin plugin;

	public CanaryModTransformer(CanaryPlugin canaryPlugin) {
		this.plugin = canaryPlugin;
	}

	@Override
	public void scheduleRunnable(Runnable runnable) {
		Canary.getServer().addSynchronousTask(new CanaryRunnable((TaskOwner)plugin, runnable, 0));
	}

	@Override
	public Player getModPlayer(String uuid) {
		net.canarymod.api.entity.living.humanoid.Player canaryPlayer = Canary.getServer().getPlayerFromUUID(uuid);
		if (canaryPlayer == null)
			return null;

		return new CanaryPlayer(canaryPlayer);
	}

	@Override
	public String getPlayerName(String uuid) {
		return Canary.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName();
	}

}
