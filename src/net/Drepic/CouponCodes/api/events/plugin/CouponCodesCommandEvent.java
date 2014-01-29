package net.Drepic.CouponCodes.api.events.plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a CouponCodes command is fired
 */
public class CouponCodesCommandEvent extends Event implements Cancellable {

	private static final HandlerList h = new HandlerList();
	
	private CommandSender sender;
	private Command command;
	private String commandLabel;
	private String[] args;
	
	private Boolean cancel = false;
	
	public CouponCodesCommandEvent(CommandSender sender, Command command, String commandLabel, String[] args) {
		this.sender = sender;
		this.command = command;
		this.commandLabel = commandLabel;
		this.args = args;
	}
	
	/**
	 * Checks if the command was cancelled
	 * @return true if the command was cancelled
	 */
	public boolean isCancelled() {
		return cancel;
	}
	
	/**
	 * Sets the command as cancelled
	 */
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
	
	/**
	 * Gets the sender of the command
	 * @return The sender of the command
	 */
	public CommandSender getSender() {
		return sender;
	}
	
	/**
	 * Sets the sender of the command
	 * @param sender The player to set as the sender of the command
	 */
	public void setSender(CommandSender sender) {
		this.sender = sender;
	}
	
	/**
	 * Gets the command that was sent
	 * @return The command that was sent
	 */
	public Command getCommand() {
		return command;
	}
	
	/**
	 * Sets the command
	 * @param command Command to set
	 */
	public void setCommand(Command command) {
		this.command = command;
	}
	
	/**
	 * Gets the command label
	 * @return The command label
	 */
	public String getCommandLabel() {
		return commandLabel;
	}
	
	/**
	 * Sets the command label
	 * @param commandLabel the command label to set
	 */
	public void setCommandLabel(String commandLabel) {
		this.commandLabel = commandLabel;
	}
	
	/**
	 * Gets the args of the command
	 * @return The args of the command
	 */
	public String[] getArgs() {
		return args;
	}
	
	/**
	 * Sets the args of the command
	 * @param args The args to set
	 */
	public void setArgs(String[] args) {
		this.args = args;
	}
	
	public HandlerList getHandlers() {
		return h;
	}
	
	public static HandlerList getHandlerList() {
		return h;
	}
	
	public void call() {
		Bukkit.getServer().getPluginManager().callEvent(this);
	}
}
