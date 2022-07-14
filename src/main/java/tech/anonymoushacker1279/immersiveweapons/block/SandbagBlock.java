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
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

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

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateDefinition.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BAGS, FACING);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 *
	 * @param context the <code>BlockPlaceContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BAGS, 0);
	}

	/**
	 * Set the shape of the block.
	 *
	 * @param state            the <code>BlockState</code> of the block
	 * @param blockGetter      the <code>BlockGetter</code> for the block
	 * @param pos              the <code>BlockPos</code> the block is at
	 * @param collisionContext the <code>CollisionContext</code> of the block
	 * @return VoxelShape
	 */
	@Override
	public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos,
	                                    @NotNull CollisionContext collisionContext) {

		return switch (state.getValue(BAGS)) {
			case 1 -> SHAPE_1;
			case 2 -> SHAPE_2;
			case 3 -> SHAPE_3;
			default -> SHAPE_0;
		};
	}

	/**
	 * Set the shading brightness on the client.
	 *
	 * @param state       the <code>BlockState</code> of the block
	 * @param blockGetter the <code>BlockGetter</code> of the block
	 * @param pos         the <code>BlockPos</code> the block is at
	 * @return float
	 */
	@Override
	public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos) {
		return 1.0F;
	}

	/**
	 * Runs when the block is activated.
	 * Allows the block to respond to user interaction.
	 *
	 * @param state     the <code>BlockState</code> of the block
	 * @param level     the <code>Level</code> the block is in
	 * @param pos       the <code>BlockPos</code> the block is at
	 * @param player    the <code>PlayerEntity</code> interacting with the block
	 * @param hand      the <code>InteractionHand</code> the PlayerEntity used
	 * @param hitResult the <code>BlockHitResult</code> of the interaction
	 * @return ActionResultType
	 */
	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
	                                      Player player, @NotNull InteractionHand hand,
	                                      @NotNull BlockHitResult hitResult) {

		if (player.getMainHandItem().getItem() == DeferredRegistryHandler.SANDBAG_ITEM.get()) {
			if (state.getValue(BAGS) == 0) {
				level.setBlock(pos, state.setValue(BAGS, 1).setValue(FACING, state.getValue(FACING)), 3);
				if (!player.isCreative()) {
					player.getMainHandItem().shrink(1);
				}
				return InteractionResult.CONSUME;
			}
			if (state.getValue(BAGS) == 1) {
				level.setBlock(pos, state.setValue(BAGS, 2).setValue(FACING, state.getValue(FACING)), 3);
				if (!player.isCreative()) {
					player.getMainHandItem().shrink(1);
				}
				return InteractionResult.CONSUME;
			}
			if (state.getValue(BAGS) == 2) {
				level.setBlock(pos, state.setValue(BAGS, 3).setValue(FACING, state.getValue(FACING)), 3);
				if (!player.isCreative()) {
					player.getMainHandItem().shrink(1);
				}
				return InteractionResult.CONSUME;
			}
		}
		return InteractionResult.PASS;
	}
}