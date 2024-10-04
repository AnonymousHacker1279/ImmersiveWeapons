package tech.anonymoushacker1279.immersiveweapons.entity.monster.lava_revenant;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.network.payload.LocalSoundPayload;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class LavaRevenantEntity extends FlyingMob implements Enemy, GrantAdvancementOnDiscovery {

	private static final int TICKS_PER_FLAP = Mth.ceil(24.166098F);
	private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(LavaRevenantEntity.class,
			EntityDataSerializers.INT);
	Vec3 moveTargetPoint = Vec3.ZERO;
	BlockPos anchorPoint = BlockPos.ZERO;
	LavaRevenantEntity.AttackPhase attackPhase = LavaRevenantEntity.AttackPhase.CIRCLE;
	private final LavaRevenantPart[] subEntities;
	private final LavaRevenantPart wing1;
	private final LavaRevenantPart wing2;
	private final LavaRevenantPart head;
	private final LavaRevenantPart body;
	private final LavaRevenantPart tail;
	private final double[][] positions = new double[64][3];
	private int posPointer = -1;
	private float yRotA;
	private boolean inWall;

	public LavaRevenantEntity(EntityType<? extends LavaRevenantEntity> entityType, Level level) {
		super(entityType, level);
		xpReward = 25 * getSize();
		moveControl = new LavaRevenantMoveControl(this);
		lookControl = new LavaRevenantLookControl(this);

		wing1 = new LavaRevenantPart(this, "wing", (6.0F * getScale()), 2.0F);
		wing2 = new LavaRevenantPart(this, "wing", (6.0F * getScale()), 2.0F);
		head = new LavaRevenantPart(this, "head", (3.0F * getScale()), (2.7F * getScale()));
		body = new LavaRevenantPart(this, "body", (5.0F * (getScale() * 0.8f)), (2.0F * getScale()));
		tail = new LavaRevenantPart(this, "tail", (2.5F * (getScale() * 0.8f)), 1.0F);
		subEntities = new LavaRevenantPart[]{wing1, wing2, head, body, tail};

		noPhysics = true;
	}

	/**
	 * Register this entity's attributes.
	 *
	 * @return AttributeSupplier.Builder
	 */
	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FLYING_SPEED, 0.70D)
				.add(Attributes.ARMOR, 20.0D);
	}

	@Override
	public boolean isFlapping() {
		return (getUniqueFlapTickOffset() + tickCount) % TICKS_PER_FLAP == 0;
	}

	@Override
	public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
		return pDistanceToClosestPlayer >= 512;
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new LavaRevenantBodyRotationControl(this);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new AttackStrategyGoal());
		goalSelector.addGoal(2, new SweepAttackGoal());
		goalSelector.addGoal(3, new CircleAroundAnchorGoal());
		targetSelector.addGoal(1, new AttackPlayerTargetGoal());
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ID_SIZE, 0);
	}

	private void updateSizeInfo() {
		refreshDimensions();
		Objects.requireNonNull(getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(14 + getSize());
		Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).setBaseValue(30 * (getSize() / 2.0f));
	}

	public int getSize() {
		return entityData.get(ID_SIZE);
	}

	public void setSize(int pSize) {
		entityData.set(ID_SIZE, Mth.clamp(pSize, 0, 64));
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		EntityDimensions dimensions = super.getDefaultDimensions(pose);
		int size = getSize();
		float scaleFactor = (dimensions.width() + 0.2F * (float) size) / dimensions.width();

		return dimensions.scale(scaleFactor).withEyeHeight(dimensions.height() * 0.35f);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
		if (ID_SIZE.equals(pKey)) {
			updateSizeInfo();
		}

		super.onSyncedDataUpdated(pKey);
	}

	public int getUniqueFlapTickOffset() {
		return getId() * 3;
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		super.tick();

		if (level().isClientSide) {
			float flapTick = Mth.cos((float) (getUniqueFlapTickOffset() + tickCount) * 7.448451F
					* ((float) Math.PI / 180F) + (float) Math.PI);
			float nextFlapTick = Mth.cos((float) (getUniqueFlapTickOffset() + tickCount + 1) * 7.448451F
					* ((float) Math.PI / 180F) + (float) Math.PI);

			if (inWall) {
				nextFlapTick *= 2.0f;
			}

			if (flapTick > 0.0F && nextFlapTick <= 0.0F) {
				level().playLocalSound(getX(), getY(), getZ(), SoundEventRegistry.LAVA_REVENANT_FLAP.get(),
						getSoundSource(), 0.95F + random.nextFloat() * 0.05F,
						0.95F + random.nextFloat() * 0.05F, false);
			}

			int size = getSize();
			float xSizeModifier = Mth.cos(getYRot() * ((float) Math.PI / 180F)) * (7.0F * (float) size);
			float zSizeModifier = Mth.sin(getYRot() * ((float) Math.PI / 180F)) * (7.0f * (float) size);
			float ySizeModifier = (0.3F + flapTick * 0.45F) * ((float) size * 0.2F + 1.5F);

			level().addParticle(ParticleTypes.LAVA, getX() + (double) xSizeModifier, getY() + (double) ySizeModifier,
					getZ() + (double) zSizeModifier, 0.0D, 0.0D, 0.0D);
			level().addParticle(ParticleTypes.LAVA, getX() - (double) xSizeModifier, getY() + (double) ySizeModifier,
					getZ() - (double) zSizeModifier, 0.0D, 0.0D, 0.0D);

		}

		if (posPointer < 0) {
			for (int i = 0; i < positions.length; ++i) {
				positions[i][0] = getYRot();
				positions[i][1] = getY();
			}
		}

		if (++posPointer == positions.length) {
			posPointer = 0;
		}

		Vec3[] subEntitiesLengthVector = new Vec3[subEntities.length];

		for (int i = 0; i < subEntities.length; ++i) {
			subEntitiesLengthVector[i] = new Vec3(subEntities[i].getX(), subEntities[i].getY(), subEntities[i].getZ());
		}

		double moveTargetDeltaX = moveTargetPoint.x - getX();
		double moveTargetDeltaZ = moveTargetPoint.z - getZ();

		if (Math.abs(moveTargetDeltaX) > (double) 1.0E-5F || Math.abs(moveTargetDeltaZ) > (double) 1.0E-5F) {
			float yRotAModifier = Mth.clamp(
					Mth.wrapDegrees(180.0F - (float) Mth.atan2(moveTargetDeltaX, moveTargetDeltaZ)
							* (180F / (float) Math.PI) - getYRot()),
					-10.0F, 10.0F);

			yRotA *= 0.8F;
			yRotA += yRotAModifier * getTurnSpeed();
		}

		float latencyPosModifier = (float) (getLatencyPos(5, 1.0F)[1]
				- getLatencyPos(10, 1.0F)[1]) * 10.0F * ((float) Math.PI / 180F);
		float cosLatencyPosModifier = Mth.cos(latencyPosModifier);
		float sinLatencyPosModifier = Mth.sin(latencyPosModifier);
		float sinRotModifier = Mth.sin(getYRot() * ((float) Math.PI / 180F) - yRotA * 0.01F);
		float cosRotModifier = Mth.cos(getYRot() * ((float) Math.PI / 180F) - yRotA * 0.01F);
		float yHeadOffset = getHeadYOffset();

		float yRadians = getYRot() * ((float) Math.PI / 180F);
		float wingXOffset = Mth.cos(yRadians);
		float wingZOffset = Mth.sin(yRadians);

		tickPart(wing1, wingXOffset * 4.5f, 2f, wingZOffset * 4.5f);
		tickPart(wing2, wingXOffset * -4.5f, 2f, wingZOffset * -4.5f);

		tickPart(body, (sinRotModifier * 0.5F), 0.0D, (-cosRotModifier * 0.5F));

		tickPart(head, (-sinRotModifier * (4.5f * getScale() * 0.8f) * cosLatencyPosModifier),
				(yHeadOffset + sinLatencyPosModifier * 6.5f),
				(cosRotModifier * (4.5f * getScale() * 0.8f) * cosLatencyPosModifier));

		tickPart(tail, (sinRotModifier * (4.5f * getScale() * 0.8f) * cosLatencyPosModifier),
				(yHeadOffset + sinLatencyPosModifier * 6.5f),
				(-cosRotModifier * (4.5f * getScale() * 0.8f) * cosLatencyPosModifier));

		if (!level().isClientSide) {
			inWall = checkWalls(head.getBoundingBox()) | checkWalls(body.getBoundingBox());
		}

		for (int i = 0; i < subEntities.length; ++i) {
			subEntities[i].xo = subEntitiesLengthVector[i].x;
			subEntities[i].yo = subEntitiesLengthVector[i].y;
			subEntities[i].zo = subEntitiesLengthVector[i].z;
			subEntities[i].xOld = subEntitiesLengthVector[i].x;
			subEntities[i].yOld = subEntitiesLengthVector[i].y;
			subEntities[i].zOld = subEntitiesLengthVector[i].z;
		}

	}

	private void tickPart(LavaRevenantPart pPart, double offsetX, double offsetY, double offsetZ) {
		pPart.setPos(getX() + offsetX, getY() + offsetY, getZ() + offsetZ);
	}

	public double[] getLatencyPos(int pointer, float multiplier) {
		if (isDeadOrDying()) {
			multiplier = 0.0F;
		}

		multiplier = 1.0F - multiplier;
		int item = posPointer - pointer & 63;
		int previousItem = posPointer - pointer - 1 & 63;
		double[] latencyPos = new double[3];
		double yawOffset = positions[item][0];
		double yOffsetMinusYawOffset = Mth.wrapDegrees(positions[previousItem][0] - yawOffset);
		latencyPos[0] = yawOffset + yOffsetMinusYawOffset * (double) multiplier;
		yawOffset = positions[item][1];
		yOffsetMinusYawOffset = positions[previousItem][1] - yawOffset;
		latencyPos[1] = yawOffset + yOffsetMinusYawOffset * (double) multiplier;
		latencyPos[2] = Mth.lerp(multiplier, positions[item][2], positions[previousItem][2]);
		return latencyPos;
	}

	private float getHeadYOffset() {
		double[] latencyPos = getLatencyPos(5, 1.0F);
		double[] latencyPos1 = getLatencyPos(0, 1.0F);
		return (float) (latencyPos[1] - latencyPos1[1]);
	}

	public float getTurnSpeed() {
		float deltaDistance = (float) (getDeltaMovement().horizontalDistance() + 1.0F);
		float min = Math.min(deltaDistance, 40.0F);
		return 0.7F / min / deltaDistance;
	}

	public boolean hurt(LavaRevenantPart part, DamageSource source, float damage) {
		if (part == wing1 || part == wing2) {
			damage = damage * 0.65f;
		} else if (part == head) {
			damage = damage * 1.5f;
		} else if (part == tail) {
			damage = damage * 0.35f;
		}

		return hurt(source, damage);
	}

	/**
	 * Destroys weak blocks that are in the way
	 */
	private boolean checkWalls(AABB pArea) {
		int minX = Mth.floor(pArea.minX);
		int minY = Mth.floor(pArea.minY);
		int minZ = Mth.floor(pArea.minZ);
		int maxX = Mth.floor(pArea.maxX);
		int maxY = Mth.floor(pArea.maxY);
		int maxZ = Mth.floor(pArea.maxZ);
		boolean stuck = false;
		boolean blockRemoved = false;

		for (int i = minX; i <= maxX; ++i) {
			for (int lMinY = minY; lMinY <= maxY; ++lMinY) {
				for (int lMinZ = minZ; lMinZ <= maxZ; ++lMinZ) {
					BlockPos pos = new BlockPos(i, lMinY, lMinZ);
					BlockState state = level().getBlockState(pos);
					if (!state.isAir()) {
						if (CommonHooks.canEntityDestroy(level(), pos, this) && state.getDestroySpeed(level(), pos) <= 1.5f) {

							blockRemoved = level().removeBlock(pos, false) || blockRemoved;
						} else if (!state.liquid()) {
							stuck = true;
						}
					}
				}
			}
		}

		if (blockRemoved) {
			BlockPos randomPos = new BlockPos(minX + random.nextInt(maxX - minX + 1),
					minY + random.nextInt(maxY - minY + 1),
					minZ + random.nextInt(maxZ - minZ + 1));
			level().levelEvent(2008, randomPos, 0);
		}

		return stuck;
	}

	@Override
	public boolean isMultipartEntity() {
		return true;
	}

	@Override
	public PartEntity<?>[] getParts() {
		return subEntities;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
	                                    MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {

		anchorPoint = blockPosition().above(15);
		setSize(getRandom().nextIntBetweenInclusive(1, 3));
		setHealth(getMaxHealth());
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
	}

	/**
	 * Helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("AX")) {
			anchorPoint = new BlockPos(pCompound.getInt("AX"),
					pCompound.getInt("AY"),
					pCompound.getInt("AZ"));
		}

		setSize(pCompound.getInt("Size"));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("AX", anchorPoint.getX());
		pCompound.putInt("AY", anchorPoint.getY());
		pCompound.putInt("AZ", anchorPoint.getZ());
		pCompound.putInt("Size", getSize());
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@Override
	public boolean shouldRenderAtSqrDistance(double pDistance) {
		return true;
	}

	@Override
	public SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEventRegistry.LAVA_REVENANT_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEventRegistry.LAVA_REVENANT_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEventRegistry.LAVA_REVENANT_DEATH.get();
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	@Override
	public boolean canAttackType(EntityType<?> pType) {
		return true;
	}

	@Override
	public boolean checkSpawnObstruction(LevelReader pLevel) {

		if (blockPosition().getY() > 0) {
			if (super.checkSpawnObstruction(pLevel)) {
				AABB box = new AABB(blockPosition()).inflate(4);

				// At least 60% of the blocks must be air
				AtomicInteger airBlocks = new AtomicInteger();
				level().getBlockStates(box).forEach(state -> {
					if (!state.isAir()) {
						airBlocks.getAndIncrement();
					}
				});
				return airBlocks.get() >= box.getSize() * 0.6;
			}
		}

		return false;
	}

	enum AttackPhase {
		CIRCLE,
		SWOOP
	}

	static class LavaRevenantLookControl extends LookControl {
		public LavaRevenantLookControl(Mob mob) {
			super(mob);
		}

		/**
		 * Updates look
		 */
		@Override
		public void tick() {
		}
	}

	class AttackPlayerTargetGoal extends Goal {
		private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(128.0D);
		private int nextScanTick = 20;

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			if (nextScanTick > 0) {
				--nextScanTick;
			} else {
				nextScanTick = 60;
				List<Player> nearbyPlayers = level().getNearbyPlayers(attackTargeting, LavaRevenantEntity.this,
						getBoundingBox().inflate(128.0D, 96.0D, 128.0D));
				if (!nearbyPlayers.isEmpty()) {
					nearbyPlayers.sort(Comparator.<Entity, Double> comparing(Entity::getY).reversed());

					for (Player player : nearbyPlayers) {
						if (canAttack(player, TargetingConditions.DEFAULT)) {
							setTarget(player);
							return true;
						}
					}
				}

			}
			return false;
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean canContinueToUse() {
			LivingEntity target = getTarget();
			return target != null && canAttack(target, TargetingConditions.DEFAULT);
		}
	}

	class AttackStrategyGoal extends Goal {
		private int nextSweepTick;

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			LivingEntity target = getTarget();
			return target != null && canAttack(getTarget(), TargetingConditions.DEFAULT);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void start() {
			nextSweepTick = 10;
			attackPhase = LavaRevenantEntity.AttackPhase.CIRCLE;
			setAnchorAboveTarget();
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		@Override
		public void stop() {
			anchorPoint = level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, anchorPoint)
					.above(30 + random.nextInt(20));
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			if (attackPhase == LavaRevenantEntity.AttackPhase.CIRCLE) {
				--nextSweepTick;
				if (nextSweepTick <= 0) {
					attackPhase = LavaRevenantEntity.AttackPhase.SWOOP;
					setAnchorAboveTarget();
					nextSweepTick = (8 + random.nextInt(4)) * 20;
					playSound(SoundEventRegistry.LAVA_REVENANT_SWOOP.get(), 10.0F,
							0.95F + random.nextFloat() * 0.1F);
				}
			}

		}

		private void setAnchorAboveTarget() {
			anchorPoint = Objects.requireNonNull(getTarget()).blockPosition().above(30 + random.nextInt(20));
			if (anchorPoint.getY() < level().getSeaLevel()) {
				anchorPoint = new BlockPos(anchorPoint.getX(), level().getSeaLevel() + 1, anchorPoint.getZ());
			}

		}
	}

	class LavaRevenantBodyRotationControl extends BodyRotationControl {
		public LavaRevenantBodyRotationControl(Mob mob) {
			super(mob);
		}

		/**
		 * Update the Head and Body rendering angles
		 */
		@Override
		public void clientTick() {
			yHeadRot = yBodyRot;
			yBodyRot = getYRot();
		}
	}

	class CircleAroundAnchorGoal extends MoveTargetGoal {
		private float angle;
		private float distance;
		private float height;
		private float clockwise;

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			return getTarget() == null || attackPhase == LavaRevenantEntity.AttackPhase.CIRCLE;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void start() {
			distance = 5.0F + random.nextFloat() * 10.0F;
			height = -4.0F + random.nextFloat() * 9.0F;
			clockwise = random.nextBoolean() ? 1.0F : -1.0F;
			selectNext();
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			if (random.nextInt(350) == 0) {
				height = -4.0F + random.nextFloat() * 9.0F;
			}

			if (random.nextInt(250) == 0) {
				++distance;
				if (distance > 15.0F) {
					distance = 5.0F;
					clockwise = -clockwise;
				}
			}

			if (random.nextInt(450) == 0) {
				angle = random.nextFloat() * 2.0F * (float) Math.PI;
				selectNext();
			}

			if (touchingTarget()) {
				selectNext();
			}

			if (moveTargetPoint.y < getY() && !level().isEmptyBlock(blockPosition().below(1))) {
				height = Math.max(1.0F, height);
				selectNext();
			}

			if (moveTargetPoint.y > getY() && !level().isEmptyBlock(blockPosition().above(1))) {
				height = Math.min(-1.0F, height);
				selectNext();
			}

		}

		private void selectNext() {
			if (BlockPos.ZERO.equals(anchorPoint)) {
				anchorPoint = blockPosition();
			}

			angle += clockwise * 15.0F * ((float) Math.PI / 180F);
			moveTargetPoint = Vec3.atLowerCornerOf(anchorPoint).add(distance * Mth.cos(angle), -4.0F + height,
					distance * Mth.sin(angle));
		}
	}

	class LavaRevenantMoveControl extends MoveControl {
		private float speed = 0.1F;

		public LavaRevenantMoveControl(Mob mob) {
			super(mob);
		}

		@Override
		public void tick() {
			if (horizontalCollision) {
				setYRot(getYRot() + 180.0F);
				speed = 0.1F;
			}

			float deltaTargetX = (float) (moveTargetPoint.x - getX());
			float deltaTargetY = (float) (moveTargetPoint.y - getY());
			float deltaTargetZ = (float) (moveTargetPoint.z - getZ());
			double sqrtXZ = Mth.sqrt(deltaTargetX * deltaTargetX + deltaTargetZ * deltaTargetZ);
			if (Math.abs(sqrtXZ) > (double) 1.0E-5F) {
				double absolute = 1.0D - (double) Mth.abs(deltaTargetY * 0.7F) / sqrtXZ;
				deltaTargetX = (float) ((double) deltaTargetX * absolute);
				deltaTargetZ = (float) ((double) deltaTargetZ * absolute);
				sqrtXZ = Mth.sqrt(deltaTargetX * deltaTargetX + deltaTargetZ * deltaTargetZ);
				double sqrtXZY = Mth.sqrt(deltaTargetX * deltaTargetX + deltaTargetZ
						* deltaTargetZ + deltaTargetY * deltaTargetY);

				float yRotation = getYRot();
				float angle = (float) Mth.atan2(deltaTargetZ, deltaTargetX);
				float yRotationWrapped90 = Mth.wrapDegrees(getYRot() + 90.0F);
				float angleWrapped = Mth.wrapDegrees(angle * (180F / (float) Math.PI));

				setYRot(Mth.approachDegrees(yRotationWrapped90, angleWrapped, 4.0F) - 90.0F);
				yBodyRot = getYRot();

				if (Mth.degreesDifferenceAbs(yRotation, getYRot()) < 3.0F) {
					speed = Mth.approach(speed, 1.8F, 0.005F * (1.8F / speed));
				} else {
					speed = Mth.approach(speed, 0.2F, 0.025F);
				}

				float xRotation = (float) (-(Mth.atan2(-deltaTargetY, sqrtXZ) * (double) (180F / (float) Math.PI)));
				setXRot(xRotation);
				float yRotationPlus90 = getYRot() + 90.0F;

				double x = (double) (speed * Mth.cos(yRotationPlus90 * ((float) Math.PI / 180F)))
						* Math.abs((double) deltaTargetX / sqrtXZY);
				double y = (double) (speed * Mth.sin(yRotationPlus90 * ((float) Math.PI / 180F)))
						* Math.abs((double) deltaTargetZ / sqrtXZY);
				double z = (double) (speed * Mth.sin(xRotation * ((float) Math.PI / 180F)))
						* Math.abs((double) deltaTargetY / sqrtXZY);

				Vec3 deltaMovement = getDeltaMovement();

				if (inWall) {
					setYRot(yRotation - 180f);
					deltaMovement.scale(0.8f);
					deltaMovement.reverse();
				}

				setDeltaMovement(deltaMovement.add((new Vec3(x, z, y)).subtract(deltaMovement).scale(0.2D)));
			}
		}
	}

	abstract class MoveTargetGoal extends Goal {
		public MoveTargetGoal() {
			setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		protected boolean touchingTarget() {
			return moveTargetPoint.distanceToSqr(getX(), getY(), getZ()) < 4.0D;
		}
	}

	class SweepAttackGoal extends MoveTargetGoal {
		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			return getTarget() != null && attackPhase == LavaRevenantEntity.AttackPhase.SWOOP;
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean canContinueToUse() {
			LivingEntity target = getTarget();
			if (target == null) {
				return false;
			} else if (!target.isAlive()) {
				return false;
			} else if (!(target instanceof Player) || !target.isSpectator() && !((Player) target).isCreative()) {
				return canUse();
			} else {
				return false;
			}
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		@Override
		public void start() {
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		@Override
		public void stop() {
			setTarget(null);
			attackPhase = LavaRevenantEntity.AttackPhase.CIRCLE;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		@Override
		public void tick() {
			LivingEntity target = getTarget();
			if (target != null) {
				moveTargetPoint = new Vec3(target.getX(), target.getY(0.5D), target.getZ());
				if (getBoundingBox().inflate(0.2F).intersects(target.getBoundingBox())) {
					if (!inWall) {
						doHurtTarget(target);
					}
					attackPhase = LavaRevenantEntity.AttackPhase.CIRCLE;
					if (!isSilent()) {
						PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level(), chunkPosition(), new LocalSoundPayload(blockPosition(), SoundEventRegistry.LAVA_REVENANT_BITE.get().getLocation(),
								SoundSource.HOSTILE, 0.3F, level().getRandom().nextFloat() * 0.1F + 0.9F, false));
					}
				} else if (horizontalCollision || hurtTime > 0) {
					attackPhase = LavaRevenantEntity.AttackPhase.CIRCLE;
				}
			}
		}
	}
}