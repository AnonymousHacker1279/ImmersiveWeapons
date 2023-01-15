package tech.anonymoushacker1279.immersiveweapons.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
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
	 * Returns the {@linkplain ResourceLocation#getPath() path} of the registry name for the given block. This
	 * is a convenience method which checks if the registry name of the entry exists, to avoid linting warnings about
	 * the nullability of the {@linkplain net.minecraftforge.registries.IForgeRegistry<Block>#getKey entry's registry name}.
	 *
	 * @param block the registry entry
	 * @return the path of the registry name of the given entry
	 * @throws NullPointerException if the entry does not have a registry name
	 */
	public static ResourceLocation blockRegistryPath(Block block) {
		return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block), "Registry name");
	}

	/**
	 * Returns the {@linkplain ResourceLocation#getPath() path} of the registry name for the given item. This
	 * is a convenience method which checks if the registry name of the entry exists, to avoid linting warnings about
	 * the nullability of the {@linkplain net.minecraftforge.registries.IForgeRegistry<Item>#getKey entry's registry name}.
	 *
	 * @param item the registry entry
	 * @return the path of the registry name of the given entry
	 * @throws NullPointerException if the entry does not have a registry name
	 */
	public static ResourceLocation itemRegistryPath(Item item) {
		return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item), "Registry name");
	}
}