package com.drevelopment.couponcodes.api.permission;

import java.util.Set;

import com.drevelopment.couponcodes.api.entity.Player;

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

	/**
	 * Sets the group of the specified player.
	 * This will not work if there is no permissions handler installed
	 * @param player The player to set the group of
	 * @param group The group to set the player to
	 */
	public abstract void setPlayerGroup(Player player, String group);

	/**
	 * Whether this permission handler has support for groups.
	 * @return True if the handler has support for groups.
	 */
	public abstract boolean groupSupport();

}
