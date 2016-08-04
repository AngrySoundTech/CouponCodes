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

object Color {

    val ESCAPE = '\u00A7'

    val BLACK = ESCAPE + "0"
    val DARK_BLUE = ESCAPE + "1"
    val DARK_GREEN = ESCAPE + "2"
    val DARK_AQUA = ESCAPE + "3"
    val DARK_RED = ESCAPE + "4"
    val DARK_PURPLE = ESCAPE + "5"
    val GOLD = ESCAPE + "6"
    val GRAY = ESCAPE + "7"
    val DARK_GRAY = ESCAPE + "8"
    val BLUE = ESCAPE + "9"
    val GREEN = ESCAPE + "a"
    val AQUA = ESCAPE + "b"
    val RED = ESCAPE + "c"
    val LIGHT_PURPLE = ESCAPE + "d"
    val YELLOW = ESCAPE + "e"
    val WHITE = ESCAPE + "f"
    val MAGIC = ESCAPE + "k"
    val BOLD = ESCAPE + "l"
    val STRIKETHROUGH = ESCAPE + "m"
    val UNDERLINE = ESCAPE + "n"
    val ITALIC = ESCAPE + "o"
    val RESET = ESCAPE + "r"

    fun replaceColors(text: String): String {
        val chrarray = text.toCharArray()
        for (index in chrarray.indices) {
            val chr = chrarray[index]
            if (chr != '&') {
                continue
            }
            if (index + 1 == chrarray.size) {
                break
            }
            val forward = chrarray[index + 1]
            if (forward >= '0' && forward <= '9' || forward >= 'a' && forward <= 'f' || forward >= 'k' && forward <= 'r') {
                chrarray[index] = ESCAPE
            }
        }
        return String(chrarray)
    }

}
