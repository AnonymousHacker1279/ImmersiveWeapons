package com.anonymoushacker1279.immersiveweapons.item.projectile.gun;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class MusketItem extends AbstractGunItem {

	private final boolean hasScope;

	/**
	 * Constructor for MusketItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public MusketItem(Properties properties, boolean hasScope) {
		super(properties);
		this.hasScope = hasScope;
	}

	@Override
	protected void fireBullets(AbstractBulletItem bulletItem, Level level, ItemStack ammo, Player player, ItemStack firingItem) {
		BulletEntity bulletEntity = bulletItem.createBullet(level, ammo, player);

		bulletEntity.setFiringItem(firingItem.getItem());

		bulletEntity.shootFromRotation(player, player.xRot, player.yRot,
				0.0F, 4.0F, 0.15F);

		// Roll for random crits
		if (GeneralUtilities.getRandomNumber(0f, 1f) <= 0.2f) {
			bulletEntity.setCritArrow(true);
		}

		bulletEntity.setOwner(player);
		bulletEntity.pickup = Pickup.DISALLOWED;

		firingItem.hurtAndBreak(1, player, (entity) ->
				entity.broadcastBreakEvent(player.getUsedItemHand()));

		level.addFreshEntity(bulletEntity);
	}

	/**
	 * Get the fire sound.
	 *
	 * @return SoundEvent
	 */
	@Override
	public SoundEvent getFireSound() {
		return DeferredRegistryHandler.MUSKET_FIRE.get();
	}

	/**
	 * Get the repair material.
	 *
	 * @return Ingredient
	 */
	@Override
	protected Ingredient getRepairMaterial() {
		return Ingredient.of(Items.IRON_INGOT);
	}

	@Override
	public int getCooldown() {
		return 200;
	}

	@Override
	public float getMaxYRecoil() {
		return -0.15f;
	}

	@Override
	public float getMaxXRecoil() {
		return -2.0f;
	}

	@Override
	public boolean canScope() {
		return hasScope;
	}
}