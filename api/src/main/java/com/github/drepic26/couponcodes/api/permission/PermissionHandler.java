package com.github.drepic26.couponcodes.api.permission;

import java.util.Set;

import com.github.drepic26.couponcodes.api.entity.Player;

public interface PermissionHandler {

	public String getName();

	public boolean isEnabled();

	public boolean hasPermission(Player player, String node);

	public Set<String> getGroups(Player player);

}
