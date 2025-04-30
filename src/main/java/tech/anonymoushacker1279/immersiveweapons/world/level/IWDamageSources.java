package tech.anonymoushacker1279.immersiveweapons.world.level;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWDamageSources {

	public static final ResourceKey<DamageType> BARBED_WIRE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "barbed_wire"));
	public static final ResourceKey<DamageType> BEAR_TRAP_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bear_trap"));
	public static final ResourceKey<DamageType> BLEEDING_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bleeding"));
	public static final ResourceKey<DamageType> EXPLOSIVE_CHOCOLATE_BAR_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "explosive_chocolate_bar"));
	public static final ResourceKey<DamageType> DEATHWEED_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "deathweed"));
	public static final ResourceKey<DamageType> WOODEN_SPIKES_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "wooden_spikes"));
	public static final ResourceKey<DamageType> SPIKE_TRAP_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "spike_trap"));
	public static final ResourceKey<DamageType> USED_SYRINGE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "used_syringe"));
	public static final ResourceKey<DamageType> MORTAR_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "mortar"));
	public static final ResourceKey<DamageType> LANDMINE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "landmine"));
	public static final ResourceKey<DamageType> DEADMANS_DESERT_ATMOSPHERE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "deadmans_desert_atmosphere"));
	public static final ResourceKey<DamageType> METEOR_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "meteor"));
	public static final ResourceKey<DamageType> PUNJI_STICKS_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "punji_sticks"));
	public static final ResourceKey<DamageType> PUNJI_STICKS_FALL_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "punji_sticks_fall"));
	public static final ResourceKey<DamageType> BULLET_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "bullet"));
	public static final ResourceKey<DamageType> CANNONBALL_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "cannonball"));
	public static final ResourceKey<DamageType> EXPLOSIVE_CANNONBALL_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "explosive_cannonball"));
	public static final ResourceKey<DamageType> EXPLOSIVE_ARROW_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "explosive_arrow"));
	public static final ResourceKey<DamageType> HELLFIRE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "hellfire"));

	private static DamageSource getDamageSource(RegistryAccess registryAccess, ResourceKey<DamageType> key) {
		return new DamageSource(registryAccess.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key));
	}

	private static DamageSource getEntityDamageSource(RegistryAccess registryAccess, ResourceKey<DamageType> key, Entity directEntity, Entity causingEntity) {
		return new DamageSource(registryAccess.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(key), directEntity, causingEntity);
	}

	public static DamageSource barbedWire(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, BARBED_WIRE_KEY);
	}

	public static DamageSource bearTrap(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, BEAR_TRAP_KEY);
	}

	public static DamageSource bleeding(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, BLEEDING_KEY);
	}

	public static DamageSource explosiveChocolateBar(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, EXPLOSIVE_CHOCOLATE_BAR_KEY);
	}

	public static DamageSource deathweed(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, DEATHWEED_KEY);
	}

	public static DamageSource woodenSpikes(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, WOODEN_SPIKES_KEY);
	}

	public static DamageSource spikeTrap(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, SPIKE_TRAP_KEY);
	}

	public static DamageSource usedSyringe(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, USED_SYRINGE_KEY);
	}

	public static DamageSource mortar(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, MORTAR_KEY);
	}

	public static DamageSource landmine(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, LANDMINE_KEY);
	}

	public static DamageSource deadmansDesertAtmosphere(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, DEADMANS_DESERT_ATMOSPHERE_KEY);
	}

	public static DamageSource punjiSticks(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, PUNJI_STICKS_KEY);
	}

	public static DamageSource punjiSticksFall(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, PUNJI_STICKS_FALL_KEY);
	}

	public static DamageSource hellfire(RegistryAccess registryAccess) {
		return getDamageSource(registryAccess, HELLFIRE_KEY);
	}

	public static DamageSource meteor(Entity entity, Entity owner) {
		return getEntityDamageSource(entity.level().registryAccess(), METEOR_KEY, entity, owner);
	}

	public static DamageSource bullet(Entity entity, Entity owner) {
		return getEntityDamageSource(entity.level().registryAccess(), BULLET_KEY, entity, owner);
	}

	public static DamageSource cannonball(Entity entity, Entity owner) {
		return getEntityDamageSource(entity.level().registryAccess(), CANNONBALL_KEY, entity, owner);
	}

	public static DamageSource explosiveCannonball(Entity entity, Entity owner) {
		return getEntityDamageSource(entity.level().registryAccess(), EXPLOSIVE_CANNONBALL_KEY, entity, owner);
	}

	public static DamageSource explosiveArrow(Entity entity, Entity owner) {
		return getEntityDamageSource(entity.level().registryAccess(), EXPLOSIVE_ARROW_KEY, entity, owner);
	}
}