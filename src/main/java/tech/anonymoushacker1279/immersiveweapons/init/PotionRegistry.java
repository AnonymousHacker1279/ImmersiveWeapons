package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.potion.CustomPotion;

@SuppressWarnings({"unused"})
public class PotionRegistry {
	// Potion Register
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, ImmersiveWeapons.MOD_ID);

	// Potions
	public static final RegistryObject<Potion> CELESTIAL_BREW_POTION = POTIONS.register("celestial_brew", () -> new CustomPotion(new MobEffectInstance(EffectRegistry.CELESTIAL_PROTECTION_EFFECT.get(), 3600)));
	public static final RegistryObject<Potion> LONG_CELESTIAL_BREW_POTION = POTIONS.register("long_celestial_brew", () -> new CustomPotion(new MobEffectInstance(EffectRegistry.CELESTIAL_PROTECTION_EFFECT.get(), 7200)));
	public static final RegistryObject<Potion> DEATH_POTION = POTIONS.register("death", () -> new CustomPotion(new MobEffectInstance(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT.get(), 1800)));
	public static final RegistryObject<Potion> STRONG_DEATH_POTION = POTIONS.register("strong_death", () -> new CustomPotion(new MobEffectInstance(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT.get(), 1200, 1)));
	public static final RegistryObject<Potion> LONG_DEATH_POTION = POTIONS.register("long_death", () -> new CustomPotion(new MobEffectInstance(EffectRegistry.DAMAGE_VULNERABILITY_EFFECT.get(), 2400)));
}