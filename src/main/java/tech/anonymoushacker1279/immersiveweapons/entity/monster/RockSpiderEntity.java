package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;


public class RockSpiderEntity extends Spider implements GrantAdvancementOnDiscovery {

	public RockSpiderEntity(EntityType<? extends RockSpiderEntity> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 4.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.35F)
				.add(Attributes.ATTACK_DAMAGE, 2.0D)
				.add(Attributes.ARMOR, 2.0D)
				.add(Attributes.STEP_HEIGHT, -0.3d);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	@Override
	protected EntityDimensions getDefaultDimensions(Pose pose) {
		EntityDimensions dimensions = super.getDefaultDimensions(pose);
		return dimensions.withEyeHeight(0.15f);
	}
}