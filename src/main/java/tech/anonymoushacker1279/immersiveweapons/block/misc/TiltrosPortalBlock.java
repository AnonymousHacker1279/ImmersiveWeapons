package tech.anonymoushacker1279.immersiveweapons.block.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.HitResult;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;

// TODO: reimplement when Custom Portals API is updated
public class TiltrosPortalBlock extends Block {

	public TiltrosPortalBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
		return ItemStack.EMPTY;
	}

	@Override
	public boolean canBeReplaced(BlockState state, Fluid fluid) {
		return false;
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextInt(50) == 0) {
			level.playLocalSound(
					pos.getX() + 0.5d,
					pos.getY() + 0.5d,
					pos.getZ() + 0.5d,
					SoundEventRegistry.TILTROS_PORTAL_WHOOSH.get(),
					SoundSource.BLOCKS,
					0.5F,
					random.nextFloat() * 0.4F + 0.8F,
					false
			);
		}

		for (int i = 0; i < 4; i++) {
			double dX = pos.getX() + random.nextDouble();
			double dY = pos.getY() + random.nextDouble();
			double dZ = pos.getZ() + random.nextDouble();
			double sX = (random.nextFloat() - 0.5d) * 0.5d;
			double sY = (random.nextFloat() - 0.5d) * 0.5d;
			double sZ = (random.nextFloat() - 0.5d) * 0.5d;
			int mod = random.nextInt(2) * 2 - 1;
			if (!level.getBlockState(pos.west()).is(this) && !level.getBlockState(pos.east()).is(this)) {
				dX = pos.getX() + 0.5f + 0.25f * mod;
				sX = random.nextFloat() * 2.0f * mod;
			} else {
				dZ = pos.getZ() + 0.5f + 0.25f * mod;
				sZ = random.nextFloat() * 2.0f * mod;
			}

			level.addParticle(ParticleTypesRegistry.TILTROS_PORTAL_PARTICLE.get(), dX, dY, dZ, sX, sY, sZ);
		}
	}
}