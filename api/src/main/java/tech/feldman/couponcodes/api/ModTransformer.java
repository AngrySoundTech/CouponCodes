/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (AngrySoundTech)
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
package tech.feldman.couponcodes.api;

import tech.feldman.couponcodes.api.command.CommandSender;
import tech.feldman.couponcodes.api.entity.Player;

public interface ModTransformer {

    /**
     * Schedules a runnable to run immediately.
     * <p> Schedules a delayed task on the server
     * @param runnable Runnable to schedule
     */
    void scheduleRunnable(Runnable runnable);

    /**
     * Gets the Player.
     * <p> If the player has not been gotten before, gets and wraps the player from the server.
     * @param UUID The unique identifier of the player to get
     * @return The player
     */
    Player getPlayer(String UUID);

    /**
     * <b>{@link #getPlayer(String)} should be used instead</b><p>
     * Gets the player from the server software, wrapped by {@link Player}
     * @param UUID The UUID of the player to get
     * @return Player A new instance of the player
     */
    Player getModPlayer(String UUID);

    /**
     * Removes a player wrapper from the plugin.
     * <p>A player should be removed when they log out, so that they can be wrapped again when
     * they next log in</p>
     * @param player The player to remove
     */
    void removePlayer(Player player);

    /**
     * This should be used in case the player you want to get the name of may be offline
     * @param UUID The UUID of the player's name to get
     * @return The name of the player
     */
    String getPlayerName(String UUID);

    /**
     * Runs a command as either console, or the specified player.
     * <p>If sender is null or not an instance of {@link Player}, the command will be run as console.</p>
     * @param sender The Sender to run the command as. If null, will default to console.
     * @param command The command to run, Without the <code>/</code> in front.
     */
    void runCommand(CommandSender sender, String command);

    boolean isNumeric(String string);

    boolean isValidMaterial(String material);

}
