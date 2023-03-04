package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AstralCrystalBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AstralCrystalRecipe;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.*;
import java.util.function.Supplier;

public class AstralCrystalBlock extends AmethystClusterBlock implements EntityBlock {

	public static List<AstralCrystalRecipe> RECIPES = new ArrayList<>(5);

	public AstralCrystalBlock(int size, int offset, Properties properties) {
		super(size, offset, properties);
	}

	/**
	 * Create a block entity for the block.
	 *
	 * @param blockPos   the <code>BlockPos</code> the block is at
	 * @param blockState the <code>BlockState</code> of the block
	 * @return BlockEntity
	 */
	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new AstralCrystalBlockEntity(blockPos, blockState);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, level, pos, oldState, isMoving);

		RECIPES = level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.ASTRAL_CRYSTAL_RECIPE_TYPE.get());
	}

	/**
	 * Runs when the block is activated.
	 * Allows the block to respond to user interaction.
	 *
	 * @param state     the <code>BlockState</code> of the block
	 * @param level     the <code>Level</code> the block is in
	 * @param pos       the <code>BlockPos</code> the block is at
	 * @param player    the <code>Player</code> interacting with the block
	 * @param hand      the <code>InteractionHand</code> the Player used
	 * @param hitResult the <code>BlockHitResult</code> of the interaction
	 * @return InteractionResult
	 */
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (isBuiltOnPlatform(level, pos)) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof AstralCrystalBlockEntity crystalBlockEntity) {
				ItemStack itemStack = player.getItemInHand(hand);
				if (itemStack.isEmpty()) {
					// If not holding anything, remove the last added item
					crystalBlockEntity.removeItem();
					return InteractionResult.SUCCESS;
				}
				if (!level.isClientSide) {
					crystalBlockEntity.addItem(player.isCreative() ? itemStack.copy() : itemStack);
					return InteractionResult.SUCCESS;
				}
				return InteractionResult.CONSUME;
			}
			return InteractionResult.PASS;
		}
		return InteractionResult.FAIL;
	}

	/**
	 * Runs when the block is removed.
	 *
	 * @param state    the <code>BlockState</code> of the block
	 * @param level    the <code>Level</code> the block is in
	 * @param pos      the <code>BlockPos</code> the block is at
	 * @param newState the <code>BlockState</code> the block now has
	 * @param isMoving determines if the block is moving
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof AstralCrystalBlockEntity crystalBlockEntity) {
				Containers.dropContents(level, pos, crystalBlockEntity.getInventory());
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (level.getBlockEntity(pos) instanceof AstralCrystalBlockEntity entity && isBuiltOnPlatform(level, pos)) {
			for (AstralCrystalRecipe recipe : RECIPES) {
				int primaryMaterialInInventory = entity.getInventory().stream()
						.map(ItemStack::getItem)
						.filter(item -> recipe.getPrimaryMaterial().test(item.getDefaultInstance())).toArray().length;

				float particleChance = primaryMaterialInInventory * 0.25f;

				if (random.nextFloat() <= particleChance) {
					level.addParticle(ParticleTypes.ELECTRIC_SPARK,
							pos.getX() + 0.5D + (GeneralUtilities.getRandomNumber(-0.2D, 0.2D)),
							pos.getY() + 0.4D + (GeneralUtilities.getRandomNumber(0.2D, 0.5D)),
							pos.getZ() + 0.5D + (GeneralUtilities.getRandomNumber(-0.2D, 0.2D)),
							(GeneralUtilities.getRandomNumber(-0.16D, 0.16D)),
							(GeneralUtilities.getRandomNumber(0.13D, 0.16D)),
							(GeneralUtilities.getRandomNumber(-0.16D, 0.16D)));
				}
			}
		}
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof ItemEntity itemEntity && isBuiltOnPlatform(level, pos)) {
			if (level.getBlockEntity(pos) instanceof AstralCrystalBlockEntity crystalBlockEntity) {
				if (!level.isClientSide) {
					for (AstralCrystalRecipe recipe : RECIPES) {
						int primaryMaterialInInventory = crystalBlockEntity.getInventory().stream()
								.map(ItemStack::getItem)
								.filter(item -> recipe.getPrimaryMaterial().test(item.getDefaultInstance())).toArray().length;

						if (primaryMaterialInInventory == 4) {
							ItemStack itemStack = itemEntity.getItem();
							if (recipe.getSecondaryMaterial().test(itemStack)) {
								for (int i = 0; i < recipe.getResultCount(); i++) {
									level.addFreshEntity(new ItemEntity(level,
											itemEntity.getX(), itemEntity.getY() + 0.5f, itemEntity.getZ(),
											recipe.getResultItem()));
								}

								crystalBlockEntity.getInventory().clear();
								level.destroyBlock(pos, false);

								// Send a packet to the client, so it can clear its inventory and add effects
								PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)),
										new AstralCrystalBlockPacketHandler(pos));

								itemEntity.remove(RemovalReason.DISCARDED);
							}
						}
					}
				}
			}
		}
	}

	private boolean isBuiltOnPlatform(Level level, BlockPos pos) {
		Block topLeft = level.getBlockState(pos.below().north().west()).getBlock();
		Block topCenter = level.getBlockState(pos.below().north()).getBlock();
		Block topRight = level.getBlockState(pos.below().north().east()).getBlock();
		Block centerLeft = level.getBlockState(pos.below().west()).getBlock();
		Block center = level.getBlockState(pos.below()).getBlock();
		Block centerRight = level.getBlockState(pos.below().east()).getBlock();
		Block bottomLeft = level.getBlockState(pos.below().south().west()).getBlock();
		Block bottomCenter = level.getBlockState(pos.below().south()).getBlock();
		Block bottomRight = level.getBlockState(pos.below().south().east()).getBlock();

		List<Block> lapisBlocks = Arrays.asList(topLeft, topRight, center, bottomLeft, bottomRight);
		List<Block> redstoneBlocks = Arrays.asList(topCenter, centerLeft, centerRight, bottomCenter);

		for (Block block : lapisBlocks) {
			if (block != Blocks.LAPIS_BLOCK) {
				return false;
			}
		}
		for (Block block : redstoneBlocks) {
			if (block != Blocks.REDSTONE_BLOCK) {
				return false;
			}
		}

		return true;
	}

	public record AstralCrystalBlockPacketHandler(BlockPos pos) {

		/**
		 * Constructor for AstralCrystalBlockPacketHandler.
		 */
		public AstralCrystalBlockPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>AstralCrystalBlockPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(AstralCrystalBlockPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBlockPos(msg.pos);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return AstralCrystalBlockPacketHandler
		 */
		public static AstralCrystalBlockPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new AstralCrystalBlockPacketHandler(packetBuffer.readBlockPos());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(AstralCrystalBlockPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> runOnClient(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs when a packet is received
		 */
		private static void runOnClient(AstralCrystalBlockPacketHandler msg) {
			ClientLevel level = Minecraft.getInstance().level;

			int x = msg.pos.getX();
			int y = msg.pos.getY();
			int z = msg.pos.getZ();

			if (level != null) {
				level.addParticle(ParticleTypes.EXPLOSION_EMITTER,
						x + 0.5D + (GeneralUtilities.getRandomNumber(-0.2D, 0.2D)),
						y + 0.4D + (GeneralUtilities.getRandomNumber(0.2D, 0.5D)),
						z + 0.5D + (GeneralUtilities.getRandomNumber(-0.2D, 0.2D)),
						(GeneralUtilities.getRandomNumber(-0.16D, 0.16D)),
						(GeneralUtilities.getRandomNumber(0.13D, 0.16D)),
						(GeneralUtilities.getRandomNumber(-0.16D, 0.16D)));

				level.playLocalSound(x, y, z, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0f, 1.3f, false);

				if (level.getBlockEntity(msg.pos) instanceof AstralCrystalBlockEntity crystalBlockEntity) {
					crystalBlockEntity.getInventory().clear();
				}
			}
		}
	}
}