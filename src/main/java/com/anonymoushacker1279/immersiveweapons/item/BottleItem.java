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

public class BottleItem {

	public static class AlcoholBottleItem extends Item {
		
		public AlcoholBottleItem(Properties properties) {
			super(properties);
			properties.containerItem(this.getItem());
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
		public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
			ItemStack itemstack = playerIn.getHeldItem(handIn);
			
			// Give effects upon use for 30 seconds
			playerIn.addPotionEffect(new EffectInstance(Effects.NAUSEA, 600, 0, false, true));
			playerIn.addPotionEffect(new EffectInstance(Effects.STRENGTH, 600, 0, false, true));
			playerIn.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 600, 0, false, true));

			if (!playerIn.abilities.isCreativeMode) {
				itemstack.shrink(1);
				playerIn.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
				playerIn.getCooldownTracker().setCooldown(this, 600);
			}

			return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
		}
		
		@Override
		public UseAction getUseAction(ItemStack stack) {
			return UseAction.DRINK;
		}
	}
	
	public static class WineBottleItem extends Item {
		
		public WineBottleItem(Properties properties) {
			super(properties);
			properties.containerItem(this.getItem());
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
		public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
			ItemStack itemstack = playerIn.getHeldItem(handIn);
			
			// Give effects upon use for 30 seconds
			playerIn.addPotionEffect(new EffectInstance(Effects.STRENGTH, 360, 0, false, true));
			playerIn.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 360, 0, false, true));

			if (!playerIn.abilities.isCreativeMode) {
				itemstack.shrink(1);
				playerIn.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
				playerIn.getCooldownTracker().setCooldown(this, 600);
			}

			return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
		}
		
		@Override
		public UseAction getUseAction(ItemStack stack) {
			return UseAction.DRINK;
		}
	}
}
