package com.anonymoushacker1279.immersiveweapons.item.bottle;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBottleItem extends Item {

	/**
	 * Constructor for AbstractBottleItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	AbstractBottleItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the item is used.
	 *
	 * @param level    the <code>Level</code> the player is in
	 * @param playerIn the <code>Player</code> instance
	 * @param handIn   the <code>InteractionHand</code> the player is using
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player playerIn, @NotNull InteractionHand handIn) {
		ItemStack itemInHand = playerIn.getItemInHand(handIn);
		onUse(playerIn);
		if (!playerIn.isCreative()) {
			itemInHand.shrink(1);
			playerIn.addItem(new ItemStack(Items.GLASS_BOTTLE));
			playerIn.getCooldowns().addCooldown(this, 600);
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide());
	}

	/**
	 * Additional code to run when the item is used.
	 *
	 * @param playerIn the <code>Player</code> instance
	 */
	protected void onUse(Player playerIn) {
	}

	/**
	 * Check if the item has a container item.
	 *
	 * @param stack the <code>ItemStack</code> to be checked
	 * @return boolean
	 */
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	/**
	 * Get the container item.
	 *
	 * @param stack the <code>ItemStack</code> instance
	 * @return ItemStack
	 */
	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		return new ItemStack(Items.GLASS_BOTTLE);
	}

	/**
	 * Get the use animation.
	 *
	 * @param stack the <code>ItemStack</code> instance
	 * @return UseAction
	 */
	@Override
	public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.DRINK;
	}
}