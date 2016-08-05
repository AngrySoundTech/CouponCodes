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
package tech.feldman.couponcodes.canary.listeners

import net.canarymod.chat.MessageReceiver
import net.canarymod.commandsys.Command
import net.canarymod.commandsys.CommandListener
import net.canarymod.hook.HookHandler
import net.canarymod.hook.player.DisconnectionHook
import tech.feldman.couponcodes.api.CouponCodes
import tech.feldman.couponcodes.canary.entity.CanaryServerSender

class CanaryListener : CommandListener {

    @Command(aliases = arrayOf("coupon"),
            description = "Base coupon command",
            permissions = arrayOf("cc.add", "cc.remove", "cc.redeem", "cc.list", "cc.info"),
            toolTip = "/coupon help")
    fun couponCommand(caller: MessageReceiver, parameters: Array<String>) {
        val sb: StringBuilder = StringBuilder()
        for (s in parameters) {
            sb.append(s).append(" ")
        }
        if (caller is net.canarymod.api.entity.living.humanoid.Player) {
            val player = CouponCodes.getModTransformer().getPlayer(caller.uuidString)
            CouponCodes.getCommandHandler().handleCommandEvent(player, sb.toString())
        } else {
            CouponCodes.getCommandHandler().handleCommandEvent(CanaryServerSender(caller), sb.toString())
        }
    }

    @HookHandler
    fun onPlayerQuit(event: DisconnectionHook) {
        CouponCodes.getModTransformer().removePlayer(CouponCodes.getModTransformer().getPlayer(event.player.uuidString))
    }
}
