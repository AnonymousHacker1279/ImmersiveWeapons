package tech.anonymoushacker1279.immersiveweapons.block.sign;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import tech.anonymoushacker1279.immersiveweapons.blockentity.StardustSignEntity;

public class StardustStandingSignBlock extends StandingSignBlock {

	public StardustStandingSignBlock(Properties propertiesIn, WoodType woodTypeIn) {
		super(propertiesIn, woodTypeIn);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new StardustSignEntity(pos, state);
	}
}