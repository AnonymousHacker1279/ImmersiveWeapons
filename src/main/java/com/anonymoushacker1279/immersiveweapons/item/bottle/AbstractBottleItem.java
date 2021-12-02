package com.anonymoushacker1279.immersiveweapons.item.bottle;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
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
	 * @param worldIn  the <code>World</code> the player is in
	 * @param playerIn the <code>PlayerEntity</code> instance
	 * @param handIn   the <code>Hand</code> the player is using
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		onUse(playerIn);
		if (!playerIn.isCreative()) {
			itemstack.shrink(1);
			playerIn.addItem(new ItemStack(Items.GLASS_BOTTLE));
			playerIn.getCooldowns().addCooldown(this, getCooldown());
		}

		return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
	}

	/**
	 * Additional code to run when the item is used.
	 *
	 * @param playerIn the <code>PlayerEntity</code> instance
	 */
	protected void onUse(Player playerIn) {
	}

	/**
	 * Get the item cooldown.
	 *
	 * @return int
	 */
	private int getCooldown() {
		return 600;
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