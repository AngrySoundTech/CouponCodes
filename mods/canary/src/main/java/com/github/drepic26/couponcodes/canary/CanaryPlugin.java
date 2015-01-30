package com.github.drepic26.couponcodes.canary;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.canary.config.CanaryConfigHandler;
import com.github.drepic26.couponcodes.canary.coupon.CanaryCouponHandler;
import com.github.drepic26.couponcodes.core.commands.SimpleCommandHandler;
import com.github.drepic26.couponcodes.core.event.SimpleEventHandler;

import net.canarymod.logger.Logman;
import net.canarymod.plugin.Plugin;

public class CanaryPlugin extends Plugin {

	private Logman logger;

	@Override
	public boolean enable() {
		logger = this.getLogman();

		CouponCodes.setCouponHandler(new CanaryCouponHandler());
		CouponCodes.setEventHandler(new SimpleEventHandler());
		CouponCodes.setCommandHandler(new SimpleCommandHandler());
		CouponCodes.setModTransformer(new CanaryModTransformer(this));
		CouponCodes.setConfigHandler(new CanaryConfigHandler(this));

		return true;
	}

	@Override
	public void disable() {
		CouponCodes.setModTransformer(null);
	}

}
