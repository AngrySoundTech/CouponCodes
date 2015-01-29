package com.github.drepic26.couponcodes.sponge;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.util.event.Subscribe;

import com.github.drepic26.couponcodes.api.ModTransformer;
import com.github.drepic26.couponcodes.api.entity.Player;
import com.github.drepic26.couponcodes.core.Version;

@Plugin(id = Version.PLUGIN_ID, name = Version.PLUGIN_NAME, version = Version.PLUGIN_VERSION)
public class SpongePlugin {

	public static SpongePlugin instance;

	private Game game;
	private Logger logger;
	private ModTransformer transformer;

	@Subscribe
	public void onEnable(ServerStartingEvent event) {
		if (instance != null) {
			throw new RuntimeException("Only one instance of CouponCodes per server!");
		}

		instance = this;
		this.game = event.getGame();
		this.transformer = new SpongeServerModTransformer(this, game);
		this.logger = game.getPluginManager().getLogger((PluginContainer) this);
	}

	@Subscribe
	public void onDisable(ServerStoppingEvent event) {
		transformer = null;
		instance = null;
	}

	public Player wrapPlayer(org.spongepowered.api.entity.player.Player spongePlayer) {
		return transformer.getPlayer(spongePlayer.getUniqueId().toString());
	}

	public Game getGame() {
		return game;
	}

}
