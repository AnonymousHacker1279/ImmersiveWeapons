package com.anonymoushacker1279.immersiveweapons.block;

import com.anonymoushacker1279.immersiveweapons.entity.projectile.MortarShellEntity;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class MortarBlock extends HorizontalBlock {

	public static final IntegerProperty ROTATION = IntegerProperty.create("rotation", 0, 2);
	public static final BooleanProperty LOADED = BooleanProperty.create("loaded");
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);

	public MortarBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ROTATION, 0).setValue(LOADED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(ROTATION, FACING, LOADED);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext selectionContext) {
		return SHAPE;
	}

	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isClientSide && handIn.equals(Hand.MAIN_HAND)) {
			ItemStack itemStack = player.getMainHandItem();
			if (state.getValue(LOADED) && itemStack.getItem() == Items.FLINT_AND_STEEL) {
				if (!player.isCreative()) {
					itemStack.setDamageValue(itemStack.getDamageValue() - 1);
				}
				this.fire(worldIn, pos, state);
				return ActionResultType.CONSUME;
			} else if (!state.getValue(LOADED) && itemStack.getItem() == DeferredRegistryHandler.MORTAR_SHELL.get()) {
				worldIn.setBlock(pos, state.setValue(LOADED, true), 3);
				if (!player.isCreative()) {
					itemStack.shrink(1);
				}
				return ActionResultType.CONSUME;
			} else if (itemStack.getItem() == Items.AIR && player.isCrouching()) {
				if (state.getValue(LOADED)) {
					if (!player.isCreative()) {
						player.inventory.add(new ItemStack(DeferredRegistryHandler.MORTAR_SHELL.get()));
					}
					worldIn.setBlock(pos, state.setValue(LOADED, false), 3);
				}
				return ActionResultType.SUCCESS;
			} else if (itemStack.getItem() == Items.AIR) {
				if (state.getValue(ROTATION) < 2) {
					worldIn.setBlock(pos, state.setValue(ROTATION, state.getValue(ROTATION) + 1), 3);
				} else {
					worldIn.setBlock(pos, state.setValue(ROTATION, 0), 3);
				}
			}

			return ActionResultType.SUCCESS;
		}

		return ActionResultType.PASS;
	}

	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (!worldIn.isClientSide) {
			if (state.getValue(LOADED) && worldIn.hasNeighborSignal(pos)) {
				this.fire(worldIn, pos, state);
			}
		}
	}

	private void fire(World worldIn, BlockPos pos, BlockState state) {
		PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> worldIn.getChunkAt(pos)), new MortarBlockPacketHandler(pos));
		worldIn.setBlock(pos, state.setValue(LOADED, false), 3);
		MortarShellEntity.create(worldIn, pos, 1f, state);
	}

	public static class MortarBlockPacketHandler {

		private final BlockPos blockPos;

		public MortarBlockPacketHandler(final BlockPos blockPos) {
			this.blockPos = blockPos;
		}

		public static void encode(final MortarBlockPacketHandler msg, final PacketBuffer packetBuffer) {
			packetBuffer.writeBlockPos(msg.blockPos);
		}

		public static MortarBlockPacketHandler decode(final PacketBuffer packetBuffer) {
			return new MortarBlockPacketHandler(packetBuffer.readBlockPos());
		}

		public static void handle(final MortarBlockPacketHandler msg, final Supplier<Context> contextSupplier) {
			final NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		@OnlyIn(Dist.CLIENT)
		private static void handleOnClient(final MortarBlockPacketHandler msg) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				minecraft.level.playLocalSound(msg.blockPos, SoundEvents.TNT_PRIMED, SoundCategory.BLOCKS, 0.7f, 1f, false);
			}
		}
	}
}