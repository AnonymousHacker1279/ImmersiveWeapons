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
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.AccessoryEffects;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.gun.AbstractGunItem;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * This class contains data for debug tracing.
 */
public class DebugTracingData {

	public static boolean isDebugTracingEnabled = false;

	public static float meleeItemDamage = 0;

	public static float lastDamageDealt = 0;

	public static float gunBaseVelocity = 0;
	public static Item selectedAmmo = Items.AIR;
	public static double liveBulletDamage = 0;
	public static boolean isBulletCritical = false;

	public static double GENERAL_DAMAGE_BONUS = 0;
	public static double MELEE_DAMAGE_BONUS = 0;
	public static double PROJECTILE_DAMAGE_BONUS = 0;

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
			} else {
				gunBaseVelocity = 0;
				selectedAmmo = Items.AIR;
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

			GENERAL_DAMAGE_BONUS += AccessoryEffects.collectEffects(EffectType.GENERAL_DAMAGE, player);
			MELEE_DAMAGE_BONUS += AccessoryEffects.collectEffects(EffectType.MELEE_DAMAGE, player);
			PROJECTILE_DAMAGE_BONUS += AccessoryEffects.collectEffects(EffectType.PROJECTILE_DAMAGE, player);
		}
	}

	public record LastDamageDealtPacketHandler(UUID playerUUID, float lastDamageDealt) {

		public static void encode(LastDamageDealtPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeUUID(msg.playerUUID()).writeFloat(msg.lastDamageDealt);
		}

		public static LastDamageDealtPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new LastDamageDealtPacketHandler(packetBuffer.readUUID(), packetBuffer.readFloat());
		}

		public static void handle(LastDamageDealtPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> runOnClient(msg)));
			context.setPacketHandled(true);
		}

		private static void runOnClient(LastDamageDealtPacketHandler msg) {
			SyncHandler.lastDamageDealtDamageOverlay(msg.lastDamageDealt, msg.playerUUID);
		}
	}
}