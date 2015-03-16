package com.drevelopment.couponcodes.core.coupon;

import java.util.HashMap;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.coupon.CommandCoupon;
import com.drevelopment.couponcodes.api.coupon.Coupon;
import com.drevelopment.couponcodes.api.coupon.EconomyCoupon;
import com.drevelopment.couponcodes.api.coupon.ItemCoupon;
import com.drevelopment.couponcodes.api.coupon.RankCoupon;
import com.drevelopment.couponcodes.api.coupon.XpCoupon;
import com.drevelopment.couponcodes.api.event.coupon.CouponAddToDatabaseEvent;
import com.drevelopment.couponcodes.api.event.coupon.CouponExpireEvent;
import com.drevelopment.couponcodes.api.event.coupon.CouponRemoveFromDatabaseEvent;

public class SimpleCoupon implements Coupon {

	private String name;
	private int usetimes;
	private int time;
	private boolean expired;
	private HashMap<String, Boolean> usedplayers;

	public SimpleCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers) {
		this.name = name;
		this.usetimes = usetimes;
		this.time = time;
		this.usedplayers = usedplayers;
		this.expired = (usetimes <= 0 || time == 0);
	}

	public boolean addToDatabase() {
		if (CouponCodes.getCouponHandler().addCouponToDatabase(this)) {
			CouponCodes.getEventHandler().post(new CouponAddToDatabaseEvent(this));
			return true;
		}
		return false;
	}

	public boolean removeFromDatabase() {
		if (CouponCodes.getCouponHandler().removeCouponFromDatabase(this)) {
			CouponCodes.getEventHandler().post(new CouponRemoveFromDatabaseEvent(this));
			return true;
		}
		return false;
	}

	public boolean isInDatabase() {
		return CouponCodes.getCouponHandler().couponExists(this);
	}

	public void updateWithDatabase() {
		CouponCodes.getCouponHandler().updateCoupon(this);
	}

	public void updateTimeWithDatabase() {
		CouponCodes.getCouponHandler().updateCouponTime(this);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUseTimes() {
		return usetimes;
	}

	public void setUseTimes(int usetimes) {
		this.usetimes = usetimes;
		if (this.usetimes <= 0)
			this.setExpired(true);
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
		if (this.time == 0)
			this.setExpired(true);
	}

	public HashMap<String, Boolean> getUsedPlayers() {
		return usedplayers;
	}

	public void setUsedPlayers(HashMap<String, Boolean> usedplayers) {
		this.usedplayers = usedplayers;
	}

	public String getType() {
		if (this instanceof ItemCoupon) return "Item";
		if (this instanceof EconomyCoupon) return "Economy";
		if (this instanceof RankCoupon) return "Rank";
		if (this instanceof XpCoupon) return "Xp";
		if (this instanceof CommandCoupon) return "Command";
		else
			return null;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
		if (expired) CouponCodes.getEventHandler().post(new CouponExpireEvent(this));
	}
}
