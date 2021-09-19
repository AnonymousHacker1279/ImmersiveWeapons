package com.anonymoushacker1279.immersiveweapons.item.handbook;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HealingExpertHandbook extends Item {

	public HealingExpertHandbook(Properties pProperties) {
		super(pProperties);
	}

	/**
	 * Runs when the item is used.
	 * @param worldIn the <code>World</code> the player is in
	 * @param playerIn the <code>PlayerEntity</code> instance
	 * @param handIn the <code>Hand</code> the player is using
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
		ItemStack itemStack = playerIn.getItemInHand(handIn);

		if (!worldIn.isClientSide) {
			if (playerIn.getPersistentData().get(Player.PERSISTED_NBT_TAG) != null && Objects.requireNonNull(playerIn.getPersistentData().get(Player.PERSISTED_NBT_TAG)).toString().contains("handbookHealingExpert")) {
				playerIn.sendMessage(new TranslatableComponent("immersiveweapons.item.handbook.already_exists").withStyle(ChatFormatting.YELLOW), Util.NIL_UUID);
			} else {
				playerIn.getPersistentData().putString(Player.PERSISTED_NBT_TAG, "handbookHealingExpert");
				itemStack.shrink(1);
				playerIn.playSound(SoundEvents.BOOK_PUT, 1, 1);
				playerIn.giveExperiencePoints(15);
				playerIn.sendMessage(new TranslatableComponent("immersiveweapons.item.handbook.healing_expert1").withStyle(ChatFormatting.GREEN), Util.NIL_UUID);
				playerIn.sendMessage(new TranslatableComponent("immersiveweapons.item.handbook.healing_expert2").withStyle(ChatFormatting.GREEN), Util.NIL_UUID);
			}
		}

		return InteractionResultHolder.sidedSuccess(itemStack, worldIn.isClientSide());
	}
}