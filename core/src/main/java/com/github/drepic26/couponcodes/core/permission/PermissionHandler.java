package com.github.drepic26.couponcodes.core.permission;

import java.util.Set;

import com.github.drepic26.couponcodes.core.entity.Player;

public class PermissionHandler {

	public String getName() {
		return "Nope";
	}

	public boolean isEnabled() {
		return false;
	}

	public boolean hasPermission(Player player, String node) {
		throw new UnsupportedOperationException("No permission handler");
	}

	public Set<String> getGroups(Player player) {
		throw new UnsupportedOperationException("No permission handler");
	}
}
