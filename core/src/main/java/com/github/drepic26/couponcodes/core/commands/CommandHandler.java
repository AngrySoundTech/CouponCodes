package com.github.drepic26.couponcodes.core.commands;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.command.CommandSender;
import com.github.drepic26.couponcodes.core.commands.runnables.AddCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.InfoCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.ListCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.RedeemCommand;
import com.github.drepic26.couponcodes.core.commands.runnables.RemoveCommand;
import com.github.drepic26.couponcodes.core.util.Color;

public abstract class CommandHandler {

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
					sender.sendMessage(Color.RED+"You do not have permission to use this command.");
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("remove")) {
				if (sender.hasPermission("cc.remove")) {
					CouponCodes.getModTransformer().scheduleRunnable(new RemoveCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(Color.RED+"You do not have permission to use this command.");
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("redeem")) {
				if (sender.hasPermission("cc.redeem")) {
					CouponCodes.getModTransformer().scheduleRunnable(new RedeemCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(Color.RED+"You do not have permission to use this command.");
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("list")) {
				if (sender.hasPermission("cc.list")) {
					CouponCodes.getModTransformer().scheduleRunnable(new ListCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(Color.RED+"You do not have permission to use this command.");
					return true;
				}
			} else
			if (args[0].equalsIgnoreCase("info")) {
				if (sender.hasPermission("cc.info")) {
					CouponCodes.getModTransformer().scheduleRunnable(new InfoCommand(sender, args));
					return true;
				} else {
					sender.sendMessage(Color.RED+"You do not have permission to use this command.");
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
		sender.sendMessage(Color.GOLD+"|-<> = required-"+Color.DARK_RED+"CouponCodes Help"+Color.GOLD+"-[]-optional-|");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"add item <name> <item1:amount,item2:amount,..> [usetimes] [time]");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"add econ <name> <money> [usetimes] [time]");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"add rank <name> <group> [usetimes] [time]");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"add xp <name> <xp> [usetimes] [time]");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"redeem <name>");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"remove <name>");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"list");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"info <name>");
	}
}
