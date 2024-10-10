package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.init.DataComponentTypeRegistry;

/**
 * A utility class for items that have a kill count.
 */
public class KillCountWeapon {

	static final DataComponentType<Integer> KILL_COUNT = DataComponentTypeRegistry.KILL_COUNT.get();
	static final DataComponentType<String> TIER = DataComponentTypeRegistry.KILL_COUNT_TIER.get();

	/**
	 * Initialize an item with a kill count.
	 *
	 * @param stack the <code>ItemStack</code> to initialize
	 */
	public static void initialize(ItemStack stack) {
		stack.set(KILL_COUNT, 0);
		stack.set(TIER, WeaponTier.SPECIAL.name);
	}

	/**
	 * Check if an item has a kill count.
	 *
	 * @param stack the <code>ItemStack</code> to check
	 * @return boolean
	 */
	public static boolean hasKillCount(ItemStack stack) {
		return stack.get(KILL_COUNT) != null;
	}

	/**
	 * Increment the kill count of an item. Will also check for prefix updates.
	 *
	 * @param stack the <code>ItemStack</code> to increment
	 */
	public static void increment(ItemStack stack) {
		int killCount = stack.getOrDefault(KILL_COUNT, 0) + 1;
		stack.set(KILL_COUNT, killCount);

		// Determine if the weapon prefix needs to be changed
		WeaponTier highestTier = WeaponTier.SPECIAL;
		for (WeaponTier tier : WeaponTier.values()) {
			if (killCount >= tier.requiredKills) {
				highestTier = tier;
			}
		}

		WeaponTier oldTier = getTierByName(stack.getOrDefault(TIER, WeaponTier.SPECIAL.name));
		if (!oldTier.equals(highestTier)) {
			stack.set(TIER, highestTier.name);
		}
	}

	/**
	 * An enum of weapon name tiers, which are used to determine the level of a weapon based on the amount of kills.
	 */
	public enum WeaponTier {
		SPECIAL("special", 0, ChatFormatting.GRAY),
		NOVICE("novice", 25, ChatFormatting.WHITE),
		APPRENTICE("apprentice", 50, ChatFormatting.GREEN),
		JOURNEYMAN("journeyman", 100, ChatFormatting.BLUE),
		EXPERT("expert", 250, ChatFormatting.DARK_AQUA),
		MASTER("master", 500, ChatFormatting.DARK_RED),
		LEGENDARY("legendary", 750, ChatFormatting.DARK_PURPLE),
		HANS_WORTHY("hans_worthy", 1000, ChatFormatting.GOLD);

		private final String name;
		private final int requiredKills;
		private final ChatFormatting chatFormatting;

		WeaponTier(String name, int requiredKills, ChatFormatting chatFormatting) {
			this.name = name;
			this.requiredKills = requiredKills;
			this.chatFormatting = chatFormatting;
		}
	}

	/**
	 * Get the tier component of an item.
	 *
	 * @param stack the <code>ItemStack</code> to check
	 * @return MutableComponent
	 */
	public static MutableComponent getTierComponent(ItemStack stack) {
		return Component.translatable("immersiveweapons.kill_count_weapon.tier." + stack.getOrDefault(TIER, WeaponTier.SPECIAL.name))
				.withStyle(getTierByName(stack.getOrDefault(TIER, WeaponTier.SPECIAL.name)).chatFormatting);
	}

	/**
	 * Get the tier component of an item.
	 *
	 * @param tier the <code>WeaponTier</code> to check
	 * @return MutableComponent
	 */
	public static MutableComponent getTierComponent(WeaponTier tier) {
		return Component.translatable("immersiveweapons.kill_count_weapon.tier." + tier.name)
				.withStyle(tier.chatFormatting);
	}

	/**
	 * Get the kill count component of an item.
	 *
	 * @param stack the <code>ItemStack</code> to check
	 * @return MutableComponent
	 */
	public static MutableComponent getKillComponent(ItemStack stack) {
		int killCount = stack.getOrDefault(KILL_COUNT, 0);
		return Component.translatable("immersiveweapons.kill_count_weapon.total_kills", killCount)
				.withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC);
	}

	/**
	 * Get the tier by name.
	 *
	 * @param name the name of the tier
	 * @return WeaponTier
	 */
	private static WeaponTier getTierByName(String name) {
		for (WeaponTier tier : WeaponTier.values()) {
			if (tier.name.equals(name)) {
				return tier;
			}
		}
		return WeaponTier.SPECIAL;
	}
}