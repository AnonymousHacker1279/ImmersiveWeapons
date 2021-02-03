package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.container.SmallPartsContainer;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class SmallPartsTable extends CraftingTableBlock {

		private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container.immersiveweapons.small_parts_table");

	   public SmallPartsTable(AbstractBlock.Properties properties) {
	      super(properties);
	   }

	   @Override
	   public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		   return new SimpleNamedContainerProvider((id, inventory, player) -> {
			   return new SmallPartsContainer(id, inventory, IWorldPosCallable.of(worldIn, pos));
		   }, CONTAINER_NAME);
	   }
	   
	   @Override
	   public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		   if (worldIn.isRemote) {
			   return ActionResultType.SUCCESS;
		   } else {
			   player.openContainer(state.getContainer(worldIn, pos));
			   return ActionResultType.CONSUME;
		   }
	   }
}
