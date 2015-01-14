package com.github.drepic26.couponcodes.sponge;

import org.spongepowered.api.Game;
import org.spongepowered.api.plugin.PluginContainer;

import com.github.drepic26.couponcodes.core.ServerModTransformer;
import com.github.drepic26.couponcodes.core.entity.Player;
import com.github.drepic26.couponcodes.sponge.entity.SpongePlayer;

public class SpongeServerModTransformer extends ServerModTransformer {

	private SpongePlugin plugin;
	private Game game;

	public SpongeServerModTransformer(SpongePlugin plugin, Game game) {
		this.plugin = plugin;
		this.game = game;

		ServerModTransformer.setInstance(this);
	}

	@Override
	protected Player getModPlayer(String name) {
		org.spongepowered.api.entity.player.Player spongePlayer = game.getPlayer(name).orNull();

		if (spongePlayer == null)
			return null;

		return new SpongePlayer(plugin, spongePlayer);
	}

	@Override
	public void scheduleRunnable(Runnable runnable) {
		game.getScheduler().runTask((PluginContainer) plugin, runnable);
	}
}
