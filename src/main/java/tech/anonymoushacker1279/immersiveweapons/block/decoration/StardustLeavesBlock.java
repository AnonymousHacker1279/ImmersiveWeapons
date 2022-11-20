package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class StardustLeavesBlock extends LeavesBlock {

	public StardustLeavesBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void animateTick(@NotNull BlockState state, Level level, BlockPos pos, @NotNull RandomSource randomSource) {
		if (level.getBlockState(pos.below()).isAir()) {
			if (randomSource.nextFloat() <= 0.15f) {
				level.addParticle(DeferredRegistryHandler.STARDUST_LEAVES_PARTICLE.get(),
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