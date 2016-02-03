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
package com.drevelopment.couponcodes.canary.config;

import net.visualillusionsent.utils.PropertiesFile;

import com.drevelopment.couponcodes.api.config.ConfigHandler;
import com.drevelopment.couponcodes.canary.CanaryPlugin;

public class CanaryConfigHandler implements ConfigHandler {

    private PropertiesFile config;

    public CanaryConfigHandler(CanaryPlugin plugin) {
        config = plugin.getConfig();
        populateConfig();
        config.save();
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
        return config.getString("locale", "en_US");
    }

    private void populateConfig() {
        config.addHeaderLines("This is the configuration for CouponCodes");
        config.addHeaderLines("If you have questions about using this plugin, or what anything means, please feel free to ask on the BukkitDev page");
        config.addHeaderLines("If you are receiving errors, please report it on Github. It really helps the development of this plugin.");
        config.addHeaderLines("If you are interested in how this plugin works (code wise) you can view the source code from GitHub.");
        config.addHeaderLines("You can also open an issue here for any feature requests / bug reporting.");
        config.addHeaderLines("https://github.com/AngrySoundTech/CouponCodes3");
        config.getString("locale", "en_US");
        config.addComment("locale", "");
        config.addComment("locale", "Language the plugin should use by default.");
        config.addComment("locale", "If you would like to help translate this plugin to your language, you can contribute here: https://crowdin.com/project/couponcodes");
        config.addComment("locale", "available languages are: en_US       de      nl     en_PT         pl      ru");
        config.addComment("locale", "                         US English  German  Dutch  Pirate Speak  Polish  Russian");
        config.getBoolean("use-thread", true);
        config.addComment("use-thread", " ");
        config.addComment("use-thread", "Leave this true UNLESS you want to disable timed coupons");
        config.getBoolean("debug", false);
        config.addComment("debug", " ");
        config.addComment("debug", "I recommend to leave this at false. Your console will be spammed with debug messages");
        config.getBoolean("use-metrics", true);
        config.addComment("use-metrics", " ");
        config.addComment("use-metrics", "Metrics collects information about CouponCodes, such as version and amount of coupons.");
        config.addComment("use-metrics", "If this is disabled, no info will be sent to http://mcstats.org/plugin/CouponCodes");
        config.getBoolean("auto-update", false);
        config.addComment("auto-update", " ");
        config.addComment("auto-update", "If set to false, the plugin will not update automatically (CURRENTLY UNAVAILABLE FOR CANARY)");
    }

}
