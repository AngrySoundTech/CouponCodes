package com.github.drepic26.couponcodes.api.permission;

import java.util.Set;

import com.github.drepic26.couponcodes.api.entity.Player;

public interface PermissionHandler {

	/**
	 * Gets the name of this permission handler.
	 * @return The name of this permission handler
	 */
	public String getName();

	/**
	 * Gets whether this permission handler is enabled.
	 * @return True if this permission handler is enabled
	 */
	public boolean isEnabled();

	/**
	 * Gets whether a player has a certain permission node.
	 * @param player The Player to check
	 * @param node The node to check
	 * @return True if the player has permission
	 */
	public boolean hasPermission(Player player, String node);

	/**
	 * Gets the groups the player is part of.
	 * @param player The player to check
	 * @return The groups the player is part of
	 */
	public Set<String> getGroups(Player player);

}
