package tech.anonymoushacker1279.immersiveweapons.block.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.blockentity.MinutemanStatueBlockEntity;

public class MinutemanStatueBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);

	/**
	 * Constructor for MinutemanStatueBlock.
	 *
	 * @param properties the <code>Properties</code> of the blockLocation
	 */
	public MinutemanStatueBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	/**
	 * Create a blockLocation entity for the blockLocation.
	 *
	 * @param blockPos   the <code>BlockPos</code> the blockLocation is at
	 * @param blockState the <code>BlockState</code> of the blockLocation
	 * @return BlockEntity
	 */
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new MinutemanStatueBlockEntity(blockPos, blockState);
	}

	/**
	 * Get the ticker for the blockLocation.
	 *
	 * @param level           the <code>Level</code> the blockLocation is in
	 * @param blockState      the <code>BlockState</code> of the blockLocation
	 * @param blockEntityType the <code>BlockEntityType</code> to get the ticker of
	 * @param <T>             the type extending BlockEntity
	 * @return BlockEntityTicker
	 */
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
	                                                              BlockEntityType<T> blockEntityType) {

		return level.isClientSide ? null : (world, pos, state, entity) -> ((MinutemanStatueBlockEntity) entity).tick(world, pos);
	}

	/**
	 * Set the shape of the blockLocation.
	 *
	 * @param state            the <code>BlockState</code> of the blockLocation
	 * @param reader           the <code>IBlockReader</code> for the blockLocation
	 * @param pos              the <code>BlockPos</code> the blockLocation is at
	 * @param selectionContext the <code>ISelectionContext</code> of the blockLocation
	 * @return VoxelShape
	 */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext selectionContext) {
		return SHAPE;
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the blockLocation
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the blockLocation for placement.
	 *
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	/**
	 * Set FluidState properties.
	 * Allows the blockLocation to exhibit waterlogged behavior.
	 *
	 * @param state the <code>BlockState</code> of the blockLocation
	 * @return FluidState
	 */
	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
}