package com.anonymoushacker1279.immersiveweapons.block.misc.portal.statue.warrior;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class WarriorStatueHead extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	private static final VoxelShape SHAPE_NS = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 11.0D, 10.0D);
	private static final VoxelShape SHAPE_EW = Block.box(3.0D, 0.0D, 3.0D, 10.0D, 11.0D, 13.0D);

	public WarriorStatueHead(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(WATERLOGGED, false));
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, WATERLOGGED);
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
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos, @NotNull CollisionContext selectionContext) {
		return switch (state.getValue(FACING)) {
			case SOUTH -> SHAPE_NS.move(0.0D, 0.0D, -0.2D);
			case EAST -> SHAPE_EW.move(-0.2D, 0.0D, 0.0D);
			case WEST -> SHAPE_EW.move(0.4D, 0.0D, 0.0D);
			default -> SHAPE_NS.move(0.0D, 0.0D, 0.4D);
		};
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
		if (blockStateBelow.getBlock() instanceof WarriorStatueTorso &&
				blockStateBelow.getValue(FACING) == context.getHorizontalDirection().getOpposite()) {
			context.getLevel().playLocalSound(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 0.5f, GeneralUtilities.getRandomNumber(0.7f, 0.8f), false);
			return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
		} else {
			return Blocks.AIR.defaultBlockState();
		}
	}

	/**
	 * Set the shading brightness on the client.
	 *
	 * @param state  the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> of the block
	 * @param pos    the <code>BlockPos</code> the block is at
	 * @return float
	 */
	@SuppressWarnings("deprecation")
	@Override
	@OnlyIn(Dist.CLIENT)
	public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
		return 1.0F;
	}

	@Override
	public @NotNull FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Runs when the player destroys the block.
	 *
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos     the <code>BlockPos</code> the block is at
	 * @param state   the <code>BlockState</code> of the block
	 * @param player  the <code>PlayerEntity</code> destroying the block
	 */
	@Override
	public void playerWillDestroy(@NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
		if (state.getValue(POWERED)) {
			worldIn.setBlock(pos.below(), DeferredRegistryHandler.WARRIOR_STATUE_TORSO.get().defaultBlockState().setValue(FACING, state.getValue(FACING)).setValue(POWERED, false), 3);
		}

		super.playerWillDestroy(worldIn, pos, state, player);
	}
}