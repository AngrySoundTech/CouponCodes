package com.github.drepic26.couponcodes.core.commands;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.command.CommandException;
import com.github.drepic26.couponcodes.api.command.CommandHandler;
import com.github.drepic26.couponcodes.api.command.CommandSender;
import com.github.drepic26.couponcodes.core.commands.runnables.AddCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.InfoCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.ListCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.RedeemCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.RemoveCommand;
import com.github.drepic26.couponcodes.core.util.LocaleHandler;

public class SimpleCommandHandler implements CommandHandler {
	// Handle a command with args
	public boolean handleCommand(String command, String[] args, CommandSender sender) throws CommandException {
		if (command.equalsIgnoreCase("coupon")) {
			// Help
			if (args[0].equalsIgnoreCase("help")) {
				help(sender);
				return true;
			// Commands
			} else
			if (args[0].equalsIgnoreCase("add")) {
				if (sender.hasPermission("cc.add")){
					CouponCodes.getModTransformer().scheduleRunnable(new AddCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(LocaleHandler.getString("Command.NoPermission"));
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("remove")) {
				if (sender.hasPermission("cc.remove")) {
					CouponCodes.getModTransformer().scheduleRunnable(new RemoveCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(LocaleHandler.getString("Command.NoPermission"));
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("redeem")) {
				if (sender.hasPermission("cc.redeem")) {
					CouponCodes.getModTransformer().scheduleRunnable(new RedeemCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(LocaleHandler.getString("Command.NoPermission"));
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("list")) {
				if (sender.hasPermission("cc.list")) {
					CouponCodes.getModTransformer().scheduleRunnable(new ListCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(LocaleHandler.getString("Command.NoPermission"));
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("info")) {
				if (sender.hasPermission("cc.info")) {
					CouponCodes.getModTransformer().scheduleRunnable(new InfoCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(LocaleHandler.getString("Command.NoPermission"));
					return true;
				}
			} else
			return false;
		} else return false;
	}

	// Handle a command with no args
	public boolean handleCommand(String command, CommandSender sender) throws CommandException {
		if (command.equalsIgnoreCase("coupon")) {
			help(sender);
			return true;
		} else return false;
	}

	//Help
	private void help(CommandSender sender) {
		sender.sendMessage(LocaleHandler.getString("Command.Help.Header"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.Instructions"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.Redeem"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.Remove"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.List"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.Info"));
	}

}
