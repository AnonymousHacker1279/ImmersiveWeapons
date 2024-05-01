package tech.anonymoushacker1279.immersiveweapons.block.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import tech.anonymoushacker1279.immersiveweapons.block.core.BasicOrientableBlock;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AmmunitionTableBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.menu.AmmunitionTableMenu;

import java.util.stream.Stream;

public class AmmunitionTableBlock extends BasicOrientableBlock implements EntityBlock {

	protected static final VoxelShape SHAPE = Shapes.join(Stream.of(
			Stream.of(
					Block.box(0, 0, 0, 2, 2, 2),
					Block.box(14, 0, 0, 16, 2, 2),
					Block.box(14, 0, 14, 16, 2, 16),
					Block.box(0, 0, 14, 2, 2, 16)
			).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
			Block.box(0.5, 2, 0.5, 1.5, 8, 1.5),
			Block.box(14.5, 2, 0.5, 15.5, 8, 1.5),
			Block.box(14.5, 2, 14.5, 15.5, 8, 15.5),
			Block.box(0.5, 2, 14.5, 1.5, 8, 15.5),
			Stream.of(
					Block.box(0.5, 3, 1.5, 1.5, 4, 14.5),
					Block.box(14.5, 3, 1.5, 15.5, 4, 14.5),
					Block.box(1.5, 3, 0.5, 14.5, 4, 1.5)
			).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
	).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), Block.box(0, 8, 0, 16, 12, 16), BooleanOp.OR);

	private static final Component CONTAINER_NAME = Component.translatable("container.immersiveweapons.ammunition_table");

	public AmmunitionTableBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new AmmunitionTableBlockEntity(blockPos, blockState);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			if (level.getBlockEntity(pos) instanceof AmmunitionTableBlockEntity blockEntity && player instanceof ServerPlayer serverPlayer) {
				serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, player1) -> new AmmunitionTableMenu(id, inventory, blockEntity, blockEntity.containerData), CONTAINER_NAME));
			}

			return InteractionResult.CONSUME;
		}
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof AmmunitionTableBlockEntity blockEntity) {
				blockEntity.removeItemNoUpdate(6);  // Remove the output item
				Containers.dropContents(level, pos, blockEntity);
			}

			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
}