package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.FlyAroundEntityGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.FlyRandomlyGoal;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class EvilEyeEntity extends FlyingMob implements Enemy, GrantAdvancementOnDiscovery {

	private static final EntityDataAccessor<Integer> ID_SIZE = SynchedEntityData.defineId(EvilEyeEntity.class,
			EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> SUMMONED_BY_STAFF = SynchedEntityData.defineId(EvilEyeEntity.class,
			EntityDataSerializers.BOOLEAN);

	private final List<Holder<MobEffect>> lowTierDebuffs = new ArrayList<>(5);
	private final List<Holder<MobEffect>> highTierDebuffs = new ArrayList<>(5);

	private final FlyRandomlyGoal flyRandomlyGoal = new FlyRandomlyGoal(this);

	@Nullable
	private LivingEntity targetedEntity;
	@Nullable
	private UUID targetedEntityUUID;

	private int ticksWatched;
	private float effectChance = 0.05f;
	private int effectDuration = 100;
	private int effectLevel = 0;

	public EvilEyeEntity(EntityType<? extends FlyingMob> entityType, Level level) {
		super(entityType, level);

		lowTierDebuffs.add(MobEffects.MOVEMENT_SLOWDOWN);
		lowTierDebuffs.add(MobEffects.CONFUSION);
		lowTierDebuffs.add(MobEffects.HUNGER);
		lowTierDebuffs.add(MobEffects.DIG_SLOWDOWN);
		lowTierDebuffs.add(MobEffects.GLOWING);

		highTierDebuffs.add(MobEffects.WITHER);
		highTierDebuffs.add(MobEffects.BLINDNESS);
		highTierDebuffs.add(MobEffects.POISON);
		highTierDebuffs.add(MobEffects.WEAKNESS);
		highTierDebuffs.add(MobEffects.LEVITATION);
		highTierDebuffs.add(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT);
		highTierDebuffs.add(EffectRegistry.BROKEN_ARMOR_EFFECT);
	}

	private EvilEyeEntity(Level level, Vec3 position, boolean staff) {
		this(EntityRegistry.EVIL_EYE_ENTITY.get(), level);
		setPos(position.x(), position.y(), position.z());
		entityData.set(SUMMONED_BY_STAFF, staff);
	}

	public static EvilEyeEntity create(Level level, Vec3 position, boolean staff) {
		EvilEyeEntity entity = new EvilEyeEntity(level, position, staff);

		if (!level.isClientSide) {
			entity.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(entity.blockPosition()),
					EntitySpawnReason.EVENT, null);
		}

		level.addFreshEntity(entity);

		return entity;
	}

	public void setEffectChance(float chance) {
		effectChance = chance;
	}

	public void setEffectDuration(int duration) {
		effectDuration = duration;
	}

	public void setEffectLevel(int level) {
		effectLevel = level;
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.FLYING_SPEED, 0.85D)
				.add(Attributes.ARMOR, 2.0D);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(ID_SIZE, 0);
		builder.define(SUMMONED_BY_STAFF, false);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);

		boolean summonedByStaff = entityData.get(SUMMONED_BY_STAFF);

		if (targetedEntityUUID != null) {
			targetedEntity = (LivingEntity) ((ServerLevel) level()).getEntity(targetedEntityUUID);
			targetedEntityUUID = null;
		}

		// Find players every 40 ticks if not summoned by a staff
		if (level() instanceof ServerLevel serverLevel) {
			if (!summonedByStaff && tickCount % 40 == 0) {
				// Increase distance based on difficulty and size
				int scanDistance = 16;
				int sizeModifier = getSize() * 2;
				int difficultyModifier = level().getDifficulty().getId() * 2;

				scanDistance += sizeModifier + difficultyModifier;

				Player player = level().getNearestPlayer(this, scanDistance);
				// Check for proper targeting conditions
				if (player != null) {
					boolean validTarget = TargetingConditions.forCombat().test(serverLevel, this, player);
					if (validTarget) {
						targetedEntity = player;

						// Remove the random flying goal
						goalSelector.removeGoal(flyRandomlyGoal);
					} else {
						targetedEntity = null;
					}
				}
			}

			if (targetedEntity == null) {
				goalSelector.addGoal(2, flyRandomlyGoal);
			} else {
				// Ensure the target is still valid
				boolean validTarget = !summonedByStaff
						? TargetingConditions.forCombat().test(serverLevel, this, targetedEntity)
						: TargetingConditions.forCombat().ignoreLineOfSight().test(serverLevel, this, targetedEntity);

				if (!validTarget) {
					targetedEntity = null;

					if (summonedByStaff) {
						remove(RemovalReason.DISCARDED);
					}

					return;
				}

				if (!targetedEntity.isAlive()) {
					targetedEntity = null;

					if (summonedByStaff) {
						remove(RemovalReason.DISCARDED);
					}
				}
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		// Face the targeted entity
		if (targetedEntity != null) {
			lookAt(EntityAnchorArgument.Anchor.EYES, targetedEntity.position());

			// If the entity is within 5 blocks of the entity, start increasing the ticks watched
			if (distanceToSqr(targetedEntity) < 25) {
				ticksWatched++;

				if (ticksWatched >= 100) {
					// If the entity has been watched for 100 ticks or more, there is a 5% chance every
					// 20 ticks to inflict a random debuff on the player
					if (tickCount % 20 == 0 && random.nextFloat() < effectChance) {
						// If there are at least 3 entities within an 8 block radius, inflict a high tier debuff
						if (level().getEntitiesOfClass(EvilEyeEntity.class, getBoundingBox().inflate(8), (entity) -> true).size() >= 3) {
							Holder<MobEffect> effect = highTierDebuffs.get(getRandom().nextIntBetweenInclusive(0, highTierDebuffs.size() - 1));
							targetedEntity.addEffect(new MobEffectInstance(effect, effectDuration, effectLevel));
						} else {
							Holder<MobEffect> effect = lowTierDebuffs.get(getRandom().nextIntBetweenInclusive(0, lowTierDebuffs.size() - 1));
							targetedEntity.addEffect(new MobEffectInstance(effect, effectDuration, effectLevel));
						}
					}
				}
			} else {
				ticksWatched = 0;
			}
		}
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new FlyAroundEntityGoal(this));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
	                                    EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
		setSize(getRandom().nextIntBetweenInclusive(1, 3));
		setHealth(getMaxHealth());
		xpReward = summonedByStaff() ? 0 : 3 * getSize();

		return super.finalizeSpawn(level, difficulty, reason, spawnData);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("Size", getSize());
		pCompound.putBoolean("SummonedByStaff", entityData.get(SUMMONED_BY_STAFF));
		pCompound.putUUID("Target", targetedEntity == null ? new UUID(0, 0) : targetedEntity.getUUID());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		setSize(pCompound.getInt("Size"));
		entityData.set(SUMMONED_BY_STAFF, pCompound.getBoolean("SummonedByStaff"));

		UUID uuid = pCompound.getUUID("Target");
		targetedEntityUUID = uuid.equals(new UUID(0, 0)) ? null : uuid;
	}

	@Override
	protected boolean shouldDropLoot() {
		return !entityData.get(SUMMONED_BY_STAFF);
	}

	private void updateSizeInfo() {
		refreshDimensions();
		Objects.requireNonNull(getAttribute(Attributes.MAX_HEALTH)).setBaseValue(8 * (getSize() / 2.0f));
	}

	public int getSize() {
		return entityData.get(ID_SIZE);
	}

	public void setSize(int pSize) {
		entityData.set(ID_SIZE, Mth.clamp(pSize, 0, 64));
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
		if (ID_SIZE.equals(pKey)) {
			updateSizeInfo();
		}

		super.onSyncedDataUpdated(pKey);
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		int size = getSize();
		EntityDimensions dimensions = super.getDefaultDimensions(pose);
		float scaleFactor = (dimensions.width() + 0.3F * (float) size) / dimensions.width();
		return dimensions.scale(scaleFactor);
	}

	@Override
	public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
		if (source.getEntity() instanceof LivingEntity entity && !summonedByStaff()) {
			if (entity instanceof Player player && player.isCreative()) {
				return super.hurtServer(serverLevel, source, amount);
			}

			targetedEntity = entity;

			// Remove the random flying goal if it exists
			goalSelector.removeGoal(flyRandomlyGoal);
		}

		return super.hurtServer(serverLevel, source, amount);
	}

	@Nullable
	public LivingEntity getTargetedEntity() {
		return targetedEntity;
	}

	public void setTargetedEntity(LivingEntity target) {
		targetedEntity = target;
	}

	public boolean summonedByStaff() {
		return entityData.get(SUMMONED_BY_STAFF);
	}

	public static boolean checkSpawnRules(EntityType<? extends Mob> type, LevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random) {
		boolean validSpawn = checkMobSpawnRules(type, level, reason, pos, random);
		boolean notUnderground = level.getBlockStates(new AABB(pos)
						.expandTowards(0, 25, 0))
				.allMatch((state) -> state.is(Blocks.CAVE_AIR) || state.is(Blocks.AIR));
		return validSpawn && notUnderground;
	}
}