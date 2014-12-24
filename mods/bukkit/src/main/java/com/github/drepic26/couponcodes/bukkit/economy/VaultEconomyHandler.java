package com.github.drepic26.couponcodes.bukkit.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import com.github.drepic26.couponcodes.bukkit.entity.BukkitPlayer;
import com.github.drepic26.couponcodes.core.economy.EconomyHandler;
import com.github.drepic26.couponcodes.core.entity.Player;

public class VaultEconomyHandler extends EconomyHandler {

	private Economy econ;
	private Permission perm;

	public VaultEconomyHandler(Economy econ, Permission perm) {
		this.econ = econ;
		this.perm = perm;
	}

	@Override
	public void giveMoney(String player, int amount) {
		econ.depositPlayer(player, amount);
	}

	@Override
	public void setPlayerGroup(Player player, String group) {
		if (perm.getName().equalsIgnoreCase("PermissionsBukkit")) {
			perm.playerAddGroup((String) null, player.getName(), group);
			for (String i : perm.getPlayerGroups((String) null, player.getName())) {
				if (i.equalsIgnoreCase(group)) continue;
				perm.playerRemoveGroup((String) null, player.getName(), i);
			}
		} else {
			perm.playerAddGroup(((BukkitPlayer) player).getBukkitPlayer(), group);
			for (String i : perm.getPlayerGroups(((BukkitPlayer) player).getBukkitPlayer())) {
				if (i.equalsIgnoreCase(group)) continue;
				perm.playerRemoveGroup(((BukkitPlayer)player).getBukkitPlayer(), i);
			}
		}
	}

}
