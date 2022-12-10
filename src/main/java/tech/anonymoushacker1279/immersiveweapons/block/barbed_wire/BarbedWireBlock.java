package tech.anonymoushacker1279.immersiveweapons.block.barbed_wire;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class BarbedWireBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, BarbedWireUtils {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static int soundCooldown = 0;

	/**
	 * Constructor for BarbedWireBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public BarbedWireBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 *
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	/**
	 * Set FluidState properties.
	 * Allows the block to exhibit waterlogged behavior.
	 *
	 * @param state the <code>BlockState</code> of the block
	 * @return FluidState
	 */
	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
	}

	/**
	 * Determines if skylight should pass through the block.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> for the block
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @return boolean
	 */
	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	/**
	 * Runs when an entity is inside the block's collision area.
	 * Allows the block to deal damage on contact.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param level  the <code>Level</code> the block is in
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @param entity the <code>Entity</code> passing through the block
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		handleEntityContact(state, level, pos, entity);
	}
}