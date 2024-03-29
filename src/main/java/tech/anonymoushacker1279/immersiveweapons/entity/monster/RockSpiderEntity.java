package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.common.NeoForgeMod;
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
				.add(NeoForgeMod.STEP_HEIGHT.value(), -0.3d);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}

	@Override
	protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
		return 0.15F;
	}

	@Override
	public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
		boolean notInWater = pLevel.getBlockState(blockPosition().below()).getFluidState().isEmpty();
		boolean onGround = !pLevel.getBlockState(blockPosition().below()).isAir();

		return notInWater && onGround;
	}
}