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
package tech.feldman.couponcodes.bukkit;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import tech.feldman.couponcodes.api.CouponCodes;
import tech.feldman.couponcodes.api.entity.Player;
import tech.feldman.couponcodes.bukkit.config.BukkitConfigHandler;
import tech.feldman.couponcodes.bukkit.coupon.BukkitCouponHandler;
import tech.feldman.couponcodes.bukkit.coupon.BukkitCouponTimer;
import tech.feldman.couponcodes.bukkit.database.SQLDatabaseHandler;
import tech.feldman.couponcodes.bukkit.database.options.MySQLOptions;
import tech.feldman.couponcodes.bukkit.database.options.SQLiteOptions;
import tech.feldman.couponcodes.bukkit.economy.VaultEconomyHandler;
import tech.feldman.couponcodes.bukkit.listeners.BukkitListener;
import tech.feldman.couponcodes.bukkit.metrics.CustomDataSender;
import tech.feldman.couponcodes.bukkit.metrics.Metrics;
import tech.feldman.couponcodes.bukkit.permission.SuperPermsPermissionHandler;
import tech.feldman.couponcodes.bukkit.permission.VaultPermissionHandler;
import tech.feldman.couponcodes.bukkit.updater.Updater;
import tech.feldman.couponcodes.core.commands.SimpleCommandHandler;
import tech.feldman.couponcodes.core.event.SimpleEventHandler;
import tech.feldman.couponcodes.core.util.LocaleHandler;

public class BukkitPlugin extends JavaPlugin implements Listener {

    private Logger logger = null;

    private Economy econ = null;

    @Override
    public void onDisable() {
        CouponCodes.setModTransformer(null);

        try {
            ((SQLDatabaseHandler) CouponCodes.getDatabaseHandler()).close();
        } catch (SQLException e) {
            logger.severe(LocaleHandler.getString("Console.SQL.CloseFailed"));
        }
    }

    @Override
    public void onEnable() {
        logger = this.getLogger();

        CouponCodes.setEventHandler(new SimpleEventHandler());
        CouponCodes.setModTransformer(new BukkitServerModTransformer(this));
        CouponCodes.setConfigHandler(new BukkitConfigHandler(this));
        CouponCodes.setCommandHandler(new SimpleCommandHandler());

        //SQL
        if (((BukkitConfigHandler) CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("MYSQL")) {
            CouponCodes.setDatabaseHandler(new SQLDatabaseHandler(this, new MySQLOptions(((BukkitConfigHandler) CouponCodes.getConfigHandler()).getHostname(), ((BukkitConfigHandler) CouponCodes.getConfigHandler()).getPort(), ((BukkitConfigHandler) CouponCodes.getConfigHandler()).getDatabase(), ((BukkitConfigHandler) CouponCodes.getConfigHandler()).getUsername(), ((BukkitConfigHandler) CouponCodes.getConfigHandler()).getPassword())));
        } else if (((BukkitConfigHandler) CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("SQLite")) {
            CouponCodes.setDatabaseHandler(new SQLDatabaseHandler(this, new SQLiteOptions(new File(getDataFolder() + "/coupon_data.db"))));
        } else if (!((BukkitConfigHandler) CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("MYSQL") && !((BukkitConfigHandler) CouponCodes.getConfigHandler()).getSQLValue().equalsIgnoreCase("SQLite")) {
            logger.severe(LocaleHandler.getString("Console.SQL.UnknownValue", ((BukkitConfigHandler) CouponCodes.getConfigHandler()).getSQLValue()));
            logger.severe(LocaleHandler.getString("Console.SQL.SetupFailed"));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        try {
            ((SQLDatabaseHandler) CouponCodes.getDatabaseHandler()).open();
            ((SQLDatabaseHandler) CouponCodes.getDatabaseHandler()).createTable("CREATE TABLE IF NOT EXISTS couponcodes (name VARCHAR(24), ctype VARCHAR(10), usetimes INT(10), usedplayers TEXT(1024), ids VARCHAR(255), money INT(10), groupname VARCHAR(20), timeuse INT(100), xp INT(10), command VARCHAR(255))");
            CouponCodes.setCouponHandler(new BukkitCouponHandler(this, ((SQLDatabaseHandler) CouponCodes.getDatabaseHandler())));
        } catch (SQLException e) {
            e.printStackTrace();
            logger.severe(LocaleHandler.getString("Console.SQL.SetupFailed"));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        logger.info(LocaleHandler.getString("Console.Database.Convert3.2"));
        // 3.1 -> 3.2+
        try {
            // Add command column
            if (!((SQLDatabaseHandler) CouponCodes.getDatabaseHandler()).getConnection().getMetaData().getColumns(null, null, "couponcodes", "command").next())
                ((SQLDatabaseHandler) CouponCodes.getDatabaseHandler()).query("ALTER TABLE couponcodes ADD COLUMN command VARCHAR(255)");
            // IDs -> Names
            ResultSet rs = ((SQLDatabaseHandler) CouponCodes.getDatabaseHandler()).query("SELECT name,ids FROM couponcodes WHERE ctype='Item'");
            if (rs != null) {
                while (rs.next()) {
                    HashMap<String, String> replacements = new HashMap<>();
                    for (String sp : rs.getString("ids").split(",")) {
                        if (StringUtils.isNumeric(sp.split(":")[0])) {
                            @SuppressWarnings("deprecation") // Warning ignored. This is a compatibility patch from old versions.
                            String name = Material.getMaterial(Integer.parseInt(sp.split(":")[0])).toString();
                            String oldid = sp.split(":")[0];
                            replacements.put(name, oldid);
                        }
                    }
                    String itemlist = rs.getString("ids");
                    for (String key : replacements.keySet()) {
                        itemlist = itemlist.replace(replacements.get(key),key);
                        logger.info("ID: "+ replacements.get(key) + " changed to: " + key);
                        logger.info(LocaleHandler.getString("Console.Database.Changed", replacements.get(key), key));
                    }
                    ((SQLDatabaseHandler) CouponCodes.getDatabaseHandler()).query("UPDATE couponcodes SET ids='"+itemlist+ "' WHERE name='"+rs.getString("name")+"'");

                }
            }

        } catch (SQLException e) {
            logger.severe(LocaleHandler.getString("Console.Database.FailedUpdate"));
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        logger.info("Database updating successful");

        // Vault
        if (!setupVault()) {
            logger.info(LocaleHandler.getString("Console.Vault.Disabled"));
        } else {
            logger.info(LocaleHandler.getString("Console.Vault.Enabled"));
            CouponCodes.setEconomyHandler(new VaultEconomyHandler(econ));
        }

        // Events
        getServer().getPluginManager().registerEvents(new BukkitListener(this), this);

        // Permissions
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            CouponCodes.setPermissionHandler(new VaultPermissionHandler());
        } else {
            CouponCodes.setPermissionHandler(new SuperPermsPermissionHandler());
        }

        // Timer
        if (CouponCodes.getConfigHandler().getUseThread()) {
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new BukkitCouponTimer(), 200L, 200L);
        }

        // Metrics
        if (CouponCodes.getConfigHandler().getUseMetrics()) {
            try {
                Metrics metrics = new Metrics(this);
                CouponCodes.getModTransformer().scheduleRunnable(new CustomDataSender(metrics));
                metrics.start();
            } catch (IOException ignored) {
            }
        }

        //Updater
        if (CouponCodes.getConfigHandler().getAutoUpdate()) {
            new Updater(this, 53833, this.getFile(), Updater.UpdateType.DEFAULT, false);
        }
    }

    private boolean setupVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        try {
            RegisteredServiceProvider<Economy> ep = getServer().getServicesManager().getRegistration(Economy.class);
            RegisteredServiceProvider<Permission> pe = getServer().getServicesManager().getRegistration(Permission.class);
            if (ep == null || pe == null) {
                return false;
            } else {
                econ = ep.getProvider();
                return true;
            }
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }

    public Player wrapPlayer(org.bukkit.entity.Player player) {
        return CouponCodes.getModTransformer().getPlayer(player.getUniqueId().toString());
    }

}