package com.drevelopment.couponcodes.bukkit.coupon;

import java.sql.SQLException;
import java.util.ArrayList;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.coupon.Coupon;
import com.drevelopment.couponcodes.api.event.coupon.CouponTimeChangeEvent;
import com.drevelopment.couponcodes.bukkit.database.options.MySQLOptions;

public class BukkitCouponTimer implements Runnable {

	private BukkitCouponHandler ch;
	private ArrayList<String> cl;
	private Coupon c;

	public BukkitCouponTimer() {
		ch = (BukkitCouponHandler) CouponCodes.getCouponHandler();

		// Make sure SQL is open
		if (ch.getDatabaseHandler().getDatabaseOptions() instanceof MySQLOptions) {
			try {
				ch.getDatabaseHandler().open();
			} catch (SQLException e) {}
		}

		cl = new ArrayList<String>();
	}

	@Override
	public void run() {
		try {
			cl = ch.getCoupons();
			if (cl == null) return;

			for (String name : cl) {
				if (ch.getDatabaseHandler().getConnection().isClosed()) return;
				c = ch.getBasicCoupon(name);
				if (c == null) continue;
				if (c.isExpired() || c.getTime() == -1) continue;

				if (c.getTime()-10 < 0) {
					if (c.getTime()-5 < 0 || c.getTime()-5 == 0) {
						c.setTime(0);
					} else {
						c.setTime(5);
					}
				} else {
					c.setTime(c.getTime()-10);
				}
				ch.updateCouponTime(c);
				CouponCodes.getEventHandler().post(new CouponTimeChangeEvent(c));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
	}

}
