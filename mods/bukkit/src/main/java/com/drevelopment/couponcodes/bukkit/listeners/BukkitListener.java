/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (Drepic26)
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
package com.drevelopment.couponcodes.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.Command;
import com.drevelopment.couponcodes.api.entity.Player;
import com.drevelopment.couponcodes.bukkit.BukkitPlugin;

public class BukkitListener implements Listener {

    private BukkitPlugin plugin;

    public BukkitListener(BukkitPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = plugin.wrapPlayer(event.getPlayer());
        String command = event.getMessage();

        if (handleCommandEvent(Command.Sender.PLAYER, player, command)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        CouponCodes.getModTransformer().removePlayer(CouponCodes.getModTransformer().getPlayer(event.getPlayer().getUniqueId().toString()));
    }

    private boolean handleCommandEvent(Command.Sender type, Player sender, String message) {
        message = trimCommand(message);
        int indexOfSpace = message.indexOf(' ');

        if (indexOfSpace != -1) {
            String command = message.substring(0, indexOfSpace);
            String args[] = message.substring(indexOfSpace + 1).split(" ");

            return CouponCodes.getCommandHandler().handleCommand(command, args, sender);
        } else {
            return CouponCodes.getCommandHandler().handleCommand(message, sender);
        }
    }

    private String trimCommand(String command) {
        if (command.startsWith("/")) {
            if (command.length() == 1) {
                return "";
            } else {
                command = command.substring(1);
            }
        }
        return command.trim();
    }

}
