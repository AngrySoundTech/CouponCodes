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

import tech.feldman.couponcodes.api.CouponCodes
import tech.feldman.couponcodes.api.command.CommandSender
import tech.feldman.couponcodes.api.coupon.EconomyCoupon
import tech.feldman.couponcodes.api.coupon.ItemCoupon
import tech.feldman.couponcodes.api.coupon.RankCoupon
import tech.feldman.couponcodes.api.coupon.XpCoupon
import tech.feldman.couponcodes.core.util.LocaleHandler
import java.text.DecimalFormat

class InfoCommand(private val sender: CommandSender, private val args: Array<String>) : Runnable {

    override fun run() {
        if (args.size == 2) {
            val c = CouponCodes.getDatabaseHandler().getCoupon(args[1])
            if (c != null) {
                sender.sendMessage(LocaleHandler.getString("Command.Info.FancyWrap"))
                sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Header", c.name))
                sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Name", c.name))
                sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Type", c.type))
                sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Usetimes", c.useTimes))
                if (c.time != -1)
                    sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.TimeLeft", c.time))
                else
                    sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.TimeLeft", "unlimited"))

                if (c.usedPlayers.isEmpty())
                    sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.UsedPlayers", "None"))
                else {
                    val usedPlayers = c.usedPlayers
                    val sb = StringBuilder()
                    for (s in usedPlayers.keys) {
                        //TODO: CHECK IF REALLY USED
                        //if (usedPlayers[s]) {
                            sb.append(CouponCodes.getModTransformer().getPlayerName(s)).append(", ")
                        //}
                    }
                    sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.UsedPlayers", sb.toString()))
                }
                if (c is ItemCoupon)
                    sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Items", CouponCodes.getDatabaseHandler().itemHashToString(c.items)))
                else if (c is EconomyCoupon)
                    sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Money", c.money))
                else if (c is RankCoupon)
                    sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Rank", c.group))
                else if (c is XpCoupon)
                    sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Xp", c.xp))
                sender.sendMessage(LocaleHandler.getString("Command.Info.FancyWrap"))
            } else {
                sender.sendMessage(LocaleHandler.getString("Command.Shared.DoesNotExist"))
            }
        } else {
            val sb1 = StringBuilder()
            val sb2 = StringBuilder()
            val co = CouponCodes.getDatabaseHandler().coupons
            var total = 0
            if (co.isEmpty() || co == null) {
                sb1.append(LocaleHandler.getString("Command.Info.None"))
                sb2.append(LocaleHandler.getString("Command.Info.Breakdown", 0, 0, 0, 0))
            } else {
                val j = co.size.toDouble()
                var it = 0.0
                var ec = 0.0
                var ra = 0.0
                var xp = 0.0
                var it2: String? = null
                var ec2: String? = null
                var ra2: String? = null
                var xp2: String? = null
                val d = DecimalFormat("##.##")
                for (name in co) {
                    sb1.append(name).append(", ")
                    val coo = CouponCodes.getDatabaseHandler().getCoupon(name)
                    if (coo is ItemCoupon)
                        it++
                    if (coo is EconomyCoupon)
                        ec++
                    if (coo is RankCoupon)
                        ra++
                    if (coo is XpCoupon)
                        xp++
                }
                sb1.deleteCharAt(sb1.length - 1)
                sb1.deleteCharAt(sb1.length - 1)
                it2 = d.format(it / j * 100)
                ec2 = d.format(ec / j * 100)
                ra2 = d.format(ra / j * 100)
                xp2 = d.format(xp / j * 100)
                total = (it + ec + ra + xp).toInt()
                sb2.append(LocaleHandler.getString("Command.Info.Breakdown", it2, ec2, ra2, xp2))
            }
            sender.sendMessage(LocaleHandler.getString("Command.Info.FancyWrap"))
            sender.sendMessage(LocaleHandler.getString("Command.Info.Header"))
            sender.sendMessage(LocaleHandler.getString("Command.Info.SpecificInstructions"))
            sender.sendMessage(LocaleHandler.getString("Command.Info.CurrentCoupons", sb1.toString()))
            sender.sendMessage(sb2.toString())
            sender.sendMessage(LocaleHandler.getString("Command.Info.Total", total))
            sender.sendMessage(LocaleHandler.getString("Command.Info.FancyWrap"))
        }

    }

}
