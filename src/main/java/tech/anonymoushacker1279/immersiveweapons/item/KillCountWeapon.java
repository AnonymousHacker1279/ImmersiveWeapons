package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

/**
 * A utility class for weapons that have a kill count.
 */
public abstract class KillCountWeapon {

	public static final String KILL_COUNT_TAG = "KillCount";
	public static final String TIER_TAG = "Tier";

	/**
	 * Determine if an item stack has a kill count.
	 *
	 * @param stack the <code>ItemStack</code> to check
	 * @return boolean
	 */
	public static boolean hasKillCount(ItemStack stack) {
		return stack.getOrCreateTag().contains(KILL_COUNT_TAG);
	}

	/**
	 * Make an item stack have a kill count.
	 *
	 * @param stack  the <code>ItemStack</code> to initialize
	 * @param amount the amount of kills to begin with
	 */
	public static void initialize(ItemStack stack, int amount) {
		stack.getOrCreateTag().putInt(KILL_COUNT_TAG, amount);
		stack.getOrCreateTag().putString(TIER_TAG, WeaponTier.SPECIAL.name);
	}

	/**
	 * Increment the kill count of an item stack. Will also check for prefix updates.
	 *
	 * @param stack the <code>ItemStack</code> to increment
	 */
	public static void incrementKillCount(ItemStack stack) {
		stack.getOrCreateTag().putInt(KILL_COUNT_TAG, getKillCount(stack) + 1);

		// Determine if the weapon prefix needs to be changed
		WeaponTier highestTier = WeaponTier.SPECIAL;
		for (WeaponTier tier : WeaponTier.values()) {
			if (getKillCount(stack) >= tier.requiredKills) {
				highestTier = tier;
			}
		}

		WeaponTier oldTier = getTierByName(stack.getOrCreateTag().getString(TIER_TAG));
		if (!oldTier.equals(highestTier)) {
			stack.getOrCreateTag().putString(TIER_TAG, highestTier.name);
		}
	}

	/**
	 * Get the kill count of an item stack.
	 *
	 * @param stack the <code>ItemStack</code> to check
	 * @return int
	 */
	public static int getKillCount(ItemStack stack) {
		return stack.getOrCreateTag().getInt(KILL_COUNT_TAG);
	}

	/**
	 * Get additional tooltip text (currently containing the total kills).
	 *
	 * @param stack the <code>ItemStack</code> to check
	 * @return MutableComponent
	 */
	public static MutableComponent getTooltipText(ItemStack stack) {
		return Component.translatable("immersiveweapons.kill_count_weapon.total_kills", getKillCount(stack));
	}

	/**
	 * Get the tier text for an item stack.
	 *
	 * @param stack the <code>ItemStack</code> to check
	 * @return MutableComponent
	 */
	public static MutableComponent getTierText(ItemStack stack) {
		return Component.translatable("immersiveweapons.kill_count_weapon.tier." + stack.getOrCreateTag().getString(TIER_TAG))
				.withStyle(getTierByName(stack.getOrCreateTag().getString(TIER_TAG)).chatFormatting);
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

	private static WeaponTier getTierByName(String name) {
		for (WeaponTier tier : WeaponTier.values()) {
			if (tier.name.equals(name)) {
				return tier;
			}
		}
		return WeaponTier.SPECIAL;
	}
}