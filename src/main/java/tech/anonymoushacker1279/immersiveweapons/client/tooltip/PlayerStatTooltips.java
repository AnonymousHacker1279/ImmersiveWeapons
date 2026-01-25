package tech.anonymoushacker1279.immersiveweapons.client.tooltip;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.event.game_effects.AccessoryManager;
import tech.anonymoushacker1279.immersiveweapons.init.AccessoryEffectTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.armor.ArmorUtils;

import java.util.ArrayList;

public class PlayerStatTooltips {

	public static ArrayList<ClientTooltipComponent> getStatTooltips(Player player) {
		ArrayList<ClientTooltipComponent> tooltips = new ArrayList<>(5);
		tooltips.add(createClientTooltip("immersiveweapons.player_stats.title"));
		tooltips.add(createClientTooltip("immersiveweapons.player_stats.health", player.getHealth(), player.getMaxHealth()));

		float toughness = Math.round(player.getAttributeValue(Attributes.ARMOR_TOUGHNESS) * 10f) / 10f;
		if (player.getArmorValue() > 0 || toughness > 0) {
			tooltips.add(createClientTooltip("immersiveweapons.player_stats.armor", player.getArmorValue(), toughness));
		}

		if (ArmorUtils.isWearingStarstormArmor(player)) {
			tooltips.add(createClientTooltip("immersiveweapons.player_stats.armor.additional_resistance", 20));
		} else if (ArmorUtils.isWearingMoltenArmor(player)) {
			int moltenBonus = 0;

			if (player.level().dimension() == Level.NETHER) {
				moltenBonus += 20;
			}
			if (player.isInLava()) {
				moltenBonus += 10;
			}

			tooltips.add(createClientTooltip("immersiveweapons.player_stats.armor.additional_resistance", moltenBonus));
		} else if (ArmorUtils.isWearingVoidArmor(player)) {
			tooltips.add(createClientTooltip("immersiveweapons.player_stats.armor.additional_resistance", 22));
		}

		float knockbackResist = Math.round(player.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) * 100f) / 100f;
		if (knockbackResist != 0) {
			tooltips.add(createClientTooltip("immersiveweapons.player_stats.knockback_resistance", Math.round(knockbackResist * 100)));
		}

		double accessoryGeneralResist = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), player);
		double bleedResist = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.BLEED_RESISTANCE.get(), player);
		double sonicResist = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.SONIC_BOOM_RESISTANCE.get(), player);

		if (accessoryGeneralResist != 0 || bleedResist != 0 || sonicResist != 0) {
			insertSpacer(tooltips);
			tooltips.add(createClientTooltip("immersiveweapons.player_stats.accessory_resistance"));
			if (accessoryGeneralResist != 0) {
				tooltips.add(createClientTooltip("immersiveweapons.player_stats.accessory_resistance.general", Math.round(accessoryGeneralResist * 100)));
			}
			if (bleedResist != 0) {
				tooltips.add(createClientTooltip("immersiveweapons.player_stats.accessory_resistance.bleed", Math.round(bleedResist * 100)));
			}
			if (sonicResist != 0) {
				tooltips.add(createClientTooltip("immersiveweapons.player_stats.accessory_resistance.sonic_boom", Math.round(sonicResist * 100)));
			}
		}

		double generalDamage = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), player);
		double meleeDamage = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.MELEE_DAMAGE.get(), player);
		double projectileDamage = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.PROJECTILE_DAMAGE.get(), player);
		double meleeCritDamageBonus = AccessoryManager.collectEffects(AccessoryEffectTypeRegistry.MELEE_CRIT_DAMAGE_BONUS.get(), player);

		if (generalDamage != 0 || meleeDamage != 0 || projectileDamage != 0 || meleeCritDamageBonus != 0) {
			insertSpacer(tooltips);
			tooltips.add(createClientTooltip("immersiveweapons.player_stats.accessory_damage"));
			if (generalDamage != 0) {
				tooltips.add(createClientTooltip("immersiveweapons.player_stats.accessory_damage.general", Math.round(generalDamage * 100)));
			}
			if (meleeDamage != 0) {
				tooltips.add(createClientTooltip("immersiveweapons.player_stats.accessory_damage.melee", Math.round(meleeDamage * 100)));
			}
			if (projectileDamage != 0) {
				tooltips.add(createClientTooltip("immersiveweapons.player_stats.accessory_damage.projectile", Math.round(projectileDamage * 100)));
			}
			if (meleeCritDamageBonus != 0) {
				tooltips.add(createClientTooltip("immersiveweapons.player_stats.accessory_damage.melee_crit_damage", Math.round((meleeCritDamageBonus + 0.5d) * 100)));
			}
		}

		return tooltips;
	}

	private static ClientTooltipComponent createClientTooltip(String translatable, Object... args) {
		return ClientTooltipComponent.create(Component.translatable(translatable, args).getVisualOrderText());
	}

	private static void insertSpacer(ArrayList<ClientTooltipComponent> tooltips) {
		tooltips.add(ClientTooltipComponent.create(Component.empty().getVisualOrderText()));
	}
}