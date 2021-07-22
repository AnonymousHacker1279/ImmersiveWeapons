package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class FirstAidKitItem extends Item {

	/**
	 * Constructor for FirstAidKitItem.
	 * @param properties the <code>Properties</code> for the item
	 */
	public FirstAidKitItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the player right-clicks.
	 * @param worldIn the <code>World</code> the player is in
	 * @param playerIn the <code>PlayerEntity</code> performing the action
	 * @param handIn the <code>Hand</code> the player is using
	 * @return ActionResult extending ItemStack
	 */
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		if (playerIn.getMaxHealth() - playerIn.getHealth() <= playerIn.getMaxHealth() / 2) { // Only use if at or less than half health
			if (worldIn.isClientSide) {
				playerIn.sendMessage(new TranslationTextComponent("immersiveweapons.item.first_aid_kit").withStyle(TextFormatting.RED), Util.NIL_UUID);
			}
			return ActionResult.pass(itemstack);
		}
		playerIn.addEffect(new EffectInstance(Effects.REGENERATION, 240, 1, false, true));
		playerIn.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 1200, 0, false, true));
		if (!playerIn.isCreative()) {
			itemstack.shrink(1);
			playerIn.getCooldowns().addCooldown(this, 400);
		}

		return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
	}

	/**
	 * Runs when the player right-clicks an entity.
	 * @param stack the <code>ItemStack</code> right-clicked with
	 * @param playerIn the <code>PlayerEntity</code> performing the action
	 * @param entity the <code>LivingEntity</code> being interacted with
	 * @param hand the <code>Hand</code> the player is using
	 * @return ActionResultType
	 */
	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entity, Hand hand) {
		if (entity.level.isClientSide) {
			return ActionResultType.PASS;
		}
		if (entity.getMaxHealth() - entity.getHealth() <= entity.getMaxHealth() / 2) { // Only use if at or less than half health
			return ActionResultType.PASS;
		}
		entity.addEffect(new EffectInstance(Effects.REGENERATION, 160, 1, false, true));
		entity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 900, 0, false, true));
		if (!playerIn.isCreative()) {
			stack.shrink(1);
		}

		return ActionResultType.PASS;
	}
}