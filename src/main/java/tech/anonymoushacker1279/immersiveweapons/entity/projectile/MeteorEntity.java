package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class MeteorEntity extends Projectile {

	@Nullable
	private static DamageSource damageSource = null;
	private BlockPos startPos = BlockPos.ZERO;
	private BlockPos targetPos = BlockPos.ZERO;
	private double distToTarget = 0;

	public MeteorEntity(EntityType<? extends Projectile> entityType, Level level) {
		super(entityType, level);
	}

	public static void create(Level level, LivingEntity owner, BlockPos targetPos) {
		if (!level.isClientSide) {
			MeteorEntity meteorEntity = new MeteorEntity(EntityRegistry.METEOR_ENTITY.get(), level);

			meteorEntity.setOwner(owner);
			damageSource = new EntityDamageSource("immersiveweapons.meteor", owner).setExplosion();

			meteorEntity.targetPos = targetPos;

			// Determine a starting position 40 blocks above the target position, and within a 15 block radius
			meteorEntity.startPos = new BlockPos(
					targetPos.getX() + GeneralUtilities.getRandomNumber(-15, 15),
					targetPos.getY() + 40,
					targetPos.getZ() + GeneralUtilities.getRandomNumber(-15, 15)
			);
			meteorEntity.setPos(meteorEntity.startPos.getCenter());

			// Move towards the target position
			Vec3 blockCenter = targetPos.getCenter();
			Vec3 entityPos = meteorEntity.position();
			Vec3 direction = blockCenter.subtract(entityPos).normalize();

			// Set the distance to the target
			meteorEntity.distToTarget = blockCenter.distanceTo(entityPos);

			meteorEntity.setDeltaMovement(direction);

			// Spawn the entity
			level.addFreshEntity(meteorEntity);
		}
	}

	@Override
	public void tick() {
		super.tick();

		Vec3 deltaMovement = getDeltaMovement();

		// Check for collisions
		Vec3 currentPosition = position();
		Vec3 currentPositionPlusMovement = currentPosition.add(deltaMovement);
		HitResult rayTraceResult = level.clip(new ClipContext(currentPosition, currentPositionPlusMovement, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		if (rayTraceResult.getType() != HitResult.Type.MISS) {
			currentPositionPlusMovement = rayTraceResult.getLocation();
		}

		while (isAlive()) {
			EntityHitResult entityRayTraceResult = ProjectileUtil.getEntityHitResult(level, this, currentPosition, currentPositionPlusMovement, getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0D), this::canHitEntity);
			if (entityRayTraceResult != null) {
				rayTraceResult = entityRayTraceResult;
			}

			if (rayTraceResult != null && rayTraceResult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, rayTraceResult)) {
				onHit(rayTraceResult);
				hasImpulse = true;
			}

			if (entityRayTraceResult == null) {
				break;
			}

			rayTraceResult = null;
		}

		// Handle movement
		if (!level.isClientSide) {
			lookAt(Anchor.EYES, targetPos.getCenter());

			// Calculate the speed of the meteor
			// First, calculate the linear distance using the Pythagorean theorem
			double xDist = Math.abs(targetPos.getX() - getX());
			double zDist = Math.abs(targetPos.getZ() - getZ());
			double linearDist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(zDist, 2));

			double speed = linearDist / distToTarget;

			// If the Y speed is greater than 3, spawn flame particles
			if (speed > 0.1f) {
				((ServerLevel) level).sendParticles(
						ParticleTypes.FLAME,
						getX(),
						getY(),
						getZ(),
						6,
						0,
						0,
						0,
						0.15);
			}

			// Spawn smoke particles
			((ServerLevel) level).sendParticles(
					ParticleTypes.SMOKE,
					getX(),
					getY(),
					getZ(),
					6,
					0,
					0,
					0,
					0.05);
		}

		double deltaMovementXPlusPosition = getX() + deltaMovement.x();
		double deltaMovementYPlusPosition = getY() + deltaMovement.y();
		double deltaMovementZPlusPosition = getZ() + deltaMovement.z();

		setDeltaMovement(deltaMovement.scale(1.125f));
		setPos(deltaMovementXPlusPosition, deltaMovementYPlusPosition, deltaMovementZPlusPosition);
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);

		// Explode on impact

		float explosionRadius = CommonConfig.METEOR_STAFF_EXPLOSION_RADIUS.get().floatValue();
		boolean breakBlocks = CommonConfig.METEOR_STAFF_EXPLOSION_BREAK_BLOCKS.get();
		ExplosionInteraction explosionInteraction = breakBlocks ? ExplosionInteraction.MOB : ExplosionInteraction.NONE;

		level.explode(this, damageSource, null, position().subtract(0, 1.5, 0), explosionRadius, false, explosionInteraction);

		// Spawn a ring of fire particles
		if (!level.isClientSide) {
			for (int i = 0; i < 360; i += 10) {
				double x = Math.cos(i) * 6;
				double z = Math.sin(i) * 6;

				((ServerLevel) level).sendParticles(
						ParticleTypes.FLAME,
						getX() + x,
						getY(),
						getZ() + z,
						3,
						0,
						0,
						0,
						0.15);
			}
		}

		kill();
	}

	@Override
	protected void defineSynchedData() {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		// Load the target position
		targetPos = new BlockPos(nbt.getInt("targetX"), nbt.getInt("targetY"), nbt.getInt("targetZ"));
		// Load the starting position
		startPos = new BlockPos(nbt.getInt("startX"), nbt.getInt("startY"), nbt.getInt("startZ"));
		// Load the distance to the target
		distToTarget = nbt.getDouble("distToTarget");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		// Save the target position
		nbt.putInt("targetX", targetPos.getX());
		nbt.putInt("targetY", targetPos.getY());
		nbt.putInt("targetZ", targetPos.getZ());
		// Save the starting position
		nbt.putInt("startX", startPos.getX());
		nbt.putInt("startY", startPos.getY());
		nbt.putInt("startZ", startPos.getZ());
		// Save the distance to the target
		nbt.putDouble("distToTarget", distToTarget);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}
}