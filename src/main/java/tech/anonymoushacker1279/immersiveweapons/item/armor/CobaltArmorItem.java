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
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.IWKeyBinds;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.network.payload.CobaltArmorPayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class CobaltArmorItem extends ArmorItem {

	private final boolean isLeggings;

	public CobaltArmorItem(ArmorMaterial material, ArmorItem.Type armorType, Properties properties, boolean isLeggings) {
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
				? ImmersiveWeapons.MOD_ID + ":textures/armor/cobalt_layer_1.png"
				: ImmersiveWeapons.MOD_ID + ":textures/armor/cobalt_layer_2.png");
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
		if (entity instanceof Player player && player.getUUID().toString().equals("94f11dac-d1bc-46da-877b-c69f533f2da2")) {
			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.COBALT_HELMET.get() &&
					player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.COBALT_CHESTPLATE.get() &&
					player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.COBALT_LEGGINGS.get() &&
					player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.COBALT_BOOTS.get()) {

				boolean effectEnabled = player.getPersistentData().getBoolean("CobaltArmorEffectEnabled");

				if (level.isClientSide) {
					if (IWKeyBinds.TOGGLE_ARMOR_EFFECT.consumeClick()) {
						// Store the toggle variable in the player's NBT
						player.getPersistentData().putBoolean("CobaltArmorEffectEnabled", !effectEnabled);

						// Send packet to server
						PacketDistributor.SERVER.noArg().send(new CobaltArmorPayload(!effectEnabled));

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
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,
							1, 4, false, false));
					player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,
							1, 1, false, false));
					player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
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