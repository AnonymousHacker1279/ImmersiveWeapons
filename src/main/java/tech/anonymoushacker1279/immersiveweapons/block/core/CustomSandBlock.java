package tech.anonymoushacker1279.immersiveweapons.block.core;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IPlantable;
import net.neoforged.neoforge.common.PlantType;

public class CustomSandBlock extends ColoredFallingBlock {

	public CustomSandBlock(ColorRGBA dustColor, Properties properties) {
		super(dustColor, properties);
	}

	@Override
	public boolean canSustainPlant(BlockState state, BlockGetter getter, BlockPos pos, Direction facing, IPlantable plantable) {
		return plantable.getPlantType(getter, pos).equals(PlantType.DESERT)
				|| plantable.getPlantType(getter, pos).equals(PlantType.BEACH);
	}
}