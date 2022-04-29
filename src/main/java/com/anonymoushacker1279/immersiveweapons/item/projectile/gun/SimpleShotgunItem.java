package com.anonymoushacker1279.immersiveweapons.item.projectile.gun;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.arrow.AbstractArrowItem;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class SimpleShotgunItem extends SimplePistolItem {

	/**
	 * Constructor for SimpleShotgunItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public SimpleShotgunItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the item is released.
	 *
	 * @param itemStack    the <code>ItemStack</code> being used
	 * @param worldIn      the <code>World</code> the entity is in
	 * @param livingEntity the <code>LivingEntity</code> releasing the item
	 * @param timeLeft     the time left from charging
	 */
	@Override
	public void releaseUsing(@NotNull ItemStack itemStack, @NotNull Level worldIn, @NotNull LivingEntity livingEntity,
	                         int timeLeft) {

		if (livingEntity instanceof Player playerEntity) {
			boolean isCreative = playerEntity.isCreative();
			boolean misfire = false;
			ItemStack ammo = findAmmo(itemStack, livingEntity);

			// Determine number of bullets to fire
			int bulletsToFire = isCreative ? 4 : getBulletsToFire(ammo);

			// Roll for misfire
			if (ammo.getItem() == DeferredRegistryHandler.WOOD_MUSKET_BALL.get()) {
				if (GeneralUtilities.getRandomNumber(1, 10) <= 3) {
					misfire = true;
					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
							getMisfireSound(), SoundSource.PLAYERS, 1.0F,
							1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);

					if (!isCreative) {
						ammo.shrink(bulletsToFire);
						if (ammo.isEmpty()) {
							playerEntity.getInventory().removeItem(ammo);
						}
						itemStack.hurtAndBreak(5, playerEntity, (entity) ->
								entity.broadcastBreakEvent(entity.getUsedItemHand()));
					}
				}
			} else if (ammo.getItem() == DeferredRegistryHandler.STONE_MUSKET_BALL.get()) {
				if (GeneralUtilities.getRandomNumber(1, 20) <= 3) {
					misfire = true;
					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
							getMisfireSound(), SoundSource.PLAYERS, 1.0F,
							1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);

					if (!isCreative) {
						ammo.shrink(bulletsToFire);
						if (ammo.isEmpty()) {
							playerEntity.getInventory().removeItem(ammo);
						}
						itemStack.hurtAndBreak(5, playerEntity, (entity) ->
								entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
					}
				}
			}

			int useDuration = getUseDuration(itemStack);
			useDuration = ForgeEventFactory.onArrowLoose(itemStack, worldIn, playerEntity, useDuration, !ammo.isEmpty() || isCreative);
			if (useDuration < 0) {
				return;
			}

			// Check if the ammunition stack is not empty, if the player is in creative mode,
			// or that a misfire hasn't occurred
			if (!ammo.isEmpty() || isCreative || !misfire) {
				// If the ammunition stack is empty, set it to the default.
				// This happens when the player is in creative mode but has
				// no ammunition.
				if (ammo.isEmpty()) {
					ammo = new ItemStack(defaultAmmo());
				}

				if (!misfire) {
					if (!worldIn.isClientSide) {
						AbstractArrowItem customArrowItem = (AbstractArrowItem) (ammo.getItem() instanceof AbstractArrowItem
								? ammo.getItem() : defaultAmmo());

						for (int i = 0; i < bulletsToFire; ++i) {
							AbstractArrow abstractBulletEntity = customArrowItem.createArrow(worldIn, ammo, playerEntity);
							abstractBulletEntity.shootFromRotation(playerEntity,
									playerEntity.xRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f),
									playerEntity.yRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f),
									0.0F, 3.0F, 1.0F);

							// Roll for random crits
							if (GeneralUtilities.getRandomNumber(0f, 1f) <= 0.1f) {
								abstractBulletEntity.setCritArrow(true);
							}

							abstractBulletEntity.setOwner(playerEntity);
							abstractBulletEntity.pickup = Pickup.DISALLOWED;
							abstractBulletEntity.setKnockback(3);

							itemStack.hurtAndBreak(1, playerEntity, (entity) ->
									entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));

							worldIn.addFreshEntity(abstractBulletEntity);
						}
					}

					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
							getFireSound(), SoundSource.PLAYERS, 1.0F,
							1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);

					if (!isCreative) {
						ammo.shrink(bulletsToFire);
						if (ammo.isEmpty()) {
							playerEntity.getInventory().removeItem(ammo);
						}
					}

					if (!playerEntity.isCreative()) {
						playerEntity.getCooldowns().addCooldown(this, 100);
					}
				}
			}
		}
	}

	/**
	 * Get the number of bullets to fire.
	 *
	 * @param itemStack the ammunition <code>ItemStack</code>
	 * @return int
	 */
	private int getBulletsToFire(ItemStack itemStack) {
		return Math.min(itemStack.getCount(), 4);
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
}