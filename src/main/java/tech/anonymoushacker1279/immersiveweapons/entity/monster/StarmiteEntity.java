package tech.anonymoushacker1279.immersiveweapons.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class StarmiteEntity extends Endermite {

	public StarmiteEntity(EntityType<? extends StarmiteEntity> entityType, Level level) {
		super(entityType, level);
	}

	// TODO: Add custom sounds

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENDERMITE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource pDamageSource) {
		return SoundEvents.ENDERMITE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENDERMITE_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pPos, BlockState pBlock) {
		playSound(SoundEvents.ENDERMITE_STEP, 0.15F, 1.0F);
	}
}