package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CurseCleaningSoapItem extends Item {

	public CurseCleaningSoapItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
		// Get all curses in the player's persistent data
		if (level.isClientSide) {
			if (player.getPersistentData().isEmpty()) {
				player.displayClientMessage(Component.translatable("immersiveweapons.item.curse_cleaning_soap.not_cursed")
						.withStyle(ChatFormatting.YELLOW), true);
			}
			for (String key : player.getPersistentData().getAllKeys()) {
				if (key.matches("used_curse_accessory_.*")) {
					// Remove the tag from the player's persistent data
					player.getPersistentData().remove(key);

					player.displayClientMessage(Component.translatable("immersiveweapons.item.curse_cleaning_soap.cleaned")
							.withStyle(ChatFormatting.GREEN), true);
				} else {
					player.displayClientMessage(Component.translatable("immersiveweapons.item.curse_cleaning_soap.not_cursed")
							.withStyle(ChatFormatting.YELLOW), true);
				}
			}
		}

		if (!level.isClientSide) {
			// Regex search for "used_curse_accessory_" in persistent data
			for (String key : player.getPersistentData().getAllKeys()) {
				if (key.matches("used_curse_accessory_.*")) {
					// Remove the tag from the player's persistent data
					player.getPersistentData().remove(key);

					// Clear effects
					player.removeAllEffects();

					return InteractionResultHolder.success(player.getItemInHand(usedHand));
				}
			}
		}

		return InteractionResultHolder.pass(player.getItemInHand(usedHand));
	}
}