package com.github.drepic26.couponcodes.bukkit.coupon;

import com.github.drepic26.couponcodes.bukkit.BukkitPlugin;
import com.github.drepic26.couponcodes.bukkit.database.SQLDatabaseHandler;
import com.github.drepic26.couponcodes.core.coupon.CouponHandler;

public class BukkitCouponHandler extends CouponHandler {

	private BukkitPlugin plugin;
	private SQLDatabaseHandler databaseHandler;

	public BukkitCouponHandler(BukkitPlugin plugin, SQLDatabaseHandler databaseHandler) {
		this.plugin = plugin;
		this.databaseHandler = databaseHandler;

	}

}
