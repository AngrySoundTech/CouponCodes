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
import tech.feldman.couponcodes.api.exceptions.UnknownMaterialException
import tech.feldman.couponcodes.core.util.LocaleHandler
import tech.feldman.couponcodes.core.util.RandomName
import java.util.*

class AddCommand(private val sender: CommandSender, private val args: Array<String>) : Runnable {

    override fun run() {
        if (args.size < 2) {
            helpAdd(sender)
            return
        }

        if (args[1].equals("item", ignoreCase = true)) {
            if (args.size >= 4) {
                var name = args[2]

                if (name.equals("random", ignoreCase = true))
                    name = RandomName.generateName()
                if (args.size >= 5) {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"))
                    return
                }

                val itemHash: HashMap<String, Int>
                try {
                    itemHash = CouponCodes.getCouponHandler().itemStringToHash(args[3], sender)
                } catch (e: UnknownMaterialException) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.InvalidName", e.itemName))
                    return
                }

                val ic = CouponCodes.getCouponHandler().createNewItemCoupon(name, 1, -1, itemHash, HashMap<String, Boolean>())

                if (ic.addToDatabase()) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name))
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"))
                }
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"))
            }
        } else if (args[1].equals("econ", ignoreCase = true)) {
            if (args.size >= 4) {
                var name = args[2]
                val money = Integer.parseInt(args[3])

                if (name.equals("random", ignoreCase = true))
                    name = RandomName.generateName()
                if (args.size >= 5) {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"))
                    return
                }

                val ec = CouponCodes.getCouponHandler().createNewEconomyCoupon(name, 1, -1, HashMap<String, Boolean>(), money)

                if (ec.addToDatabase()) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name))
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"))
                }
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"))
            }

        } else if (args[1].equals("rank", ignoreCase = true)) {
            if (args.size >= 4) {
                var name = args[2]
                val group = args[3]

                if (name.equals("random", ignoreCase = true))
                    name = RandomName.generateName()
                if (args.size >= 5) {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"))
                    return
                }

                val rc = CouponCodes.getCouponHandler().createNewRankCoupon(name, group, 1, -1, HashMap<String, Boolean>())

                if (rc.addToDatabase()) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name))
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"))
                }
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"))
            }
        } else if (args[1].equals("xp", ignoreCase = true)) {
            if (args.size >= 4) {
                var name = args[2]
                val xp = Integer.parseInt(args[3])

                if (name.equals("random", ignoreCase = true))
                    name = RandomName.generateName()
                if (args.size >= 5) {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"))
                    return
                }

                val xc = CouponCodes.getCouponHandler().createNewXpCoupon(name, xp, 1, -1, HashMap<String, Boolean>())

                if (xc.addToDatabase()) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name))
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"))
                }
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"))
            }
        } else if (args[1].equals("cmd", ignoreCase = true)) {
            if (args.size >= 4) {
                var name = args[2]
                val sb = StringBuilder()
                for (i in 3..args.size - 1) {
                    sb.append(args[i]).append(" ")
                }
                val cmd = sb.toString()

                if (name.equals("random", ignoreCase = true))
                    name = RandomName.generateName()

                val cc = CouponCodes.getCouponHandler().createNewCommandCoupon(name, cmd, 1, -1, HashMap<String, Boolean>())

                if (cc.addToDatabase()) {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name))
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"))
                }
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Help.AddCmd"))
            }
        } else {
            helpAdd(sender)
        }
    }

    private fun helpAdd(sender: CommandSender) {
        sender.sendMessage(LocaleHandler.getString("Command.Help.Header"))
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"))
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"))
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"))
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"))
    }
}
