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
import tech.feldman.couponcodes.api.coupon.*
import tech.feldman.couponcodes.api.entity.Player
import tech.feldman.couponcodes.api.exceptions.UnknownMaterialException
import tech.feldman.couponcodes.core.util.LocaleHandler

class RedeemCommand(private val sender: CommandSender, private val args: Array<String>) : Runnable {

    override fun run() {
        if (args.size == 2) {
            val coupon = CouponCodes.getCouponHandler().getCoupon(args[1])

            if (sender !is Player) {
                sender.sendMessage(LocaleHandler.getString("Command.Redeem.NotPlayer"))
                return
            }

            if (coupon == null) {
                sender.sendMessage(LocaleHandler.getString("Command.Shared.DoesNotExist"))
                return
            }
            if (coupon.useTimes < 1) {
                sender.sendMessage(LocaleHandler.getString("Command.Redeem.UsedUp"))
                return
            }
            if (coupon.usedPlayers != null) {
                if (!coupon.usedPlayers.isEmpty()) {
                    if (coupon.usedPlayers.containsKey(sender.uuid)) {
                        //TODO: CHECK IF USED
                        //if (coupon.usedPlayers[sender.uuid]) {
                            sender.sendMessage(LocaleHandler.getString("Command.Redeem.AlreadyUsed"))
                            return
                        //}
                    }
                }
            }
            if (coupon.time == 0) {
                sender.sendMessage(LocaleHandler.getString("Command.Redeem.OutOfTime"))
                return
            }
            if (coupon.isExpired) {
                sender.sendMessage(LocaleHandler.getString("Command.Redeem.Expired"))
                return
            }

            if (coupon is ItemCoupon) {
                for ((key, value) in coupon.items) {
                    try {
                        sender.giveItem(key, value)
                    } catch (ignored: UnknownMaterialException) {
                    }

                }
                sender.sendMessage(LocaleHandler.getString("Command.Redeem.RedeemItem", coupon.name))
            } else if (coupon is EconomyCoupon) {
                if (CouponCodes.getEconomyHandler() == null) {
                    sender.sendMessage(LocaleHandler.getString("Command.Redeem.EconDisabled"))
                    return
                } else {
                    CouponCodes.getEconomyHandler().giveMoney(sender.uuid, coupon.money)
                    sender.sendMessage(LocaleHandler.getString("Command.Redeem.RedeemEcon", coupon.name))
                }
            } else if (coupon is RankCoupon) {
                if (!CouponCodes.getPermissionHandler().groupSupport()) {
                    sender.sendMessage(LocaleHandler.getString("Command.Redeem.RankDisabled"))
                    return
                } else {
                    CouponCodes.getPermissionHandler().setPlayerGroup(sender, coupon.group)
                    sender.sendMessage(LocaleHandler.getString("Command.Redeem.RedeemRank", coupon.name, coupon.group))
                }
            } else if (coupon is XpCoupon) {
                sender.level = sender.level + coupon.xp
                sender.sendMessage(LocaleHandler.getString("Command.Redeem.RedeemXp", coupon.name, coupon.xp))
            } else if (coupon is CommandCoupon) {
                CouponCodes.getModTransformer().runCommand(null, coupon.cmd.replace("%p", sender.name))
                sender.sendMessage(LocaleHandler.getString("Command.Redeem.RedeemCmd", coupon.name))
            }

            val up = coupon.usedPlayers
            up.put(sender.uuid, true)
            coupon.usedPlayers = up
            coupon.useTimes = coupon.useTimes - 1
            CouponCodes.getCouponHandler().updateCoupon(coupon)
        } else {
            sender.sendMessage(LocaleHandler.getString("Command.Help.Redeem"))
        }
    }
}
