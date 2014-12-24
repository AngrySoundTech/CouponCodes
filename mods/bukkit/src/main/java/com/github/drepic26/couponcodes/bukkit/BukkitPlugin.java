package com.github.drepic26.couponcodes.bukkit;

import java.util.logging.Logger;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.drepic26.couponcodes.bukkit.commands.BukkitCommandHandler;
import com.github.drepic26.couponcodes.bukkit.listeners.BukkitListener;
import com.github.drepic26.couponcodes.bukkit.permission.SuperPermsPermissionHandler;
import com.github.drepic26.couponcodes.bukkit.permission.VaultPermissionHandler;
import com.github.drepic26.couponcodes.core.ServerModTransformer;
import com.github.drepic26.couponcodes.core.commands.CommandHandler;
import com.github.drepic26.couponcodes.core.entity.Player;

public class BukkitPlugin extends JavaPlugin implements Listener {

	private Logger logger = null;

	private ServerModTransformer transformer = new BukkitServerModTransformer(this);
	private final CommandHandler commandHandler = new BukkitCommandHandler();

	@Override
	public void onEnable() {
		logger = this.getLogger();

		// Events
		getServer().getPluginManager().registerEvents(new BukkitListener(this), this);

		// Permissions
		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			transformer.setPermissionHandler(new VaultPermissionHandler());
		} else {
			transformer.setPermissionHandler(new SuperPermsPermissionHandler());
		}
	}

	@Override
	public void onDisable() {
		transformer = null;
	}

	public Player wrapPlayer(org.bukkit.entity.Player player) {
		return transformer.getPlayer(player.getName());
	}

	public ServerModTransformer getTransformer() {
		return transformer;
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

}
