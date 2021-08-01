package com.anonymoushacker1279.immersiveweapons.item.projectile.gun;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.arrow.AbstractArrowItem;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class SimpleShotgunItem extends SimplePistolItem {

	/**
	 * Constructor for SimpleShotgunItem.
	 * @param properties the <code>Properties</code> for the item
	 */
	public SimpleShotgunItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the item is released.
	 * @param itemStack the <code>ItemStack</code> being used
	 * @param worldIn the <code>World</code> the entity is in
	 * @param entityLiving the <code>LivingEntity</code> releasing the item
	 * @param timeLeft the time left from charging
	 */
	@Override
	public void releaseUsing(ItemStack itemStack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player playerEntity) {
			boolean flag = playerEntity.isCreative();
			boolean misfire = false;
			ItemStack itemStack1 = findAmmo(itemStack, entityLiving);

			// Determine number of bullets to fire
			int bulletsToFire = getBulletsToFire(itemStack1);

			// Roll for misfire
			if (itemStack1.getItem() == DeferredRegistryHandler.WOOD_MUSKET_BALL.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(1, 10);
				if (randomNumber <= 3) {
					misfire = true;
					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), getMisfireSound(), SoundSource.PLAYERS, 1.0F, 1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);
					if (!playerEntity.isCreative()) {
						itemStack1.shrink(bulletsToFire);
						if (itemStack1.isEmpty()) {
							playerEntity.getInventory().removeItem(itemStack1);
						}
						itemStack.hurtAndBreak(5, playerEntity, (entity) -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
					}
				}
			}
			// Roll for misfire
			if (itemStack1.getItem() == DeferredRegistryHandler.STONE_MUSKET_BALL.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(1, 20);
				if (randomNumber <= 3) {
					misfire = true;
					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), getMisfireSound(), SoundSource.PLAYERS, 1.0F, 1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);
					if (!playerEntity.isCreative()) {
						itemStack1.shrink(bulletsToFire);
						if (itemStack1.isEmpty()) {
							playerEntity.getInventory().removeItem(itemStack1);
						}
						itemStack.hurtAndBreak(5, playerEntity, (entity) -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
					}
				}
			}

			int i = getUseDuration(itemStack);
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(itemStack, worldIn, playerEntity, i, !itemStack1.isEmpty() || flag);
			if (i < 0) return;

			if (!itemStack1.isEmpty() || flag || !misfire) {
				if (itemStack1.isEmpty()) {
					itemStack1 = new ItemStack(defaultAmmo());
				}

				if (!misfire) {
					boolean flag1 = playerEntity.isCreative() || (itemStack1.getItem() instanceof AbstractArrowItem && ((AbstractArrowItem) itemStack1.getItem()).isInfinite(itemStack1, itemStack, playerEntity));
					if (!worldIn.isClientSide) {
						for (int iterator = 0; iterator < bulletsToFire; ++iterator) {
							AbstractArrowItem customArrowItem = (AbstractArrowItem) (itemStack1.getItem() instanceof AbstractArrowItem ? itemStack1.getItem() : defaultAmmo());
							AbstractArrow abstractBulletEntity = customArrowItem.createArrow(worldIn, itemStack1, playerEntity);
							abstractBulletEntity.setKnockback(3);
							abstractBulletEntity.shootFromRotation(playerEntity, playerEntity.xRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f), playerEntity.yRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f), 0.0F, 3.0F, 1.0F);
							abstractBulletEntity.setCritArrow(true);
							worldIn.addFreshEntity(abstractBulletEntity);
						}
						itemStack.hurtAndBreak(1, playerEntity, (entity) -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
					}

					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), getFireSound(), SoundSource.PLAYERS, 1.0F, 1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 0.5F));
					if (!flag1 && !playerEntity.isCreative()) {
						itemStack1.shrink(bulletsToFire);
						if (itemStack1.isEmpty()) {
							playerEntity.getInventory().removeItem(itemStack1);
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
	 * @param itemStack the ammunition <code>ItemStack</code>
	 * @return int
	 */
	private int getBulletsToFire(ItemStack itemStack) {
		return Math.min(itemStack.getCount(), 4);
	}

	/**
	 * Get the fire sound.
	 * @return SoundEvent
	 */
	@Override
	public SoundEvent getFireSound() {
		return DeferredRegistryHandler.BLUNDERBUSS_FIRE.get();
	}

	/**
	 * Get the repair material.
	 * @return Ingredient
	 */
	@Override
	protected Ingredient getRepairMaterial() {
		return Ingredient.of(Items.GOLD_INGOT);
	}
}