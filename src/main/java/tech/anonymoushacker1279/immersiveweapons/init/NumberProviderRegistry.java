package tech.anonymoushacker1279.immersiveweapons.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.number_providers.EntityKillersValue.Serializer;

public class NumberProviderRegistry {

	// Loot Number Provider Registry
	public static final DeferredRegister<LootNumberProviderType> NUMBER_PROVIDERS = DeferredRegister.create(Registries.LOOT_NUMBER_PROVIDER_TYPE, ImmersiveWeapons.MOD_ID);

	public static final RegistryObject<LootNumberProviderType> ENTITY_KILLERS = NUMBER_PROVIDERS.register("entity_killers", () -> new LootNumberProviderType(new Serializer()));
}