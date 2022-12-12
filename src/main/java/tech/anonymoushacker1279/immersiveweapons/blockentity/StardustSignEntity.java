package tech.anonymoushacker1279.immersiveweapons.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.init.BlockEntityRegistry;

public class StardustSignEntity extends SignBlockEntity {

	public StardustSignEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<StardustSignEntity> getType() {
		return BlockEntityRegistry.STARDUST_SIGN_ENTITY.get();
	}
}