package com.github.drepic26.couponcodes.api.config;

public interface ConfigHandler {

	public boolean getUseThread();

	public boolean getDebug();

	public boolean getUseMetrics();

	public boolean getAutoUpdate();

	public String getSQLValue();

	public String getHostname();

	public String getPort();

	public String getDatabase();

	public String getUsername();

	public String getPassword();

}
