package tech.anonymoushacker1279.immersiveweapons.data.damage_types;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class DamageTypesGenerator {

	public static void bootstrap(BootstapContext<DamageType> context) {
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.BARBED_WIRE_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.BARBED_WIRE);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.BEAR_TRAP_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.BEAR_TRAP);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.BLEEDING_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.BLEEDING);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.EXPLOSIVE_CHOCOLATE_BAR_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.EXPLOSIVE_CHOCOLATE_BAR);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.DEATHWEED_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.DEATHWEED);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.WOODEN_SPIKES_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.WOODEN_SPIKES);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.SPIKE_TRAP_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.SPIKE_TRAP);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.USED_SYRINGE_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.USED_SYRINGE);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.MORTAR_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.MORTAR);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.LANDMINE_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.LANDMINE);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.DEADMANS_DESERT_ATMOSPHERE_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.DEADMANS_DESERT_ATMOSPHERE);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.METEOR_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.METEOR);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.PUNJI_STICKS_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.PUNJI_STICKS);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.PUNJI_STICKS_FALL_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.PUNJI_STICKS);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.BULLET_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.BULLET);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.CANNONBALL_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.CANNONBALL);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.EXPLOSIVE_CANNONBALL_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.EXPLOSIVE_CANNONBALL);
		damageType(context, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.EXPLOSIVE_ARROW_KEY, tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes.EXPLOSIVE_ARROW);
	}

	protected static void damageType(BootstapContext<DamageType> context, ResourceKey<DamageType> key, DamageType damageType) {
		context.register(key, damageType);
	}
}