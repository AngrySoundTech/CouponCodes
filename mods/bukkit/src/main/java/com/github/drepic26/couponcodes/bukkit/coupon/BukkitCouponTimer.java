package com.github.drepic26.couponcodes.bukkit.coupon;

import java.sql.SQLException;
import java.util.ArrayList;

import com.github.drepic26.couponcodes.bukkit.database.options.MySQLOptions;
import com.github.drepic26.couponcodes.core.ServerModTransformer;
import com.github.drepic26.couponcodes.core.coupon.Coupon;

public class BukkitCouponTimer implements Runnable {

	private BukkitCouponHandler ch;
	private ArrayList<String> cl;
	private Coupon c;

	public BukkitCouponTimer() {
		ch = (BukkitCouponHandler) ServerModTransformer.getInstance().getCouponHandler();

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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

		}
	}

}
