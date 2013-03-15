package net.Drepic.CouponCodes.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import net.Drepic.CouponCodes.CouponCodes;
import net.Drepic.CouponCodes.api.coupon.Coupon;
import net.Drepic.CouponCodes.api.coupon.EconomyCoupon;
import net.Drepic.CouponCodes.api.coupon.ItemCoupon;
import net.Drepic.CouponCodes.api.coupon.RankCoupon;
import net.Drepic.CouponCodes.api.coupon.XpCoupon;
import net.Drepic.CouponCodes.api.events.EventHandle;
import net.Drepic.CouponCodes.sql.SQL;
import net.Drepic.CouponCodes.sql.options.MySQLOptions;

public class CouponManager {
	
	private CouponCodes plugin;
	private SQL sql;
	
	public CouponManager(CouponCodes plugin, SQL sql) {
		this.plugin = plugin;
		this.sql = sql;
	}
	
	public boolean addCouponToDatabase(Coupon coupon) throws SQLException {
		if (couponExists(coupon)) return false;
		Connection con = sql.getConnection();
		PreparedStatement p = null;
		
		if (coupon instanceof ItemCoupon) {
			ItemCoupon c = (ItemCoupon) coupon;
			p = con.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, ids, timeuse) "+
					"VALUES (?, ?, ?, ?, ?, ?)");
			p.setString(1, c.getName());
			p.setString(2, c.getType());
			p.setInt(3, c.getUseTimes());
			p.setString(4, plugin.convertHashToString2(c.getUsedPlayers()));
			p.setString(5, plugin.convertHashToString(c.getIDs()));
			p.setInt(6, c.getTime());
		}
		
		else if (coupon instanceof EconomyCoupon) {
			EconomyCoupon c = (EconomyCoupon) coupon;
			p = con.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, money, timeuse) "+
					"VALUES (?, ?, ?, ?, ?, ?)");
			p.setString(1, c.getName());
			p.setString(2, c.getType());
			p.setInt(3, c.getUseTimes());
			p.setString(4, plugin.convertHashToString2(c.getUsedPlayers()));
			p.setInt(5, c.getMoney());
			p.setInt(6, c.getTime());
		}
		
		else if (coupon instanceof RankCoupon) {
			RankCoupon c = (RankCoupon) coupon;
			p = con.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, groupname, timeuse) "+
					"VALUES (?, ?, ?, ?, ?, ?)");
			p.setString(1, c.getName());
			p.setString(2, c.getType());
			p.setInt(3, c.getUseTimes());
			p.setString(4, plugin.convertHashToString2(c.getUsedPlayers()));
			p.setString(5, c.getGroup());
			p.setInt(6, c.getTime());
		}
		
		else if (coupon instanceof XpCoupon) {
			XpCoupon c = (XpCoupon) coupon;
			p = con.prepareStatement("INSERT INTO couponcodes (name, ctype, usetimes, usedplayers, timeuse, xp) "+
					"VALUES (?, ?, ?, ?, ?, ?)");
			p.setString(1, c.getName());
			p.setString(2, c.getType());
			p.setInt(3, c.getUseTimes());
			p.setString(4, plugin.convertHashToString2(c.getUsedPlayers()));
			p.setInt(5, c.getTime());
			p.setInt(6, c.getXp());
		}
		
		p.addBatch();
		con.setAutoCommit(false);
		p.executeBatch();
		con.setAutoCommit(true);
		EventHandle.callCouponAddToDatabaseEvent(coupon);
		return true;
	}
	
	public boolean removeCouponFromDatabase(Coupon coupon) throws SQLException {
		if (!couponExists(coupon)) return false;
		sql.query("DELETE FROM couponcodes WHERE name='"+coupon.getName()+"'");
		EventHandle.callCouponRemoveFromDatabaseEvent(coupon.getName());
		return true;
	}
	
	public boolean removeCouponFromDatabase(String coupon) throws SQLException {
		if (!couponExists(coupon)) return false;
		sql.query("DELETE FROM couponcodes WHERE name='"+coupon+"'");
		EventHandle.callCouponRemoveFromDatabaseEvent(coupon);
		return true;
	}
	
	public boolean couponExists(Coupon coupon) throws SQLException {
		return getCoupons().contains(coupon.getName());
	}
	
	public boolean couponExists(String name) throws SQLException {
		return getCoupons().contains(name);
	}
	
	public ArrayList<String> getCoupons() throws SQLException {
		ArrayList<String> c = new ArrayList<String>();
		try {
			ResultSet rs = sql.query("SELECT name FROM couponcodes");
			if (rs == null) return c;
			while (rs.next())
				c.add(rs.getString(1));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public void updateCoupon(Coupon coupon) throws SQLException {
		sql.query("UPDATE couponcodes SET usetimes='"+coupon.getUseTimes()+"' WHERE name='"+coupon.getName()+"'");
		sql.query("UPDATE couponcodes SET usedplayers='"+plugin.convertHashToString2(coupon.getUsedPlayers())+"' WHERE name='"+coupon.getName()+"'");
		sql.query("UPDATE couponcodes SET timeuse='"+coupon.getTime()+"' WHERE name='"+coupon.getName()+"'");
		
		if (coupon instanceof ItemCoupon)
			sql.query("UPDATE couponcodes SET ids='"+plugin.convertHashToString(((ItemCoupon) coupon).getIDs())+"' WHERE name='"+coupon.getName()+"'");
		else if (coupon instanceof EconomyCoupon)
			sql.query("UPDATE couponcodes SET money='"+((EconomyCoupon) coupon).getMoney()+"' WHERE name='"+coupon.getName()+"'");
		else if (coupon instanceof RankCoupon)
			sql.query("UPDATE couponcodes SET groupname='"+((RankCoupon) coupon).getGroup()+"' WHERE name='"+coupon.getName()+"'");
		else if (coupon instanceof XpCoupon)
			sql.query("UPDATE couponcodes SET xp='"+((XpCoupon) coupon).getXp()+"' WHERE name='"+coupon.getName()+"'");
	}
	
	public void updateCouponTime(Coupon c) throws SQLException {
		sql.query("UPDATE couponcodes SET timeuse='"+c.getTime()+"' WHERE name='"+c.getName()+"'");
	}
	
	public Coupon getCoupon(String coupon) throws SQLException {
		if (!couponExists(coupon)) return null;
		ResultSet rs = sql.query("SELECT * FROM couponcodes WHERE name='"+coupon+"'");
		if (sql.getDatabaseOptions() instanceof MySQLOptions) rs.first();
		int usetimes = rs.getInt("usetimes");
		int time = rs.getInt("timeuse");
		HashMap<String, Boolean> usedplayers = plugin.convertStringToHash2(rs.getString("usedplayers"));
		
		if (rs.getString("ctype").equalsIgnoreCase("Item"))
			return createNewItemCoupon(coupon, usetimes, time, plugin.convertStringToHash(rs.getString("ids")), usedplayers);
		else if (rs.getString("ctype").equalsIgnoreCase("Economy"))
			return createNewEconomyCoupon(coupon, usetimes, time, usedplayers, rs.getInt("money"));
		else if (rs.getString("ctype").equalsIgnoreCase("Rank"))
			return createNewRankCoupon(coupon, rs.getString("groupname"), usetimes, time, usedplayers);
		else if (rs.getString("ctype").equalsIgnoreCase("Xp"))
			return createNewXpCoupon(coupon, rs.getInt("xp"), usetimes, time, usedplayers);
		else
			return null;
	}
	
	public Coupon getBasicCoupon(String coupon) throws SQLException {
		ResultSet rs = sql.query("SELECT * FROM couponcodes WHERE name='"+coupon+"'");
		if (rs == null) return null;
		if (sql.getDatabaseOptions() instanceof MySQLOptions) rs.first();
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
		else
			return null;
	}
	
	public int getAmountOf(String type) throws SQLException {
		ArrayList<String> list = getCoupons();
		int item = 0;
		int econ = 0;
		int rank = 0;
		int xp = 0;
		
		for (String name : list) {
			Coupon c = getBasicCoupon(name);
			if (c instanceof ItemCoupon) item++;
			if (c instanceof EconomyCoupon) econ++;
			if (c instanceof RankCoupon) rank++;
			if (c instanceof XpCoupon) xp++;
		}
		
		if (type.equalsIgnoreCase("Item"))
			return item;
		else if (type.equalsIgnoreCase("Economy"))
			return econ;
		else if (type.equalsIgnoreCase("Rank"))
			return rank;
		else if (type.equalsIgnoreCase("Xp"))
			return xp;
		else
			return 0;
	}
	
	public ItemCoupon createNewItemCoupon(String name, int usetimes, int time, HashMap<Integer, Integer> ids, HashMap<String, Boolean> usedplayers) {
		return new ItemCoupon(name, usetimes, time, usedplayers, ids);
	}
	
	public EconomyCoupon createNewEconomyCoupon(String name, int usetimes, int time, HashMap<String, Boolean> usedplayers, int money) {
		return new EconomyCoupon(name, usetimes, time, usedplayers, money);
	}
	
	public RankCoupon createNewRankCoupon(String name, String group, int usetimes, int time, HashMap<String, Boolean> usedplayers) {
		return new RankCoupon(name, group, usetimes, time, usedplayers);
	}
	
	public XpCoupon createNewXpCoupon(String name, int xp, int usetimes, int time, HashMap<String, Boolean> usedplayers) {
		return new XpCoupon(name, usetimes, time, usedplayers, xp);
	}
	
	public SQL getSQL() {
		return sql;
	}
}
