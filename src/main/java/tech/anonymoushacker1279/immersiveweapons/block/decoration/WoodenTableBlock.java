package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.*;

public class WoodenTableBlock extends Block implements SimpleWaterloggedBlock {

	public static final VoxelShape SHAPE = Shapes.or(Block.box(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D),
			Block.box(7.0D, 0.0D, 7.0D, 9.0D, 14.0D, 9.0D));
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	/**
	 * Constructor for WoodenTableBlock.
	 *
	 * @param properties the <code>Properties</code> of the blockLocation
	 */
	public WoodenTableBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any()
				.setValue(WATERLOGGED, false));
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateDefinition.Builder</code> of the blockLocation
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
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

	/**
	 * Set the shape of the blockLocation.
	 *
	 * @param state            the <code>BlockState</code> of the blockLocation
	 * @param reader           the <code>BlockGetter</code> for the blockLocation
	 * @param pos              the <code>BlockPos</code> the blockLocation is at
	 * @param collisionContext the <code>CollisionContext</code> of the blockLocation
	 * @return VoxelShape
	 */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos,
	                           CollisionContext collisionContext) {

		return SHAPE;
	}
}