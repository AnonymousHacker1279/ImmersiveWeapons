package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.blockentity.TeleporterBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class TeleporterBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D);

	public TeleporterBlock(Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
		if (!level.isClientSide) {
			boolean isPowered = level.hasNeighborSignal(pos);
			if (isPowered && level.getBlockEntity(pos) instanceof TeleporterBlockEntity blockEntity) {
				ResourceKey<Level> dimension = blockEntity.getLinkedTeleporterDimension();
				BlockPos linkedPos = blockEntity.getLinkedTeleporterPos();

				if (level.getServer() == null) {
					return;
				}

				if (dimension != null && linkedPos != null) {
					ServerLevel serverLevel = level.getServer().getLevel(dimension);

					if (serverLevel == null) {
						return;
					}

					boolean teleporterExists = serverLevel.getBlockState(linkedPos).getBlock() instanceof TeleporterBlock;
					AtomicBoolean didTeleport = new AtomicBoolean(false);
					AtomicBoolean didMoldBread = new AtomicBoolean(false);

					getEntitiesOnPlatform(level, pos).forEach(entity -> {
						if (teleporterExists) {
							didTeleport.set(true);
							entity.teleportTo(serverLevel,
									linkedPos.getX() + 0.5D,
									linkedPos.getY() + 1.0D,
									linkedPos.getZ() + 0.5D,
									Set.of(),
									entity.getYRot(),
									entity.getXRot(),
									false);

							if (entity instanceof ItemEntity itemEntity && itemEntity.getItem().is(Items.BREAD)) {
								didMoldBread.set(true);
								itemEntity.setItem(new ItemStack(ItemRegistry.MOLDY_BREAD.get(), itemEntity.getItem().getCount()));
							}
						} else if (entity instanceof Player player) {
							player.displayClientMessage(Component.translatable("immersiveweapons.block.teleporter.linked_teleporter_does_not_exist")
									.withStyle(ChatFormatting.RED), true);
						}
					});

					if (didTeleport.get()) {
						serverLevel.playSound(null, pos, SoundEvents.PLAYER_TELEPORT, SoundSource.BLOCKS, 1.0F, 1.0F);
						serverLevel.playSound(null, linkedPos, SoundEvents.PLAYER_TELEPORT, SoundSource.BLOCKS, 1.0F, 1.0F);
						serverLevel.sendParticles(ParticleTypes.PORTAL,
								pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D,
								32,
								0.5D, 0.5D, 0.5D,
								0.1D);

						serverLevel.sendParticles(ParticleTypes.PORTAL,
								linkedPos.getX() + 0.5D, linkedPos.getY() + 1.0D, linkedPos.getZ() + 0.5D,
								32,
								0.5D, 0.5D, 0.5D,
								0.1D);

						if (didMoldBread.get()) {
							serverLevel.playSound(null, linkedPos, SoundEvents.ZOMBIE_INFECT, SoundSource.BLOCKS, 1.0F, 1.0F);
							serverLevel.sendParticles(ParticleTypes.POOF,
									linkedPos.getX() + 0.5D, linkedPos.getY() + 1.0D, linkedPos.getZ() + 0.5D,
									32,
									0.5D, 0.5D, 0.5D,
									0.1D);
						}
					}
				} else {
					getEntitiesOnPlatform(level, pos.above()).forEach(entity -> {
						if (entity instanceof Player player) {
							player.displayClientMessage(Component.translatable("immersiveweapons.block.teleporter.no_linked_teleporter")
									.withStyle(ChatFormatting.RED), true);
						}
					});
				}
			}
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TeleporterBlockEntity(pos, state);
	}

	private List<Entity> getEntitiesOnPlatform(Level level, BlockPos pos) {
		return level.getEntitiesOfClass(Entity.class, new AABB(pos));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(WATERLOGGED, context.getLevel()
				.getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}
}