package net.Drepic.CouponCodes.misc;

import org.bukkit.ChatColor;

public enum CommandUsage {
	C_ADD_ITEM (ChatColor.YELLOW+"/c add item [name] [item1:amount,item2:amount..] (usetimes) (time)"),
	C_ADD_ECON (ChatColor.YELLOW+"/c add econ [name] [money] (usetimes) (time)"),
	C_ADD_RANK (ChatColor.YELLOW+"/c add rank [name] [group] (usetimes) (time)"),
	C_ADD_XP (ChatColor.YELLOW+"/c add xp [name] [xp] (usetimes) (time)"),
	C_REDEEM (ChatColor.YELLOW+"/c redeem [name]"),
	C_REMOVE (ChatColor.YELLOW+"/c remove [name/all]"),
	C_LIST (ChatColor.YELLOW+"/c list"),
	C_INFO (ChatColor.YELLOW+"/c info (name)"),
	C_RELOAD (ChatColor.YELLOW+"/c reload");
	
	private String usage;
	
	private CommandUsage(String usage) {
		this.usage = usage;
	}
	
	public String getUsage() {
		return usage;
	}
	
	@Override
	public String toString() {
		return getUsage();
	}
}
