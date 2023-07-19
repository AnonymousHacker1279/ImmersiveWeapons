package tech.anonymoushacker1279.immersiveweapons.item.utility;

import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectBuilder;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectBuilder.EffectScalingType;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;

public class AccessoryItemEffects {

	public static final EffectBuilder SATCHEL = new EffectBuilder()
			.addEffect(EffectType.FIREARM_AMMO_CONSERVATION_CHANCE, 0.1d);
	public static final EffectBuilder POWDER_HORN = new EffectBuilder()
			.addEffect(EffectType.FIREARM_RELOAD_SPEED, 0.15d);
	public static final EffectBuilder BERSERKERS_AMULET = new EffectBuilder()
			.addEffect(EffectType.MELEE_DAMAGE, 0.2d)
			.addEffect(EffectType.PROJECTILE_DAMAGE, 0.1d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, -0.2d);
	public static final EffectBuilder HANS_BLESSING = new EffectBuilder()
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.15d);
	public static final EffectBuilder CELESTIAL_SPIRIT = new EffectBuilder()
			.addEffect(EffectType.OTHER, 0.0d);
	public static final EffectBuilder BLADEMASTER_EMBLEM = new EffectBuilder()
			.addEffect(EffectType.MELEE_DAMAGE, 0.1d)
			.addEffect(EffectType.MELEE_BLEED_CHANCE, 0.3d);
	public static final EffectBuilder DEADEYE_PENDANT = new EffectBuilder()
			.addEffect(EffectType.OTHER, 0.0d);
	public static final EffectBuilder BLOATED_HEART = new EffectBuilder()
			.addEffect(EffectType.OTHER, 0.0d);
	public static final EffectBuilder NETHERITE_SHIELD = new EffectBuilder()
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.03d);
	public static final EffectBuilder MELEE_MASTERS_MOLTEN_GLOVE = new EffectBuilder()
			.addEffect(EffectType.MELEE_DAMAGE, 0.1d)
			.addEffect(EffectType.MELEE_KNOCKBACK, 0.75d);
	public static final EffectBuilder COPPER_RING = new EffectBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.01d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.01d);
	public static final EffectBuilder IRON_RING = new EffectBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.02d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.02d);
	public static final EffectBuilder COBALT_RING = new EffectBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.025d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.025d);
	public static final EffectBuilder GOLDEN_RING = new EffectBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.03d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.03d);
	public static final EffectBuilder EMERALD_RING = new EffectBuilder()
			.addEffect(EffectType.OTHER, 0.0d);
	public static final EffectBuilder AMETHYST_RING = new EffectBuilder()
			.addEffect(EffectType.OTHER, 0.0d);
	public static final EffectBuilder DIAMOND_RING = new EffectBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.04d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.04d);
	public static final EffectBuilder NETHERITE_RING = new EffectBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.05d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.05d);
	public static final EffectBuilder MEDAL_OF_ADEQUACY = new EffectBuilder()
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.01d);
	public static final EffectBuilder DEPTH_CHARM = new EffectBuilder()
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.003d, EffectScalingType.DEPTH_SCALING)
			.addEffect(EffectType.GENERAL_DAMAGE, 0.002d, EffectScalingType.DEPTH_SCALING)
			.addEffect(EffectType.MELEE_KNOCKBACK, 0.001d, EffectScalingType.DEPTH_SCALING);
	public static final EffectBuilder INSOMNIA_AMULET = new EffectBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.003d, EffectScalingType.INSOMNIA_SCALING);
	public static final EffectBuilder GOGGLES = new EffectBuilder()
			.addEffect(EffectType.PROJECTILE_DAMAGE, 0.03d);
	public static final EffectBuilder LAVA_GOGGLES = new EffectBuilder()
			.addEffect(EffectType.OTHER, 0.0d);
	public static final EffectBuilder NIGHT_VISION_GOGGLES = new EffectBuilder()
			.addEffect(EffectType.OTHER, 0.0d);
	public static final EffectBuilder AGILITY_BRACELET = new EffectBuilder()
			.addEffect(EffectType.OTHER, 0.0d);
}