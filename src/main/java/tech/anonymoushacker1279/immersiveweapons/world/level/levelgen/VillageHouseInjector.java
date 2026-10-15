package tech.anonymoushacker1279.immersiveweapons.world.level.levelgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import org.jspecify.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.ArrayList;
import java.util.List;

/// Appends mod structures to vanilla village template pools at runtime, so the vanilla pool JSON never has to be
/// overridden (overriding would clobber other mods that touch the same pools).
@EventBusSubscriber(modid = ImmersiveWeapons.MOD_ID)
public class VillageHouseInjector {

	private static final List<Injection> INJECTIONS = List.of(
			new Injection(
					poolKey("village/plains/houses"),
					Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "village/plains/houses/plains_gunsmith_house_1"),
					ProcessorLists.MOSSIFY_10_PERCENT,
					5),
			new Injection(
					poolKey("village/desert/houses"),
					Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "village/desert/houses/desert_gunsmith_house_1"),
					null,
					5),
			new Injection(
					poolKey("village/savanna/houses"),
					Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "village/savanna/houses/savanna_gunsmith_house_1"),
					null,
					5),
			new Injection(
					poolKey("village/snowy/houses"),
					Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "village/snowy/houses/snowy_gunsmith_house_1"),
					null,
					5),
			new Injection(
					poolKey("village/taiga/houses"),
					Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "village/taiga/houses/taiga_gunsmith_house_1"),
					ProcessorLists.MOSSIFY_10_PERCENT,
					5)
	);

	@SubscribeEvent
	public static void serverAboutToStart(ServerAboutToStartEvent event) {
		HolderLookup.Provider registries = event.getServer().registryAccess();
		HolderLookup.RegistryLookup<StructureTemplatePool> pools = registries.lookupOrThrow(Registries.TEMPLATE_POOL);
		HolderLookup.RegistryLookup<StructureProcessorList> processors = registries.lookupOrThrow(Registries.PROCESSOR_LIST);

		for (Injection injection : INJECTIONS) {
			StructureTemplatePool pool = pools.getOrThrow(injection.pool()).value();
			String location = injection.structure().toString();
			StructurePoolElement element = (injection.processors() == null
					? StructurePoolElement.legacy(location)
					: StructurePoolElement.legacy(location, processors.getOrThrow(injection.processors())))
					.apply(Projection.RIGID);

			// The raw list is what other mods/serialization read, the shuffled list is what generation actually samples from.
			// An element is listed once per weight in the latter.
			List<Pair<StructurePoolElement, Integer>> rawTemplates = new ArrayList<>(pool.getTemplates());
			rawTemplates.add(Pair.of(element, injection.weight()));
			pool.rawTemplates = rawTemplates;

			for (int i = 0; i < injection.weight(); i++) {
				pool.templates.add(element);
			}
		}
	}

	private static ResourceKey<StructureTemplatePool> poolKey(String path) {
		return ResourceKey.create(Registries.TEMPLATE_POOL, Identifier.withDefaultNamespace(path));
	}

	/// @param processors the processor list to apply, or `null` for none
	private record Injection(ResourceKey<StructureTemplatePool> pool, Identifier structure,
	                         @Nullable ResourceKey<StructureProcessorList> processors, int weight) {
	}
}