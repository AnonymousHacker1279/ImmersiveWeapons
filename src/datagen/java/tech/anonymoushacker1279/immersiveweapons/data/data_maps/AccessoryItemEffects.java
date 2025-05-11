package tech.anonymoushacker1279.immersiveweapons.data.data_maps;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.init.AccessoryEffectScalingTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.AccessoryEffectTypeRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryEffectBuilder;

public class AccessoryItemEffects {

	public static final AttributeModifier BLOATED_HEART_HEALTH_MODIFIER = new AttributeModifier(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bloated_heart_health_modifier"),
			4.0d, Operation.ADD_VALUE);
	public static final AttributeModifier AGILITY_BRACELET_SPEED_MODIFIER = new AttributeModifier(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "agility_bracelet_speed_modifier"),
			0.05d, Operation.ADD_MULTIPLIED_BASE);
	public static final AttributeModifier AGILITY_BRACELET_STEP_HEIGHT_MODIFIER = new AttributeModifier(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "agility_bracelet_step_height_modifier"),
			0.5d, Operation.ADD_VALUE);
	public static final AttributeModifier NETHERITE_SHIELD_KB_MODIFIER = new AttributeModifier(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "netherite_shield_knockback_resistance"),
			0.0d, Operation.ADD_VALUE);
	public static final AttributeModifier GLOVE_OF_RAPID_SWINGING_ATTACK_SPEED_MODIFIER = new AttributeModifier(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "glove_of_rapid_swinging_attack_speed_modifier"),
			0.25d, Operation.ADD_VALUE);
	public static final AttributeModifier SUPER_BLANKET_CAPE_SPEED_MODIFIER = new AttributeModifier(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "super_blanket_cape_speed_modifier"),
			0.05d, Operation.ADD_MULTIPLIED_BASE);

	public static final AccessoryEffectBuilder SATCHEL = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.FIREARM_AMMO_CONSERVATION_CHANCE.get(), 0.1d);
	public static final AccessoryEffectBuilder POWDER_HORN = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.FIREARM_RELOAD_SPEED.get(), 0.15d);
	public static final AccessoryEffectBuilder BERSERKERS_AMULET = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.MELEE_DAMAGE.get(), 0.2d)
			.addEffect(AccessoryEffectTypeRegistry.PROJECTILE_DAMAGE.get(), 0.1d)
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), -0.2d);
	public static final AccessoryEffectBuilder HANS_BLESSING = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.15d);
	public static final AccessoryEffectBuilder CELESTIAL_SPIRIT = new AccessoryEffectBuilder();
	public static final AccessoryEffectBuilder VOID_BLESSING = new AccessoryEffectBuilder()
			.addObjectsFromBuilder(HANS_BLESSING);
	public static final AccessoryEffectBuilder BLADEMASTER_EMBLEM = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.MELEE_DAMAGE.get(), 0.1d)
			.addEffect(AccessoryEffectTypeRegistry.MELEE_BLEED_CHANCE.get(), 0.3d);
	public static final AccessoryEffectBuilder DEADEYE_PENDANT = new AccessoryEffectBuilder();
	public static final AccessoryEffectBuilder BLOATED_HEART = new AccessoryEffectBuilder()
			.addAttributeModifier(BLOATED_HEART_HEALTH_MODIFIER, Attributes.MAX_HEALTH);
	public static final AccessoryEffectBuilder NETHERITE_SHIELD = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.03d)
			.addDynamicAttributeModifier(NETHERITE_SHIELD_KB_MODIFIER, Attributes.KNOCKBACK_RESISTANCE, 1.0d);
	public static final AccessoryEffectBuilder MELEE_MASTERS_MOLTEN_GLOVE = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.MELEE_DAMAGE.get(), 0.1d)
			.addEffect(AccessoryEffectTypeRegistry.MELEE_KNOCKBACK.get(), 0.75d);
	public static final AccessoryEffectBuilder IRON_FIST = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.MELEE_CRIT_DAMAGE_BONUS.get(), 0.25d)
			.addEffect(AccessoryEffectTypeRegistry.MELEE_CRIT_CHANCE.get(), 0.15d);
	public static final AccessoryEffectBuilder GLOVE_OF_RAPID_SWINGING = new AccessoryEffectBuilder()
			.addAttributeModifier(GLOVE_OF_RAPID_SWINGING_ATTACK_SPEED_MODIFIER, Attributes.ATTACK_SPEED);
	public static final AccessoryEffectBuilder HAND_OF_DOOM = new AccessoryEffectBuilder()
			.addObjectsFromBuilder(MELEE_MASTERS_MOLTEN_GLOVE)
			.addObjectsFromBuilder(IRON_FIST)
			.addObjectsFromBuilder(GLOVE_OF_RAPID_SWINGING);
	public static final AccessoryEffectBuilder COPPER_RING = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), 0.01d)
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.01d);
	public static final AccessoryEffectBuilder IRON_RING = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), 0.02d)
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.02d);
	public static final AccessoryEffectBuilder COBALT_RING = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), 0.025d)
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.025d);
	public static final AccessoryEffectBuilder GOLDEN_RING = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), 0.03d)
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.03d);
	public static final AccessoryEffectBuilder EMERALD_RING = new AccessoryEffectBuilder()
			.addMobEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 60, 0, true, true));
	public static final AccessoryEffectBuilder AMETHYST_RING = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.LOOTING_LEVEL.get(), 2);
	public static final AccessoryEffectBuilder DIAMOND_RING = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), 0.04d)
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.04d);
	public static final AccessoryEffectBuilder NETHERITE_RING = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), 0.05d)
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.05d);
	public static final AccessoryEffectBuilder DEATH_GEM_RING = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.GENERAL_WITHER_CHANCE.get(), 1.0d);
	public static final AccessoryEffectBuilder MEDAL_OF_ADEQUACY = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.01d);
	public static final AccessoryEffectBuilder DEPTH_CHARM = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.003d, AccessoryEffectScalingTypeRegistry.DEPTH.get())
			.addEffect(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), 0.002d, AccessoryEffectScalingTypeRegistry.DEPTH.get())
			.addEffect(AccessoryEffectTypeRegistry.MELEE_KNOCKBACK.get(), 0.001d, AccessoryEffectScalingTypeRegistry.DEPTH.get());
	public static final AccessoryEffectBuilder REINFORCED_DEPTH_CHARM = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.003d, AccessoryEffectScalingTypeRegistry.DEPTH.get())
			.addEffect(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), 0.002d, AccessoryEffectScalingTypeRegistry.DEPTH.get())
			.addEffect(AccessoryEffectTypeRegistry.MELEE_KNOCKBACK.get(), 0.001d, AccessoryEffectScalingTypeRegistry.DEPTH.get())
			.addEffect(AccessoryEffectTypeRegistry.SONIC_BOOM_RESISTANCE.get(), 0.5d);
	public static final AccessoryEffectBuilder INSOMNIA_AMULET = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), 0.003d, AccessoryEffectScalingTypeRegistry.INSOMNIA.get());
	public static final AccessoryEffectBuilder GOGGLES = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.PROJECTILE_DAMAGE.get(), 0.03d);
	public static final AccessoryEffectBuilder LAVA_GOGGLES = new AccessoryEffectBuilder();
	public static final AccessoryEffectBuilder NIGHT_VISION_GOGGLES = new AccessoryEffectBuilder()
			.addMobEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 220, 0, true, true));
	public static final AccessoryEffectBuilder AGILITY_BRACELET = new AccessoryEffectBuilder()
			.addAttributeModifier(AGILITY_BRACELET_SPEED_MODIFIER, Attributes.MOVEMENT_SPEED)
			.addAttributeModifier(AGILITY_BRACELET_STEP_HEIGHT_MODIFIER, Attributes.STEP_HEIGHT);
	public static final AccessoryEffectBuilder BLOODY_CLOTH = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.BLEED_CANCEL_CHANCE.get(), 0.15d)
			.addEffect(AccessoryEffectTypeRegistry.BLEED_RESISTANCE.get(), 0.30d);
	public static final AccessoryEffectBuilder ANCIENT_SCROLL = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.EXPERIENCE_MODIFIER.get(), 0.35d);
	public static final AccessoryEffectBuilder HOLY_MANTLE = new AccessoryEffectBuilder();
	public static final AccessoryEffectBuilder VENSTRAL_JAR = new AccessoryEffectBuilder();
	public static final AccessoryEffectBuilder SUPER_BLANKET_CAPE = new AccessoryEffectBuilder()
			.addAttributeModifier(SUPER_BLANKET_CAPE_SPEED_MODIFIER, Attributes.MOVEMENT_SPEED)
			.addMobEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 60, 0, true, true))
			.addMobEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 0, true, true));
	public static final AccessoryEffectBuilder MEDAL_OF_HONOR = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.DAMAGE_RESISTANCE.get(), 0.05d);
	public static final AccessoryEffectBuilder MEDAL_OF_DISHONOR = new AccessoryEffectBuilder()
			.addEffect(AccessoryEffectTypeRegistry.GENERAL_DAMAGE.get(), 0.05d);
}