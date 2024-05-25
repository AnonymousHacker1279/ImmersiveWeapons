package tech.anonymoushacker1279.immersiveweapons.data.damage_types;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageType;

public class DamageTypesGenerator {

	public static void bootstrap(BootstrapContext<DamageType> context) {
		context.register(IWDamageTypes.BARBED_WIRE_KEY, IWDamageTypes.BARBED_WIRE);
		context.register(IWDamageTypes.BEAR_TRAP_KEY, IWDamageTypes.BEAR_TRAP);
		context.register(IWDamageTypes.BLEEDING_KEY, IWDamageTypes.BLEEDING);
		context.register(IWDamageTypes.EXPLOSIVE_CHOCOLATE_BAR_KEY, IWDamageTypes.EXPLOSIVE_CHOCOLATE_BAR);
		context.register(IWDamageTypes.DEATHWEED_KEY, IWDamageTypes.DEATHWEED);
		context.register(IWDamageTypes.WOODEN_SPIKES_KEY, IWDamageTypes.WOODEN_SPIKES);
		context.register(IWDamageTypes.SPIKE_TRAP_KEY, IWDamageTypes.SPIKE_TRAP);
		context.register(IWDamageTypes.USED_SYRINGE_KEY, IWDamageTypes.USED_SYRINGE);
		context.register(IWDamageTypes.MORTAR_KEY, IWDamageTypes.MORTAR);
		context.register(IWDamageTypes.LANDMINE_KEY, IWDamageTypes.LANDMINE);
		context.register(IWDamageTypes.DEADMANS_DESERT_ATMOSPHERE_KEY, IWDamageTypes.DEADMANS_DESERT_ATMOSPHERE);
		context.register(IWDamageTypes.METEOR_KEY, IWDamageTypes.METEOR);
		context.register(IWDamageTypes.PUNJI_STICKS_KEY, IWDamageTypes.PUNJI_STICKS);
		context.register(IWDamageTypes.PUNJI_STICKS_FALL_KEY, IWDamageTypes.PUNJI_STICKS);
		context.register(IWDamageTypes.BULLET_KEY, IWDamageTypes.BULLET);
		context.register(IWDamageTypes.CANNONBALL_KEY, IWDamageTypes.CANNONBALL);
		context.register(IWDamageTypes.EXPLOSIVE_CANNONBALL_KEY, IWDamageTypes.EXPLOSIVE_CANNONBALL);
		context.register(IWDamageTypes.EXPLOSIVE_ARROW_KEY, IWDamageTypes.EXPLOSIVE_ARROW);
		context.register(IWDamageTypes.HELLFIRE_KEY, IWDamageTypes.HELLFIRE);
	}
}