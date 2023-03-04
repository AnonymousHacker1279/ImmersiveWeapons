package tech.anonymoushacker1279.immersiveweapons.block.decoration.skull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tech.anonymoushacker1279.immersiveweapons.blockentity.CustomSkullBlockEntity;

public class CustomWallSkullBlock extends WallSkullBlock {

	public CustomWallSkullBlock(CustomSkullTypes type, Properties properties) {
		super(type, properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CustomSkullBlockEntity(pos, state);
	}
}