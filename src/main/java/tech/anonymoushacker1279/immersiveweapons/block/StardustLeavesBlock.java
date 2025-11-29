package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

public class StardustLeavesBlock extends TintedParticleLeavesBlock {

	public StardustLeavesBlock(Properties properties) {
		super(0.15f, properties);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);
		if (level.getBlockState(pos.below()).isAir()) {
			if (random.nextFloat() <= 0.025f) {
				level.addParticle(ParticleTypesRegistry.STARDUST_LEAVES_PARTICLE.get(),
						pos.getX() + 0.5D + (0.1D * random.nextGaussian()),
						pos.getY() + (0.1D * random.nextGaussian()),
						pos.getZ() + 0.5D + (0.1D * random.nextGaussian()),
						0.02D * random.nextGaussian(),
						0.03D * random.nextGaussian(),
						0.02D * random.nextGaussian());
			}
		}
	}
}