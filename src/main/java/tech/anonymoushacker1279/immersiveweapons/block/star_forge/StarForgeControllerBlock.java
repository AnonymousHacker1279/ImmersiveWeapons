package tech.anonymoushacker1279.immersiveweapons.block.star_forge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import tech.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

public class StarForgeControllerBlock extends BasicOrientableBlock {

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
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
		super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

		if (level instanceof ServerLevel serverLevel) {
			checkForValidMultiBlock(state, pos, serverLevel);
		}
	}

	/**
	 * Scan the 3x3x5 area around the controller to check for a valid multiblock structure.
	 * @return boolean
	 */
	public boolean checkForValidMultiBlock(BlockState controllerState, BlockPos controllerPos, ServerLevel level) {
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
		if (isSliceInvalid(sliceStart, controllerPos, 4, level)) {
			return false;
		}

		// Spawn particles within the area that fly out from in front of the controller
		level.sendParticles(
				ParticleTypes.SOUL_FIRE_FLAME,
				controllerPos.getX(),
				controllerPos.getY(),
				controllerPos.getZ(),
				48,
				1,
				1,
				1,
				0.15f
		);

		return true;
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
	public boolean isSliceInvalid(BlockPos centerPos, BlockPos controllerPos, int slice, ServerLevel level) {
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