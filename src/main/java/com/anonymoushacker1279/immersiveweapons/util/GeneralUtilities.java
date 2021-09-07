package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import java.util.UUID;

public class GeneralUtilities {

	public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("9f470b49-0445-4341-ae85-55b9e5ec2a1c");

	/**
	 * Get a random number between a minimum and maximum.
	 * @param min minimum number
	 * @param max maximum number
	 * @return float
	 */
	public static float getRandomNumber(float min, float max) {
		return (float) ((Math.random() * (max - min)) + min);
	}

	/**
	 * Get a random number between a minimum and maximum.
	 * @param min minimum number
	 * @param max maximum number
	 * @return double
	 */
	public static double getRandomNumber(double min, double max) {
		return (Math.random() * (max - min)) + min;
	}

	/**
	 * Get a random number between a minimum and maximum.
	 * @param min minimum number
	 * @param max maximum number
	 * @return int
	 */
	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	/**
	 * Create a stained-glass block from a color.
	 * @param color the <code>DyeColor</code>
	 * @param properties the <code>Properties</code> for the block
	 * @return StainedGlassBlock
	 */
	public static StainedGlassBlock createStainedGlassFromColor(DyeColor color, Properties properties) {
		return new StainedGlassBlock(color, properties);
	}

	/**
	 * Register an item property getter.
	 * @param item the <code>ItemLike</code> instance
	 * @param resourceLocation the <code>ResourceLocation</code> of the item
	 * @param propertyValue the <code>ClampedItemPropertyFunction</code> value
	 */
	public static void registerPropertyGetter(ItemLike item, ResourceLocation resourceLocation, ClampedItemPropertyFunction propertyValue) {
		ItemProperties.register(item.asItem(), resourceLocation, propertyValue);
	}

	/***
	 * Get the prefix of a string.
	 * @param path the path to prefix
	 * @return ResourceLocation
	 */
	public static ResourceLocation prefix(String path) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, path);
	}
}