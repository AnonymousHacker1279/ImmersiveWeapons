package tech.anonymoushacker1279.immersiveweapons.block.core;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
// TODO: re-add imports when stripping is restored: UseOnContext, ItemAbilities, ItemAbility, Nullable

public class StrippablePillarBlock extends RotatedPillarBlock {

	private final BlockState strippedBlockState;

	public StrippablePillarBlock(Properties properties, BlockState strippedState) {
		super(properties);
		strippedBlockState = strippedState;
	}

	// TODO: axe stripping is data-driven (BlockTransformer) in 26.3 and ItemAbilities.AXE_STRIP was removed.
	//  Restore custom stripping once https://github.com/neoforged/NeoForge/pull/3509 is merged.
	/*
	@Override
	public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
		if (itemAbility == ItemAbilities.AXE_STRIP) {
			return strippedBlockState;
		}

		return super.getToolModifiedState(state, context, itemAbility, simulate);
	}
	*/
}