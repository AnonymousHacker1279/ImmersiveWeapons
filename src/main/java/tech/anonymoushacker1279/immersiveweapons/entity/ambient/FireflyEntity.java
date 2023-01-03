package tech.anonymoushacker1279.immersiveweapons.entity.ambient;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
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
	private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(FireflyEntity.class, EntityDataSerializers.BYTE);
	private static final TargetingConditions RESTING_TARGETING = TargetingConditions.forNonCombat().range(2.0D);
	@Nullable
	private BlockPos targetBlockPosition;
	@Nullable
	private Vec3 targetPosition;

	public FireflyEntity(EntityType<? extends FireflyEntity> type, Level level) {
		super(type, level);
		setResting(true);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DATA_ID_FLAGS, (byte) 0);
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
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
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
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
	protected void doPush(Entity pEntity) {
	}

	@Override
	protected void pushEntities() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 1.0D);
	}

	public boolean isResting() {
		return (entityData.get(DATA_ID_FLAGS) & 1) != 0;
	}

	public void setResting(boolean isResting) {
		byte b = entityData.get(DATA_ID_FLAGS);
		if (isResting) {
			entityData.set(DATA_ID_FLAGS, (byte) (b | 1));
		} else {
			entityData.set(DATA_ID_FLAGS, (byte) (b & -2));
		}

	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		super.tick();
		if (!isResting()) {
			setDeltaMovement(getDeltaMovement().multiply(0.75D, 0.6D, 0.75D));
		}
	}

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
		BlockPos pos = blockPosition();
		// Get the block pos of the block the entity is facing
		BlockPos targetPos = pos.relative(getDirection());
		if (isResting()) {
			boolean isSilent = isSilent();
			if (level.getBlockState(targetPos).isRedstoneConductor(level, pos)) {
				// If the distance between the entity and the target block is greater than 0.05, move towards it
				// Use an BlockHitResult to check if the entity is colliding with a block

				BlockHitResult result = level.clip(new ClipContext(getEyePosition(1.0F), Vec3.atBottomCenterOf(targetPos), Block.COLLIDER, Fluid.NONE, this));
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
				if (level.getNearestPlayer(RESTING_TARGETING, this) != null) {
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
		} else {
			if (targetBlockPosition != null && (!level.isEmptyBlock(targetBlockPosition) || targetBlockPosition.getY() <= level.getMinBuildHeight())) {
				targetBlockPosition = null;
			}

			if (targetBlockPosition == null || random.nextInt(15) == 0 || targetBlockPosition.closerToCenterThan(position(), 2.0D)) {
				targetBlockPosition = new BlockPos(
						getX() + (double) random.nextInt(7) - (double) random.nextInt(7),
						getY() + (double) random.nextInt(6) - 2.0D,
						getZ() + (double) random.nextInt(7) - (double) random.nextInt(7)
				);
			}

			double xDiff = (double) targetBlockPosition.getX() - getX();
			assert targetBlockPosition != null;
			double yDiff = (double) targetBlockPosition.getY() - getY();
			assert targetBlockPosition != null;
			double zDiff = (double) targetBlockPosition.getZ() - getZ();

			Vec3 deltaMovement = getDeltaMovement();
			Vec3 newMovement = deltaMovement.add(
					(Math.signum(xDiff) * 0.5D - deltaMovement.x) * (double) 0.1F,
					(Math.signum(yDiff) * (double) 0.7F - deltaMovement.y) * (double) 0.1F,
					(Math.signum(zDiff) * 0.5D - deltaMovement.z) * (double) 0.1F
			);

			setDeltaMovement(newMovement);
			float f = (float) (Mth.atan2(newMovement.z, newMovement.x) * (double) (180F / (float) Math.PI)) - 90.0F;
			float yRotChange = Mth.wrapDegrees(f - getYRot());
			zza = 0.5F;
			setYRot(getYRot() + yRotChange);
			if (random.nextInt(50) == 0 && level.getBlockState(targetPos).isRedstoneConductor(level, targetPos)) {
				setResting(true);
			}
		}

	}

	@Override
	protected Entity.MovementEmission getMovementEmission() {
		return Entity.MovementEmission.EVENTS;
	}

	@Override
	public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
		return false;
	}

	@Override
	protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
	}

	/**
	 * Return whether this entity should NOT trigger a pressure plate or a tripwire.
	 */
	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (isInvulnerableTo(pSource)) {
			return false;
		} else {
			if (!level.isClientSide && isResting()) {
				setResting(false);
				targetPosition = null;
			}

			return super.hurt(pSource, pAmount);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		entityData.set(DATA_ID_FLAGS, pCompound.getByte("FireflyFlags"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putByte("FireflyFlags", entityData.get(DATA_ID_FLAGS));
	}

	@Override
	protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
		return pSize.height / 2.0F;
	}
}