package com.anonymoushacker1279.immersiveweapons.util;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import java.util.UUID;

public class GeneralUtilities {

	public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("9f470b49-0445-4341-ae85-55b9e5ec2a1c");
	public static final ResourceKey<Level> TILTROS = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(ImmersiveWeapons.MOD_ID, "tiltros"));


	/**
	 * Get a random number between a minimum and maximum.
	 *
	 * @param min minimum number
	 * @param max maximum number
	 * @return float
	 */
	public static float getRandomNumber(float min, float max) {
		return (float) ((Math.random() * (max - min)) + min);
	}

	/**
	 * Get a random number between a minimum and maximum.
	 *
	 * @param min minimum number
	 * @param max maximum number
	 * @return double
	 */
	public static double getRandomNumber(double min, double max) {
		return (Math.random() * (max - min)) + min;
	}

	/**
	 * Get a random number between a minimum and maximum.
	 *
	 * @param min minimum number
	 * @param max maximum number
	 * @return int
	 */
	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	/**
	 * Create a stained-glass block from a color.
	 *
	 * @param color      the <code>DyeColor</code>
	 * @param properties the <code>Properties</code> for the block
	 * @return StainedGlassBlock
	 */
	public static StainedGlassBlock createStainedGlassFromColor(DyeColor color, Properties properties) {
		return new StainedGlassBlock(color, properties);
	}

	public static boolean hasFeatherFalling(LivingEntity entity) {
		ItemStack boots = entity.getArmorSlots().iterator().next();
		return EnchantmentHelper.getEnchantments(boots).containsKey(Enchantments.FALL_PROTECTION);
	}

	public static int getFeatherFallingLevel(LivingEntity entity) {
		ItemStack boots = entity.getArmorSlots().iterator().next();
		if (hasFeatherFalling(entity)) {
			return EnchantmentHelper.getEnchantments(boots).getOrDefault(Enchantments.FALL_PROTECTION, 0);
		}
		return 0;
	}
}