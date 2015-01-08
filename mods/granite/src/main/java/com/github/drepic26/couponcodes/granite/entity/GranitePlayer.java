package com.github.drepic26.couponcodes.granite.entity;

import com.github.drepic26.couponcodes.core.entity.Player;
import com.github.drepic26.couponcodes.core.util.Color;

public class GranitePlayer extends Player {

	private org.granitemc.granite.api.entity.player.Player granitePlayer;

	public GranitePlayer(org.granitemc.granite.api.entity.player.Player granitePlayer) {
		this.granitePlayer = granitePlayer;
	}

	@Override
	public void sendMessage(String message) {
		for (String line : message.split("\n")) {
            granitePlayer.sendMessage(Color.replaceColors(line));
        }
	}

	@Override
	public void setLevel(int level) {
		granitePlayer.addExperienceLevel(level - this.getLevel());
	}

	@Override
	public int getLevel() {
		return granitePlayer.getExperiencePoints(granitePlayer);
	}

	@Override
	public void giveItem(int item, int amount) {
		//TODO figure out how this granite thing works
	}

}
