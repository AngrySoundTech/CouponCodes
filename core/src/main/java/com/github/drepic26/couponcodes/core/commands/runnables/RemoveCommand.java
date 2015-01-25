package com.github.drepic26.couponcodes.core.commands.runnables;

import java.util.ArrayList;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.command.CommandSender;
import com.github.drepic26.couponcodes.core.commands.CommandUsage;
import com.github.drepic26.couponcodes.core.util.Color;

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
				sender.sendMessage(Color.GREEN+"A total of "+Color.GOLD+j+Color.GREEN+" coupons have been removed.");
				return;
			}
			if (!CouponCodes.getCouponHandler().couponExists(args[1])) {
				sender.sendMessage(Color.RED+"That coupon doesn't exist!");
				return;
			}
			CouponCodes.getCouponHandler().removeCouponFromDatabase(CouponCodes.getCouponHandler().createNewItemCoupon(args[1], 0, -1, null, null));
			sender.sendMessage(Color.GREEN+"The coupon "+Color.GOLD+args[1]+Color.GREEN+" has been removed.");
			return;
		} else {
			sender.sendMessage(CommandUsage.C_REMOVE.toString());
			return;
		}
	}
}
