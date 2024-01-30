package tech.anonymoushacker1279.immersiveweapons.item.utility;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectBuilder;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectBuilder.EffectScalingType;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem.EffectType;

import java.util.UUID;

public class AccessoryItemEffects {

	public static final AttributeModifier BLOATED_HEART_HEALTH_MODIFIER = new AttributeModifier(UUID.fromString("e5485685-cfbe-422b-b7dd-eda29049a508"),
			"Bloated Heart boost", 4.0d, Operation.ADDITION);
	public static final AttributeModifier AGILITY_BRACELET_SPEED_MODIFIER = new AttributeModifier(UUID.fromString("0360d69c-bf02-4dc3-a64f-86d9a9b345cd"),
			"Agility Bracelet boost", 0.05d, Operation.MULTIPLY_BASE);
	public static final AttributeModifier AGILITY_BRACELET_STEP_HEIGHT_MODIFIER = new AttributeModifier(UUID.fromString("57108471-149e-4d4a-829b-e38632429f57"),
			"Agility Bracelet boost", 0.5d, Operation.ADDITION);
	public static final AttributeModifier NETHERITE_SHIELD_KB_MODIFIER = new AttributeModifier(UUID.fromString("18e993c3-cf20-4a18-bdb4-11751c4dfb0f"),
			"Netherite Shield knockback resistance", 0.0d, Operation.ADDITION);
	public static final AttributeModifier GLOVE_OF_RAPID_SWINGING_ATTACK_SPEED_MODIFIER = new AttributeModifier(UUID.fromString("c8ea3a7c-99ab-437b-90c7-3b7ddfb3ffd4"),
			"Glove of Rapid Swinging boost", 0.25d, Operation.ADDITION);
	public static final AttributeModifier SUPER_BLANKET_CAPE_SPEED_MODIFIER = new AttributeModifier(UUID.fromString("e71dd651-f3c8-4354-b5f9-c3d9ed24d8f3"),
			"Super Blanket Cape boost", 0.05d, Operation.MULTIPLY_BASE);

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
	public static final EffectBuilder CELESTIAL_SPIRIT = new EffectBuilder();
	public static final EffectBuilder BLADEMASTER_EMBLEM = new EffectBuilder()
			.addEffect(EffectType.MELEE_DAMAGE, 0.1d)
			.addEffect(EffectType.MELEE_BLEED_CHANCE, 0.3d);
	public static final EffectBuilder DEADEYE_PENDANT = new EffectBuilder();
	public static final EffectBuilder BLOATED_HEART = new EffectBuilder()
			.addAttributeModifier(BLOATED_HEART_HEALTH_MODIFIER, Attributes.MAX_HEALTH);
	public static final EffectBuilder NETHERITE_SHIELD = new EffectBuilder()
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.03d)
			.addDynamicAttributeModifier(NETHERITE_SHIELD_KB_MODIFIER, Attributes.KNOCKBACK_RESISTANCE, 1.0d);
	public static final EffectBuilder MELEE_MASTERS_MOLTEN_GLOVE = new EffectBuilder()
			.addEffect(EffectType.MELEE_DAMAGE, 0.1d)
			.addEffect(EffectType.MELEE_KNOCKBACK, 0.75d);
	public static final EffectBuilder IRON_FIST = new EffectBuilder()
			.addEffect(EffectType.MELEE_CRIT_DAMAGE_BONUS, 0.25d)
			.addEffect(EffectType.MELEE_CRIT_CHANCE, 0.15d);
	public static final EffectBuilder GLOVE_OF_RAPID_SWINGING = new EffectBuilder()
			.addAttributeModifier(GLOVE_OF_RAPID_SWINGING_ATTACK_SPEED_MODIFIER, Attributes.ATTACK_SPEED);
	public static final EffectBuilder HAND_OF_DOOM = new EffectBuilder()
			.addObjectsFromBuilder(MELEE_MASTERS_MOLTEN_GLOVE)
			.addObjectsFromBuilder(IRON_FIST)
			.addObjectsFromBuilder(GLOVE_OF_RAPID_SWINGING);
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
			.addMobEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 60, 0, true, true));
	public static final EffectBuilder AMETHYST_RING = new EffectBuilder()
			.addEffect(EffectType.LOOTING_LEVEL, 2);
	public static final EffectBuilder DIAMOND_RING = new EffectBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.04d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.04d);
	public static final EffectBuilder NETHERITE_RING = new EffectBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.05d)
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.05d);
	public static final EffectBuilder DEATH_GEM_RING = new EffectBuilder()
			.addEffect(EffectType.GENERAL_WITHER_CHANCE, 1.0d);
	public static final EffectBuilder MEDAL_OF_ADEQUACY = new EffectBuilder()
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.01d);
	public static final EffectBuilder DEPTH_CHARM = new EffectBuilder()
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.003d, EffectScalingType.DEPTH_SCALING)
			.addEffect(EffectType.GENERAL_DAMAGE, 0.002d, EffectScalingType.DEPTH_SCALING)
			.addEffect(EffectType.MELEE_KNOCKBACK, 0.001d, EffectScalingType.DEPTH_SCALING);
	public static final EffectBuilder REINFORCED_DEPTH_CHARM = new EffectBuilder()
			.addEffect(EffectType.DAMAGE_RESISTANCE, 0.003d, EffectScalingType.DEPTH_SCALING)
			.addEffect(EffectType.GENERAL_DAMAGE, 0.002d, EffectScalingType.DEPTH_SCALING)
			.addEffect(EffectType.MELEE_KNOCKBACK, 0.001d, EffectScalingType.DEPTH_SCALING)
			.addEffect(EffectType.SONIC_BOOM_RESISTANCE, 0.5d);
	public static final EffectBuilder INSOMNIA_AMULET = new EffectBuilder()
			.addEffect(EffectType.GENERAL_DAMAGE, 0.003d, EffectScalingType.INSOMNIA_SCALING);
	public static final EffectBuilder GOGGLES = new EffectBuilder()
			.addEffect(EffectType.PROJECTILE_DAMAGE, 0.03d);
	public static final EffectBuilder LAVA_GOGGLES = new EffectBuilder();
	public static final EffectBuilder NIGHT_VISION_GOGGLES = new EffectBuilder()
			.addMobEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, true));
	public static final EffectBuilder AGILITY_BRACELET = new EffectBuilder()
			.addAttributeModifier(AGILITY_BRACELET_SPEED_MODIFIER, Attributes.MOVEMENT_SPEED)
			.addAttributeModifier(AGILITY_BRACELET_STEP_HEIGHT_MODIFIER, NeoForgeMod.STEP_HEIGHT.value());
	public static final EffectBuilder BLOODY_CLOTH = new EffectBuilder()
			.addEffect(EffectType.BLEED_CANCEL_CHANCE, 0.15d)
			.addEffect(EffectType.BLEED_RESISTANCE, 0.30d);
	public static final EffectBuilder ANCIENT_SCROLL = new EffectBuilder()
			.addEffect(EffectType.EXPERIENCE_MODIFIER, 0.35d);
	public static final EffectBuilder HOLY_MANTLE = new EffectBuilder();
	public static final EffectBuilder VENSTRAL_JAR = new EffectBuilder();
	public static final EffectBuilder SUPER_BLANKET_CAPE = new EffectBuilder()
			.addAttributeModifier(SUPER_BLANKET_CAPE_SPEED_MODIFIER, Attributes.MOVEMENT_SPEED)
			.addMobEffect(new MobEffectInstance(MobEffects.JUMP, 60, 0, true, true))
			.addMobEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 0, true, true));
}