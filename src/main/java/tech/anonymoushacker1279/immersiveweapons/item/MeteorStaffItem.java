package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EnchantmentRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class MeteorStaffItem extends Item implements SummoningStaff {

	public MeteorStaffItem(Properties properties) {
		super(properties);
	}

	// Summon a meteor entity at the location the player is looking at on right-click
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player,
	                                              InteractionHand hand) {

		BlockPos lookingAt = getBlockLookingAt(player, level, getMaxRange());

		if (!level.isClientSide) {
			if (lookingAt != null) {
				int enchantmentLevel = getEnchantmentLevel(player.getItemInHand(hand), EnchantmentRegistry.CELESTIAL_FURY.get());

				if (enchantmentLevel > 0 && player.isCrouching()) {
					for (int i = 0; i < 3; i++) {
						if (!MeteorEntity.create(level, player, player.getItemInHand(hand), lookingAt, null)) {
							// If the meteor entity could not be created (because the starting position was inside a solid blockLocation)
							// send a message to the player
							player.displayClientMessage(Component.translatable("immersiveweapons.item.meteor_staff.not_enough_clearance")
									.withStyle(ChatFormatting.RED), true);
							return InteractionResultHolder.fail(player.getItemInHand(hand));
						}
					}
					handleCooldown(this, lookingAt, player, hand, getStaffCooldown() * 3);
				} else {
					if (!MeteorEntity.create(level, player, player.getItemInHand(hand), lookingAt, null)) {
						// If the meteor entity could not be created (because the starting position was inside a solid blockLocation)
						// send a message to the player
						player.displayClientMessage(Component.translatable("immersiveweapons.item.meteor_staff.not_enough_clearance")
								.withStyle(ChatFormatting.RED), true);
						return InteractionResultHolder.fail(player.getItemInHand(hand));
					}
					handleCooldown(this, lookingAt, player, hand);
				}
			} else {
				return InteractionResultHolder.pass(player.getItemInHand(hand));
			}
		}

		return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
	}

	@Override
	public boolean useOnRelease(ItemStack stack) {
		return true;
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return Ingredient.of(ItemRegistry.CELESTIAL_FRAGMENT.get()).test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public int getMaxRange() {
		return ImmersiveWeapons.COMMON_CONFIG.meteorStaffMaxUseRange().get();
	}

	@Override
	public int getEnchantmentValue(ItemStack stack) {
		return 1;
	}
}