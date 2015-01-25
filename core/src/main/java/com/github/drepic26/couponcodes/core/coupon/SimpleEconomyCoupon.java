package com.github.drepic26.couponcodes.core.coupon;

import java.util.HashMap;

import com.github.drepic26.couponcodes.api.coupon.EconomyCoupon;

public class SimpleEconomyCoupon extends SimpleCoupon implements EconomyCoupon {

	private int money;

	public SimpleEconomyCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, int money) {
		super(name, usetimes, time, usedplayers);
		this.money = money;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
