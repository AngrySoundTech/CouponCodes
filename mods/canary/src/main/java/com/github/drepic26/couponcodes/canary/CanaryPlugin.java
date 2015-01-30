package com.github.drepic26.couponcodes.canary;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.canary.config.CanaryConfigHandler;
import com.github.drepic26.couponcodes.canary.coupon.CanaryCouponHandler;
import com.github.drepic26.couponcodes.canary.database.CanaryDatabaseHandler;
import com.github.drepic26.couponcodes.canary.listeners.CanaryListener;
import com.github.drepic26.couponcodes.core.commands.SimpleCommandHandler;
import com.github.drepic26.couponcodes.core.event.SimpleEventHandler;

import net.canarymod.Canary;
import net.canarymod.commandsys.CanaryCommand;
import net.canarymod.commandsys.CommandDependencyException;
import net.canarymod.commandsys.CommandOwner;
import net.canarymod.logger.Logman;
import net.canarymod.plugin.Plugin;

public class CanaryPlugin extends Plugin {

	private Logman logger;

	@Override
	public boolean enable() {
		logger = this.getLogman();

		CouponCodes.setEventHandler(new SimpleEventHandler());
		CouponCodes.setCommandHandler(new SimpleCommandHandler());
		CouponCodes.setModTransformer(new CanaryModTransformer(this));
		CouponCodes.setConfigHandler(new CanaryConfigHandler(this));

		CouponCodes.setDatabaseHandler(new CanaryDatabaseHandler());
		CouponCodes.setCouponHandler(new CanaryCouponHandler());

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
