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

import tech.feldman.couponcodes.api.CouponCodes
import java.text.MessageFormat
import java.util.*

object LocaleHandler {

    private val BUNDLE_ROOT = "tech.feldman.couponcodes.locale.locale"

    private var bundle: ResourceBundle
    private var enBundle: ResourceBundle

    init {
        Locale.setDefault(Locale("en", "US"))
        var locale: Locale? = null
        val myLocale = CouponCodes.getConfigHandler().locale.split("[-_ ]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        if (myLocale.size == 1) {
            locale = Locale(myLocale[0])
        } else if (myLocale.size >= 2) {
            locale = Locale(myLocale[0], myLocale[1])
        }

        bundle = ResourceBundle.getBundle(BUNDLE_ROOT, locale!!)
        enBundle = ResourceBundle.getBundle(BUNDLE_ROOT, Locale.US)
    }

    /**
     * Gets the appropriate string from the Locale files.

     * @param key The key to look up the string with
     * *
     * @param messageArguments Any arguments to be added to the string
     * *
     * @return The properly formatted locale string
     */
    @JvmOverloads
    fun getString(key: String, vararg messageArguments: Any = null as Array<Any>): String {
        if (bundle == null) {
            initialize()
        }

        try {
            return getString(key, bundle, *messageArguments)
        } catch (ex: MissingResourceException) {
            try {
                return getString(key, enBundle, *messageArguments)
            } catch (ex2: MissingResourceException) {
                if (!key.contains("Guides")) {
                    println("Could not find locale string: " + key)
                }

                return "!$key!"
            }

        }

    }

    @Throws(MissingResourceException::class)
    private fun getString(key: String, bundle: ResourceBundle, vararg messageArguments: Any): String {
        return formatString(bundle.getString(key), *messageArguments)
    }

    fun formatString(string: String, vararg messageArguments: Any): String {
        var string = string
        if (messageArguments != null) {
            val formatter = MessageFormat("")
            formatter.applyPattern(string)
            string = formatter.format(messageArguments)
        }

        string = addColors(string)

        return string
    }

    val currentLocale: Locale
        get() {
            if (bundle == null) {
                initialize()
            }
            return bundle!!.locale
        }

    private fun initialize() {

    }

    private fun addColors(input: String): String {
        var input = input
        input = input.replace("\\Q[[BLACK]]\\E".toRegex(), Color.BLACK)
        input = input.replace("\\Q[[DARK_BLUE]]\\E".toRegex(), Color.DARK_BLUE)
        input = input.replace("\\Q[[DARK_GREEN]]\\E".toRegex(), Color.DARK_GREEN)
        input = input.replace("\\Q[[DARK_AQUA]]\\E".toRegex(), Color.DARK_AQUA)
        input = input.replace("\\Q[[DARK_RED]]\\E".toRegex(), Color.DARK_RED)
        input = input.replace("\\Q[[DARK_PURPLE]]\\E".toRegex(), Color.DARK_PURPLE)
        input = input.replace("\\Q[[GOLD]]\\E".toRegex(), Color.GOLD)
        input = input.replace("\\Q[[GRAY]]\\E".toRegex(), Color.GRAY)
        input = input.replace("\\Q[[DARK_GRAY]]\\E".toRegex(), Color.DARK_GRAY)
        input = input.replace("\\Q[[BLUE]]\\E".toRegex(), Color.BLUE)
        input = input.replace("\\Q[[GREEN]]\\E".toRegex(), Color.GREEN)
        input = input.replace("\\Q[[AQUA]]\\E".toRegex(), Color.AQUA)
        input = input.replace("\\Q[[RED]]\\E".toRegex(), Color.RED)
        input = input.replace("\\Q[[LIGHT_PURPLE]]\\E".toRegex(), Color.LIGHT_PURPLE)
        input = input.replace("\\Q[[YELLOW]]\\E".toRegex(), Color.YELLOW)
        input = input.replace("\\Q[[WHITE]]\\E".toRegex(), Color.WHITE)
        input = input.replace("\\Q[[BOLD]]\\E".toRegex(), Color.BOLD)
        input = input.replace("\\Q[[UNDERLINE]]\\E".toRegex(), Color.UNDERLINE)
        input = input.replace("\\Q[[ITALIC]]\\E".toRegex(), Color.ITALIC)
        input = input.replace("\\Q[[STRIKE]]\\E".toRegex(), Color.STRIKETHROUGH)
        input = input.replace("\\Q[[MAGIC]]\\E".toRegex(), Color.MAGIC)
        input = input.replace("\\Q[[RESET]]\\E".toRegex(), Color.RESET)

        return input
    }

}
