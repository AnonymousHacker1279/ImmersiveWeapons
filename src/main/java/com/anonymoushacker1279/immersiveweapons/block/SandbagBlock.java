package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SandbagBlock extends HorizontalBlock {

	public static final IntegerProperty BAGS = IntegerProperty.create("bags", 1, 4);
	protected static final VoxelShape SHAPE_1 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);
	protected static final VoxelShape SHAPE_2 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
	protected static final VoxelShape SHAPE_3 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
	protected static final VoxelShape SHAPE_4 = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	/**
	 * Constructor for SandbagBlock.
	 * @param properties the <code>Properties</code> of the block
	 */
	public SandbagBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BAGS, 1));
	}

	/**
	 * Create the BlockState definition.
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BAGS, FACING);
	}

	/**
	 * Set placement properties.
	 * Sets the facing direction of the block for placement.
	 * @param context the <code>BlockItemUseContext</code> during placement
	 * @return BlockState
	 */
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(BAGS, 1);
	}

	/**
	 * Set the shape of the block.
	 * @param state the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> for the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param selectionContext the <code>ISelectionContext</code> of the block
	 * @return VoxelShape
	 */
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext selectionContext) {
		Vector3d vector3d = state.getOffset(reader, pos);
		switch (state.getValue(BAGS)) {
			case 1:
			default:
				return SHAPE_1.move(vector3d.x, vector3d.y, vector3d.z);
			case 2:
				return SHAPE_2.move(vector3d.x, vector3d.y, vector3d.z);
			case 3:
				return SHAPE_3.move(vector3d.x, vector3d.y, vector3d.z);
			case 4:
				return SHAPE_4.move(vector3d.x, vector3d.y, vector3d.z);
		}
	}

	/**
	 * Set the shading brightness on the client.
	 * @param state the <code>BlockState</code> of the block
	 * @param reader the <code>IBlockReader</code> of the block
	 * @param pos the <code>BlockPos</code> the block is at
	 * @return float
	 */
	@Override
	@OnlyIn(Dist.CLIENT)
	public float getShadeBrightness(BlockState state, IBlockReader reader, BlockPos pos) {
		return 1.0F;
	}

	/**
	 * Runs when the block is activated.
	 * Allows the block to respond to user interaction.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param player the <code>PlayerEntity</code> interacting with the block
	 * @param handIn the <code>Hand</code> the PlayerEntity used
	 * @param blockRayTraceResult the <code>BlockRayTraceResult</code> of the interaction
	 * @return ActionResultType
	 */
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult blockRayTraceResult) {
		if (player.getMainHandItem().getItem() == DeferredRegistryHandler.SANDBAG_ITEM.get()) {
			if (state.getValue(BAGS) == 1) {
				worldIn.setBlock(pos, state.setValue(BAGS, 2).setValue(FACING, state.getValue(FACING)), 3);
				if (!player.abilities.instabuild) {
					player.getMainHandItem().shrink(1);
				}
				return ActionResultType.CONSUME;
			}
			if (state.getValue(BAGS) == 2) {
				worldIn.setBlock(pos, state.setValue(BAGS, 3).setValue(FACING, state.getValue(FACING)), 3);
				if (!player.abilities.instabuild) {
					player.getMainHandItem().shrink(1);
				}
				return ActionResultType.CONSUME;
			}
			if (state.getValue(BAGS) == 3) {
				worldIn.setBlock(pos, state.setValue(BAGS, 4).setValue(FACING, state.getValue(FACING)), 3);
				if (!player.abilities.instabuild) {
					player.getMainHandItem().shrink(1);
				}
				return ActionResultType.CONSUME;
			}
		}
		return ActionResultType.PASS;
	}
}