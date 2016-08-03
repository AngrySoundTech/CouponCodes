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
package tech.feldman.couponcodes.core.commands.runnables

import tech.feldman.couponcodes.api.CouponCodes
import tech.feldman.couponcodes.api.command.CommandSender
import tech.feldman.couponcodes.core.util.LocaleHandler

class RemoveCommand(private val sender: CommandSender, private val args: Array<String>) : Runnable {

    override fun run() {
        if (args.size == 2) {
            if (args[1].equals("all", ignoreCase = true)) {
                var j = 0
                val cs = CouponCodes.getCouponHandler().coupons
                for (i in cs) {
                    CouponCodes.getCouponHandler().removeCouponFromDatabase(i)
                    j++
                }
                sender.sendMessage(LocaleHandler.getString("Command.Remove.AllRemoved", j))
                return
            }
            if (!CouponCodes.getCouponHandler().couponExists(args[1])) {
                sender.sendMessage(LocaleHandler.getString("Command.Shared.DoesNotExist"))
                return
            }
            CouponCodes.getCouponHandler().removeCouponFromDatabase(CouponCodes.getCouponHandler().createNewItemCoupon(args[1], 0, -1, null, null))
            sender.sendMessage(LocaleHandler.getString("Command.Remove.Removed", args[1]))
        } else {
            sender.sendMessage(LocaleHandler.getString("Command.Help.Remove"))
        }
    }
}
