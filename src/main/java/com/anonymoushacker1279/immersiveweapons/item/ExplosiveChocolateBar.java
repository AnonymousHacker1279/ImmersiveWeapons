package com.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class ExplosiveChocolateBar extends Item {

	private static final DamageSource damageSource = new DamageSource("immersiveweapons.explosive_chocolate_bar");

	public ExplosiveChocolateBar(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		worldIn.createExplosion(null, damageSource, null, entityLiving.getPosition().getX(), entityLiving.getPosition().getY(), entityLiving.getPosition().getZ(), 2.0F, false, Explosion.Mode.NONE);
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
}