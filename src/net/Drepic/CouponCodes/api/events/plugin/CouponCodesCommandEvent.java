package net.Drepic.CouponCodes.api.events.plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

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
	
	public boolean isCancelled() {
		return cancel;
	}
	
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
	
	public CommandSender getSender() {
		return sender;
	}
	
	public void setSender(CommandSender sender) {
		this.sender = sender;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public String getCommandLabel() {
		return commandLabel;
	}
	
	public void setCommandLabel(String commandLabel) {
		this.commandLabel = commandLabel;
	}
	
	public String[] getArgs() {
		return args;
	}
	
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
