package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.util.markers.LanguageEntryOverride;

public class PotionRegistry {
	// Potion Register
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, ImmersiveWeapons.MOD_ID);

	// Potions
	@LanguageEntryOverride("Celestial Brew")
	public static final DeferredHolder<Potion, Potion> CELESTIAL_BREW_POTION = POTIONS.register("celestial_brew", () -> new Potion("celestial_brew", new MobEffectInstance(EffectRegistry.CELESTIAL_PROTECTION_EFFECT, 3600)));
	@LanguageEntryOverride("Celestial Brew")
	public static final DeferredHolder<Potion, Potion> LONG_CELESTIAL_BREW_POTION = POTIONS.register("long_celestial_brew", () -> new Potion("celestial_brew", new MobEffectInstance(EffectRegistry.CELESTIAL_PROTECTION_EFFECT, 7200)));
	public static final DeferredHolder<Potion, Potion> DEATH_POTION = POTIONS.register("death", () -> new Potion("death", new MobEffectInstance(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT, 1800)));
	public static final DeferredHolder<Potion, Potion> STRONG_DEATH_POTION = POTIONS.register("strong_death", () -> new Potion("death", new MobEffectInstance(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT, 1200, 1)));
	public static final DeferredHolder<Potion, Potion> LONG_DEATH_POTION = POTIONS.register("long_death", () -> new Potion("death", new MobEffectInstance(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT, 2400)));
	public static final DeferredHolder<Potion, Potion> SUPER_HEALING_POTION = POTIONS.register("super_healing", () -> new Potion("healing", new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1, 2)));
	public static final DeferredHolder<Potion, Potion> ULTRA_HEALING_POTION = POTIONS.register("ultra_healing", () -> new Potion("healing", new MobEffectInstance(MobEffects.INSTANT_HEALTH, 1, 3)));
	public static final DeferredHolder<Potion, Potion> SUPER_REGENERATION_POTION = POTIONS.register("super_regeneration", () -> new Potion("regeneration", new MobEffectInstance(MobEffects.REGENERATION, 300, 2)));
	public static final DeferredHolder<Potion, Potion> ULTRA_REGENERATION_POTION = POTIONS.register("ultra_regeneration", () -> new Potion("regeneration", new MobEffectInstance(MobEffects.REGENERATION, 200, 3)));
	public static final DeferredHolder<Potion, Potion> BROKEN_ARMOR_POTION = POTIONS.register("broken_armor", () -> new Potion("broken_armor", new MobEffectInstance(EffectRegistry.BROKEN_ARMOR_EFFECT, 1800)));
	public static final DeferredHolder<Potion, Potion> STRONG_BROKEN_ARMOR_POTION = POTIONS.register("strong_broken_armor", () -> new Potion("broken_armor", new MobEffectInstance(EffectRegistry.BROKEN_ARMOR_EFFECT, 1200, 1)));
	public static final DeferredHolder<Potion, Potion> LONG_BROKEN_ARMOR_POTION = POTIONS.register("long_broken_armor", () -> new Potion("broken_armor", new MobEffectInstance(EffectRegistry.BROKEN_ARMOR_EFFECT, 2400)));
}