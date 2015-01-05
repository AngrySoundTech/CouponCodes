package com.github.drepic26.couponcodes.core.commands;

import com.github.drepic26.couponcodes.core.util.Color;

public enum CommandUsage {
	C_ADD_ITEM (Color.GOLD+"|--"+Color.YELLOW+"/coupon add item [name] [item1:amount,item2:amount..] (usetimes) (time)"),
	C_ADD_ECON (Color.GOLD+"|--"+Color.YELLOW+"/coupon add econ [name] [money] (usetimes) (time)"),
	C_ADD_RANK (Color.GOLD+"|--"+Color.YELLOW+"/coupon add rank [name] [group] (usetimes) (time)"),
	C_ADD_XP (Color.GOLD+"|--"+Color.YELLOW+"/coupon add xp [name] [xp] (usetimes) (time)"),
	C_REDEEM (Color.GOLD+"|--"+Color.YELLOW+"/coupon redeem [name]"),
	C_REMOVE (Color.GOLD+"|--"+Color.YELLOW+"/coupon remove [name/all]"),
	C_LIST (Color.GOLD+"|--"+Color.YELLOW+"/coupon list"),
	C_INFO (Color.GOLD+"|--"+Color.YELLOW+"/coupon info (name)");

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
