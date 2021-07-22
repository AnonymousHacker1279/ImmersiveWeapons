package com.anonymoushacker1279.immersiveweapons.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class AbstractPikeItem extends Item {

	public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("9f470b49-0445-4341-ae85-55b9e5ec2a1c");

	/**
	 * Constructor for PikeItem.
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn the damage
	 * @param attackSpeedIn the attack speed
	 */
	AbstractPikeItem(Properties properties, double damageIn, double attackSpeedIn) {
		super(properties);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
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
	public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}

	/**
	 * Get the use animation.
	 * @param stack the <code>ItemStack</code> instance
	 * @return UseAction
	 */
	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.SPEAR;
	}

	/**
	 * Runs when an enemy is hurt.
	 * @param stack the <code>ItemStack</code> instance
	 * @param target the <code>LivingEntity</code> target
	 * @param attacker the <code>LivingEntity</code> attacker
	 * @return boolean
	 */
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
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
	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getDestroySpeed(worldIn, pos) != 0.0D) {
			stack.hurtAndBreak(2, entityLiving, (entity) -> {
				entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
			});
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
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
	}
}