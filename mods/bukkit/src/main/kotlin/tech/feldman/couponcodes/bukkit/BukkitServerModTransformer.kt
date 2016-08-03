/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (AngrySoundTech)
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package tech.feldman.couponcodes.bukkit

import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.Material
import tech.feldman.couponcodes.api.command.CommandSender
import tech.feldman.couponcodes.api.entity.Player
import tech.feldman.couponcodes.bukkit.entity.BukkitPlayer
import tech.feldman.couponcodes.core.ServerModTransformer
import java.util.*

class BukkitServerModTransformer(private val plugin: BukkitPlugin) : ServerModTransformer() {

    override fun scheduleRunnable(runnable: Runnable) {
        plugin.server.scheduler.scheduleSyncDelayedTask(plugin, runnable)
    }

    override fun getModPlayer(uuid: String): Player? {
        val bukkitPlayer = Bukkit.getPlayer(UUID.fromString(uuid)) ?: return null

        return BukkitPlayer(plugin, bukkitPlayer)
    }

    override fun getPlayerName(uuid: String): String {
        return Bukkit.getOfflinePlayer(UUID.fromString(uuid)).name
    }

    override fun runCommand(sender: CommandSender, command: String) {
        if (sender is Player) {
            Bukkit.getServer().dispatchCommand(Bukkit.getPlayer(UUID.fromString(sender.uuid)), command)
        } else {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command)
        }
    }

    override fun isNumeric(string: String): Boolean {
        return StringUtils.isNumeric(string)
    }

    override fun isValidMaterial(name: String): Boolean {
        return Material.getMaterial(name) != null
    }

}
