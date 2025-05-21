package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;

public class StormCreeperEntity extends Creeper implements GrantAdvancementOnDiscovery {

	public StormCreeperEntity(EntityType<? extends StormCreeperEntity> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.15D);
	}

	@Override
	public void tick() {
		super.tick();

		// Spawn a circle of smoke particles around the creeper every 8 ticks
		if (tickCount % 8 == 0) {
			for (int i = 0; i < 360; i += 30) {
				double x = getX() + 0.5 * Math.cos(i);
				double z = getZ() + 0.5 * Math.sin(i);
				level().addParticle(ParticleTypes.CLOUD, x, getY() + 0.5, z, 0, 0, 0);
			}
		}
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	@Override
	protected int getBaseExperienceReward(ServerLevel level) {
		xpReward = 5 + level.random.nextInt(5);
		return super.getBaseExperienceReward(level);
	}
}