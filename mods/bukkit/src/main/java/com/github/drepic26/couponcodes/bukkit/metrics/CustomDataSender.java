package com.github.drepic26.couponcodes.bukkit.metrics;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.coupon.CouponHandler;
import com.github.drepic26.couponcodes.bukkit.BukkitPlugin;
import com.github.drepic26.couponcodes.bukkit.coupon.BukkitCouponHandler;
import com.github.drepic26.couponcodes.bukkit.database.options.MySQLOptions;
import com.github.drepic26.couponcodes.bukkit.database.options.SQLiteOptions;
import com.github.drepic26.couponcodes.bukkit.metrics.Metrics.Graph;
import com.github.drepic26.couponcodes.core.ServerModTransformer;

public class CustomDataSender implements Runnable {

	private BukkitPlugin plugin;
	private Metrics metrics;
	private CouponHandler ch;

	public CustomDataSender(BukkitPlugin plugin, Metrics metrics) {
		this.plugin = plugin;
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
	    // SQL Types
	    Graph sqlTypesGraph = metrics.createGraph("SQL Type");
	    sqlTypesGraph.addPlotter(new Metrics.Plotter("MySQL") {
			@Override
			public int getValue() {
				if (((BukkitCouponHandler) ch).getDatabaseHandler().getDatabaseOptions() instanceof MySQLOptions)
					return 1;
				else
					return 0;
			}
		});
	    sqlTypesGraph.addPlotter(new Metrics.Plotter("SQLite") {
			@Override
			public int getValue() {
				if (((BukkitCouponHandler) ch).getDatabaseHandler().getDatabaseOptions() instanceof SQLiteOptions)
					return 1;
				else
					return 0;
			}
		});
	}
}
