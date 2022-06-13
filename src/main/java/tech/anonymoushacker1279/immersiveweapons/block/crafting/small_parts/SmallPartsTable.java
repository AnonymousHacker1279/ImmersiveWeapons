package tech.anonymoushacker1279.immersiveweapons.block.crafting.small_parts;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.container.SmallPartsContainer;

public class SmallPartsTable extends HorizontalDirectionalBlock {

	private static final Component CONTAINER_NAME = new TranslatableComponent("container.immersiveweapons.small_parts_table");

	/**
	 * Constructor for SmallPartsTable.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public SmallPartsTable(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	/**
	 * Get the INamedContainerProvider for the block.
	 *
	 * @param state   the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos     the <code>BlockPos</code> the block is at
	 * @return INamedContainerProvider
	 */
	@Override
	public MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos) {
		return new SimpleMenuProvider((id, inventory, player) -> new SmallPartsContainer(id, inventory, ContainerLevelAccess.create(worldIn, pos)), CONTAINER_NAME);
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
	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult blockRayTraceResult) {
		if (worldIn.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			NetworkHooks.openGui((ServerPlayer) player, state.getMenuProvider(worldIn, pos), pos);
			return InteractionResult.CONSUME;
		}
	}

	/**
	 * Create the BlockState definition.
	 *
	 * @param builder the <code>StateDefinition.Builder</code> of the block
	 */
	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
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
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
}