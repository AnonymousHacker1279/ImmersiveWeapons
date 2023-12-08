package tech.anonymoushacker1279.immersiveweapons.block.star_forge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.NetworkHooks;
import tech.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.StarForgeBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.menu.StarForgeMenu;

public class StarForgeControllerBlock extends BasicOrientableBlock implements EntityBlock {

	private static final Component CONTAINER_NAME = Component.translatable("container.immersiveweapons.star_forge");

	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public StarForgeControllerBlock(Properties properties) {
		super(properties);

		registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, Boolean.FALSE));
	}

	@Override
	public void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return new SimpleMenuProvider((id, inventory, player) -> new StarForgeMenu(id, inventory), CONTAINER_NAME);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new StarForgeBlockEntity(blockPos, blockState);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {

		return level.isClientSide ? null : (world, pos, state, entity) -> ((StarForgeBlockEntity) entity).tick((ServerLevel) world, pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			if (level.getBlockEntity(pos) instanceof StarForgeBlockEntity blockEntity) {
				NetworkHooks.openScreen((ServerPlayer) player, blockEntity, pos);
			}
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

		if (level instanceof ServerLevel serverLevel) {
			checkForValidMultiBlock(state, pos, serverLevel);
		}
	}

	/**
	 * Scan the 3x3x5 area around the controller to check for a valid multiblock structure.
	 *
	 * @return boolean
	 */
	public static boolean checkForValidMultiBlock(BlockState controllerState, BlockPos controllerPos, ServerLevel level) {
		// Get the selection of blocks to check relative to the controller's direction
		// The controller is always in front of the forge
		Direction controllerDirection = controllerState.getValue(FACING);

		// Check each "slice" of the structure and ensure it is valid

		// Slice 0
		BlockPos sliceStart = controllerPos.below().relative(controllerDirection);
		if (isSliceInvalid(sliceStart, controllerPos, 0, level)) {
			return false;
		}

		// Slice 1
		sliceStart = controllerPos.relative(controllerDirection);
		if (isSliceInvalid(sliceStart, controllerPos, 1, level)) {
			return false;
		}

		// Slice 2
		sliceStart = controllerPos.above().relative(controllerDirection);
		if (isSliceInvalid(sliceStart, controllerPos, 2, level)) {
			return false;
		}

		// Slice 3
		sliceStart = controllerPos.above(2).relative(controllerDirection);
		if (isSliceInvalid(sliceStart, controllerPos, 3, level)) {
			return false;
		}

		// Slice 4
		sliceStart = controllerPos.above(3).relative(controllerDirection);
		return !isSliceInvalid(sliceStart, controllerPos, 4, level);
	}

	/**
	 * Check a slice of the multiblock structure for validity.
	 *
	 * @param centerPos     the center position of the slice
	 * @param controllerPos the position of the controller
	 * @param slice         the slice to check
	 * @param level         the <code>ServerLevel</code> instance
	 * @return boolean
	 */
	public static boolean isSliceInvalid(BlockPos centerPos, BlockPos controllerPos, int slice, ServerLevel level) {
		boolean isValid = true;
		switch (slice) {
			case 0 -> {
				// Slice 0: All blocks should be Star Forge Bricks
				// Iterate over the 3x3 area around the center position
				for (int x = -1; x <= 1; x++) {
					for (int z = -1; z <= 1; z++) {
						BlockPos blockPos = centerPos.offset(x, 0, z);

						if (!level.getBlockState(blockPos).is(BlockRegistry.STAR_FORGE_BRICKS.get())) {
							isValid = false;
						}
					}
				}
			}
			case 1 -> {
				// Slice 1: All blocks should be Star Forge Bricks excluding the controller itself, with an air block in the center
				for (int x = -1; x <= 1; x++) {
					for (int z = -1; z <= 1; z++) {
						BlockPos blockPos = centerPos.offset(x, 0, z);

						if (blockPos.equals(controllerPos) && !level.getBlockState(controllerPos).is(BlockRegistry.STAR_FORGE_CONTROLLER.get())) {
							isValid = false;
						} else if (x == 0 && z == 0) {
							if (!level.getBlockState(blockPos).isAir()) {
								isValid = false;
							}
						} else if (!blockPos.equals(controllerPos)) {
							if (!level.getBlockState(blockPos).is(BlockRegistry.STAR_FORGE_BRICKS.get())) {
								isValid = false;
							}
						}
					}
				}
			}
			case 2 -> {
				// Slice 2: All blocks should be Star Forge Bricks, with an air block in the center
				for (int x = -1; x <= 1; x++) {
					for (int z = -1; z <= 1; z++) {
						BlockPos blockPos = centerPos.offset(x, 0, z);

						if (x == 0 && z == 0) {
							if (!level.getBlockState(blockPos).isAir()) {
								isValid = false;
							}
						} else if (!level.getBlockState(blockPos).is(BlockRegistry.STAR_FORGE_BRICKS.get())) {
							isValid = false;
						}
					}
				}
			}
			case 3 -> {
				// Slice 3: Each corner is an Iron Bar and other blocks must be air
				for (int x = -1; x <= 1; x++) {
					for (int z = -1; z <= 1; z++) {
						BlockPos blockPos = centerPos.offset(x, 0, z);

						if (x == -1 && z == -1) {
							if (!level.getBlockState(blockPos).is(Blocks.IRON_BARS)) {
								isValid = false;
							}
						} else if (x == -1 && z == 1) {
							if (!level.getBlockState(blockPos).is(Blocks.IRON_BARS)) {
								isValid = false;
							}
						} else if (x == 1 && z == -1) {
							if (!level.getBlockState(blockPos).is(Blocks.IRON_BARS)) {
								isValid = false;
							}
						} else if (x == 1 && z == 1) {
							if (!level.getBlockState(blockPos).is(Blocks.IRON_BARS)) {
								isValid = false;
							}
						} else if (!level.getBlockState(blockPos).isAir()) {
							isValid = false;
						}
					}
				}
			}
			case 4 -> {
				// Slice 4: All blocks should be Star Forge Bricks, with a Solar Lens in the center
				for (int x = -1; x <= 1; x++) {
					for (int z = -1; z <= 1; z++) {
						BlockPos blockPos = centerPos.offset(x, 0, z);

						if (x == 0 && z == 0) {
							if (!level.getBlockState(blockPos).is(BlockRegistry.SOLAR_LENS.get())) {
								isValid = false;
							}
						} else if (!level.getBlockState(blockPos).is(BlockRegistry.STAR_FORGE_BRICKS.get())) {
							isValid = false;
						}
					}
				}
			}
		}

		return !isValid;
	}
}