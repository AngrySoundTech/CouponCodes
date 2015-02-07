package com.drevelopment.couponcodes.canary.database;

import com.drevelopment.couponcodes.api.database.DatabaseHandler;

public class CanaryDatabaseHandler implements DatabaseHandler {

	@Override
	public String getDatabaseType() {
		return "Canary";
	}

}
