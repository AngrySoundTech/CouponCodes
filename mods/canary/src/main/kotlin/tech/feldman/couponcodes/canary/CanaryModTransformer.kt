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
package tech.feldman.couponcodes.canary


import net.canarymod.Canary
import net.canarymod.api.inventory.ItemType
import org.apache.commons.lang3.StringUtils
import tech.feldman.couponcodes.api.command.CommandSender
import tech.feldman.couponcodes.api.entity.Player
import tech.feldman.couponcodes.canary.entity.CanaryPlayer
import tech.feldman.couponcodes.canary.runnable.CanaryRunnable
import tech.feldman.couponcodes.core.ServerModTransformer
import java.util.*

class CanaryModTransformer(private val plugin: CanaryPlugin) : ServerModTransformer() {

    override fun scheduleRunnable(runnable: Runnable) {
        Canary.getServer().addSynchronousTask(CanaryRunnable(plugin, runnable, 0))
    }

    override fun getModPlayer(uuid: String): Player? {
        val canaryPlayer = Canary.getServer().getPlayerFromUUID(uuid) ?: return null

        return CanaryPlayer(canaryPlayer)
    }

    override fun getPlayerName(uuid: String): String {
        return Canary.getServer().getOfflinePlayer(UUID.fromString(uuid)).name
    }

    override fun runCommand(sender: CommandSender, command: String) {
        if (sender is Player) {
            Canary.getServer().getPlayerFromUUID(sender.uuid).executeCommand(command.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        } else {
            Canary.getServer().consoleCommand(command)
        }
    }

    override fun isNumeric(string: String): Boolean {
        return StringUtils.isNumeric(string)
    }

    override fun isValidMaterial(name: String): Boolean {
        return ItemType.fromString(name) != null
    }

}
