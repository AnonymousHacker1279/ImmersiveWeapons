package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class BandageItem extends Item {

	public BandageItem(Properties properties) {
		super(properties);
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 40;
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		RayTraceResult rayTraceResult = Minecraft.getInstance().hitResult;
		if (rayTraceResult != null && rayTraceResult.getType() != Type.ENTITY) {
			playerIn.addEffect(new EffectInstance(Effects.REGENERATION, 240, 0, false, true));
			if (!playerIn.abilities.instabuild) {
				itemstack.shrink(1);
				playerIn.getCooldowns().addCooldown(this, 300);
			}
		}

		return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
	}

	@Override
	public ActionResultType interactLivingEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity entity, Hand hand) {
		if (entity.level.isClientSide) {
			return ActionResultType.PASS;
		}

		entity.addEffect(new EffectInstance(Effects.REGENERATION, 240, 0, false, true));
		if (!playerIn.abilities.instabuild) {
			stack.shrink(1);
		}

		return ActionResultType.PASS;
	}

	@Override
	public UseAction getUseAnimation(ItemStack stack) {
		return UseAction.DRINK;
	}
}