package com.github.drepic26.couponcodes.core.commands.runnables;

import java.util.ArrayList;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.command.CommandSender;
import com.github.drepic26.couponcodes.core.util.LocaleHandler;

public class RemoveCommand implements Runnable {

	private CommandSender sender;
	private String[] args;

	public RemoveCommand(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}

	@Override
	public void run() {
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("all")) {
				int j = 0;
				ArrayList<String> cs = CouponCodes.getCouponHandler().getCoupons();
				for (String i : cs) {
					CouponCodes.getCouponHandler().removeCouponFromDatabase(i);
					j++;
				}
				sender.sendMessage(LocaleHandler.getString(sender, "Command.Remove.AllRemoved", j));
				return;
			}
			if (!CouponCodes.getCouponHandler().couponExists(args[1])) {
				sender.sendMessage(LocaleHandler.getString(sender, "Command.Shared.DoesNotExist"));
				return;
			}
			CouponCodes.getCouponHandler().removeCouponFromDatabase(CouponCodes.getCouponHandler().createNewItemCoupon(args[1], 0, -1, null, null));
			sender.sendMessage(LocaleHandler.getString(sender, "Command.Remove.Removed", args[1]));
			return;
		} else {
			sender.sendMessage(LocaleHandler.getString(sender, "Command.Help.Remove"));
			return;
		}
	}
}
