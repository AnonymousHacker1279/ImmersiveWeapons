package tech.anonymoushacker1279.immersiveweapons.block.crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.network.NetworkHooks;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AmmunitionTableBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.menu.AmmunitionTableMenu;

import java.util.stream.Stream;

public class AmmunitionTableBlock extends HorizontalDirectionalBlock implements EntityBlock {

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

		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {

		return SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
		return new SimpleMenuProvider((id, inventory, player) -> new AmmunitionTableMenu(id, inventory), CONTAINER_NAME);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new AmmunitionTableBlockEntity(blockPos, blockState);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			if (level.getBlockEntity(pos) instanceof AmmunitionTableBlockEntity blockEntity) {
				NetworkHooks.openScreen((ServerPlayer) player, blockEntity, pos);
			}
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof AmmunitionTableBlockEntity blockEntity) {
				Containers.dropContents(level, pos, blockEntity);
				level.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
}