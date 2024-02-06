package tech.anonymoushacker1279.immersiveweapons.data.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class StructureTemplatePoolGenerator {

	public static final ResourceKey<StructureTemplatePool> ABANDONED_FACTORY = createKey("abandoned_factory");
	public static final ResourceKey<StructureTemplatePool> BATTLEFIELD_CAMP = createKey("battlefield_camp");
	public static final ResourceKey<StructureTemplatePool> BATTLEFIELD_TOWN = createKey("battlefield_town");
	public static final ResourceKey<StructureTemplatePool> BEAR_TRAP = createKey("bear_trap");
	public static final ResourceKey<StructureTemplatePool> BIODOME = createKey("biodome");
	public static final ResourceKey<StructureTemplatePool> CAMPSITE = createKey("campsite");
	public static final ResourceKey<StructureTemplatePool> CELESTIAL_ASTEROID = createKey("celestial_asteroid");
	public static final ResourceKey<StructureTemplatePool> CHAMPION_TOWER_TOP = createKey("champion_tower/top");
	public static final ResourceKey<StructureTemplatePool> CHAMPION_TOWER_BOTTOM = createKey("champion_tower/bottom");
	public static final ResourceKey<StructureTemplatePool> CLOUD_ISLAND = createKey("cloud_island");
	public static final ResourceKey<StructureTemplatePool> DESTROYED_HOUSE = createKey("destroyed_house");
	public static final ResourceKey<StructureTemplatePool> GRAVEYARD = createKey("graveyard");
	public static final ResourceKey<StructureTemplatePool> HANS_HUT = createKey("hans_hut");
	public static final ResourceKey<StructureTemplatePool> LANDMINE_TRAP = createKey("landmine_trap");
	public static final ResourceKey<StructureTemplatePool> PITFALL_TRAP = createKey("pitfall_trap");
	public static final ResourceKey<StructureTemplatePool> SPACE_OBSERVATORY = createKey("space_observatory");
	public static final ResourceKey<StructureTemplatePool> UNDERGROUND_BUNKER_TOP = createKey("underground_bunker/top");
	public static final ResourceKey<StructureTemplatePool> UNDERGROUND_BUNKER_BOTTOM = createKey("underground_bunker/bottom");
	public static final ResourceKey<StructureTemplatePool> WATER_TOWER = createKey("water_tower");

	private static ResourceKey<StructureTemplatePool> createKey(String name) {
		return ResourceKey.create(Registries.TEMPLATE_POOL, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
		HolderGetter<StructureTemplatePool> templatePoolHolderGetter = context.lookup(Registries.TEMPLATE_POOL);
		HolderGetter<StructureProcessorList> processorListsHolderGetter = context.lookup(Registries.PROCESSOR_LIST);

		register(context, ABANDONED_FACTORY, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":abandoned_factory"), 1)
				),
				Projection.RIGID));

		register(context, BATTLEFIELD_CAMP, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":battlefield_camp"), 1)
				),
				Projection.RIGID));

		register(context, BATTLEFIELD_TOWN, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":battlefield_town"), 1)
				),
				Projection.RIGID));

		register(context, BEAR_TRAP, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":bear_trap"), 1)
				),
				Projection.RIGID));

		register(context, BIODOME, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":biodome", processorListsHolderGetter.getOrThrow(StructureProcessorListGenerator.RUST_70_PERCENT)), 1)
				),
				Projection.RIGID));

		register(context, CAMPSITE, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":campsite"), 1)
				),
				Projection.RIGID));

		register(context, CELESTIAL_ASTEROID, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":celestial_asteroid"), 1)
				),
				Projection.RIGID));

		register(context, CHAMPION_TOWER_TOP, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":champion_tower/champion_tower_top"), 1)
				),
				Projection.RIGID));

		register(context, CHAMPION_TOWER_BOTTOM, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":champion_tower/champion_tower_bottom"), 1)
				),
				Projection.RIGID));

		register(context, CLOUD_ISLAND, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":cloud_island"), 1)
				),
				Projection.RIGID));

		register(context, DESTROYED_HOUSE, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":destroyed_house/house_1"), 1),
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":destroyed_house/house_2"), 1)
				),
				Projection.RIGID));

		register(context, GRAVEYARD, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":graveyard"), 1)
				),
				Projection.RIGID));

		register(context, HANS_HUT, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":hans_hut"), 1)
				),
				Projection.RIGID));

		register(context, LANDMINE_TRAP, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":landmine_trap"), 1)
				),
				Projection.RIGID));

		register(context, PITFALL_TRAP, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":pitfall_trap"), 1)
				),
				Projection.RIGID));

		register(context, SPACE_OBSERVATORY, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":space_observatory"), 1)
				),
				Projection.RIGID));

		register(context, UNDERGROUND_BUNKER_TOP, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":underground_bunker/underground_bunker_top", processorListsHolderGetter.getOrThrow(StructureProcessorListGenerator.RUST_70_PERCENT)), 1)
				),
				Projection.RIGID));

		register(context, UNDERGROUND_BUNKER_BOTTOM, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":underground_bunker/underground_bunker_bottom", processorListsHolderGetter.getOrThrow(StructureProcessorListGenerator.RUST_50_PERCENT)), 1)
				),
				Projection.RIGID));

		register(context, WATER_TOWER, new StructureTemplatePool(
				templatePoolHolderGetter.getOrThrow(Pools.EMPTY),
				ImmutableList.of(
						Pair.of(StructurePoolElement.single(ImmersiveWeapons.MOD_ID + ":water_tower", processorListsHolderGetter.getOrThrow(StructureProcessorListGenerator.RUST_70_PERCENT)), 1)
				),
				Projection.RIGID));
	}

	protected static void register(BootstapContext<StructureTemplatePool> context, ResourceKey<StructureTemplatePool> key, StructureTemplatePool pool) {
		context.register(key, pool);
	}
}