package tech.anonymoushacker1279.immersiveweapons.util;

import java.util.UUID;

/**
 * A collection of random utility methods for general use.
 */
public class GeneralUtilities {


	/**
	 * Get a random number between a minimum and maximum.
	 *
	 * @param min minimum number (inclusive)
	 * @param max maximum number (not inclusive)
	 * @return float
	 */
	public static float getRandomNumber(float min, float max) {
		return (float) ((Math.random() * (max - min)) + min);
	}

	/**
	 * Get a random number between a minimum and maximum.
	 *
	 * @param min minimum number (inclusive)
	 * @param max maximum number (not inclusive)
	 * @return double
	 */
	public static double getRandomNumber(double min, double max) {
		return (Math.random() * (max - min)) + min;
	}

	/**
	 * Get a random number between a minimum and maximum.
	 *
	 * @param min minimum number (inclusive)
	 * @param max maximum number (not inclusive)
	 * @return int
	 */
	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	/**
	 * Convert an integer to a Roman numeral.
	 *
	 * @param number the integer to convert
	 * @return the Roman numeral
	 */
	public static String convertToRoman(int number) {
		if (number < 1 || number > 3999) {
			throw new IllegalArgumentException("Number must be between 1 and 3999.");
		}

		String[] thousands = {"", "M", "MM", "MMM"};
		String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
		String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
		String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

		return thousands[number / 1000] +
				hundreds[(number % 1000) / 100] +
				tens[(number % 100) / 10] +
				ones[number % 10];
	}

	/**
	 * Check if the specified UUID is not Jonny's. Used as a dev bonus for the Jonny's Curse item, where all effects
	 * are inverted for Jonny himself.
	 *
	 * @param uuid the UUID to check
	 * @return true if the UUID is not Jonny's, false otherwise
	 */
	public static boolean notJonny(UUID uuid) {
		return !uuid.toString().equals("009c8c55-8a9e-4664-9bd5-f4c15ccaf726");
	}
}