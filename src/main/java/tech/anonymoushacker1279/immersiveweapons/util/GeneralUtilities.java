package tech.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import javax.annotation.Nullable;
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

	/**
	 * Adds an enchantment to an ItemStack, like {@link ItemStack#enchant(Enchantment, int)}, but uses a custom helper method.
	 *
	 * @param itemStack   The ItemStack to enchant
	 * @param enchantment The enchantment to add
	 * @param level       The level of the enchantment
	 */
	public static void unrestrictedEnchant(ItemStack itemStack, Enchantment enchantment, int level) {
		itemStack.getOrCreateTag();

		if (itemStack.getTag() == null) {
			return;
		}

		if (!itemStack.getTag().contains("Enchantments", 9)) {
			itemStack.getTag().put("Enchantments", new ListTag());
		}

		ListTag enchantments = itemStack.getTag().getList("Enchantments", 10);
		enchantments.add(storeEnchantment(EnchantmentHelper.getEnchantmentId(enchantment), level));
	}

	/**
	 * Stores an enchantment in a CompoundTag, like {@link EnchantmentHelper#storeEnchantment(ResourceLocation, int)}. This
	 * method does not cast the level to a short.
	 *
	 * @param location The location of the enchantment
	 * @param level    The level of the enchantment
	 * @return A CompoundTag representing the enchantment
	 */
	private static CompoundTag storeEnchantment(@Nullable ResourceLocation location, int level) {
		CompoundTag compoundtag = new CompoundTag();
		compoundtag.putString("id", String.valueOf(location));
		compoundtag.putInt("lvl", level);
		return compoundtag;
	}

	/**
	 * Get the total levels of all enchantments on an ItemStack.
	 *
	 * @param itemStack The ItemStack to check
	 * @return The total levels of all enchantments
	 */
	public static int getTotalEnchantmentLevels(ItemStack itemStack) {
		if (itemStack.isEnchanted()) {
			return itemStack.getAllEnchantments().values().stream().mapToInt(Integer::intValue).sum();
		}

		return 0;
	}
}