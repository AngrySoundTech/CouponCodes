package com.drevelopment.couponcodes.bukkit.entity;

import com.drevelopment.couponcodes.core.commands.ServerSender;
import org.bukkit.command.CommandSender;

public class BukkitServerSender extends ServerSender {

    CommandSender bukkitSender;

    public BukkitServerSender(CommandSender bukkitSender) {
        this.bukkitSender = bukkitSender;
    }

    @Override
    public void sendMessage(String message) {
        bukkitSender.sendMessage(message);
    }

}
