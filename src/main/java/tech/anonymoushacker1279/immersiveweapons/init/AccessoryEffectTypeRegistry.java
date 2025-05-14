package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.AccessoryEffectType;
import tech.anonymoushacker1279.immersiveweapons.potion.BleedingEffect;

import java.util.function.Supplier;

public class AccessoryEffectTypeRegistry {

	public static final ResourceKey<Registry<AccessoryEffectType>> ACCESSORY_EFFECT_TYPE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "accessory_effect_type"));
	public static final Registry<AccessoryEffectType> ACCESSORY_EFFECT_TYPE_REGISTRY = new RegistryBuilder<>(ACCESSORY_EFFECT_TYPE_KEY)
			.sync(true)
			.create();

	public static final DeferredRegister<AccessoryEffectType> ACCESSORY_EFFECT_TYPES = DeferredRegister.create(ACCESSORY_EFFECT_TYPE_REGISTRY, ImmersiveWeapons.MOD_ID);

	/**
	 * Chance for firearms to not consume ammo.
	 */
	public static final Supplier<AccessoryEffectType> FIREARM_AMMO_CONSERVATION_CHANCE = ACCESSORY_EFFECT_TYPES.register("firearm_ammo_conservation_chance", (name) -> new AccessoryEffectType(name, true));
	/**
	 * Modifier for reload time for firearms.
	 */
	public static final Supplier<AccessoryEffectType> FIREARM_RELOAD_SPEED = ACCESSORY_EFFECT_TYPES.register("firearm_reload_speed", AccessoryEffectType::new);
	/**
	 * Modifier for melee damage.
	 */
	public static final Supplier<AccessoryEffectType> MELEE_DAMAGE = ACCESSORY_EFFECT_TYPES.register("melee_damage", AccessoryEffectType::new);
	/**
	 * Modifier for projectile damage.
	 */
	public static final Supplier<AccessoryEffectType> PROJECTILE_DAMAGE = ACCESSORY_EFFECT_TYPES.register("projectile_damage", AccessoryEffectType::new);
	/**
	 * Modifier to all outgoing damage sources.
	 */
	public static final Supplier<AccessoryEffectType> GENERAL_DAMAGE = ACCESSORY_EFFECT_TYPES.register("general_damage", AccessoryEffectType::new);
	/**
	 * Modifier to all incoming damage sources.
	 */
	public static final Supplier<AccessoryEffectType> DAMAGE_RESISTANCE = ACCESSORY_EFFECT_TYPES.register("damage_resistance", AccessoryEffectType::new);
	/**
	 * Modifier to melee knockback.
	 */
	public static final Supplier<AccessoryEffectType> MELEE_KNOCKBACK = ACCESSORY_EFFECT_TYPES.register("melee_knockback", AccessoryEffectType::new);
	/**
	 * Chance for melee attacks to inflict {@link BleedingEffect}.
	 */
	public static final Supplier<AccessoryEffectType> MELEE_BLEED_CHANCE = ACCESSORY_EFFECT_TYPES.register("melee_bleed_chance", (name) -> new AccessoryEffectType(name, true));
	/**
	 * Modifier to melee critical damage. Additive with vanilla critical damage, which is 50% by default. For example, a
	 * value of 0.5d will result in 100% critical damage.
	 */
	public static final Supplier<AccessoryEffectType> MELEE_CRIT_DAMAGE_BONUS = ACCESSORY_EFFECT_TYPES.register("melee_crit_damage_bonus", AccessoryEffectType::new);
	/**
	 * Chance for any melee attack to become critical, regardless of vanilla critical hit conditions.
	 */
	public static final Supplier<AccessoryEffectType> MELEE_CRIT_CHANCE = ACCESSORY_EFFECT_TYPES.register("melee_crit_chance", (name) -> new AccessoryEffectType(name, true));
	/**
	 * Chance for {@link BleedingEffect} to be cancelled each damage tick.
	 */
	public static final Supplier<AccessoryEffectType> BLEED_CANCEL_CHANCE = ACCESSORY_EFFECT_TYPES.register("bleed_cancel_chance", (name) -> new AccessoryEffectType(name, true));
	/**
	 * Modifier to {@link BleedingEffect} damage.
	 */
	public static final Supplier<AccessoryEffectType> BLEED_RESISTANCE = ACCESSORY_EFFECT_TYPES.register("bleed_resistance", (name) -> new AccessoryEffectType(name, true));
	/**
	 * Chance for attacks to inflict {@link MobEffects#WITHER}.
	 */
	public static final Supplier<AccessoryEffectType> GENERAL_WITHER_CHANCE = ACCESSORY_EFFECT_TYPES.register("general_wither_chance", (name) -> new AccessoryEffectType(name, true));
	/**
	 * Modifier for experience drops.
	 */
	public static final Supplier<AccessoryEffectType> EXPERIENCE_MODIFIER = ACCESSORY_EFFECT_TYPES.register("experience_modifier", AccessoryEffectType::new);
	/**
	 * Modifier to {@link DamageTypes#SONIC_BOOM} damage.
	 */
	public static final Supplier<AccessoryEffectType> SONIC_BOOM_RESISTANCE = ACCESSORY_EFFECT_TYPES.register("sonic_boom_resistance", (name) -> new AccessoryEffectType(name, true));
	/**
	 * Modifier to the looting level of the player.
	 */
	public static final Supplier<AccessoryEffectType> LOOTING_LEVEL = ACCESSORY_EFFECT_TYPES.register("looting_level", AccessoryEffectType::new);
}