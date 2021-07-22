package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.container.TeslaSynthesizerContainer;
import com.anonymoushacker1279.immersiveweapons.tileentity.AbstractTeslaSynthesizerTileEntity;
import com.anonymoushacker1279.immersiveweapons.tileentity.TeslaSynthesizerTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Random;

public class TeslaSynthesizerBlock extends ContainerBlock implements IWaterLoggable {

	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.immersiveweapons.tesla_synthesizer");

	/**
	 * Constructor for TeslaSynthesizerBlock.
	 * @param properties the <code>Properties</code> of the block
	 */
	public TeslaSynthesizerBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
	}

	/**
	 * Create a tile entity for the block.
	 * @param reader the <code>IBlockReader</code> of the block
	 * @return TileEntity
	 */
	@Override
	public TileEntity newBlockEntity(IBlockReader reader) {
		return new TeslaSynthesizerTileEntity();
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
		return SHAPE;
	}

	/**
	 * Create the BlockState definition.
	 * @param builder the <code>StateContainer.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	/**
	 * Set FluidState properties.
	 * Allows the block to exhibit waterlogged behavior.
	 * @param state the <code>BlockState</code> of the block
	 * @return FluidState
	 */
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	/**
	 * Set the RenderShape of the block's model.
	 * @param state the <code>BlockState</code> of the block
	 * @return BlockRenderType
	 */
	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

	/**
	 * Get the INamedContainerProvider for the block.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @return INamedContainerProvider
	 */
	@Override
	public INamedContainerProvider getMenuProvider(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((id, inventory, player) -> new TeslaSynthesizerContainer(id, inventory), CONTAINER_NAME);
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
		if (worldIn.isClientSide) {
			return ActionResultType.SUCCESS;
		} else {
			TileEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof TeslaSynthesizerTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, pos);
			}
			return ActionResultType.CONSUME;
		}
	}

	/**
	 * Runs when the block is removed.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param newState the <code>BlockState</code> the block now has
	 * @param isMoving determines if the block is moving
	 */
	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			TileEntity tileEntity = worldIn.getBlockEntity(pos);
			if (tileEntity instanceof AbstractTeslaSynthesizerTileEntity) {
				InventoryHelper.dropContents(worldIn, pos, (AbstractTeslaSynthesizerTileEntity) tileEntity);
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	/**
	 * Runs occasionally to create animations.
	 * @param stateIn the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @param rand a <code>Random</code> instance
	 */
	@Override
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		worldIn.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.getX() + 0.5D, pos.getY() + 0.4D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
	}
}