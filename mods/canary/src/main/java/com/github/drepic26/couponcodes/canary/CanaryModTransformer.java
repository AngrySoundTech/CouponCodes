package com.github.drepic26.couponcodes.canary;

import java.util.UUID;

import net.canarymod.Canary;
import net.canarymod.tasks.TaskOwner;

import com.github.drepic26.couponcodes.api.entity.Player;
import com.github.drepic26.couponcodes.canary.entity.CanaryPlayer;
import com.github.drepic26.couponcodes.canary.runnable.CanaryRunnable;
import com.github.drepic26.couponcodes.core.ServerModTransformer;

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
		net.canarymod.api.entity.living.humanoid.Player canaryPlayer = Canary.getServer().getPlayerFromUUID(UUID.fromString(uuid));
		if (canaryPlayer == null)
			return null;

		return new CanaryPlayer(canaryPlayer);
	}

	@Override
	public String getPlayerName(String UUID) {
		return Canary.getServer().getOfflinePlayer(UUID).getName();
	}

}
