package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;

public class SandbagBlock extends HorizontalDirectionalBlock {

	public static final IntegerProperty BAGS = IntegerProperty.create("bags", 0, 3);
	private static final VoxelShape SHAPE_0 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	private static final VoxelShape SHAPE_1 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
	private static final VoxelShape SHAPE_2 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
	private static final VoxelShape SHAPE_3 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	/**
	 * Constructor for SandbagBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public SandbagBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BAGS, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BAGS, FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BAGS, 0);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos,
	                           CollisionContext collisionContext) {

		return switch (state.getValue(BAGS)) {
			case 1 -> SHAPE_1;
			case 2 -> SHAPE_2;
			case 3 -> SHAPE_3;
			default -> SHAPE_0;
		};
	}

	@Override
	public float getShadeBrightness(BlockState state, BlockGetter blockGetter, BlockPos pos) {
		return 1.0F;
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos,
	                             Player player, InteractionHand hand,
	                             BlockHitResult hitResult) {

		if (player.getMainHandItem().getItem() == BlockItemRegistry.SANDBAG_ITEM.get()) {
			int bags = state.getValue(BAGS);
			if (bags < 3) {
				level.setBlock(pos, state.setValue(BAGS, bags + 1).setValue(FACING, state.getValue(FACING)), 3);
				if (!player.isCreative()) {
					player.getMainHandItem().shrink(1);
				}
				return InteractionResult.CONSUME;
			}
		}
		return InteractionResult.PASS;
	}
}