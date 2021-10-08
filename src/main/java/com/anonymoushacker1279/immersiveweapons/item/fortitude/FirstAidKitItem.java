package com.anonymoushacker1279.immersiveweapons.item.fortitude;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		if (playerIn.getMaxHealth() - playerIn.getHealth() <= playerIn.getMaxHealth() / 2) { // Only use if at or less than half health
			if (worldIn.isClientSide) {
				playerIn.sendMessage(new TranslatableComponent("immersiveweapons.item.first_aid_kit").withStyle(ChatFormatting.RED), Util.NIL_UUID);
			}
			return InteractionResultHolder.pass(itemstack);
		}
		playerIn.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 240, 1, false, true));
		playerIn.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0, false, true));
		if (!playerIn.isCreative()) {
			itemstack.shrink(1);
			playerIn.getCooldowns().addCooldown(this, 400);
		}

		return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
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
	public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player playerIn, LivingEntity entity, @NotNull InteractionHand hand) {
		if (entity.level.isClientSide) {
			return InteractionResult.PASS;
		}
		if (entity.getMaxHealth() - entity.getHealth() <= entity.getMaxHealth() / 2) { // Only use if at or less than half health
			return InteractionResult.PASS;
		}
		entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 160, 1, false, true));
		entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 0, false, true));
		if (!playerIn.isCreative()) {
			stack.shrink(1);
		}

		return InteractionResult.PASS;
	}
}