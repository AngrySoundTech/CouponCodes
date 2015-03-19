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
package com.drevelopment.couponcodes.canary.coupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import net.canarymod.Canary;
import net.canarymod.database.DataAccess;
import net.canarymod.database.Database;
import net.canarymod.database.exceptions.DatabaseReadException;
import net.canarymod.database.exceptions.DatabaseWriteException;

import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.api.coupon.CommandCoupon;
import com.drevelopment.couponcodes.api.coupon.Coupon;
import com.drevelopment.couponcodes.api.coupon.EconomyCoupon;
import com.drevelopment.couponcodes.api.coupon.ItemCoupon;
import com.drevelopment.couponcodes.api.coupon.RankCoupon;
import com.drevelopment.couponcodes.api.coupon.XpCoupon;
import com.drevelopment.couponcodes.canary.database.CanaryDataAccess;
import com.drevelopment.couponcodes.core.coupon.SimpleCouponHandler;

public class CanaryCouponHandler extends SimpleCouponHandler {

	@Override
	public boolean addCouponToDatabase(Coupon coupon) {
		if (couponExists(coupon)) return false;
			CanaryDataAccess da = new CanaryDataAccess();
			da.name = coupon.getName();
			da.usetimes = coupon.getUseTimes();
			da.usedplayers = playerHashToString(coupon.getUsedPlayers());
			da.ctype = coupon.getType();
			da.timeuse = coupon.getTime();

			if (coupon instanceof ItemCoupon) {
				da.ids = itemHashToString(((ItemCoupon) coupon).getIDs());
			} else
			if (coupon instanceof EconomyCoupon) {
				da.money = ((EconomyCoupon) coupon).getMoney();
			} else
			if (coupon instanceof RankCoupon) {
				da.groupname = ((RankCoupon) coupon).getGroup();
			} else
			if (coupon instanceof XpCoupon) {
				da.xp = ((XpCoupon) coupon).getXp();
			} else
			if (coupon instanceof CommandCoupon) {
				da.command = ((CommandCoupon) coupon).getCmd();
			}

			HashMap<String, Object> filter = new HashMap<String, Object>();
			filter.put("name", coupon.getName());

			try {
				Database.get().update(da, filter);
				return true;
			} catch (DatabaseWriteException e) {
				return false;
			}
	}

	@Override
	public boolean removeCouponFromDatabase(Coupon coupon) {
		if (!couponExists(coupon)) return false;
		CanaryDataAccess da = new CanaryDataAccess();
		HashMap<String,Object> filter = new HashMap<String, Object>();
		filter.put("name", coupon.getName());
		try {
			Database.get().remove(da, filter);
			return true;
		} catch (DatabaseWriteException e) {
			return false;
		}
	}

	@Override
	public boolean removeCouponFromDatabase(String coupon) {
		if (!couponExists(coupon)) return false;
		CanaryDataAccess da = new CanaryDataAccess();
		HashMap<String,Object> filter = new HashMap<String, Object>();
		filter.put("name", coupon);
		try {
			Database.get().remove(da, filter);
			return true;
		} catch (DatabaseWriteException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<String> getCoupons() {
		CanaryDataAccess da = new CanaryDataAccess();
		List<CanaryDataAccess> ds = new ArrayList<CanaryDataAccess>();
		Map<String,Object> filter = new HashMap<String, Object>();
		ArrayList<String> coupons = new ArrayList<String>();
		try {
			Database.get().loadAll(da, (List<DataAccess>)(List<?>)ds, filter);
		} catch (DatabaseReadException e) {
			return coupons;
		}
		for (CanaryDataAccess cda: ds) {
			coupons.add(cda.name);
		}
		return coupons;
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		CanaryDataAccess da = new CanaryDataAccess();
		da.name = coupon.getName();
		da.usetimes = coupon.getUseTimes();
		da.timeuse = coupon.getTime();
		da.usedplayers = playerHashToString(coupon.getUsedPlayers());
		da.ctype = coupon.getType();

		if (coupon instanceof ItemCoupon) {
			da.ids = itemHashToString(((ItemCoupon) coupon).getIDs());
		} else
		if (coupon instanceof EconomyCoupon) {
			da.money = ((EconomyCoupon) coupon).getMoney();
		} else
		if (coupon instanceof RankCoupon) {
			da.groupname = ((RankCoupon) coupon).getGroup();
		} else
		if (coupon instanceof XpCoupon) {
			da.xp = ((XpCoupon) coupon).getXp();
		}
		if (coupon instanceof CommandCoupon) {
			da.command = ((CommandCoupon) coupon).getCmd();
		}

		HashMap<String, Object> filter = new HashMap<String, Object>();
		filter.put("name", coupon.getName());
		try {
			Database.get().update(da, filter);
		} catch (DatabaseWriteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCouponTime(Coupon coupon) {
		updateCoupon(coupon);
	}

	@Override
	public Coupon getCoupon(String coupon) {
		if (!couponExists(coupon)) return null;
		CanaryDataAccess da = new CanaryDataAccess();
		HashMap<String, Object> filter = new HashMap<String, Object>();
		filter.put("name", coupon);

		try {
			Database.get().load(da, filter);
		} catch (DatabaseReadException e) {
			e.printStackTrace();
			return null;
		}

		int usetimes = da.usetimes;
		int time = da.timeuse;
		HashMap<String,Boolean> usedplayers = playerStringToHash(da.usedplayers);

		if (da.ctype.equalsIgnoreCase("Item"))
			return createNewItemCoupon(coupon, usetimes, time, itemStringToHash(da.ids, null), usedplayers);
		else if (da.ctype.equalsIgnoreCase("Economy"))
			return createNewEconomyCoupon(coupon, usetimes, time, usedplayers, da.money);
		else if (da.ctype.equalsIgnoreCase("Rank"))
			return createNewRankCoupon(coupon, da.groupname, usetimes, time, usedplayers);
		else if (da.ctype.equalsIgnoreCase("Xp"))
			return createNewXpCoupon(coupon, da.xp, usetimes, time, usedplayers);
		else if (da.ctype.equalsIgnoreCase("Command"))
			return createNewCommandCoupon(coupon, da.command, usetimes, time, usedplayers);
		else
			return null;
	}

	@Override
	public Coupon getBasicCoupon(String coupon) {
		if (!couponExists(coupon)) return null;
		CanaryDataAccess da = new CanaryDataAccess();
		HashMap<String, Object> filter = new HashMap<String, Object>();
		filter.put("name", coupon);

		try {
			Database.get().load(da, filter);
		} catch (DatabaseReadException e) {
			e.printStackTrace();
			return null;
		}

		int usetimes = da.usetimes;
		int time = da.timeuse;

		if (da.ctype.equalsIgnoreCase("Item"))
			return createNewItemCoupon(coupon, usetimes, time, null, null);
		else if (da.ctype.equalsIgnoreCase("Economy"))
			return createNewEconomyCoupon(coupon, usetimes, time, null, 0);
		else if (da.ctype.equalsIgnoreCase("Rank"))
			return createNewRankCoupon(coupon, null, usetimes, time, null);
		else if (da.ctype.equalsIgnoreCase("Xp"))
			return createNewXpCoupon(coupon, 0, usetimes, time, null);
		else if (da.ctype.equalsIgnoreCase("Command"))
			return createNewCommandCoupon(coupon, null, usetimes, time, null);
		else
			return null;
	}

	@Override
	public HashMap<Integer, Integer> itemStringToHash(String args,CommandSender sender) {
		HashMap<Integer, Integer> ids = new HashMap<Integer, Integer>();
		String[] sp = args.split(",");
		try {
			for (int i = 0; i < sp.length; i++) {
				int a = 0;
				if (NumberUtils.isNumber(sp[i].split(":")[0])) {
					a = Integer.parseInt(sp[i].split(":")[0]);
				} else {
					if (Canary.factory().getItemFactory().newItem(sp[i].split(":")[0]) != null) {
						a = Canary.factory().getItemFactory().newItem(sp[i].split(":")[0]).getId();
					} else {
						a = 1;
					}
				}
				int b = Integer.parseInt(sp[i].split(":")[1]);
					ids.put(a, b);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return ids;
	}

}
