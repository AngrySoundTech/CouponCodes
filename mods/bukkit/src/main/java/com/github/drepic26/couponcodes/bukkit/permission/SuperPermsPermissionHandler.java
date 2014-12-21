package com.github.drepic26.couponcodes.bukkit.permission;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.github.drepic26.couponcodes.core.entity.Player;
import com.github.drepic26.couponcodes.core.permission.PermissionHandler;

public class SuperPermsPermissionHandler extends PermissionHandler {

	private static final String GROUP_PREFIX = "group.";

	@Override
	public String getName() {
		return "Default";
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean hasPermission(Player player, String node) {
		org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(player.getName());
		return bukkitPlayer != null && bukkitPlayer.hasPermission(node);
	}

	@SuppressWarnings("deprecation")
	@Override
	public Set<String> getGroups(Player player) {
		org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(player.getName());
		Set<String> groups = new HashSet<String>();

		for (PermissionAttachmentInfo pai : bukkitPlayer.getEffectivePermissions()) {
			if (pai.getPermission().startsWith(GROUP_PREFIX)) {
				groups.add(pai.getPermission().substring(GROUP_PREFIX.length()));
			}
		}
		return groups;
	}
}
