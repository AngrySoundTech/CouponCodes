package net.Drepic.CouponCodes.runnable;

import java.sql.SQLException;

import org.bukkit.Bukkit;

import net.Drepic.CouponCodes.CouponCodes;
import net.Drepic.CouponCodes.api.CouponManager;
import net.Drepic.CouponCodes.misc.Metrics;
import net.Drepic.CouponCodes.misc.Metrics.Graph;
import net.Drepic.CouponCodes.sql.options.MySQLOptions;
import net.Drepic.CouponCodes.sql.options.SQLiteOptions;

public class CustomDataSender implements Runnable {

	@SuppressWarnings("unused")
	private CouponCodes plugin;
	private Metrics mt;
	private CouponManager cm;
	
	public CustomDataSender(CouponCodes plugin, Metrics mt) {
		this.plugin = plugin;
		this.mt = mt;
		this.cm = CouponCodes.getCouponManager();
	}
	
	@Override
	public void run() {
		try {
		Graph couponGraph = mt.createGraph("Number of coupons");
		Graph sqlGraph = mt.createGraph("SQL Type");
		
		couponGraph.addPlotter(new Metrics.Plotter("Total Coupons") {
			
			@Override
			public int getValue() {
				try {
					return cm.getCoupons().size();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		
		couponGraph.addPlotter(new Metrics.Plotter("Item Coupons") {
			
			@Override
			public int getValue() {
				try {
					return cm.getAmountOf("Item");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		
		couponGraph.addPlotter(new Metrics.Plotter("Economy Coupons") {
			
			@Override
			public int getValue() {
				try {
					return cm.getAmountOf("Economy");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		
		couponGraph.addPlotter(new Metrics.Plotter("Rank Coupons") {
			
			@Override
			public int getValue() {
				try {
					Bukkit.broadcastMessage("SENT " + cm.getAmountOf("Rank"));
					return cm.getAmountOf("Rank");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		
		couponGraph.addPlotter(new Metrics.Plotter("Xp Coupons") {
			
			@Override
			public int getValue() {
				try {
					return cm.getAmountOf("Xp");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
		
		sqlGraph.addPlotter(new Metrics.Plotter("MySQL Users") {
			
			@Override
			public int getValue() {
				if (cm.getSQL().getDatabaseOptions() instanceof MySQLOptions)
					return 1;
				else
					return 0;
			}
		});
		
		sqlGraph.addPlotter(new Metrics.Plotter("SQLite Users") {
			
			@Override
			public int getValue() {
				if (cm.getSQL().getDatabaseOptions() instanceof SQLiteOptions)
					return 1;
				else
					return 0;
			}
		});
	
		mt.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
