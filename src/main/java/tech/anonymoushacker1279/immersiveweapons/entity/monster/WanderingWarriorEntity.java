package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

public class WanderingWarriorEntity extends AbstractWanderingWarriorEntity {

	private boolean isBerserk = false;

	public WanderingWarriorEntity(EntityType<? extends WanderingWarriorEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEventRegistry.WANDERING_WARRIOR_AMBIENT.get();
	}

	@Override
	public int getAmbientSoundInterval() {
		return getRandom().nextIntBetweenInclusive(240, 1600);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEventRegistry.WANDERING_WARRIOR_HURT.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEventRegistry.WANDERING_WARRIOR_DEATH.get();
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
		// 10% chance to spawn as berserk, increasing with difficulty
		float berserkChance = 0.1f + (0.66f * difficulty.getSpecialMultiplier());

		if (random.nextFloat() <= berserkChance) {
			isBerserk = true;

			// Double health
			getAttribute(Attributes.MAX_HEALTH).setBaseValue(getAttribute(Attributes.MAX_HEALTH).getBaseValue() * 2);
			heal(getMaxHealth());

			// Increase movement speed and damage
			getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() * 1.15);
			getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() * 1.5);
		}

		return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);

		compound.putBoolean("isBerserk", isBerserk);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);

		isBerserk = compound.getBoolean("isBerserk");
	}

	@Override
	public void tick() {
		super.tick();

		if (isBerserk && level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
					ParticleTypes.DRAGON_BREATH,
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
	}
}