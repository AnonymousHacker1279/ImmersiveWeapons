package tech.anonymoushacker1279.immersiveweapons.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class ChampionKeycardBlock extends Block {

	public ChampionKeycardBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (hand.equals(InteractionHand.MAIN_HAND)) {
			// If the player is holding a Champion Keycard item, break
			if (player.getItemInHand(hand).getItem() == ItemRegistry.CHAMPION_KEYCARD.get()) {
				level.destroyBlock(pos, false);

				propagateToNearbyBlocks(level, pos, player, hand, result);

				if (!player.isCreative()) {
					player.getItemInHand(hand).shrink(1);
				}

				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.PASS;
	}

	/**
	 * Break neighboring keycard blocks that share a side with the block that was broken
	 */
	private void propagateToNearbyBlocks(Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		BlockPos[] nearbyBlocks = new BlockPos[]{
				pos.north(),
				pos.south(),
				pos.east(),
				pos.west(),
				pos.above(),
				pos.below()
		};

		for (BlockPos nearbyBlock : nearbyBlocks) {
			if (level.getBlockState(nearbyBlock).getBlock() instanceof ChampionKeycardBlock championKeycardBlock) {
				// Call the use method on the neighboring block, which will call this method again
				championKeycardBlock.useItemOn(player.getItemInHand(hand), level.getBlockState(nearbyBlock), level, nearbyBlock, player, hand, hitResult);
			}
		}
	}
}