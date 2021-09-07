package com.anonymoushacker1279.immersiveweapons.block.base;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
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
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class SandbagBlock extends HorizontalDirectionalBlock {

	private static final IntegerProperty BAGS = IntegerProperty.create("bags", 1, 4);
	private static final VoxelShape SHAPE_1 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	private static final VoxelShape SHAPE_2 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
	private static final VoxelShape SHAPE_3 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
	private static final VoxelShape SHAPE_4 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	/**
	 * Constructor for SandbagBlock.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public SandbagBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BAGS, 1));
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BAGS, FACING);
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
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BAGS, 1);
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
		return switch (state.getValue(BAGS)) {
			case 2 -> SHAPE_2.move(vector3d.x, vector3d.y, vector3d.z);
			case 3 -> SHAPE_3.move(vector3d.x, vector3d.y, vector3d.z);
			case 4 -> SHAPE_4.move(vector3d.x, vector3d.y, vector3d.z);
			default -> SHAPE_1.move(vector3d.x, vector3d.y, vector3d.z);
		};
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

	/**
	 * Runs when the block is activated.
	 * Allows the block to respond to user interaction.
	 *
	 * @param state               the <code>BlockState</code> of the block
	 * @param worldIn             the <code>World</code> the block is in
	 * @param pos                 the <code>BlockPos</code> the block is at
	 * @param player              the <code>PlayerEntity</code> interacting with the block
	 * @param handIn              the <code>Hand</code> the PlayerEntity used
	 * @param blockRayTraceResult the <code>BlockRayTraceResult</code> of the interaction
	 * @return ActionResultType
	 */
	@SuppressWarnings("deprecation")
	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult blockRayTraceResult) {
		if (player.getMainHandItem().getItem() == DeferredRegistryHandler.SANDBAG_ITEM.get()) {
			if (state.getValue(BAGS) == 1) {
				worldIn.setBlock(pos, state.setValue(BAGS, 2).setValue(FACING, state.getValue(FACING)), 3);
				if (!player.isCreative()) {
					player.getMainHandItem().shrink(1);
				}
				return InteractionResult.CONSUME;
			}
			if (state.getValue(BAGS) == 2) {
				worldIn.setBlock(pos, state.setValue(BAGS, 3).setValue(FACING, state.getValue(FACING)), 3);
				if (!player.isCreative()) {
					player.getMainHandItem().shrink(1);
				}
				return InteractionResult.CONSUME;
			}
			if (state.getValue(BAGS) == 3) {
				worldIn.setBlock(pos, state.setValue(BAGS, 4).setValue(FACING, state.getValue(FACING)), 3);
				if (!player.isCreative()) {
					player.getMainHandItem().shrink(1);
				}
				return InteractionResult.CONSUME;
			}
		}
		return InteractionResult.PASS;
	}
}