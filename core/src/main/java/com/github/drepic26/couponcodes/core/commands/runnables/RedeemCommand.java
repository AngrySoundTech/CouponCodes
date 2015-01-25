package com.github.drepic26.couponcodes.core.commands.runnables;

import java.util.HashMap;
import java.util.Map;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.command.CommandSender;
import com.github.drepic26.couponcodes.api.coupon.Coupon;
import com.github.drepic26.couponcodes.api.coupon.EconomyCoupon;
import com.github.drepic26.couponcodes.api.coupon.ItemCoupon;
import com.github.drepic26.couponcodes.api.coupon.RankCoupon;
import com.github.drepic26.couponcodes.api.coupon.XpCoupon;
import com.github.drepic26.couponcodes.api.entity.Player;
import com.github.drepic26.couponcodes.core.commands.CommandUsage;
import com.github.drepic26.couponcodes.core.util.Color;

public class RedeemCommand implements Runnable{

	private CommandSender sender;
	private String[] args;

	public RedeemCommand(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}

	@Override
	public void run() {
		if (args.length == 2) {
			Coupon coupon = CouponCodes.getCouponHandler().getCoupon(args[1]);

			if (coupon == null) {
				sender.sendMessage(Color.RED+"That coupon doesn't exist!");
				return;
			}
				if (coupon.getUseTimes() < 1) {
				sender.sendMessage(Color.RED+"This coupon has been used up!");
				return;
			}
			if (coupon.getUsedPlayers() != null) {
				if (!coupon.getUsedPlayers().isEmpty()) {
					if (coupon.getUsedPlayers().containsKey(sender.getUUID())) {
						if (coupon.getUsedPlayers().get(sender.getUUID())) {
							sender.sendMessage(Color.RED+"You have already used this coupon");
							return;
						}
					}
				}
			}

			if (coupon.getTime() == 0) {
				sender.sendMessage(Color.RED+"This coupon has ran out of time to be redeemed!");
				return;
			}

			if (coupon.isExpired()) {
				sender.sendMessage(Color.RED+"This coupon has expired!");
				return;
			}

			if (coupon instanceof ItemCoupon) {
				ItemCoupon c = (ItemCoupon) coupon;
				for (Map.Entry<Integer, Integer> en : c.getIDs().entrySet()) {
					((Player) sender).giveItem(en.getKey(), en.getValue());
				}
				sender.sendMessage(Color.GREEN+"Coupon "+Color.GOLD+c.getName()+Color.GREEN+" has been redeemed, and the items added to your inventory!");
			} else

			if (coupon instanceof EconomyCoupon) {
				if (CouponCodes.getEconomyHandler() == null) {
					sender.sendMessage(Color.DARK_RED+"Economy support is currently disabled. You cannot redeem an economy coupon.");
					return;
				} else {
					EconomyCoupon c = (EconomyCoupon) coupon;
					CouponCodes.getEconomyHandler().giveMoney(sender.getUUID(), c.getMoney());
					sender.sendMessage(Color.GREEN+"Coupon "+Color.GOLD+c.getName()+Color.GREEN+" has been redeemed, and the money added to your account!");
				}
			} else

			if (coupon instanceof RankCoupon) {
				if (CouponCodes.getEconomyHandler() == null) {
					sender.sendMessage(Color.DARK_RED+"Economy support is currently disabled. You cannot redeem a rank coupon.");
					return;
				} else {
					RankCoupon c = (RankCoupon) coupon;
					CouponCodes.getEconomyHandler().setPlayerGroup((Player)sender, c.getGroup());
					sender.sendMessage(Color.GREEN+"Coupon "+Color.GOLD+c.getName()+Color.GREEN+" has been redeemed, and your group has been set to "+Color.GOLD+c.getGroup());
				}
			} else

			if (coupon instanceof XpCoupon) {
				XpCoupon c = (XpCoupon) coupon;
				((Player) sender).setLevel(((Player)sender).getLevel()+c.getXp());
				sender.sendMessage(Color.GREEN+"Coupon "+Color.GOLD+c.getName()+Color.GREEN+" has been redeemed, and you have received "+Color.GOLD+c.getXp()+Color.GREEN+" XP levels!");
			}

			HashMap<String, Boolean> up = coupon.getUsedPlayers();
			up.put(sender.getUUID(), true);
			coupon.setUsedPlayers(up);
			coupon.setUseTimes(coupon.getUseTimes()-1);
			CouponCodes.getCouponHandler().updateCoupon(coupon);
			return;
		} else {
			sender.sendMessage(CommandUsage.C_REDEEM.toString());
			return;
		}
	}
}
