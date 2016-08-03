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
package tech.feldman.couponcodes.core.coupon

import tech.feldman.couponcodes.api.CouponCodes
import tech.feldman.couponcodes.api.coupon.*
import tech.feldman.couponcodes.api.event.coupon.CouponAddToDatabaseEvent
import tech.feldman.couponcodes.api.event.coupon.CouponExpireEvent
import tech.feldman.couponcodes.api.event.coupon.CouponRemoveFromDatabaseEvent
import java.util.*

open class SimpleCoupon(private var name: String, private var usetimes: Int, private var time: Int, private var usedplayers: HashMap<String, Boolean>) : Coupon {
    private var expired: Boolean = false

    init {
        this.expired = usetimes <= 0 || time == 0
    }

    override fun addToDatabase(): Boolean {
        if (CouponCodes.getCouponHandler().addCouponToDatabase(this)) {
            CouponCodes.getEventHandler().post(CouponAddToDatabaseEvent(this))
            return true
        }
        return false
    }

    override fun removeFromDatabase(): Boolean {
        if (CouponCodes.getCouponHandler().removeCouponFromDatabase(this)) {
            CouponCodes.getEventHandler().post(CouponRemoveFromDatabaseEvent(this))
            return true
        }
        return false
    }

    override fun isInDatabase(): Boolean {
        return CouponCodes.getCouponHandler().couponExists(this)
    }

    override fun updateWithDatabase() {
        CouponCodes.getCouponHandler().updateCoupon(this)
    }

    override fun updateTimeWithDatabase() {
        CouponCodes.getCouponHandler().updateCouponTime(this)
    }

    override fun getName(): String {
        return name
    }

    override fun setName(name: String) {
        this.name = name
    }

    override fun getUseTimes(): Int {
        return usetimes
    }

    override fun setUseTimes(usetimes: Int) {
        this.usetimes = usetimes
        if (this.usetimes <= 0)
            this.isExpired = true
    }

    override fun getTime(): Int {
        return time
    }

    override fun setTime(time: Int) {
        this.time = time
        if (this.time == 0)
            this.isExpired = true
    }

    override fun getUsedPlayers(): HashMap<String, Boolean> {
        return usedplayers
    }

    override fun setUsedPlayers(usedplayers: HashMap<String, Boolean>) {
        this.usedplayers = usedplayers
    }

    override fun getType(): String? {
        if (this is ItemCoupon)
            return "Item"
        if (this is EconomyCoupon)
            return "Economy"
        if (this is RankCoupon)
            return "Rank"
        if (this is XpCoupon)
            return "Xp"
        if (this is CommandCoupon)
            return "Command"
        else
            return null
    }

    override fun isExpired(): Boolean {
        return expired
    }

    override fun setExpired(expired: Boolean) {
        this.expired = expired
        if (expired)
            CouponCodes.getEventHandler().post(CouponExpireEvent(this))
    }
}
