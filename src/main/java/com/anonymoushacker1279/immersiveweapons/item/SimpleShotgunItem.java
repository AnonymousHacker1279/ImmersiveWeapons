package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class SimpleShotgunItem extends SimplePistolItem {

	public SimpleShotgunItem(Properties builder) {
		super(builder);
	}

	@Override
	public void releaseUsing(ItemStack itemStack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) entityLiving;
			boolean flag = playerEntity.abilities.instabuild;
			boolean misfire = false;
			ItemStack itemStack1 = findAmmo(itemStack, entityLiving);

			// Determine number of bullets to fire
			int bulletsToFire = getBulletsToFire(itemStack1);

			if (itemStack1.getItem() == DeferredRegistryHandler.WOOD_MUSKET_BALL.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(1, 10);
				if (randomNumber <= 3) {
					misfire = true;
					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), getMisfireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
					if (!playerEntity.abilities.instabuild) {
						itemStack1.shrink(bulletsToFire);
						if (itemStack1.isEmpty()) {
							playerEntity.inventory.removeItem(itemStack1);
						}
						itemStack.hurtAndBreak(5, playerEntity, (entity) -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
					}
				}
			}
			if (itemStack1.getItem() == DeferredRegistryHandler.STONE_MUSKET_BALL.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(1, 20);
				if (randomNumber <= 3) {
					misfire = true;
					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), getMisfireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
					if (!playerEntity.abilities.instabuild) {
						itemStack1.shrink(bulletsToFire);
						if (itemStack1.isEmpty()) {
							playerEntity.inventory.removeItem(itemStack1);
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

				float f = getArrowVelocity(i);
				if (!(f < 0.1D) && !misfire) {
					boolean flag1 = playerEntity.abilities.instabuild || (itemStack1.getItem() instanceof CustomArrowItem && ((CustomArrowItem) itemStack1.getItem()).isInfinite(itemStack1, itemStack, playerEntity));
					if (!worldIn.isClientSide) {
						for (int iterator = 0; iterator < bulletsToFire; ++iterator) {
							CustomArrowItem customArrowItem = (CustomArrowItem) (itemStack1.getItem() instanceof CustomArrowItem ? itemStack1.getItem() : defaultAmmo());
							AbstractArrowEntity abstractBulletEntity = customArrowItem.createArrow(worldIn, itemStack1, playerEntity);
							abstractBulletEntity = customArrow(abstractBulletEntity);
							abstractBulletEntity.setKnockback(3);
							abstractBulletEntity.shootFromRotation(playerEntity, playerEntity.xRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f), playerEntity.yRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f), 0.0F, f * 3.0F, 1.0F);
							if (f == 1.0F) {
								abstractBulletEntity.setCritArrow(true);
							}
							worldIn.addFreshEntity(abstractBulletEntity);
						}
						itemStack.hurtAndBreak(1, playerEntity, (entity) -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
					}

					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), getFireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!flag1 && !playerEntity.abilities.instabuild) {
						itemStack1.shrink(bulletsToFire);
						if (itemStack1.isEmpty()) {
							playerEntity.inventory.removeItem(itemStack1);
						}
					}

					playerEntity.awardStat(Stats.ITEM_USED.get(this));
					if (!playerEntity.abilities.instabuild) {
						playerEntity.getCooldowns().addCooldown(this, 100);
					}
				}
			}
		}
	}

	private int getBulletsToFire(ItemStack itemStack) {
		return Math.min(itemStack.getCount(), 4);
	}

	@Override
	public SoundEvent getMisfireSound() {
		return DeferredRegistryHandler.FLINTLOCK_PISTOL_MISFIRE.get();
	}

	@Override
	public SoundEvent getFireSound() {
		return DeferredRegistryHandler.BLUNDERBUSS_FIRE.get();
	}
}