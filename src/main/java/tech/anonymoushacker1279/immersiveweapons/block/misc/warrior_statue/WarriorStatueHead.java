package tech.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class WarriorStatueHead extends WarriorStatueBase {

	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	private static final VoxelShape SHAPE_NS = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 11.0D, 10.0D);
	private static final VoxelShape SHAPE_EW = Block.box(3.0D, 0.0D, 3.0D, 10.0D, 11.0D, 13.0D);

	public WarriorStatueHead(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext selectionContext) {
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
		if (blockStateBelow.getBlock() == BlockRegistry.WARRIOR_STATUE_TORSO.get() &&
				blockStateBelow.getValue(FACING) == context.getHorizontalDirection().getOpposite()) {

			context.getLevel().playLocalSound(context.getClickedPos().getX(),
					context.getClickedPos().getY(),
					context.getClickedPos().getZ(),
					SoundEvents.END_PORTAL_FRAME_FILL,
					SoundSource.BLOCKS, 0.5f,
					GeneralUtilities.getRandomNumber(0.7f, 0.8f),
					false);

			return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
		} else {
			return Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		if (level.getBlockState(pos.below()).getBlock() != BlockRegistry.WARRIOR_STATUE_TORSO.get()) {
			return false;
		}

		return super.canSurvive(state, level, pos);
	}

	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (!level.isClientSide) {
			if (level.getBlockState(pos.below()).getBlock() != BlockRegistry.WARRIOR_STATUE_TORSO.get()) {
				level.destroyBlock(pos, true);
			}
		}
	}
}