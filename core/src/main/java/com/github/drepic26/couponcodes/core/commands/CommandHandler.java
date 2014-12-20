package com.github.drepic26.couponcodes.core.commands;

public abstract class CommandHandler {

	public boolean handleCommand(String command, String args, CommandSender sender) throws CommandException {
		sender.sendMessage("test");
		return true;
	}
	
	public boolean handleCommand(String command, CommandSender sender) throws CommandException {
		return true;
	}
}
