package net.Drepic.CouponCodes.api.events;

import java.sql.Connection;
import java.sql.ResultSet;

import net.Drepic.CouponCodes.api.coupon.Coupon;
import net.Drepic.CouponCodes.api.events.coupon.CouponAddToDatabaseEvent;
import net.Drepic.CouponCodes.api.events.coupon.CouponExpireEvent;
import net.Drepic.CouponCodes.api.events.coupon.CouponRemoveFromDatabaseEvent;
import net.Drepic.CouponCodes.api.events.coupon.CouponTimeChangeEvent;
import net.Drepic.CouponCodes.api.events.database.DatabaseCloseConnectionEvent;
import net.Drepic.CouponCodes.api.events.database.DatabaseOpenConnectionEvent;
import net.Drepic.CouponCodes.api.events.database.DatabaseQueryEvent;
import net.Drepic.CouponCodes.api.events.plugin.CouponCodesCommandEvent;
import net.Drepic.CouponCodes.sql.options.DatabaseOptions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class EventHandle {
	
	public static CouponAddToDatabaseEvent callCouponAddToDatabaseEvent(Coupon coupon) {
		CouponAddToDatabaseEvent ev = new CouponAddToDatabaseEvent(coupon);
		ev.call();
		return ev;
	}
	
	public static CouponRemoveFromDatabaseEvent callCouponRemoveFromDatabaseEvent(String coupon) {
		CouponRemoveFromDatabaseEvent ev = new CouponRemoveFromDatabaseEvent(coupon);
		ev.call();
		return ev;
	}
	
	public static CouponExpireEvent callCouponExpireEvent(Coupon coupon) {
		CouponExpireEvent ev = new CouponExpireEvent(coupon);
		ev.call();
		return ev;
	}
	
	public static DatabaseOpenConnectionEvent callDatabaseOpenConnectionEvent(Connection con, DatabaseOptions dop, Boolean success) {
		DatabaseOpenConnectionEvent ev = new DatabaseOpenConnectionEvent(con, dop, success);
		ev.call();
		return ev;
	}
	
	public static DatabaseCloseConnectionEvent callDatabaseCloseConnectionEvent(Connection con, DatabaseOptions dop) {
		DatabaseCloseConnectionEvent ev = new DatabaseCloseConnectionEvent(con, dop);
		ev.call();
		return ev;
	}
	
	public static DatabaseQueryEvent callDatabaseQueryEvent(DatabaseOptions dop, String query, ResultSet rs) {
		DatabaseQueryEvent ev = new DatabaseQueryEvent(dop, query, rs);
		ev.call();
		return ev;
	}
	
	public static CouponCodesCommandEvent callCouponCodesCommandEvent(CommandSender sender, Command command, String commandLabel, String[] args) {
		CouponCodesCommandEvent ev = new CouponCodesCommandEvent(sender, command, commandLabel, args);
		ev.call();
		return ev;
	}
	
	public static CouponTimeChangeEvent callCouponTimeChangeEvent(Coupon coupon) {
		CouponTimeChangeEvent ev = new CouponTimeChangeEvent(coupon);
		ev.call();
		return ev;
	}
}
