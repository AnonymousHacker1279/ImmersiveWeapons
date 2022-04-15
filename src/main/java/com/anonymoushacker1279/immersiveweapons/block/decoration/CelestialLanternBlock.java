package com.anonymoushacker1279.immersiveweapons.block.decoration;

import com.anonymoushacker1279.immersiveweapons.blockentity.CelestialLanternBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CelestialLanternBlock extends LanternBlock implements EntityBlock {

	protected static final VoxelShape AABB = Shapes.or(
			Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
			Block.box(5.0D, 10.0D, 5.0D, 11.0D, 11.0D, 11.0D));

	public static List<BlockPos> ALL_TILTROS_LANTERNS = new ArrayList<>(3);

	public CelestialLanternBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter,
	                                    @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {

		return AABB;
	}

	@Override
	public void setPlacedBy(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState,
	                        @Nullable LivingEntity pPlacer, @NotNull ItemStack pStack) {

		super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
		if (!pLevel.isClientSide) {
			ALL_TILTROS_LANTERNS.add(pPos);
		}
	}

	@Override
	public void destroy(@NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState) {
		super.destroy(pLevel, pPos, pState);

		if (!pLevel.isClientSide()) {
			ALL_TILTROS_LANTERNS.remove(pPos);
		}
	}

	/**
	 * Create a block entity for the block.
	 *
	 * @param blockPos   the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return new CelestialLanternBlockEntity(blockPos, blockState);
	}
}