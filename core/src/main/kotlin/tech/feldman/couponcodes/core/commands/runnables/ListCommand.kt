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

class ListCommand(private val sender: CommandSender, args: Array<String>) : Runnable {

    override fun run() {
        val sb = StringBuilder()
        val c = CouponCodes.getCouponHandler().coupons
        if (c!!.isEmpty() || c.size <= 0 || c == null) {
            sender.sendMessage(LocaleHandler.getString("Command.List.NoFound"))
        } else {
            sb.append(LocaleHandler.getString("Command.List.List"))
            for (i in c.indices) {
                sb.append(c[i])
                if (Integer.valueOf(i + 1) != c.size) {
                    sb.append(", ")
                }
            }
            sender.sendMessage(sb.toString())
        }
    }

}
