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
package tech.feldman.couponcodes.canary;


import net.canarymod.Canary;
import net.canarymod.api.inventory.ItemType;
import org.apache.commons.lang3.StringUtils;
import tech.feldman.couponcodes.api.command.CommandSender;
import tech.feldman.couponcodes.api.entity.Player;
import tech.feldman.couponcodes.canary.entity.CanaryPlayer;
import tech.feldman.couponcodes.canary.runnable.CanaryRunnable;
import tech.feldman.couponcodes.core.ServerModTransformer;

import java.util.UUID;

public class CanaryModTransformer extends ServerModTransformer {

    private CanaryPlugin plugin;

    public CanaryModTransformer(CanaryPlugin canaryPlugin) {
        this.plugin = canaryPlugin;
    }

    @Override
    public void scheduleRunnable(Runnable runnable) {
        Canary.getServer().addSynchronousTask(new CanaryRunnable(plugin, runnable, 0));
    }

    @Override
    public Player getModPlayer(String uuid) {
        net.canarymod.api.entity.living.humanoid.Player canaryPlayer = Canary.getServer().getPlayerFromUUID(uuid);
        if (canaryPlayer == null)
            return null;

        return new CanaryPlayer(canaryPlayer);
    }

    @Override
    public String getPlayerName(String uuid) {
        return Canary.getServer().getOfflinePlayer(UUID.fromString(uuid)).getName();
    }

    @Override
    public void runCommand(CommandSender sender, String command) {
        if (sender instanceof Player) {
            Canary.getServer().getPlayerFromUUID(((Player) sender).getUUID()).executeCommand(command.split(" "));
        } else {
            Canary.getServer().consoleCommand(command);
        }
    }

    @Override
    public boolean isNumeric(String string) {
        return StringUtils.isNumeric(string);
    }

    @Override
    public boolean isValidMaterial(String name) {
        return ItemType.fromString(name) != null;
    }

}
