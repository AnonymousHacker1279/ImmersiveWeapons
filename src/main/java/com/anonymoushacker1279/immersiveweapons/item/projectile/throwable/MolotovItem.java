package com.anonymoushacker1279.immersiveweapons.item.projectile.throwable;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.MolotovEntity;
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

public class MolotovItem extends Item {

	/**
	 * Constructor for MolotovItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public MolotovItem(Properties properties) {
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
	public @NotNull InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
		ItemStack itemInHand = playerIn.getItemInHand(handIn);
		worldIn.playLocalSound(playerIn.getX(), playerIn.getY(), playerIn.getZ(),
				DeferredRegistryHandler.GENERIC_WHOOSH.get(), SoundSource.NEUTRAL,
				0.5F, 0.4F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 0.8F), false);

		if (!worldIn.isClientSide) {
			MolotovEntity molotovEntity = new MolotovEntity(worldIn, playerIn);
			molotovEntity.setItem(itemInHand);
			molotovEntity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, -20.0F, 0.5F, 1.0F);
			worldIn.addFreshEntity(molotovEntity);
		}

		if (!playerIn.isCreative()) {
			itemInHand.shrink(1);
			playerIn.getCooldowns().addCooldown(this, 100);
		}

		return InteractionResultHolder.sidedSuccess(itemInHand, worldIn.isClientSide());
	}
}