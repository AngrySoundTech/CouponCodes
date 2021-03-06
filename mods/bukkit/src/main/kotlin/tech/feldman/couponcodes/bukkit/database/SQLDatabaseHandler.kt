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
package tech.feldman.couponcodes.bukkit.database

import tech.feldman.couponcodes.api.coupon.*
import tech.feldman.couponcodes.api.exceptions.UnknownMaterialException
import tech.feldman.couponcodes.bukkit.BukkitPlugin
import tech.feldman.couponcodes.bukkit.database.options.MySQLOptions
import tech.feldman.couponcodes.core.coupon.*
import tech.feldman.couponcodes.core.database.SimpleDatabaseHandler
import java.sql.PreparedStatement
import java.sql.SQLException
import java.util.*

class SQLDatabaseHandler(val plugin: BukkitPlugin, val database: SQLDatabase) : SimpleDatabaseHandler() {

    override fun addCouponToDatabase(coupon: Coupon): Boolean {
        if (couponExists(coupon))
            return false
        try {
            val con = database.connection
            var p: PreparedStatement? = null

            if (coupon is ItemCoupon) {
                p = con!!.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, ids, timeuse) " + "VALUES (?, ?, ?, ?, ?, ?)")
                p!!.setString(1, coupon.name)
                p.setString(2, coupon.type)
                p.setInt(3, coupon.useTimes)
                p.setString(4, playerHashToString(coupon.usedPlayers))
                p.setString(5, itemHashToString(coupon.items))
                p.setInt(6, coupon.time)
            } else if (coupon is EconomyCoupon) {
                p = con!!.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, money, timeuse) " + "VALUES (?, ?, ?, ?, ?, ?)")
                p!!.setString(1, coupon.name)
                p.setString(2, coupon.type)
                p.setInt(3, coupon.useTimes)
                p.setString(4, playerHashToString(coupon.usedPlayers))
                p.setInt(5, coupon.money)
                p.setInt(6, coupon.time)
            } else if (coupon is RankCoupon) {
                p = con!!.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, groupname, timeuse) " + "VALUES (?, ?, ?, ?, ?, ?)")
                p!!.setString(1, coupon.name)
                p.setString(2, coupon.type)
                p.setInt(3, coupon.useTimes)
                p.setString(4, playerHashToString(coupon.usedPlayers))
                p.setString(5, coupon.group)
                p.setInt(6, coupon.time)
            } else if (coupon is XpCoupon) {
                p = con!!.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, timeuse, xp) " + "VALUES (?, ?, ?, ?, ?, ?)")
                p!!.setString(1, coupon.name)
                p.setString(2, coupon.type)
                p.setInt(3, coupon.useTimes)
                p.setString(4, playerHashToString(coupon.usedPlayers))
                p.setInt(5, coupon.time)
                p.setInt(6, coupon.xp)
            } else if (coupon is CommandCoupon) {
                p = con!!.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, timeuse, command) " + "VALUES (?, ?, ?, ?, ?, ?)")
                p!!.setString(1, coupon.name)
                p.setString(2, coupon.type)
                p.setInt(3, coupon.useTimes)
                p.setString(4, playerHashToString(coupon.usedPlayers))
                p.setInt(5, coupon.time)
                p.setString(6, coupon.cmd)
            }

            p!!.addBatch()
            con!!.autoCommit = false
            p.executeBatch()
            con.autoCommit = true
            return true
        } catch (e: SQLException) {
            return false
        }

    }

    override fun removeCouponFromDatabase(coupon: Coupon): Boolean {
        if (!couponExists(coupon))
            return false
        try {
            database.query("DELETE FROM couponcodes WHERE name='" + coupon.name + "'")
            return true
        } catch (e: SQLException) {
            return false
        }

    }

    override fun removeCouponFromDatabase(coupon: String): Boolean {
        if (!couponExists(coupon))
            return false
        try {
            database.query("DELETE FROM couponcodes WHERE name='$coupon'")
            return true
        } catch (e: SQLException) {
            return false
        }

    }

    override fun getCoupons(): List<String> {
        val c = ArrayList<String>()
        try {
            val rs = database.query("SELECT name FROM couponcodes")
            while (rs.next())
                c.add(rs.getString(1))
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return c
    }

    override fun updateCoupon(coupon: Coupon) {
        try {
            database.query("UPDATE couponcodes SET usetimes='" + coupon.useTimes + "' WHERE name='" + coupon.name + "'")
            database.query("UPDATE couponcodes SET usedplayers='" + playerHashToString(coupon.usedPlayers) + "' WHERE name='" + coupon.name + "'")
            database.query("UPDATE couponcodes SET timeuse='" + coupon.time + "' WHERE name='" + coupon.name + "'")

            if (coupon is ItemCoupon)
                database.query("UPDATE couponcodes SET ids='" + itemHashToString(coupon.items) + "' WHERE name='" + coupon.getName() + "'")
            else if (coupon is EconomyCoupon)
                database.query("UPDATE couponcodes SET money='" + coupon.money + "' WHERE name='" + coupon.getName() + "'")
            else if (coupon is RankCoupon)
                database.query("UPDATE couponcodes SET groupname='" + coupon.group + "' WHERE name='" + coupon.getName() + "'")
            else if (coupon is XpCoupon)
                database.query("UPDATE couponcodes SET xp='" + coupon.xp + "' WHERE name='" + coupon.getName() + "'")
            else if (coupon is CommandCoupon)
                database.query("UPDATE couponcodes SET command='" + coupon.cmd + "' WHERE name='" + coupon.getName() + "'")
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    override fun updateCouponTime(coupon: Coupon) {
        try {
            database.query("UPDATE couponcodes SET timeuse='" + coupon.time + "' WHERE name='" + coupon.name + "'")
        } catch (e: SQLException) {
            e.printStackTrace()
        }

    }

    override fun getCoupon(coupon: String): Coupon? {
        if (!couponExists(coupon))
            return null
        try {
            val rs = database.query("SELECT * FROM couponcodes WHERE name='$coupon'")
            if (database.databaseOptions is MySQLOptions)
                rs.first()
            val usetimes = rs.getInt("usetimes")
            val time = rs.getInt("timeuse")
            val usedplayers = playerStringToHash(rs.getString("usedplayers"))

            if (rs.getString("ctype").equals("Item", ignoreCase = true))
                try {
                    return SimpleItemCoupon(coupon, usetimes, time, usedplayers, itemStringToHash(rs.getString("ids")))
                } catch (e: UnknownMaterialException) {
                    // This should never happen, unless the database was modified by something not this plugin
                    return null
                }
            else if (rs.getString("ctype").equals("Economy", ignoreCase = true))
                return SimpleEconomyCoupon(coupon, usetimes, time, usedplayers, rs.getInt("money"))
            else if (rs.getString("ctype").equals("Rank", ignoreCase = true))
                return SimpleRankCoupon(coupon, usetimes, time, usedplayers, rs.getString("groupname"))
            else if (rs.getString("ctype").equals("Xp", ignoreCase = true))
                return SimpleXpCoupon(coupon, usetimes, time, usedplayers, rs.getInt("xp"))
            else if (rs.getString("ctype").equals("Command", ignoreCase = true))
                return SimpleCommandCoupon(coupon, usetimes, time, usedplayers, rs.getString("command"))
            else
                return null
        } catch (e: SQLException) {
            e.printStackTrace()
            return null
        }

    }

}
