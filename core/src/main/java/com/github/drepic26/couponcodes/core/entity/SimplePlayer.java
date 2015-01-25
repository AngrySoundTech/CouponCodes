package com.github.drepic26.couponcodes.core.entity;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.command.CommandSender;
import com.github.drepic26.couponcodes.api.entity.Player;

public abstract class SimplePlayer implements CommandSender, Player {

	/**
	 * Gets whether the player has a certain permission node
	 */
	public boolean hasPermission(String node) {
		return CouponCodes.getPermissionHandler().hasPermission(this, node);
	}

	public String getName() {
		return null;
	}

	public abstract void setLevel(int level);

	public abstract int getLevel();

	public abstract void giveItem(int item, int amount);

}
