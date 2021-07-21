package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MorphineItem extends Item {

	/**
	 * Constructor for MorphineItem.
	 * @param properties the <code>Properties</code> for the item
	 */
	public MorphineItem(Properties properties) {
		super(properties);
	}

	/**
	 * Runs when the player right-clicks.
	 * @param worldIn the <code>World</code> the player is in
	 * @param playerIn the <code>PlayerEntity</code> performing the action
	 * @param handIn the <code>Hand</code> the player is using
	 * @return ActionResult extending ItemStack
	 */
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		playerIn.addEffect(new EffectInstance(DeferredRegistryHandler.MORPHINE_EFFECT.get(), 1800, 0, false, true));
		if (!playerIn.isCreative()) {
			itemstack.shrink(1);
			playerIn.inventory.add(new ItemStack(DeferredRegistryHandler.USED_SYRINGE.get()));
			playerIn.getCooldowns().addCooldown(this, 2400);
		}

		return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
	}

	/**
	 * Runs when the player right-clicks an entity.
	 * @param stack the <code>ItemStack</code> right-clicked with
	 * @param playerIn the <code>PlayerEntity</code> performing the action
	 * @param entity the <code>LivingEntity</code> being interacted with
	 * @param hand the <code>Hand</code> the player is using
	 * @return ActionResultType
	 */
	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entity, Hand hand) {
		if (entity.level.isClientSide) {
			return ActionResultType.PASS;
		}
		entity.addEffect(new EffectInstance(DeferredRegistryHandler.MORPHINE_EFFECT.get(), 1800, 0, false, true));
		if (!playerIn.isCreative()) {
			stack.shrink(1);
			playerIn.inventory.add(new ItemStack(DeferredRegistryHandler.USED_SYRINGE.get()));
		}
		
		return ActionResultType.PASS;
	}
}