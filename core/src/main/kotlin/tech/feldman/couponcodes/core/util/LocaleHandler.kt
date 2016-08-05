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
package tech.feldman.couponcodes.core.util

import java.text.MessageFormat
import java.util.*

object LocaleHandler {

    private val BUNDLE_ROOT = "tech.feldman.couponcodes.locale.locale"

    private var bundle: ResourceBundle = ResourceBundle.getBundle(BUNDLE_ROOT, Locale.US)
    private val enBundle: ResourceBundle = ResourceBundle.getBundle(BUNDLE_ROOT, Locale.US)

    var locale: Locale
        get() = bundle.locale
        set(value) {
            bundle = ResourceBundle.getBundle(BUNDLE_ROOT, value)
        }

    /**
     * Gets the appropriate string from the Locale files.

     * @param key The key to look up the string with
     * @param messageArguments Any arguments to be added to the string
     * @return The properly formatted locale string
     */
    fun getString(key: String, vararg messageArguments: Any): String {
        try {
            return getString(key, if (bundle.containsKey(key)) bundle else enBundle, *messageArguments)
        } catch (e: MissingResourceException) {
            return key
        }

    }

    private fun getString(key: String, bundle: ResourceBundle, vararg messageArguments: Any): String {
        return formatString(bundle.getString(key), *messageArguments)
    }

    private fun formatString(string: String, vararg messageArguments: Any): String {
        return addColors(MessageFormat(string).format(messageArguments))
    }

    private fun addColors(input: String): String {
        return input
                .replace("\\Q[[BLACK]]\\E".toRegex(), Color.BLACK)
                .replace("\\Q[[DARK_BLUE]]\\E".toRegex(), Color.DARK_BLUE)
                .replace("\\Q[[DARK_GREEN]]\\E".toRegex(), Color.DARK_GREEN)
                .replace("\\Q[[DARK_AQUA]]\\E".toRegex(), Color.DARK_AQUA)
                .replace("\\Q[[DARK_RED]]\\E".toRegex(), Color.DARK_RED)
                .replace("\\Q[[DARK_PURPLE]]\\E".toRegex(), Color.DARK_PURPLE)
                .replace("\\Q[[GOLD]]\\E".toRegex(), Color.GOLD)
                .replace("\\Q[[GRAY]]\\E".toRegex(), Color.GRAY)
                .replace("\\Q[[DARK_GRAY]]\\E".toRegex(), Color.DARK_GRAY)
                .replace("\\Q[[BLUE]]\\E".toRegex(), Color.BLUE)
                .replace("\\Q[[GREEN]]\\E".toRegex(), Color.GREEN)
                .replace("\\Q[[AQUA]]\\E".toRegex(), Color.AQUA)
                .replace("\\Q[[RED]]\\E".toRegex(), Color.RED)
                .replace("\\Q[[LIGHT_PURPLE]]\\E".toRegex(), Color.LIGHT_PURPLE)
                .replace("\\Q[[YELLOW]]\\E".toRegex(), Color.YELLOW)
                .replace("\\Q[[WHITE]]\\E".toRegex(), Color.WHITE)
                .replace("\\Q[[BOLD]]\\E".toRegex(), Color.BOLD)
                .replace("\\Q[[UNDERLINE]]\\E".toRegex(), Color.UNDERLINE)
                .replace("\\Q[[ITALIC]]\\E".toRegex(), Color.ITALIC)
                .replace("\\Q[[STRIKE]]\\E".toRegex(), Color.STRIKETHROUGH)
                .replace("\\Q[[MAGIC]]\\E".toRegex(), Color.MAGIC)
                .replace("\\Q[[RESET]]\\E".toRegex(), Color.RESET)

    }

}
