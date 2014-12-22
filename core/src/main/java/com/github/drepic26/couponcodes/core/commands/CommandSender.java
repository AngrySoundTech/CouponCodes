package com.github.drepic26.couponcodes.core.commands;

public interface CommandSender {
	
	public void sendMessage(String message);

	public boolean hasPermission(String node);

}
