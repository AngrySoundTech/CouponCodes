package com.drevelopment.couponcodes.bukkit.coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;

import com.drevelopment.couponcodes.api.command.CommandSender;
import com.drevelopment.couponcodes.api.coupon.CommandCoupon;
import com.drevelopment.couponcodes.api.coupon.Coupon;
import com.drevelopment.couponcodes.api.coupon.EconomyCoupon;
import com.drevelopment.couponcodes.api.coupon.ItemCoupon;
import com.drevelopment.couponcodes.api.coupon.RankCoupon;
import com.drevelopment.couponcodes.api.coupon.XpCoupon;
import com.drevelopment.couponcodes.bukkit.BukkitPlugin;
import com.drevelopment.couponcodes.bukkit.database.SQLDatabaseHandler;
import com.drevelopment.couponcodes.bukkit.database.options.MySQLOptions;
import com.drevelopment.couponcodes.core.coupon.SimpleCouponHandler;

public class BukkitCouponHandler extends SimpleCouponHandler {

	private BukkitPlugin plugin;
	private SQLDatabaseHandler databaseHandler;

	public BukkitCouponHandler(BukkitPlugin plugin, SQLDatabaseHandler databaseHandler) {
		this.plugin = plugin;
		this.databaseHandler = databaseHandler;

	}

	public SQLDatabaseHandler getDatabaseHandler() {
		return this.databaseHandler;
	}

	@Override
	public boolean addCouponToDatabase(Coupon coupon) {
		if (couponExists(coupon)) return false;
		try {
			Connection con = databaseHandler.getConnection();
			PreparedStatement p = null;

			if (coupon instanceof ItemCoupon) {
				ItemCoupon c = (ItemCoupon) coupon;
				p = con.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, ids, timeuse) "+"VALUES (?, ?, ?, ?, ?, ?)");
				p.setString(1, c.getName());
				p.setString(2, c.getType());
				p.setInt(3, c.getUseTimes());
				p.setString(4, playerHashToString(c.getUsedPlayers()));
				p.setString(5, itemHashToString(c.getIDs()));
				p.setInt(6, c.getTime());
			} else

			if (coupon instanceof EconomyCoupon) {
				EconomyCoupon c = (EconomyCoupon) coupon;
				p = con.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, money, timeuse) "+"VALUES (?, ?, ?, ?, ?, ?)");
				p.setString(1, c.getName());
				p.setString(2, c.getType());
				p.setInt(3, c.getUseTimes());
				p.setString(4, playerHashToString(c.getUsedPlayers()));
				p.setInt(5, c.getMoney());
				p.setInt(6, c.getTime());
			} else

			if (coupon instanceof RankCoupon) {
				RankCoupon c = (RankCoupon) coupon;
				p = con.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, groupname, timeuse) "+"VALUES (?, ?, ?, ?, ?, ?)");
				p.setString(1, c.getName());
				p.setString(2, c.getType());
				p.setInt(3, c.getUseTimes());
				p.setString(4, playerHashToString(c.getUsedPlayers()));
				p.setString(5, c.getGroup());
				p.setInt(6, c.getTime());
			} else

			if (coupon instanceof XpCoupon) {
				XpCoupon c = (XpCoupon) coupon;
				p = con.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, timeuse, xp) "+"VALUES (?, ?, ?, ?, ?, ?)");
				p.setString(1, c.getName());
				p.setString(2, c.getType());
				p.setInt(3, c.getUseTimes());
				p.setString(4, playerHashToString(c.getUsedPlayers()));
				p.setInt(5, c.getTime());
				p.setInt(6, c.getXp());
			} else

			if (coupon instanceof CommandCoupon) {
				CommandCoupon c = (CommandCoupon) coupon;
				p = con.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, timeuse, command) "+"VALUES (?, ?, ?, ?, ?, ?)");
				p.setString(1, c.getName());
				p.setString(2, c.getType());
				p.setInt(3, c.getUseTimes());
				p.setString(4, playerHashToString(c.getUsedPlayers()));
				p.setInt(5, c.getTime());
				p.setString(6, c.getCmd());
			}

			p.addBatch();
			con.setAutoCommit(false);
			p.executeBatch();
			con.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean removeCouponFromDatabase(Coupon coupon) {
		if (!couponExists(coupon)) return false;
		try {
			databaseHandler.query("DELETE FROM couponcodes WHERE name='"+coupon.getName()+"'");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public boolean removeCouponFromDatabase(String coupon) {
		if (!couponExists(coupon)) return false;
		try {
			databaseHandler.query("DELETE FROM couponcodes WHERE name='"+coupon+"'");
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public ArrayList<String> getCoupons() {
		ArrayList<String> c = new ArrayList<String>();
		try {
			ResultSet rs = databaseHandler.query("SELECT name FROM couponcodes");
			if (rs == null) return c;
			while (rs.next())
				c.add(rs.getString(1));
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		try {
			databaseHandler.query("UPDATE couponcodes SET usetimes='"+coupon.getUseTimes()+"' WHERE name='"+coupon.getName()+"'");
			databaseHandler.query("UPDATE couponcodes SET usedplayers='"+playerHashToString(coupon.getUsedPlayers())+"' WHERE name='"+coupon.getName()+"'");
			databaseHandler.query("UPDATE couponcodes SET timeuse='"+coupon.getTime()+"' WHERE name='"+coupon.getName()+"'");

			if (coupon instanceof ItemCoupon)
				databaseHandler.query("UPDATE couponcodes SET ids='"+itemHashToString(((ItemCoupon) coupon).getIDs())+"' WHERE name='"+coupon.getName()+"'");
			else if (coupon instanceof EconomyCoupon)
				databaseHandler.query("UPDATE couponcodes SET money='"+((EconomyCoupon) coupon).getMoney()+"' WHERE name='"+coupon.getName()+"'");
			else if (coupon instanceof RankCoupon)
				databaseHandler.query("UPDATE couponcodes SET groupname='"+((RankCoupon) coupon).getGroup()+"' WHERE name='"+coupon.getName()+"'");
			else if (coupon instanceof XpCoupon)
				databaseHandler.query("UPDATE couponcodes SET xp='"+((XpCoupon) coupon).getXp()+"' WHERE name='"+coupon.getName()+"'");
			else if (coupon instanceof CommandCoupon)
				databaseHandler.query("UPDATE couponcodes SET command='"+((CommandCoupon) coupon).getCmd()+"' WHERE name='"+coupon.getName()+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCouponTime(Coupon coupon) {
		try {
			databaseHandler.query("UPDATE couponcodes SET timeuse='"+coupon.getTime()+"' WHERE name='"+coupon.getName()+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Coupon getCoupon(String coupon) {
		if (!couponExists(coupon)) return null;
		try {
			ResultSet rs = databaseHandler.query("SELECT * FROM couponcodes WHERE name='"+coupon+"'");
			if (databaseHandler.getDatabaseOptions() instanceof MySQLOptions) rs.first();
			int usetimes = rs.getInt("usetimes");
			int time = rs.getInt("timeuse");
			HashMap<String, Boolean> usedplayers = playerStringToHash(rs.getString("usedplayers"));

			if (rs.getString("ctype").equalsIgnoreCase("Item"))
				return createNewItemCoupon(coupon, usetimes, time, itemStringToHash(rs.getString("ids"), null), usedplayers);
			else if (rs.getString("ctype").equalsIgnoreCase("Economy"))
				return createNewEconomyCoupon(coupon, usetimes, time, usedplayers, rs.getInt("money"));
			else if (rs.getString("ctype").equalsIgnoreCase("Rank"))
				return createNewRankCoupon(coupon, rs.getString("groupname"), usetimes, time, usedplayers);
			else if (rs.getString("ctype").equalsIgnoreCase("Xp"))
				return createNewXpCoupon(coupon, rs.getInt("xp"), usetimes, time, usedplayers);
			else if (rs.getString("ctype").equalsIgnoreCase("Command"))
				return createNewCommandCoupon(coupon, rs.getString("command"), usetimes, time, usedplayers);
			else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Coupon getBasicCoupon(String coupon) {
		try {
			ResultSet rs = databaseHandler.query("SELECT * FROM couponcodes WHERE name='"+coupon+"'");
			if (rs == null) return null;
			if (databaseHandler.getDatabaseOptions() instanceof MySQLOptions) rs.first();
			int usetimes = rs.getInt("usetimes");
			int time = rs.getInt("timeuse");
			String type = rs.getString("ctype");

			if (type.equalsIgnoreCase("Item"))
				return createNewItemCoupon(coupon, usetimes, time, null, null);
			else if (type.equalsIgnoreCase("Economy"))
				return createNewEconomyCoupon(coupon, usetimes, time, null, 0);
			else if (type.equalsIgnoreCase("Rank"))
				return createNewRankCoupon(coupon, null, usetimes, time, null);
			else if (type.equalsIgnoreCase("Xp"))
				return this.createNewXpCoupon(coupon, 0, usetimes, time, null);
			else if (type.equalsIgnoreCase("command"))
				return this.createNewCommandCoupon(coupon, null, usetimes, time, null);
			else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public HashMap<Integer, Integer> itemStringToHash(String args, CommandSender sender) {
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

	public BukkitPlugin getPlugin() {
		return plugin;
	}

}
