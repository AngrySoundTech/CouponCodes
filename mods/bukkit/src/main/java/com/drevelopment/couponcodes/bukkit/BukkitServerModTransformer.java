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
package com.drevelopment.couponcodes.bukkit;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;

import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.api.entity.Player;
import com.drevelopment.couponcodes.bukkit.entity.BukkitPlayer;
import com.drevelopment.couponcodes.core.ServerModTransformer;
import org.bukkit.Material;

public class BukkitServerModTransformer extends ServerModTransformer {

    private BukkitPlugin plugin;

    public BukkitServerModTransformer(BukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void scheduleRunnable(Runnable runnable) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, runnable);
    }

    @Override
    public Player getModPlayer(String uuid) {
        org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(UUID.fromString(uuid));

        if (bukkitPlayer == null)
            return null;

        return new BukkitPlayer(plugin, bukkitPlayer);
    }

    @Override
    public String getPlayerName(String uuid) {
        return Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
    }

    @Override
    public void runCommand(CommandSender sender, String command) {
        if (sender instanceof Player) {
            Bukkit.getServer().dispatchCommand(Bukkit.getPlayer(UUID.fromString(((Player) sender).getUUID())), command);
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    @Override
    public boolean isNumeric(String string) {
        return StringUtils.isNumeric(string);
    }

    @Override
    public boolean isValidMaterial(String name) {
        return Material.getMaterial(name) != null;
    }

}
