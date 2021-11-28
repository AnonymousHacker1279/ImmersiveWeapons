package com.anonymoushacker1279.immersiveweapons.item.tool.molten;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;

public class MoltenHoe extends HoeItem {

	/**
	 * Constructor for MoltenHoe.
	 *
	 * @param tier           the <code>IItemTier</code>
	 * @param attackDamageIn attack damage
	 * @param attackSpeedIn  attack speed
	 * @param properties     the <code>Properties</code> for the item
	 */
	public MoltenHoe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
		super(tier, attackDamageIn, attackSpeedIn, properties);
	}

	/**
	 * Runs when an entity is hit.
	 *
	 * @param itemStack     the <code>ItemStack</code> instance
	 * @param livingEntity  the <code>LivingEntity</code> attacking
	 * @param livingEntity1 the <code>LivingEntity</code> being hit
	 * @return boolean
	 */
	@Override
	public boolean hurtEnemy(@NotNull ItemStack itemStack, LivingEntity livingEntity, @NotNull LivingEntity livingEntity1) {
		livingEntity.setSecondsOnFire(10);
		return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
	}
}