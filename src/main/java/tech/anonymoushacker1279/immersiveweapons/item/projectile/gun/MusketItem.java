package tech.anonymoushacker1279.immersiveweapons.item.projectile.gun;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

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
	protected void fireBullets(AbstractBulletItem bulletItem, Level level, Player player, ItemStack firingItem) {
		BulletEntity bulletEntity = bulletItem.createBullet(level, player);

		bulletEntity.setFiringItem(firingItem.getItem());

		bulletEntity.shootFromRotation(player, player.xRot, player.yRot,
				0.0F, CommonConfig.MUSKET_FIRE_VELOCITY.get().floatValue(), CommonConfig.MUSKET_FIRE_INACCURACY.get().floatValue());

		// Roll for random crits
		if (GeneralUtilities.getRandomNumber(0f, 1f) <= CommonConfig.GUN_CRIT_CHANCE.get()) {
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
		return SoundEventRegistry.MUSKET_FIRE.get();
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