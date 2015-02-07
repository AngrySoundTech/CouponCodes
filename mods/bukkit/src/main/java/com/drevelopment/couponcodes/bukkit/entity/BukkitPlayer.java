package com.drevelopment.couponcodes.bukkit.entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.drevelopment.couponcodes.bukkit.BukkitPlugin;
import com.drevelopment.couponcodes.core.entity.SimplePlayer;
import com.drevelopment.couponcodes.core.util.Color;

public class BukkitPlayer extends SimplePlayer {

	private final BukkitPlugin plugin;
	private final org.bukkit.entity.Player bukkitPlayer;

	public BukkitPlayer(BukkitPlugin plugin, org.bukkit.entity.Player bukkitPlayer) {
		this.plugin = plugin;
		this.bukkitPlayer = bukkitPlayer;
	}

	public void sendMessage(String message) {
		for (String line : message.split("\n")) {
			bukkitPlayer.sendMessage(Color.replaceColors(line));
		}
	}

	@Override
	public String getName() {
		return bukkitPlayer.getName();
	}

	@Override
	public String getUUID() {
		return bukkitPlayer.getUniqueId().toString();
	}

	@Override
	public void setLevel(int level) {
		bukkitPlayer.setLevel(level);
	}

	@Override
	public int getLevel() {
		return bukkitPlayer.getLevel();
	}

	@Override
	public void giveItem(int item, int amount) {
		if (bukkitPlayer.getInventory().firstEmpty() == -1) {
			bukkitPlayer.getLocation().getWorld().dropItem(bukkitPlayer.getLocation(), new ItemStack(item, amount));
		} else {
			bukkitPlayer.getInventory().addItem(new ItemStack(item, amount));
		}
	}

	public org.bukkit.entity.Player getBukkitPlayer() {
		return bukkitPlayer;
	}

	public BukkitPlugin getPlugin() {
		return plugin;
	}

	@Override
	public String getLocale() {
		try {
			Object ep = getMethod("getHandle", bukkitPlayer.getClass()).invoke(bukkitPlayer, (Object[]) null);
			Field f = ep.getClass().getDeclaredField("locale");
			f.setAccessible(true);
			String language = (String) f.get(ep);
			return language;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Method getMethod(String name, Class<? extends Player> class1) {
		for (Method m : class1.getDeclaredMethods()) {
			if (m.getName().equals(name))
				return m;
		}
		return null;
	}

}
