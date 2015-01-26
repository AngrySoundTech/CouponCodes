package com.github.drepic26.couponcodes.core.commands.runnables;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.command.CommandSender;
import com.github.drepic26.couponcodes.api.coupon.Coupon;
import com.github.drepic26.couponcodes.api.coupon.EconomyCoupon;
import com.github.drepic26.couponcodes.api.coupon.ItemCoupon;
import com.github.drepic26.couponcodes.api.coupon.RankCoupon;
import com.github.drepic26.couponcodes.api.coupon.XpCoupon;
import com.github.drepic26.couponcodes.core.util.LocaleHandler;

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
				sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.FancyWrap"));
				sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.Header", c.getName()));
				sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.Name", c.getName()));
				sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.Type", c.getType()));
				sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.Usetimes", c.getUseTimes()));
				if (c.getTime() != -1)
					sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.TimeLeft", c.getTime()));
				else
					sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.TimeLeft", "unlimited"));
				sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.Expired", c.isExpired()));

				if (c.getUsedPlayers().isEmpty())
					sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.UsedPlayers", "None"));
				else
					sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.UsedPlayers", CouponCodes.getCouponHandler().playerHashToString(c.getUsedPlayers())));
				if (c instanceof ItemCoupon)
					sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.Items", CouponCodes.getCouponHandler().itemHashToString(((ItemCoupon) c).getIDs())));
				else if (c instanceof EconomyCoupon)
					sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.Money", ((EconomyCoupon) c).getMoney()));
				else if (c instanceof RankCoupon)
					sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.Rank", ((RankCoupon) c).getGroup()));
				else if (c instanceof XpCoupon)
					sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Specific.Xp", ((XpCoupon) c).getXp()));
				sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.FancyWrap"));
				return;
			} else {
				sender.sendMessage(LocaleHandler.getString(sender, "Command.Shared.DoesNotExist"));
				return;
			}
		} else {
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			ArrayList<String> co = CouponCodes.getCouponHandler().getCoupons();
			int total = 0;
			if (co.isEmpty() || co.equals(null)) {
				sb1.append(LocaleHandler.getString(sender, "Command.Info.None"));
				sb2.append(LocaleHandler.getString(sender, "Command.Info.Breakdown", 0, 0, 0, 0));
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
				sb2.append(LocaleHandler.getString(sender,"Command.Info.Breakdown", it2, ec2, ra2, xp2));
			}
			sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.FancyWrap"));
			sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Header"));
			sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.SpecificInstructions"));
			sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.CurrentCoupons", sb1.toString()));
			sender.sendMessage(sb2.toString());
			sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.Total", total));
			sender.sendMessage(LocaleHandler.getString(sender, "Command.Info.FancyWrap"));
			return;
		}

	}

}
