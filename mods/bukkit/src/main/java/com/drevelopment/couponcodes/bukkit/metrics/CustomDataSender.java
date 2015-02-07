package com.drevelopment.couponcodes.bukkit.metrics;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.coupon.CouponHandler;
import com.drevelopment.couponcodes.bukkit.metrics.Metrics.Graph;

public class CustomDataSender implements Runnable {

	private Metrics metrics;
	private CouponHandler ch;

	public CustomDataSender(Metrics metrics) {
		this.metrics = metrics;
		this.ch = CouponCodes.getCouponHandler();
	}

	@Override
	public void run() {
		// Coupon Types
	    Graph couponTypesGraph = metrics.createGraph("Coupon Types");
	    couponTypesGraph.addPlotter(new Metrics.Plotter("Item Coupons") {
			@Override
			public int getValue() {
				return ch.getAmountOf("Item");
			}
		});
	    couponTypesGraph.addPlotter(new Metrics.Plotter("Economy Coupons") {
			@Override
			public int getValue() {
				return ch.getAmountOf("Economy");
			}
		});
	    couponTypesGraph.addPlotter(new Metrics.Plotter("Rank Coupons") {
			@Override
			public int getValue() {
				return ch.getAmountOf("Rank");
			}
		});
	    couponTypesGraph.addPlotter(new Metrics.Plotter("Xp Coupons") {
			@Override
			public int getValue() {
				return ch.getAmountOf("Xp");
			}
		});
	    // Database types
	    Graph dbTypesGraph = metrics.createGraph("Database Type");
	    dbTypesGraph.addPlotter(new Metrics.Plotter(CouponCodes.getDatabaseHandler().getDatabaseType()) {
			@Override
			public int getValue() {
				return 1;
			}
		});
	}
}
