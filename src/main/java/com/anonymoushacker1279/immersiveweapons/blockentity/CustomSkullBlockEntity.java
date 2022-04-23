package com.anonymoushacker1279.immersiveweapons.blockentity;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CustomSkullBlockEntity extends SkullBlockEntity {

	public CustomSkullBlockEntity(BlockPos blockPos, BlockState state) {
		super(blockPos, state);
	}

	@Override
	public @NotNull BlockEntityType<?> getType() {
		return DeferredRegistryHandler.CUSTOM_SKULL_BLOCK_ENTITY.get();
	}
}