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
package tech.feldman.couponcodes.bukkit.entity

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import tech.feldman.couponcodes.api.exceptions.UnknownMaterialException
import tech.feldman.couponcodes.bukkit.BukkitPlugin
import tech.feldman.couponcodes.core.entity.SimplePlayer
import tech.feldman.couponcodes.core.util.Color
import java.lang.reflect.Method
import java.util.*

class BukkitPlayer(val plugin: BukkitPlugin, val bukkitPlayer: org.bukkit.entity.Player) : SimplePlayer() {

    override fun sendMessage(message: String) {
        for (line in message.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            bukkitPlayer.sendMessage(Color.replaceColors(line))
        }
    }

    override fun getLocale(): Locale {
        val ep = getMethod("getHandle", bukkitPlayer.javaClass)!!.invoke(bukkitPlayer, *null as Array<Any>)
        val f = ep.javaClass.getDeclaredField("locale")
        f.isAccessible = true
        return Locale.forLanguageTag(f.get(ep) as String)
    }

    override fun getName(): String = bukkitPlayer.name

    override fun getUUID(): String = bukkitPlayer.uniqueId.toString()

    override fun getLevel() = bukkitPlayer.level
    override fun setLevel(level: Int) {
        bukkitPlayer.level = level
    }

    @Throws(UnknownMaterialException::class)
    override fun giveItem(item: String, amount: Int) {
        if (Material.getMaterial(item) != null) {
            if (bukkitPlayer.inventory.firstEmpty() == -1) {
                bukkitPlayer.location.world.dropItem(bukkitPlayer.location, ItemStack(Material.getMaterial(item), amount))
            } else {
                bukkitPlayer.inventory.addItem(ItemStack(Material.getMaterial(item), amount))
            }
        } else
            throw UnknownMaterialException(item)
    }

    private fun getMethod(name: String, class1: Class<out Player>): Method? {
        for (m in class1.declaredMethods) {
            if (m.name == name)
                return m
        }
        return null
    }

}
