package com.github.drepic26.couponcodes.core.economy;

import com.github.drepic26.couponcodes.core.entity.SimplePlayer;

public abstract class EconomyHandler {

	public abstract void giveMoney(String player, int amount);

	public abstract void setPlayerGroup(SimplePlayer player, String group);

}
