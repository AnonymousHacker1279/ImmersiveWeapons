package tech.anonymoushacker1279.immersiveweapons.entity.ambient;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.client.particle.wisp.WispParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.SplineFloatGoal;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.SplineFloatGoalConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.WispOrbitEntityGoal;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.List;

public class WispEntity extends AmbientCreature implements GrantAdvancementOnDiscovery {

	private static final EntityDataAccessor<Integer> WISP_TYPE = SynchedEntityData.defineId(WispEntity.class, EntityDataSerializers.INT);
	@Nullable
	private LivingEntity targetEntity;

	public WispEntity(EntityType<? extends AmbientCreature> entityType, Level level) {
		super(entityType, level);
	}

	private WispEntity(Level level, BlockPos pos, int wispType) {
		this(EntityRegistry.WISP_ENTITY.get(), level);
		setPos(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f);
		entityData.set(WISP_TYPE, wispType);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 2.0D)
				.add(Attributes.FLYING_SPEED, 0.02D)
				.add(Attributes.GRAVITY, 0.0D);
	}

	public static WispEntity create(Level level, BlockPos pos, int wispType) {
		WispEntity wisp = new WispEntity(level, pos, wispType);

		if (!level.isClientSide() && level instanceof ServerLevelAccessor accessor) {
			wisp.finalizeSpawn(accessor, accessor.getCurrentDifficultyAt(pos), EntitySpawnReason.SPAWN_ITEM_USE, null);
		}

		level.addFreshEntity(wisp);

		return wisp;
	}

	public static boolean checkSpawnRules(EntityType<WispEntity> entityType, ServerLevelAccessor accessor, EntitySpawnReason reason, BlockPos blockPos, RandomSource random) {
		return true;
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new WispOrbitEntityGoal(this));
		goalSelector.addGoal(2, new SplineFloatGoal(this,
				SplineFloatGoalConfig.wisp(),
				mob -> mob instanceof WispEntity wispEntity && wispEntity.getTargetEntity() == null));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(WISP_TYPE, 0);
	}

	@Override
	public void tick() {
		super.tick();

		if (level().isClientSide()) {
			for (int i = 0; i < 3; i++) {
				level().addParticle(
						new WispParticleOptions(getColorByType(getWispType())),
						true,
						true,
						getX() + (random.nextDouble() - 0.5d) * 0.5d,
						getY() + random.nextDouble(),
						getZ() + (random.nextDouble() - 0.5d) * 0.5d,
						0, 0, 0
				);
			}

			level().addParticle(
					ParticleTypes.ELECTRIC_SPARK,
					getX() + (random.nextDouble() - 0.5d) * 0.5d,
					getY() + random.nextDouble(),
					getZ() + (random.nextDouble() - 0.5d) * 0.5d,
					0, 0, 0
			);
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();

		if (tickCount % 40 == 0) {
			scanForNearbyEntities();
			checkForDiscovery(this);
		}
	}

	/// Scans for nearby entities within range and sets a target if conditions are met.
	private void scanForNearbyEntities() {
		if (targetEntity != null && !targetEntity.isAlive()) {
			targetEntity = null;
		}

		// If already has a target, verify it's still valid
		if (targetEntity != null) {
			double distance = distanceTo(targetEntity);
			if (distance > 12.0d) {
				targetEntity = null;
			} else {
				// Check if there are now too many wisps of the same type around this entity
				int wispCount = countWispsAroundEntity(targetEntity, getWispType());
				if (wispCount > 3) {
					targetEntity = null;
				}
			}
		}

		// Find a new target if needed
		if (targetEntity == null) {
			List<LivingEntity> nearbyEntities = level().getEntities(this,
							getBoundingBox().inflate(12.0d),
							entity -> entity instanceof LivingEntity && (!(entity instanceof WispEntity)))
					.stream().map(entity -> (LivingEntity) entity)
					.toList();

			for (LivingEntity entity : nearbyEntities) {
				if (entity.isAlive()) {
					// Check if there are already 3 wisps of the same type around this entity
					int wispCount = countWispsAroundEntity(entity, getWispType());
					if (wispCount < 3) {
						targetEntity = entity;
						break;
					}
				}
			}
		}
	}

	/// Counts the number of wisps of a specific type around an entity.
	private int countWispsAroundEntity(Entity entity, int wispType) {
		List<WispEntity> nearbyWisps = level()
				.getEntitiesOfClass(WispEntity.class,
						entity.getBoundingBox().inflate(12.0),
						wisp -> wisp.getWispType() == wispType);

		return nearbyWisps.size();
	}

	public int getWispType() {
		return entityData.get(WISP_TYPE);
	}

	@Nullable
	public LivingEntity getTargetEntity() {
		return targetEntity;
	}

	public void attemptApplyEffectToTarget() {
		if (targetEntity != null && targetEntity.isAlive()) {
			targetEntity.addEffect(getEffectByType(getWispType(), targetEntity));
		}
	}

	private int getColorByType(int type) {
		return switch (type) {
			case 0 -> 0x800080; // Purple
			case 1 -> 0xFF0000; // Red
			case 2 -> 0x00FF00; // Green
			case 3 -> 0x0000FF; // Blue
			case 4 -> 0xFFA500; // Orange
			default -> 0xFFFFFF; // White
		};
	}

	private MobEffectInstance getEffectByType(int type, Entity target) {
		int wisps = countWispsAroundEntity(target, type);
		int purpleWisps = countWispsAroundEntity(target, 0);
		int level = Math.min(wisps, 3);
		int duration = 100 + (wisps * 40);

		if (type != 0 && purpleWisps > 0) {
			level = level + purpleWisps;
			duration = (int) (duration * (1.0f + (purpleWisps * 0.25f)));
		}

		return switch (type) {
			case 0 -> new MobEffectInstance(MobEffects.LUCK, duration, level - 1, true, false);
			case 1 ->
					new MobEffectInstance(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT, duration, level - 1, true, false);
			case 2 -> new MobEffectInstance(MobEffects.REGENERATION, duration, level - 1, true, false);
			case 3 -> new MobEffectInstance(MobEffects.SPEED, duration, level - 1, true, false);
			case 4 -> new MobEffectInstance(MobEffects.MINING_FATIGUE, duration, level - 1, true, false);
			default -> new MobEffectInstance(MobEffects.INVISIBILITY, duration, level - 1, true, false);
		};
	}

	@Nullable
	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
		if (spawnReason != EntitySpawnReason.SPAWN_ITEM_USE) {
			entityData.set(WISP_TYPE, random.nextInt(5));
		}

		return super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
	}

	@Override
	protected InteractionResult mobInteract(Player player, InteractionHand hand) {
		if (player.getItemInHand(hand).is(Items.GLASS_BOTTLE)) {
			if (!level().isClientSide()) {
				if (!player.isCreative()) {
					player.getItemInHand(hand).shrink(1);
				}

				ItemStack stack = switch (getWispType()) {
					case 0 -> new ItemStack(ItemRegistry.WISP_IN_A_BOTTLE_PURPLE.get());
					case 1 -> new ItemStack(ItemRegistry.WISP_IN_A_BOTTLE_RED.get());
					case 2 -> new ItemStack(ItemRegistry.WISP_IN_A_BOTTLE_GREEN.get());
					case 3 -> new ItemStack(ItemRegistry.WISP_IN_A_BOTTLE_BLUE.get());
					case 4 -> new ItemStack(ItemRegistry.WISP_IN_A_BOTTLE_ORANGE.get());
					default -> throw new IllegalStateException("Unexpected value: " + getWispType());
				};
				player.addItem(stack);
				discard();
			}
			return InteractionResult.CONSUME;
		}

		return InteractionResult.PASS;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	protected void doPush(Entity entity) {
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
	}

	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.AMETHYST_BLOCK_CHIME;
	}

	@Override
	protected void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		entityData.set(WISP_TYPE, valueInput.getIntOr("WispType", 0));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		valueOutput.putInt("WispType", entityData.get(WISP_TYPE));
	}
}