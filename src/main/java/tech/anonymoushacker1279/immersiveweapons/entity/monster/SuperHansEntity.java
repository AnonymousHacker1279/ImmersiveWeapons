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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;

public class SuperHansEntity extends HansEntity {

	public final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(getDisplayName(), BossBarColor.PURPLE,
			BossBarOverlay.PROGRESS)).setDarkenScreen(true);

	public SuperHansEntity(EntityType<? extends HansEntity> entityType, Level level) {
		super(entityType, level);
		isImmuneToProjectiles = true;
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ARMOR, 25.0D)
				.add(Attributes.MAX_HEALTH, 150.0D)
				.add(Attributes.ATTACK_DAMAGE, 10.0D);
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

		return super.hurt(source, amount);
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
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

		return super.doHurtTarget(entity);
	}

	@Override
	public void tick() {
		super.tick();

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
	}
}