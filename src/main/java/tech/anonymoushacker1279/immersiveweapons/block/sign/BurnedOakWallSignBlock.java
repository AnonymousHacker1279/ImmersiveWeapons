package tech.anonymoushacker1279.immersiveweapons.block.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import tech.anonymoushacker1279.immersiveweapons.blockentity.BurnedOakSignEntity;

public class BurnedOakWallSignBlock extends WallSignBlock {

	public BurnedOakWallSignBlock(Properties propertiesIn, WoodType woodTypeIn) {
		super(propertiesIn, woodTypeIn);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BurnedOakSignEntity(pos, state);
	}
}