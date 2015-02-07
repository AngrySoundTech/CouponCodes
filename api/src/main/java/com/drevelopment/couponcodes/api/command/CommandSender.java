package com.drevelopment.couponcodes.api.command;

public interface CommandSender {

	/**
	 * Sends a message to the player
	 * @param message The message to send
	 */
	public void sendMessage(String message);

	/**
	 * Checks whether the player has a certain permission node.
	 * @param node The node to check
	 * @return True if the player has the specified permission node.
	 * @see com.drevelopment.couponcodes.api.permission.PermissionHandler PermissionHandler
	 */
	public boolean hasPermission(String node);

	/**
	 * Gets the locale of the player
	 * <p>Locales are returned in the form <code>en_US</code>, <code>de_DE</code>, etc. <br>
	 * This will be the current language the player's client is set to. If the sender is the console,
	 * the returned locale will be the one specified in the config.
	 * @return The current language of the player's client
	 */
	public String getLocale();

}
