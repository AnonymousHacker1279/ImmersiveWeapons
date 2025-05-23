package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.IWDamageSources;

public class LandmineBlock extends Block implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 3.5D, 14.0D);
	public static final BooleanProperty ARMED = BooleanProperty.create("armed");
	public static final BooleanProperty SAND = BooleanProperty.create("sand");
	public static final BooleanProperty VINES = BooleanProperty.create("vines");

	public LandmineBlock(BlockBehaviour.Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any()
				.setValue(ARMED, Boolean.FALSE)
				.setValue(WATERLOGGED, Boolean.FALSE)
				.setValue(VINES, Boolean.FALSE)
				.setValue(SAND, Boolean.FALSE));

	}

	private static void explode(Level level, BlockPos pos, @Nullable LivingEntity livingEntity) {
		if (!level.isClientSide) {
			level.explode(livingEntity, IWDamageSources.landmine(level.registryAccess()), null, pos.getX(), pos.getY(), pos.getZ(), 2.0F, false, ExplosionInteraction.BLOCK);
			level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F, false);
		}
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (!level.isClientSide) {
			ItemStack currentlyHeldItem = player.getMainHandItem();

			// Disarm with pliers if armed
			if (state.getValue(ARMED) && currentlyHeldItem.getItem() == ItemRegistry.PLIERS.get()) {
				level.setBlock(pos, state.setValue(ARMED, false), 3);
				return InteractionResult.PASS;
			}

			// Arm if not currently set
			if (!state.getValue(ARMED) && currentlyHeldItem.isEmpty()) {
				level.setBlock(pos, state.setValue(ARMED, true), 3);
			}

			// Add vines if not already camouflaged
			if (!state.getValue(VINES) && !state.getValue(SAND) && currentlyHeldItem.getItem() == Items.VINE) {
				level.setBlock(pos, state.setValue(VINES, true), 3);
				if (!player.isCreative()) {
					currentlyHeldItem.shrink(1);
				}

				return InteractionResult.CONSUME;
			}

			// Add sand if not already camouflaged
			if (!state.getValue(SAND) && !state.getValue(VINES) && currentlyHeldItem.getItem() == Items.SAND) {
				level.setBlock(pos, state.setValue(SAND, true), 3);
				if (!player.isCreative()) {
					currentlyHeldItem.shrink(1);
				}

				return InteractionResult.CONSUME;
			}
		}

		return InteractionResult.PASS;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext selectionContext) {
		return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext selectionContext) {
		return Shapes.empty();
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		// Ensure the block is on solid ground
		BlockPos blockpos = pos.below();
		BlockState blockstate = level.getBlockState(blockpos);
		return blockstate.isFaceSturdy(level, blockpos, Direction.UP);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
		if (state.getValue(ARMED) && !state.getValue(WATERLOGGED)) {
			if (entity instanceof Player player && player.isCreative()) {
				return;
			}

			if (entity instanceof LivingEntity livingEntity) {
				explode(level, pos, livingEntity);
			}
		}

		if (entity instanceof Projectile) {
			explode(level, pos, null);
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ARMED, SAND, VINES, WATERLOGGED);
	}

	@Override
	public void wasExploded(ServerLevel level, BlockPos pos, Explosion explosion) {
		explode(level, pos, null);
	}

	@Override
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (!level.isClientSide() && !player.isCreative() && state.getValue(ARMED)) {
			explode(level, pos, player);
		}

		return super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	public boolean canDropFromExplosion(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
		return false;
	}
}