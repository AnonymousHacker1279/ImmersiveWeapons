package com.anonymoushacker1279.immersiveweapons.blockentity;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BurnedOakSignEntity extends SignBlockEntity {

	public BurnedOakSignEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public @NotNull BlockEntityType<BurnedOakSignEntity> getType() {
		return DeferredRegistryHandler.BURNED_OAK_SIGN_ENTITY.get();
	}
}