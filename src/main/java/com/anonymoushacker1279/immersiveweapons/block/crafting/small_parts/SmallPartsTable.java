package com.anonymoushacker1279.immersiveweapons.block.crafting.small_parts;

import com.anonymoushacker1279.immersiveweapons.container.SmallPartsContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SmallPartsTable extends Block {

	private static final Component CONTAINER_NAME = new TranslatableComponent("container.immersiveweapons.small_parts_table");

	/**
	 * Constructor for SmallPartsTable.
	 *
	 * @param properties the <code>Properties</code> of the block
	 */
	public SmallPartsTable(BlockBehaviour.Properties properties) {
		super(properties);
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

	@Nullable
	public static ListTag getItemPatterns(ItemStack pStack) {
		ListTag listtag = null;
		CompoundTag compoundtag = BlockItem.getBlockEntityData(pStack);
		if (compoundtag != null && compoundtag.contains("Patterns", 9)) {
			listtag = compoundtag.getList("Patterns", 10).copy();
		}

		return listtag;
	}
}