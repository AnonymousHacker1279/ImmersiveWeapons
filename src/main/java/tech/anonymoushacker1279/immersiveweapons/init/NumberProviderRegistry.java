package tech.anonymoushacker1279.immersiveweapons.init;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.number_providers.EntityKillersValue;

public class NumberProviderRegistry {

	// Number Provider Registry
	public static final DeferredRegister<MapCodec<? extends ContextIntProvider>> NUMBER_PROVIDERS = DeferredRegister.create(Registries.CONTEXT_INT_PROVIDER_TYPE, ImmersiveWeapons.MOD_ID);

	static {
		NUMBER_PROVIDERS.register("entity_killers", () -> EntityKillersValue.MAP_CODEC);
	}
}