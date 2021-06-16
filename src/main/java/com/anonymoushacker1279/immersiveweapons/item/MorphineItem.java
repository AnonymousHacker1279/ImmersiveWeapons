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

	public MorphineItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		playerIn.addEffect(new EffectInstance(DeferredRegistryHandler.MORPHINE_EFFECT.get(), 1800, 0, false, true));
		if (!playerIn.abilities.instabuild) {
			itemstack.shrink(1);
			playerIn.inventory.add(new ItemStack(DeferredRegistryHandler.USED_SYRINGE.get()));
			playerIn.getCooldowns().addCooldown(this, 2400);
		}

		return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entity, Hand hand) {
		if (entity.level.isClientSide) {
			return ActionResultType.PASS;
		}

		entity.addEffect(new EffectInstance(DeferredRegistryHandler.MORPHINE_EFFECT.get(), 1800, 0, false, true));
		if (!playerIn.abilities.instabuild) {
			stack.shrink(1);
			playerIn.inventory.add(new ItemStack(DeferredRegistryHandler.USED_SYRINGE.get()));
		}

		return ActionResultType.PASS;
	}
}