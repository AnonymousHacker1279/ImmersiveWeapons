package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.potion.*;

import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class EffectRegistry {

	// Effect Register
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, ImmersiveWeapons.MOD_ID);

	// Effects
	public static final Supplier<MorphineEffect> MORPHINE_EFFECT = EFFECTS.register("morphine", () -> new MorphineEffect(MobEffectCategory.NEUTRAL, 3484189));
	public static final Supplier<BleedingEffect> BLEEDING_EFFECT = EFFECTS.register("bleeding", () -> new BleedingEffect(MobEffectCategory.HARMFUL, 8392463));
	public static final Supplier<AlcoholEffect> ALCOHOL_EFFECT = EFFECTS.register("alcohol", () -> new AlcoholEffect(MobEffectCategory.NEUTRAL, 14465637));
	public static final Supplier<BasicPotionEffect> CELESTIAL_PROTECTION_EFFECT = EFFECTS.register("celestial_protection", () -> new BasicPotionEffect(MobEffectCategory.BENEFICIAL, 10011890));
	public static final Supplier<BasicPotionEffect> DAMAGE_VULNERABILITY_EFFECT = EFFECTS.register("damage_vulnerability", () -> new BasicPotionEffect(MobEffectCategory.HARMFUL, 9056876));
	public static final Supplier<BasicPotionEffect> FLASHBANG_EFFECT = EFFECTS.register("flashbang", () -> new BasicPotionEffect(MobEffectCategory.HARMFUL, 16777215));
}