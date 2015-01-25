package com.github.drepic26.couponcodes.bukkit.permission;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.github.drepic26.couponcodes.api.entity.Player;

public class VaultPermissionHandler extends SuperPermsPermissionHandler {

	private net.milkbowl.vault.permission.Permission permission;

	public VaultPermissionHandler() {
		checkVault();
	}

	@Override
	public String getName() {
		return "Vault";
	}

	@Override
	public boolean isEnabled() {
		return checkVault();
	}

	@Override
	public boolean hasPermission(Player player, String node) {
		if (isEnabled()) {
			org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(UUID.fromString(player.getUUID()));
			return permission.has(bukkitPlayer, node);
		}
		return false;
	}

	@Override
	public Set<String> getGroups(Player player) {
		Set<String> groups = new HashSet<String>();
		if(isEnabled()) {
			org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(UUID.fromString(player.getUUID()));
			try {
				String[] vaultGroups = permission.getPlayerGroups(bukkitPlayer);

				if (vaultGroups == null || vaultGroups.length == 0) {
					return super.getGroups(player);
				}

				Collections.addAll(groups, vaultGroups);
			} catch (Exception e) {

			}
		}
		return groups;
	}

	private boolean checkVault(){
		if (permission != null) {
			return true;
		}

		RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (rsp == null) {
			return false;
		}
		permission = rsp.getProvider();
		return permission != null;
	}
}
