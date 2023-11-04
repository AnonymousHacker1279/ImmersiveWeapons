package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.potion.*;

@SuppressWarnings({"unused"})
public class EffectRegistry {

	// Effect Register
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ImmersiveWeapons.MOD_ID);

	// Effects
	public static final RegistryObject<MorphineEffect> MORPHINE_EFFECT = EFFECTS.register("morphine", () -> new MorphineEffect(MobEffectCategory.NEUTRAL, 3484189));
	public static final RegistryObject<BleedingEffect> BLEEDING_EFFECT = EFFECTS.register("bleeding", () -> new BleedingEffect(MobEffectCategory.HARMFUL, 8392463));
	public static final RegistryObject<AlcoholEffect> ALCOHOL_EFFECT = EFFECTS.register("alcohol", () -> new AlcoholEffect(MobEffectCategory.NEUTRAL, 14465637));
	public static final RegistryObject<BasicPotionEffect> CELESTIAL_PROTECTION_EFFECT = EFFECTS.register("celestial_protection", () -> new BasicPotionEffect(MobEffectCategory.BENEFICIAL, 10011890));
	public static final RegistryObject<BasicPotionEffect> DAMAGE_VULNERABILITY_EFFECT = EFFECTS.register("damage_vulnerability", () -> new BasicPotionEffect(MobEffectCategory.HARMFUL, 9056876));
}