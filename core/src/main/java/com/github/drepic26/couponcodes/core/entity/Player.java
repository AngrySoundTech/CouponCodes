package com.github.drepic26.couponcodes.core.entity;

import com.github.drepic26.couponcodes.core.ServerModTransformer;
import com.github.drepic26.couponcodes.core.commands.CommandSender;

public abstract class Player implements CommandSender {

	/**
	 * Gets whether the player has a certain permission node
	 */
	public boolean hasPermission(String node) {
		return ServerModTransformer.getInstance().getPermissionHandler().hasPermission(this, node);
	}

	public String getName() {
		return null;
	}

}
