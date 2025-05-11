package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.network.payload.CobaltArmorPayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import javax.annotation.Nullable;

public class CobaltArmorItem extends Item {

	public CobaltArmorItem(ArmorMaterial material, ArmorType armorType, Properties properties) {
		super(properties.humanoidArmor(material, armorType));
	}

	@Override
	public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
		if (entity instanceof Player player && player.getUUID().toString().equals("94f11dac-d1bc-46da-877b-c69f533f2da2")) {
			if (ArmorUtils.isWearingCobaltArmor(player)) {

				boolean effectEnabled = player.getPersistentData().getBoolean("CobaltArmorEffectEnabled").orElse(false);

				if (level.isClientSide) {
					if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
						// Store the toggle variable in the player's NBT
						player.getPersistentData().putBoolean("CobaltArmorEffectEnabled", !effectEnabled);

						// Send packet to server
						PacketDistributor.sendToServer(new CobaltArmorPayload(!effectEnabled));

						if (effectEnabled) {
							player.displayClientMessage(Component.translatable("immersiveweapons.armor_effects.disabled")
									.withStyle(ChatFormatting.RED), true);
						} else {
							player.displayClientMessage(Component.translatable("immersiveweapons.armor_effects.enabled")
									.withStyle(ChatFormatting.GREEN), true);
						}
					}
				}

				if (effectEnabled) {
					player.addEffect(new MobEffectInstance(MobEffects.STRENGTH,
							1, 4, false, false));
					player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE,
							1, 1, false, false));
					player.addEffect(new MobEffectInstance(MobEffects.SPEED,
							1, 1, false, false));

					if (level instanceof ServerLevel serverLevel) {
						serverLevel.sendParticles(ParticleTypes.SOUL_FIRE_FLAME,
								player.getX(),
								player.getY() + 2.2D,
								player.getZ(),
								5,
								GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
								GeneralUtilities.getRandomNumber(0.0d, 0.001d),
								GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
								0.0D);
						serverLevel.sendParticles(ParticleTypes.FLAME,
								player.getX(),
								player.getY() + 2.2D,
								player.getZ(),
								5,
								GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
								GeneralUtilities.getRandomNumber(0.0d, 0.001d),
								GeneralUtilities.getRandomNumber(-0.01d, 0.01d),
								0.0D);
					}
				}
			}
		}
	}
}