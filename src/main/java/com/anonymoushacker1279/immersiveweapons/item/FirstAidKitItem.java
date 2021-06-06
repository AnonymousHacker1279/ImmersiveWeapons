package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FirstAidKitItem extends Item {

	public FirstAidKitItem(Properties properties) {
		super(properties);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 80;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult rayTraceResult = Minecraft.getInstance().objectMouseOver;
		if (rayTraceResult.getType() != Type.ENTITY) {
			if (playerIn.getMaxHealth() - playerIn.getHealth() <= playerIn.getMaxHealth() / 2) { // Only use if at or less than half health
				if (worldIn.isRemote) {
					playerIn.sendMessage(new TranslationTextComponent("immersiveweapons.item.first_aid_kit").mergeStyle(TextFormatting.RED), Util.DUMMY_UUID);
				}
				return ActionResult.resultPass(itemstack);
			}
			playerIn.addPotionEffect(new EffectInstance(Effects.REGENERATION, 240, 1, false, true));
			playerIn.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1200, 0, false, true));
			if (!playerIn.abilities.isCreativeMode) {
				itemstack.shrink(1);
				playerIn.getCooldownTracker().setCooldown(this, 400);
			}
		}

		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	}

	@Override
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entity, Hand hand) {
		if (entity.world.isRemote) {
			return ActionResultType.PASS;
		}
		if (entity.getMaxHealth() - entity.getHealth() <= entity.getMaxHealth() / 2) { // Only use if at or less than half health
			return ActionResultType.PASS;
		}
		entity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 240, 1, false, true));
		entity.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1200, 0, false, true));
		if (!playerIn.abilities.isCreativeMode) {
			stack.shrink(1);
		}

		return ActionResultType.PASS;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}
}