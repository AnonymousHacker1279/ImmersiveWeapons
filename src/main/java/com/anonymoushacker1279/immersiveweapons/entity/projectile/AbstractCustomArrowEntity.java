package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public abstract class AbstractCustomArrowEntity extends AbstractArrow {
	Item referenceItem;

	/**
	 * Constructor for AbstractCustomArrowEntity.
	 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
	 * @param world the <code>World</code> the entity is in
	 */
	AbstractCustomArrowEntity(EntityType<? extends AbstractArrow> type, Level world) {
		super(type, world);
		referenceItem = null;
	}

	/**
	 * Constructor for AbstractCustomArrowEntity.
	 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
	 * @param shooter the <code>LivingEntity</code> shooting the entity
	 * @param world the <code>World</code> the entity is in
	 */
	AbstractCustomArrowEntity(EntityType<? extends AbstractCustomArrowEntity> type, LivingEntity shooter, Level world) {
		super(type, shooter, world);
		referenceItem = null;
	}

	/**
	 * Constructor for AbstractCustomArrowEntity.
	 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
	 * @param world the <code>World</code> the entity is in
	 * @param x the X position
	 * @param y the Y position
	 * @param z the Z position
	 */
	AbstractCustomArrowEntity(EntityType<? extends AbstractCustomArrowEntity> type, Level world, double x, double y, double z) {
		super(type, x, y, z, world);
		referenceItem = null;
	}

	/**
	 * Get the pickup item.
	 * @return ItemStack
	 */
	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(referenceItem);
	}

	/**
	 * Get the entity spawn packet.
	 * @return IPacket
	 */
	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	/**
	 * Runs once each tick.
	 */
	@Override
	public void tick() {
		super.tick();
		boolean flag = isNoPhysics();
		Vec3 vector3d = getDeltaMovement();
		float yRot;
		float xRot;
		if (xRotO == 0.0F && yRotO == 0.0F) {
			double horizontalDistanceSqr = vector3d.horizontalDistanceSqr();
			yRot = (float) (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (Mth.atan2(vector3d.y, horizontalDistanceSqr) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		BlockPos blockPos = blockPosition();
		BlockState blockState = level.getBlockState(blockPos);
		if (!blockState.isAir() && !flag) {
			VoxelShape voxelShape = blockState.getCollisionShape(level, blockPos);
			if (!voxelShape.isEmpty()) {
				Vec3 vector3d1 = position();

				for (AABB aabb : voxelShape.toAabbs()) {
					if (aabb.move(blockPos).contains(vector3d1)) {
						inGround = true;
						break;
					}
				}
			}
		}

		if (shakeTime > 0) {
			--shakeTime;
		}

		if (isInWaterOrRain()) {
			clearFire();
		}

		if (inGround && !flag) {
			if (shouldFall()) {
				startFalling();
			} else if (!level.isClientSide) {
				tickDespawn();
			}

			++inGroundTime;
		} else {
			inGroundTime = 0;
			Vec3 vector3d2 = position();
			Vec3 vector3d3 = vector3d2.add(vector3d);
			HitResult rayTraceResult = level.clip(new ClipContext(vector3d2, vector3d3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
			if (rayTraceResult.getType() != HitResult.Type.MISS) {
				vector3d3 = rayTraceResult.getLocation();
			}

			while (isAlive()) {
				EntityHitResult entityRayTraceResult = findHitEntity(vector3d2, vector3d3);
				if (entityRayTraceResult != null) {
					rayTraceResult = entityRayTraceResult;
				}

				if (rayTraceResult != null && rayTraceResult.getType() == HitResult.Type.ENTITY) {
					Entity entity = null;
					if (rayTraceResult instanceof EntityHitResult) {
						entity = ((EntityHitResult) rayTraceResult).getEntity();
					}
					Entity entity1 = getOwner();
					if (entity instanceof Player && entity1 instanceof Player && !((Player) entity1).canHarmPlayer((Player) entity)) {
						rayTraceResult = null;
						entityRayTraceResult = null;
					}
				}

				if (rayTraceResult != null && rayTraceResult.getType() != HitResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, rayTraceResult)) {
					onHit(rayTraceResult);
					hasImpulse = true;
				}

				if (entityRayTraceResult == null || getPierceLevel() <= 0) {
					break;
				}

				rayTraceResult = null;
			}

			vector3d = getDeltaMovement();
			double d3 = vector3d.x;
			double d4 = vector3d.y;
			double d0 = vector3d.z;
			if (isCritArrow()) {
				for (int i = 0; i < 4; ++i) {
					level.addParticle(ParticleTypes.CRIT, getX() + d3 * i / 4.0D, getY() + d4 * i / 4.0D, getZ() + d0 * i / 4.0D, -d3, -d4 + 0.2D, -d0);
				}
			}

			double d5 = getX() + d3;
			double d1 = getY() + d4;
			double d2 = getZ() + d0;

			float f2 = 0.99F;
			if (isInWater()) {
				for (int j = 0; j < 4; ++j) {
					level.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
				}

				f2 = getWaterInertia();
			}

			setDeltaMovement(vector3d.scale(f2));
			if (!isNoGravity() && !flag) {
				Vec3 vector3d4 = getDeltaMovement();
				setDeltaMovement(vector3d4.x, vector3d4.y - 0.05d + getMovementModifier(), vector3d4.z);
			}

			setPos(d5, d1, d2);
			checkInsideBlocks();
		}
	}

	/**
	 * Get the movement modifier.
	 * @return double
	 */
	public double getMovementModifier() {
		return 0.0d;
	}
}