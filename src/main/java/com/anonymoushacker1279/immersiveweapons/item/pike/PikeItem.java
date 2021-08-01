package com.anonymoushacker1279.immersiveweapons.item.pike;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PikeItem extends Item {

	public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("9f470b49-0445-4341-ae85-55b9e5ec2a1c");

	/**
	 * Constructor for PikeItem.
	 * @param properties the <code>Properties</code> for the item
	 */
	PikeItem(Properties properties) {
		super(properties);
	}

	/**
	 * Check if the block can be damaged.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param player the <code>PlayerEntity</code> damaging the block
	 * @return boolean
	 */
	@Override
	public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, Player player) {
		return !player.isCreative();
	}

	/**
	 * Get the use animation.
	 * @param stack the <code>ItemStack</code> instance
	 * @return UseAction
	 */
	@Override
	public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.SPEAR;
	}

	/**
	 * Runs when an enemy is hurt.
	 * @param stack the <code>ItemStack</code> instance
	 * @param target the <code>LivingEntity</code> target
	 * @param attacker the <code>LivingEntity</code> attacker
	 * @return boolean
	 */
	@Override
	public boolean hurtEnemy(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		return true;
	}

	/**
	 * Runs when a block is mined.
	 * @param stack the <code>ItemStack</code> instance
	 * @param worldIn the <code>World</code> the block is in
	 * @param state the <code>BlockState</code> of the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param entityLiving the <code>LivingEntity</code> destroying the block
	 * @return boolean
	 */
	@Override
	public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level worldIn, BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
		if (state.getDestroySpeed(worldIn, pos) != 0.0D) {
			stack.hurtAndBreak(2, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
		}
		return true;
	}

	/**
	 * Get the enchantment value.
	 * @return int
	 */
	@Override
	public int getEnchantmentValue() {
		return 1;
	}

	/**
	 * Get the repair material.
	 * @return Ingredient
	 */
	Ingredient getRepairMaterial() {
		return Ingredient.of(ItemTags.PLANKS);
	}

	/**
	 * Check if the repair item is valid.
	 * @param toRepair the <code>ItemStack</code> to repair
	 * @param repair the <code>ItemStack</code> to repair the first item
	 * @return boolean
	 */
	@Override
	public boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
		return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
	}
}