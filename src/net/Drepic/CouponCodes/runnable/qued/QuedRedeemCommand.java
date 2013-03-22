package net.Drepic.CouponCodes.runnable.qued;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.Drepic.CouponCodes.CouponCodes;
import net.Drepic.CouponCodes.api.CouponManager;
import net.Drepic.CouponCodes.api.coupon.Coupon;
import net.Drepic.CouponCodes.api.coupon.EconomyCoupon;
import net.Drepic.CouponCodes.api.coupon.ItemCoupon;
import net.Drepic.CouponCodes.api.coupon.RankCoupon;
import net.Drepic.CouponCodes.api.coupon.XpCoupon;
import net.Drepic.CouponCodes.misc.CommandUsage;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class QuedRedeemCommand implements Runnable {

	private Player player;
	private String[] args;
	
	private boolean va;
	private CouponManager api;
	private Permission perm;
	private Economy econ;
	
	public QuedRedeemCommand(CouponCodes plugin, Player player, String[] args) {
		this.player = player;
		this.args = args;
		this.va = plugin.isVaultEnabled();
		this.api = CouponCodes.getCouponManager();
		this.perm = plugin.perm;
		this.econ = plugin.econ;
	}
	
	@Override
	public void run() {
		if (args.length == 2) {
			try {
				Coupon coupon = api.getCoupon(args[1]);
				if (coupon == null) {
					player.sendMessage(ChatColor.RED+CouponCodes.l.getMessage("COUPON_NOT_EXIST"));
					return;
				}
				
				if (coupon.getUseTimes() < 1) {
					player.sendMessage(ChatColor.RED+CouponCodes.l.getMessage("COUPON_USED_UP"));
					return;
				}
				
				if (coupon.getUsedPlayers() != null) {
					if (!coupon.getUsedPlayers().isEmpty()) {
						if (coupon.getUsedPlayers().containsKey(player.getName())) {
							if (coupon.getUsedPlayers().get(player.getName())) {
								player.sendMessage(ChatColor.RED+CouponCodes.l.getMessage("COUPON_ALREADY_USED"));
								return;
							}
						}
					}
				}
				
				if (coupon.getTime() == 0) {
					player.sendMessage(ChatColor.RED+CouponCodes.l.getMessage("COUPON_TIMEOUT"));
					return;
				}
				
				if (coupon.isExpired()) {
					player.sendMessage(ChatColor.RED+CouponCodes.l.getMessage("COUPON_EXPPIRED"));
					return;
				}
				
				if (coupon instanceof ItemCoupon) {
					ItemCoupon c = (ItemCoupon) coupon;
					if (player.getInventory().firstEmpty() == -1) {
						for (Map.Entry<Integer, Integer> en : c.getIDs().entrySet()) {
							player.getLocation().getWorld().dropItem(player.getLocation(), new ItemStack(en.getKey(), en.getValue()));
						}
						player.sendMessage(ChatColor.RED+CouponCodes.l.getMessage("FULL_INV_DROP"));
					} else {
						for (Map.Entry<Integer, Integer> en : c.getIDs().entrySet()) {
							player.getInventory().addItem(new ItemStack(en.getKey(), en.getValue()));
						}
						player.sendMessage(ChatColor.GREEN+CouponCodes.l.getMessage("COUPON")+" "+ChatColor.GOLD+c.getName()+ChatColor.GREEN+CouponCodes.l.getMessage("HAS_BEEN_REDEEMED"));
					}
				}
				
				else if (coupon instanceof EconomyCoupon) {
					if (!va) {
						player.sendMessage(ChatColor.DARK_RED+CouponCodes.l.getMessage("ECONOMY_VAULT_DISABLED"));
						return;
					} else {
						EconomyCoupon c = (EconomyCoupon) coupon;
						econ.depositPlayer(player.getName(), c.getMoney());
						player.sendMessage(ChatColor.GREEN+CouponCodes.l.getMessage("COUPON")+" "+ChatColor.GOLD+c.getName()+ChatColor.GREEN+CouponCodes.l.getMessage("REDEEM_ECON"));
					}
				}
				
				else if (coupon instanceof RankCoupon) {
					if (!va) {
						player.sendMessage(ChatColor.DARK_RED+CouponCodes.l.getMessage("VAULT_DISABLED_RANK"));
						return;
					} else {
						RankCoupon c = (RankCoupon) coupon;
						boolean permbuk = (perm.getName().equalsIgnoreCase("PermissionsBukkit"));
						if (permbuk) {
							perm.playerAddGroup((String) null, player.getName(), c.getGroup());
							for (String i : perm.getPlayerGroups((String) null, player.getName())) {
								if (i.equalsIgnoreCase(c.getGroup())) continue;
								perm.playerRemoveGroup((String) null, player.getName(), i);
							}
						} else {
							perm.playerAddGroup(player, c.getGroup());
							for (String i : perm.getPlayerGroups(player)) {
								if (i.equalsIgnoreCase(c.getGroup())) continue;
								perm.playerRemoveGroup(player, i);
							}
						}
						player.sendMessage(ChatColor.GREEN+CouponCodes.l.getMessage("COUPON")+" "+ChatColor.GOLD+c.getName()+ChatColor.GREEN+CouponCodes.l.getMessage("REDEEM_RANK")+" "+ChatColor.GOLD+c.getGroup());
					}
				}
				
				else if (coupon instanceof XpCoupon) {
					XpCoupon c = (XpCoupon) coupon;
					player.setLevel(player.getLevel()+c.getXp());
					player.sendMessage(ChatColor.GREEN+CouponCodes.l.getMessage("COUPON")+" "+ChatColor.GOLD+c.getName()+ChatColor.GREEN+" "+CouponCodes.l.getMessage("XP_REDEEM")+" "+ChatColor.GOLD+c.getXp()+ChatColor.GREEN+" "+CouponCodes.l.getMessage("XP_LEVELS"));
				}
				
				HashMap<String, Boolean> up = coupon.getUsedPlayers();
				up.put(player.getName(), true);
				coupon.setUsedPlayers(up);
				coupon.setUseTimes(coupon.getUseTimes()-1);
				api.updateCoupon(coupon);
				return;
			} catch (SQLException e) {
				player.sendMessage(ChatColor.DARK_RED+CouponCodes.l.getMessage("FINDING_ERROR")+" "+ChatColor.GOLD+args[1]+ChatColor.DARK_RED+" "+CouponCodes.l.getMessage("DATABASE_FIND_ERROR"));
				player.sendMessage(ChatColor.DARK_RED+CouponCodes.l.getMessage("ERROR_PERSISTS"));
				e.printStackTrace();
				return;
			}
		} else {
			player.sendMessage(CommandUsage.C_REDEEM.toString());
			return;
		}
	}
}
