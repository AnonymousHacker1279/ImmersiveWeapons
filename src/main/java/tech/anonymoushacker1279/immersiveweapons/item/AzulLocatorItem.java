package tech.anonymoushacker1279.immersiveweapons.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Set;

public class AzulLocatorItem extends Item {

	public AzulLocatorItem(Properties properties) {
		super(properties);
	}

	@Override
	public boolean useOnRelease(ItemStack stack) {
		return super.useOnRelease(stack);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
		player.startUsingItem(usedHand);

		return InteractionResult.CONSUME;
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
				if (player instanceof ServerPlayer serverPlayer) {
					if (serverPlayer.getRespawnConfig() == null) {
						player.displayClientMessage(Component.translatable("immersiveweapons.item.azul_locator.no_spawn")
								.withStyle(ChatFormatting.RED), true);
						player.getCooldowns().addCooldown(stack, 60);
					} else {
						BlockPos spawnPos = serverPlayer.getRespawnConfig().pos();
						if (serverPlayer.getServer() != null) {
							ServerLevel spawnLevel = serverPlayer.getServer().getLevel(serverPlayer.getRespawnConfig().dimension());

							if (spawnLevel != null) {
								serverPlayer.teleportTo(spawnLevel, spawnPos.getX() + 0.5f, spawnPos.getY(), spawnPos.getZ() + 0.5f, Set.of(), player.getYRot(), player.getXRot(), false);
								player.teleportTo(spawnPos.getX() + 0.5f, spawnPos.getY(), spawnPos.getZ() + 0.5f);
								player.displayClientMessage(Component.translatable("immersiveweapons.item.azul_locator.teleported")
										.withStyle(ChatFormatting.GREEN), true);
							}
						}

						if (!player.isCreative()) {
							stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
						} else {
							player.getCooldowns().addCooldown(stack, 60);
						}
					}
				}
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 60;
	}
}