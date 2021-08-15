package com.anonymoushacker1279.immersiveweapons.block.sign;

import com.anonymoushacker1279.immersiveweapons.blockentity.BurnedOakSignEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

public class BurnedOakStandingSignBlock extends StandingSignBlock {

	public BurnedOakStandingSignBlock(Properties propertiesIn, WoodType woodTypeIn) {
		super(propertiesIn, woodTypeIn);
	}

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new BurnedOakSignEntity(pos, state);
	}
}