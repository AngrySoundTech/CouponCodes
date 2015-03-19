/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (Drepic26)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.drevelopment.couponcodes.bukkit.permission;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.drevelopment.couponcodes.api.entity.Player;
import com.drevelopment.couponcodes.bukkit.entity.BukkitPlayer;

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
	public void setPlayerGroup(Player player, String group) {
		if (permission.getName().equalsIgnoreCase("PermissionsBukkit")) {
			permission.playerAddGroup((String) null, Bukkit.getOfflinePlayer(UUID.fromString(player.getUUID())), group);
			for (String i : permission.getPlayerGroups((String) null, Bukkit.getOfflinePlayer(UUID.fromString(player.getUUID())))) {
				if (i.equalsIgnoreCase(group)) continue;
				permission.playerRemoveGroup((String) null, Bukkit.getOfflinePlayer(UUID.fromString(player.getUUID())), i);
			}
		} else {
			permission.playerAddGroup(((BukkitPlayer) player).getBukkitPlayer(), group);
			for (String i : permission.getPlayerGroups(((BukkitPlayer) player).getBukkitPlayer())) {
				if (i.equalsIgnoreCase(group)) continue;
				permission.playerRemoveGroup(((BukkitPlayer)player).getBukkitPlayer(), i);
			}
		}
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
	
	@Override
	public boolean groupSupport() {
		return true;
	}
}
