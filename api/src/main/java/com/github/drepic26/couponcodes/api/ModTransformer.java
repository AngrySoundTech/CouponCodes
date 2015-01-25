package com.github.drepic26.couponcodes.api;

import com.github.drepic26.couponcodes.api.entity.Player;

public interface ModTransformer {

	public void scheduleRunnable(Runnable runnable);

	public Player getPlayer(String UUID);

}
