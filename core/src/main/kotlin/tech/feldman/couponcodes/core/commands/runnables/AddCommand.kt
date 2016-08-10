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
package tech.feldman.couponcodes.core.commands.runnables

import tech.feldman.couponcodes.api.command.CommandSender
import tech.feldman.couponcodes.api.exceptions.UnknownMaterialException
import tech.feldman.couponcodes.core.coupon.*
import tech.feldman.couponcodes.core.database.SimpleDatabaseHandler
import tech.feldman.couponcodes.core.util.LocaleHandler
import tech.feldman.couponcodes.core.util.RandomName
import java.util.*

class AddCommand(private val sender: CommandSender, private val args: Array<String>) : Runnable {

    override fun run() {
        if (args.size < 2) {
            helpAdd(sender)
            return
        }

        when(args[1].toLowerCase()) {
            "item" -> {
                if (args.size >= 4) {
                    var name = args[2]

                    if (name.equals("random", ignoreCase = true))
                        name = RandomName.generateName()
                    if (args.size >= 5) {
                        sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"))
                        return
                    }

                    val itemHash: Map<String, Int>
                    try {
                        itemHash = SimpleDatabaseHandler.itemStringToHash(args[3])
                    } catch (e: UnknownMaterialException) {
                        sender.sendMessage(LocaleHandler.getString("Command.Add.InvalidName", e.itemName))
                        return
                    }

                    val ic = SimpleItemCoupon(name, 1, -1, HashMap(), itemHash)

                    if (ic.addToDatabase()) {
                        sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name))
                    } else {
                        sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"))
                    }
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"))
                }
            }
            "econ" -> {
                if (args.size >= 4) {
                    var name = args[2]
                    val money = Integer.parseInt(args[3])

                    if (name.equals("random", ignoreCase = true))
                        name = RandomName.generateName()
                    if (args.size >= 5) {
                        sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"))
                        return
                    }

                    val ec = SimpleEconomyCoupon(name, 1, -1, HashMap(), money)

                    if (ec.addToDatabase()) {
                        sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name))
                    } else {
                        sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"))
                    }
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"))
                }
            }
            "rank" -> {
                if (args.size >= 4) {
                    var name = args[2]
                    val group = args[3]

                    if (name.equals("random", ignoreCase = true))
                        name = RandomName.generateName()
                    if (args.size >= 5) {
                        sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"))
                        return
                    }

                    val rc = SimpleRankCoupon(name, 1, -1, HashMap(), group)

                    if (rc.addToDatabase()) {
                        sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name))
                    } else {
                        sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"))
                    }
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"))
                }
            }
            "xp" -> {
                if (args.size >= 4) {
                    var name = args[2]
                    val xp = Integer.parseInt(args[3])

                    if (name.equals("random", ignoreCase = true))
                        name = RandomName.generateName()
                    if (args.size >= 5) {
                        sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"))
                        return
                    }

                    val xc = SimpleXpCoupon(name, 1, -1, HashMap(), xp)

                    if (xc.addToDatabase()) {
                        sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name))
                    } else {
                        sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"))
                    }
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"))
                }
            }
            "cmd" -> {
                if (args.size >= 4) {
                    var name = args[2]
                    val sb = StringBuilder()
                    for (i in 3..args.size - 1) {
                        sb.append(args[i]).append(" ")
                    }
                    val cmd = sb.toString()

                    if (name.equals("random", ignoreCase = true))
                        name = RandomName.generateName()

                    val cc = SimpleCommandCoupon(name, 1, -1, HashMap(), cmd)

                    if (cc.addToDatabase()) {
                        sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name))
                    } else {
                        sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"))
                    }
                } else {
                    sender.sendMessage(LocaleHandler.getString("Command.Help.AddCmd"))
                }
            }
            else -> helpAdd(sender)
        }
    }

    private fun helpAdd(sender: CommandSender) {
        sender.sendMessage(LocaleHandler.getString("Command.Help.Header"))
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"))
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"))
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"))
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"))
        sender.sendMessage(LocaleHandler.getString("Command.Help.AddCmd"))
    }
}
