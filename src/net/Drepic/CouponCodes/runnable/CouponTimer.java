package net.Drepic.CouponCodes.runnable;

import java.sql.SQLException;
import java.util.ArrayList;

import net.Drepic.CouponCodes.CouponCodes;
import net.Drepic.CouponCodes.api.CouponManager;
import net.Drepic.CouponCodes.api.coupon.Coupon;
import net.Drepic.CouponCodes.api.events.EventHandle;
import net.Drepic.CouponCodes.sql.SQL;
import net.Drepic.CouponCodes.sql.options.MySQLOptions;

public class CouponTimer implements Runnable {
	
	private CouponManager cm;
	
	private ArrayList<String> cl;
	private Coupon c;
	
	public CouponTimer() {
		if (CouponCodes.getCouponManager().getSQL().getDatabaseOptions() instanceof MySQLOptions) {
			cm = new CouponManager(CouponCodes.getInstance(), new SQL(CouponCodes.getInstance(), CouponCodes.getCouponManager().getSQL().getDatabaseOptions()));
			try {
				cm.getSQL().open();
			} catch (SQLException e) {
				cm = CouponCodes.getCouponManager();
			}
		} else {
			cm = CouponCodes.getCouponManager();
		}
		cl = new ArrayList<String>();
	}
	
	@Override
	public void run() {
		try {
			cl = cm.getCoupons();
			if (cl == null) return;
			
			for (String name : cl) {
				if (cm.getSQL().getConnection().isClosed()) return;
				c = cm.getBasicCoupon(name);
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
				cm.updateCouponTime(c);
				EventHandle.callCouponTimeChangeEvent(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
		}
	}
}
