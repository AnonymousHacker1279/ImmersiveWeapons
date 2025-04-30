package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.network.payload.VoidArmorPayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class VoidArmorItem extends ArmorItem {

	private int dashCooldown = 0;

	public VoidArmorItem(ArmorMaterial material, ArmorType armorType, Properties properties) {
		super(material, armorType, properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		if (entity instanceof Player player) {
			if (ArmorUtils.isWearingVoidArmor(player)) {
				boolean effectEnabled = player.getPersistentData().getBoolean("VoidArmorEffectEnabled");

				if (level.isClientSide) {
					if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
						// Store the toggle variable in the player's NBT
						player.getPersistentData().putBoolean("VoidArmorEffectEnabled", !effectEnabled);

						// Send packet to server
						PacketDistributor.sendToServer(new VoidArmorPayload(!effectEnabled, false));

						if (effectEnabled) {
							player.displayClientMessage(Component.translatable("immersiveweapons.armor_effects.disabled")
									.withStyle(ChatFormatting.RED), true);
						} else {
							player.displayClientMessage(Component.translatable("immersiveweapons.armor_effects.enabled")
									.withStyle(ChatFormatting.GREEN), true);
						}
					}
					if (effectEnabled) {
						if (Minecraft.getInstance().options.keyJump.consumeClick()) {
							level.addParticle(ParticleTypes.CLOUD,
									player.getX(),
									player.getY() + 0.1d,
									player.getZ(),
									GeneralUtilities.getRandomNumber(-0.04d, 0.04d),
									GeneralUtilities.getRandomNumber(0.0d, 0.04d),
									GeneralUtilities.getRandomNumber(-0.04d, 0.04d));
						}
						if (IWKeyBinds.ARMOR_ACTION.consumeClick()) {
							if (dashCooldown == 0) {
								Vec3 viewVector = player.getViewVector(1);
								Vec3 dashVector = new Vec3(viewVector.x(), player.getDeltaMovement().y(), viewVector.z()).scale(1.2d);
								player.setNoGravity(true);
								player.setDeltaMovement(dashVector);

								if (player.isShiftKeyDown()) {
									PacketDistributor.sendToServer(new VoidArmorPayload(true, true));
								}

								dashCooldown = 60;
							}
						}
					}

					if (dashCooldown > 0) {
						if (dashCooldown < 50 && player.isNoGravity()) {
							player.setNoGravity(false);
						}

						level.addParticle(ParticleTypes.ELECTRIC_SPARK,
								player.getX(), player.getY(), player.getZ(),
								GeneralUtilities.getRandomNumber(-0.04d, 0.04d),
								GeneralUtilities.getRandomNumber(0.0d, 0.04d),
								GeneralUtilities.getRandomNumber(-0.04d, 0.04d));

						dashCooldown--;

						if (dashCooldown == 0) {
							player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
						}
					}
				}

				if (effectEnabled) {
					player.addEffect(new MobEffectInstance(MobEffects.JUMP,
							0, 1, false, false));
					player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
							0, 0, false, false));
					player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED,
							0, 0, false, false));
				}
			}
		}
	}
}