package com.github.drepic26.couponcodes.api.entity;

import com.github.drepic26.couponcodes.api.command.CommandSender;

public interface Player extends CommandSender {

	public String getName();

	public String getUUID();

	public void setLevel(int xp);

	public int getLevel();

	public void giveItem(int item, int amount);

	public String getLocale();

}
