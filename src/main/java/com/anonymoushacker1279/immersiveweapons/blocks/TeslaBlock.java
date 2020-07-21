package com.anonymoushacker1279.immersiveweapons.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraftforge.common.ToolType;

public class TeslaBlock extends HorizontalBlock {

	public TeslaBlock() {
		super(Block.Properties.create(Material.IRON)
				.hardnessAndResistance(2.8f, 8.8f)
				.sound(SoundType.METAL)
				.harvestLevel(0)
				.harvestTool(ToolType.PICKAXE));
	}
	
	@Override
	public void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL_FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

}
