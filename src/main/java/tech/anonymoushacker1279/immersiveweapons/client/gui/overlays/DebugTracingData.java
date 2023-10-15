package tech.anonymoushacker1279.immersiveweapons.client.gui.overlays;

import com.google.common.collect.Multimap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import tech.anonymoushacker1279.immersiveweapons.event.SyncHandler;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.AccessoryManager;
import tech.anonymoushacker1279.immersiveweapons.init.EffectRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * This class contains data for debug tracing.
 */
public class DebugTracingData {

	public static boolean isDebugTracingEnabled = false;

	public static float meleeItemDamage = 0;

	public static float lastDamageDealt = 0;
	public static float lastDamageTaken = 0;

	public static float gunBaseVelocity = 0;
	public static Item selectedAmmo = Items.AIR;
	public static Item selectedPowder = Items.AIR;
	public static double liveBulletDamage = 0;
	public static boolean isBulletCritical = false;

	public static double GENERAL_DAMAGE_BONUS = 0;
	public static double MELEE_DAMAGE_BONUS = 0;
	public static double PROJECTILE_DAMAGE_BONUS = 0;

	public static double ARMOR_VALUE = 0;
	public static double ARMOR_TOUGHNESS_VALUE = 0;
	public static double GENERAL_DAMAGE_RESISTANCE = 0;
	public static double KNOCKBACK_RESISTANCE = 0;

	public static float CELESTIAL_PROTECTION_NO_DAMAGE_CHANCE = 0;

	public static int TICKS_SINCE_REST = 0;

	public static void handleTracing(Player player) {
		if (player.tickCount % 20 == 0) {
			// Get the melee damage attribute of the currently held item
			ItemStack heldItem = player.getMainHandItem();
			Multimap<Attribute, AttributeModifier> modifiers = heldItem.getAttributeModifiers(EquipmentSlot.MAINHAND);

			if (modifiers.isEmpty()) {
				meleeItemDamage = 0;
			} else {
				modifiers.forEach((attribute, modifier) -> {
					if (attribute.equals(Attributes.ATTACK_DAMAGE)) {
						double damage = modifier.getAmount() + 1;
						damage += EnchantmentHelper.getDamageBonus(heldItem, MobType.UNDEFINED);
						meleeItemDamage = (float) damage;
					}
				});
			}

			if (heldItem.getItem() instanceof AbstractGunItem gunItem) {
				// Round to nearest 0.1
				gunBaseVelocity = Math.round(gunItem.getBaseFireVelocity() * 10.0f) / 10.0f;
				selectedAmmo = gunItem.findAmmo(heldItem, player).getItem();
				selectedPowder = gunItem.findPowder(heldItem, player).getItem();
			} else {
				gunBaseVelocity = 0;
				selectedAmmo = Items.AIR;
				selectedPowder = Items.AIR;
			}

			GENERAL_DAMAGE_BONUS = 0;
			MELEE_DAMAGE_BONUS = 0;
			PROJECTILE_DAMAGE_BONUS = 0;

			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.STARSTORM_HELMET.get() &&
					player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.STARSTORM_CHESTPLATE.get() &&
					player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.STARSTORM_LEGGINGS.get() &&
					player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.STARSTORM_BOOTS.get()) {

				GENERAL_DAMAGE_BONUS += 0.2d;
			}
			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.MOLTEN_HELMET.get() &&
					player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.MOLTEN_CHESTPLATE.get() &&
					player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.MOLTEN_LEGGINGS.get() &&
					player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.MOLTEN_BOOTS.get()) {

				double bonus = 0;
				if (player.level().dimension() == Level.NETHER) {
					bonus += 0.2d;
				}

				if (player.isInLava()) {
					bonus += 0.1d;
				}

				GENERAL_DAMAGE_BONUS += bonus;
			}

			GENERAL_DAMAGE_BONUS += AccessoryManager.collectEffects(EffectType.GENERAL_DAMAGE, player);
			MELEE_DAMAGE_BONUS += AccessoryManager.collectEffects(EffectType.MELEE_DAMAGE, player);
			PROJECTILE_DAMAGE_BONUS += AccessoryManager.collectEffects(EffectType.PROJECTILE_DAMAGE, player);

			ARMOR_VALUE = player.getArmorValue();
			ARMOR_TOUGHNESS_VALUE = player.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
			GENERAL_DAMAGE_RESISTANCE = AccessoryManager.collectEffects(EffectType.DAMAGE_RESISTANCE, player);
			KNOCKBACK_RESISTANCE = player.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);

			if (player.hasEffect(EffectRegistry.CELESTIAL_PROTECTION_EFFECT.get())) {
				GENERAL_DAMAGE_RESISTANCE += 0.05d;

				if (!player.getPersistentData().isEmpty()) {
					CELESTIAL_PROTECTION_NO_DAMAGE_CHANCE = player.getPersistentData().getFloat("celestialProtectionChanceForNoDamage");
				}
			}
		}
	}

	public record DebugDataPacketHandler(UUID playerUUID, float lastDamageDealt, float lastDamageTaken,
	                                     int ticksSinceRest) {

		public static void encode(DebugDataPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeUUID(msg.playerUUID()).writeFloat(msg.lastDamageDealt).writeFloat(msg.lastDamageTaken).writeInt(msg.ticksSinceRest);
		}

		public static DebugDataPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new DebugDataPacketHandler(packetBuffer.readUUID(), packetBuffer.readFloat(), packetBuffer.readFloat(), packetBuffer.readInt());
		}

		public static void handle(DebugDataPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> runOnClient(msg)));
			context.setPacketHandled(true);
		}

		private static void runOnClient(DebugDataPacketHandler msg) {
			SyncHandler.debugDataHandler(msg.lastDamageDealt, msg.lastDamageTaken, msg.ticksSinceRest, msg.playerUUID);
		}
	}
}