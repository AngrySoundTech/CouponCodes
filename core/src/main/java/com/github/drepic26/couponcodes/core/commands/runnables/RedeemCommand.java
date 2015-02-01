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
import com.github.drepic26.couponcodes.core.util.LocaleHandler;

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

			if (!(sender instanceof Player)) {
				sender.sendMessage(LocaleHandler.getString("Command.Redeem.NotPlayer"));
				return;
			}

			if (coupon == null) {
				sender.sendMessage(LocaleHandler.getString("Command.Shared.DoesNotExist"));
				return;
			}
			if (coupon.getUseTimes() < 1) {
					sender.sendMessage(LocaleHandler.getString("Command.Redeem.UsedUp"));
				return;
			}
			if (coupon.getUsedPlayers() != null) {
				if (!coupon.getUsedPlayers().isEmpty()) {
					if (coupon.getUsedPlayers().containsKey(((Player)sender).getUUID())) {
						if (coupon.getUsedPlayers().get(((Player)sender).getUUID())) {
							sender.sendMessage(LocaleHandler.getString("Command.Redeem.AlreadyUsed"));
							return;
						}
					}
				}
			}
			if (coupon.getTime() == 0) {
				sender.sendMessage(LocaleHandler.getString("Command.Redeem.OutOfTime"));
				return;
			}
			if (coupon.isExpired()) {
				sender.sendMessage(LocaleHandler.getString("Command.Redeem.Expired"));
				return;
			}

			if (coupon instanceof ItemCoupon) {
				ItemCoupon c = (ItemCoupon) coupon;
				for (Map.Entry<Integer, Integer> en : c.getIDs().entrySet()) {
					((Player) sender).giveItem(en.getKey(), en.getValue());
				}
				sender.sendMessage(LocaleHandler.getString("Command.Redeem.RedeemItem", c.getName()));
			} else

			if (coupon instanceof EconomyCoupon) {
				if (CouponCodes.getEconomyHandler() == null) {
					sender.sendMessage(LocaleHandler.getString("Command.Redeem.EconDisabled"));
					return;
				} else {
					EconomyCoupon c = (EconomyCoupon) coupon;
					CouponCodes.getEconomyHandler().giveMoney(((Player)sender).getUUID(), c.getMoney());
					sender.sendMessage(LocaleHandler.getString("Command.Redeem.RedeemEcon", c.getName()));
				}
			} else

			if (coupon instanceof RankCoupon) {
				if (!CouponCodes.getPermissionHandler().groupSupport()) {
					sender.sendMessage(LocaleHandler.getString("Command.Redeem.RankDisabled"));
					return;
				} else {
					RankCoupon c = (RankCoupon) coupon;
					CouponCodes.getPermissionHandler().setPlayerGroup((Player)sender, c.getGroup());
					sender.sendMessage(LocaleHandler.getString("Command.Redeem.RedeemRank", c.getName(), c.getGroup()));
				}
			} else

			if (coupon instanceof XpCoupon) {
				XpCoupon c = (XpCoupon) coupon;
				((Player) sender).setLevel(((Player)sender).getLevel()+c.getXp());
				sender.sendMessage(LocaleHandler.getString("Command.Redeem.RedeemXp", c.getName(), c.getXp()));
			}

			HashMap<String, Boolean> up = coupon.getUsedPlayers();
			up.put(((Player)sender).getUUID(), true);
			coupon.setUsedPlayers(up);
			coupon.setUseTimes(coupon.getUseTimes()-1);
			CouponCodes.getCouponHandler().updateCoupon(coupon);
			return;
		} else {
			sender.sendMessage(LocaleHandler.getString("Command.Help.Redeem"));
			return;
		}
	}
}
