package com.github.drepic26.couponcodes.bukkit.economy;

import java.util.UUID;

import org.bukkit.Bukkit;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import com.github.drepic26.couponcodes.api.economy.EconomyHandler;
import com.github.drepic26.couponcodes.api.entity.Player;
import com.github.drepic26.couponcodes.bukkit.entity.BukkitPlayer;

public class VaultEconomyHandler implements EconomyHandler {

	private Economy econ;
	private Permission perm;

	public VaultEconomyHandler(Economy econ, Permission perm) {
		this.econ = econ;
		this.perm = perm;
	}

	@Override
	public void giveMoney(String uuid, int amount) {
		econ.depositPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)), amount);
	}

	@Override
	public void setPlayerGroup(Player player, String group) {
		if (perm.getName().equalsIgnoreCase("PermissionsBukkit")) {
			perm.playerAddGroup((String) null, Bukkit.getOfflinePlayer(UUID.fromString(player.getUUID())), group);
			for (String i : perm.getPlayerGroups((String) null, Bukkit.getOfflinePlayer(UUID.fromString(player.getUUID())))) {
				if (i.equalsIgnoreCase(group)) continue;
				perm.playerRemoveGroup((String) null, Bukkit.getOfflinePlayer(UUID.fromString(player.getUUID())), i);
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
