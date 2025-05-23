package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.blockentity.BiodomeLifeSupportUnitBlockEntity;

public class BiodomeLifeSupportUnitBlock extends Block implements EntityBlock {

	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

	public BiodomeLifeSupportUnitBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(POWERED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(POWERED, false);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @javax.annotation.Nullable Orientation orientation, boolean movedByPiston) {
		if (!level.isClientSide) {
			boolean isPowered = level.hasNeighborSignal(pos);
			if (isPowered) {
				if (level.getBlockEntity(pos) instanceof BiodomeLifeSupportUnitBlockEntity blockEntity) {
					blockEntity.setCooldown(0);
					blockEntity.setPowered(true);
				}
				level.scheduleTick(pos, this, 1);
			} else {
				if (level.getBlockEntity(pos) instanceof BiodomeLifeSupportUnitBlockEntity blockEntity) {
					blockEntity.setPowered(false);
				}
				level.setBlock(pos, state.cycle(POWERED), 16);
			}
		}
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!oldState.is(state.getBlock())) {
			if (level.hasNeighborSignal(pos)) {
				if (level.getBlockEntity(pos) instanceof BiodomeLifeSupportUnitBlockEntity blockEntity) {
					blockEntity.setPowered(true);
				}
				level.setBlock(pos, state.setValue(POWERED, true), 3);
			}
		}
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BiodomeLifeSupportUnitBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
	                                                              BlockEntityType<T> blockEntityType) {

		return level.isClientSide ? null : (world, pos, state, entity) -> ((BiodomeLifeSupportUnitBlockEntity) entity).tick(world, pos, state);
	}
}