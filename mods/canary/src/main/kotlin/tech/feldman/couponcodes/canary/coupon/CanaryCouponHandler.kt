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
package tech.feldman.couponcodes.canary.coupon

import net.canarymod.database.Database
import net.canarymod.database.exceptions.DatabaseReadException
import net.canarymod.database.exceptions.DatabaseWriteException
import tech.feldman.couponcodes.api.coupon.*
import tech.feldman.couponcodes.api.exceptions.UnknownMaterialException
import tech.feldman.couponcodes.canary.database.CanaryDataAccess
import tech.feldman.couponcodes.core.coupon.SimpleCouponHandler
import java.util.*

class CanaryCouponHandler : SimpleCouponHandler() {

    override fun addCouponToDatabase(coupon: Coupon): Boolean {
        if (couponExists(coupon))
            return false
        val da: CanaryDataAccess = CanaryDataAccess()
        da.couponName = coupon.name
        da.usetimes = coupon.useTimes
        da.usedplayers = playerHashToString(coupon.usedPlayers)
        da.ctype = coupon.type
        da.timeuse = coupon.time

        if (coupon is ItemCoupon) {
            da.ids = itemHashToString(coupon.items)
        } else if (coupon is EconomyCoupon) {
            da.money = coupon.money
        } else if (coupon is RankCoupon) {
            da.groupname = coupon.group
        } else if (coupon is XpCoupon) {
            da.xp = coupon.xp
        } else if (coupon is CommandCoupon) {
            da.command = coupon.cmd
        }

        val filter: HashMap<String, *> = hashMapOf(
                Pair("name", coupon.name)
        )

        try {
            Database.get().update(da, filter)
            return true
        } catch (e: DatabaseWriteException) {
            return false
        }
    }

    override fun removeCouponFromDatabase(coupon: Coupon): Boolean {
        if (!couponExists(coupon))
            return false

        val da: CanaryDataAccess = CanaryDataAccess()
        val filter: HashMap<String, *> = hashMapOf(
                Pair("name", coupon.name)
        )

        try {
            Database.get().remove(da, filter)
            return true
        } catch (e: DatabaseWriteException) {
            return false
        }
    }

    override fun removeCouponFromDatabase(coupon: String): Boolean {
        if (!couponExists(coupon))
            return false

        val da: CanaryDataAccess = CanaryDataAccess()
        val filter: HashMap<String, *> = hashMapOf(
                Pair("name", coupon)
        )

        try {
            Database.get().remove(da, filter)
            return true
        } catch (e: DatabaseWriteException) {
            return false
        }
    }

    override fun getCoupons(): List<String> {
        val da: CanaryDataAccess = CanaryDataAccess()
        val ds: List<CanaryDataAccess> = arrayListOf()
        val filter: Map<String, Any>  = hashMapOf()
        val coupons: MutableList<String> = mutableListOf()

        try {
            Database.get().loadAll(da, ds, filter)
        } catch (e: DatabaseReadException) {
            return coupons
        }

        for (cda in ds) {
            coupons.add(cda.name)
        }

        return coupons
    }

    override fun updateCoupon(coupon: Coupon) {
        val da: CanaryDataAccess = CanaryDataAccess()
        da.couponName = coupon.name
        da.usetimes = coupon.useTimes
        da.timeuse = coupon.time
        da.usedplayers = playerHashToString(coupon.usedPlayers)
        da.ctype = coupon.type

        if (coupon is ItemCoupon) {
            da.ids = itemHashToString(coupon.items)
        } else if (coupon is EconomyCoupon) {
            da.money = coupon.money
        } else if (coupon is RankCoupon) {
            da.groupname = coupon.group
        } else if (coupon is XpCoupon) {
            da.xp = coupon.xp
        }
        if (coupon is CommandCoupon) {
            da.command = coupon.cmd
        }

        val filter: HashMap<String, *> = hashMapOf(
                Pair("name", coupon.name)
        )

        try {
            Database.get().update(da, filter)
        } catch (e: DatabaseWriteException) {
            e.printStackTrace()
        }
    }

    override fun updateCouponTime(coupon: Coupon) {
        updateCoupon(coupon)
    }

    override fun getCoupon(coupon: String): Coupon? {
        if (!couponExists(coupon))
            return null

        val da: CanaryDataAccess = CanaryDataAccess()
        val filter: HashMap<String, *> = hashMapOf(
                Pair("name", coupon)
        )

        try {
            Database.get().load(da, filter)
        } catch (e: DatabaseReadException) {
            e.printStackTrace()
            return null
        }

        val usetimes = da.usetimes
        val time = da.timeuse
        val usedplayers = playerStringToHash(da.usedplayers)

        if (da.ctype.equals("Item", ignoreCase = true))
            try {
                return createNewItemCoupon(coupon, usetimes, time, itemStringToHash(da.ids, null), usedplayers)
            } catch (e: UnknownMaterialException) {
                // This should never happen, unless the database was modified by something not this plugin
                return null
            }
        else if (da.ctype.equals("Economy", ignoreCase = true))
            return createNewEconomyCoupon(coupon, usetimes, time, usedplayers, da.money)
        else if (da.ctype.equals("Rank", ignoreCase = true))
            return createNewRankCoupon(coupon, da.groupname, usetimes, time, usedplayers)
        else if (da.ctype.equals("Xp", ignoreCase = true))
            return createNewXpCoupon(coupon, da.xp, usetimes, time, usedplayers)
        else if (da.ctype.equals("Command", ignoreCase = true))
            return createNewCommandCoupon(coupon, da.command, usetimes, time, usedplayers)
        else
            return null
    }

}
