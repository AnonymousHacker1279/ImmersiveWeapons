package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.neutral.MinutemanEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class FlareEntity extends BulletEntity implements ItemSupplier {

	/// Ticks over which the light fades out at the end of the flare's life.
	private static final int FADE_TICKS = 60;

	private int explodeDelay = 45;
	private int deathDelay = 600;

	public FlareEntity(EntityType<? extends Arrow> entityType, Level level) {
		super(entityType, level);
	}

	public FlareEntity(EntityType<? extends BulletEntity> entityType, LivingEntity shooter, Level level, @Nullable ItemStack firedFromWeapon) {
		super(entityType, shooter, level, firedFromWeapon);
	}

	@Override
	protected void doWhileTicking() {
		if (!isOnFire()) {
			igniteForSeconds(300);
		}

		double x = getX();
		double y = getY();
		double z = getZ();
		Vec3 deltaMovement = getDeltaMovement();

		if (level().isClientSide() && explodeDelay != 0 && (tickCount % 4) >= 2) {
			level().addParticle(ParticleTypes.FIREWORK, x, y - 0.3D, z, random.nextGaussian() * 0.05D, -deltaMovement.y * 0.5D, random.nextGaussian() * 0.05D);
		}

		shouldStopMoving = false;
		if (explodeDelay >= 0) {
			explodeDelay--;
		} else {
			if (deathDelay >= 0) {
				shouldStopMoving = true;
				if (level().isClientSide() && (tickCount % 4) == 0) {
					for (int i = 0; i < 8; i++) {
						level().addAlwaysVisibleParticle(ParticleTypes.FLAME, true, x, y, z, random.nextGaussian() * 0.1D, -deltaMovement.y * 0.25D, random.nextGaussian() * 0.1D);
					}
				}
				deathDelay--;
			} else {
				discard();
			}
		}

		// Aggro nearby minutemen to attack the nearest monster
		if (shouldStopMoving && level() instanceof ServerLevel serverLevel && tickCount % 4 == 0) {
			aggroNearbyMinutemen(serverLevel);
			setDeltaMovement(deltaMovement.multiply(1, 0.05, 1));
		}
	}

	/// Runs when an entity is hit.
	///
	/// @param entity the `Entity` being hit
	@Override
	protected void doWhenHitEntity(Entity entity) {
		super.doWhenHitEntity(entity);
		entity.igniteForSeconds(6);

		if (entity.level() instanceof ServerLevel serverLevel) {
			aggroNearbyMinutemen(serverLevel);
		}
	}

	/// Get the item associated with this entity.
	///
	/// @return ItemStack
	@Override
	public ItemStack getItem() {
		return new ItemStack(ItemRegistry.FLARE.get());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
	}

	/// How brightly the flare is lighting its surroundings, from 0 to 1. It runs at full strength, then fades over the
	/// last few seconds of its life. The glow itself is drawn client-side by `FlareLightPass`.
	///
	/// @return the light intensity
	public float getLightIntensity() {
		if (explodeDelay >= 0) {
			return 1.0F;
		}

		return Mth.clamp(deathDelay / (float) FADE_TICKS, 0.0F, 1.0F);
	}

	private void aggroNearbyMinutemen(ServerLevel serverLevel) {
		Monster monster = serverLevel.getNearestEntity(Monster.class, TargetingConditions.forCombat(), null, getX(), getY(), getZ(), getBoundingBox().inflate(7));
		MinutemanEntity minuteman = serverLevel.getNearestEntity(MinutemanEntity.class, TargetingConditions.forNonCombat(), null, getX(), getY(), getZ(), getBoundingBox().inflate(16));

		if (monster != null && minuteman != null && getOwner() instanceof LivingEntity ownerEntity) {
			if (!minuteman.isAngryAt(ownerEntity, serverLevel)) {
				minuteman.aggroNearbyMinutemen(getBoundingBox(), monster);
			}
		}
	}
}