package com.github.drepic26.couponcodes.core.commands.runnables;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.github.drepic26.couponcodes.core.ServerModTransformer;
import com.github.drepic26.couponcodes.core.commands.CommandSender;
import com.github.drepic26.couponcodes.core.coupon.Coupon;
import com.github.drepic26.couponcodes.core.coupon.EconomyCoupon;
import com.github.drepic26.couponcodes.core.coupon.ItemCoupon;
import com.github.drepic26.couponcodes.core.coupon.RankCoupon;
import com.github.drepic26.couponcodes.core.coupon.XpCoupon;
import com.github.drepic26.couponcodes.core.util.Color;
import com.github.drepic26.couponcodes.core.util.RandomName;

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
			Coupon c = ServerModTransformer.getInstance().getCouponHandler().getCoupon(args[1]);
			if (c != null) {
				sender.sendMessage(Color.GOLD+"|----------------------|");
				sender.sendMessage(Color.GOLD+"|---"+Color.DARK_RED+"Coupon "+Color.YELLOW+c.getName()+Color.DARK_RED+" info"+Color.GOLD+"---|");
				sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Name: "+Color.PURPLE+c.getName());
				sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Type: "+Color.PURPLE+c.getType());
				sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Use times left: "+Color.PURPLE+c.getUseTimes());
				if (c.getTime() != -1)
					sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Time left: "+Color.PURPLE+c.getTime()+Color.YELLOW+" seconds");
				else
					sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Time left: "+Color.PURPLE+"Unlimited");
				sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Expired: "+Color.PURPLE+c.isExpired());
				if (c.getUsedPlayers().isEmpty())
					sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Used players: "+Color.PURPLE+"None");
				else
					sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Used players: "+Color.PURPLE+ServerModTransformer.getInstance().getCouponHandler().convertHashToString2(c.getUsedPlayers()));
				if (c instanceof ItemCoupon)
					sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Items: "+Color.PURPLE+ServerModTransformer.getInstance().getCouponHandler().convertHashToString(((ItemCoupon) c).getIDs()));
				else if (c instanceof EconomyCoupon)
					sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Money: "+Color.PURPLE+((EconomyCoupon) c).getMoney());
				else if (c instanceof RankCoupon)
					sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Rank: "+Color.PURPLE+((RankCoupon) c).getGroup());
				else if (c instanceof XpCoupon)
					sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"XP: "+Color.PURPLE+((XpCoupon) c).getXp());
				sender.sendMessage(Color.GOLD+"|----------------------|");
				return;
			} else {
				sender.sendMessage(Color.RED+"That coupon doesn't exist!");
				return;
			}
		} else {
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			ArrayList<String> co = ServerModTransformer.getInstance().getCouponHandler().getCoupons();
			int total = 0;
			if (co.isEmpty() || co.equals(null)) {
				sb1.append("None");
				sb2.append("Out of those, 0% are item, 0% are economy, and 0% are rank coupons.");
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
					Coupon coo = ServerModTransformer.getInstance().getCouponHandler().getBasicCoupon(name);
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
				sb2.append("Out of those, "+it2+"% are item, "+ec2+"% are economy, "+ra2+"% are rank, and "+xp2+"% are XP coupons.");
			}
			sender.sendMessage(Color.GOLD+"|-----------------------|");
			sender.sendMessage(Color.GOLD+"|-"+Color.DARK_RED+"Info on current coupons"+Color.GOLD+"-|");
			sender.sendMessage(Color.GOLD+"|--"+Color.GOLD+"Use /coupon info [name] to view a specific coupon");
			sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Current coupons: "+Color.PURPLE+sb1.toString());
			sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+sb2.toString());
			sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"Total Coupons: "+Color.PURPLE+total);
			sender.sendMessage(Color.GOLD+"|-----------------------|");
			return;
		}

	}

}
