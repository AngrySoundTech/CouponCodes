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

public class Color {

    public static final char ESCAPE = '\u00A7';

    public static final String BLACK = ESCAPE + "0";
    public static final String DARK_BLUE = ESCAPE + "1";
    public static final String DARK_GREEN = ESCAPE + "2";
    public static final String DARK_AQUA = ESCAPE + "3";
    public static final String DARK_RED = ESCAPE + "4";
    public static final String DARK_PURPLE = ESCAPE + "5";
    public static final String GOLD = ESCAPE + "6";
    public static final String GRAY = ESCAPE + "7";
    public static final String DARK_GRAY = ESCAPE + "8";
    public static final String BLUE = ESCAPE + "9";
    public static final String GREEN = ESCAPE + "a";
    public static final String AQUA = ESCAPE + "b";
    public static final String RED = ESCAPE + "c";
    public static final String LIGHT_PURPLE = ESCAPE + "d";
    public static final String YELLOW = ESCAPE + "e";
    public static final String WHITE = ESCAPE + "f";
    public static final String MAGIC = ESCAPE + "k";
    public static final String BOLD = ESCAPE + "l";
    public static final String STRIKETHROUGH = ESCAPE + "m";
    public static final String UNDERLINE = ESCAPE + "n";
    public static final String ITALIC = ESCAPE + "o";
    public static final String RESET = ESCAPE + "r";

    public static String replaceColors(String text) {
        char[] chrarray = text.toCharArray();
        for (int index = 0; index < chrarray.length; index++) {
            char chr = chrarray[index];
            if (chr != '&') {
                continue;
            }
            if ((index + 1) == chrarray.length) {
                break;
            }
            char forward = chrarray[index + 1];
            if ((forward >= '0' && forward <= '9') || (forward >= 'a' && forward <= 'f') || (forward >= 'k' && forward <= 'r')) {
                chrarray[index] = ESCAPE;
            }
        }
        return new String(chrarray);
    }

}
