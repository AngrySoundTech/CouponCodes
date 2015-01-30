package com.github.drepic26.couponcodes.canary.permission;

import java.util.Set;
import java.util.TreeSet;

import net.canarymod.Canary;

import com.github.drepic26.couponcodes.api.entity.Player;
import com.github.drepic26.couponcodes.api.permission.PermissionHandler;

public class CanaryPermissionHandler implements PermissionHandler {

	@Override
	public String getName() {
		return "Canary";
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean hasPermission(Player player, String node) {
		return Canary.getServer().getPlayerFromUUID(player.getUUID()).hasPermission(node);
	}

	@Override
	public Set<String> getGroups(Player player) {
		Set<String> groups = new TreeSet<String>();
		groups.add(Canary.getServer().getPlayerFromUUID(player.getUUID()).getGroup().getName());
		return groups;
	}

	@Override
	public void setPlayerGroup(Player player, String group) {
		Canary.getServer().getPlayerFromUUID(player.getUUID()).setGroup(Canary.usersAndGroups().getGroup(group));;;
	}

	@Override
	public boolean groupSupport() {
		return true;
	}

}
