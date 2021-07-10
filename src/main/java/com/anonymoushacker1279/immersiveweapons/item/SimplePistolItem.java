package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class SimplePistolItem extends ShootableBullet implements IVanishable {

	public SimplePistolItem(Item.Properties builder) {
		super(builder);
	}

	public static float getArrowVelocity(int charge) {
		float f = charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	public ItemStack findAmmo(ItemStack itemStack, LivingEntity entityLiving) {
		PlayerEntity playerentity = (PlayerEntity) entityLiving;
		if (!(itemStack.getItem() instanceof ShootableBullet)) {
			return ItemStack.EMPTY;
		} else {
			Predicate<ItemStack> predicate = ((ShootableBullet) itemStack.getItem()).getAmmoPredicate();
			ItemStack itemstack = ShootableBullet.getHeldAmmo(playerentity, predicate);
			if (!itemstack.isEmpty()) {
				return itemstack;
			} else {
				predicate = ((ShootableBullet) itemStack.getItem()).getInventoryAmmoPredicate();

				for (int i = 0; i < playerentity.inventory.getContainerSize(); ++i) {
					ItemStack itemStack1 = playerentity.inventory.getItem(i);
					if (predicate.test(itemStack1)) {
						return itemStack1;
					}
				}

				return playerentity.abilities.instabuild ? new ItemStack(defaultAmmo()) : ItemStack.EMPTY;
			}
		}
	}

	@Override
	public void releaseUsing(ItemStack itemStack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) entityLiving;
			boolean flag = playerEntity.abilities.instabuild;
			boolean misfire = false;
			ItemStack itemStack1 = findAmmo(itemStack, entityLiving);

			if (itemStack1.getItem() == DeferredRegistryHandler.WOOD_MUSKET_BALL.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(1, 10);
				if (randomNumber <= 3) {
					misfire = true;
					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), getMisfireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
					if (!playerEntity.abilities.instabuild) {
						itemStack1.shrink(1);
						if (itemStack1.isEmpty()) {
							playerEntity.inventory.removeItem(itemStack1);
						}
						itemStack.hurtAndBreak(5, playerEntity, (entity) -> entity.broadcastBreakEvent(entity.getUsedItemHand()));
					}
				}
			}
			if (itemStack1.getItem() == DeferredRegistryHandler.STONE_MUSKET_BALL.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(1, 20);
				if (randomNumber <= 3) {
					misfire = true;
					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), getMisfireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
					if (!playerEntity.abilities.instabuild) {
						itemStack1.shrink(1);
						if (itemStack1.isEmpty()) {
							playerEntity.inventory.removeItem(itemStack1);
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

				float f = getArrowVelocity(i);
				if (!(f < 0.1D) && !misfire) {
					boolean flag1 = playerEntity.abilities.instabuild || (itemStack1.getItem() instanceof CustomArrowItem && ((CustomArrowItem) itemStack1.getItem()).isInfinite(itemStack1, itemStack, playerEntity));
					if (!worldIn.isClientSide) {
						CustomArrowItem customArrowItem = (CustomArrowItem) (itemStack1.getItem() instanceof CustomArrowItem ? itemStack1.getItem() : defaultAmmo());
						AbstractArrowEntity abstractArrowEntity = customArrowItem.createArrow(worldIn, itemStack1, playerEntity);
						abstractArrowEntity = customArrow(abstractArrowEntity);
						abstractArrowEntity.shootFromRotation(playerEntity, playerEntity.xRot, playerEntity.yRot, 0.0F, f * 3.0F, 1.0F);
						if (f == 1.0F) {
							abstractArrowEntity.setCritArrow(true);
						}

						itemStack.hurtAndBreak(1, playerEntity, (entity) -> entity.broadcastBreakEvent(playerEntity.getUsedItemHand()));

						worldIn.addFreshEntity(abstractArrowEntity);
					}

					worldIn.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), getFireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!flag1 && !playerEntity.abilities.instabuild) {
						itemStack1.shrink(1);
						if (itemStack1.isEmpty()) {
							playerEntity.inventory.removeItem(itemStack1);
						}
					}

					playerEntity.awardStat(Stats.ITEM_USED.get(this));
					if (!playerEntity.abilities.instabuild) {
						playerEntity.getCooldowns().addCooldown(this, 60);
					}
				}
			}
		}
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getUseDuration(ItemStack stack) {
		return 100;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.CROSSBOW;
	}


	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		boolean flag = !findAmmo(itemstack, playerIn).isEmpty();

		ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
		if (ret != null) return ret;

		if (!playerIn.abilities.instabuild && !flag) {
			return ActionResult.fail(itemstack);
		} else {
			playerIn.startUsingItem(handIn);
			return ActionResult.consume(itemstack);
		}
	}

	/**
	 * Get the predicate to match ammunition when searching the player's inventory, not their main/offhand
	 */
	@Override
	public Predicate<ItemStack> getInventoryAmmoPredicate() {
		return MUSKET_BALLS;
	}

	public AbstractArrowEntity customArrow(AbstractArrowEntity arrow) {
		return arrow;
	}

	public Ingredient getRepairMaterial() {
		return Ingredient.of(Items.IRON_INGOT);
	}

	public Item defaultAmmo() {
		return DeferredRegistryHandler.IRON_MUSKET_BALL.get();
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	public SoundEvent getMisfireSound() {
		return DeferredRegistryHandler.FLINTLOCK_PISTOL_MISFIRE.get();
	}

	public SoundEvent getFireSound() {
		return DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get();
	}
}