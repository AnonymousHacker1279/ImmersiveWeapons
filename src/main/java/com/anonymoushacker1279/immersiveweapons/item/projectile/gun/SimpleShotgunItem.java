package com.anonymoushacker1279.immersiveweapons.item.projectile.gun;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.arrow.AbstractArrowItem;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class SimpleShotgunItem extends AbstractGunItem {

	/**
	 * Constructor for SimpleShotgunItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public SimpleShotgunItem(Properties properties) {
		super(properties);
	}

	@Override
	protected void fireBullets(AbstractArrowItem customArrowItem, Level level, ItemStack ammo, Player player, ItemStack itemStack) {
		AbstractArrow abstractBulletEntity = customArrowItem.createArrow(level, ammo, player);
		abstractBulletEntity.shootFromRotation(player,
				player.xRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f),
				player.yRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f),
				0.0F, 3.0F, 1.0F);

		// Roll for random crits
		if (GeneralUtilities.getRandomNumber(0f, 1f) <= 0.1f) {
			abstractBulletEntity.setCritArrow(true);
		}

		abstractBulletEntity.setOwner(player);
		abstractBulletEntity.pickup = Pickup.DISALLOWED;
		abstractBulletEntity.setKnockback(3);

		itemStack.hurtAndBreak(1, player, (entity) ->
				entity.broadcastBreakEvent(player.getUsedItemHand()));

		level.addFreshEntity(abstractBulletEntity);
	}

	/**
	 * Get the fire sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	public SoundEvent getFireSound() {
		return DeferredRegistryHandler.BLUNDERBUSS_FIRE.get();
	}

	/**
	 * Get the repair material.
	 *
	 * @return Ingredient
	 */
	@Override
	protected Ingredient getRepairMaterial() {
		return Ingredient.of(Items.GOLD_INGOT);
	}

	@Override
	public int getCooldown() {
		return 100;
	}

	@Override
	public float getMaxYRecoil() {
		return -1.5f;
	}

	@Override
	public float getMaxXRecoil() {
		return -13.0f;
	}

	@Override
	public int getMaxBulletsToFire() {
		return 4;
	}
}