package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.data.IWEnchantments;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.MeteorEntity;

public class MeteorStaffItem extends Item implements SummoningStaff {

	public MeteorStaffItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {

		BlockPos lookingAt = getBlockLookingAt(player, level, getMaxRange());
		ItemStack itemInHand = player.getItemInHand(hand);

		if (!level.isClientSide) {
			if (lookingAt != null) {
				HolderGetter<Enchantment> enchantmentGetter = player.registryAccess().lookup(Registries.ENCHANTMENT).orElseThrow();

				int enchantmentLevel = itemInHand.getEnchantmentLevel(enchantmentGetter.getOrThrow(IWEnchantments.CELESTIAL_FURY));

				if (enchantmentLevel > 0 && player.isCrouching()) {
					for (int i = 0; i < 3; i++) {
						if (!MeteorEntity.create(level, player, itemInHand, lookingAt, null)) {
							player.displayClientMessage(Component.translatable("immersiveweapons.item.meteor_staff.not_enough_clearance")
									.withStyle(ChatFormatting.RED), true);
							return InteractionResult.FAIL;
						}
					}
					handleCooldown(lookingAt, player, hand, getStaffCooldown() * 3);
				} else {
					if (!MeteorEntity.create(level, player, itemInHand, lookingAt, null)) {
						player.displayClientMessage(Component.translatable("immersiveweapons.item.meteor_staff.not_enough_clearance")
								.withStyle(ChatFormatting.RED), true);
						return InteractionResult.FAIL;
					}
					handleCooldown(lookingAt, player, hand);
				}
			} else {
				return InteractionResult.PASS;
			}
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	public boolean useOnRelease(ItemStack stack) {
		return true;
	}

	@Override
	public int getMaxRange() {
		return IWConfigs.SERVER.meteorStaffMaxUseRange.getAsInt();
	}
}