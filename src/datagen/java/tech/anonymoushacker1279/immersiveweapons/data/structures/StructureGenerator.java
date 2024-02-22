package tech.anonymoushacker1279.immersiveweapons.data.structures;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride.BoundingBoxType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWWorldGenTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

import java.util.Map;

public class StructureGenerator {

	public static final ResourceKey<Structure> ABANDONED_FACTORY = createKey("abandoned_factory");
	public static final ResourceKey<Structure> BATTLEFIELD_CAMP = createKey("battlefield_camp");
	public static final ResourceKey<Structure> BATTLEFIELD_TOWN = createKey("battlefield_town");
	public static final ResourceKey<Structure> BEAR_TRAP = createKey("bear_trap");
	public static final ResourceKey<Structure> BIODOME = createKey("biodome");
	public static final ResourceKey<Structure> CAMPSITE = createKey("campsite");
	public static final ResourceKey<Structure> CELESTIAL_ASTEROID = createKey("celestial_asteroid");
	public static final ResourceKey<Structure> CHAMPION_TOWER = createKey("champion_tower");
	public static final ResourceKey<Structure> CLOUD_ISLAND = createKey("cloud_island");
	public static final ResourceKey<Structure> COMMANDER_OUTPOST = createKey("commander_outpost");
	public static final ResourceKey<Structure> DESTROYED_HOUSE = createKey("destroyed_house");
	public static final ResourceKey<Structure> GRAVEYARD = createKey("graveyard");
	public static final ResourceKey<Structure> HANS_HUT = createKey("hans_hut");
	public static final ResourceKey<Structure> LANDMINE_TRAP = createKey("landmine_trap");
	public static final ResourceKey<Structure> PITFALL_TRAP = createKey("pitfall_trap");
	public static final ResourceKey<Structure> SPACE_OBSERVATORY = createKey("space_observatory");
	public static final ResourceKey<Structure> UNDERGROUND_BUNKER = createKey("underground_bunker");
	public static final ResourceKey<Structure> WATER_TOWER = createKey("water_tower");

	private static ResourceKey<Structure> createKey(String name) {
		return ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstapContext<Structure> context) {
		HolderGetter<Biome> biomeHolderGetter = context.lookup(Registries.BIOME);
		HolderGetter<StructureTemplatePool> templatePoolHolderGetter = context.lookup(Registries.TEMPLATE_POOL);

		register(context, ABANDONED_FACTORY, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_ABANDONED_FACTORY),
						Map.of(
								MobCategory.MONSTER,
								new StructureSpawnOverride(
										BoundingBoxType.STRUCTURE,
										WeightedRandomList.create(new SpawnerData(EntityType.ZOMBIE, 1, 1, 2))
								)
						),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.ABANDONED_FACTORY),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, BATTLEFIELD_CAMP, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_BATTLEFIELD_CAMP),
						Map.of(
								MobCategory.MONSTER,
								new StructureSpawnOverride(
										BoundingBoxType.STRUCTURE,
										WeightedRandomList.create(new SpawnerData(EntityRegistry.DYING_SOLDIER_ENTITY.get(), 1, 2, 2))
								)
						),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.BATTLEFIELD_CAMP),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, BATTLEFIELD_TOWN, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_BATTLEFIELD_TOWN),
						Map.of(
								MobCategory.CREATURE,
								new StructureSpawnOverride(
										BoundingBoxType.STRUCTURE,
										WeightedRandomList.create(new SpawnerData(EntityType.CAT, 1, 1, 2))
								)
						),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.BATTLEFIELD_TOWN),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, BEAR_TRAP, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_BEAR_TRAP),
						Map.of(),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.BEAR_TRAP),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, BIODOME, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_BIODOME),
						Map.of(),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.BIODOME),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, CAMPSITE, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_CAMPSITE),
						Map.of(
								MobCategory.MISC,
								new StructureSpawnOverride(
										BoundingBoxType.STRUCTURE,
										WeightedRandomList.create(new SpawnerData(EntityRegistry.MINUTEMAN_ENTITY.get(), 1, 1, 2))
								)
						),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.CAMPSITE),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, CELESTIAL_ASTEROID, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_CELESTIAL_ASTEROID),
						Map.of(
								MobCategory.MONSTER,
								new StructureSpawnOverride(
										BoundingBoxType.STRUCTURE,
										WeightedRandomList.create(new SpawnerData(EntityType.SKELETON, 1, 1, 2))
								)
						),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.NONE
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.CELESTIAL_ASTEROID),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, CHAMPION_TOWER, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_CHAMPION_TOWER),
						Map.of(),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.CHAMPION_TOWER_BOTTOM),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, CLOUD_ISLAND, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_CLOUD_ISLAND),
						Map.of(),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.NONE
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.CLOUD_ISLAND),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(64)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, COMMANDER_OUTPOST, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_COMMANDER_OUTPOST),
						Map.of(
								MobCategory.MONSTER,
								new StructureSpawnOverride(
										BoundingBoxType.STRUCTURE,
										WeightedRandomList.create(new SpawnerData(EntityRegistry.DYING_SOLDIER_ENTITY.get(), 1, 1, 2))
								)
						),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.COMMANDER_OUTPOST),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, DESTROYED_HOUSE, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_DESTROYED_HOUSE),
						Map.of(),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.DESTROYED_HOUSE),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, GRAVEYARD, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_GRAVEYARD),
						Map.of(
								MobCategory.MONSTER,
								new StructureSpawnOverride(
										BoundingBoxType.STRUCTURE,
										WeightedRandomList.create(new SpawnerData(EntityType.ZOMBIE, 1, 1, 2))
								)
						),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.GRAVEYARD),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, HANS_HUT, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_HANS_HUT),
						Map.of(),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.HANS_HUT),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, LANDMINE_TRAP, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_LANDMINE_TRAP),
						Map.of(),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.LANDMINE_TRAP),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, PITFALL_TRAP, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_PITFALL_TRAP),
						Map.of(),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.NONE
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.PITFALL_TRAP),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(-3)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, SPACE_OBSERVATORY, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_SPACE_OBSERVATORY),
						Map.of(),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.SPACE_OBSERVATORY),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, UNDERGROUND_BUNKER, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_UNDERGROUND_BUNKER),
						Map.of(
								MobCategory.MONSTER,
								new StructureSpawnOverride(
										BoundingBoxType.STRUCTURE,
										WeightedRandomList.create(new SpawnerData(EntityRegistry.DYING_SOLDIER_ENTITY.get(), 1, 1, 2))
								)
						),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.UNDERGROUND_BUNKER_TOP),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));

		register(context, WATER_TOWER, new JigsawStructure(
				Structures.structure(
						biomeHolderGetter.getOrThrow(IWWorldGenTagGroups.HAS_WATER_TOWER),
						Map.of(),
						Decoration.SURFACE_STRUCTURES,
						TerrainAdjustment.BEARD_THIN
				),
				templatePoolHolderGetter.getOrThrow(StructureTemplatePoolGenerator.WATER_TOWER),
				1,
				ConstantHeight.of(VerticalAnchor.absolute(0)),
				false,
				Types.WORLD_SURFACE_WG
		));
	}

	protected static void register(BootstapContext<Structure> context, ResourceKey<Structure> key, Structure structure) {
		context.register(key, structure);
	}
}