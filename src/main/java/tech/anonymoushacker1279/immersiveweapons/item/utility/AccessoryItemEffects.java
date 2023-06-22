package tech.anonymoushacker1279.immersiveweapons.item.utility;

import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectMapBuilder;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;

import java.util.Map;

public class AccessoryItemEffects {

	public static final Map<EffectType, Double> SATCHEL = new EffectMapBuilder()
			.addEffect(EffectType.FIREARM_AMMO_CONSERVATION_CHANCE, 0.1d)
			.build();
	public static final Map<EffectType, Double> POWDER_HORN = new EffectMapBuilder()
			.addEffect(EffectType.FIREARM_RELOAD_SPEED, 0.15d)
			.build();
	public static final Map<EffectType, Double> BERSERKERS_AMULET = new EffectMapBuilder()
			.addEffect(EffectType.MELEE_DAMAGE, 0.2d)
			.addEffect(EffectType.PROJECTILE_DAMAGE, 0.1d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, -0.2d)
			.build();
	public static final Map<EffectType, Double> HANS_BLESSING = new EffectMapBuilder()
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.15d)
			.build();
	public static final Map<EffectType, Double> CELESTIAL_SPIRIT = new EffectMapBuilder()
			.addEffect(EffectType.OTHER, 0.0d)
			.build();
	public static final Map<EffectType, Double> BLADEMASTER_EMBLEM = new EffectMapBuilder()
			.addEffect(EffectType.MELEE_DAMAGE, 0.1d)
			.addEffect(EffectType.MELEE_BLEED_CHANCE, 0.3d)
			.build();
	public static final Map<EffectType, Double> DEADEYE_PENDANT = new EffectMapBuilder()
			.addEffect(EffectType.OTHER, 0.0d)
			.build();
	public static final Map<EffectType, Double> BLOATED_HEART = new EffectMapBuilder()
			.addEffect(EffectType.OTHER, 0.0d)
			.build();
	public static final Map<EffectType, Double> NETHERITE_SHIELD = new EffectMapBuilder()
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.03d)
			.build();
	public static final Map<EffectType, Double> MELEE_MASTERS_MOLTEN_GLOVE = new EffectMapBuilder()
			.addEffect(EffectType.MELEE_DAMAGE, 0.1d)
			.addEffect(EffectType.MELEE_KNOCKBACK, 0.75d)
			.build();
	public static final Map<EffectType, Double> COPPER_RING = new EffectMapBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.01d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.01d)
			.build();
	public static final Map<EffectType, Double> IRON_RING = new EffectMapBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.02d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.02d)
			.build();
	public static final Map<EffectType, Double> COBALT_RING = new EffectMapBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.025d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.025d)
			.build();
	public static final Map<EffectType, Double> GOLDEN_RING = new EffectMapBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.03d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.03d)
			.build();
	public static final Map<EffectType, Double> EMERALD_RING = new EffectMapBuilder()
			.addEffect(EffectType.OTHER, 0.0d)
			.build();
	public static final Map<EffectType, Double> AMETHYST_RING = new EffectMapBuilder()
			.addEffect(EffectType.OTHER, 0.0d)
			.build();
	public static final Map<EffectType, Double> DIAMOND_RING = new EffectMapBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.04d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.04d)
			.build();
	public static final Map<EffectType, Double> NETHERITE_RING = new EffectMapBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.05d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.05d)
			.build();
	public static final Map<EffectType, Double> MEDAL_OF_ADEQUACY = new EffectMapBuilder()
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.01d)
			.build();
}