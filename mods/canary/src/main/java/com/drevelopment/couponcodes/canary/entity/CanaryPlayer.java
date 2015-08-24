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
package com.drevelopment.couponcodes.canary.entity;

import net.canarymod.Canary;

import com.drevelopment.couponcodes.core.entity.SimplePlayer;
import net.canarymod.api.inventory.ItemType;

public class CanaryPlayer extends SimplePlayer {

    private final net.canarymod.api.entity.living.humanoid.Player canaryPlayer;

    public CanaryPlayer(net.canarymod.api.entity.living.humanoid.Player canaryPlayer) {
        this.canaryPlayer = canaryPlayer;
    }

    @Override
    public boolean hasPermission(String node) {
        return canaryPlayer.hasPermission(node);
    }

    @Override
    public String getLocale() {
        return canaryPlayer.getLocale();
    }

    @Override
    public String getUUID() {
        return canaryPlayer.getUUIDString();
    }

    @Override
    public int getLevel() {
        return canaryPlayer.getLevel();
    }

    @Override
    public void setLevel(int level) {
        canaryPlayer.setLevel(level);
    }

    @Override
    public void giveItem(String item, int amount) throws IllegalArgumentException {
        if (ItemType.fromString(item) != null) {
            canaryPlayer.giveItem(Canary.factory().getItemFactory().newItem(ItemType.fromString(item), 0, amount));
        } else
            throw new IllegalArgumentException("Unknown item name");
    }

    @Override
    public void sendMessage(String message) {
        canaryPlayer.message(message);
    }

}
