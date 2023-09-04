package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.*;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;

public class SuperHansEntity extends HansEntity {

	public final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(getDisplayName(), BossBarColor.PURPLE,
			BossBarOverlay.PROGRESS)).setDarkenScreen(true);

	public SuperHansEntity(EntityType<? extends HansEntity> entityType, Level level) {
		super(entityType, level);
		isImmuneToProjectiles = false;
		meleeAttackGoal = new SuperHansMeleeAttackGoal(this, 1.2D, false);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 25.0D)
				.add(Attributes.MAX_HEALTH, 150.0D)
				.add(Attributes.ATTACK_DAMAGE, 10.0D);
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
			case NORMAL -> xpReward = 150;
			case HARD -> xpReward = 225;
			default -> xpReward = 75;
		}

		return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
	}

	@Override
	public float getVoicePitch() {
		return super.getVoicePitch() * 0.25f;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		bossEvent.setProgress(getHealth() / getMaxHealth());

		// Damage reduction from ranged projectiles
		if (source.is(DamageTypeTags.IS_PROJECTILE) || source.getDirectEntity() instanceof AbstractArrow) {
			if (getHealth() / getMaxHealth() <= 0.5f) {
				amount *= Math.max(getHealth() / getMaxHealth(), 0.2f);
			} else {
				amount *= 0.75f;
			}
		}

		// Add strength effect if health is low
		if (getHealth() / getMaxHealth() <= 0.25f) {
			addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300, 1));
		}

		return super.hurt(source, amount);
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
		super.tick();

		if (tickCount % 8 == 0) {
			if (level() instanceof ServerLevel serverLevel) {
				if (getHealth() <= getMaxHealth() * 0.5f) {
					spawnParticles(serverLevel, ParticleTypes.GLOW);
					return;
				}
				if (getHealth() <= getMaxHealth() * 0.75f) {
					spawnParticles(serverLevel, ParticleTypes.SMOKE);
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
						entity.knockback(0.5f, entity.getX() - hans.getX(), entity.getZ() - hans.getZ());
					}
				}

				cooldown = 400;
			}
		}

		@Override
		public void stop() {
			cooldown = 400;
		}
	}
}