package com.drevelopment.couponcodes.core.coupon;

import java.util.HashMap;
import java.util.Map;

import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.api.coupon.CouponHandler;

public abstract class SimpleCouponHandler implements CouponHandler {

	public abstract HashMap<Integer, Integer> itemStringToHash(String args, CommandSender sender);

	public String itemHashToString(HashMap<Integer, Integer> hash) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, Integer> en : hash.entrySet()) {
			sb.append(en.getKey()+":"+en.getValue()+",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	public String playerHashToString(HashMap<String, Boolean> hash) {
		if (hash.isEmpty() || hash == null || hash.size() < 1) return "";
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Boolean> en : hash.entrySet()) {
			sb.append(en.getKey()+":"+en.getValue()+",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	public HashMap<String, Boolean> playerStringToHash(String args) {
		HashMap<String, Boolean> pl = new HashMap<String, Boolean>();
		if (args.equals(null) || args.length() < 1) return pl;
		String[] sp = args.split(",");
		try {
			for (int i = 0; i < sp.length; i++) {
				String a = String.valueOf(sp[i].split(":")[0]);
				Boolean b = Boolean.valueOf(sp[i].split(":")[1]);
				pl.put(a, b);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return pl;
	}

}
