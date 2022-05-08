package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact.BulletImpactParticleOptions;
import com.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import com.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeBlockTagGroups;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.item.projectile.gun.MusketItem;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class BulletEntity extends AbstractArrow {

	private static final boolean canBreakGlass = CommonConfig.BULLETS_BREAK_GLASS.get();
	private final SoundEvent hitSound = getDefaultHitGroundSoundEvent();
	private static final byte VANILLA_IMPACT_STATUS_ID = 3;
	Item referenceItem;
	int knockbackStrength;
	boolean shouldStopMoving = false;
	private BlockState inBlockState;
	private IntOpenHashSet piercedEntities;
	private boolean hasAlreadyBrokeGlass = false;
	private Item firingItem = DeferredRegistryHandler.FLINTLOCK_PISTOL.get();

	/**
	 * Constructor for AbstractBulletEntity.
	 *
	 * @param entityType the <code>EntityType</code> instance
	 * @param level      the <code>Level</code> the entity is in
	 */
	public BulletEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
		super(entityType, level);
	}

	/**
	 * Constructor for BulletEntity.
	 *
	 * @param entityType the <code>EntityType</code> instance
	 * @param shooter    the <code>LivingEntity</code> shooting the entity
	 * @param level      the <code>Level</code> the entity is in
	 */
	public BulletEntity(EntityType<? extends BulletEntity> entityType, LivingEntity shooter, Level level) {
		super(entityType, shooter, level);
	}

	/**
	 * Get the pickup item.
	 *
	 * @return ItemStack
	 */
	@Override
	public @NotNull ItemStack getPickupItem() {
		return new ItemStack(referenceItem);
	}

	/**
	 * Get the entity spawn packet.
	 *
	 * @return Packet
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
		if (!hasBeenShot) {
			gameEvent(GameEvent.PROJECTILE_SHOOT, getOwner(), blockPosition());
			hasBeenShot = true;
		}

		if (!leftOwner) {
			leftOwner = checkLeftOwner();
		}

		baseTick();

		// Run extra stuff while ticking, useful for classes extending this class
		// but not needing to overwrite the entire tick function.
		doWhileTicking();

		boolean isNoPhysics = isNoPhysics();
		Vec3 deltaMovement = getDeltaMovement();

		double yRotation;
		double xRotation;
		// Make the bullet rotate
		if (xRotO == 0.0F && yRotO == 0.0F && !inGround) {
			double horizontalDistanceSquareRoot = deltaMovement.horizontalDistanceSqr();
			yRotation = (Mth.atan2(deltaMovement.x, deltaMovement.z) * (180F / (float) Math.PI));
			xRotation = (Mth.atan2(deltaMovement.y, horizontalDistanceSquareRoot) * (180F / (float) Math.PI));
			yRotO = (float) yRotation;
			xRotO = (float) xRotation;
		}

		BlockPos currentBlockPosition = blockPosition();
		BlockState blockStateAtCurrentPosition = level.getBlockState(currentBlockPosition);
		// Check if the block at the current position is air, and that it has physics enabled
		if (!blockStateAtCurrentPosition.isAir() && !isNoPhysics) {
			VoxelShape currentPositionBlockSupportShape = blockStateAtCurrentPosition.getBlockSupportShape(level,
					currentBlockPosition);

			// Check the hitboxes first, if this isn't an air block
			if (!currentPositionBlockSupportShape.isEmpty()) {
				Vec3 position = position();

				for (AABB aabb : currentPositionBlockSupportShape.toAabbs()) {
					// Check if the bullet reached the hitbox of the non-air block
					if (aabb.move(currentBlockPosition).contains(position)) {
						inGround = true;
						break;
					}
				}
			}
		}

		// If the shake time is above zero, tick it down
		if (shakeTime > 0) {
			shakeTime--;
		}

		// If the bullet is in water or rain, clear any fire
		if (isInWaterOrRain()) {
			clearFire();
		}

		// Check if the bullet is in the ground, and if it has physics enabled
		if (inGround && !isNoPhysics) {
			/* If the current blockstate is not the same as the previous one, and it should start falling,
			 begin the fall process. This runs when the block the bullet is under is broken. Otherwise,
			 tick for despawn. */
			if (inBlockState != blockStateAtCurrentPosition && shouldFall()) {
				startFalling();
			} else if (!level.isClientSide) {
				tickDespawn();
			}

			inGroundTime++;
		} else {
			// At this point, the bullet is still in the air
			inGroundTime = 0;
			Vec3 currentPosition = position();
			Vec3 newPosition = currentPosition.add(deltaMovement);
			HitResult hitResult = level.clip(new ClipContext(currentPosition, newPosition, ClipContext.Block.COLLIDER, Fluid.NONE,
					this));

			// If there's a block between the current position and the new one, set the
			// location to the hit location of the clip (includes hitboxes).
			if (hitResult.getType() != Type.MISS) {
				newPosition = hitResult.getLocation();
			}

			// Check for hit entities
			EntityHitResult entityHitResult = findHitEntity(currentPosition, newPosition);
			if (entityHitResult != null) {
				hitResult = entityHitResult;
			}

			if (hitResult.getType() == Type.ENTITY) {
				Entity entity = null;
				// Get the entity being hit
				if (hitResult instanceof EntityHitResult) {
					entity = ((EntityHitResult) hitResult).getEntity();
				}
				// Get the owner of the bullet
				Entity owner = getOwner();
				// Check if the entity is a player, and if so, if they are allowed
				// to be harmed by the owner
				if (entity instanceof Player && owner instanceof Player
						&& !((Player) owner).canHarmPlayer((Player) entity)) {

					hitResult = null;
				}
			}

			// If something was hit, and physics are enabled, execute necessary code
			if (hitResult != null && hitResult.getType() != Type.MISS && !isNoPhysics
					&& !ForgeEventFactory.onProjectileImpact(this, hitResult)) {

				onHit(hitResult);
				hasImpulse = true;
			}

			// Get the current delta movement values
			deltaMovement = getDeltaMovement();
			double deltaMovementX = deltaMovement.x;
			double deltaMovementY = deltaMovement.y;
			double deltaMovementZ = deltaMovement.z;
			// Add particles behind the bullet if it is a "critical" one
			if (isCritArrow()) {
				for (int i = 0; i < 4; ++i) {
					level.addParticle(ParticleTypes.CRIT,
							getX() + deltaMovementX * i / 4.0D,
							getY() + deltaMovementY * i / 4.0D,
							getZ() + deltaMovementZ * i / 4.0D,
							-deltaMovementX,
							-deltaMovementY + 0.2D,
							-deltaMovementZ);
				}
			}

			double newPositionX = getX() + deltaMovementX;
			double newPositionY = getY() + deltaMovementY;
			double newPositionZ = getZ() + deltaMovementZ;
			double horizontalDistance = deltaMovement.horizontalDistance();

			if (isNoPhysics) {
				setYRot((float) (Mth.atan2(-deltaMovementX, -deltaMovementZ) * (double) (180F / (float) Math.PI)));
			} else {
				setYRot((float) (Mth.atan2(deltaMovementX, deltaMovementZ) * (double) (180F / (float) Math.PI)));
			}

			setXRot((float) (Mth.atan2(deltaMovementY, horizontalDistance) * (double) (180F / (float) Math.PI)));
			setXRot(lerpRotation(xRotO, getXRot()));
			setYRot(lerpRotation(yRotO, getYRot()));

			float inertia = 0.99F;
			// Check if the bullet is in water
			if (isInWater()) {
				for (int j = 0; j < 4; ++j) {
					level.addParticle(ParticleTypes.BUBBLE,
							newPositionX - deltaMovementX * 0.25D,
							newPositionY - deltaMovementY * 0.25D,
							newPositionZ - deltaMovementZ * 0.25D,
							deltaMovementX, deltaMovementY, deltaMovementZ);
				}

				inertia = getWaterInertia();
			}

			setDeltaMovement(deltaMovement.scale(inertia));

			// Set movement and position
			if (!isNoGravity() && !isNoPhysics) {
				Vec3 deltaMovement1 = getDeltaMovement();
				if (shouldStopMoving) {
					setDeltaMovement(0, 0, 0);
				} else {
					double gravityModifier = firingItem instanceof MusketItem ? getGravityModifier() / 4 : getGravityModifier();
					setDeltaMovement(deltaMovement1.x, deltaMovement1.y - gravityModifier, deltaMovement1.z);
				}
			}

			setPos(newPositionX, newPositionY, newPositionZ);
			checkInsideBlocks();
		}
	}

	/**
	 * Get the movement modifier.
	 *
	 * @return double
	 */
	public double getGravityModifier() {
		return 1.0d;
	}

	public void setFiringItem(Item stack) {
		firingItem = stack;
	}

	/**
	 * Runs when an entity is hit.
	 *
	 * @param entityRayTraceResult the <code>EntityRayTraceResult</code> instance
	 */
	@Override
	protected void onHitEntity(@NotNull EntityHitResult entityRayTraceResult) {
		Entity entity = entityRayTraceResult.getEntity();
		float velocityModifier = (float) getDeltaMovement().length();
		// Determine the damage to be dealt, which is calculated by multiplying the velocity modifier
		// and the base damage. It's clamped if the velocity is extremely high.
		int damage = Mth.ceil(Mth.clamp(velocityModifier * baseDamage, 0.0D, 2.147483647E9D));

		int pierceLevel = getPierceLevel();

		// Extra code to run when an entity is hit
		doWhenHitEntity(entity, entityRayTraceResult);

		// Check the piercing level, if its above zero then start piercing entities
		if (pierceLevel > 0) {
			if (piercedEntities == null) {
				piercedEntities = new IntOpenHashSet(5);
			}

			// If we've pierced the maximum number of entities,
			// destroy the bullet
			if (piercedEntities.size() >= pierceLevel + 1) {
				kill();
				return;
			}

			piercedEntities.add(entity.getId());
		}

		// Add crit modifier if the bullet is critical
		if (isCritArrow()) {
			long randomCritModifier = random.nextInt(damage / 2 + 2);
			damage = (int) Math.min(randomCritModifier + damage, 2147483647L);
		}

		Entity owner = getOwner();
		DamageSource damageSource;

		// If the arrow owner doesn't exist (null), set the indirect entity to itself
		if (owner == null) {
			damageSource = DamageSource.arrow(this, this);
		} else {
			damageSource = DamageSource.arrow(this, owner);

			// Disable invulnerability for bullets; specifically with the blunderbuss, otherwise
			// multiple shots on the same target will simply bounce back
			entity.invulnerableTime = 0;
			entity.setInvulnerable(false);

			if (owner instanceof LivingEntity) {
				((LivingEntity) owner).setLastHurtMob(entity);
			}
		}

		boolean isEnderman = entity.getType() == EntityType.ENDERMAN;
		int remainingFireTicks = entity.getRemainingFireTicks();

		// Set the entity on fire if the bullet is on fire, except
		// for when the entity is an enderman
		if (isOnFire() && !isEnderman) {
			entity.setSecondsOnFire(5);
		}

		if (entity.hurt(damageSource, damage)) {
			if (isEnderman) {
				return;
			}

			if (entity instanceof LivingEntity livingEntity) {

				// Apply knockback if the strength is above zero
				if (knockbackStrength > 0) {
					Vec3 scaledDeltaMovement = getDeltaMovement()
							.multiply(1.0D, 0.0D, 1.0D)
							.normalize()
							.scale(knockbackStrength * 0.6D);

					if (scaledDeltaMovement.lengthSqr() > 0.0D) {
						livingEntity.push(scaledDeltaMovement.x, 0.1D, scaledDeltaMovement.z);
					}
				}

				if (!level.isClientSide && owner instanceof LivingEntity) {
					EnchantmentHelper.doPostHurtEffects(livingEntity, owner);
					EnchantmentHelper.doPostDamageEffects((LivingEntity) owner, livingEntity);
				}

				// Code to run after the entity is hurt
				doPostHurtEffects(livingEntity);
			}

			if (pierceLevel <= 0) {
				kill();
			}
		} else {
			entity.setRemainingFireTicks(remainingFireTicks);

			if (!level.isClientSide && getDeltaMovement().lengthSqr() < 1.0E-7D) {
				kill();
			}
		}
	}

	/**
	 * Runs when a block is hit.
	 *
	 * @param blockHitResult the <code>BlockHitResult</code> instance
	 */
	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		inBlockState = level.getBlockState(blockHitResult.getBlockPos());
		boolean didPassThroughBlock = false;

		// Check if the bullet hit a permeable block like leaves, if so
		// keep moving and decrease velocity
		if (inBlockState.is(BlockTags.LEAVES)) {
			push(0, -0.1, 0);
			shakeTime = 4;
			didPassThroughBlock = true;
		} else {
			Vec3 locationMinusCurrentPosition = blockHitResult.getLocation().subtract(getX(), getY(), getZ());
			setDeltaMovement(locationMinusCurrentPosition);
			Vec3 scaledPosition = locationMinusCurrentPosition.normalize().scale(0.0025F);
			setPosRaw(getX() - scaledPosition.x, getY() - scaledPosition.y, getZ() - scaledPosition.z);
			playSound(DeferredRegistryHandler.BULLET_WHIZZ.get(), 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
			inGround = true;
			shakeTime = 2;
			setCritArrow(false);
			setPierceLevel((byte) 0);
			setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
			resetPiercedEntities();
		}

		// Check if glass can be broken, and if it hasn't already broken glass
		if (canBreakGlass && !hasAlreadyBrokeGlass
				&& !inBlockState.is(ForgeBlockTagGroups.BULLETPROOF_GLASS)
				&& inBlockState.is(Blocks.GLASS)
				|| inBlockState.is(Blocks.GLASS_PANES)) {

			level.destroyBlock(blockHitResult.getBlockPos(), false);
			hasAlreadyBrokeGlass = true;
		}

		inBlockState.onProjectileHit(level, inBlockState, blockHitResult, this);

		if (!didPassThroughBlock && level.isClientSide) {
			level.addParticle(new BulletImpactParticleOptions(1.0F, Block.getId(inBlockState)),
					blockHitResult.getLocation().x, blockHitResult.getLocation().y, blockHitResult.getLocation().z,
					GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
					GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
					GeneralUtilities.getRandomNumber(-0.01d, 0.01d));
		}
	}

	private boolean checkLeftOwner() {
		Entity owner = getOwner();
		if (owner != null) {
			for (Entity entityInWorld : level.getEntities(this, getBoundingBox()
							.expandTowards(getDeltaMovement()).inflate(1.0D),
					(entity) -> !entity.isSpectator() && entity.isPickable())) {

				if (entityInWorld.getRootVehicle() == owner.getRootVehicle()) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Handle entity events.
	 *
	 * @param statusID the <code>byte</code> containing status ID
	 */
	@Override
	public void handleEntityEvent(byte statusID) {
		if (statusID == VANILLA_IMPACT_STATUS_ID) {
			for (int i = 0; i <= 16; i++) {
				level.addParticle(DeferredRegistryHandler.BLOOD_PARTICLE.get(),
						position().x, position().y, position().z,
						GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
						GeneralUtilities.getRandomNumber(-0.03d, 0.03d),
						GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
			}
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
	 *
	 * @param entity the <code>Entity</code> being hit
	 */
	protected void doWhenHitEntity(Entity entity, EntityHitResult entityHitResult) {
		level.broadcastEntityEvent(this, VANILLA_IMPACT_STATUS_ID);
	}
}