package tech.anonymoushacker1279.immersiveweapons.data.damage_types;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class DamageTypesGenerator {

	public static void bootstrap(BootstapContext<DamageType> context) {
		damageType(context, IWDamageTypes.BARBED_WIRE_KEY, IWDamageTypes.BARBED_WIRE);
		damageType(context, IWDamageTypes.BEAR_TRAP_KEY, IWDamageTypes.BEAR_TRAP);
		damageType(context, IWDamageTypes.BLEEDING_KEY, IWDamageTypes.BLEEDING);
		damageType(context, IWDamageTypes.EXPLOSIVE_CHOCOLATE_BAR_KEY, IWDamageTypes.EXPLOSIVE_CHOCOLATE_BAR);
		damageType(context, IWDamageTypes.DEATHWEED_KEY, IWDamageTypes.DEATHWEED);
		damageType(context, IWDamageTypes.WOODEN_SPIKES_KEY, IWDamageTypes.WOODEN_SPIKES);
		damageType(context, IWDamageTypes.SPIKE_TRAP_KEY, IWDamageTypes.SPIKE_TRAP);
		damageType(context, IWDamageTypes.USED_SYRINGE_KEY, IWDamageTypes.USED_SYRINGE);
		damageType(context, IWDamageTypes.MORTAR_KEY, IWDamageTypes.MORTAR);
		damageType(context, IWDamageTypes.LANDMINE_KEY, IWDamageTypes.LANDMINE);
		damageType(context, IWDamageTypes.DEADMANS_DESERT_ATMOSPHERE_KEY, IWDamageTypes.DEADMANS_DESERT_ATMOSPHERE);
		damageType(context, IWDamageTypes.METEOR_KEY, IWDamageTypes.METEOR);
		damageType(context, IWDamageTypes.PUNJI_STICKS_KEY, IWDamageTypes.PUNJI_STICKS);
		damageType(context, IWDamageTypes.PUNJI_STICKS_FALL_KEY, IWDamageTypes.PUNJI_STICKS);
		damageType(context, IWDamageTypes.BULLET_KEY, IWDamageTypes.BULLET);
		damageType(context, IWDamageTypes.CANNONBALL_KEY, IWDamageTypes.CANNONBALL);
		damageType(context, IWDamageTypes.EXPLOSIVE_CANNONBALL_KEY, IWDamageTypes.EXPLOSIVE_CANNONBALL);
		damageType(context, IWDamageTypes.EXPLOSIVE_ARROW_KEY, IWDamageTypes.EXPLOSIVE_ARROW);
		damageType(context, IWDamageTypes.HELLFIRE_KEY, IWDamageTypes.HELLFIRE);
	}

	protected static void damageType(BootstapContext<DamageType> context, ResourceKey<DamageType> key, DamageType damageType) {
		context.register(key, damageType);
	}
}