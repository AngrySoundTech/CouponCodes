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
package com.drevelopment.couponcodes.core.commands;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.CommandException;
import com.drevelopment.couponcodes.api.command.CommandHandler;
import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.core.commands.runnables.AddCommand;
import com.drevelopment.couponcodes.core.commands.runnables.InfoCommand;
import com.drevelopment.couponcodes.core.commands.runnables.ListCommand;
import com.drevelopment.couponcodes.core.commands.runnables.RedeemCommand;
import com.drevelopment.couponcodes.core.commands.runnables.RemoveCommand;
import com.drevelopment.couponcodes.core.util.LocaleHandler;

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
