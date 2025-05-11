package tech.anonymoushacker1279.immersiveweapons.entity.animal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import javax.annotation.Nullable;

public class StarWolfEntity extends Wolf implements GrantAdvancementOnDiscovery {

	public StarWolfEntity(EntityType<? extends Wolf> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.35F)
				.add(Attributes.MAX_HEALTH, 12.0D)
				.add(Attributes.ATTACK_DAMAGE, 5.0D);
	}

	@Override
	public void setTame(boolean tamed, boolean applySideEffects) {
		super.setTame(tamed, applySideEffects);

		if (tamed) {
			getAttribute(Attributes.MAX_HEALTH).setBaseValue(44.0D);
			setHealth(44.0F);
		} else {
			getAttribute(Attributes.MAX_HEALTH).setBaseValue(12.0D);
		}

		getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0D);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
		if (damageSource.is(DamageTypeTags.IS_FALL)) {
			return false;
		}

		return super.hurtServer(level, damageSource, amount);
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 2;
	}

	@Override
	public boolean isMaxGroupSizeReached(int size) {
		return size >= 2;
	}

	@Nullable
	@Override
	public Wolf getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		Wolf wolf = EntityRegistry.STAR_WOLF_ENTITY.get().create(level, EntitySpawnReason.BREEDING);
		if (wolf != null && getOwner() != null) {
			wolf.setOwner(getOwner());
			wolf.setTame(true, true);
		}

		return wolf;
	}
}