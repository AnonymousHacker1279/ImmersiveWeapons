package tech.anonymoushacker1279.immersiveweapons.block.core;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class StrippablePillarBlock extends RotatedPillarBlock {

	private final BlockState strippedBlockState;

	public StrippablePillarBlock(Properties properties, BlockState strippedState) {
		super(properties);
		strippedBlockState = strippedState;
	}

	@Override
	public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
		if (toolAction == ToolActions.AXE_STRIP) {
			return strippedBlockState;
		}

		return null;
	}
}