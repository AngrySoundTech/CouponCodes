package com.drevelopment.couponcodes.canary;

import java.io.IOException;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.canary.config.CanaryConfigHandler;
import com.drevelopment.couponcodes.canary.coupon.CanaryCouponHandler;
import com.drevelopment.couponcodes.canary.coupon.CanaryCouponTimer;
import com.drevelopment.couponcodes.canary.database.CanaryDatabaseHandler;
import com.drevelopment.couponcodes.canary.listeners.CanaryListener;
import com.drevelopment.couponcodes.canary.metrics.CustomDataSender;
import com.drevelopment.couponcodes.canary.metrics.Metrics;
import com.drevelopment.couponcodes.canary.permission.CanaryPermissionHandler;
import com.drevelopment.couponcodes.core.commands.SimpleCommandHandler;
import com.drevelopment.couponcodes.core.event.SimpleEventHandler;

import net.canarymod.Canary;
import net.canarymod.commandsys.CommandDependencyException;
import net.canarymod.logger.Logman;
import net.canarymod.plugin.Plugin;

public class CanaryPlugin extends Plugin {

	private Logman logger;
	private Metrics metrics;

	@Override
	public boolean enable() {
		logger = this.getLogman();

		CouponCodes.setEventHandler(new SimpleEventHandler());
		CouponCodes.setCommandHandler(new SimpleCommandHandler());
		CouponCodes.setModTransformer(new CanaryModTransformer(this));
		CouponCodes.setConfigHandler(new CanaryConfigHandler(this));

		CouponCodes.setDatabaseHandler(new CanaryDatabaseHandler());
		CouponCodes.setCouponHandler(new CanaryCouponHandler());

		CouponCodes.setPermissionHandler(new CanaryPermissionHandler());

		if (CouponCodes.getConfigHandler().getUseThread()) {
			Canary.getServer().addSynchronousTask(new CanaryCouponTimer(this, 200L));
		}

		if (CouponCodes.getConfigHandler().getUseMetrics()) {
			try {
				metrics = new Metrics(this);
				CouponCodes.getModTransformer().scheduleRunnable(new CustomDataSender(metrics));
				metrics.start();
			} catch (IOException e) {}
		}

		try {
			Canary.commands().registerCommands(new CanaryListener(), this, false);
		} catch (CommandDependencyException e) {
			return false;
		}

		return true;
	}

	@Override
	public void disable() {
		CouponCodes.setModTransformer(null);
	}

}
