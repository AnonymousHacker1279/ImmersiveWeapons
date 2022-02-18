package com.anonymoushacker1279.immersiveweapons.item.projectile.gun;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.arrow.AbstractArrowItem;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class SimplePistolItem extends AbstractGunItem implements Vanishable {

	/**
	 * Constructor for SimplePistolItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	public SimplePistolItem(Properties properties) {
		super(properties);
	}

	/**
	 * Find ammunition.
	 *
	 * @param itemStack    the <code>ItemStack</code> to look for
	 * @param livingEntity the <code>LivingEntity</code> to be searched
	 * @return ItemStack
	 */
	ItemStack findAmmo(ItemStack itemStack, LivingEntity livingEntity) {
		Player playerEntity = (Player) livingEntity;
		if (!(itemStack.getItem() instanceof AbstractGunItem)) {
			return ItemStack.EMPTY;
		} else {
			Predicate<ItemStack> ammoPredicate = ((AbstractGunItem) itemStack.getItem()).getAmmoPredicate();
			ItemStack heldAmmo = AbstractGunItem.getHeldAmmo(playerEntity, ammoPredicate);
			if (!heldAmmo.isEmpty()) {
				return heldAmmo;
			} else {
				ammoPredicate = ((AbstractGunItem) itemStack.getItem()).getInventoryAmmoPredicate();
				for (int i = 0; i < playerEntity.getInventory().getContainerSize(); ++i) {
					ItemStack ammoItem = playerEntity.getInventory().getItem(i);
					if (ammoPredicate.test(ammoItem)) {
						return ammoItem;
					}
				}

				return playerEntity.isCreative() ? new ItemStack(defaultAmmo()) : ItemStack.EMPTY;
			}
		}
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

			// Roll for misfire
			if (ammo.getItem() == DeferredRegistryHandler.WOOD_MUSKET_BALL.get()) {
				if (GeneralUtilities.getRandomNumber(1, 10) <= 3) {
					misfire = true;
					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
							getMisfireSound(), SoundSource.PLAYERS, 1.0F,
							1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);

					if (!isCreative) {
						ammo.shrink(1);
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
						ammo.shrink(1);
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

						AbstractArrow abstractBulletEntity = customArrowItem.createArrow(worldIn, ammo, playerEntity);
						abstractBulletEntity.shootFromRotation(playerEntity, playerEntity.xRot, playerEntity.yRot,
								0.0F, 3.0F, 1.0F);

						// Roll for random crits
						if (GeneralUtilities.getRandomNumber(0f, 1f) <= 0.1f) {
							abstractBulletEntity.setCritArrow(true);
						}

						abstractBulletEntity.setOwner(playerEntity);
						abstractBulletEntity.pickup = Pickup.DISALLOWED;

						itemStack.hurtAndBreak(1, playerEntity, (entity) ->
								entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));

						worldIn.addFreshEntity(abstractBulletEntity);
					}

					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
							getFireSound(), SoundSource.PLAYERS, 1.0F,
							1.0F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 1.2F) + 0.5F);

					if (!isCreative) {
						ammo.shrink(1);
						if (ammo.isEmpty()) {
							playerEntity.getInventory().removeItem(ammo);
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
	 * Runs when the player right-clicks.
	 *
	 * @param worldIn  the <code>World</code> the player is in
	 * @param playerIn the <code>PlayerEntity</code> performing the action
	 * @param handIn   the <code>Hand</code> the player is using
	 * @return ActionResult extending ItemStack
	 */
	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn,
	                                                       @NotNull InteractionHand handIn) {

		ItemStack itemInHand = playerIn.getItemInHand(handIn);
		boolean hasAmmo = !findAmmo(itemInHand, playerIn).isEmpty();

		InteractionResultHolder<ItemStack> resultHolder = ForgeEventFactory.onArrowNock(itemInHand, worldIn, playerIn,
				handIn, hasAmmo);
		if (resultHolder != null) {
			return resultHolder;
		}

		if (!playerIn.isCreative() && !hasAmmo) {
			return InteractionResultHolder.fail(itemInHand);
		} else {
			playerIn.startUsingItem(handIn);
			return InteractionResultHolder.consume(itemInHand);
		}
	}

	/**
	 * Get the use duration.
	 *
	 * @param stack the <code>ItemStack</code> to check
	 * @return int
	 */
	@Override
	public int getUseDuration(@NotNull ItemStack stack) {
		return 100;
	}

	/**
	 * Get ammo predicates.
	 *
	 * @return Predicate extending ItemStack
	 */
	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return MUSKET_BALLS;
	}

	/**
	 * Get the repair material.
	 *
	 * @return Ingredient
	 */
	protected Ingredient getRepairMaterial() {
		return Ingredient.of(Items.IRON_INGOT);
	}

	/**
	 * Get the default ammunition.
	 *
	 * @return Item
	 */
	public Item defaultAmmo() {
		return DeferredRegistryHandler.IRON_MUSKET_BALL.get();
	}

	/**
	 * Check for a valid repair item.
	 *
	 * @param toRepair the <code>ItemStack</code> being repaired
	 * @param repair   the <code>ItemStack</code> to repair the first one
	 * @return boolean
	 */
	@Override
	public boolean isValidRepairItem(@NotNull ItemStack toRepair, @NotNull ItemStack repair) {
		return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	/**
	 * Get the misfire sound.
	 *
	 * @return SoundEvent
	 */
	SoundEvent getMisfireSound() {
		return DeferredRegistryHandler.FLINTLOCK_PISTOL_MISFIRE.get();
	}

	/**
	 * Get the fire sound.
	 *
	 * @return SoundEvent
	 */
	public SoundEvent getFireSound() {
		return DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get();
	}
}