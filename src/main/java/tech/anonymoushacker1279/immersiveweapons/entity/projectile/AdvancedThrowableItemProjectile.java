package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public abstract class AdvancedThrowableItemProjectile extends ThrowableItemProjectile {

	protected boolean stopMoving = false;
	protected boolean hasActivated = false;
	@Nullable
	protected BlockState inBlockState;
	public float randomRotation;
	protected int ticksInGround;

	public AdvancedThrowableItemProjectile(EntityType<? extends AdvancedThrowableItemProjectile> entityType, Level level) {
		super(entityType, level);
	}

	public AdvancedThrowableItemProjectile(EntityType<? extends ThrowableItemProjectile> entityType, LivingEntity livingEntity, Level level) {
		super(entityType, livingEntity, level);
	}

	public AdvancedThrowableItemProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level, double x, double y, double z) {
		super(entityType, x, y, z, level);
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!stopMoving) {
			double x = getDeltaMovement().x();
			double y = getDeltaMovement().y();
			double z = getDeltaMovement().z();

			if (hitResult.getType() == HitResult.Type.BLOCK) {
				BlockHitResult blockHitResult = (BlockHitResult) hitResult;
				switch (blockHitResult.getDirection()) {
					case UP:
					case DOWN:
						y = -y * 0.4d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
						break;
					case NORTH:
					case SOUTH:
						z = -z * 0.4d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
						break;
					case EAST:
					case WEST:
						x = -x * 0.4d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
						break;
				}
			} else if (hitResult.getType() == Type.ENTITY) {
				x = -x * 0.3d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
				y = -y * 0.3d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
				z = -z * 0.3d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
			}

			setDeltaMovement(x, y, z);

			level().playLocalSound(this, SoundEvents.METAL_HIT, SoundSource.NEUTRAL, 1.0f, 0.6f / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 0.8f));

			if (getDeltaMovement().lengthSqr() < 0.1d) {
				stopMoving = true;
				setDeltaMovement(Vec3.ZERO);
				setNoGravity(true);
				inBlockState = level().getBlockState(blockPosition());

				if (!hasActivated) {
					onActivate();
					hasActivated = true;
				}
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (inBlockState != null) {
			if (inBlockState.isAir()) {
				if (stopMoving) {
					// Keep falling until it hits the ground
					setDeltaMovement(getDeltaMovement().x(), getDeltaMovement().y() - getGravity() - 0.001f, getDeltaMovement().z());
					inBlockState = level().getBlockState(blockPosition());
				}
			} else {
				setDeltaMovement(0, 0, 0);
				setNoGravity(true);
				ticksInGround++;
			}

			if (inBlockState != level().getBlockState(blockPosition())) {
				inBlockState = null;
				stopMoving = false;
				setNoGravity(false);
			}
		}

		if (ticksInGround > 300) {
			kill();
		}
	}

	public void shootFromDirection(Direction direction, float velocity, float inaccuracy) {
		float x;
		float y;
		float z;

		switch (direction) {
			case EAST, WEST -> {
				x = 0;
				y = direction.toYRot();
				z = 0;
			}
			default -> {
				x = direction.toYRot();
				y = 0;
				z = 0;
			}
		}

		float newX = -Mth.sin(y * (float) (Math.PI / 180.0f)) * Mth.cos(x * (float) (Math.PI / 180.0f));
		float newY = -Mth.sin((x + z) * (float) (Math.PI / 180.0f));
		float newZ = Mth.cos(y * (float) (Math.PI / 180.0f)) * Mth.cos(x * (float) (Math.PI / 180.0f));

		this.shoot(newX, newY, newZ, velocity, inaccuracy);
	}

	public static boolean canSee(LivingEntity livingEntity, Entity entity, boolean lookingAt) {
		if (livingEntity.hasLineOfSight(entity)) {
			if (!lookingAt) {
				return true;
			}

			Vec3 lookVec = livingEntity.getLookAngle();
			Vec3 toEntity = entity.position().subtract(livingEntity.position()).normalize();

			double dotProduct = lookVec.dot(toEntity);
			double maxCosine = Math.cos(Math.toRadians(90));
			double angleToGround = 180 - (Math.acos(lookVec.y) * (180.0f / Math.PI));
			return dotProduct > maxCosine || angleToGround < 30;
		}

		return false;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);

		pCompound.putBoolean("stopMoving", stopMoving);
		pCompound.putBoolean("hasActivated", hasActivated);
		pCompound.putInt("ticksInGround", ticksInGround);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);

		stopMoving = pCompound.getBoolean("stopMoving");
		hasActivated = pCompound.getBoolean("hasActivated");
		ticksInGround = pCompound.getInt("ticksInGround");
	}

	/**
	 * Called whenever activation effects should take place.
	 */
	protected void onActivate() {
	}

	/**
	 * Get the number of ticks the projectile has been in the ground. Used by IWCB.
	 *
	 * @return int
	 */
	@SuppressWarnings("unused")
	public int getTicksInGround() {
		return ticksInGround;
	}
}