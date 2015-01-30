package com.github.drepic26.couponcodes.canary.config;

import net.canarymod.config.Configuration;
import net.visualillusionsent.utils.PropertiesFile;

import com.github.drepic26.couponcodes.api.config.ConfigHandler;
import com.github.drepic26.couponcodes.canary.CanaryPlugin;

public class CanaryConfigHandler implements ConfigHandler {

	private PropertiesFile config;

	public CanaryConfigHandler(CanaryPlugin plugin) {
		config = Configuration.getPluginConfig(plugin);
	}

	@Override
	public boolean getUseThread() {
		return config.getBoolean("use-thread", true);
	}

	@Override
	public boolean getDebug() {
		return config.getBoolean("debug", false);
	}

	@Override
	public boolean getUseMetrics() {
		return config.getBoolean("use-metrics", true);
	}

	@Override
	public boolean getAutoUpdate() {
		return config.getBoolean("auto-update", true);
	}

	@Override
	public String getLocale() {
		return config.getString("locale", "en_US");
	}

}
