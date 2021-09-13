package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBulletEntity extends AbstractArrow {

	Item referenceItem;
	private final SoundEvent hitSound = getDefaultHitGroundSoundEvent();
	private BlockState inBlockState;
	private IntOpenHashSet piercedEntities;
	int knockbackStrength;
	private boolean hasAlreadyBrokeGlass = false;
	boolean shouldStopMoving = false;
	private static final boolean canBreakGlass = Config.BULLETS_BREAK_GLASS.get();

	/**
	 * Constructor for AbstractBulletEntity.
	 * @param entityType the <code>EntityType</code> instance
	 * @param world the <code>World</code> the entity is in
	 */
	AbstractBulletEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
		super(entityType, world);
	}

	/**
	 * Constructor for AbstractBulletEntity.
	 * @param entityType the <code>EntityType</code> instance
	 * @param shooter the <code>LivingEntity</code> shooting the entity
	 * @param world the <code>World</code> the entity is in
	 */
	AbstractBulletEntity(EntityType<? extends AbstractBulletEntity> entityType, LivingEntity shooter, Level world) {
		super(entityType, shooter, world);
	}

	/**
	 * Get the pickup item.
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack getPickupItem() {
		return new ItemStack(referenceItem);
	}

	/**
	 * Get the entity spawn packet.
	 * @return IPacket
	 */
	@Override
	public @NotNull Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	/**
	 * Runs once each tick.
	 */
	@Override
	public void tick() {
		super.tick();
		doWhileTicking();
		boolean flag = isNoPhysics();
		Vec3 vector3d = getDeltaMovement();
		double yRot;
		double xRot;
		if (xRotO == 0.0F && yRotO == 0.0F) {
			double d = vector3d.horizontalDistanceSqr();
			yRot = (Mth.atan2(vector3d.x, vector3d.z) * (180F / (float) Math.PI));
			xRot = (Mth.atan2(vector3d.y, d) * (180F / (float) Math.PI));
			yRotO = (float) yRot;
			xRotO = (float) xRot;
		}

		BlockPos blockpos = blockPosition();
		BlockState blockstate = level.getBlockState(blockpos);
		if (!blockstate.isAir() && !flag) {
			VoxelShape voxelshape = blockstate.getBlockSupportShape(level, blockpos);
			if (!voxelshape.isEmpty()) {
				Vec3 vector3d1 = position();

				for (AABB aabb : voxelshape.toAabbs()) {
					if (aabb.move(blockpos).contains(vector3d1)) {
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
			Vec3 vector3d2 = position();
			Vec3 vector3d3 = vector3d2.add(vector3d);
			HitResult hitResult = level.clip(new ClipContext(vector3d2, vector3d3, Block.COLLIDER, Fluid.NONE, this));
			if (hitResult.getType() != Type.MISS) {
				vector3d3 = hitResult.getLocation();
			}

			while (isAlive()) {
				EntityHitResult entityHitResult = findHitEntity(vector3d2, vector3d3);
				if (entityHitResult != null) {
					hitResult = entityHitResult;
				}

				if (hitResult != null && hitResult.getType() == Type.ENTITY) {
					Entity entity = null;
					if (hitResult instanceof EntityHitResult) {
						entity = ((EntityHitResult) hitResult).getEntity();
					}
					Entity entity1 = getOwner();
					if (entity instanceof Player && entity1 instanceof Player && !((Player) entity1).canHarmPlayer((Player) entity)) {
						hitResult = null;
						entityHitResult = null;
					}
				}

				if (hitResult != null && hitResult.getType() != Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, hitResult)) {
					onHit(hitResult);
					hasImpulse = true;
				}

				if (entityHitResult == null || getPierceLevel() <= 0) {
					break;
				}

				hitResult = null;
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

	/**
	 * Get the movement modifier.
	 * @return double
	 */
	public double getMovementModifier() {
		return 0.0d;
	}

	/**
	 * Runs when an entity is hit.
	 * @param entityRayTraceResult the <code>EntityRayTraceResult</code> instance
	 */
	@Override
	protected void onHitEntity(@NotNull EntityHitResult entityRayTraceResult) {
		super.onHitEntity(entityRayTraceResult);
		Entity entity = entityRayTraceResult.getEntity();
		float f = (float) getDeltaMovement().length();
		int i = Mth.ceil(Mth.clamp(f * baseDamage, 0.0D, 2.147483647E9D));
		if (getPierceLevel() > 0) {
			if (piercedEntities == null) {
				piercedEntities = new IntOpenHashSet(5);
			}

			if (piercedEntities.size() >= getPierceLevel() + 1) {
				kill();
				return;
			}

			piercedEntities.add(entity.getId());
		}

		if (isCritArrow()) {
			long j = random.nextInt(i / 2 + 2);
			i = (int) Math.min(j + i, 2147483647L);
		}

		Entity owner = getOwner();
		DamageSource damagesource;
		if (owner == null) {
			damagesource = DamageSource.arrow(this, this);
		} else {
			damagesource = DamageSource.arrow(this, owner);
			if (owner instanceof LivingEntity) {
				entity.invulnerableTime = 0;
				entity.setInvulnerable(false);
				doWhenHitEntity(entity);
				((LivingEntity) owner).setLastHurtMob(entity);
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

			if (entity instanceof LivingEntity livingEntity) {

				if (knockbackStrength > 0) {
					Vec3 vector3d = getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(knockbackStrength * 0.6D);
					if (vector3d.lengthSqr() > 0.0D) {
						livingEntity.push(vector3d.x, 0.1D, vector3d.z);
					}
				}

				if (!level.isClientSide && owner instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingEntity, owner);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) owner, livingEntity);
				}

				doPostHurtEffects(livingEntity);
				if (livingEntity != owner && livingEntity instanceof Player && owner instanceof ServerPlayer && !isSilent()) {
					((ServerPlayer) owner).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
				}
			}

			playSound(hitSound, 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
			if (getPierceLevel() <= 0) {
				kill();
			}
		} else {
			entity.setRemainingFireTicks(k);
			if (!level.isClientSide && getDeltaMovement().lengthSqr() < 1.0E-7D) {
				kill();
			}
		}
	}

	/**
	 * Runs when a block is hit.
	 * @param blockHitResult the <code>BlockHitResult</code> instance
	 */
	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		inBlockState = level.getBlockState(blockHitResult.getBlockPos());

		if (inBlockState.is(BlockTags.bind("minecraft:leaves"))) {
			push(0, -0.1, 0);
			shakeTime = 4;
		} else {
			Vec3 vector3d = blockHitResult.getLocation().subtract(getX(), getY(), getZ());
			setDeltaMovement(vector3d);
			Vec3 vector3d1 = vector3d.normalize().scale(0.05F);
			setPosRaw(getX() - vector3d1.x, getY() - vector3d1.y, getZ() - vector3d1.z);
			inGround = true;
			shakeTime = 2;
			setCritArrow(false);
			setPierceLevel((byte) 0);
			setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			resetPiercedEntities();
		}

		if (canBreakGlass && !hasAlreadyBrokeGlass && !inBlockState.is(BlockTags.bind("forge:bulletproof_glass")) && inBlockState.is(BlockTags.bind("forge:glass")) || inBlockState.is(BlockTags.bind("forge:glass_panes"))) {
			level.destroyBlock(blockHitResult.getBlockPos(), false);
			hasAlreadyBrokeGlass = true;
		}
	}

	/**
	 * Reset the pierced entities list.
	 */
	private void resetPiercedEntities() {
		if (piercedEntities != null) {
			piercedEntities.clear();
		}
	}

	/**
	 * Additional stuff to do while ticking.
	 */
	protected void doWhileTicking() {
	}

	/**
	 * Additional stuff to do when an entity is hit.
	 * @param entity the <code>Entity</code> being hit
	 */
	protected void doWhenHitEntity(Entity entity) {
	}
}