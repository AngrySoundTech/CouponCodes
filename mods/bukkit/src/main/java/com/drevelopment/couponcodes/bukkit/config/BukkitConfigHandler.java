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
package com.drevelopment.couponcodes.bukkit.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import com.drevelopment.couponcodes.api.config.ConfigHandler;

public class BukkitConfigHandler implements ConfigHandler {

    private FileConfiguration config;

    public BukkitConfigHandler(Plugin plugin) {
        this.config = plugin.getConfig();

        if (!(new File("plugins/CouponCodes/config.yml").exists()))
            plugin.saveDefaultConfig();
        if (!config.options().copyDefaults(true).configuration().equals(config))
            plugin.saveConfig();
    }

    @Override
    public boolean getUseThread() {
        return config.getBoolean("use-thread", true);
    }

    @Override
    public boolean getDebug() {
        return config.getBoolean("debug", false);
    }

    @Override
    public boolean getUseMetrics() {
        return config.getBoolean("use-metrics", true);
    }

    @Override
    public boolean getAutoUpdate() {
        return config.getBoolean("auto-update", true);
    }

    @Override
    public String getLocale() {
        return config.getString("locale");
    }

    public String getSQLValue() {
        return config.getString("sql-type");
    }

    public String getHostname() {
        return config.getString("MySQL-options.hostname");
    }

    public String getPort() {
        return config.getString("MySQL-options.port");
    }

    public String getDatabase() {
        return config.getString("MySQL-options.database");
    }

    public String getUsername() {
        return config.getString("MySQL-options.username");
    }

    public String getPassword() {
        return config.getString("MySQL-options.password");
    }
}
