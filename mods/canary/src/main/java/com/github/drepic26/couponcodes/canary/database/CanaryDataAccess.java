package com.github.drepic26.couponcodes.canary.database;

import net.canarymod.database.Column;
import net.canarymod.database.DataAccess;
import net.canarymod.database.Column.DataType;

public class CanaryDataAccess extends DataAccess {

	public CanaryDataAccess() {
		super("canary_coupon_object");
	}

	@Column(columnName="name", dataType=DataType.STRING)
	public String name;

	@Column(columnName="ctype", dataType=DataType.STRING)
	public String ctype;

	@Column(columnName="usetimes", dataType=DataType.INTEGER)
	public int usetimes;

	@Column(columnName="usedplayers", dataType=DataType.STRING)
	public String usedplayers;

	@Column(columnName="ids", dataType=DataType.STRING)
	public String ids;

	@Column(columnName="money", dataType=DataType.INTEGER)
	public int money;

	@Column(columnName="groupname", dataType=DataType.STRING)
	public String groupname;

	@Column(columnName="timeuse", dataType=DataType.INTEGER)
	public int timeuse;

	@Column(columnName="xp", dataType=DataType.INTEGER)
	public int xp;

	@Override
	public DataAccess getInstance() {
		return new CanaryDataAccess();
	}

}
