package com.github.drepic26.couponcodes.core.commands.runnables;

import com.github.drepic26.couponcodes.core.commands.CommandSender;

public class AddCommand implements Runnable {

	private CommandSender sender;
	private String[] args;

	public AddCommand(CommandSender sender, String[] args) {
		this.sender = sender;
		this.args = args;
	}

	@Override
	public void run() {

	}

}
