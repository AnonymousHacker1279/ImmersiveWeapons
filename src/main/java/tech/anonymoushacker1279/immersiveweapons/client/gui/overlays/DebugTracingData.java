package tech.anonymoushacker1279.immersiveweapons.client.gui.overlays;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.gun.AbstractGunItem;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains data for debug tracing.
 */
public class DebugTracingData {

	public static boolean isDebugTracingEnabled = false;

	public static float meleeItemDamage = 0;

	public static float gunBaseVelocity = 0;
	public static Item selectedAmmo = Items.AIR;
	public static double liveBulletDamage = 0;
	public static boolean isBulletCritical = false;

	public static List<Double> damageBonusList = new ArrayList<>(5);

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
				gunBaseVelocity = Math.round(gunItem.getFireVelocity() * 10.0f) / 10.0f;
				selectedAmmo = gunItem.findAmmo(heldItem, player).getItem();
			} else {
				gunBaseVelocity = 0;
				selectedAmmo = Items.AIR;
			}

			damageBonusList.clear();
			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.STARSTORM_HELMET.get() &&
					player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.STARSTORM_CHESTPLATE.get() &&
					player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.STARSTORM_LEGGINGS.get() &&
					player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.STARSTORM_BOOTS.get()) {

				damageBonusList.add(0.2);
			}
			if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistry.MOLTEN_HELMET.get() &&
					player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistry.MOLTEN_CHESTPLATE.get() &&
					player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistry.MOLTEN_LEGGINGS.get() &&
					player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistry.MOLTEN_BOOTS.get()) {

				double damageBonus = 0;
				if (player.level.dimension() == Level.NETHER) {
					damageBonus += 0.2;
				}

				if (player.isInLava()) {
					damageBonus += 0.1;
				}

				damageBonusList.add(damageBonus);
			}
		}
	}
}