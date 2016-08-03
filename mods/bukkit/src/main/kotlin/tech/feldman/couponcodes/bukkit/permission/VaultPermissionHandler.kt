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
import tech.feldman.couponcodes.bukkit.entity.BukkitPlayer
import java.util.*

class VaultPermissionHandler : SuperPermsPermissionHandler() {

    private var permission: net.milkbowl.vault.permission.Permission? = null

    init {
        checkVault()
    }

    override fun getName(): String {
        return "Vault"
    }

    override fun isEnabled(): Boolean {
        return checkVault()
    }

    override fun hasPermission(player: Player, node: String): Boolean {
        if (isEnabled) {
            val bukkitPlayer = Bukkit.getPlayer(UUID.fromString(player.uuid))
            return permission!!.has(bukkitPlayer, node)
        }
        return false
    }

    override fun getGroups(player: Player): Set<String> {
        val groups = HashSet<String>()
        if (isEnabled) {
            val bukkitPlayer = Bukkit.getPlayer(UUID.fromString(player.uuid))
            try {
                val vaultGroups = permission!!.getPlayerGroups(bukkitPlayer)

                if (vaultGroups == null || vaultGroups.size == 0) {
                    return super.getGroups(player)
                }

                Collections.addAll(groups, *vaultGroups)
            } catch (ignored: Exception) {

            }

        }
        return groups
    }

    override fun setPlayerGroup(player: Player, group: String) {
        if (permission!!.name.equals("PermissionsBukkit", ignoreCase = true)) {
            permission!!.playerAddGroup(null, Bukkit.getOfflinePlayer(UUID.fromString(player.uuid)), group)
            for (i in permission!!.getPlayerGroups(null, Bukkit.getOfflinePlayer(UUID.fromString(player.uuid)))) {
                if (i.equals(group, ignoreCase = true))
                    continue
                permission!!.playerRemoveGroup(null, Bukkit.getOfflinePlayer(UUID.fromString(player.uuid)), i)
            }
        } else {
            permission!!.playerAddGroup((player as BukkitPlayer).bukkitPlayer, group)
            for (i in permission!!.getPlayerGroups(player.bukkitPlayer)) {
                if (i.equals(group, ignoreCase = true))
                    continue
                permission!!.playerRemoveGroup(player.bukkitPlayer, i)
            }
        }
    }

    override fun groupSupport(): Boolean {
        return true
    }

    private fun checkVault(): Boolean {
        if (permission != null) {
            return true
        }

        val rsp = Bukkit.getServer().servicesManager.getRegistration(net.milkbowl.vault.permission.Permission::class.java) ?: return false
        permission = rsp.provider
        return permission != null
    }
}
