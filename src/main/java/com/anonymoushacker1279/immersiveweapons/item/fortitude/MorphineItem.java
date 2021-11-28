package com.anonymoushacker1279.immersiveweapons.item.fortitude;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MorphineItem extends Item {

	/**
	 * Constructor for MorphineItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public MorphineItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the player right-clicks.
	 *
	 * @param worldIn  the <code>World</code> the player is in
	 * @param playerIn the <code>PlayerEntity</code> performing the action
	 * @param handIn   the <code>Hand</code> the player is using
	 * @return ActionResult extending ItemStack
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		playerIn.addEffect(new MobEffectInstance(DeferredRegistryHandler.MORPHINE_EFFECT.get(), 1800, 0, false, true));
		if (!playerIn.isCreative()) {
			itemstack.shrink(1);
			playerIn.getInventory().add(new ItemStack(DeferredRegistryHandler.USED_SYRINGE.get()));
			playerIn.getCooldowns().addCooldown(this, 2400);
		}

		return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
	}

	/**
	 * Runs when the player right-clicks an entity.
	 *
	 * @param stack    the <code>ItemStack</code> right-clicked with
	 * @param playerIn the <code>PlayerEntity</code> performing the action
	 * @param entity   the <code>LivingEntity</code> being interacted with
	 * @param hand     the <code>Hand</code> the player is using
	 * @return ActionResultType
	 */
	@Override
	public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player playerIn, LivingEntity entity, @NotNull InteractionHand hand) {
		if (entity.level.isClientSide) {
			return InteractionResult.PASS;
		}
		entity.addEffect(new MobEffectInstance(DeferredRegistryHandler.MORPHINE_EFFECT.get(), 1800, 0, false, true));
		if (!playerIn.isCreative()) {
			stack.shrink(1);
			playerIn.getInventory().add(new ItemStack(DeferredRegistryHandler.USED_SYRINGE.get()));
		}

		return InteractionResult.PASS;
	}
}