/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (AngrySoundTech)
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.drevelopment.couponcodes.bukkit.permission;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.drevelopment.couponcodes.api.entity.Player;
import com.drevelopment.couponcodes.api.permission.PermissionHandler;

public class SuperPermsPermissionHandler implements PermissionHandler {

    private static final String GROUP_PREFIX = "group.";

    @Override
    public String getName() {
        return "Default";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean hasPermission(Player player, String node) {
        org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(UUID.fromString(player.getUUID()));
        return bukkitPlayer != null && bukkitPlayer.hasPermission(node);
    }

    @Override
    public Set<String> getGroups(Player player) {
        org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(UUID.fromString(player.getUUID()));
        Set<String> groups = new HashSet<>();

        for (PermissionAttachmentInfo pai : bukkitPlayer.getEffectivePermissions()) {
            if (pai.getPermission().startsWith(GROUP_PREFIX)) {
                groups.add(pai.getPermission().substring(GROUP_PREFIX.length()));
            }
        }
        return groups;
    }

    @Override
    public void setPlayerGroup(Player player, String group) {

    }

    @Override
    public boolean groupSupport() {
        return false;
    }
}
