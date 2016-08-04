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
package tech.feldman.couponcodes.canary.entity

import net.canarymod.Canary
import net.canarymod.api.inventory.ItemType
import tech.feldman.couponcodes.api.exceptions.UnknownMaterialException
import tech.feldman.couponcodes.core.entity.SimplePlayer

class CanaryPlayer(private val canaryPlayer: net.canarymod.api.entity.living.humanoid.Player) : SimplePlayer() {

    override fun hasPermission(node: String): Boolean {
        return canaryPlayer.hasPermission(node)
    }

    override fun getLocale(): String {
        return canaryPlayer.locale
    }

    override fun getUUID(): String? {
        return canaryPlayer.uuidString
    }

    override fun getLevel(): Int {
        return canaryPlayer.level
    }

    override fun setLevel(level: Int) {
        canaryPlayer.level = level
    }

    @Throws(UnknownMaterialException::class)
    override fun giveItem(item: String, amount: Int) {
        if (ItemType.fromString(item) != null) {
            canaryPlayer.giveItem(Canary.factory().itemFactory.newItem(ItemType.fromString(item), 0, amount))
        } else
            throw UnknownMaterialException(item)
    }

    override fun sendMessage(message: String) {
        canaryPlayer.message(message)
    }

}
