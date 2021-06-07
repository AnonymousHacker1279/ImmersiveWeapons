package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock.Properties;

public class BarbedWireFenceBlock extends FenceBlock {

	private final DamageSource damageSource = new DamageSource("immersiveweapons.barbed_wire");
	private int soundCooldown = 0;

	public BarbedWireFenceBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity) {
			entity.makeStuckInBlock(state, new Vector3d(0.45F, 0.40D, 0.45F));
			if (!world.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				double d0 = Math.abs(entity.getX() - entity.xOld);
				double d1 = Math.abs(entity.getZ() - entity.zOld);
				if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
					entity.hurt(damageSource, 2.0F);
				}
			}
			if (entity instanceof PlayerEntity && soundCooldown <= 0) {
				world.playSound((PlayerEntity) entity, pos, DeferredRegistryHandler.BARBED_WIRE_RATTLE.get(), SoundCategory.BLOCKS, 1f, 1f);
				soundCooldown = 40;
			} else {
				soundCooldown--;
			}
		}
	}
}