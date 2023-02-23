package tech.anonymoushacker1279.immersiveweapons.entity.animal;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;

public class StarWolfEntity extends Wolf {

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
	public void setTame(boolean pTamed) {
		super.setTame(pTamed);

		if (pTamed) {
			getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0D);
			setHealth(30.0F);
		} else {
			getAttribute(Attributes.MAX_HEALTH).setBaseValue(12.0D);
		}

		getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		// Star wolves are immune to fall damage
		if (source.isFall()) {
			return false;
		} else {
			return super.hurt(source, amount);
		}
	}

	@Override
	public int getMaxSpawnClusterSize() {
		return 4;
	}

	@Override
	public boolean isMaxGroupSizeReached(int size) {
		return size >= 2;
	}
}