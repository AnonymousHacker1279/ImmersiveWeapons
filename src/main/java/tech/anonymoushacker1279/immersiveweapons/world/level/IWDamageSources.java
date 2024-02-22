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

	private static RegistryAccess access;

	public static DamageSource BARBED_WIRE;
	public static DamageSource BEAR_TRAP;
	public static DamageSource BLEEDING;
	public static DamageSource EXPLOSIVE_CHOCOLATE_BAR;
	public static DamageSource DEATHWEED;
	public static DamageSource WOODEN_SPIKES;
	public static DamageSource SPIKE_TRAP;
	public static DamageSource USED_SYRINGE;
	public static DamageSource MORTAR;
	public static DamageSource LANDMINE;
	public static DamageSource DEADMANS_DESERT_ATMOSPHERE;
	public static DamageSource PUNJI_STICKS;
	public static DamageSource PUNJI_STICKS_FALL;
	public static DamageSource BULLET;
	public static DamageSource CANNONBALL;
	public static DamageSource EXPLOSIVE_CANNONBALL;
	public static DamageSource EXPLOSIVE_ARROW;

	public static final ResourceKey<DamageType> BARBED_WIRE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "barbed_wire"));
	public static final ResourceKey<DamageType> BEAR_TRAP_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "bear_trap"));
	public static final ResourceKey<DamageType> BLEEDING_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "bleeding"));
	public static final ResourceKey<DamageType> EXPLOSIVE_CHOCOLATE_BAR_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "explosive_chocolate_bar"));
	public static final ResourceKey<DamageType> DEATHWEED_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "deathweed"));
	public static final ResourceKey<DamageType> WOODEN_SPIKES_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "wooden_spikes"));
	public static final ResourceKey<DamageType> SPIKE_TRAP_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "spike_trap"));
	public static final ResourceKey<DamageType> USED_SYRINGE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "used_syringe"));
	public static final ResourceKey<DamageType> MORTAR_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "mortar"));
	public static final ResourceKey<DamageType> LANDMINE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "landmine"));
	public static final ResourceKey<DamageType> DEADMANS_DESERT_ATMOSPHERE_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "deadmans_desert_atmosphere"));
	public static final ResourceKey<DamageType> METEOR_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "meteor"));
	public static final ResourceKey<DamageType> PUNJI_STICKS_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "punji_sticks"));
	public static final ResourceKey<DamageType> PUNJI_STICKS_FALL_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "punji_sticks_fall"));
	public static final ResourceKey<DamageType> BULLET_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "bullet"));
	public static final ResourceKey<DamageType> CANNONBALL_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "cannonball"));
	public static final ResourceKey<DamageType> EXPLOSIVE_CANNONBALL_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "explosive_cannonball"));
	public static final ResourceKey<DamageType> EXPLOSIVE_ARROW_KEY = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(ImmersiveWeapons.MOD_ID, "explosive_arrow"));

	public static void init(RegistryAccess registryAccess) {
		access = registryAccess;

		BARBED_WIRE = getDamageSource(registryAccess, BARBED_WIRE_KEY);
		BEAR_TRAP = getDamageSource(registryAccess, BEAR_TRAP_KEY);
		BLEEDING = getDamageSource(registryAccess, BLEEDING_KEY);
		EXPLOSIVE_CHOCOLATE_BAR = getDamageSource(registryAccess, EXPLOSIVE_CHOCOLATE_BAR_KEY);
		DEATHWEED = getDamageSource(registryAccess, DEATHWEED_KEY);
		WOODEN_SPIKES = getDamageSource(registryAccess, WOODEN_SPIKES_KEY);
		SPIKE_TRAP = getDamageSource(registryAccess, SPIKE_TRAP_KEY);
		USED_SYRINGE = getDamageSource(registryAccess, USED_SYRINGE_KEY);
		MORTAR = getDamageSource(registryAccess, MORTAR_KEY);
		LANDMINE = getDamageSource(registryAccess, LANDMINE_KEY);
		DEADMANS_DESERT_ATMOSPHERE = getDamageSource(registryAccess, DEADMANS_DESERT_ATMOSPHERE_KEY);
		PUNJI_STICKS = getDamageSource(registryAccess, PUNJI_STICKS_KEY);
		PUNJI_STICKS_FALL = getDamageSource(registryAccess, PUNJI_STICKS_FALL_KEY);
		BULLET = getDamageSource(registryAccess, BULLET_KEY);
		CANNONBALL = getDamageSource(registryAccess, CANNONBALL_KEY);
		EXPLOSIVE_CANNONBALL = getDamageSource(registryAccess, EXPLOSIVE_CANNONBALL_KEY);
		EXPLOSIVE_ARROW = getDamageSource(registryAccess, EXPLOSIVE_ARROW_KEY);
	}

	private static DamageSource getDamageSource(RegistryAccess registryAccess, ResourceKey<DamageType> key) {
		return new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
	}

	private static DamageSource getEntityDamageSource(RegistryAccess registryAccess, ResourceKey<DamageType> key, Entity directEntity, Entity causingEntity) {
		return new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), directEntity, causingEntity);
	}

	public static DamageSource meteor(Entity entity, Entity owner) {
		return getEntityDamageSource(access, METEOR_KEY, entity, owner);
	}

	public static DamageSource bullet(Entity entity, Entity owner) {
		return getEntityDamageSource(access, BULLET_KEY, entity, owner);
	}

	public static DamageSource cannonball(Entity entity, Entity owner) {
		return getEntityDamageSource(access, CANNONBALL_KEY, entity, owner);
	}

	public static DamageSource explosiveCannonball(Entity entity, Entity owner) {
		return getEntityDamageSource(access, EXPLOSIVE_CANNONBALL_KEY, entity, owner);
	}

	public static DamageSource explosiveArrow(Entity entity, Entity owner) {
		return getEntityDamageSource(access, EXPLOSIVE_ARROW_KEY, entity, owner);
	}
}