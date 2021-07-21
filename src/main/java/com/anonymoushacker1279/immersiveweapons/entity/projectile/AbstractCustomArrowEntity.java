package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractCustomArrowEntity extends AbstractArrowEntity {
	Item referenceItem;

	/**
	 * Constructor for AbstractCustomArrowEntity.
	 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
	 * @param world the <code>World</code> the entity is in
	 */
	AbstractCustomArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world) {
		super(type, world);
		referenceItem = null;
	}

	/**
	 * Constructor for AbstractCustomArrowEntity.
	 * @param type the <code>EntityType</code> instance; must extend AbstractArrowEntity
	 * @param shooter the <code>LivingEntity</code> shooting the entity
	 * @param world the <code>World</code> the entity is in
	 */
	AbstractCustomArrowEntity(EntityType<? extends AbstractCustomArrowEntity> type, LivingEntity shooter, World world) {
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
	AbstractCustomArrowEntity(EntityType<? extends AbstractCustomArrowEntity> type, World world, double x, double y, double z) {
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
	public IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	/**
	 * Runs once each tick.
	 */
	@Override
	public void tick() {
		super.tick();
		boolean flag = isNoPhysics();
		Vector3d vector3d = getDeltaMovement();
		if (xRotO == 0.0F && yRotO == 0.0F) {
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		BlockPos blockPos = blockPosition();
		BlockState blockState = level.getBlockState(blockPos);
		if (!blockState.isAir(level, blockPos) && !flag) {
			VoxelShape voxelShape = blockState.getCollisionShape(level, blockPos);
			if (!voxelShape.isEmpty()) {
				Vector3d vector3d1 = position();

				for (AxisAlignedBB axisalignedbb : voxelShape.toAabbs()) {
					if (axisalignedbb.move(blockPos).contains(vector3d1)) {
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
			Vector3d vector3d2 = position();
			Vector3d vector3d3 = vector3d2.add(vector3d);
			RayTraceResult rayTraceResult = level.clip(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
			if (rayTraceResult.getType() != RayTraceResult.Type.MISS) {
				vector3d3 = rayTraceResult.getLocation();
			}

			while (isAlive()) {
				EntityRayTraceResult entityRayTraceResult = findHitEntity(vector3d2, vector3d3);
				if (entityRayTraceResult != null) {
					rayTraceResult = entityRayTraceResult;
				}

				if (rayTraceResult != null && rayTraceResult.getType() == RayTraceResult.Type.ENTITY) {
					Entity entity = null;
					if (rayTraceResult instanceof EntityRayTraceResult) {
						entity = ((EntityRayTraceResult) rayTraceResult).getEntity();
					}
					Entity entity1 = getOwner();
					if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity) entity1).canHarmPlayer((PlayerEntity) entity)) {
						rayTraceResult = null;
						entityRayTraceResult = null;
					}
				}

				if (rayTraceResult != null && rayTraceResult.getType() != RayTraceResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, rayTraceResult)) {
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
			float f1 = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			if (flag) {
				yRot = (float) (MathHelper.atan2(-d3, -d0) * (180F / (float) Math.PI));
			} else {
				yRot = (float) (MathHelper.atan2(d3, d0) * (180F / (float) Math.PI));
			}

			xRot = (float) (MathHelper.atan2(d4, f1) * (180F / (float) Math.PI));
			xRot = lerpRotation(xRotO, xRot);
			yRot = lerpRotation(yRotO, yRot);
			float f2 = 0.99F;
			if (isInWater()) {
				for (int j = 0; j < 4; ++j) {
					level.addParticle(ParticleTypes.BUBBLE, d5 - d3 * 0.25D, d1 - d4 * 0.25D, d2 - d0 * 0.25D, d3, d4, d0);
				}

				f2 = getWaterInertia();
			}

			setDeltaMovement(vector3d.scale(f2));
			if (!isNoGravity() && !flag) {
				Vector3d vector3d4 = getDeltaMovement();
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