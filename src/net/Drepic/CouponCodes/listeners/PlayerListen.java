package net.Drepic.CouponCodes.listeners;

import net.Drepic.CouponCodes.CouponCodes;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListen implements Listener {

	private CouponCodes plugin;
	
	public PlayerListen(CouponCodes plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (plugin.has(player, "cc.update")) {
			if (!plugin.version.equals(plugin.newversion) && !plugin.version.contains("TEST") && !(plugin.newversion == null)) {
				player.sendMessage(ChatColor.GREEN+"There is a new update for CouponCodes! Current version: "+plugin.version+" New version: "+plugin.newversion);
				player.sendMessage(ChatColor.GOLD+"About the update: "+plugin.verinfo);
			}
		}
	}
}
