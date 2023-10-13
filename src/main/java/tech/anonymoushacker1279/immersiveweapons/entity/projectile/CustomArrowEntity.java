package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.SmokeGrenadeEntity.SmokeGrenadeEntityPacketHandler;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.List;

public class CustomArrowEntity extends AbstractArrow implements HitEffectUtils {

	protected static final byte VANILLA_IMPACT_STATUS_ID = 3;
	public Item referenceItem = Items.AIR;
	protected float inertia;
	public double gravityModifier = 1.0d;
	protected boolean shouldStopMoving = false;
	public List<Double> shootingVectorInputs = List.of(0.0075d, -0.0095d, 0.0075d);
	public HitEffect hitEffect = HitEffect.NONE;
	public int color = -1;

	public static final EntityDataAccessor<Float> GRAVITY_MODIFIER_ACCESSOR = SynchedEntityData.defineId(CustomArrowEntity.class,
			EntityDataSerializers.FLOAT);

	public CustomArrowEntity(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	public CustomArrowEntity(EntityType<? extends CustomArrowEntity> type, LivingEntity shooter, Level level) {
		super(type, shooter, level);
	}

	public static class ArrowEntityBuilder implements HitEffectUtils {

		private final EntityType<? extends AbstractArrow> entityType;
		private final Item referenceItem;

		public ArrowEntityBuilder(EntityType<? extends AbstractArrow> entityType, Item referenceItem) {
			this.entityType = entityType;
			this.referenceItem = referenceItem;
		}

		public CustomArrowEntity build(Level level) {
			CustomArrowEntity arrowEntity = new CustomArrowEntity(entityType, level);
			arrowEntity.referenceItem = referenceItem;

			return arrowEntity;
		}
	}

	@Override
	public ItemStack getPickupItem() {
		return new ItemStack(referenceItem);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(GRAVITY_MODIFIER_ACCESSOR, 1.0f);
	}

	@Override
	public void tick() {
		super.tick();

		// Update the gravity modifier on the client via a data accessor
		if (gravityModifier == 1.0d) {
			if (level().isClientSide) {
				gravityModifier = entityData.get(GRAVITY_MODIFIER_ACCESSOR);
			}
		}

		// Extra stuff to do while ticking, for child classes
		doWhileTicking();

		boolean noPhysics = isNoPhysics();
		Vec3 deltaMovement = getDeltaMovement();

		// Rotate the projectile based on movement
		if (xRotO == 0.0F && yRotO == 0.0F) {
			double horizontalDistance = deltaMovement.horizontalDistance();
			setYRot((float) (Mth.atan2(deltaMovement.x, deltaMovement.z) * (180F / (float) Math.PI)));
			setXRot((float) (Mth.atan2(deltaMovement.y, horizontalDistance) * (180F / (float) Math.PI)));
			yRotO = getYRot();
			xRotO = getXRot();
		}

		BlockPos currentBlockPosition = blockPosition();
		BlockState blockState = level().getBlockState(currentBlockPosition);
		// Check if the block at the current position is air, and that it has physics enabled
		if (!blockState.isAir() && !noPhysics) {
			VoxelShape voxelShape = blockState.getCollisionShape(level(), currentBlockPosition);
			if (!voxelShape.isEmpty()) {
				Vec3 vector3d1 = position();

				for (AABB aabb : voxelShape.toAabbs()) {
					if (aabb.move(currentBlockPosition).contains(vector3d1)) {
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

		if (inGround && !noPhysics) {
			// The projectile is in the ground, check if it should start to fall (if a block is broken underneath it) or despawn
			if (lastState != blockState && shouldFall()) {
				startFalling();
			} else if (!level().isClientSide) {
				tickDespawn();
			}

			inGroundTime++;
		} else {
			inGroundTime = 0;
			Vec3 currentPosition = position();
			Vec3 adjustedPosition = currentPosition.add(deltaMovement);
			HitResult hitResult = level().clip(new ClipContext(currentPosition, adjustedPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));

			// Check if the projectile hit a block
			if (hitResult.getType() != HitResult.Type.MISS) {
				adjustedPosition = hitResult.getLocation();
			}

			// Check for hit entities
			while (!isRemoved()) {
				EntityHitResult entityHitResult = findHitEntity(currentPosition, adjustedPosition);
				if (entityHitResult != null) {
					hitResult = entityHitResult;
				}

				if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
					Entity hitEntity = null;
					if (hitResult instanceof EntityHitResult entityHitResult1) {
						hitEntity = entityHitResult1.getEntity();
					}

					// Check if the projectile hit a player, and was fired by a player
					Entity owner = getOwner();
					if (hitEntity instanceof Player && owner instanceof Player player && !player.canHarmPlayer(player)) {
						hitResult = null;
						entityHitResult = null;
					}
				}

				if (hitResult != null && hitResult.getType() != HitResult.Type.MISS && !noPhysics) {
					if (ForgeEventFactory.onProjectileImpact(this, hitResult)) {
						break;
					}

					onHit(hitResult);
					hasImpulse = true;
				}

				if (entityHitResult == null || getPierceLevel() <= 0) {
					break;
				}

				hitResult = null;
			}

			// Get the current movement vector
			deltaMovement = getDeltaMovement();
			double deltaX = deltaMovement.x;
			double deltaY = deltaMovement.y;
			double deltaZ = deltaMovement.z;

			// Add crit particles
			if (isCritArrow()) {
				for (int i = 0; i < 4; ++i) {
					level().addParticle(ParticleTypes.CRIT, getX() + deltaX * i / 4.0D, getY() + deltaY * i / 4.0D, getZ() + deltaZ * i / 4.0D, -deltaX, -deltaY + 0.2D, -deltaZ);
				}
			}

			double newX = getX() + deltaX;
			double newY = getY() + deltaY;
			double newZ = getZ() + deltaZ;
			double horizontalDistance = deltaMovement.horizontalDistance();

			if (noPhysics) {
				setYRot((float) (Mth.atan2(-deltaX, -deltaZ) * (double) (180F / (float) Math.PI)));
			} else {
				setYRot((float) (Mth.atan2(deltaX, deltaZ) * (double) (180F / (float) Math.PI)));
			}

			setXRot((float) (Mth.atan2(-deltaY, horizontalDistance) * (double) (180F / (float) Math.PI)));
			setXRot(lerpRotation(xRotO, getXRot()));
			setYRot(lerpRotation(yRotO, getYRot()));

			// Check if the projectile is in water
			inertia = getDefaultInertia();
			if (isInWater()) {
				for (int i = 0; i < 4; ++i) {
					level().addParticle(ParticleTypes.BUBBLE,
							newX - deltaX * 0.25D,
							newY - deltaY * 0.25D,
							newZ - deltaZ * 0.25D,
							deltaX, deltaY, deltaZ);
				}

				inertia = getWaterInertia();
			}

			// Set movement and position
			setDeltaMovement(deltaMovement.scale(inertia));
			if (!isNoGravity() && !noPhysics) {
				if (shouldStopMoving) {
					setDeltaMovement(0, 0, 0);
				} else {
					Vec3 newDeltaMovement = getDeltaMovement();
					setDeltaMovement(newDeltaMovement.x, newDeltaMovement.y - getGravityModifier(), newDeltaMovement.z);
				}
			}

			setPos(newX, newY, newZ);
			checkInsideBlocks();
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);

		if (color != -1 && !level().isClientSide) {
			PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level().getChunkAt(blockPosition())),
					new SmokeGrenadeEntityPacketHandler(getX(), getY(), getZ(), color));
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);

		// Extra code to run when an entity is hit
		doWhenHitEntity(result.getEntity());

		if (result.getEntity() instanceof LivingEntity livingEntity) {
			// Apply any hit effects from the bullet
			switch (hitEffect) {
				case MOLTEN -> addMoltenEffects(livingEntity);
				case TESLA -> addTeslaEffects(livingEntity);
				case VENTUS -> addVentusEffects(livingEntity);
			}
		}
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		Vec3 shootingVector = new Vec3(x, y, z)
				.normalize()
				.add(random.triangle(0d,
								shootingVectorInputs.get(0) * inaccuracy
										* GeneralUtilities.getRandomNumber(shootingVectorInputs.get(1), shootingVectorInputs.get(2))),
						random.triangle(0d,
								shootingVectorInputs.get(0) * inaccuracy
										* GeneralUtilities.getRandomNumber(shootingVectorInputs.get(1), shootingVectorInputs.get(2))),
						random.triangle(0d,
								shootingVectorInputs.get(0) * inaccuracy
										* GeneralUtilities.getRandomNumber(shootingVectorInputs.get(1), shootingVectorInputs.get(2))))
				.scale(velocity);

		setDeltaMovement(shootingVector);
		double horizontalDistanceSqr = shootingVector.horizontalDistanceSqr();
		setYRot((float) (Mth.atan2(shootingVector.x, shootingVector.z) * (180F / (float) Math.PI)));
		setXRot((float) (Mth.atan2(shootingVector.y, horizontalDistanceSqr) * (180F / (float) Math.PI)));
		yRotO = getYRot();
		xRotO = getXRot();
	}

	protected float getDefaultInertia() {
		return 0.99f;
	}

	protected double getGravityModifier() {
		return gravityModifier;
	}

	/**
	 * Additional stuff to do while ticking. Runs early in the tick method.
	 */
	protected void doWhileTicking() {
	}

	/**
	 * Additional stuff to do when an entity is hit.
	 *
	 * @param entity the <code>Entity</code> being hit
	 */
	protected void doWhenHitEntity(Entity entity) {
		level().broadcastEntityEvent(this, VANILLA_IMPACT_STATUS_ID);
	}

	/**
	 * Additional stuff to do when a block is hit.
	 */
	protected void doWhenHitBlock() {
	}
}