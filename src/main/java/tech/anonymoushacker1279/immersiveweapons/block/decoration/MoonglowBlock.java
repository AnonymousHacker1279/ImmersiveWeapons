package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class MoonglowBlock extends FlowerBlock {

	public MoonglowBlock(MobEffect mobEffect, int effectDuration, Properties properties) {
		super(mobEffect, effectDuration, properties);
	}

	@Override
	public void animateTick(@NotNull BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
		level.addParticle(DeferredRegistryHandler.MOONGLOW_PARTICLE.get(),
				pos.getX() + 0.5D + (GeneralUtilities.getRandomNumber(-0.1D, 0.1D)),
				pos.getY() + 0.7D + (GeneralUtilities.getRandomNumber(-0.1D, 0.1D)),
				pos.getZ() + 0.5D + (GeneralUtilities.getRandomNumber(-0.1D, 0.1D)),
				0.0D + (GeneralUtilities.getRandomNumber(-0.03D, 0.03D)),
				0.0D + (GeneralUtilities.getRandomNumber(-0.01D, 0.01D)),
				0.0D + (GeneralUtilities.getRandomNumber(-0.03D, 0.03D)));
	}
}