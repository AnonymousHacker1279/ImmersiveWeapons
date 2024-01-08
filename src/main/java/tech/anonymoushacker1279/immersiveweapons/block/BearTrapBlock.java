package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.*;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import tech.anonymoushacker1279.immersiveweapons.blockentity.BearTrapBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class BearTrapBlock extends Block implements SimpleWaterloggedBlock, EntityBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
	private static final VoxelShape COLLISION_SHAPE = Shapes.empty();
	public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");
	public static final BooleanProperty VINES = BooleanProperty.create("vines");

	public BearTrapBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any()
				.setValue(TRIGGERED, Boolean.FALSE)
				.setValue(WATERLOGGED, Boolean.FALSE)
				.setValue(VINES, Boolean.FALSE));
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos,
	                             Player player, InteractionHand hand,
	                             BlockHitResult hitResult) {

		if (!level.isClientSide && hand.equals(InteractionHand.MAIN_HAND) && level.getBlockEntity(pos) instanceof BearTrapBlockEntity blockEntity) {
			ItemStack mainHandItem = player.getMainHandItem();

			if (state.getValue(TRIGGERED) && !blockEntity.hasTrappedEntity()) {
				level.setBlock(pos, state.setValue(TRIGGERED, false).setValue(VINES, false), 3);
				return InteractionResult.SUCCESS;
			}

			if (!state.getValue(VINES) && mainHandItem.getItem() == Items.VINE) {
				level.setBlock(pos, state.setValue(VINES, true), 3);
				if (!player.isCreative()) {
					mainHandItem.shrink(1);
				}
			}
		}

		return InteractionResult.PASS;
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
		return COLLISION_SHAPE;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new BearTrapBlockEntity(blockPos, blockState);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
	                                                              BlockEntityType<T> blockEntityType) {

		return (world, pos, state, entity) -> ((BearTrapBlockEntity) entity).tick(pos);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof Player player && player.isCreative() || entity.getType().is(EntityTypes.BOSSES)) {
			return;
		}

		if (level.getBlockEntity(pos) instanceof BearTrapBlockEntity bearTrapBlockEntity) {
			if (state.getValue(TRIGGERED)) {
				if (bearTrapBlockEntity.getTrappedEntity() == entity) {
					entity.makeStuckInBlock(state, new Vec3(0.0F, 0.0D, 0.0F));
					if ((entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
						double d0 = Math.abs(entity.getX() - entity.xOld);
						double d1 = Math.abs(entity.getZ() - entity.zOld);
						if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
							entity.hurt(IWDamageSources.BEAR_TRAP, 1.0F);
						}
					}
				}
				return;
			}

			if (entity instanceof LivingEntity livingEntity) {
				level.setBlock(pos, state.setValue(TRIGGERED, true).setValue(VINES, !state.getValue(VINES)), 3);

				level.setBlock(pos, state.setValue(TRIGGERED, true), 3);
				livingEntity.hurt(IWDamageSources.BEAR_TRAP, 2.0F);
				level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEventRegistry.BEAR_TRAP_CLOSE.get(),
						SoundSource.BLOCKS, 1f, 1f, false);

				bearTrapBlockEntity.setTrappedEntity(livingEntity);
			}
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(WATERLOGGED, context.getLevel()
				.getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		return state.getValue(TRIGGERED) ? 15 : 0;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TRIGGERED, VINES, WATERLOGGED);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isSignalSource(BlockState state) {
		return state.getValue(TRIGGERED);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getSignal(BlockState blockState, BlockGetter getter, BlockPos pos, Direction side) {
		if (!blockState.isSignalSource()) {
			return 0;
		} else {
			return 15;
		}
	}
}