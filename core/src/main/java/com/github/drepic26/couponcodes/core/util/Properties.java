package com.github.drepic26.couponcodes.core.util;

import java.io.IOException;

public class Properties {

	private static java.util.Properties prop = new java.util.Properties();

	private static String version;

	static {
		loadProperties();
	}

	public static String getVersion() {
		return version;
	}

	private static void loadProperties() {
			try{
				prop.load(Properties.class.getResourceAsStream("/couponcodes.properties"));
				//load properties
				version = prop.getProperty("version");

			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
