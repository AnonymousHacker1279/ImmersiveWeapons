package com.anonymoushacker1279.immersiveweapons.item.projectile.throwable;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeBombEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SmokeBombItem extends Item {

	private final int color;

	/**
	 * Constructor for SmokeBombItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public SmokeBombItem(Properties properties, int color) {
		super(properties);
		this.color = color;
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
	public @NotNull InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), DeferredRegistryHandler.GENERIC_WHOOSH.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 0.8F));
		if (!worldIn.isClientSide) {
			SmokeBombEntity.setColor(color);
			SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, playerIn);
			smokeBombEntity.setItem(itemstack);
			smokeBombEntity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.0F, 1.5F, 1.0F);
			worldIn.addFreshEntity(smokeBombEntity);
		}
		if (!playerIn.isCreative()) {
			itemstack.shrink(1);
			playerIn.getCooldowns().addCooldown(this, 100);    // Helps to prevent spamming, mostly to reduce the total particles in an area
		}

		return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
	}
}