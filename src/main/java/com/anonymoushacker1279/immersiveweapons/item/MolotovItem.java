package com.anonymoushacker1279.immersiveweapons.item;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.MolotovEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class MolotovItem extends Item {

	public MolotovItem(Item.Properties builder) {
		super(builder);
	}

	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), DeferredRegistryHandler.GENERIC_WHOOSH.get(), SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		if (!worldIn.isClientSide) {
			MolotovEntity molotovEntity = new MolotovEntity(worldIn, playerIn);
			molotovEntity.setItem(itemstack);
			molotovEntity.shootFromRotation(playerIn, playerIn.xRot, playerIn.yRot, 0.01F, 1F, 1.0F);
			worldIn.addFreshEntity(molotovEntity);
		}

		playerIn.awardStat(Stats.ITEM_USED.get(this));
		if (!playerIn.abilities.instabuild) {
			itemstack.shrink(1);
			playerIn.getCooldowns().addCooldown(this, 100);    // Helps to prevent spamming
		}

		return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
	}
}