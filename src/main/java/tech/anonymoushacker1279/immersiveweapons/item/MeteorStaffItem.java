package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class MeteorStaffItem extends Item {

	public MeteorStaffItem(Properties properties) {
		super(properties);
	}

	// Summon a meteor entity at the location the player is looking at on right-click
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player,
	                                              InteractionHand hand) {

		BlockPos lookingAt = getBlockLookingAt(player, level);

		if (!level.isClientSide) {

			if (lookingAt != null) {
				MeteorEntity.create(level, player, lookingAt);
			} else {
				return InteractionResultHolder.pass(player.getItemInHand(hand));
			}
		}

		if (lookingAt != null) {
			if (!player.isCreative()) {
				player.getItemInHand(hand).hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(player.getUsedItemHand()));

				// Add a cooldown to the item
				player.getCooldowns().addCooldown(this, 100);
			}
		}

		return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
	}

	@Nullable
	private BlockPos getBlockLookingAt(Player player, Level level) {
		int maxRange = CommonConfig.METEOR_STAFF_MAX_USE_RANGE.get();
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

	@Override
	public boolean useOnRelease(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return Ingredient.of(ItemRegistry.CELESTIAL_FRAGMENT.get()).test(repair) || super.isValidRepairItem(toRepair, repair);
	}
}