package com.github.drepic26.couponcodes.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.github.drepic26.couponcodes.bukkit.BukkitPlugin;
import com.github.drepic26.couponcodes.core.commands.Command;
import com.github.drepic26.couponcodes.core.commands.CommandException;
import com.github.drepic26.couponcodes.core.entity.Player;

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

	private boolean handleCommandEvent(Command.Sender type, Player sender, String message) {
		message = trimCommand(message);
		int indexOfSpace = message.indexOf(' ');

		try {
			if (indexOfSpace != -1) {
				String command = message.substring(0, indexOfSpace);
				String args[] = message.substring(indexOfSpace + 1).split(" ");

				return plugin.getCommandHandler().handleCommand(command, args, sender);
			} else {
				return plugin.getCommandHandler().handleCommand(message, sender);
			}
		} catch (CommandException e) {
			return false;
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
