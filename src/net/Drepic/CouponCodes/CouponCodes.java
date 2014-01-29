package net.Drepic.CouponCodes;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.Drepic.CouponCodes.api.CouponManager;
import net.Drepic.CouponCodes.api.events.EventHandle;
import net.Drepic.CouponCodes.api.events.plugin.CouponCodesCommandEvent;
import net.Drepic.CouponCodes.listeners.DebugListen;
import net.Drepic.CouponCodes.misc.CommandUsage;
import net.Drepic.CouponCodes.misc.Metrics;
import net.Drepic.CouponCodes.misc.Updater;
import net.Drepic.CouponCodes.runnable.CouponTimer;
import net.Drepic.CouponCodes.runnable.CustomDataSender;
import net.Drepic.CouponCodes.runnable.qued.QuedAddCommand;
import net.Drepic.CouponCodes.runnable.qued.QuedInfoCommand;
import net.Drepic.CouponCodes.runnable.qued.QuedListCommand;
import net.Drepic.CouponCodes.runnable.qued.QuedRedeemCommand;
import net.Drepic.CouponCodes.runnable.qued.QuedRemoveCommand;
import net.Drepic.CouponCodes.sql.SQL;
import net.Drepic.CouponCodes.sql.options.DatabaseOptions;
import net.Drepic.CouponCodes.sql.options.MySQLOptions;
import net.Drepic.CouponCodes.sql.options.SQLiteOptions;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;

public class CouponCodes extends JavaPlugin {
	
	private static CouponCodes instance;
	private static CouponManager cm;
	
	private DatabaseOptions dataop;
	private Config config;
	
	private boolean va = false;
	private boolean debug = false;
	private boolean usethread = true;
	public boolean checkupdate;
	private SQL sql;
	private Metrics mt = null;
	
	public Server server;
	public Economy econ;
	public Permission perm;
	
	public String version;

	
	@Override
	public void onEnable() {
		
		instance = this;
		server = getServer();
		config = new Config(this);
		debug = config.getDebug();
		version = getDescription().getVersion();
		usethread = config.getUseThread();
		if (config.getCheckUpdate()) {
			@SuppressWarnings("unused")
			Updater updater = new Updater(this, 53833, this.getFile(), Updater.UpdateType.DEFAULT, true);
		}
		
		try {
			mt = new Metrics(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (!setupVault()) {
			send("Vault support is disabled. This option can be changed in the config.");
			va = false;
		} else {
			send("Vault support is enabled.");
			va = true;
		}
		
		// This is for this plugin's own events!
		server.getPluginManager().registerEvents(new DebugListen(this), this);
		
		// Bukkit listeners
		
		if (!setupSQL()) {
			send("Database could not be setup. CouponCodes will now disable");
			server.getPluginManager().disablePlugin(this);
			return;
		}
		
		// Timers!
		if (usethread) {
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new CouponTimer(), 200L, 200L);
		}
		
		// This timer is required, so it can't be in (usethread)!
		getServer().getScheduler().scheduleSyncDelayedTask(this, new CustomDataSender(this, mt));
		
		send("is now enabled! Version: "+version);
	}
	
	@Override
	public void onDisable() {
		server.getScheduler().cancelTasks(this);
		send("Tasks cancelled");
		try {
			sql.close();
		} catch (SQLException e) {
			sendErr("Could not close SQL connection");
		} catch (NullPointerException e) {
			sendErr("SQL is null. Connection doesn't exist");
		}
		send("SQL connection closed");
		cm = null;
		send("is now disabled.");
	}
	
	private boolean setupSQL() {
		if (config.getSQLValue().equalsIgnoreCase("MySQL")) {
			dataop = new MySQLOptions(config.getHostname(), config.getPort(), config.getDatabase(), config.getUsername(), config.getPassword());
		}
		else if (config.getSQLValue().equalsIgnoreCase("SQLite")) {
			dataop = new SQLiteOptions(new File(getDataFolder()+"/coupon_data.db"));
		}
		else if (!config.getSQLValue().equalsIgnoreCase("MySQL") && !config.getSQLValue().equalsIgnoreCase("SQLite")) {
			sendErr("The SQLType has the unknown value of: "+config.getSQLValue());
			return false;
		}
		
		sql = new SQL(this, dataop);
		
		try {
			sql.open();
			sql.createTable("CREATE TABLE IF NOT EXISTS couponcodes (name VARCHAR(24), ctype VARCHAR(10), usetimes INT(10), usedplayers TEXT(1024), ids VARCHAR(255), money INT(10), groupname VARCHAR(20), timeuse INT(100), xp INT(10))");
			cm = new CouponManager(this, sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean setupVault() {
		if (!config.getVault())
			return false;
		try {
			RegisteredServiceProvider<Economy> ep = server.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
			RegisteredServiceProvider<Permission> pe = server.getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
			if (ep == null)
				return false;
			else if (pe == null)
				return false;
			else
				econ = ep.getProvider();
				perm = pe.getProvider();
				return true;
		} catch (NoClassDefFoundError e) {
			return false;
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		// Event handling
		CouponCodesCommandEvent ev = EventHandle.callCouponCodesCommandEvent(sender, command, commandLabel, args);
		if (ev.isCancelled()) return true;
		sender = ev.getSender();
		command = ev.getCommand();
		commandLabel = ev.getCommandLabel();
		args = ev.getArgs();
		
		if (args.length == 0) {
			help(sender);
			return true;
		}
		
		boolean pl = false;
		if (sender instanceof Player) pl = true;
		
		
		// Add command 2.0
		if (args[0].equalsIgnoreCase("add")) {
			if (args.length < 2) {
				helpAdd(sender);
				return true;
			}
			if (has(sender, "cc.add")) {
				server.getScheduler().scheduleSyncDelayedTask(this, new QuedAddCommand(this, sender, args));
				return true;
			} else {
				sender.sendMessage(ChatColor.RED+"You do not have permission to use this command.");
				return true;
			}
		}
		
		// Remove command
		else if (args[0].equalsIgnoreCase("remove")) {
			if (has(sender, "cc.remove")) {
				server.getScheduler().scheduleSyncDelayedTask(this, new QuedRemoveCommand(sender, args));
				return true;
			} else {
				sender.sendMessage(ChatColor.RED+"You do not have permission to use this command");
				return true;
			}
		}
		
		// Redeem command
		else if (args[0].equalsIgnoreCase("redeem")) {
			if (!pl) {
				sender.sendMessage("You must be a player to redeem a coupon");
				return true;
			} else {
				Player player = (Player) sender;
				if (has(player, "cc.redeem")) {
					server.getScheduler().scheduleSyncDelayedTask(this, new QuedRedeemCommand(this, player, args));
					return true;
				} else {
					player.sendMessage(ChatColor.RED+"You do not have permission to use this command");
					return true;
				}
			}
		}
		
		// List command
		else if (args[0].equalsIgnoreCase("list")) {
			if (has(sender, "cc.list")) {
				server.getScheduler().scheduleSyncDelayedTask(this, new QuedListCommand(sender));
				return true;
			} else {
				sender.sendMessage(ChatColor.RED+"You do not have permission to use this command");
				return true;
			}
		}
		
		// Info command
		else if (args[0].equalsIgnoreCase("info")) {
			if (has(sender, "cc.info")) {
				server.getScheduler().scheduleSyncDelayedTask(this, new QuedInfoCommand(this, sender, args));
				return true;
			} else {
				sender.sendMessage(ChatColor.RED+"You do not have permission to use this command");
				return true;
			}
		}
		
		// Reload command
		else if (args[0].equalsIgnoreCase("reload")) {
			if (has(sender, "cc.reload")) {
				if (!sql.reload())
					sender.sendMessage(ChatColor.DARK_RED+"Could not reload the database");
				else
					sender.sendMessage(ChatColor.GREEN+"Database reloaded");
				reloadConfig();
				config = new Config(this);
				debug = config.getDebug();
				sender.sendMessage(ChatColor.GREEN+"Config reloaded");
				return true;
			} else {
				sender.sendMessage(ChatColor.RED+"You do not have permission to use this command");
				return true;
			}
		} else {
			help(sender);
			return true;
		}
	}
	
	public boolean has(CommandSender sender, String permission) {
		if (sender instanceof ConsoleCommandSender) return true;
		if (!va) return sender.hasPermission(permission);
		return perm.has(sender, permission);
	}
	
	private void help(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD+"|-<> = required-"+ChatColor.DARK_RED+"CouponCodes Help"+ChatColor.GOLD+"-[] = optional-|");
		sender.sendMessage(ChatColor.GOLD+"|--"+ChatColor.YELLOW+"add item <name> <item1:amount,item2:amount,..> [usetimes] [time]");
		sender.sendMessage(ChatColor.GOLD+"|--"+ChatColor.YELLOW+"add econ <name> <money> [usetimes] [time]");
		sender.sendMessage(ChatColor.GOLD+"|--"+ChatColor.YELLOW+"add rank <name> <group> [usetimes] [time]");
		sender.sendMessage(ChatColor.GOLD+"|--"+ChatColor.YELLOW+"add xp <name> <xp> [usetimes] [time]");
		sender.sendMessage(ChatColor.GOLD+"|--"+ChatColor.YELLOW+"redeem <name>");
		sender.sendMessage(ChatColor.GOLD+"|--"+ChatColor.YELLOW+"remove <name>");
		sender.sendMessage(ChatColor.GOLD+"|--"+ChatColor.YELLOW+"list");
		sender.sendMessage(ChatColor.GOLD+"|--"+ChatColor.YELLOW+"info <name>");
		sender.sendMessage(ChatColor.GOLD+"|--"+ChatColor.YELLOW+"reload");
	}
	
	public void helpAdd(CommandSender sender) {
		sender.sendMessage(CommandUsage.C_ADD_ITEM.toString());
		sender.sendMessage(CommandUsage.C_ADD_ECON.toString());
		sender.sendMessage(CommandUsage.C_ADD_RANK.toString());
		sender.sendMessage(CommandUsage.C_ADD_XP.toString());
	}
	
	@SuppressWarnings("deprecation")
	public HashMap<Integer, Integer> convertStringToHash(String args, CommandSender sender) {
		HashMap<Integer, Integer> ids = new HashMap<Integer, Integer>();
		String[] sp = args.split(",");
		try {
			for (int i = 0; i < sp.length; i++) {
				int a = 0;
				if (StringUtils.isNumeric(sp[i].split(":")[0])) {
					a = Integer.parseInt(sp[i].split(":")[0]);
				} else {
					if (Material.matchMaterial(sp[i].split(":")[0]) != null) {
						a = Material.matchMaterial(sp[i].split(":")[0]).getId();
					} else {
						a = 1;
						if (sender != null) {
							sender.sendMessage(ChatColor.GREEN+"Item "+ChatColor.GOLD+sp[i].split(":")[0]+ChatColor.GREEN+" does not exist. Item defaulted to stone.");
						}
					}
				}
				int b = Integer.parseInt(sp[i].split(":")[1]);
					ids.put(a, b);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	public String convertHashToString(HashMap<Integer, Integer> hash) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, Integer> en : hash.entrySet()) {
			sb.append(en.getKey()+":"+en.getValue()+",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	
	public HashMap<String, Boolean> convertStringToHash2(String args) {
		HashMap<String, Boolean> pl = new HashMap<String, Boolean>();
		if (args.equals(null) || args.length() < 1) return pl;
		String[] sp = args.split(",");
		try {
			for (int i = 0; i < sp.length; i++) {
				String a = String.valueOf(sp[i].split(":")[0]);
				Boolean b = Boolean.valueOf(sp[i].split(":")[1]);
				pl.put(a, b);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return pl;
	}
	
	public String convertHashToString2(HashMap<String, Boolean> hash) {
		if (hash.isEmpty() || hash == null || hash.size() < 1) return "";
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Boolean> en : hash.entrySet()) {
			sb.append(en.getKey()+":"+en.getValue()+",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public void send(String message) {
		System.out.println("[CouponCodes] "+message);
	}
	
	public void sendErr(String message) {
		System.err.println("[CouponCodes] [Error] "+message);
	}
	
	public void debug(String message) {
		if (!debug) return;
		System.out.println("[CouponCodes] [Debug] "+message);
	}
	
	public static CouponCodes getInstance() {
		return instance;
	}
	
	/**
	 * @return Current instance of the coupon manager
	 */
	public static CouponManager getCouponManager() {
		return cm;
	}
	
	public SQL getSQLAPI() {
		return sql;
	}
	
	public DatabaseOptions getDatabaseOptions() {
		return dataop;
	}
	
	public boolean isVaultEnabled() {
		return va;
	}
	
	public boolean isDebug() {
		return debug;
	}
	
	public boolean useThread() {
		return usethread;
	}
}
