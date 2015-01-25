package com.github.drepic26.couponcodes.api.entity;

public interface Player {

	public boolean hasPermission(String node);

	public String getName();

	public void setLevel(int xp);

	public int getLevel();

	public void giveItem(int item, int amount);

}
