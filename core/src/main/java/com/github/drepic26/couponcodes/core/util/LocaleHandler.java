package com.github.drepic26.couponcodes.core.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.github.drepic26.couponcodes.api.CouponCodes;
import com.github.drepic26.couponcodes.api.command.CommandSender;

public final class LocaleHandler {

	private static final String BUNDLE_ROOT = "com.github.drepic26.couponcodes.core.locale.locale";

	private static ResourceBundle bundle = null;
	private static ResourceBundle enUSBundle = null;
	private static ResourceBundle enPTBundle = null;

	public static String getString(String key) {
		return getString(null, key);
	}

	public static String getString(CommandSender sender, String key) {
		return getString(sender, key, (Object[]) null);
	}

	public static String getString(CommandSender sender, String key, Object... messageArguments) {
		if (bundle == null) {
			initialize();
		}
		try {
			return getString(key, getBundleFromString(sender.getLocale()), messageArguments);
		} catch (MissingResourceException e) {
			try {
				return getString(key, bundle, messageArguments);
			} catch (MissingResourceException e2) {
				System.out.println("Could not find locale string: " + key);
			}
			return "!" + key + "!";
		}
	}

	private static ResourceBundle getBundleFromString(String locale) {
		if (locale.equalsIgnoreCase("en_US")) {
			return enUSBundle;
		} else
		if (locale.equalsIgnoreCase("en_PT")) {
			return enPTBundle;
		} else return bundle;
	}

	private static String getString(String key, ResourceBundle bundle, Object... messageArguments) throws MissingResourceException {
		return formatString(bundle.getString(key), messageArguments);
	}

	public static String formatString(String string, Object... messageArguments) {
		if (messageArguments != null) {
			MessageFormat formatter  = new MessageFormat("");
			formatter.applyPattern(string);
			string = formatter.format(messageArguments);
		}

		string = addColors(string);

		return string;
	}

	private static void initialize() {
		if (bundle == null) {
			Locale.setDefault(new Locale("en", "US"));
			Locale locale = null;
			String[] myLocale = CouponCodes.getConfigHandler().getLocale().split("[-_ ]");
			if (myLocale.length == 1) {
				locale = new Locale(myLocale[0]);
			}
			else if (myLocale.length >= 2) {
				locale = new Locale(myLocale[0], myLocale[1]);
			}
			bundle = ResourceBundle.getBundle(BUNDLE_ROOT, locale);
			enPTBundle = ResourceBundle.getBundle(BUNDLE_ROOT, new Locale("en", "PT"));
			enUSBundle = ResourceBundle.getBundle(BUNDLE_ROOT, Locale.US);

		}
	}

	private static String addColors(String input) {
		input = input.replaceAll("\\\\Q[[BLACK]]\\\\E", Color.BLACK.toString());
		input = input.replaceAll("\\\\Q[[DARK_BLUE]]\\\\E", Color.DARK_BLUE.toString());
		input = input.replaceAll("\\\\Q[[DARK_GREEN]]\\\\E", Color.DARK_GREEN.toString());
		input = input.replaceAll("\\\\Q[[DARK_AQUA]]\\\\E", Color.DARK_AQUA.toString());
		input = input.replaceAll("\\\\Q[[DARK_RED]]\\\\E", Color.DARK_RED.toString());
		input = input.replaceAll("\\\\Q[[DARK_PURPLE]]\\\\E", Color.DARK_PURPLE.toString());
		input = input.replaceAll("\\\\Q[[GOLD]]\\\\E", Color.GOLD.toString());
		input = input.replaceAll("\\\\Q[[GRAY]]\\\\E", Color.GRAY.toString());
		input = input.replaceAll("\\\\Q[[DARK_GRAY]]\\\\E", Color.DARK_GRAY.toString());
		input = input.replaceAll("\\\\Q[[BLUE]]\\\\E", Color.BLUE.toString());
		input = input.replaceAll("\\\\Q[[GREEN]]\\\\E", Color.GREEN.toString());
		input = input.replaceAll("\\\\Q[[AQUA]]\\\\E", Color.AQUA.toString());
		input = input.replaceAll("\\\\Q[[RED]]\\\\E", Color.RED.toString());
		input = input.replaceAll("\\\\Q[[LIGHT_PURPLE]]\\\\E", Color.LIGHT_PURPLE.toString());
		input = input.replaceAll("\\\\Q[[YELLOW]]\\\\E", Color.YELLOW.toString());
		input = input.replaceAll("\\\\Q[[WHITE]]\\\\E", Color.WHITE.toString());
		input = input.replaceAll("\\\\Q[[BOLD]]\\\\E", Color.BOLD.toString());
		input = input.replaceAll("\\\\Q[[UNDERLINE]]\\\\E", Color.UNDERLINE.toString());
		input = input.replaceAll("\\\\Q[[ITALIC]]\\\\E", Color.ITALIC.toString());
		input = input.replaceAll("\\\\Q[[STRIKE]]\\\\E", Color.STRIKETHROUGH.toString());
		input = input.replaceAll("\\\\Q[[MAGIC]]\\\\E", Color.MAGIC.toString());
		input = input.replaceAll("\\\\Q[[RESET]]\\\\E", Color.RESET.toString());

		return input;
	}

}
