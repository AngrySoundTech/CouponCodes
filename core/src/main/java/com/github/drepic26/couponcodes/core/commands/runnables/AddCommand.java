package com.github.drepic26.couponcodes.core.commands.runnables;

import java.util.HashMap;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.command.CommandSender;
import com.github.drepic26.couponcodes.api.coupon.EconomyCoupon;
import com.github.drepic26.couponcodes.api.coupon.ItemCoupon;
import com.github.drepic26.couponcodes.api.coupon.RankCoupon;
import com.github.drepic26.couponcodes.api.coupon.XpCoupon;
import com.github.drepic26.couponcodes.core.commands.CommandUsage;
import com.github.drepic26.couponcodes.core.util.Color;
import com.github.drepic26.couponcodes.core.util.RandomName;

public class AddCommand implements Runnable {

	private CommandSender sender;
	private String[] args;

	public AddCommand(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}

	@Override
	public void run() {
		if (args.length < 2) {
			helpAdd(sender);
			return;
		}

		if (args[1].equalsIgnoreCase("item")) {
			if (args.length >= 4) {
				try {

					String name = args[2];
					int usetimes = 1;
					int time = -1;

					if (name.equalsIgnoreCase("random")) name = RandomName.generateName();
					if (args.length >= 5) usetimes = Integer.parseInt(args[4]);
					if (args.length >= 6) time = Integer.parseInt(args[5]);
					if (args.length > 6) {
						sender.sendMessage(CommandUsage.C_ADD_ITEM.toString());
						return;
					}

					ItemCoupon ic = CouponCodes.getCouponHandler().createNewItemCoupon(name, usetimes, time, CouponCodes.getCouponHandler().itemStringToHash(args[3], sender), new HashMap<String, Boolean>());

					if (ic.addToDatabase()) {
						sender.sendMessage(Color.GREEN+"Coupon "+Color.GOLD+name+Color.GREEN+" has been added!");
						return;
					} else {
						sender.sendMessage(Color.RED+"This coupon already exists!");
						return;
					}

				} catch (NumberFormatException e) {
					sender.sendMessage(Color.DARK_RED+"Expected a number, but got a string. Please check your syntax.");
					return;
				}
			} else {
				sender.sendMessage(CommandUsage.C_ADD_ITEM.toString());
				return;
			}
		} else

		if (args[1].equalsIgnoreCase("econ")) {
			if (args.length >= 4) {
				try {
					String name = args[2];
					int usetimes = 1;
					int time = -1;
					int money = Integer.parseInt(args[3]);

					if (name.equalsIgnoreCase("random")) name = RandomName.generateName();
					if (args.length >= 5) usetimes = Integer.parseInt(args[4]);
					if (args.length >= 6) time = Integer.parseInt(args[5]);
					if (args.length > 6) {
						sender.sendMessage(CommandUsage.C_ADD_ECON.toString());
						return;
					}

					EconomyCoupon ec = CouponCodes.getCouponHandler().createNewEconomyCoupon(name, usetimes, time, new HashMap<String, Boolean>(), money);

					if (ec.addToDatabase()) {
						sender.sendMessage(Color.GREEN+"Coupon "+Color.GOLD+name+Color.GREEN+" has been added!");
						return;
					} else {
						sender.sendMessage(Color.RED+"This coupon already exists!");
						return;
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(Color.DARK_RED+"Expected a number, but got a string. Please check your syntax.");
					return;
				}
			} else {
				sender.sendMessage(CommandUsage.C_ADD_ECON.toString());
				return;
			}

		} else

		if (args[1].equalsIgnoreCase("rank")) {
			if (args.length >= 4) {
				try {
					String name = args[2];
					String group = args[3];
					int usetimes = 1;
					int time = -1;

					if (name.equalsIgnoreCase("random")) name = RandomName.generateName();
					if (args.length >= 5) usetimes = Integer.parseInt(args[4]);
					if (args.length >= 6) time = Integer.parseInt(args[5]);
					if (args.length > 6) {
						sender.sendMessage(CommandUsage.C_ADD_RANK.toString());
						return;
					}

					RankCoupon rc = CouponCodes.getCouponHandler().createNewRankCoupon(name, group, usetimes, time, new HashMap<String, Boolean>());

					if (rc.addToDatabase()) {
						sender.sendMessage(Color.GREEN+"Coupon "+Color.GOLD+name+Color.GREEN+" has been added!");
						return;
					} else {
						sender.sendMessage(Color.RED+"This coupon already exists!");
						return;
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(Color.DARK_RED+"Expected a number, but got a string. Please check your syntax.");
					return;
				}
			} else {
				sender.sendMessage(CommandUsage.C_ADD_RANK.toString());
				return;
			}
		} else

		if (args[1].equalsIgnoreCase("xp")) {
			if (args.length >= 4) {
				try {
					String name = args[2];
					int xp = Integer.parseInt(args[3]);
					int usetimes = 1;
					int time = -1;

					if (name.equalsIgnoreCase("random")) name = RandomName.generateName();
					if (args.length >= 5) usetimes = Integer.parseInt(args[4]);
					if (args.length >= 6) time = Integer.parseInt(args[5]);
					if (args.length > 6) {
						sender.sendMessage(CommandUsage.C_ADD_XP.toString());
						return;
					}

					XpCoupon xc = CouponCodes.getCouponHandler().createNewXpCoupon(name, xp, usetimes, time, new HashMap<String, Boolean>());

					if (xc.addToDatabase()) {
						sender.sendMessage(Color.GREEN+"Coupon "+Color.GOLD+name+Color.GREEN+" has been added!");
						return;
					} else {
						sender.sendMessage(Color.RED+"This coupon already exists!");
						return;
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(Color.DARK_RED+"Expected a number, but got a string. Please check your syntax.");
					return;
				}
			} else {
				sender.sendMessage(CommandUsage.C_ADD_XP.toString());
				return;
			}
		} else {
			helpAdd(sender);
		}
	}

	private void helpAdd(CommandSender sender) {
		sender.sendMessage(Color.GOLD+"|-<> = required-"+Color.DARK_RED+"CouponCodes Help"+Color.GOLD+"-[]-optional-|");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"add item <name> <item1:amount,item2:amount,..> [usetimes] [time]");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"add econ <name> <money> [usetimes] [time]");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"add rank <name> <group> [usetimes] [time]");
		sender.sendMessage(Color.GOLD+"|--"+Color.YELLOW+"add xp <name> <xp> [usetimes] [time]");
	}
}
