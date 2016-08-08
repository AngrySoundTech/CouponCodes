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
package tech.feldman.couponcodes.core.database

import tech.feldman.couponcodes.api.CouponCodes
import tech.feldman.couponcodes.api.command.CommandSender
import tech.feldman.couponcodes.api.coupon.*
import tech.feldman.couponcodes.api.database.DatabaseHandler
import tech.feldman.couponcodes.api.exceptions.UnknownMaterialException
import java.util.*

abstract class SimpleDatabaseHandler : DatabaseHandler {

    override fun couponExists(coupon: Coupon): Boolean {
        return coupons.contains(coupon.name)
    }

    override fun couponExists(coupon: String): Boolean {
        return coupons.contains(coupon)
    }

    override fun getAmountOf(type: String): Int {
        val list = coupons
        var item = 0
        var econ = 0
        var rank = 0
        var xp = 0
        var cmd = 0

        for (name in list) {
            val c = getCoupon(name)
            if (c is ItemCoupon)
                item++
            if (c is EconomyCoupon)
                econ++
            if (c is RankCoupon)
                rank++
            if (c is XpCoupon)
                xp++
            if (c is CommandCoupon)
                cmd++
        }

        if (type.equals("Item", ignoreCase = true))
            return item
        else if (type.equals("Economy", ignoreCase = true))
            return econ
        else if (type.equals("Rank", ignoreCase = true))
            return rank
        else if (type.equals("Xp", ignoreCase = true))
            return xp
        else if (type.equals("Cmd", ignoreCase = true))
            return cmd
        else
            return 0
    }

    override fun itemHashToString(hash: HashMap<String, Int>): String {
        val sb = StringBuilder()
        for ((key, value) in hash) {
            sb.append(key).append(",").append(value).append("|")
        }
        sb.deleteCharAt(sb.length - 1)
        return sb.toString()
    }

    @Throws(UnknownMaterialException::class)
    override fun itemStringToHash(args: String, sender: CommandSender?): HashMap<String, Int> {
        val ids = HashMap<String, Int>()
        val sp = args.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        try {
            for (s in sp) {
                val name: String
                var amount = 0
                if (CouponCodes.getModTransformer().isValidMaterial(s.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].toUpperCase())) {
                    name = s.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].toUpperCase()
                } else {
                    throw UnknownMaterialException(s.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].toUpperCase())
                }
                if (CouponCodes.getModTransformer().isNumeric(s.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])) {
                    amount = Integer.parseInt(s.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])
                }
                ids.put(name, amount)
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        return ids
    }

    override fun playerHashToString(hash: HashMap<String, Boolean>): String {
        if (hash.isEmpty())
            return ""
        val sb = StringBuilder()
        for ((key, value) in hash) {
            sb.append(key).append(":").append(value).append(",")
        }
        sb.deleteCharAt(sb.length - 1)
        return sb.toString()
    }

    override fun playerStringToHash(args: String): HashMap<String, Boolean> {
        val pl = HashMap<String, Boolean>()
        if (args.length < 1)
            return pl
        val sp = args.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        try {
            for (aSp in sp) {
                val a = aSp.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].toString()
                val b = java.lang.Boolean.valueOf(aSp.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])
                pl.put(a, b)
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }

        return pl
    }

}
