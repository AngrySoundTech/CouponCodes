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
package tech.feldman.couponcodes.core.coupon

import tech.feldman.couponcodes.api.CouponCodes
import tech.feldman.couponcodes.api.coupon.*
import tech.feldman.couponcodes.api.event.coupon.CouponAddToDatabaseEvent
import tech.feldman.couponcodes.api.event.coupon.CouponExpireEvent
import tech.feldman.couponcodes.api.event.coupon.CouponRemoveFromDatabaseEvent
import java.util.*

abstract class SimpleCoupon(private var name: String, private var usetimes: Int, private var time: Int, private var usedplayers: Map<String, Boolean>) : Coupon {

    private var expired: Boolean

    init {
        this.expired = usetimes <= 0 || time == 0
    }

    override fun addToDatabase(): Boolean {
        if (CouponCodes.getDatabaseHandler().addCouponToDatabase(this)) {
            CouponCodes.getEventHandler().post(CouponAddToDatabaseEvent(this))
            return true
        }
        return false
    }

    override fun removeFromDatabase(): Boolean {
        if (CouponCodes.getDatabaseHandler().removeCouponFromDatabase(this)) {
            CouponCodes.getEventHandler().post(CouponRemoveFromDatabaseEvent(this))
            return true
        }
        return false
    }

    override fun isInDatabase() = CouponCodes.getDatabaseHandler().couponExists(this)

    override fun updateWithDatabase() = CouponCodes.getDatabaseHandler().updateCoupon(this)

    override fun updateTimeWithDatabase() = CouponCodes.getDatabaseHandler().updateCouponTime(this)

    override fun getName()= name
    override fun setName(name: String) {
        this.name = name
    }

    override fun getUseTimes() = usetimes
    override fun setUseTimes(usetimes: Int) {
        this.usetimes = usetimes
        if (this.usetimes <= 0)
            this.isExpired = true
    }

    override fun getTime() = time
    override fun setTime(time: Int) {
        this.time = time
        if (this.time == 0)
            this.isExpired = true
    }

    override fun getUsedPlayers(): HashMap<String, Boolean> = usedPlayers
    override fun setUsedPlayers(usedplayers: Map<String, Boolean>) {
        this.usedplayers = usedplayers
    }

    override fun isExpired() = expired
    override fun setExpired(expired: Boolean) {
        this.expired = expired
        if (expired)
            CouponCodes.getEventHandler().post(CouponExpireEvent(this))
    }

    override fun getType(): String? {
        return when(this) {
            is ItemCoupon -> "Item"
            is EconomyCoupon -> "Economy"
            is RankCoupon -> "Rank"
            is XpCoupon -> "Xp"
            is CommandCoupon -> "Command"
            else -> null
        }
    }

}
