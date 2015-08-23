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
package com.drevelopment.couponcodes.api.entity;

import com.drevelopment.couponcodes.api.command.CommandSender;

public interface Player extends CommandSender {

    /**
     * Gets the name of the player.
     * @return The name of the player
     */
    public String getName();

    /**
     * Gets the unique identifier of a player.
     * @return the unique identifier of the player
     */
    public String getUUID();

    /**
     * Gets the current level of the player.
     * @return The current level of the player
     */
    public int getLevel();

    /**
     * Sets the level of the player.
     * @param xp The level of the player to set to
     */
    public void setLevel(int xp);

    /**
     * Gives the player the specified item and amount.
     * @param item The id of the item to give the player
     * @param amount The amount of the item to give the player
     */
    public void giveItem(int item, int amount);

    public String getLocale();

}
