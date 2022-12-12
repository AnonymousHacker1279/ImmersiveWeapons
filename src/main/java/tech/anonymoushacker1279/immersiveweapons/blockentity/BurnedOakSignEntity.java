package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

public class BurnedOakSignEntity extends SignBlockEntity {

	public BurnedOakSignEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<BurnedOakSignEntity> getType() {
		return BlockEntityRegistry.BURNED_OAK_SIGN_ENTITY.get();
	}
}