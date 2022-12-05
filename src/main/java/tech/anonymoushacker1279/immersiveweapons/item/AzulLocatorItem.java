package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AzulLocatorItem extends Item {

	public AzulLocatorItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean useOnRelease(ItemStack stack) {
		return super.useOnRelease(stack);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
		player.startUsingItem(usedHand);

		return InteractionResultHolder.consume(player.getItemInHand(usedHand));
	}

	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
		if (livingEntity instanceof Player player) {
			if (player.getUseItemRemainingTicks() > 1) {
				player.displayClientMessage(Component.translatable("immersiveweapons.item.azul_locator.teleporting",
								player.getUseItemRemainingTicks() / 20)
						.withStyle(ChatFormatting.GOLD), true);
			}
			if (player.getUseItemRemainingTicks() == 1) {
				if (!level.isClientSide) {
					ServerPlayer serverPlayer = (ServerPlayer) player;
					BlockPos spawnPos = serverPlayer.getRespawnPosition();

					if (spawnPos == null) {
						player.displayClientMessage(Component.translatable("immersiveweapons.item.azul_locator.no_spawn")
								.withStyle(ChatFormatting.RED), true);
					} else {
						player.teleportTo(spawnPos.getX() + 0.5f, spawnPos.getY(), spawnPos.getZ() + 0.5f);
						player.displayClientMessage(Component.translatable("immersiveweapons.item.azul_locator.teleported")
								.withStyle(ChatFormatting.GREEN), true);

						if (!player.isCreative()) {
							stack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(player.getUsedItemHand()));
						} else {
							player.getCooldowns().addCooldown(this, 60);
						}
					}
				}
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 60;
	}
}