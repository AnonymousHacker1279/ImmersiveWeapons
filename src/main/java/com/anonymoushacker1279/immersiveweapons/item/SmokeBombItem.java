package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeBombEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class SmokeBombItem extends Item {

	private final int color;

	/**
	 * Constructor for SmokeBombItem.
	 * @param properties the <code>Properties</code> for the item
	 */
	public SmokeBombItem(Properties properties, int color) {
		super(properties);
		this.color = color;
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
		worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), DeferredRegistryHandler.GENERIC_WHOOSH.get(), SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
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

		return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
	}
}