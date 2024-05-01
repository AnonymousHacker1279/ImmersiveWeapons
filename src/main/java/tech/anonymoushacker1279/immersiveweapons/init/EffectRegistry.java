package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.potion.*;
import tech.anonymoushacker1279.immersiveweapons.util.markers.LanguageEntryOverride;

@SuppressWarnings({"unused"})
public class EffectRegistry {

	// Effect Register
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, ImmersiveWeapons.MOD_ID);

	// Effects
	public static final DeferredHolder<MobEffect, MorphineEffect> MORPHINE_EFFECT = EFFECTS.register("morphine", () -> new MorphineEffect(MobEffectCategory.NEUTRAL, 3484189));
	public static final DeferredHolder<MobEffect, BleedingEffect> BLEEDING_EFFECT = EFFECTS.register("bleeding", () -> new BleedingEffect(MobEffectCategory.HARMFUL, 8392463));
	public static final DeferredHolder<MobEffect, AlcoholEffect> ALCOHOL_EFFECT = EFFECTS.register("alcohol", () -> new AlcoholEffect(MobEffectCategory.NEUTRAL, 14465637));
	public static final DeferredHolder<MobEffect, BasicPotionEffect> CELESTIAL_PROTECTION_EFFECT = EFFECTS.register("celestial_protection", () -> new BasicPotionEffect(MobEffectCategory.BENEFICIAL, 10011890));
	public static final DeferredHolder<MobEffect, BasicPotionEffect> DAMAGE_VULNERABILITY_EFFECT = EFFECTS.register("damage_vulnerability", () -> new BasicPotionEffect(MobEffectCategory.HARMFUL, 9056876));
	@LanguageEntryOverride("Flashbanged")
	public static final DeferredHolder<MobEffect, BasicPotionEffect> FLASHBANG_EFFECT = EFFECTS.register("flashbang", () -> new BasicPotionEffect(MobEffectCategory.HARMFUL, 16777215));
	public static final DeferredHolder<MobEffect, HellfireEffect> HELLFIRE_EFFECT = EFFECTS.register("hellfire", () -> new HellfireEffect(MobEffectCategory.HARMFUL, 16729605));
}