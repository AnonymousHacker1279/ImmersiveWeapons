package tech.anonymoushacker1279.immersiveweapons.util;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A collection of random utility methods for general use.
 */
public class GeneralUtilities {

	public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("9f470b49-0445-4341-ae85-55b9e5ec2a1c");


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
	 * Check if the specified UUID is not Jonny's. Used as a dev bonus for the Jonny's Curse item, where all effects are
	 * inverted for Jonny himself.
	 *
	 * @param uuid the UUID to check
	 * @return true if the UUID is not Jonny's, false otherwise
	 */
	public static boolean notJonny(UUID uuid) {
		return !uuid.toString().equals("009c8c55-8a9e-4664-9bd5-f4c15ccaf726");
	}

	/**
	 * Get the total levels of all enchantments on an ItemStack.
	 *
	 * @param itemStack The ItemStack to check
	 * @param lookup    The enchantment registry
	 * @return The total levels of all enchantments
	 */
	public static int getTotalEnchantmentLevels(RegistryLookup<Enchantment> lookup, ItemStack itemStack) {
		if (itemStack.isEnchanted()) {
			return itemStack.getAllEnchantments(lookup).entrySet().stream().mapToInt(Entry::getIntValue).sum();
		}

		return 0;
	}

	/**
	 * Rotate a VoxelShape to a given horizontal direction.
	 *
	 * @param from  the direction the shape is currently facing
	 * @param to    the direction the shape should be facing
	 * @param shape the shape to rotate
	 * @return the rotated shape
	 */
	public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
		if (from.getAxis() == Axis.Y || to.getAxis() == Axis.Y) {
			throw new IllegalArgumentException("Cannot rotate around the Y axis.");
		}

		if (from == to) {
			return shape;
		}

		List<AABB> sourceBoxes = shape.toAabbs();
		VoxelShape rotatedShape = Shapes.empty();
		int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
		for (AABB box : sourceBoxes) {
			for (int i = 0; i < times; i++) {
				box = new AABB(1 - box.maxZ, box.minY, box.minX, 1 - box.minZ, box.maxY, box.maxX);
			}
			rotatedShape = Shapes.joinUnoptimized(rotatedShape, Shapes.create(box), BooleanOp.OR);
		}

		return rotatedShape;
	}

	/**
	 * Enchant the gear of a given entity.
	 *
	 * @param mob       the entity
	 * @param doWeapons whether to enchant weapons
	 * @param doArmor   whether to enchant armor
	 */
	public static void enchantGear(Mob mob, boolean doWeapons, boolean doArmor) {
		RandomSource random = mob.getRandom();
		DifficultyInstance difficulty = mob.level().getCurrentDifficultyAt(mob.blockPosition());

		float specialMultiplier = difficulty.getSpecialMultiplier();

		if (doWeapons) {
			enchantSpawnedWeapon(mob, random, specialMultiplier);
		}

		if (doArmor) {
			enchantSpawnedArmor(mob, random, specialMultiplier);
		}
	}

	/**
	 * Enchant the weapon of a spawned entity.
	 *
	 * @param mob              the entity
	 * @param random           the random source
	 * @param chanceMultiplier the chance multiplier
	 */
	public static void enchantSpawnedWeapon(Mob mob, RandomSource random, float chanceMultiplier) {
		if (!mob.getMainHandItem().isEmpty()) {
			RegistryAccess access = mob.level().registryAccess();
			Optional<Named<Enchantment>> tag = access.lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentTags.ON_RANDOM_LOOT);

			mob.setItemSlot(
					EquipmentSlot.MAINHAND,
					EnchantmentHelper.enchantItem(
							random,
							mob.getMainHandItem(),
							(int) (5.0F + chanceMultiplier * (float) random.nextInt(18)),
							access,
							tag
					)
			);
		}
	}

	/**
	 * Enchant the armor of a spawned entity.
	 *
	 * @param mob              the entity
	 * @param random           the random source
	 * @param chanceMultiplier the chance multiplier
	 */
	public static void enchantSpawnedArmor(Mob mob, RandomSource random, float chanceMultiplier) {
		for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
			if (equipmentslot.getType() == Type.HUMANOID_ARMOR) {
				ItemStack slotStack = mob.getItemBySlot(equipmentslot);
				RegistryAccess access = mob.level().registryAccess();
				Optional<Named<Enchantment>> tag = access.lookupOrThrow(Registries.ENCHANTMENT).get(EnchantmentTags.ON_RANDOM_LOOT);

				if (!slotStack.isEmpty()) {
					mob.setItemSlot(
							equipmentslot,
							EnchantmentHelper.enchantItem(
									random,
									slotStack,
									(int) (5.0F + chanceMultiplier * (float) random.nextInt(18)),
									access,
									tag
							)
					);
				}
			}
		}
	}
}