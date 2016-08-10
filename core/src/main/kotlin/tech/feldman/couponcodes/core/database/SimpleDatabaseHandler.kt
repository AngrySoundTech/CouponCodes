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

import tech.feldman.couponcodes.api.coupon.Coupon
import tech.feldman.couponcodes.api.database.DatabaseHandler

abstract class SimpleDatabaseHandler : DatabaseHandler {

    override fun couponExists(coupon: Coupon) = coupons.contains(coupon.name)

    override fun couponExists(coupon: String) = coupons.contains(coupon)

    override fun getAmountOf(type: String) = coupons.filter { getCoupon(it).type.equals(type)}.size

    companion object {

        fun itemHashToString(hash: Map<String, Int>) =
                hash.map { "${it.key},${it.value}" }.joinToString(separator = "|")

        fun itemStringToHash(args: String) =
                args.split("|").map { Pair(it.split(",")[0], it.split(",")[1].toInt()) }.toMap()

        fun playerHashToString(hash: Map<String, Boolean>) =
                hash.map { "${it.key}:${it.value}" }.joinToString(separator = ",")

        fun playerStringToHash(args: String) =
                args.split(",").map { Pair(it.split(":")[0], it.split(":")[1].toBoolean()) }.toMap()
    }

}
