package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class BottleItem {

	public static class AlcoholBottleItem extends Item {

		public AlcoholBottleItem(Properties properties) {
			super(properties);
			properties.craftRemainder(this.getItem());
		}

		@Override
		public boolean hasContainerItem(ItemStack stack) {
			return true;
		}

		@Override
		public ItemStack getContainerItem(ItemStack stack) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}

		@Override
		public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
			ItemStack itemstack = playerIn.getItemInHand(handIn);

			// Give effects upon use for 30 seconds
			playerIn.addEffect(new EffectInstance(Effects.CONFUSION, 600, 0, false, true));
			playerIn.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 600, 0, false, true));
			playerIn.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 600, 0, false, true));

			if (!playerIn.abilities.instabuild) {
				itemstack.shrink(1);
				playerIn.addItem(new ItemStack(Items.GLASS_BOTTLE));
				playerIn.getCooldowns().addCooldown(this, 600);
			}

			return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
		}

		@Override
		public UseAction getUseAnimation(ItemStack stack) {
			return UseAction.DRINK;
		}
	}

	public static class WineBottleItem extends Item {

		public WineBottleItem(Properties properties) {
			super(properties);
			properties.craftRemainder(this.getItem());
		}

		@Override
		public boolean hasContainerItem(ItemStack stack) {
			return true;
		}

		@Override
		public ItemStack getContainerItem(ItemStack stack) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}

		@Override
		public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
			ItemStack itemstack = playerIn.getItemInHand(handIn);
			
			playerIn.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, 360, 0, false, true));
			playerIn.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 360, 0, false, true));

			if (!playerIn.abilities.instabuild) {
				itemstack.shrink(1);
				playerIn.addItem(new ItemStack(Items.GLASS_BOTTLE));
				playerIn.getCooldowns().addCooldown(this, 600);
			}

			return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
		}

		@Override
		public UseAction getUseAnimation(ItemStack stack) {
			return UseAction.DRINK;
		}
	}
}