package tech.anonymoushacker1279.immersiveweapons.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.blockentity.AstralCrystalBlockEntity;
import tech.anonymoushacker1279.immersiveweapons.item.crafting.AstralCrystalRecipe;
import tech.anonymoushacker1279.immersiveweapons.network.payload.AstralCrystalPayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.Arrays;
import java.util.List;

public class AstralCrystalBlock extends AmethystClusterBlock implements EntityBlock {

	public AstralCrystalBlock(int size, int offset, Properties properties) {
		super(size, offset, properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new AstralCrystalBlockEntity(blockPos, blockState);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
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
		}

		return InteractionResult.PASS;
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (level.getBlockEntity(pos) instanceof AstralCrystalBlockEntity entity && isBuiltOnPlatform(level, pos)) {
			float particleChance = entity.getFilledSlots() * 0.25f;

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

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
		if (entity instanceof ItemEntity itemEntity && isBuiltOnPlatform(level, pos)) {
			if (level.getBlockEntity(pos) instanceof AstralCrystalBlockEntity blockEntity && level instanceof ServerLevel serverLevel) {
				RecipeHolder<AstralCrystalRecipe> holder = blockEntity.getRecipe(serverLevel.recipeAccess(), serverLevel);

				if (holder != null) {
					AstralCrystalRecipe recipe = holder.value();
					if (recipe.catalyst().test(itemEntity.getItem())) {
						serverLevel.addFreshEntity(new ItemEntity(serverLevel,
								itemEntity.getX(), itemEntity.getY() + 0.5f, itemEntity.getZ(),
								recipe.result()));

						blockEntity.getInventory().clear();
						serverLevel.destroyBlock(pos, false);

						// Send a packet to the client, so it can clear its inventory and add effects
						PacketDistributor.sendToPlayersTrackingChunk(serverLevel, entity.chunkPosition(), new AstralCrystalPayload(pos));

						itemEntity.remove(RemovalReason.DISCARDED);
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