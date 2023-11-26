package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

public class StarForgeBlockEntity extends AbstractInventoryBlockEntity {

	public StarForgeBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(BlockEntityRegistry.STAR_FORGE_BLOCK_ENTITY.get(), blockPos, blockState);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new StarForgeBlockEntity(pos, state);
	}
}