package tech.anonymoushacker1279.immersiveweapons.item.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.config.ClientConfig;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.SoundEventRegistry;
import tech.anonymoushacker1279.immersiveweapons.network.payload.TeslaArmorPayload;

import java.util.List;

public class TeslaArmorItem extends ArmorItem {

	private final boolean isLeggings;
	private int countdown = 0;

	public TeslaArmorItem(ArmorMaterial material, ArmorItem.Type armorType, Properties properties, boolean isLeggings) {
		super(material, armorType, properties);
		this.isLeggings = isLeggings;
	}

	/**
	 * Get the armor texture.
	 *
	 * @param stack  the <code>ItemStack</code> instance
	 * @param entity the <code>Entity</code> wearing the armor
	 * @param slot   the <code>EquipmentSlot</code>
	 * @param type   type ID
	 * @return String
	 */
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return (!isLeggings
				? ImmersiveWeapons.MOD_ID + ":textures/armor/tesla_layer_1.png"
				: ImmersiveWeapons.MOD_ID + ":textures/armor/tesla_layer_2.png");
	}

	/**
	 * Runs once per tick while armor is equipped
	 *
	 * @param stack  the <code>ItemStack</code> instance
	 * @param level  the <code>Level</code> the player is in
	 * @param player the <code>Player</code> wearing the armor
	 */
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.TESLA_HELMET.get() &&
				player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.TESLA_CHESTPLATE.get() &&
				player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.TESLA_LEGGINGS.get() &&
				player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.TESLA_BOOTS.get()) {

			String data = player.getPersistentData().getString("TeslaArmorEffectState");
			EffectState state = data.isEmpty() ? EffectState.DISABLED : EffectState.getFromString(data);

			if (level.isClientSide) {
				if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
					// Store the toggle variable in the player's NBT
					player.getPersistentData().putString("TeslaArmorEffectState", state.getNext().getSerializedName());

					// Send packet to server
					state = state.getNext();
					PacketDistributor.SERVER.noArg().send(new TeslaArmorPayload(state));

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

						countdown = 0;
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
				nearbyEntities.removeIf(entity -> entity instanceof Player && entity.isAlliedTo(player));

				if (!nearbyEntities.isEmpty()) {
					for (Entity entity : nearbyEntities) {
						if (state == EffectState.EFFECT_EVERYTHING) {
							if (entity instanceof LivingEntity livingEntity) {
								handleEffect(livingEntity, level, player);
							}
						} else if (state == EffectState.EFFECT_MOBS) {
							if (entity instanceof LivingEntity livingEntity && !(entity instanceof Player)) {
								handleEffect(livingEntity, level, player);
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
		livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
				100, 0, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,
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
		if (countdown == 0 && ClientConfig.TESLA_ARMOR_EFFECT_SOUND.get()) {
			level.playSound(player,
					player.blockPosition(),
					SoundEventRegistry.TESLA_ARMOR_EFFECT.get(),
					SoundSource.NEUTRAL,
					0.65f,
					1);

			countdown = 120;
		} else if (countdown > 0) {
			countdown--;
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