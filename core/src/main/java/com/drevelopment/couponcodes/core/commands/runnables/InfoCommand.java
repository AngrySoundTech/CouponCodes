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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.api.coupon.Coupon;
import com.drevelopment.couponcodes.api.coupon.EconomyCoupon;
import com.drevelopment.couponcodes.api.coupon.ItemCoupon;
import com.drevelopment.couponcodes.api.coupon.RankCoupon;
import com.drevelopment.couponcodes.api.coupon.XpCoupon;
import com.drevelopment.couponcodes.core.util.LocaleHandler;

public class InfoCommand implements Runnable {

	private CommandSender sender;
	private String[] args;

	public InfoCommand(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}

	@Override
	public void run() {
		if (args.length == 2) {
			Coupon c = CouponCodes.getCouponHandler().getCoupon(args[1]);
			if (c != null) {
				sender.sendMessage(LocaleHandler.getString("Command.Info.FancyWrap"));
				sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Header", c.getName()));
				sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Name", c.getName()));
				sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Type", c.getType()));
				sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Usetimes", c.getUseTimes()));
				if (c.getTime() != -1)
					sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.TimeLeft", c.getTime()));
				else
					sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.TimeLeft", "unlimited"));
				sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Expired", c.isExpired()));

				if (c.getUsedPlayers().isEmpty())
					sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.UsedPlayers", "None"));
				else {
					HashMap<String,Boolean> usedPlayers = c.getUsedPlayers();
					StringBuilder sb = new StringBuilder();
					for (String s : usedPlayers.keySet()) {
						if (usedPlayers.get(s)) {
							sb.append(CouponCodes.getModTransformer().getPlayerName(s)+", ");
						}
					}
					sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.UsedPlayers", sb.toString()));
				}
				if (c instanceof ItemCoupon)
					sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Items", CouponCodes.getCouponHandler().itemHashToString(((ItemCoupon) c).getIDs())));
				else if (c instanceof EconomyCoupon)
					sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Money", ((EconomyCoupon) c).getMoney()));
				else if (c instanceof RankCoupon)
					sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Rank", ((RankCoupon) c).getGroup()));
				else if (c instanceof XpCoupon)
					sender.sendMessage(LocaleHandler.getString("Command.Info.Specific.Xp", ((XpCoupon) c).getXp()));
				sender.sendMessage(LocaleHandler.getString("Command.Info.FancyWrap"));
				return;
			} else {
				sender.sendMessage(LocaleHandler.getString("Command.Shared.DoesNotExist"));
				return;
			}
		} else {
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			ArrayList<String> co = CouponCodes.getCouponHandler().getCoupons();
			int total = 0;
			if (co.isEmpty() || co.equals(null)) {
				sb1.append(LocaleHandler.getString("Command.Info.None"));
				sb2.append(LocaleHandler.getString("Command.Info.Breakdown", 0, 0, 0, 0));
			} else {
				double j = co.size();
				double it = 0;
				double ec = 0;
				double ra = 0;
				double xp = 0;
				String it2 = null;
				String ec2 = null;
				String ra2 = null;
				String xp2 = null;
				DecimalFormat d = new DecimalFormat("##.##");
				for (String name : co) {
					sb1.append(name+", ");
					Coupon coo = CouponCodes.getCouponHandler().getBasicCoupon(name);
					if (coo instanceof ItemCoupon) it++;
					if (coo instanceof EconomyCoupon) ec++;
					if (coo instanceof RankCoupon) ra++;
					if (coo instanceof XpCoupon) xp++;
				}
				sb1.deleteCharAt(sb1.length()-1);
				sb1.deleteCharAt(sb1.length()-1);
				it2 = d.format(it/j*100);
				ec2 = d.format(ec/j*100);
				ra2 = d.format(ra/j*100);
				xp2 = d.format(xp/j*100);
				total = (int) (it+ec+ra+xp);
				sb2.append(LocaleHandler.getString("Command.Info.Breakdown", it2, ec2, ra2, xp2));
			}
			sender.sendMessage(LocaleHandler.getString("Command.Info.FancyWrap"));
			sender.sendMessage(LocaleHandler.getString("Command.Info.Header"));
			sender.sendMessage(LocaleHandler.getString("Command.Info.SpecificInstructions"));
			sender.sendMessage(LocaleHandler.getString("Command.Info.CurrentCoupons", sb1.toString()));
			sender.sendMessage(sb2.toString());
			sender.sendMessage(LocaleHandler.getString("Command.Info.Total", total));
			sender.sendMessage(LocaleHandler.getString("Command.Info.FancyWrap"));
			return;
		}

	}

}
