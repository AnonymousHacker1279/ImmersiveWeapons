package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

public class MoonglowBlock extends FlowerBlock {

	public MoonglowBlock(Holder<MobEffect> mobEffect, int effectDuration, Properties properties) {
		super(mobEffect, effectDuration, properties);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		level.addParticle(ParticleTypesRegistry.MOONGLOW_PARTICLE.get(),
				pos.getX() + 0.5D + (0.1D * random.nextGaussian()),
				pos.getY() + 0.7D + (0.1D * random.nextGaussian()),
				pos.getZ() + 0.5D + (0.1D * random.nextGaussian()),
				0.03D * random.nextGaussian(),
				0.03D * random.nextGaussian(),
				0.03D * random.nextGaussian());
	}
}