package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class MorphineItem extends Item {

	public MorphineItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult rayTraceResult = Minecraft.getInstance().objectMouseOver;
		if (rayTraceResult.getType() != Type.ENTITY) {
			playerIn.addPotionEffect(new EffectInstance(DeferredRegistryHandler.MORPHINE_EFFECT.get(), 1800, 0, false, true));
			if (!playerIn.abilities.isCreativeMode) {
				itemstack.shrink(1);
				playerIn.inventory.addItemStackToInventory(new ItemStack(DeferredRegistryHandler.USED_SYRINGE.get()));
				playerIn.getCooldownTracker().setCooldown(this, 2400);
			}
		}

		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	}

	@Override
	public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entity, Hand hand) {
		if (entity.world.isRemote) {
			return ActionResultType.PASS;
		}

		entity.addPotionEffect(new EffectInstance(DeferredRegistryHandler.MORPHINE_EFFECT.get(), 1800, 0, false, true));
		if (!playerIn.abilities.isCreativeMode) {
			stack.shrink(1);
			playerIn.inventory.addItemStackToInventory(new ItemStack(DeferredRegistryHandler.USED_SYRINGE.get()));
		}

		return ActionResultType.PASS;
	}
}