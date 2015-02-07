package com.drevelopment.couponcodes.canary.listeners;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.CommandException;
import com.drevelopment.couponcodes.api.entity.Player;

import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandListener;

public class CanaryListener implements CommandListener {

	@Command(aliases = { "coupon"},
			description = "Base coupon command",
			permissions = {/*"cc.add", "cc.remove", "cc.redeem", "cc.list", "cc.info"*/""},
			toolTip = "/coupon help")
	public void couponCommand(MessageReceiver caller, String[] parameters) {
		if (caller instanceof net.canarymod.api.entity.living.humanoid.Player) {
			Player player = CouponCodes.getModTransformer().getPlayer(((net.canarymod.api.entity.living.humanoid.Player)caller).getUUIDString());

			StringBuilder sb = new StringBuilder();
			for (String s : parameters) sb.append(s+" ");
			handleCommandEvent(com.drevelopment.couponcodes.api.command.Command.Sender.PLAYER, player, sb.toString());
		}
	}

	private boolean handleCommandEvent(com.drevelopment.couponcodes.api.command.Command.Sender type, Player sender, String message) {
		message = trimCommand(message);
		int indexOfSpace = message.indexOf(' ');

		try {
			if (indexOfSpace != -1) {
				String command = message.substring(0, indexOfSpace);
				String args[] = message.substring(indexOfSpace + 1).split(" ");

				return CouponCodes.getCommandHandler().handleCommand(command, args, sender);
			} else {
				return CouponCodes.getCommandHandler().handleCommand(message, sender);
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
