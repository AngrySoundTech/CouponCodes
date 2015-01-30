package com.github.drepic26.couponcodes.canary;

import java.io.IOException;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.canary.config.CanaryConfigHandler;
import com.github.drepic26.couponcodes.canary.coupon.CanaryCouponHandler;
import com.github.drepic26.couponcodes.canary.database.CanaryDatabaseHandler;
import com.github.drepic26.couponcodes.canary.listeners.CanaryListener;
import com.github.drepic26.couponcodes.canary.metrics.CustomDataSender;
import com.github.drepic26.couponcodes.canary.metrics.Metrics;
import com.github.drepic26.couponcodes.canary.permission.CanaryPermissionHandler;
import com.github.drepic26.couponcodes.core.commands.SimpleCommandHandler;
import com.github.drepic26.couponcodes.core.event.SimpleEventHandler;

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
