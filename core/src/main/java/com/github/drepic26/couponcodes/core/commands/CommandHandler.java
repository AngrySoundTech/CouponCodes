package com.github.drepic26.couponcodes.core.commands;

import com.github.drepic26.couponcodes.core.ServerModTransformer;
import com.github.drepic26.couponcodes.core.commands.runnables.AddCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.InfoCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.ListCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.RedeemCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.ReloadCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.RemoveCommand;
import com.github.drepic26.couponcodes.core.util.Color;

public abstract class CommandHandler {

	// Handle a command with args
	public boolean handleCommand(String command, String[] args, CommandSender sender) throws CommandException {
		if (command.equalsIgnoreCase("coupon")) {
			// Help
			if (args[0].equalsIgnoreCase("help")) {
				help(sender, args);
				return true;
			// Commands
			} else
			if (args[0].equalsIgnoreCase("add")) {
				if (args.length < 2) {
					helpAdd(sender);
					return true;
				}
				if (sender.hasPermission("cc.add")){
					ServerModTransformer.getInstance().scheduleRunnable(new AddCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(Color.RED+"You do not have permission to use this command.");
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("remove")) {
				if (sender.hasPermission("cc.remove")) {
					ServerModTransformer.getInstance().scheduleRunnable(new RemoveCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(Color.RED+"You do not have permission to use this command.");
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("redeem")) {
				if (sender.hasPermission("cc.redeem")) {
					ServerModTransformer.getInstance().scheduleRunnable(new RedeemCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(Color.RED+"You do not have permission to use this command.");
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("list")) {
				if (sender.hasPermission("cc.list")) {
					ServerModTransformer.getInstance().scheduleRunnable(new ListCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(Color.RED+"You do not have permission to use this command.");
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("info")) {
				if (sender.hasPermission("cc.info")) {
					ServerModTransformer.getInstance().scheduleRunnable(new InfoCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(Color.RED+"You do not have permission to use this command.");
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("cc.reload")) {
					ServerModTransformer.getInstance().scheduleRunnable(new ReloadCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(Color.RED+"You do not have permission to use this command.");
					return true;
				}
			}
			return false;
		} else return false;
	}
	
	// Handle a command with no args
	public boolean handleCommand(String command, CommandSender sender) throws CommandException {
		if (command.equalsIgnoreCase("coupon")) {
			help(sender, new String[]{""});
			//ServerModTransformer.getInstance().scheduleRunnable(new AddCommand(sender));
			return true;
		} else return false;
	}

	//Help
	private void help(CommandSender sender, String[] args) {
		if (args.length == 1) {
			sender.sendMessage(Color.GOLD+"|-<> = required-"+Color.DARK_RED+"CouponCodes Help"+Color.GOLD+"-[]-optional-|");
			sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"/coupon help add");
		} else if (args.length > 1) {
			helpAdd(sender);
		}
	}

	// Help with the add command
	private void helpAdd(CommandSender sender) {
		//TODO Help add
		sender.sendMessage("helpadd");
	}
}
