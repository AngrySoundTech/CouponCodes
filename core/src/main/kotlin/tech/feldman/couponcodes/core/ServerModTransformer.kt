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
package tech.feldman.couponcodes.core

import tech.feldman.couponcodes.api.ModTransformer
import tech.feldman.couponcodes.api.entity.Player
import java.util.*

abstract class ServerModTransformer : ModTransformer {

    protected val players: MutableMap<String, Player> = HashMap()

    override fun getPlayer(UUID: String): Player? {
        if (players.containsKey(UUID))
            return players[UUID]
        val player = getModPlayer(UUID)
        if (player != null) {
            players.put(UUID, player)
            return player
        }
        return null
    }

    override fun removePlayer(player: Player) {
        players.remove(player.uuid)
    }
}
