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
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			boolean flag = playerentity.abilities.instabuild;
			boolean misfire = false;
			ItemStack itemstack = findAmmo(stack, entityLiving);

			// Determine number of bullets to fire
			int bulletsToFire = this.getBulletsToFire(itemstack);

			if (itemstack.getItem() == DeferredRegistryHandler.WOOD_MUSKET_BALL.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(1, 10);
				if (randomNumber <= 3) {
					misfire = true;
					worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), this.getMisfireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
					if (!playerentity.abilities.instabuild) {
						itemstack.shrink(bulletsToFire);
						if (itemstack.isEmpty()) {
							playerentity.inventory.removeItem(itemstack);
						}
						stack.hurtAndBreak(5, playerentity, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));
					}
				}
			}
			if (itemstack.getItem() == DeferredRegistryHandler.STONE_MUSKET_BALL.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(1, 20);
				if (randomNumber <= 3) {
					misfire = true;
					worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), this.getMisfireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
					if (!playerentity.abilities.instabuild) {
						itemstack.shrink(bulletsToFire);
						if (itemstack.isEmpty()) {
							playerentity.inventory.removeItem(itemstack);
						}
						stack.hurtAndBreak(5, playerentity, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));
					}
				}
			}

			int i = this.getUseDuration(stack);
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
			if (i < 0) return;

			if (!itemstack.isEmpty() || flag || !misfire) {
				if (itemstack.isEmpty()) {
					itemstack = new ItemStack(DeferredRegistryHandler.IRON_MUSKET_BALL.get());
				}

				float f = getArrowVelocity(i);
				if (!(f < 0.1D) && !misfire) {
					boolean flag1 = playerentity.abilities.instabuild || (itemstack.getItem() instanceof CustomArrowItem && ((CustomArrowItem) itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
					if (!worldIn.isClientSide) {
						for (int iter = 0; iter < bulletsToFire; ++iter) {
							CustomArrowItem arrowitem = (CustomArrowItem) (itemstack.getItem() instanceof CustomArrowItem ? itemstack.getItem() : DeferredRegistryHandler.IRON_MUSKET_BALL.get());
							AbstractArrowEntity abstractBulletEntity = arrowitem.createArrow(worldIn, itemstack, playerentity);
							abstractBulletEntity = customArrow(abstractBulletEntity);
							abstractBulletEntity.setKnockback(3);
							abstractBulletEntity.shootFromRotation(playerentity, playerentity.xRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f), playerentity.yRot + GeneralUtilities.getRandomNumber(-5.0f, 5.0f), 0.0F, f * 3.0F, 1.0F);
							if (f == 1.0F) {
								abstractBulletEntity.setCritArrow(true);
							}
							worldIn.addFreshEntity(abstractBulletEntity);
						}
						stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand()));
					}

					worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), this.getFireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!flag1 && !playerentity.abilities.instabuild) {
						itemstack.shrink(bulletsToFire);
						if (itemstack.isEmpty()) {
							playerentity.inventory.removeItem(itemstack);
						}
					}

					playerentity.awardStat(Stats.ITEM_USED.get(this));
					if (!playerentity.abilities.instabuild) {
						playerentity.getCooldowns().addCooldown(this, 100);
					}
				}
			}
		}
	}

	private int getBulletsToFire(ItemStack itemStack) {
		return Math.min(itemStack.getCount(), 4);
	}

	private SoundEvent getMisfireSound() {
		return DeferredRegistryHandler.FLINTLOCK_PISTOL_MISFIRE.get();
	}

	private SoundEvent getFireSound() {
		return DeferredRegistryHandler.BLUNDERBUSS_FIRE.get();
	}
}