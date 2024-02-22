package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;

public interface SummoningStaff {

	@Nullable
	default BlockPos getBlockLookingAt(Player player, Level level, int maxRange) {
		BlockHitResult result = level.clip(
				new ClipContext(
						player.getEyePosition(1.0f),
						player.getEyePosition(1.0f)
								.add(player.getLookAngle().x() * maxRange,
										player.getLookAngle().y() * maxRange,
										player.getLookAngle().z() * maxRange),
						Block.OUTLINE,
						Fluid.NONE,
						player
				)
		);

		if (result.getType() == Type.BLOCK) {
			return result.getBlockPos();
		} else {
			return null;
		}
	}

	default void handleCooldown(Item item, @Nullable BlockPos lookingAt, Player player, InteractionHand hand) {
		handleCooldown(item, lookingAt, player, hand, getStaffCooldown());
	}

	default void handleCooldown(Item item, Player player, InteractionHand hand) {
		handleCooldown(item, BlockPos.ZERO, player, hand, getStaffCooldown());
	}

	default void handleCooldown(Item item, @Nullable BlockPos lookingAt, Player player, InteractionHand hand, int cooldown) {
		if (lookingAt != null) {
			if (!player.isCreative()) {
				player.getItemInHand(hand).hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(player.getUsedItemHand()));

				// Add a cooldown to the item
				player.getCooldowns().addCooldown(item, cooldown);
			}
		}
	}

	default int getMaxRange() {
		return 100;
	}

	default int getStaffCooldown() {
		return 100;
	}
}