package net.Drepic.CouponCodes.runnable.qued;

import java.sql.SQLException;
import java.util.ArrayList;

import net.Drepic.CouponCodes.CouponCodes;
import net.Drepic.CouponCodes.api.CouponManager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class QuedListCommand implements Runnable {

	private CommandSender sender;
	private CouponManager api;
	
	public QuedListCommand(CommandSender sender) {
		this.sender = sender;
		this.api = CouponCodes.getCouponManager();
	}
	
	@Override
	public void run() {
		StringBuilder sb = new StringBuilder();
		try {
			ArrayList<String> c = api.getCoupons();
			if (c.isEmpty() || c.size() <= 0 || c == null) {
				sender.sendMessage(ChatColor.RED+"No coupons found.");
				return;
			} else {
				sb.append(ChatColor.DARK_PURPLE+"Coupon list: "+ChatColor.GOLD);
				for (int i = 0; i < c.size(); i++) {
					sb.append(c.get(i));
					if (!(Integer.valueOf(i+1).equals(c.size()))){
						sb.append(", ");
					}
				}
				sender.sendMessage(sb.toString());
				return;
			}
		} catch (SQLException e) {
			sender.sendMessage(ChatColor.DARK_RED+CouponCodes.l.getMessage("SQL_INTERACT_ERROR"));
			sender.sendMessage(ChatColor.DARK_RED+CouponCodes.l.getMessage("ERROR_PERSISTS"));
			e.printStackTrace();
			return;
		}
	}
}
