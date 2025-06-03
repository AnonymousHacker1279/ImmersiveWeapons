package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class StarstormCrystalBlock extends AmethystClusterBlock {

	public StarstormCrystalBlock(int size, int offset, Properties properties) {
		super(size, offset, properties);
	}

	@Override
	public @Nullable PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}
}