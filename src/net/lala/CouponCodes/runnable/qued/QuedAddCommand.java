package net.lala.CouponCodes.runnable.qued;

import java.sql.SQLException;
import java.util.HashMap;

import net.lala.CouponCodes.CouponCodes;
import net.lala.CouponCodes.api.CouponManager;
import net.lala.CouponCodes.api.coupon.EconomyCoupon;
import net.lala.CouponCodes.api.coupon.ItemCoupon;
import net.lala.CouponCodes.api.coupon.RankCoupon;
import net.lala.CouponCodes.api.coupon.XpCoupon;
import net.lala.CouponCodes.misc.CommandUsage;
import net.lala.CouponCodes.misc.Misc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class QuedAddCommand implements Runnable {

	private CouponCodes plugin;
	private CommandSender sender;
	private String[] args;
	private CouponManager api;
	
	public QuedAddCommand(CouponCodes plugin, CommandSender sender, String[] args) {
		this.plugin = plugin;
		this.sender = sender;
		this.args = args;
		this.api = CouponCodes.getCouponManager();
	}
	
	@Override
	public void run() {
		if (args[1].equalsIgnoreCase("item")) {
			if (args.length >= 4) {
				try {
					String name = args[2];
					int usetimes = 1;
					int time = -1;
					
					if (name.equalsIgnoreCase("random")) name = Misc.generateName();
					if (args.length >= 5) usetimes = Integer.parseInt(args[4]);
					if (args.length >= 6) time = Integer.parseInt(args[5]);
					if (args.length > 6) {
						sender.sendMessage(CommandUsage.C_ADD_ITEM.toString());
						return;
					}
					
					ItemCoupon ic = api.createNewItemCoupon(name, usetimes, time, plugin.convertStringToHash(args[3]), new HashMap<String, Boolean>());
					
					if (ic.addToDatabase()) {
						sender.sendMessage(ChatColor.GREEN+"Coupon "+ChatColor.GOLD+name+ChatColor.GREEN+" has been added!");
						return;
					} else {
						sender.sendMessage(ChatColor.RED+"This coupon already exists!");
						return;
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.DARK_RED+"Expected a number, but got a string. Please check your syntax.");
					return;
				} catch (SQLException e) {
					sender.sendMessage(ChatColor.DARK_RED+"Error while interacting with the database. See console for more info.");
					sender.sendMessage(ChatColor.DARK_RED+"If this error persists, please report it.");
					e.printStackTrace();
					return;
				}
			} else {
				sender.sendMessage(CommandUsage.C_ADD_ITEM.toString());
				return;
			}
		}
		
		else if (args[1].equalsIgnoreCase("econ")) {
			if (args.length >= 4) {
				try {
					String name = args[2];
					int usetimes = 1;
					int time = -1;
					int money = Integer.parseInt(args[3]);
					
					if (name.equalsIgnoreCase("random")) name = Misc.generateName();
					if (args.length >= 5) usetimes = Integer.parseInt(args[4]);
					if (args.length >= 6) time = Integer.parseInt(args[5]);
					if (args.length > 6) {
						sender.sendMessage(CommandUsage.C_ADD_ECON.toString());
						return;
					}
					
					EconomyCoupon ec = api.createNewEconomyCoupon(name, usetimes, time, new HashMap<String, Boolean>(), money);
					
					if (ec.addToDatabase()) {
						sender.sendMessage(ChatColor.GREEN+"Coupon "+ChatColor.GOLD+name+ChatColor.GREEN+" has been added!");
						return;
					} else {
						sender.sendMessage(ChatColor.RED+"This coupon already exists!");
						return;
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.DARK_RED+"Expected a number, but got a string. Please check your syntax.");
					return;
				} catch (SQLException e) {
					sender.sendMessage(ChatColor.DARK_RED+"Error while interacting with the database. See console for more info.");
					sender.sendMessage(ChatColor.DARK_RED+"If this error persists, please report it.");
					e.printStackTrace();
					return;
				}
			} else {
				sender.sendMessage(CommandUsage.C_ADD_ECON.toString());
				return;
			}
		}
		
		else if (args[1].equalsIgnoreCase("rank")) {
			if (args.length >= 4) {
				try {
					String name = args[2];
					String group = args[3];
					int usetimes = 1;
					int time = -1;
					
					if (name.equalsIgnoreCase("random")) name = Misc.generateName();
					if (args.length >= 5) usetimes = Integer.parseInt(args[4]);
					if (args.length >= 6) time = Integer.parseInt(args[5]);
					if (args.length > 6) {
						sender.sendMessage(CommandUsage.C_ADD_RANK.toString());
						return;
					}
					
					RankCoupon rc = api.createNewRankCoupon(name, group, usetimes, time, new HashMap<String, Boolean>());
					
					if (rc.addToDatabase()) {
						sender.sendMessage(ChatColor.GREEN+"Coupon "+ChatColor.GOLD+name+ChatColor.GREEN+" has been added!");
						return;
					} else {
						sender.sendMessage(ChatColor.RED+"This coupon already exists!");
						return;
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.DARK_RED+"Expected a number, but got a string. Please check your syntax.");
					return;
				} catch (SQLException e) {
					sender.sendMessage(ChatColor.DARK_RED+"Error while interacting with the database. See console for more info.");
					sender.sendMessage(ChatColor.DARK_RED+"If this error persists, please report it.");
					e.printStackTrace();
					return;
				}
			} else {
				sender.sendMessage(CommandUsage.C_ADD_RANK.toString());
				return;
			}
		}
		
		else if (args[1].equalsIgnoreCase("xp")) {
			if (args.length >= 4) {
				try {
					String name = args[2];
					int xp = Integer.parseInt(args[3]);
					int usetimes = 1;
					int time = -1;
					
					if (name.equalsIgnoreCase("random")) name = Misc.generateName();
					if (args.length >= 5) usetimes = Integer.parseInt(args[4]);
					if (args.length >= 6) time = Integer.parseInt(args[5]);
					if (args.length > 6) {
						sender.sendMessage(CommandUsage.C_ADD_XP.toString());
						return;
					}
					
					XpCoupon xc = api.createNewXpCoupon(name, xp, usetimes, time, new HashMap<String, Boolean>());
					
					if (xc.addToDatabase()) {
						sender.sendMessage(ChatColor.GREEN+"Coupon "+ChatColor.GOLD+name+ChatColor.GREEN+" has been added!");
						return;
					} else {
						sender.sendMessage(ChatColor.RED+"This coupon already exists!");
						return;
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.DARK_RED+"Expected a number, but got a string. Please check your syntax.");
					return;
				} catch (SQLException e) {
					sender.sendMessage(ChatColor.DARK_RED+"Error while interacting with the database. See console for more info.");
					sender.sendMessage(ChatColor.DARK_RED+"If this error persists, please report it.");
					e.printStackTrace();
					return;
				}
			} else {
				sender.sendMessage(CommandUsage.C_ADD_XP.toString());
				return;
			}
		} else {
			plugin.helpAdd(sender);
			return;
		}
	}
}
