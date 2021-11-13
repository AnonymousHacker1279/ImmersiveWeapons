package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class LavaRevenantEntity extends FlyingMob implements Enemy {

	public static final int TICKS_PER_FLAP = Mth.ceil(24.166098F);
	private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(LavaRevenantEntity.class, EntityDataSerializers.INT);
	Vec3 moveTargetPoint = Vec3.ZERO;
	BlockPos anchorPoint = BlockPos.ZERO;
	LavaRevenantEntity.AttackPhase attackPhase = LavaRevenantEntity.AttackPhase.CIRCLE;

	public LavaRevenantEntity(EntityType<? extends LavaRevenantEntity> entityType, Level level) {
		super(entityType, level);
		xpReward = 25;
		moveControl = new LavaRevenantMoveControl(this);
		lookControl = new LavaRevenantLookControl(this);
	}

	@Override
	public boolean isFlapping() {
		return (getUniqueFlapTickOffset() + tickCount) % TICKS_PER_FLAP == 0;
	}

	@Override
	public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
		return pDistanceToClosestPlayer >= 768;
	}

	@Override
	protected @NotNull BodyRotationControl createBodyControl() {
		return new LavaRevenantBodyRotationControl(this);
	}

	/**
	 * Register this entity's attributes.
	 * @return AttributeModifierMap.MutableAttribute
	 */
	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FLYING_SPEED, 0.70D)
				.add(Attributes.ARMOR, 35.0D);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new AttackStrategyGoal());
		goalSelector.addGoal(2, new SweepAttackGoal());
		goalSelector.addGoal(3, new CircleAroundAnchorGoal());
		targetSelector.addGoal(1, new AttackPlayerTargetGoal());
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(ID_SIZE, 0);
	}

	public void setSize(int pSize) {
		entityData.set(ID_SIZE, Mth.clamp(pSize, 0, 64));
	}

	private void updateSizeInfo() {
		refreshDimensions();
		Objects.requireNonNull(getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(14 + getSize());
		Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).setBaseValue(30 * (getSize() / 2.0f));
	}

	public int getSize() {
		return entityData.get(ID_SIZE);
	}

	@Override
	protected float getStandingEyeHeight(@NotNull Pose pPose, EntityDimensions pSize) {
		return pSize.height * 0.35F;
	}

	@Override
	public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> pKey) {
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

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		super.tick();
		if (level.isClientSide) {
			float f = Mth.cos((float)(getUniqueFlapTickOffset() + tickCount) * 7.448451F * ((float)Math.PI / 180F) + (float)Math.PI);
			float f1 = Mth.cos((float)(getUniqueFlapTickOffset() + tickCount + 1) * 7.448451F * ((float)Math.PI / 180F) + (float)Math.PI);
			if (f > 0.0F && f1 <= 0.0F) {
				level.playLocalSound(getX(), getY(), getZ(), DeferredRegistryHandler.LAVA_REVENANT_FLAP.get(), getSoundSource(), 0.95F + random.nextFloat() * 0.05F, 0.95F + random.nextFloat() * 0.05F, false);
			}

			int size = getSize();
			float xSizeModifier = Mth.cos(getYRot() * ((float)Math.PI / 180F)) * (7.0F * (float)size);
			float zSizeModifier = Mth.sin(getYRot() * ((float)Math.PI / 180F)) * (7.0f * (float)size);
			float ySizeModifier = (0.3F + f * 0.45F) * ((float)size * 0.2F + 1.5F);
			level.addParticle(ParticleTypes.LAVA, getX() + (double)xSizeModifier, getY() + (double)ySizeModifier, getZ() + (double)zSizeModifier, 0.0D, 0.0D, 0.0D);
			level.addParticle(ParticleTypes.LAVA, getX() - (double)xSizeModifier, getY() + (double)ySizeModifier, getZ() - (double)zSizeModifier, 0.0D, 0.0D, 0.0D);
		}

	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
	 * use this to react to sunlight and start to burn.
	 */
	@Override
	public void aiStep() {
		super.aiStep();
	}

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
	}

	@Override
	public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor pLevel, @NotNull DifficultyInstance pDifficulty, @NotNull MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
		anchorPoint = blockPosition().above(15);
		setSize(GeneralUtilities.getRandomNumber(1, 4));
		setHealth(getMaxHealth());
		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("AX")) {
			anchorPoint = new BlockPos(pCompound.getInt("AX"), pCompound.getInt("AY"), pCompound.getInt("AZ"));
		}

		setSize(pCompound.getInt("Size"));
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
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
	public @NotNull SoundSource getSoundSource() {
		return SoundSource.HOSTILE;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.LAVA_REVENANT_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
		return DeferredRegistryHandler.LAVA_REVENANT_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.LAVA_REVENANT_DEATH.get();
	}

	@Override
	public @NotNull MobType getMobType() {
		return MobType.UNDEAD;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 1.0F;
	}

	@Override
	public boolean canAttackType(@NotNull EntityType<?> pType) {
		return true;
	}

	@Override
	public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
		int i = getSize();
		EntityDimensions dimensions = super.getDimensions(pPose);
		float f = (dimensions.width + 0.2F * (float)i) / dimensions.width;
		return dimensions.scale(f);
	}

	enum AttackPhase {
		CIRCLE,
		SWOOP
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
				List<Player> list = level.getNearbyPlayers(attackTargeting, LavaRevenantEntity.this, getBoundingBox().inflate(128.0D, 96.0D, 128.0D));
				if (!list.isEmpty()) {
					list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

					for(Player player : list) {
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
			LivingEntity livingentity = getTarget();
			return livingentity != null && canAttack(livingentity, TargetingConditions.DEFAULT);
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
			LivingEntity livingentity = getTarget();
			return livingentity != null && canAttack(getTarget(), TargetingConditions.DEFAULT);
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
			anchorPoint = level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, anchorPoint).above(30 + random.nextInt(20));
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
					playSound(DeferredRegistryHandler.LAVA_REVENANT_SWOOP.get(), 10.0F, 0.95F + random.nextFloat() * 0.1F);
				}
			}

		}

		private void setAnchorAboveTarget() {
			anchorPoint = Objects.requireNonNull(getTarget()).blockPosition().above(30 + random.nextInt(20));
			if (anchorPoint.getY() < level.getSeaLevel()) {
				anchorPoint = new BlockPos(anchorPoint.getX(), level.getSeaLevel() + 1, anchorPoint.getZ());
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
				angle = random.nextFloat() * 2.0F * (float)Math.PI;
				selectNext();
			}

			if (touchingTarget()) {
				selectNext();
			}

			if (moveTargetPoint.y < getY() && !level.isEmptyBlock(blockPosition().below(1))) {
				height = Math.max(1.0F, height);
				selectNext();
			}

			if (moveTargetPoint.y > getY() && !level.isEmptyBlock(blockPosition().above(1))) {
				height = Math.min(-1.0F, height);
				selectNext();
			}

		}

		private void selectNext() {
			if (BlockPos.ZERO.equals(anchorPoint)) {
				anchorPoint = blockPosition();
			}

			angle += clockwise * 15.0F * ((float)Math.PI / 180F);
			moveTargetPoint = Vec3.atLowerCornerOf(anchorPoint).add(distance * Mth.cos(angle), -4.0F + height, distance * Mth.sin(angle));
		}
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

			float f = (float)(moveTargetPoint.x - getX());
			float f1 = (float)(moveTargetPoint.y - getY());
			float f2 = (float)(moveTargetPoint.z - getZ());
			double d0 = Mth.sqrt(f * f + f2 * f2);
			if (Math.abs(d0) > (double)1.0E-5F) {
				double d1 = 1.0D - (double)Mth.abs(f1 * 0.7F) / d0;
				f = (float)((double)f * d1);
				f2 = (float)((double)f2 * d1);
				d0 = Mth.sqrt(f * f + f2 * f2);
				double d2 = Mth.sqrt(f * f + f2 * f2 + f1 * f1);
				float f3 = getYRot();
				float f4 = (float)Mth.atan2(f2, f);
				float f5 = Mth.wrapDegrees(getYRot() + 90.0F);
				float f6 = Mth.wrapDegrees(f4 * (180F / (float)Math.PI));
				setYRot(Mth.approachDegrees(f5, f6, 4.0F) - 90.0F);
				yBodyRot = getYRot();
				if (Mth.degreesDifferenceAbs(f3, getYRot()) < 3.0F) {
					speed = Mth.approach(speed, 1.8F, 0.005F * (1.8F / speed));
				} else {
					speed = Mth.approach(speed, 0.2F, 0.025F);
				}

				float f7 = (float)(-(Mth.atan2(-f1, d0) * (double)(180F / (float)Math.PI)));
				setXRot(f7);
				float f8 = getYRot() + 90.0F;
				double d3 = (double)(speed * Mth.cos(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f / d2);
				double d4 = (double)(speed * Mth.sin(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f2 / d2);
				double d5 = (double)(speed * Mth.sin(f7 * ((float)Math.PI / 180F))) * Math.abs((double)f1 / d2);
				Vec3 vec3 = getDeltaMovement();
				setDeltaMovement(vec3.add((new Vec3(d3, d5, d4)).subtract(vec3).scale(0.2D)));
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
			LivingEntity livingentity = getTarget();
			if (livingentity == null) {
				return false;
			} else if (!livingentity.isAlive()) {
				return false;
			} else if (!(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative()) {
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
			LivingEntity livingentity = getTarget();
			if (livingentity != null) {
				moveTargetPoint = new Vec3(livingentity.getX(), livingentity.getY(0.5D), livingentity.getZ());
				if (getBoundingBox().inflate(0.2F).intersects(livingentity.getBoundingBox())) {
					doHurtTarget(livingentity);
					attackPhase = LavaRevenantEntity.AttackPhase.CIRCLE;
					if (!isSilent()) {
						PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockPosition())), new LavaRevenantEntityPacketHandler(blockPosition()));
					}
				} else if (horizontalCollision || hurtTime > 0) {
					attackPhase = LavaRevenantEntity.AttackPhase.CIRCLE;
				}
			}
		}
	}

	public record LavaRevenantEntityPacketHandler(BlockPos blockPos) {

		/**
		 * Constructor for LavaRevenantEntityPacketHandler.
		 *
		 * @param blockPos the <code>BlockPos</code> the packet came from
		 */
		public LavaRevenantEntityPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>LavaRevenantEntityPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(LavaRevenantEntityPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBlockPos(msg.blockPos);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return LavaRevenantEntityPacketHandler
		 */
		public static LavaRevenantEntityPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new LavaRevenantEntityPacketHandler(packetBuffer.readBlockPos());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param msg             the <code>LavaRevenantEntityPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(LavaRevenantEntityPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the client, when a packet is received
		 *
		 * @param msg the <code>LavaRevenantEntityPacketHandler</code> message being sent
		 */
		@OnlyIn(Dist.CLIENT)
		private static void handleOnClient(LavaRevenantEntityPacketHandler msg) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				minecraft.level.playLocalSound(msg.blockPos.getX(), msg.blockPos.getY(), msg.blockPos.getZ(), DeferredRegistryHandler.LAVA_REVENANT_BITE.get(), SoundSource.HOSTILE, 0.3F, GeneralUtilities.getRandomNumber(0.0f, 1.0f) * 0.1F + 0.9F, false);
			}
		}
	}
}