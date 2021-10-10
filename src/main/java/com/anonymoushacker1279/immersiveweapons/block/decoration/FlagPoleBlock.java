package com.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FlagPoleBlock extends Block {

	private static final BooleanProperty IS_BASE = BooleanProperty.create("base");
	private static final VoxelShape SHAPE_POLE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
	private static final VoxelShape SHAPE_BASE = Shapes.or(SHAPE_POLE, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D));

	/**
	 * Constructor for FlagPoleBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public FlagPoleBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(IS_BASE, true));
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(IS_BASE);
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
		BlockState blockStateBelow = context.getLevel().getBlockState(context.getClickedPos().below());
		if (blockStateBelow.getBlock() instanceof FlagPoleBlock || blockStateBelow.getBlock() instanceof FlagBlock) {
			return defaultBlockState().setValue(IS_BASE, false);
		} else {
			return defaultBlockState().setValue(IS_BASE, true);
		}
	}

	/**
	 * Set the shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param reader           the <code>IBlockReader</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param selectionContext the <code>ISelectionContext</code> of the block
	 * @return VoxelShape
	 */
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos, @NotNull CollisionContext selectionContext) {
		Vec3 vector3d = state.getOffset(reader, pos);
		if (state.getValue(IS_BASE)) {
			return SHAPE_BASE.move(vector3d.x, vector3d.y, vector3d.z);
		} else {
			return SHAPE_POLE.move(vector3d.x, vector3d.y, vector3d.z);
		}
	}
}