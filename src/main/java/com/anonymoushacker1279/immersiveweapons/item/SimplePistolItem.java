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

import java.util.function.Predicate;

public class SimplePistolItem extends ShootableBullet implements IVanishable {

	public SimplePistolItem(Item.Properties builder) {
		super(builder);
	}

	/**
	 * Gets the velocity of the arrow entity from the bow's charge
	 */
	public static float getArrowVelocity(int charge) {
		float f = charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	public ItemStack findAmmo(ItemStack shootable, LivingEntity entityLiving) {
		PlayerEntity playerentity = (PlayerEntity) entityLiving;
		if (!(shootable.getItem() instanceof ShootableBullet)) {
			return ItemStack.EMPTY;
		} else {
			Predicate<ItemStack> predicate = ((ShootableBullet) shootable.getItem()).getAmmoPredicate();
			ItemStack itemstack = ShootableBullet.getHeldAmmo(playerentity, predicate);
			if (!itemstack.isEmpty()) {
				return itemstack;
			} else {
				predicate = ((ShootableBullet) shootable.getItem()).getInventoryAmmoPredicate();

				for (int i = 0; i < playerentity.inventory.getContainerSize(); ++i) {
					ItemStack itemstack1 = playerentity.inventory.getItem(i);
					if (predicate.test(itemstack1)) {
						return itemstack1;
					}
				}

				return playerentity.abilities.instabuild ? new ItemStack(DeferredRegistryHandler.IRON_MUSKET_BALL.get()) : ItemStack.EMPTY;
			}
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity playerentity = (PlayerEntity) entityLiving;
			boolean flag = playerentity.abilities.instabuild;
			boolean misfire = false;
			ItemStack itemstack = findAmmo(stack, entityLiving);

			if (itemstack.getItem() == DeferredRegistryHandler.WOOD_MUSKET_BALL.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(1, 10);
				if (randomNumber <= 3) {
					misfire = true;
					worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), this.getMisfireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
					if (!playerentity.abilities.instabuild) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							playerentity.inventory.removeItem(itemstack);
						}
						stack.hurtAndBreak(5, playerentity, (p_220009_1_) -> {
							p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand());
						});
					}
				}
			}
			if (itemstack.getItem() == DeferredRegistryHandler.STONE_MUSKET_BALL.get()) {
				int randomNumber = GeneralUtilities.getRandomNumber(1, 20);
				if (randomNumber <= 3) {
					misfire = true;
					worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), this.getMisfireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + 0.5F);
					if (!playerentity.abilities.instabuild) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							playerentity.inventory.removeItem(itemstack);
						}
						stack.hurtAndBreak(5, playerentity, (p_220009_1_) -> {
							p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand());
						});
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
						CustomArrowItem arrowitem = (CustomArrowItem) (itemstack.getItem() instanceof CustomArrowItem ? itemstack.getItem() : DeferredRegistryHandler.IRON_MUSKET_BALL.get());
						AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
						abstractarrowentity = customArrow(abstractarrowentity);
						abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * 3.0F, 1.0F);
						if (f == 1.0F) {
							abstractarrowentity.setCritArrow(true);
						}

						stack.hurtAndBreak(1, playerentity, (p_220009_1_) -> {
							p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand());
						});

						worldIn.addFreshEntity(abstractarrowentity);
					}

					worldIn.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), this.getFireSound(), SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
					if (!flag1 && !playerentity.abilities.instabuild) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							playerentity.inventory.removeItem(itemstack);
						}
					}

					playerentity.awardStat(Stats.ITEM_USED.get(this));
					if (!playerentity.abilities.instabuild) {
						playerentity.getCooldowns().addCooldown(this, 60);
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

	/**
	 * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
	 * {@link #onItemUse}.
	 */
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		boolean flag = !findAmmo(itemstack, playerIn).isEmpty();

		ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
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

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return this.getRepairMaterial().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	private SoundEvent getMisfireSound() {
		return DeferredRegistryHandler.FLINTLOCK_PISTOL_MISFIRE.get();
	}

	private SoundEvent getFireSound() {
		return DeferredRegistryHandler.FLINTLOCK_PISTOL_FIRE.get();
	}
}