package com.github.drepic26.couponcodes.core.economy;

import com.github.drepic26.couponcodes.core.entity.Player;

public abstract class EconomyHandler {

	public abstract void giveMoney(String uuid, int amount);

	public abstract void setPlayerGroup(Player player, String group);

}
