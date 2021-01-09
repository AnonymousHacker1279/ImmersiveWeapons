package com.anonymoushacker1279.immersiveweapons.items;

import java.util.UUID;

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

public class Pike extends Item {

	public static Multimap<Attribute, AttributeModifier> pikeAttributes;
	public static final UUID ATTACK_REACH_MODIFIER = UUID.fromString("9f470b49-0445-4341-ae85-55b9e5ec2a1c");
	
	public Pike(Item.Properties builderIn, double damageIn, double attackSpeedIn) {
		super(builderIn);
		Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", damageIn, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeedIn, AttributeModifier.Operation.ADDITION));
		Pike.pikeAttributes = builder.build();
	 }

	@Override
	 public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
	    return !player.isCreative();
	 }

	 @Override
	 public UseAction getUseAction(ItemStack stack) {
	    return UseAction.SPEAR;
	 }

	 @Override
	 public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		 stack.damageItem(1, attacker, (entity) -> {
			 entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
	      });
		 return true;
	 }

	 @Override
	 public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
	      if (state.getBlockHardness(worldIn, pos) != 0.0D) {
	         stack.damageItem(2, entityLiving, (entity) -> {
	            entity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
	         });
	      }

	      return true;
	 }

	@Override
	public int getItemEnchantability() {
		return 1;
	 }
}