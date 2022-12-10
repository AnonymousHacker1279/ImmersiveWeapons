package tech.anonymoushacker1279.immersiveweapons.block.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import tech.anonymoushacker1279.immersiveweapons.blockentity.StardustSignEntity;

public class StardustWallSignBlock extends WallSignBlock {

	public StardustWallSignBlock(Properties properties, WoodType type) {
		super(properties, type);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new StardustSignEntity(pos, state);
	}
}