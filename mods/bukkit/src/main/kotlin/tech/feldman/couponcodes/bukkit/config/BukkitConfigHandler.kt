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
package tech.feldman.couponcodes.bukkit.config

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.Plugin
import tech.feldman.couponcodes.api.config.ConfigHandler
import java.io.File

class BukkitConfigHandler(plugin: Plugin) : ConfigHandler {

    private val config: FileConfiguration

    init {
        this.config = plugin.config

        if (!File("plugins/CouponCodes/config.yml").exists())
            plugin.saveDefaultConfig()
        if (config.options().copyDefaults(true).configuration() != config)
            plugin.saveConfig()
    }

    override fun getUseThread(): Boolean {
        return config.getBoolean("use-thread", true)
    }

    override fun getDebug(): Boolean {
        return config.getBoolean("debug", false)
    }

    override fun getUseMetrics(): Boolean {
        return config.getBoolean("use-metrics", true)
    }

    override fun getAutoUpdate(): Boolean {
        return config.getBoolean("auto-update", true)
    }

    override fun getLocale(): String {
        return config.getString("locale")
    }

    val sqlValue: String
        get() = config.getString("sql-type")

    val hostname: String
        get() = config.getString("MySQL-options.hostname")

    val port: String
        get() = config.getString("MySQL-options.port")

    val database: String
        get() = config.getString("MySQL-options.database")

    val username: String
        get() = config.getString("MySQL-options.username")

    val password: String
        get() = config.getString("MySQL-options.password")
}
