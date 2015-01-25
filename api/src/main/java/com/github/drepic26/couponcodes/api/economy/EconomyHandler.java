package com.github.drepic26.couponcodes.api.economy;

import com.github.drepic26.couponcodes.api.entity.Player;

public interface EconomyHandler {

	public abstract void giveMoney(String uuid, int amount);

	public abstract void setPlayerGroup(Player player, String group);

}
