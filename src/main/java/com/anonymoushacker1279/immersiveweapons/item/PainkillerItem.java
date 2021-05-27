package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PainkillerItem extends Item {

	public PainkillerItem(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		playerIn.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1200, 0, false, true));
		if (!playerIn.abilities.isCreativeMode) {
			itemstack.shrink(1);
			playerIn.getCooldownTracker().setCooldown(this, 2400);
		}

		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	}
}