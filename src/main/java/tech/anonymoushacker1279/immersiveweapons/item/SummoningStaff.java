package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

	default void handleCooldown(@Nullable BlockPos lookingAt, Player player, InteractionHand hand) {
		handleCooldown(lookingAt, player, hand, getStaffCooldown());
	}

	default void handleCooldown(Player player, InteractionHand hand) {
		handleCooldown(BlockPos.ZERO, player, hand, getStaffCooldown());
	}

	default void handleCooldown(@Nullable BlockPos lookingAt, Player player, InteractionHand hand, int cooldown) {
		if (lookingAt != null) {
			if (!player.isCreative()) {
				ItemStack stack = player.getItemInHand(hand);
				stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
				player.getCooldowns().addCooldown(stack, cooldown);
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