package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AstralCrystalBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.init.RecipeTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AstralCrystalRecipe;
import tech.anonymoushacker1279.immersiveweapons.network.payload.AstralCrystalPayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.*;

public class AstralCrystalBlock extends AmethystClusterBlock implements EntityBlock {

	public static List<RecipeHolder<AstralCrystalRecipe>> RECIPES = new ArrayList<>(5);

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


	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (isBuiltOnPlatform(level, pos)) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof AstralCrystalBlockEntity crystalBlockEntity) {
				ItemStack itemStack = player.getItemInHand(hand);
				if (itemStack.isEmpty()) {
					// If not holding anything, remove the last added item
					crystalBlockEntity.removeItem();
					return ItemInteractionResult.SUCCESS;
				}
				if (!level.isClientSide) {
					crystalBlockEntity.addItem(player.isCreative() ? itemStack.copy() : itemStack);

					return ItemInteractionResult.SUCCESS;
				}

				return ItemInteractionResult.CONSUME;
			}

			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		}

		return ItemInteractionResult.FAIL;
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
			for (RecipeHolder<AstralCrystalRecipe> recipe : RECIPES) {
				int primaryMaterialInInventory = entity.getInventory().stream()
						.map(ItemStack::getItem)
						.filter(item -> recipe.value().getPrimaryMaterial().test(item.getDefaultInstance())).toArray().length;

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
				if (level instanceof ServerLevel serverLevel) {
					for (RecipeHolder<AstralCrystalRecipe> recipe : RECIPES) {
						int primaryMaterialInInventory = crystalBlockEntity.getInventory().stream()
								.map(ItemStack::getItem)
								.filter(item -> recipe.value().getPrimaryMaterial().test(item.getDefaultInstance())).toArray().length;

						if (primaryMaterialInInventory == 4) {
							ItemStack itemStack = itemEntity.getItem();
							if (recipe.value().getSecondaryMaterial().test(itemStack)) {
								serverLevel.addFreshEntity(new ItemEntity(serverLevel,
										itemEntity.getX(), itemEntity.getY() + 0.5f, itemEntity.getZ(),
										recipe.value().getResultItem(serverLevel.registryAccess())));

								crystalBlockEntity.getInventory().clear();
								serverLevel.destroyBlock(pos, false);

								// Send a packet to the client, so it can clear its inventory and add effects
								PacketDistributor.sendToPlayersTrackingChunk(serverLevel, entity.chunkPosition(), new AstralCrystalPayload(pos));

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
}