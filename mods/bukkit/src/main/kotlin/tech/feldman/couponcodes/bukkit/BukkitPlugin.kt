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
package tech.feldman.couponcodes.bukkit

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import tech.feldman.couponcodes.api.CouponCodes
import tech.feldman.couponcodes.api.entity.Player
import tech.feldman.couponcodes.bukkit.config.BukkitConfigHandler
import tech.feldman.couponcodes.bukkit.coupon.BukkitCouponTimer
import tech.feldman.couponcodes.bukkit.database.SQLDatabase
import tech.feldman.couponcodes.bukkit.database.SQLDatabaseHandler
import tech.feldman.couponcodes.bukkit.database.options.MySQLOptions
import tech.feldman.couponcodes.bukkit.database.options.SQLiteOptions
import tech.feldman.couponcodes.bukkit.economy.VaultEconomyHandler
import tech.feldman.couponcodes.bukkit.listeners.BukkitListener
import tech.feldman.couponcodes.bukkit.metrics.CustomDataSender
import tech.feldman.couponcodes.bukkit.metrics.Metrics
import tech.feldman.couponcodes.bukkit.permission.SuperPermsPermissionHandler
import tech.feldman.couponcodes.bukkit.permission.VaultPermissionHandler
import tech.feldman.couponcodes.core.commands.SimpleCommandHandler
import tech.feldman.couponcodes.core.event.SimpleEventHandler
import tech.feldman.couponcodes.core.util.LocaleHandler
import java.io.File
import java.io.IOException
import java.sql.SQLException

class BukkitPlugin : JavaPlugin(), Listener {

    private var econ: Economy? = null

    override fun onDisable() {
        CouponCodes.setModTransformer(null)

        try {
            (CouponCodes.getDatabase() as SQLDatabase).close()
        } catch (e: SQLException) {
            logger!!.severe(LocaleHandler.getString("Console.SQL.CloseFailed"))
        }

    }

    override fun onEnable() {
        CouponCodes.setEventHandler(SimpleEventHandler())
        CouponCodes.setModTransformer(BukkitServerModTransformer(this))
        CouponCodes.setConfigHandler(BukkitConfigHandler(this))
        CouponCodes.setCommandHandler(SimpleCommandHandler())

        LocaleHandler.locale = CouponCodes.getConfigHandler().locale

        //SQL
        if ((CouponCodes.getConfigHandler() as BukkitConfigHandler).sqlValue.equals("MYSQL", ignoreCase = true)) {
            CouponCodes.setDatabase(SQLDatabase(this, MySQLOptions((CouponCodes.getConfigHandler() as BukkitConfigHandler).hostname, (CouponCodes.getConfigHandler() as BukkitConfigHandler).port, (CouponCodes.getConfigHandler() as BukkitConfigHandler).database, (CouponCodes.getConfigHandler() as BukkitConfigHandler).username, (CouponCodes.getConfigHandler() as BukkitConfigHandler).password)))
        } else if ((CouponCodes.getConfigHandler() as BukkitConfigHandler).sqlValue.equals("SQLite", ignoreCase = true)) {
            CouponCodes.setDatabase(SQLDatabase(this, SQLiteOptions(File("$dataFolder/coupon_data.db"))))
        } else if (!(CouponCodes.getConfigHandler() as BukkitConfigHandler).sqlValue.equals("MYSQL", ignoreCase = true) && !(CouponCodes.getConfigHandler() as BukkitConfigHandler).sqlValue.equals("SQLite", ignoreCase = true)) {
            logger!!.severe(LocaleHandler.getString("Console.SQL.UnknownValue", (CouponCodes.getConfigHandler() as BukkitConfigHandler).sqlValue))
            logger!!.severe(LocaleHandler.getString("Console.SQL.SetupFailed"))
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }
        try {
            (CouponCodes.getDatabase() as SQLDatabase).open()
            (CouponCodes.getDatabase() as SQLDatabase).createTable("CREATE TABLE IF NOT EXISTS couponcodes (name VARCHAR(24), ctype VARCHAR(10), usetimes INT(10), usedplayers TEXT(1024), ids VARCHAR(255), money INT(10), groupname VARCHAR(20), timeuse INT(100), xp INT(10), command VARCHAR(255))")
            CouponCodes.setDatabaseHandler(SQLDatabaseHandler(this, CouponCodes.getDatabase() as SQLDatabase))
        } catch (e: SQLException) {
            e.printStackTrace()
            logger!!.severe(LocaleHandler.getString("Console.SQL.SetupFailed"))
            Bukkit.getPluginManager().disablePlugin(this)
            return
        }

        // Vault
        if (setupVault()) {
            logger!!.info(LocaleHandler.getString("Console.Vault.Enabled"))
            CouponCodes.setEconomyHandler(VaultEconomyHandler(econ!!))
        } else {
            logger!!.info(LocaleHandler.getString("Console.Vault.Disabled"))
        }


        // Events
        server.pluginManager.registerEvents(BukkitListener(this), this)

        // Permissions
        if (server.pluginManager.getPlugin("Vault") != null) {
            CouponCodes.setPermissionHandler(VaultPermissionHandler())
        } else {
            CouponCodes.setPermissionHandler(SuperPermsPermissionHandler())
        }

        // Timer
        if (CouponCodes.getConfigHandler().useThread) {
            server.scheduler.scheduleSyncRepeatingTask(this, BukkitCouponTimer(), 200L, 200L)
        }

        // Metrics
        if (CouponCodes.getConfigHandler().useMetrics) {
            try {
                val metrics = Metrics(this)
                CouponCodes.getModTransformer().scheduleRunnable(CustomDataSender(metrics))
                metrics.start()
            } catch (ignored: IOException) {
            }

        }
    }

    private fun setupVault(): Boolean {
        if (server.pluginManager.getPlugin("Vault") == null)
            return false
        try {
            val ep = server.servicesManager.getRegistration(Economy::class.java)
            val pe = server.servicesManager.getRegistration(Permission::class.java)
            if (ep == null || pe == null) {
                return false
            } else {
                econ = ep.provider
                return true
            }
        } catch (e: NoClassDefFoundError) {
            return false
        }
    }

    fun wrapPlayer(player: org.bukkit.entity.Player): Player {
        return CouponCodes.getModTransformer().getPlayer(player.uniqueId.toString())
    }

}
