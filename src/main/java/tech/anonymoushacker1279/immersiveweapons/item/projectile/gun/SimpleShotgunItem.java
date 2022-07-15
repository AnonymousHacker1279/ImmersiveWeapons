package tech.anonymoushacker1279.immersiveweapons.item.projectile.gun;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

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
	protected void fireBullets(AbstractBulletItem bulletItem, Level level, Player player, ItemStack firingItem) {
		BulletEntity bulletEntity = bulletItem.createBullet(level, player);

		bulletEntity.setFiringItem(firingItem.getItem());

		bulletEntity.shootFromRotation(player,
				player.xRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f),
				player.yRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f),
				0.0F, 1.7F, 2.0F);

		// Roll for random crits
		if (GeneralUtilities.getRandomNumber(0f, 1f) <= 0.1f) {
			bulletEntity.setCritArrow(true);
		}

		bulletEntity.setOwner(player);
		bulletEntity.pickup = Pickup.DISALLOWED;
		bulletEntity.setKnockback(3);

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