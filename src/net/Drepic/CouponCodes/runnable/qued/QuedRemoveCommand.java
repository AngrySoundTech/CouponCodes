package net.Drepic.CouponCodes.runnable.qued;

import java.sql.SQLException;
import java.util.ArrayList;

import net.Drepic.CouponCodes.CouponCodes;
import net.Drepic.CouponCodes.api.CouponManager;
import net.Drepic.CouponCodes.misc.CommandUsage;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class QuedRemoveCommand implements Runnable {

	private CommandSender sender;
	private String[] args;
	private CouponManager api;
	
	public QuedRemoveCommand(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
		this.api = CouponCodes.getCouponManager();
	}
	
	@Override
	public void run() {
		if (args.length == 2) {
			try {
				if (args[1].equalsIgnoreCase("all")) {
					int j = 0;
					ArrayList<String> cs = api.getCoupons();
					for (String i : cs) {
						api.removeCouponFromDatabase(i);
						j++;
					}
					sender.sendMessage(ChatColor.GREEN+"A total of "+ChatColor.GOLD+j+ChatColor.GREEN+" coupons have been removed.");
					return;
				}
				if (!api.couponExists(args[1])) {
					sender.sendMessage(ChatColor.RED+"That coupon doesn't exist!");
					return;
				}
				api.removeCouponFromDatabase(api.createNewItemCoupon(args[1], 0, -1, null, null));
				sender.sendMessage(ChatColor.GREEN+"The coupon "+ChatColor.GOLD+args[1]+ChatColor.GREEN+" has been removed.");
				return;
			} catch (SQLException e) {
				sender.sendMessage(ChatColor.DARK_RED+"Error while removing coupon from the database. Please check the console for more info.");
				sender.sendMessage(ChatColor.DARK_RED+"If this error persists, please report it.");
				e.printStackTrace();
				return;
			}
		} else {
			sender.sendMessage(CommandUsage.C_REMOVE.toString());
			return;
		}
	}
}
