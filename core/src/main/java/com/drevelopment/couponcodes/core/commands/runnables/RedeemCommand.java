/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (Drepic26)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.drevelopment.couponcodes.core.commands.runnables;

import java.util.HashMap;
import java.util.Map;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.api.coupon.CommandCoupon;
import com.drevelopment.couponcodes.api.coupon.Coupon;
import com.drevelopment.couponcodes.api.coupon.EconomyCoupon;
import com.drevelopment.couponcodes.api.coupon.ItemCoupon;
import com.drevelopment.couponcodes.api.coupon.RankCoupon;
import com.drevelopment.couponcodes.api.coupon.XpCoupon;
import com.drevelopment.couponcodes.api.entity.Player;
import com.drevelopment.couponcodes.core.util.LocaleHandler;

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
			} else

			if (coupon instanceof CommandCoupon) {
				CommandCoupon c = (CommandCoupon) coupon;
				CouponCodes.getModTransformer().runCommand(null, c.getCmd().replace("%p", ((Player) sender).getName()));
				sender.sendMessage(LocaleHandler.getString("Command.Redeem.RedeemCmd", c.getName()));
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
