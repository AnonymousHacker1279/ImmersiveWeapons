package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class SimplePistolItem extends AbstractBullet implements Vanishable {

	/**
	 * Constructor for SimplePistolItem.
	 * @param properties the <code>Properties</code> for the item
	 */
	public SimplePistolItem(Properties properties) {
		super(properties);
	}

	/**
	 * Find ammunition.
	 * @param itemStack the <code>ItemStack</code> to look for
	 * @param entityLiving the <code>LivingEntity</code> to be searched
	 * @return ItemStack
	 */
	ItemStack findAmmo(ItemStack itemStack, LivingEntity entityLiving) {
		Player playerEntity = (Player) entityLiving;
		if (!(itemStack.getItem() instanceof AbstractBullet)) {
			return ItemStack.EMPTY;
		} else {
			Predicate<ItemStack> predicate = ((AbstractBullet) itemStack.getItem()).getAmmoPredicate();
			ItemStack itemStack2 = AbstractBullet.getHeldAmmo(playerEntity, predicate);
			if (!itemStack2.isEmpty()) {
				return itemStack2;
			} else {
				predicate = ((AbstractBullet) itemStack.getItem()).getInventoryAmmoPredicate();
				for (int i = 0; i < playerEntity.getInventory().getContainerSize(); ++i) {
					ItemStack itemStack1 = playerEntity.getInventory().getItem(i);
					if (predicate.test(itemStack1)) {
						return itemStack1;
					}
				}

				return playerEntity.isCreative() ? new ItemStack(defaultAmmo()) : ItemStack.EMPTY;
			}
		}
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

			// Roll for misfire
			if (itemStack1.getItem() == DeferredRegistryHandler.WOOD_MUSKET_BALL.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(1, 10);
				if (randomNumber <= 3) {
					misfire = true;
					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), getMisfireSound(), SoundSource.PLAYERS, 1.0F, 1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);
					if (!playerEntity.isCreative()) {
						itemStack1.shrink(1);
						if (itemStack1.isEmpty()) {
							playerEntity.getInventory().removeItem(itemStack1);
						}
						itemStack.hurtAndBreak(5, playerEntity, (entity) -> entity.broadcastBreakEvent(entity.getUsedItemHand()));
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
						itemStack1.shrink(1);
						if (itemStack1.isEmpty()) {
							playerEntity.getInventory().removeItem(itemStack1);
						}
						itemStack.hurtAndBreak(5, playerEntity, (entity) -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
					}
				}
			}

			int i = getUseDuration(itemStack);
			i = ForgeEventFactory.onArrowLoose(itemStack, worldIn, playerEntity, i, !itemStack1.isEmpty() || flag);
			if (i < 0) return;

			if (!itemStack1.isEmpty() || flag || !misfire) {
				if (itemStack1.isEmpty()) {
					itemStack1 = new ItemStack(defaultAmmo());
				}

				if (!misfire) {
					boolean flag1 = playerEntity.isCreative() || (itemStack1.getItem() instanceof CustomArrowItem && ((CustomArrowItem) itemStack1.getItem()).isInfinite(itemStack1, itemStack, playerEntity));
					if (!worldIn.isClientSide) {
						CustomArrowItem customArrowItem = (CustomArrowItem) (itemStack1.getItem() instanceof CustomArrowItem ? itemStack1.getItem() : defaultAmmo());
						AbstractArrow abstractArrowEntity = customArrowItem.createArrow(worldIn, itemStack1, playerEntity);
						abstractArrowEntity.shootFromRotation(playerEntity, playerEntity.xRot, playerEntity.yRot, 0.0F, 3.0F, 1.0F);
						abstractArrowEntity.setCritArrow(true);

						itemStack.hurtAndBreak(1, playerEntity, (entity) -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));

						worldIn.addFreshEntity(abstractArrowEntity);
					}

					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), getFireSound(), SoundSource.PLAYERS, 1.0F, 1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);
					if (!flag1 && !playerEntity.isCreative()) {
						itemStack1.shrink(1);
						if (itemStack1.isEmpty()) {
							playerEntity.getInventory().removeItem(itemStack1);
						}
					}
					if (!playerEntity.isCreative()) {
						playerEntity.getCooldowns().addCooldown(this, 60);
					}
				}
			}
		}
	}

	/**
	 * Get the use animation.
	 * @param stack the <code>ItemStack</code> to get an animation for
	 * @return UseAction
	 */
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.CROSSBOW;
	}

	/**
	 * Runs when the player right-clicks.
	 * @param worldIn the <code>World</code> the player is in
	 * @param playerIn the <code>PlayerEntity</code> performing the action
	 * @param handIn the <code>Hand</code> the player is using
	 * @return ActionResult extending ItemStack
	 */
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		boolean flag = !findAmmo(itemstack, playerIn).isEmpty();

		InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
		if (ret != null) return ret;

		if (!playerIn.isCreative() && !flag) {
			return InteractionResultHolder.fail(itemstack);
		} else {
			playerIn.startUsingItem(handIn);
			return InteractionResultHolder.consume(itemstack);
		}
	}

	/**
	 * Get the use duration.
	 * @param stack the <code>ItemStack</code> to check
	 * @return int
	 */
	@Override
	public int getUseDuration(ItemStack stack) {
		return 100;
	}

	/**
	 * Get ammo predicates.
	 * @return Predicate extending ItemStack
	 */
	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return MUSKET_BALLS;
	}

	/**
	 * Get the repair material.
	 * @return Ingredient
	 */
	protected Ingredient getRepairMaterial() {
		return Ingredient.of(Items.IRON_INGOT);
	}

	/**
	 * Get the default ammunition.
	 * @return Item
	 */
	public Item defaultAmmo() {
		return DeferredRegistryHandler.IRON_MUSKET_BALL.get();
	}

	/**
	 * Check for a valid repair item.
	 * @param toRepair the <code>ItemStack</code> being repaired
	 * @param repair the <code>ItemStack</code> to repair the first one
	 * @return boolean
	 */
	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	/**
	 * Get the misfire sound.
	 * @return SoundEvent
	 */
	SoundEvent getMisfireSound() {
		return DeferredRegistryHandler.FLINTLOCK_PISTOL_MISFIRE.get();
	}

	/**
	 * Get the fire sound.
	 * @return SoundEvent
	 */
	public SoundEvent getFireSound() {
		return DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get();
	}
}