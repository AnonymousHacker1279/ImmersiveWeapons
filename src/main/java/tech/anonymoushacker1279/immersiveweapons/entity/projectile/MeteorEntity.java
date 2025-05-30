package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class MeteorEntity extends Projectile {

	private BlockPos startPos = BlockPos.ZERO;
	private BlockPos targetPos = BlockPos.ZERO;
	private double distToTarget = 0;
	private float explosionRadiusModifier = 0.0f;
	private boolean catchFire = false;

	public MeteorEntity(EntityType<? extends Projectile> entityType, Level level) {
		super(entityType, level);
	}

	/**
	 * Create a new meteor at the specified position.
	 *
	 * @param level        the <code>Level</code> the meteor is in
	 * @param owner        the <code>LivingEntity</code> owner
	 * @param staff        the <code>ItemStack</code> staff. May be null if not summoned by a staff.
	 * @param targetPos    the <code>BlockPos</code> target position
	 * @param targetEntity the <code>LivingEntity</code> target entity. May be null. If not, the meteor will only damage
	 *                     the specified target.
	 * @return true if the meteor was successfully created, false otherwise
	 */
	public static boolean create(Level level, LivingEntity owner, @Nullable ItemStack staff, BlockPos targetPos, @Nullable LivingEntity targetEntity) {
		if (!level.isClientSide) {
			MeteorEntity meteorEntity = new MeteorEntity(EntityRegistry.METEOR_ENTITY.get(), level);

			// Determine a starting position 40 blocks above the target position, and within a 15 block radius
			meteorEntity.startPos = new BlockPos(
					targetPos.getX() + level.getRandom().nextIntBetweenInclusive(-15, 15),
					targetPos.getY() + 40,
					targetPos.getZ() + level.getRandom().nextIntBetweenInclusive(-15, 15)
			);

			// Check if the position is inside a solid block
			if (level.getBlockState(meteorEntity.startPos).isSolid()) {
				return false;
			}

			// Set the meteor's position to the starting position and set the target position
			meteorEntity.setPos(meteorEntity.startPos.getCenter());
			meteorEntity.targetPos = targetPos;

			// Set the owner and damage source
			meteorEntity.setOwner(owner);

			// Handle any enchantments
			if (staff != null) {
				HolderGetter<Enchantment> enchantmentGetter = meteorEntity.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();

				int enchantLevel = staff.getEnchantmentLevel(enchantmentGetter.getOrThrow(IWEnchantments.HEAVY_COMET));
				if (enchantLevel > 0) {
					// Increase the explosion radius, capping at +2 blocks
					meteorEntity.explosionRadiusModifier = (float) Math.min(enchantLevel * 0.5, 2);
				}

				enchantLevel = staff.getEnchantmentLevel(enchantmentGetter.getOrThrow(IWEnchantments.BURNING_HEAT));
				if (enchantLevel > 0) {
					meteorEntity.catchFire = true;
				}
			}

			// Move towards the target position
			Vec3 blockCenter = targetPos.getCenter();
			Vec3 entityPos = meteorEntity.position();
			Vec3 direction = blockCenter.subtract(entityPos).normalize();

			// Set the distance to the target
			meteorEntity.distToTarget = blockCenter.distanceTo(entityPos);

			meteorEntity.setDeltaMovement(direction);

			if (targetEntity != null) {
				meteorEntity.getPersistentData().store("targetEntityUUID", UUIDUtil.CODEC, targetEntity.getUUID());
			}

			// Spawn the entity
			level.addFreshEntity(meteorEntity);
		}
		return true;
	}

	@Override
	public void tick() {
		super.tick();

		Vec3 deltaMovement = getDeltaMovement();

		// Check for collisions
		Vec3 currentPosition = position();
		Vec3 currentPositionPlusMovement = currentPosition.add(deltaMovement);
		HitResult rayTraceResult = level().clip(new ClipContext(currentPosition, currentPositionPlusMovement, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
		if (rayTraceResult.getType() != HitResult.Type.MISS) {
			currentPositionPlusMovement = rayTraceResult.getLocation();
		}

		while (isAlive()) {
			EntityHitResult entityRayTraceResult = ProjectileUtil.getEntityHitResult(level(), this, currentPosition, currentPositionPlusMovement, getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0D), this::canHitEntity);
			if (entityRayTraceResult != null) {
				rayTraceResult = entityRayTraceResult;
			}

			if (rayTraceResult != null && rayTraceResult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, rayTraceResult)) {
				onHit(rayTraceResult);
				hasImpulse = true;
			}

			if (entityRayTraceResult == null) {
				break;
			}

			rayTraceResult = null;
		}

		// Handle movement
		if (!level().isClientSide) {
			lookAt(Anchor.EYES, targetPos.getCenter());

			// Calculate the speed of the meteor
			// First, calculate the linear distance using the Pythagorean theorem
			double xDist = Math.abs(targetPos.getX() - getX());
			double zDist = Math.abs(targetPos.getZ() - getZ());
			double linearDist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(zDist, 2));

			double speed = linearDist / distToTarget;

			// If the Y speed is greater than 3, spawn flame particles
			if (speed > 0.1f) {
				((ServerLevel) level()).sendParticles(
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
			((ServerLevel) level()).sendParticles(
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

		ExplosionInteraction explosionInteraction = IWConfigs.SERVER.meteorStaffExplosionBreakBlocks.getAsBoolean() ? ExplosionInteraction.MOB : ExplosionInteraction.NONE;

		if (!level().isClientSide) {
			if (getOwner() != null) {
				level().explode(this,
						IWDamageSources.meteor(this, getOwner()),
						null,
						position().subtract(0, 1.5, 0),
						(float) (IWConfigs.SERVER.meteorStaffExplosionRadius.getAsDouble() + explosionRadiusModifier),
						catchFire,
						explosionInteraction);
			}

			// Spawn a ring of fire particles
			for (int i = 0; i < 360; i += 10) {
				double x = Math.cos(i) * 6;
				double z = Math.sin(i) * 6;

				((ServerLevel) level()).sendParticles(
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

		discard();
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		targetPos = BlockPos.of(nbt.getLongOr("target", 0L));
		startPos = BlockPos.of(nbt.getLongOr("start", 0L));
		distToTarget = nbt.getDoubleOr("distToTarget", 0.0);
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putLong("target", targetPos.asLong());
		nbt.putLong("start", startPos.asLong());
		nbt.putDouble("distToTarget", distToTarget);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
		return super.getAddEntityPacket(entity);
	}
}