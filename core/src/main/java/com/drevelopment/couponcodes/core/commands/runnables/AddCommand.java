package com.drevelopment.couponcodes.core.commands.runnables;

import java.util.HashMap;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.api.coupon.EconomyCoupon;
import com.drevelopment.couponcodes.api.coupon.ItemCoupon;
import com.drevelopment.couponcodes.api.coupon.RankCoupon;
import com.drevelopment.couponcodes.api.coupon.XpCoupon;
import com.drevelopment.couponcodes.core.util.LocaleHandler;
import com.drevelopment.couponcodes.core.util.RandomName;

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
						sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"));
						return;
					}

					ItemCoupon ic = CouponCodes.getCouponHandler().createNewItemCoupon(name, usetimes, time, CouponCodes.getCouponHandler().itemStringToHash(args[3], sender), new HashMap<String, Boolean>());

					if (ic.addToDatabase()) {
						sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name));
						return;
					} else {
						sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"));
						return;
					}

				} catch (NumberFormatException e) {
					sender.sendMessage(LocaleHandler.getString("Command.Add.SyntaxError"));
					return;
				}
			} else {
				sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"));
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
						sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"));
						return;
					}

					EconomyCoupon ec = CouponCodes.getCouponHandler().createNewEconomyCoupon(name, usetimes, time, new HashMap<String, Boolean>(), money);

					if (ec.addToDatabase()) {
						sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name));
						return;
					} else {
						sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"));
						return;
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(LocaleHandler.getString("Command.Add.SyntaxError"));
					return;
				}
			} else {
				sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"));
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
						sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"));
						return;
					}

					RankCoupon rc = CouponCodes.getCouponHandler().createNewRankCoupon(name, group, usetimes, time, new HashMap<String, Boolean>());

					if (rc.addToDatabase()) {
						sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name));
						return;
					} else {
						sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"));
						return;
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(LocaleHandler.getString("Command.Add.SyntaxError"));
					return;
				}
			} else {
				sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"));
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
						sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"));
						return;
					}

					XpCoupon xc = CouponCodes.getCouponHandler().createNewXpCoupon(name, xp, usetimes, time, new HashMap<String, Boolean>());

					if (xc.addToDatabase()) {
						sender.sendMessage(LocaleHandler.getString("Command.Add.Added", name));
						return;
					} else {
						sender.sendMessage(LocaleHandler.getString("Command.Add.AlreadyExists"));
						return;
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(LocaleHandler.getString("Command.Add.SyntaxError"));
					return;
				}
			} else {
				sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"));
				return;
			}
		} else {
			helpAdd(sender);
		}
	}

	private void helpAdd(CommandSender sender) {
		sender.sendMessage(LocaleHandler.getString("Command.Help.Header"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.AddItem"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.AddEcon"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.AddRank"));
		sender.sendMessage(LocaleHandler.getString("Command.Help.AddXp"));
	}
}
