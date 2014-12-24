package com.github.drepic26.couponcodes.core.commands;

import com.github.drepic26.couponcodes.core.util.Color;

public enum CommandUsage {
	C_ADD_ITEM (Color.YELLOW+"/c add item [name] [item1:amount,item2:amount..] (usetimes) (time)"),
	C_ADD_ECON (Color.YELLOW+"/c add econ [name] [money] (usetimes) (time)"),
	C_ADD_RANK (Color.YELLOW+"/c add rank [name] [group] (usetimes) (time)"),
	C_ADD_XP (Color.YELLOW+"/c add xp [name] [xp] (usetimes) (time)"),
	C_REDEEM (Color.YELLOW+"/c redeem [name]"),
	C_REMOVE (Color.YELLOW+"/c remove [name/all]"),
	C_LIST (Color.YELLOW+"/c list"),
	C_INFO (Color.YELLOW+"/c info (name)"),
	C_RELOAD (Color.YELLOW+"/c reload");

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
