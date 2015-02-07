package com.drevelopment.couponcodes.api.command;

public interface CommandHandler {

	/**
	 * Handles a command with arguments.
	 * @param command The command to handle. This is what immediately follows the <code>/</code>
	 * @param args The arguments of the command
	 * @param sender The sender of the command
	 * @return True if the command was handled successfully
	 * @throws CommandException
	 */
	public boolean handleCommand(String command, String[] args, CommandSender sender) throws CommandException;

	/**
	 * Handles a command with no arguments.
	 * @param command The command to handle. This is what immediately follows the <code>/</code>
	 * @param sender The sender of the command
	 * @return True if the command was handled successfully
	 * @throws CommandException
	 */
	public boolean handleCommand(String command, CommandSender sender) throws CommandException;

}
