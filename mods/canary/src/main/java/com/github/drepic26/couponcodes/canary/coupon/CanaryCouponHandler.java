package com.github.drepic26.couponcodes.canary.coupon;

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

import com.github.drepic26.couponcodes.api.command.CommandSender;
import com.github.drepic26.couponcodes.api.coupon.Coupon;
import com.github.drepic26.couponcodes.api.coupon.EconomyCoupon;
import com.github.drepic26.couponcodes.api.coupon.ItemCoupon;
import com.github.drepic26.couponcodes.api.coupon.RankCoupon;
import com.github.drepic26.couponcodes.api.coupon.XpCoupon;
import com.github.drepic26.couponcodes.canary.database.CanaryDataAccess;
import com.github.drepic26.couponcodes.core.coupon.SimpleCouponHandler;
import com.github.drepic26.couponcodes.core.coupon.SimpleEconomyCoupon;
import com.github.drepic26.couponcodes.core.coupon.SimpleItemCoupon;
import com.github.drepic26.couponcodes.core.coupon.SimpleRankCoupon;
import com.github.drepic26.couponcodes.core.coupon.SimpleXpCoupon;

public class CanaryCouponHandler extends SimpleCouponHandler {

	@Override
	public boolean addCouponToDatabase(Coupon coupon) {
		if (couponExists(coupon)) return false;
			CanaryDataAccess da = new CanaryDataAccess();
			da.name = coupon.getName();
			da.timeuse = coupon.getUseTimes();
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

	@Override
	public boolean couponExists(Coupon coupon) {
		return getCoupons().contains(coupon.getName());
	}

	@Override
	public boolean couponExists(String coupon) {
		return getCoupons().contains(coupon);
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
		da.timeuse = coupon.getUseTimes();
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
		else
			return null;
	}

	@Override
	public int getAmountOf(String type) {
		ArrayList<String> list = getCoupons();
		int item = 0;
		int econ = 0;
		int rank = 0;
		int xp = 0;

		for (String name : list) {
			Coupon c = getBasicCoupon(name);
			if (c instanceof ItemCoupon) item++;
			if (c instanceof EconomyCoupon) econ++;
			if (c instanceof RankCoupon) rank++;
			if (c instanceof XpCoupon) xp++;
		}

		if (type.equalsIgnoreCase("Item"))
			return item;
		else if (type.equalsIgnoreCase("Economy"))
			return econ;
		else if (type.equalsIgnoreCase("Rank"))
			return rank;
		else if (type.equalsIgnoreCase("Xp"))
			return xp;
		else
			return 0;
	}

	@Override
	public ItemCoupon createNewItemCoupon(String name, int usetimes, int time, HashMap<Integer, Integer> ids, HashMap<String, Boolean> usedplayers) {
		return new SimpleItemCoupon(name, usetimes, time, usedplayers, ids);
	}

	@Override
	public EconomyCoupon createNewEconomyCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, int money) {
		return new SimpleEconomyCoupon(name, usetimes, time, usedplayers, money);
	}

	@Override
	public RankCoupon createNewRankCoupon(String name, String group, int usetimes, int time, HashMap<String, Boolean> usedplayers) {
		return new SimpleRankCoupon(name, group, usetimes, time, usedplayers);
	}

	@Override
	public XpCoupon createNewXpCoupon(String name, int xp, int usetimes, int time, HashMap<String, Boolean> usedplayers) {
		return new SimpleXpCoupon(name, usetimes, time, usedplayers, xp);
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
