package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.config.IWConfigs;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.network.payload.TeslaArmorPayload;

import javax.annotation.Nullable;
import java.util.List;

public class TeslaArmorItem extends Item {

	private int noiseCooldown = 0;

	public TeslaArmorItem(ArmorMaterial material, ArmorType armorType, Properties properties) {
		super(properties.humanoidArmor(material, armorType));
	}

	@Override
	public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
		if (entity instanceof Player player) {
			if (ArmorUtils.isWearingTeslaArmor(player)) {
				String data = player.getPersistentData().getString("TeslaArmorEffectState").orElse("");
				EffectState state = data.isEmpty() ? EffectState.DISABLED : EffectState.getFromString(data);

				if (level.isClientSide) {
					if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
						// Store the toggle variable in the player's NBT
						player.getPersistentData().putString("TeslaArmorEffectState", state.getNext().getSerializedName());

						// Send packet to server
						state = state.getNext();
						PacketDistributor.sendToServer(new TeslaArmorPayload(state));

						if (state == EffectState.DISABLED) {
							level.playSound(player,
									player.blockPosition(),
									SoundEventRegistry.TESLA_ARMOR_POWER_DOWN.get(),
									SoundSource.PLAYERS,
									0.9f,
									1.0f);

						} else {
							level.playSound(player,
									player.blockPosition(),
									SoundEventRegistry.TESLA_ARMOR_POWER_UP.get(),
									SoundSource.PLAYERS,
									0.9f,
									1.0f);

							noiseCooldown = 0;
						}

						if (state == EffectState.DISABLED) {
							player.displayClientMessage(Component.translatable("immersiveweapons.armor_effects.disabled")
									.withStyle(ChatFormatting.RED), true);
						} else if (state == EffectState.EFFECT_MOBS) {
							player.displayClientMessage(Component.translatable("immersiveweapons.armor_effects.tesla_armor.effect_mobs")
									.withStyle(ChatFormatting.GREEN), true);
						} else if (state == EffectState.EFFECT_EVERYTHING) {
							player.displayClientMessage(Component.translatable("immersiveweapons.armor_effects.tesla_armor.effect_everything")
									.withStyle(ChatFormatting.GREEN), true);
						}
					}
				}

				if (state != EffectState.DISABLED && player.tickCount % 20 == 0) {
					List<Entity> nearbyEntities = level.getEntities(player, player.getBoundingBox().inflate(3));

					// Remove any players in the list that are on the same team
					nearbyEntities.removeIf(nearbyEntity -> nearbyEntity instanceof Player && nearbyEntity.isAlliedTo(player));

					if (!nearbyEntities.isEmpty()) {
						for (Entity nearbyEntity : nearbyEntities) {
							if (state == EffectState.EFFECT_EVERYTHING) {
								if (nearbyEntity instanceof LivingEntity livingEntity) {
									handleEffect(livingEntity, level, player);
								}
							} else if (state == EffectState.EFFECT_MOBS) {
								if (nearbyEntity instanceof LivingEntity livingEntity && !(nearbyEntity instanceof Player)) {
									handleEffect(livingEntity, level, player);
								}
							}
						}
					}
				}

			}
		}
	}

	private void handleEffect(LivingEntity livingEntity, Level level, Player player) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,
				100, 0, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS,
				100, 0, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.NAUSEA,
				100, 0, false, false));

		if (level.isClientSide) {
			effectNoise(level, player);
		}
	}

	/**
	 * Play a sound while the armor effect is toggled.
	 *
	 * @param level  the <code>Level</code> the player is in
	 * @param player the <code>Player</code> instance
	 */
	private void effectNoise(Level level, Player player) {
		if (noiseCooldown == 0 && IWConfigs.CLIENT.teslaArmorEffectSound.getAsBoolean()) {
			level.playSound(player,
					player.blockPosition(),
					SoundEventRegistry.TESLA_ARMOR_EFFECT.get(),
					SoundSource.NEUTRAL,
					0.65f,
					1);

			noiseCooldown = 120;
		} else if (noiseCooldown > 0) {
			noiseCooldown--;
		}
	}

	public enum EffectState implements StringRepresentable {
		EFFECT_EVERYTHING("effect_everything"),
		EFFECT_MOBS("effect_mobs"),
		DISABLED("disabled");

		private final String name;

		EffectState(String name) {
			this.name = name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}

		public EffectState getNext() {
			return values()[(ordinal() + 1) % values().length];
		}

		public static EffectState getFromString(String name) {
			for (EffectState state : values()) {
				if (state.getSerializedName().equals(name)) {
					return state;
				}
			}
			return DISABLED;
		}
	}
}