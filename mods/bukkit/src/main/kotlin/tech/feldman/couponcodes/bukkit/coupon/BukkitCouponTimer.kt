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
package tech.feldman.couponcodes.bukkit.coupon

import tech.feldman.couponcodes.api.CouponCodes
import tech.feldman.couponcodes.api.event.coupon.CouponTimeChangeEvent
import tech.feldman.couponcodes.bukkit.database.options.MySQLOptions
import java.sql.SQLException
import java.util.*

class BukkitCouponTimer : Runnable {

    private val ch: BukkitCouponHandler
    private var cl: ArrayList<String>? = null

    init {
        ch = CouponCodes.getCouponHandler() as BukkitCouponHandler

        // Make sure SQL is open
        if (ch.databaseHandler.databaseOptions is MySQLOptions) {
            try {
                ch.databaseHandler.open()
            } catch (ignored: SQLException) {
            }

        }

        cl = ArrayList<String>()
    }

    override fun run() {
        try {
            cl = ch.coupons
            if (cl == null)
                return

            for (name in cl!!) {
                if (ch.databaseHandler.connection!!.isClosed)
                    return
                val c = ch.getCoupon(name) ?: continue
                if (c.isExpired || c.time == -1)
                    continue

                if (c.time - 10 < 0) {
                    if (c.time - 5 < 0 || c.time - 5 == 0) {
                        c.time = 0
                    } else {
                        c.time = 5
                    }
                } else {
                    c.time = c.time - 10
                }
                ch.updateCouponTime(c)
                CouponCodes.getEventHandler().post(CouponTimeChangeEvent(c))
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } catch (ignored: Exception) {

        }

    }

}
