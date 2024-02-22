package tech.anonymoushacker1279.immersiveweapons.block.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import tech.anonymoushacker1279.immersiveweapons.blockentity.CustomHangingSignBlockEntity;

public class CustomCeilingHangingSignBlock extends CeilingHangingSignBlock {

	public CustomCeilingHangingSignBlock(Properties properties, WoodType type) {
		super(type, properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CustomHangingSignBlockEntity(pos, state);
	}
}