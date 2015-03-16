package com.drevelopment.couponcodes.core.coupon;

import java.util.HashMap;

import com.drevelopment.couponcodes.api.coupon.CommandCoupon;

public class SimpleCommandCoupon extends SimpleCoupon implements CommandCoupon {

	private String cmd;

	public SimpleCommandCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, String cmd) {
		super(name, usetimes, time, usedplayers);
		this.cmd = cmd;
	}

	@Override
	public String getCmd() {
		return cmd;
	}

	@Override
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

}
