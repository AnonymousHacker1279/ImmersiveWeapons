package com.anonymoushacker1279.immersiveweapons.entity.monster;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DecayedSoldierEntity extends AbstractDecayedSoldierEntity {

	public DecayedSoldierEntity(EntityType<? extends DecayedSoldierEntity> p_i50194_1_, World p_i50194_2_) {
		super(p_i50194_1_, p_i50194_2_);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_ZOMBIE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ZOMBIE_DEATH;
	}

	@Override
	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}

	@Override
	protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
		super.dropSpecialItems(source, looting, recentlyHitIn);
		int lootingModifier = looting * 2;
		if (lootingModifier >= 75) {
			this.entityDropItem(DeferredRegistryHandler.FLINTLOCK_PISTOL.get());
		} else {
			int i = GeneralUtilities.getRandomNumber(1, 75 - lootingModifier);
			if (i == 1) {
				this.entityDropItem(DeferredRegistryHandler.FLINTLOCK_PISTOL.get());
			}
		}
	}
}