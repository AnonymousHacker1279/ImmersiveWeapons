package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class StardustLeavesBlock extends TintedParticleLeavesBlock {

	public StardustLeavesBlock(Properties properties) {
		super(0.15f, properties);
	}

	// TODO: remove after testing the vanilla leaf particles
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
		if (level.getBlockState(pos.below()).isAir()) {
			if (randomSource.nextFloat() <= 0.15f) {
				level.addParticle(ParticleTypesRegistry.STARDUST_LEAVES_PARTICLE.get(),
						pos.getX() + 0.5D + (GeneralUtilities.getRandomNumber(-0.1D, 0.1D)),
						pos.getY() + (GeneralUtilities.getRandomNumber(-0.1D, 0.1D)),
						pos.getZ() + 0.5D + (GeneralUtilities.getRandomNumber(-0.1D, 0.1D)),
						(GeneralUtilities.getRandomNumber(-0.03D, 0.03D)),
						(GeneralUtilities.getRandomNumber(-0.03D, -0.01D)),
						(GeneralUtilities.getRandomNumber(-0.03D, 0.03D)));
			}
		}
	}
}