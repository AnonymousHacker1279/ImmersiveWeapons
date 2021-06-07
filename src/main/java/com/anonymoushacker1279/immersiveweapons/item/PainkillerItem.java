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
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		playerIn.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 1200, 0, false, true));
		if (!playerIn.abilities.instabuild) {
			itemstack.shrink(1);
			playerIn.getCooldowns().addCooldown(this, 2400);
		}

		return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
	}
}