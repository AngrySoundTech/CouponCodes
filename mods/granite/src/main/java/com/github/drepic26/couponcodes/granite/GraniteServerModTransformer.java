package com.github.drepic26.couponcodes.granite;

import org.granitemc.granite.api.Granite;
import org.granitemc.granite.api.plugin.Plugin;

import com.github.drepic26.couponcodes.core.ServerModTransformer;
import com.github.drepic26.couponcodes.core.entity.Player;
import com.github.drepic26.couponcodes.granite.entity.GranitePlayer;

public class GraniteServerModTransformer extends ServerModTransformer {

	public GraniteServerModTransformer() {
		ServerModTransformer.setInstance(this);
	}

	@Override
	protected Player getModPlayer(String name) {
		org.granitemc.granite.api.entity.player.Player granitePlayer = Granite.getServer().getPlayer(name);
		if (granitePlayer == null)
			return null;

		return new GranitePlayer(granitePlayer);
	}

	@Override
	public void scheduleRunnable(Runnable runnable) {
		//TODO figure out how granite works
	}

}
