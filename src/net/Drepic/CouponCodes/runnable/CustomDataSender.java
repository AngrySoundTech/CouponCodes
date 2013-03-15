package net.Drepic.CouponCodes.runnable;

import java.io.IOException;
import java.sql.SQLException;

import net.Drepic.CouponCodes.CouponCodes;
import net.Drepic.CouponCodes.api.CouponManager;
import net.Drepic.CouponCodes.misc.Metrics;
import net.Drepic.CouponCodes.sql.options.MySQLOptions;
import net.Drepic.CouponCodes.sql.options.SQLiteOptions;

public class CustomDataSender implements Runnable {

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
		plugin.debug("Beginning Custom data sending");
		mt.addCustomData(plugin, new Metrics.Plotter() {
			
			@Override
			public int getValue() {
				try {
					return cm.getCoupons().size();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
			
			@Override
			public String getColumnName() {
				return "Total Coupons";
			}
		});
		
		mt.addCustomData(plugin, new Metrics.Plotter() {
			
			@Override
			public int getValue() {
				try {
					return cm.getAmountOf("Item");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
			
			@Override
			public String getColumnName() {
				return "Item Coupons";
			}
		});
		
		mt.addCustomData(plugin, new Metrics.Plotter() {
			
			@Override
			public int getValue() {
				try {
					return cm.getAmountOf("Economy");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
			
			@Override
			public String getColumnName() {
				return "Economy Coupons";
			}
		});
		
		mt.addCustomData(plugin, new Metrics.Plotter() {
			
			@Override
			public int getValue() {
				try {
					return cm.getAmountOf("Rank");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
			
			@Override
			public String getColumnName() {
				return "Rank Coupons";
			}
		});
		
		mt.addCustomData(plugin, new Metrics.Plotter() {
			
			@Override
			public int getValue() {
				try {
					return cm.getAmountOf("Xp");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
			
			@Override
			public String getColumnName() {
				return "Xp Coupons";
			}
		});
		
		mt.addCustomData(plugin, new Metrics.Plotter() {
			
			@Override
			public int getValue() {
				if (cm.getSQL().getDatabaseOptions() instanceof MySQLOptions)
					return 1;
				else
					return 0;
			}
			
			@Override
			public String getColumnName() {
				return ("MySQL Users");
			}
		});
		
		mt.addCustomData(plugin, new Metrics.Plotter() {
			
			@Override
			public int getValue() {
				if (cm.getSQL().getDatabaseOptions() instanceof SQLiteOptions)
					return 1;
				else
					return 0;
			}
			
			@Override
			public String getColumnName() {
				return ("SQLite Users");
			}
		});
		
		try {
			mt.beginMeasuringPlugin(plugin);
		} catch (IOException e) {
			plugin.sendErr("Could not measure plugin");
			e.printStackTrace();
		}
		
		plugin.debug("End of custom data sending");
	}
}
