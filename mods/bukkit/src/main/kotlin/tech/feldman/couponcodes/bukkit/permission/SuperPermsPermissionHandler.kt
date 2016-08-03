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
package tech.feldman.couponcodes.bukkit.permission

import org.bukkit.Bukkit
import tech.feldman.couponcodes.api.entity.Player
import tech.feldman.couponcodes.api.permission.PermissionHandler
import java.util.*

open class SuperPermsPermissionHandler : PermissionHandler {

    override fun getName(): String {
        return "Default"
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun hasPermission(player: Player, node: String): Boolean {
        val bukkitPlayer = Bukkit.getPlayer(UUID.fromString(player.uuid))
        return bukkitPlayer != null && bukkitPlayer.hasPermission(node)
    }

    override fun getGroups(player: Player): Set<String> {
        val bukkitPlayer = Bukkit.getPlayer(UUID.fromString(player.uuid))
        val groups = HashSet<String>()

        for (pai in bukkitPlayer.effectivePermissions) {
            if (pai.permission.startsWith(GROUP_PREFIX)) {
                groups.add(pai.permission.substring(GROUP_PREFIX.length))
            }
        }
        return groups
    }

    override fun setPlayerGroup(player: Player, group: String) {

    }

    override fun groupSupport(): Boolean {
        return false
    }

    companion object {

        private val GROUP_PREFIX = "group."
    }
}
