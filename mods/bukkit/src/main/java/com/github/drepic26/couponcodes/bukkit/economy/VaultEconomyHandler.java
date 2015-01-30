package com.github.drepic26.couponcodes.bukkit.economy;

import java.util.UUID;

import org.bukkit.Bukkit;

import net.milkbowl.vault.economy.Economy;

import com.github.drepic26.couponcodes.api.economy.EconomyHandler;

public class VaultEconomyHandler implements EconomyHandler {

	private Economy econ;

	public VaultEconomyHandler(Economy econ) {
		this.econ = econ;
	}

	@Override
	public void giveMoney(String uuid, int amount) {
		econ.depositPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)), amount);
	}

}
