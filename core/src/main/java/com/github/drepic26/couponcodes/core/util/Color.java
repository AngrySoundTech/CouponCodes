package com.github.drepic26.couponcodes.core.util;

public class Color {

	public static final char ESCAPE = '\u00A7';

	public static final String BLACK = ESCAPE + "0";
	public static final String DARK_BLUE = ESCAPE + "1";
	public static final String DARK_GREEN = ESCAPE + "2";
	public static final String DARK_CYAN = ESCAPE + "3";
	public static final String DARK_RED = ESCAPE + "4";
	public static final String PURPLE = ESCAPE + "5";
	public static final String GOLD = ESCAPE + "6";
	public static final String GRAY = ESCAPE + "7";
	public static final String DARK_GRAY = ESCAPE + "8";
	public static final String BLUE = ESCAPE + "9";
	public static final String GREEN = ESCAPE + "a";
	public static final String CYAN = ESCAPE + "b";
	public static final String RED = ESCAPE + "c";
	public static final String PINK = ESCAPE + "d";
	public static final String YELLOW = ESCAPE + "e";
	public static final String WHITE = ESCAPE + "f";

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
