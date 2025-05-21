package tech.anonymoushacker1279.immersiveweapons.entity.ambient;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class FireflyEntity extends AmbientCreature {
	private static final EntityDataAccessor<Boolean> IS_RESTING = SynchedEntityData.defineId(FireflyEntity.class, EntityDataSerializers.BOOLEAN);
	@Nullable
	private BlockPos targetBlockPosition;
	@Nullable
	private Vec3 targetPosition;

	public FireflyEntity(EntityType<? extends FireflyEntity> type, Level level) {
		super(type, level);
		setResting(true);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(IS_RESTING, false);
	}

	@Override
	protected float getSoundVolume() {
		return 0.1F;
	}

	@Override
	@Nullable
	public SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void doPush(Entity entity) {
	}

	@Override
	protected void pushEntities() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0D);
	}

	public boolean isResting() {
		return entityData.get(IS_RESTING);
	}

	public void setResting(boolean isResting) {
		entityData.set(IS_RESTING, isResting);
	}

	@Override
	public void tick() {
		super.tick();
		if (!isResting()) {
			setDeltaMovement(getDeltaMovement().multiply(0.75D, 0.6D, 0.75D));
		}
	}

	@Override
	protected void customServerAiStep(ServerLevel serverLevel) {
		super.customServerAiStep(serverLevel);
		BlockPos pos = blockPosition();
		// Get the block pos of the block the entity is facing
		BlockPos targetPos = pos.relative(getDirection());
		if (isResting()) {
			boolean isSilent = isSilent();
			if (level().getBlockState(targetPos).isRedstoneConductor(level(), pos)) {
				BlockHitResult result = level().clip(new ClipContext(getEyePosition(1.0F), Vec3.atBottomCenterOf(targetPos), Block.COLLIDER, Fluid.NONE, this));
				// Move towards the hit position
				if (result.getType() == Type.BLOCK) {
					if (targetPosition == null) {
						targetPosition = Vec3.atCenterOf(targetPos);
						// Randomize the target position a bit, for a more natural look
						targetPosition = targetPosition.add(random.nextGaussian() * 0.25D, random.nextGaussian() * 0.25D, random.nextGaussian() * 0.25D);
					}

					Vec3 current = getEyePosition(1.0F);
					assert targetPosition != null;
					Vec3 delta = targetPosition.subtract(current);
					double distance = delta.length();
					if (distance > 0.05d) {
						Vec3 motion = delta.normalize().scale(0.05);
						setDeltaMovement(motion);
					}
				} else {
					setDeltaMovement(Vec3.ZERO);
				}

				// Set the rotation to face the block
				setYRot(getDirection().toYRot());

				// If players get too close, stop resting
				if (level().getNearestPlayer(this, 2.0D) != null) {
					setResting(false);
					targetPosition = null;
					if (!isSilent) {
						playSound(SoundEventRegistry.FIREFLY_FLYING.get());
					}
				}
			} else {
				setResting(false);
				targetPosition = null;
				if (!isSilent) {
					playSound(SoundEventRegistry.FIREFLY_FLYING.get());
				}
			}

			// 1/200 chance per tick to move
			if (random.nextInt(200) == 0) {
				setResting(false);
				targetBlockPosition = null;
			}
		} else {
			if (targetBlockPosition != null && (!level().isEmptyBlock(targetBlockPosition) || targetBlockPosition.getY() <= level().getMinY())) {
				targetBlockPosition = null;
			}

			if (targetBlockPosition == null || random.nextInt(15) == 0 || targetBlockPosition.closerToCenterThan(position(), 2.0D)) {
				targetBlockPosition = new BlockPos(
						getBlockX() + random.nextInt(7) - random.nextInt(7),
						getBlockY() + random.nextInt(6) - 2,
						getBlockZ() + random.nextInt(7) - random.nextInt(7)
				);
			}

			Vec3 newMovement = getMovement();

			setDeltaMovement(newMovement);
			float f = (float) (Mth.atan2(newMovement.z, newMovement.x) * (double) (180F / (float) Math.PI)) - 90.0F;
			float yRotChange = Mth.wrapDegrees(f - getYRot());
			zza = 0.5F;
			setYRot(getYRot() + yRotChange);
			if (random.nextInt(50) == 0 && level().getBlockState(targetPos).isRedstoneConductor(level(), targetPos)) {
				setResting(true);
			}
		}
	}

	private Vec3 getMovement() {
		if (targetBlockPosition == null) {
			return Vec3.ZERO;
		}

		double dX = (double) targetBlockPosition.getX() - getX();
		assert targetBlockPosition != null;
		double dY = (double) targetBlockPosition.getY() - getY();
		assert targetBlockPosition != null;
		double dZ = (double) targetBlockPosition.getZ() - getZ();

		Vec3 deltaMovement = getDeltaMovement();
		return deltaMovement.add(
				(Math.signum(dX) * 0.5D - deltaMovement.x) * (double) 0.1F,
				(Math.signum(dY) * (double) 0.7F - deltaMovement.y) * (double) 0.1F,
				(Math.signum(dZ) * 0.5D - deltaMovement.z) * (double) 0.1F
		);
	}

	@Override
	public boolean causeFallDamage(double distance, float damageMultiplier, DamageSource source) {
		return false;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState blockState, BlockPos blockPos) {
	}

	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
		if (isResting()) {
			setResting(false);
			targetPosition = null;
		}

		return super.hurtServer(level, damageSource, amount);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		entityData.set(IS_RESTING, compound.getBooleanOr("isResting", false));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("isResting", entityData.get(IS_RESTING));
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		EntityDimensions dimensions = super.getDefaultDimensions(pose);
		return dimensions.withEyeHeight(dimensions.height() / 2);
	}
}