package com.drevelopment.couponcodes.core.util;

import java.util.Random;

public class RandomName {

private static Random random = new Random();

	public static String generateName() {
		String ref = "";
		while (ref.length() < 5)
			ref = ref+"qwertyuiopasdfghjklzxcvbnm1234567890".charAt(random.nextInt(36));
		return ref;
	}
}
