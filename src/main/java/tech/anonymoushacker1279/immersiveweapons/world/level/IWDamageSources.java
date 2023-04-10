package tech.anonymoushacker1279.immersiveweapons.world.level;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import tech.anonymoushacker1279.immersiveweapons.data.damage_types.IWDamageTypes;

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

	public static void init(RegistryAccess registryAccess) {
		access = registryAccess;

		BARBED_WIRE = getDamageSource(registryAccess, IWDamageTypes.BARBED_WIRE_KEY);
		BEAR_TRAP = getDamageSource(registryAccess, IWDamageTypes.BEAR_TRAP_KEY);
		BLEEDING = getDamageSource(registryAccess, IWDamageTypes.BLEEDING_KEY);
		EXPLOSIVE_CHOCOLATE_BAR = getDamageSource(registryAccess, IWDamageTypes.EXPLOSIVE_CHOCOLATE_BAR_KEY);
		DEATHWEED = getDamageSource(registryAccess, IWDamageTypes.DEATHWEED_KEY);
		WOODEN_SPIKES = getDamageSource(registryAccess, IWDamageTypes.WOODEN_SPIKES_KEY);
		SPIKE_TRAP = getDamageSource(registryAccess, IWDamageTypes.SPIKE_TRAP_KEY);
		USED_SYRINGE = getDamageSource(registryAccess, IWDamageTypes.USED_SYRINGE_KEY);
		MORTAR = getDamageSource(registryAccess, IWDamageTypes.MORTAR_KEY);
		LANDMINE = getDamageSource(registryAccess, IWDamageTypes.LANDMINE_KEY);
		DEADMANS_DESERT_ATMOSPHERE = getDamageSource(registryAccess, IWDamageTypes.DEADMANS_DESERT_ATMOSPHERE_KEY);
		PUNJI_STICKS = getDamageSource(registryAccess, IWDamageTypes.PUNJI_STICKS_KEY);
		PUNJI_STICKS_FALL = getDamageSource(registryAccess, IWDamageTypes.PUNJI_STICKS_FALL_KEY);
	}

	private static DamageSource getDamageSource(RegistryAccess registryAccess, ResourceKey<DamageType> key) {
		return new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
	}

	private static DamageSource getEntityDamageSource(RegistryAccess registryAccess, ResourceKey<DamageType> key, Entity directEntity, Entity causingEntity) {
		return new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), directEntity, causingEntity);
	}

	public static DamageSource meteor(Entity entity, Entity owner) {
		return getEntityDamageSource(access, IWDamageTypes.METEOR_KEY, entity, owner);
	}
}