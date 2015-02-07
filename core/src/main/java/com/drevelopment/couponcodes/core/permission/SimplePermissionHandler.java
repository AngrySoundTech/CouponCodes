package com.drevelopment.couponcodes.core.permission;

import java.util.Set;

import com.drevelopment.couponcodes.core.entity.SimplePlayer;
import com.drevelopment.couponcodes.core.util.LocaleHandler;

public class SimplePermissionHandler {

	public String getName() {
		return "Nope";
	}

	public boolean isEnabled() {
		return false;
	}

	public boolean hasPermission(SimplePlayer player, String node) {
		throw new UnsupportedOperationException("No permission handler");
	}

	public Set<String> getGroups(SimplePlayer player) {
		throw new UnsupportedOperationException(LocaleHandler.getString("No permission handler"));
	}
}
