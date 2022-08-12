package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class DeathweedBlock extends FlowerBlock {

	public static final DamageSource DAMAGE_SOURCE = new DamageSource("immersiveweapons.deathweed")
			.bypassArmor().setMagic();

	public DeathweedBlock(MobEffect mobEffect, int effectDuration, Properties properties) {
		super(mobEffect, effectDuration, properties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.is(DeferredRegistryHandler.BLOOD_SAND.get());
	}

	@Override
	public PlantType getPlantType(BlockGetter level, BlockPos pos) {
		return PlantType.DESERT;
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (entity instanceof LivingEntity) {
			if (!level.isClientSide) {
				if (entity.tickCount % 8 == 0 && GeneralUtilities.getRandomNumber(0.0f, 1.0f) <= 0.65f) {
					entity.hurt(DAMAGE_SOURCE, 1.0f);
				}
			}
		}
	}
}