/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (Drepic26)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.drevelopment.couponcodes.canary;


import java.util.UUID;

import net.canarymod.Canary;
import net.canarymod.tasks.TaskOwner;

import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.api.entity.Player;
import com.drevelopment.couponcodes.canary.entity.CanaryPlayer;
import com.drevelopment.couponcodes.canary.runnable.CanaryRunnable;
import com.drevelopment.couponcodes.core.ServerModTransformer;

public class CanaryModTransformer extends ServerModTransformer {

	private CanaryPlugin plugin;

	public CanaryModTransformer(CanaryPlugin canaryPlugin) {
		this.plugin = canaryPlugin;
	}

	@Override
	public void scheduleRunnable(Runnable runnable) {
		Canary.getServer().addSynchronousTask(new CanaryRunnable((TaskOwner)plugin, runnable, 0));
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

}
