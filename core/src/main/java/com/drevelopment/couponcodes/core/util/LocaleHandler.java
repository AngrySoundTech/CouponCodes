/**
 * The MIT License
 * Copyright (c) 2015 Nicholas Feldman (AngrySoundTech)
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.drevelopment.couponcodes.core.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.drevelopment.couponcodes.api.CouponCodes;

public final class LocaleHandler {

    private static final String BUNDLE_ROOT = "com.drevelopment.couponcodes.locale.locale";

    private static ResourceBundle bundle = null;
    private static ResourceBundle enBundle = null;

    public static String getString(String key) {
        return getString(key, (Object[]) null);
    }

    /**
     * Gets the appropriate string from the Locale files.
     *
     * @param key The key to look up the string with
     * @param messageArguments Any arguments to be added to the string
     * @return The properly formatted locale string
     */
    public static String getString(String key, Object... messageArguments) {
        if (bundle == null) {
            initialize();
        }

        try {
            return getString(key, bundle, messageArguments);
        } catch (MissingResourceException ex) {
            try {
                return getString(key, enBundle, messageArguments);
            } catch (MissingResourceException ex2) {
                if (!key.contains("Guides")) {
                    System.out.println("Could not find locale string: " + key);
                }

                return '!' + key + '!';
            }
        }
    }

    private static String getString(String key, ResourceBundle bundle, Object... messageArguments) throws MissingResourceException {
        return formatString(bundle.getString(key), messageArguments);
    }

    public static String formatString(String string, Object... messageArguments) {
        if (messageArguments != null) {
            MessageFormat formatter = new MessageFormat("");
            formatter.applyPattern(string);
            string = formatter.format(messageArguments);
        }

        string = addColors(string);

        return string;
    }

    public static Locale getCurrentLocale() {
        if (bundle == null) {
            initialize();
        }
        return bundle.getLocale();
    }

    private static void initialize() {
        if (bundle == null) {
            Locale.setDefault(new Locale("en", "US"));
            Locale locale = null;
            String[] myLocale = CouponCodes.getConfigHandler().getLocale().split("[-_ ]");

            if (myLocale.length == 1) {
                locale = new Locale(myLocale[0]);
            } else if (myLocale.length >= 2) {
                locale = new Locale(myLocale[0], myLocale[1]);
            }

            bundle = ResourceBundle.getBundle(BUNDLE_ROOT, locale);
            enBundle = ResourceBundle.getBundle(BUNDLE_ROOT, Locale.US);
        }
    }

    private static String addColors(String input) {
        input = input.replaceAll("\\Q[[BLACK]]\\E", Color.BLACK);
        input = input.replaceAll("\\Q[[DARK_BLUE]]\\E", Color.DARK_BLUE);
        input = input.replaceAll("\\Q[[DARK_GREEN]]\\E", Color.DARK_GREEN);
        input = input.replaceAll("\\Q[[DARK_AQUA]]\\E", Color.DARK_AQUA);
        input = input.replaceAll("\\Q[[DARK_RED]]\\E", Color.DARK_RED);
        input = input.replaceAll("\\Q[[DARK_PURPLE]]\\E", Color.DARK_PURPLE);
        input = input.replaceAll("\\Q[[GOLD]]\\E", Color.GOLD);
        input = input.replaceAll("\\Q[[GRAY]]\\E", Color.GRAY);
        input = input.replaceAll("\\Q[[DARK_GRAY]]\\E", Color.DARK_GRAY);
        input = input.replaceAll("\\Q[[BLUE]]\\E", Color.BLUE);
        input = input.replaceAll("\\Q[[GREEN]]\\E", Color.GREEN);
        input = input.replaceAll("\\Q[[AQUA]]\\E", Color.AQUA);
        input = input.replaceAll("\\Q[[RED]]\\E", Color.RED);
        input = input.replaceAll("\\Q[[LIGHT_PURPLE]]\\E", Color.LIGHT_PURPLE);
        input = input.replaceAll("\\Q[[YELLOW]]\\E", Color.YELLOW);
        input = input.replaceAll("\\Q[[WHITE]]\\E", Color.WHITE);
        input = input.replaceAll("\\Q[[BOLD]]\\E", Color.BOLD);
        input = input.replaceAll("\\Q[[UNDERLINE]]\\E", Color.UNDERLINE);
        input = input.replaceAll("\\Q[[ITALIC]]\\E", Color.ITALIC);
        input = input.replaceAll("\\Q[[STRIKE]]\\E", Color.STRIKETHROUGH);
        input = input.replaceAll("\\Q[[MAGIC]]\\E", Color.MAGIC);
        input = input.replaceAll("\\Q[[RESET]]\\E", Color.RESET);

        return input;
    }

}
