package com.github.drepic26.couponcodes.api.command;

public interface CommandSender {

	public void sendMessage(String message);

	public boolean hasPermission(String node);

	public String getLocale();

}
