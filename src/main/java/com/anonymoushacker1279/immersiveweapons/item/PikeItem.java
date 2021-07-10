package com.anonymoushacker1279.immersiveweapons.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.google.common.collect.Multimap;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class PikeItem extends Item {

	public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("9f470b49-0445-4341-ae85-55b9e5ec2a1c");
	public static Multimap<Attribute, AttributeModifier> pikeAttributes;

	public PikeItem(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
		super(builderIn);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
		PikeItem.pikeAttributes = builder.build();
	}

	@Override
	public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
		return !player.isCreative();
	}

	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.SPEAR;
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		stack.hurtAndBreak(1, attacker, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		if (state.getDestroySpeed(worldIn, pos) != 0.0D) {
			stack.hurtAndBreak(2, entityLiving, (entity) -> {
				entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND);
			});
		}
		return true;
	}

	@Override
	public int getEnchantmentValue() {
		return 1;
	}
}