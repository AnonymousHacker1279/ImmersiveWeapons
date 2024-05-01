package tech.anonymoushacker1279.immersiveweapons.entity.animal;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import javax.annotation.Nullable;
import java.util.UUID;

public class StarWolfEntity extends Wolf implements GrantAdvancementOnDiscovery {

	public StarWolfEntity(EntityType<? extends Wolf> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MOVEMENT_SPEED, 0.35F)
				.add(Attributes.MAX_HEALTH, 12.0D)
				.add(Attributes.ATTACK_DAMAGE, 2.5D);
	}

	@Override
	public void setTame(boolean tamed, boolean applySideEffects) {
		super.setTame(tamed, applySideEffects);

		// TODO: re-evaluate attributes with the new wolf changes
		/*if (pTamed) {
			getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0D);
			setHealth(30.0F);
		} else {
			getAttribute(Attributes.MAX_HEALTH).setBaseValue(12.0D);
		}

		getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D);*/
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		// Star wolves are immune to fall damage
		if (source.is(DamageTypeTags.IS_FALL)) {
			return false;
		} else {
			return super.hurt(source, amount);
		}
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
	public Wolf getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
		Wolf wolf = EntityRegistry.STAR_WOLF_ENTITY.get().create(pLevel);
		if (wolf != null) {
			UUID ownerUUID = getOwnerUUID();
			if (ownerUUID != null) {
				wolf.setOwnerUUID(ownerUUID);
				wolf.setTame(true, true);
			}
		}

		return wolf;
	}
}