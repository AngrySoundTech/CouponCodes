package com.github.drepic26.couponcodes.canary.database;

import com.github.drepic26.couponcodes.api.database.DatabaseHandler;

public class CanaryDatabaseHandler implements DatabaseHandler {

	@Override
	public String getDatabaseType() {
		return "Canary";
	}

}
