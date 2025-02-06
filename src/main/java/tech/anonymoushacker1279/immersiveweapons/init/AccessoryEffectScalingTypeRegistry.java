package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.item.accessory.scaling.AccessoryEffectScalingType;

public class AccessoryEffectScalingTypeRegistry {

	public static final ResourceKey<Registry<AccessoryEffectScalingType>> ACCESSORY_EFFECT_SCALING_TYPE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "accessory_effect_scaling_type"));
	public static final Registry<AccessoryEffectScalingType> ACCESSORY_EFFECT_SCALING_TYPE_REGISTRY = new RegistryBuilder<>(ACCESSORY_EFFECT_SCALING_TYPE_KEY)
			.sync(true)
			.create();

	public static final DeferredRegister<AccessoryEffectScalingType> ACCESSORY_EFFECT_SCALING_TYPES = DeferredRegister.create(ACCESSORY_EFFECT_SCALING_TYPE_REGISTRY, ImmersiveWeapons.MOD_ID);

	/**
	 * The default scaling type, which has no effects on the final value.
	 */
	public static final DeferredHolder<AccessoryEffectScalingType, AccessoryEffectScalingType> NONE = ACCESSORY_EFFECT_SCALING_TYPES.register("none", AccessoryEffectScalingType::new);
	/**
	 * Scales the effect value based on the player's depth, beginning at y{@literal <}64.
	 */
	public static final DeferredHolder<AccessoryEffectScalingType, AccessoryEffectScalingType> DEPTH = ACCESSORY_EFFECT_SCALING_TYPES.register("depth", AccessoryEffectScalingType::new);
	/**
	 * Scales the effect value based on the player's insomnia value, beginning after 24000 ticks.
	 */
	public static final DeferredHolder<AccessoryEffectScalingType, AccessoryEffectScalingType> INSOMNIA = ACCESSORY_EFFECT_SCALING_TYPES.register("insomnia", AccessoryEffectScalingType::new);
}