package com.github.drepic26.couponcodes.api.config;

public interface ConfigHandler {

	/**
	 * Gets whether or not a thread should be created for timed coupons.
	 * <p> If this is false, then timed coupons will not function
	 * @return boolean specified in config.
	 */
	public boolean getUseThread();

	/**
	 * Gets whether or not debug messages should be printed to the console
	 * @return boolean specified in config.
	 */
	public boolean getDebug();

	/**
	 * Gets whether or not the plugin should use Metrics.
	 * <p> These statistics can be viewed <a href='http://mcstats.org/plugin/CouponCodes'>here</a>
	 * @return boolean specified in config
	 */
	public boolean getUseMetrics();

	/**
	 * Gets whether or not the plugin should update itself automatically
	 * @return boolean specified in config
	 */
	public boolean getAutoUpdate();

	/**
	 * Gets the default locale to use
	 * @return the default locale specified in the config, in the form <code>en_US</code>
	 */
	public String getLocale();

}
