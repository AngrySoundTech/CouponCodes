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
					sender.sendMessage(ChatColor.GREEN+CouponCodes.l.getMessage("TOTAL_OF")+" "+ChatColor.GOLD+j+" "+ChatColor.GREEN+CouponCodes.l.getMessage("HAVE_REMOVED"));
					return;
				}
				if (!api.couponExists(args[1])) {
					sender.sendMessage(ChatColor.RED+CouponCodes.l.getMessage("COUPON_NOT_EXIST"));
					return;
				}
				api.removeCouponFromDatabase(api.createNewItemCoupon(args[1], 0, -1, null, null));
				sender.sendMessage(ChatColor.GOLD+args[1]+ChatColor.GREEN+" "+CouponCodes.l.getMessage("HAS_BEEN_REMOVED"));
				return;
			} catch (SQLException e) {
				sender.sendMessage(ChatColor.DARK_RED+CouponCodes.l.getMessage("SQL_INTERACT_ERROR"));
				sender.sendMessage(ChatColor.DARK_RED+CouponCodes.l.getMessage("ERROR_PERSISTS"));
				e.printStackTrace();
				return;
			}
		} else {
			sender.sendMessage(CommandUsage.C_REMOVE.toString());
			return;
		}
	}
}
