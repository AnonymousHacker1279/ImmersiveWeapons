package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.entity.GrantAdvancementOnDiscovery;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class StarmiteEntity extends Endermite implements GrantAdvancementOnDiscovery {

	public StarmiteEntity(EntityType<? extends StarmiteEntity> entityType, Level level) {
		super(entityType, level);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return DeferredRegistryHandler.STARMITE_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return DeferredRegistryHandler.STARMITE_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return DeferredRegistryHandler.STARMITE_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pPos, BlockState pBlock) {
		playSound(DeferredRegistryHandler.STARMITE_STEP.get(), 0.15F, 1.0F);
	}

	@Override
	public void aiStep() {
		super.aiStep();
		checkForDiscovery(this);
	}
}