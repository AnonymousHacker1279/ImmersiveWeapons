package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

public class TiltrosPortalBlockEntity extends BlockEntity {

	public TiltrosPortalBlockEntity(BlockPos pos, BlockState blockState) {
		super(BlockEntityRegistry.TILTROS_PORTAL_BLOCK_ENTITY.get(), pos, blockState);
	}
}