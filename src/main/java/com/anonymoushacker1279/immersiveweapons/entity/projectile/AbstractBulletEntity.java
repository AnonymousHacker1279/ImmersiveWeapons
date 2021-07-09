package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractBulletEntity extends AbstractArrowEntity {

	protected Item referenceItem;
	protected final SoundEvent hitSound = getDefaultHitGroundSoundEvent();
	protected BlockState inBlockState;
	protected IntOpenHashSet piercedEntities;
	protected List<Entity> hitEntities;
	protected int knockbackStrength;
	protected boolean hasAlreadyBrokeGlass = false;
	protected boolean shouldStopMoving = false;
	public static boolean canBreakGlass = Config.BULLETS_BREAK_GLASS.get();

	public AbstractBulletEntity(EntityType<? extends AbstractArrowEntity> entityType, World world) {
		super(entityType, world);
	}

	public AbstractBulletEntity(EntityType<? extends AbstractBulletEntity> type, LivingEntity shooter, World world) {
		super(type, shooter, world);
	}

	@Override
	public @NotNull ItemStack getPickupItem() {
		return new ItemStack(referenceItem);
	}

	@Override
	public @NotNull IPacket<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	protected boolean shouldFall() {
		return inGround && level.noCollision((new AxisAlignedBB(position(), position())).inflate(0.06D));
	}

	protected void startFalling() {
		inGround = false;
		Vector3d vector3d = getDeltaMovement();
		setDeltaMovement(vector3d.multiply(random.nextFloat() * 0.4F, random.nextFloat() * 0.4F, random.nextFloat() * 0.4F));
	}

	@Override
	public void tick() {
		super.tick();
		doWhileTicking();
		boolean flag = isNoPhysics();
		Vector3d vector3d = getDeltaMovement();
		if (xRotO == 0.0F && yRotO == 0.0F) {
			float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
			yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (float) (MathHelper.atan2(vector3d.y, f) * (180F / (float) Math.PI));
			yRotO = yRot;
			xRotO = xRot;
		}

		BlockPos blockpos = blockPosition();
		BlockState blockstate = level.getBlockState(blockpos);
		if (!blockstate.isAir(level, blockpos) && !flag) {
			VoxelShape voxelshape = blockstate.getBlockSupportShape(level, blockpos);
			if (!voxelshape.isEmpty()) {
				Vector3d vector3d1 = position();

				for (AxisAlignedBB axisalignedbb : voxelshape.toAabbs()) {
					if (axisalignedbb.move(blockpos).contains(vector3d1)) {
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
			if (inBlockState != blockstate && shouldFall()) {
				startFalling();
			} else if (!level.isClientSide) {
				tickDespawn();
			}

			++inGroundTime;
		} else {
			inGroundTime = 0;
			Vector3d vector3d2 = position();
			Vector3d vector3d3 = vector3d2.add(vector3d);
			RayTraceResult raytraceresult = level.clip(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
			if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
				vector3d3 = raytraceresult.getLocation();
			}

			while (isAlive()) {
				EntityRayTraceResult entityraytraceresult = findHitEntity(vector3d2, vector3d3);
				if (entityraytraceresult != null) {
					raytraceresult = entityraytraceresult;
				}

				if (raytraceresult != null && raytraceresult.getType() == RayTraceResult.Type.ENTITY) {
					Entity entity = null;
					if (raytraceresult instanceof EntityRayTraceResult) {
						entity = ((EntityRayTraceResult) raytraceresult).getEntity();
					}
					Entity entity1 = getOwner();
					if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity) entity1).canHarmPlayer((PlayerEntity) entity)) {
						raytraceresult = null;
						entityraytraceresult = null;
					}
				}

				if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
					onHit(raytraceresult);
					hasImpulse = true;
				}

				if (entityraytraceresult == null || getPierceLevel() <= 0) {
					break;
				}

				raytraceresult = null;
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
				if (shouldStopMoving) {
					setDeltaMovement(0, 0, 0);
				} else {
					setDeltaMovement(vector3d4.x, vector3d4.y + getMovementModifier(), vector3d4.z);
				}
			}

			setPos(d5, d1, d2);
			checkInsideBlocks();
		}
	}

	public double getMovementModifier() {
		return 0.0d;
	}

	@Override
	protected void onHitEntity(@NotNull EntityRayTraceResult entityRayTraceResult) {
		super.onHitEntity(entityRayTraceResult);
		Entity entity = entityRayTraceResult.getEntity();
		float f = (float) getDeltaMovement().length();
		int i = MathHelper.ceil(MathHelper.clamp(f * baseDamage, 0.0D, 2.147483647E9D));
		if (getPierceLevel() > 0) {
			if (piercedEntities == null) {
				piercedEntities = new IntOpenHashSet(5);
			}

			if (hitEntities == null) {
				hitEntities = Lists.newArrayListWithCapacity(5);
			}

			if (piercedEntities.size() >= getPierceLevel() + 1) {
				remove();
				return;
			}

			piercedEntities.add(entity.getId());
		}

		if (isCritArrow()) {
			long j = random.nextInt(i / 2 + 2);
			i = (int) Math.min(j + i, 2147483647L);
		}

		Entity entity1 = getOwner();
		DamageSource damagesource;
		if (entity1 == null) {
			damagesource = DamageSource.arrow(this, this);
		} else {
			damagesource = DamageSource.arrow(this, entity1);
			if (entity1 instanceof LivingEntity) {
				entity.invulnerableTime = 0;
				entity.setInvulnerable(false);
				doWhenHitEntity(entity);
				((LivingEntity) entity1).setLastHurtMob(entity);
			}
		}

		boolean flag = entity.getType() == EntityType.ENDERMAN;
		int k = entity.getRemainingFireTicks();
		if (isOnFire() && !flag) {
			entity.setSecondsOnFire(5);
		}

		if (entity.hurt(damagesource, i)) {
			if (flag) {
				return;
			}

			if (entity instanceof LivingEntity) {
				LivingEntity livingentity = (LivingEntity) entity;

				if (knockbackStrength > 0) {
					Vector3d vector3d = getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(knockbackStrength * 0.6D);
					if (vector3d.lengthSqr() > 0.0D) {
						livingentity.push(vector3d.x, 0.1D, vector3d.z);
					}
				}

				if (!level.isClientSide && entity1 instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) entity1, livingentity);
				}

				doPostHurtEffects(livingentity);
				if (livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !isSilent()) {
					((ServerPlayerEntity) entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
				}

				if (entity.isAlive() && hitEntities != null) {
					hitEntities.add(livingentity);
				}


			}

			playSound(hitSound, 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
			if (getPierceLevel() <= 0) {
				remove();
			}
		} else {
			entity.setRemainingFireTicks(k);
			if (!level.isClientSide && getDeltaMovement().lengthSqr() < 1.0E-7D) {
				remove();
			}
		}
	}

	@Override
	protected void onHitBlock(BlockRayTraceResult blockStateRayTraceResult) {
		inBlockState = level.getBlockState(blockStateRayTraceResult.getBlockPos());

		if (inBlockState.getBlock().is(BlockTags.bind("forge:leaves"))) {
			push(0, -0.1, 0);
			shakeTime = 4;
		} else {
			Vector3d vector3d = blockStateRayTraceResult.getLocation().subtract(getX(), getY(), getZ());
			setDeltaMovement(vector3d);
			Vector3d vector3d1 = vector3d.normalize().scale(0.05F);
			setPosRaw(getX() - vector3d1.x, getY() - vector3d1.y, getZ() - vector3d1.z);
			inGround = true;
			shakeTime = 2;
			setCritArrow(false);
			setPierceLevel((byte) 0);
			setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			resetPiercedEntities();
		}

		if (canBreakGlass && !hasAlreadyBrokeGlass && !inBlockState.getBlock().is(BlockTags.bind("forge:bulletproof_glass")) && inBlockState.getBlock().is(BlockTags.bind("forge:glass")) || inBlockState.getBlock().is(BlockTags.bind("forge:glass_panes"))) {
			level.destroyBlock(blockStateRayTraceResult.getBlockPos(), false);
			hasAlreadyBrokeGlass = true;
		}
	}

	private void resetPiercedEntities() {
		if (hitEntities != null) {
			hitEntities.clear();
		}

		if (piercedEntities != null) {
			piercedEntities.clear();
		}

	}

	protected void doWhileTicking() {
	}

	protected void doWhenHitEntity(Entity entity) {
	}
}