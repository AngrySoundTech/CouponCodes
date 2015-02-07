package com.drevelopment.couponcodes.core.commands.runnables;

import java.util.ArrayList;

import com.drevelopment.couponcodes.api.CouponCodes;
import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.core.util.LocaleHandler;

public class ListCommand implements Runnable {

	private CommandSender sender;

	public ListCommand(CommandSender sender, String[] args) {
		this.sender = sender;
	}

	@Override
	public void run() {
		StringBuilder sb = new StringBuilder();
			ArrayList<String> c = CouponCodes.getCouponHandler().getCoupons();
			if (c.isEmpty() || c.size() <= 0 || c == null) {
				sender.sendMessage(LocaleHandler.getString("Command.List.NoFound"));
				return;
			} else {
				sb.append(LocaleHandler.getString("Command.List.List"));
				for (int i = 0; i < c.size(); i++) {
					sb.append(c.get(i));
					if (!(Integer.valueOf(i+1).equals(c.size()))){
						sb.append(", ");
					}
				}
				sender.sendMessage(sb.toString());
				return;
			}
	}

}
