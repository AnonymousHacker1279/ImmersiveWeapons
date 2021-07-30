package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.container.SmallPartsContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class SmallPartsTable extends Block {

	private static final Component CONTAINER_NAME = new TranslatableComponent("container.immersiveweapons.small_parts_table");

	/**
	 * Constructor for SmallPartsTable.
	 * @param properties the <code>Properties</code> of the block
	 */
	public SmallPartsTable(BlockBehaviour.Properties properties) {
		super(properties);
	}

	/**
	 * Get the INamedContainerProvider for the block.
	 * @param state the <code>BlockState</code> of the block
	 * @param worldIn the <code>World</code> the block is in
	 * @param pos the <code>BlockPos</code> the block is at
	 * @return INamedContainerProvider
	 */
	@Override
	public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
		return new SimpleMenuProvider((id, inventory, player) -> new SmallPartsContainer(id, inventory, ContainerLevelAccess.create(worldIn, pos)), CONTAINER_NAME);
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
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult blockRayTraceResult) {
		if (worldIn.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			NetworkHooks.openGui((ServerPlayer) player, state.getMenuProvider(worldIn, pos), pos);
			return InteractionResult.CONSUME;
		}
	}
}