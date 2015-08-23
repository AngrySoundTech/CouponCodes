/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (Drepic26)
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
