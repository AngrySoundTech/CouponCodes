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
