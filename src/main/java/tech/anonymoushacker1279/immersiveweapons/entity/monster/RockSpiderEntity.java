package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent.Applicable;
import net.minecraftforge.eventbus.api.Event.Result;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;


public class RockSpiderEntity extends Monster implements GrantAdvancementOnDiscovery {

	private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(RockSpiderEntity.class,
			EntityDataSerializers.BYTE);

	public RockSpiderEntity(EntityType<? extends RockSpiderEntity> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 4.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.35F)
				.add(Attributes.ATTACK_DAMAGE, 2.0D)
				.add(Attributes.ARMOR, 2.0D);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FloatGoal(this));
		goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
		goalSelector.addGoal(4, new RockSpiderAttackGoal(this));
		goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new HurtByTargetGoal(this));
		targetSelector.addGoal(2, new RockSpiderTargetGoal<>(this, Player.class));
		targetSelector.addGoal(3, new RockSpiderTargetGoal<>(this, IronGolem.class));
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding this one.
	 */
	@Override
	public double getPassengersRidingOffset() {
		return getBbHeight() * 0.5F;
	}

	@Override
	protected PathNavigation createNavigation(Level pLevel) {
		return new WallClimberNavigation(this, pLevel);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DATA_FLAGS_ID, (byte) 0);
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void tick() {
		super.tick();
		if (!level().isClientSide) {
			setClimbing(horizontalCollision);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.SPIDER_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEvents.SPIDER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.SPIDER_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pPos, BlockState pBlock) {
		playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
	}

	/**
	 * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or
	 * for AI reasons)
	 */
	@Override
	public boolean onClimbable() {
		return isClimbing();
	}

	@Override
	public void makeStuckInBlock(BlockState pState, Vec3 pMotionMultiplier) {
		if (!pState.is(Blocks.COBWEB)) {
			super.makeStuckInBlock(pState, pMotionMultiplier);
		}

	}

	@Override
	public MobType getMobType() {
		return MobType.ARTHROPOD;
	}

	@Override
	public boolean canBeAffected(MobEffectInstance potionEffect) {
		if (potionEffect.getEffect() == MobEffects.POISON) {
			Applicable event = new Applicable(this, potionEffect);
			MinecraftForge.EVENT_BUS.post(event);
			return event.getResult() == Result.ALLOW;
		}
		return super.canBeAffected(potionEffect);
	}

	/**
	 * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
	 * setBesideClimbableBlock.
	 */
	public boolean isClimbing() {
		return (entityData.get(DATA_FLAGS_ID) & 1) != 0;
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	 * false.
	 */
	public void setClimbing(boolean pClimbing) {
		byte b0 = entityData.get(DATA_FLAGS_ID);
		if (pClimbing) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 = (byte) (b0 & -2);
		}

		entityData.set(DATA_FLAGS_ID, b0);
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
	                                    MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData,
	                                    @Nullable CompoundTag pDataTag) {

		pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
		if (pSpawnData == null) {
			pSpawnData = new RockSpiderEffectsGroupData();
			if (pLevel.getDifficulty() == Difficulty.HARD
					&& pLevel.getRandom().nextFloat() < 0.1F * pDifficulty.getSpecialMultiplier()) {

				((RockSpiderEffectsGroupData) pSpawnData).setRandomEffect(random);
			}
		}

		if (pSpawnData instanceof RockSpiderEffectsGroupData) {
			MobEffect mobEffect = ((RockSpiderEffectsGroupData) pSpawnData).effect;
			if (mobEffect != null) {
				addEffect(new MobEffectInstance(mobEffect, Integer.MAX_VALUE));
			}
		}

		return pSpawnData;
	}

	@Override
	protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
		return 0.15F;
	}

	@Override
	public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
		boolean notInWater = pLevel.getBlockState(blockPosition().below()).getFluidState().isEmpty();
		boolean onGround = !pLevel.getBlockState(blockPosition().below()).isAir();

		return notInWater && onGround;
	}

	static class RockSpiderAttackGoal extends MeleeAttackGoal {
		public RockSpiderAttackGoal(RockSpiderEntity entity) {
			super(entity, 1.0D, true);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			return super.canUse() && !mob.isVehicle();
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		@Override
		public boolean canContinueToUse() {
			float f = mob.getLightLevelDependentMagicValue();
			if (f >= 0.5F && mob.getRandom().nextInt(100) == 0) {
				mob.setTarget(null);
				return false;
			} else {
				return super.canContinueToUse();
			}
		}

		@Override
		protected double getAttackReachSqr(LivingEntity pAttackTarget) {
			return 4.0F + pAttackTarget.getBbWidth();
		}
	}

	public static class RockSpiderEffectsGroupData implements SpawnGroupData {
		@Nullable
		public MobEffect effect;

		public void setRandomEffect(RandomSource pRand) {
			int random = pRand.nextInt(5);
			if (random <= 1) {
				effect = MobEffects.MOVEMENT_SPEED;
			} else if (random == 2) {
				effect = MobEffects.DAMAGE_BOOST;
			} else if (random == 3) {
				effect = MobEffects.REGENERATION;
			} else {
				effect = MobEffects.INVISIBILITY;
			}

		}
	}

	static class RockSpiderTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
		public RockSpiderTargetGoal(RockSpiderEntity entity, Class<T> tClass) {
			super(entity, tClass, true);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		@Override
		public boolean canUse() {
			float brightness = mob.getLightLevelDependentMagicValue();
			return !(brightness >= 0.5F) && super.canUse();
		}
	}
}