package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeBombEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class SmokeBombItem extends Item {

	private String color = "none";

	public SmokeBombItem(Item.Properties builder, String color) {
		super(builder);
		this.color = color;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		worldIn.playSound((PlayerEntity) null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), DeferredRegistryHandler.GENERIC_WHOOSH.get(), SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isRemote) {
			SmokeBombEntity.setColor(color);
			SmokeBombEntity smokeBombEntity = new SmokeBombEntity(worldIn, playerIn);
			smokeBombEntity.setItem(itemstack);
			smokeBombEntity.setDirectionAndMovement(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
			worldIn.addEntity(smokeBombEntity);
		}

		playerIn.addStat(Stats.ITEM_USED.get(this));
		if (!playerIn.abilities.isCreativeMode) {
			itemstack.shrink(1);
			playerIn.getCooldownTracker().setCooldown(this, 100);    // Helps to prevent spamming, mostly to reduce the total particles in an area
		}

		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	}
}