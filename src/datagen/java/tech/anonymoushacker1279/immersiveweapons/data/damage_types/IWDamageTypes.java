package tech.anonymoushacker1279.immersiveweapons.data.damage_types;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWDamageTypes {

	public static final ResourceKey<DamageType> BARBED_WIRE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "barbed_wire"));
	public static final DamageType BARBED_WIRE = new DamageType(
			"immersiveweapons.barbed_wire",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> BEAR_TRAP_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bear_trap"));
	public static final DamageType BEAR_TRAP = new DamageType(
			"immersiveweapons.bear_trap",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> BLEEDING_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bleeding"));
	public static final DamageType BLEEDING = new DamageType(
			"immersiveweapons.bleeding",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> EXPLOSIVE_CHOCOLATE_BAR_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "explosive_chocolate_bar"));
	public static final DamageType EXPLOSIVE_CHOCOLATE_BAR = new DamageType(
			"immersiveweapons.explosive_chocolate_bar",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> DEATHWEED_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "deathweed"));
	public static final DamageType DEATHWEED = new DamageType(
			"immersiveweapons.deathweed",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> WOODEN_SPIKES_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "wooden_spikes"));
	public static final DamageType WOODEN_SPIKES = new DamageType(
			"immersiveweapons.wooden_spikes",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> SPIKE_TRAP_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "spike_trap"));
	public static final DamageType SPIKE_TRAP = new DamageType(
			"immersiveweapons.spike_trap",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> USED_SYRINGE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "used_syringe"));
	public static final DamageType USED_SYRINGE = new DamageType(
			"immersiveweapons.used_syringe",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> MORTAR_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "mortar"));
	public static final DamageType MORTAR = new DamageType(
			"immersiveweapons.mortar",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> LANDMINE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "landmine"));
	public static final DamageType LANDMINE = new DamageType(
			"immersiveweapons.landmine",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> DEADMANS_DESERT_ATMOSPHERE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "deadmans_desert_atmosphere"));
	public static final DamageType DEADMANS_DESERT_ATMOSPHERE = new DamageType(
			"immersiveweapons.deadmans_desert_atmosphere",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> METEOR_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "meteor"));
	public static final DamageType METEOR = new DamageType(
			"immersiveweapons.meteor",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> PUNJI_STICKS_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "punji_sticks"));
	public static final ResourceKey<DamageType> PUNJI_STICKS_FALL_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "punji_sticks_fall"));
	public static final DamageType PUNJI_STICKS = new DamageType(
			"immersiveweapons.punji_sticks",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> BULLET_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bullet"));
	public static final DamageType BULLET = new DamageType(
			"immersiveweapons.bullet",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> CANNONBALL_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "cannonball"));
	public static final DamageType CANNONBALL = new DamageType(
			"immersiveweapons.cannonball",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> EXPLOSIVE_CANNONBALL_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "explosive_cannonball"));
	public static final DamageType EXPLOSIVE_CANNONBALL = new DamageType(
			"immersiveweapons.explosive_cannonball",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> EXPLOSIVE_ARROW_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "explosive_arrow"));
	public static final DamageType EXPLOSIVE_ARROW = new DamageType(
			"immersiveweapons.explosive_arrow",
			DamageScaling.ALWAYS,
			0.1f
	);

	public static final ResourceKey<DamageType> HELLFIRE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "hellfire"));
	public static final DamageType HELLFIRE = new DamageType(
			"immersiveweapons.hellfire",
			DamageScaling.ALWAYS,
			0.1f
	);
}