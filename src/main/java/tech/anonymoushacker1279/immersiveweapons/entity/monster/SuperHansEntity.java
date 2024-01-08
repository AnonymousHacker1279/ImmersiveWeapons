package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.*;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.AttackerTracker;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;

import java.util.*;

public class SuperHansEntity extends HansEntity implements AttackerTracker {

	public final ServerBossEvent bossEvent = new ServerBossEvent(getDisplayName(), BossBarColor.PURPLE,
			BossBarOverlay.PROGRESS);

	public static final ResourceKey<Structure> championTowerKey = ResourceKey.create(Registries.STRUCTURE,
			new ResourceLocation(ImmersiveWeapons.MOD_ID, "champion_tower"));
	private boolean spawnedInChampionTower = false;
	@Nullable
	private BoundingBox championTowerBounds;
	private boolean towerMinibossesAlive = false;

	List<Entity> attackingEntities = new ArrayList<>(5);

	public SuperHansEntity(EntityType<? extends HansEntity> entityType, Level level) {
		super(entityType, level);
		isImmuneToProjectiles = false;
		meleeAttackGoal = new SuperHansMeleeAttackGoal(this, 1.2D, false);

		setPersistenceRequired();
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 25.0D)
				.add(Attributes.MAX_HEALTH, 150.0D)
				.add(Attributes.ATTACK_DAMAGE, 10.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
				.add(Attributes.FOLLOW_RANGE, 64.0D);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();

		goalSelector.addGoal(1, new SuperHansGroundSmashGoal(this));
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
		bossEvent.setProgress(1.0f);

		switch (difficulty.getDifficulty()) {
			case NORMAL -> {
				xpReward = 225;
				getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.32D);
			}
			case HARD -> {
				xpReward = 500;
				getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.35D);
				getAttribute(Attributes.ARMOR).setBaseValue(30.0D);
				getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(15.0D);
			}
			default -> xpReward = 150;
		}

		return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
	}

	@Override
	public float getVoicePitch() {
		return super.getVoicePitch() * 0.25f;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (!source.is(DamageTypes.GENERIC_KILL) && towerMinibossesAlive) {
			if (source.getEntity() instanceof Player player) {
				player.displayClientMessage(Component.translatable("immersiveweapons.entity.super_hans.tower_minibosses_alive")
						.withStyle(ChatFormatting.RED), true);
			}

			return false;
		}

		// Damage reduction from ranged projectiles
		if (source.is(DamageTypeTags.IS_PROJECTILE) || source.getDirectEntity() instanceof AbstractArrow) {
			if (getHealth() / getMaxHealth() <= 0.5f) {
				amount *= Math.max(getHealth() / getMaxHealth(), 0.2f);
			} else {
				amount *= 0.75f;
			}
		}

		// Reduce damage as the number of players attacking increase
		float playerCountReductionFactor;
		switch (getAttackingEntities()) {
			case 0, 1 -> playerCountReductionFactor = 1.0f;
			case 2 -> playerCountReductionFactor = 0.90f;
			case 3 -> playerCountReductionFactor = 0.80f;
			case 4 -> playerCountReductionFactor = 0.75f;
			default -> playerCountReductionFactor = 0.70f;
		}

		amount *= playerCountReductionFactor;

		// Add strength effect if health is low
		if (getHealth() / getMaxHealth() <= 0.25f) {
			addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 1));
		}

		boolean doesHurt = super.hurt(source, amount);

		if (doesHurt) {
			bossEvent.setProgress(getHealth() / getMaxHealth());

			if (source.getEntity() != null) {
				attackedByEntity(source.getEntity(), attackingEntities);
			}
		}

		return doesHurt;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		boolean hurtTarget = super.doHurtTarget(entity);

		if (hurtTarget) {
			if (entity instanceof LivingEntity livingEntity) {
				if (getHealth() <= getMaxHealth() * 0.75f) {
					// Make the entity bleed
					int duration = (int) Math.min((20 * Math.pow((getHealth() / getMaxHealth()), -1)) * 4, 900);
					int amplifier = (int) Math.min((Math.pow((getHealth() / getMaxHealth()), -1)), 5);
					livingEntity.addEffect(
							new MobEffectInstance(EffectRegistry.BLEEDING_EFFECT.get(), duration, amplifier, false, true)
					);
				}
			}
		}

		return hurtTarget;
	}

	@Override
	public void tick() {
		// This has to run before the super call otherwise firstTick is always false
		if (firstTick) {
			if (level() instanceof ServerLevel serverLevel) {
				StructureStart structureStart = serverLevel.structureManager().getStructureWithPieceAt(blockPosition(), championTowerKey);
				spawnedInChampionTower = structureStart.isValid();
				if (spawnedInChampionTower) {
					championTowerBounds = structureStart.getBoundingBox();
				}
			}
		}

		super.tick();

		if (tickCount % 8 == 0) {
			if (level() instanceof ServerLevel serverLevel) {
				if (towerMinibossesAlive) {
					spawnParticles(serverLevel, ParticleTypes.REVERSE_PORTAL);
					return;
				} else {
					AttributeInstance kbr = getAttribute(Attributes.KNOCKBACK_RESISTANCE);
					if (kbr != null && kbr.getValue() == 1.0D) {
						kbr.setBaseValue(0.5D);
					}
				}

				if (getHealth() <= getMaxHealth() * 0.5f) {
					spawnParticles(serverLevel, ParticleTypes.GLOW);
					return;
				}
				if (getHealth() <= getMaxHealth() * 0.75f) {
					spawnParticles(serverLevel, ParticleTypes.SMOKE);
				}
			}

			bossEvent.setProgress(getHealth() / getMaxHealth());
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();

		// Check if the entity is inside a Champion Tower structure area
		if (level() instanceof ServerLevel serverLevel) {
			if (spawnedInChampionTower && championTowerBounds != null && tickCount % 40 == 0) {
				// Get the bounds of the structure, loop through all entities inside and check if there are any with a "ChampionTowerMiniboss" tag
				if (championTowerBounds.isInside(blockPosition())) {
					List<Entity> entityList = serverLevel.getEntities(this, AABB.of(championTowerBounds));
					Optional<Entity> optionalEntity = entityList
							.stream()
							.filter(Player.class::isInstance)
							.findAny();

					if (optionalEntity.isPresent()) {
						if (optionalEntity.get() instanceof Player player) {
							player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 4, true, true));
						}
					}

					// Check for any minibosses
					towerMinibossesAlive = entityList
							.stream()
							.anyMatch(entity -> entity.getTags().contains("ChampionTowerMiniboss"));
				}
			}
		}

	}

	private void spawnParticles(ServerLevel level, SimpleParticleType type) {
		level.sendParticles(
				type,
				getX() + (random.nextDouble() - 0.5) * getBbWidth(),
				getY() + random.nextDouble() * getBbHeight(),
				getZ() + (random.nextDouble() - 0.5) * getBbWidth(),
				3,
				0.0,
				0.0,
				0.0,
				0.0
		);
	}

	@Override
	public void startSeenByPlayer(ServerPlayer pPlayer) {
		super.startSeenByPlayer(pPlayer);
		bossEvent.addPlayer(pPlayer);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer pPlayer) {
		super.stopSeenByPlayer(pPlayer);
		bossEvent.removePlayer(pPlayer);
	}

	@Override
	public void setCustomName(@Nullable Component pName) {
		super.setCustomName(pName);
		bossEvent.setName(getDisplayName());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);

		if (hasCustomName()) {
			bossEvent.setName(getDisplayName());
		}

		bossEvent.setProgress(getHealth() / getMaxHealth());
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);

		pCompound.putInt("xpReward", xpReward);
	}

	@Override
	public void load(CompoundTag pCompound) {
		super.load(pCompound);
		
		xpReward = pCompound.getInt("xpReward");
	}

	public int getAttackingEntities() {
		return attackingEntities.size();
	}

	static class SuperHansMeleeAttackGoal extends MeleeAttackGoal {

		public SuperHansMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen) {
			super(mob, speedModifier, followingTargetEvenIfNotSeen);
		}

		@Override
		public void tick() {
			super.tick();

			// If the target player is blocking with a shield, switch to an axe
			if (mob.getTarget() instanceof Player player) {
				if (player.isBlocking()) {
					mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
				} else {
					mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
				}
			}
		}
	}

	static class SuperHansGroundSmashGoal extends Goal {

		private final SuperHansEntity hans;
		private int cooldown = 0;

		public SuperHansGroundSmashGoal(SuperHansEntity hans) {
			this.hans = hans;
		}

		@Override
		public boolean canUse() {
			return hans.getTarget() != null;
		}

		@Override
		public void tick() {
			if (cooldown > 0) {
				cooldown--;
				return;
			}

			if (hans.getTarget() != null && hans.getTarget().distanceTo(hans) <= 8.0D) {
				hans.setDeltaMovement(hans.getDeltaMovement().add(0, 1.0, 0));

				for (int i = 0; i < 360; i += 10) {
					double x = hans.getX() + 8 * Math.cos(i);
					double z = hans.getZ() + 8 * Math.sin(i);
					((ServerLevel) hans.level()).sendParticles(
							ParticleTypes.FLASH,
							x,
							hans.getY(),
							z,
							2,
							0.0,
							0.0,
							0.0,
							0.0
					);
				}

				hans.playSound(hans.getAmbientSound(), 1.0f, 1.25f);

				// Give blindness to nearby entities and knock them back
				for (LivingEntity entity : hans.level().getEntitiesOfClass(LivingEntity.class, hans.getBoundingBox().inflate(8.0D))) {
					if (entity != hans) {
						entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 140, 0));

						// Calculate the angle between Super Hans and the entity
						double angle = Math.atan2(entity.getZ() - hans.getZ(), entity.getX() - hans.getX()) + Math.PI;

						// Calculate the X and Z knockback directions based on the angle
						double knockbackX = Math.cos(angle);
						double knockbackZ = Math.sin(angle);

						entity.knockback(2.5f, knockbackX, knockbackZ);
						entity.hurtMarked = true;
					}
				}

				cooldown = calculateCooldown(hans.level().getDifficulty());
			}
		}

		@Override
		public void stop() {
			cooldown = 400;
		}

		private int calculateCooldown(Difficulty difficulty) {
			int healthModifier = (int) (hans.getMaxHealth() / hans.getHealth());
			int baseCooldown;
			switch (difficulty) {
				case EASY -> {
					baseCooldown = 600;
				}
				case NORMAL -> {
					baseCooldown = 400;
				}
				default -> {
					baseCooldown = 200;
				}
			}

			return Mth.clamp((baseCooldown / healthModifier), 60, 600);
		}
	}
}