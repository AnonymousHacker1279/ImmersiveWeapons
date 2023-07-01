package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

public class CustomHangingSignBlockEntity extends HangingSignBlockEntity {

	public CustomHangingSignBlockEntity(BlockPos pos, BlockState blockState) {
		super(pos, blockState);
	}

	@Override
	public BlockEntityType<? extends CustomHangingSignBlockEntity> getType() {
		return BlockEntityRegistry.CUSTOM_HANGING_SIGN_ENTITY.get();
	}
}