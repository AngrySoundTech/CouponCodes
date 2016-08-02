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
package tech.feldman.couponcodes.api;

import tech.feldman.couponcodes.api.command.CommandHandler;
import tech.feldman.couponcodes.api.config.ConfigHandler;
import tech.feldman.couponcodes.api.coupon.CouponHandler;
import tech.feldman.couponcodes.api.database.DatabaseHandler;
import tech.feldman.couponcodes.api.economy.EconomyHandler;
import tech.feldman.couponcodes.api.event.EventHandler;
import tech.feldman.couponcodes.api.permission.PermissionHandler;

public class CouponCodes {

    private static ModTransformer modTransformer;
    private static PermissionHandler permissionHandler;
    private static EconomyHandler economyHandler;
    private static CouponHandler couponHandler;
    private static ConfigHandler configHandler;
    private static EventHandler eventHandler;
    private static CommandHandler commandHandler;
    private static DatabaseHandler databaseHandler;

    public static ModTransformer getModTransformer() {
        return modTransformer;
    }

    public static void setModTransformer(ModTransformer mt) {
        modTransformer = mt;
    }

    public static PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }

    public static void setPermissionHandler(PermissionHandler ph) {
        permissionHandler = ph;
    }

    public static EconomyHandler getEconomyHandler() {
        return economyHandler;
    }

    public static void setEconomyHandler(EconomyHandler eh) {
        economyHandler = eh;
    }

    public static CouponHandler getCouponHandler() {
        return couponHandler;
    }

    public static void setCouponHandler(CouponHandler ch) {
        couponHandler = ch;
    }

    public static ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public static void setConfigHandler(ConfigHandler ch) {
        configHandler = ch;
    }

    public static EventHandler getEventHandler() {
        return eventHandler;
    }

    public static void setEventHandler(EventHandler eh) {
        eventHandler = eh;
    }

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public static void setCommandHandler(CommandHandler ch) {
        commandHandler = ch;
    }

    public static DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }

    public static void setDatabaseHandler(DatabaseHandler dh) {
        databaseHandler = dh;
    }

}
