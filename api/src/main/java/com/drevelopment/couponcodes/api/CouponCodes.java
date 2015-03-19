/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (Drepic26)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.drevelopment.couponcodes.api;

import com.drevelopment.couponcodes.api.command.CommandHandler;
import com.drevelopment.couponcodes.api.config.ConfigHandler;
import com.drevelopment.couponcodes.api.coupon.CouponHandler;
import com.drevelopment.couponcodes.api.database.DatabaseHandler;
import com.drevelopment.couponcodes.api.economy.EconomyHandler;
import com.drevelopment.couponcodes.api.event.EventHandler;
import com.drevelopment.couponcodes.api.permission.PermissionHandler;

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

	public static PermissionHandler getPermissionHandler() {
		return permissionHandler;
	}

	public static EconomyHandler getEconomyHandler() {
		return economyHandler;
	}

	public static CouponHandler getCouponHandler() {
		return couponHandler;
	}

	public static ConfigHandler getConfigHandler() {
		return configHandler;
	}

	public static EventHandler getEventHandler() {
		return eventHandler;
	}

	public static CommandHandler getCommandHandler() {
		return commandHandler;
	}

	public static DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public static void setModTransformer(ModTransformer mt) {
		modTransformer = mt;
	}

	public static void setPermissionHandler(PermissionHandler ph) {
		permissionHandler = ph;
	}

	public static void setEconomyHandler(EconomyHandler eh) {
		economyHandler = eh;
	}

	public static void setCouponHandler(CouponHandler ch) {
		couponHandler = ch;
	}

	public static void setConfigHandler(ConfigHandler ch) {
		configHandler = ch;
	}

	public static void setEventHandler(EventHandler eh) {
		eventHandler = eh;
	}

	public static void setCommandHandler(CommandHandler ch) {
		commandHandler = ch;
	}

	public static void setDatabaseHandler(DatabaseHandler dh) {
		databaseHandler = dh;
	}

}
