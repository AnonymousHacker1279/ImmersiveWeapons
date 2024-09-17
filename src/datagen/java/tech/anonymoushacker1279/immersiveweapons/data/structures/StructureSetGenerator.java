package tech.anonymoushacker1279.immersiveweapons.data.structures;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.List;

public class StructureSetGenerator {

	public static final ResourceKey<StructureSet> ABANDONED_FACTORY = createKey("abandoned_factory");
	public static final ResourceKey<StructureSet> BATTLEFIELD_CAMP = createKey("battlefield_camp");
	public static final ResourceKey<StructureSet> BATTLEFIELD_TOWN = createKey("battlefield_town");
	public static final ResourceKey<StructureSet> BEAR_TRAP = createKey("bear_trap");
	public static final ResourceKey<StructureSet> BIODOME = createKey("biodome");
	public static final ResourceKey<StructureSet> CAMPSITE = createKey("campsite");
	public static final ResourceKey<StructureSet> CELESTIAL_ASTEROID = createKey("celestial_asteroid");
	public static final ResourceKey<StructureSet> CHAMPION_TOWER = createKey("champion_tower");
	public static final ResourceKey<StructureSet> CLOUD_ISLAND = createKey("cloud_island");
	public static final ResourceKey<StructureSet> COMMANDER_OUTPOST = createKey("commander_outpost");
	public static final ResourceKey<StructureSet> DESTROYED_HOUSE = createKey("destroyed_house");
	public static final ResourceKey<StructureSet> GRAVEYARD = createKey("graveyard");
	public static final ResourceKey<StructureSet> HANS_HUT = createKey("hans_hut");
	public static final ResourceKey<StructureSet> LANDMINE_TRAP = createKey("landmine_trap");
	public static final ResourceKey<StructureSet> PITFALL_TRAP = createKey("pitfall_trap");
	public static final ResourceKey<StructureSet> SPACE_OBSERVATORY = createKey("space_observatory");
	public static final ResourceKey<StructureSet> UNDERGROUND_BUNKER = createKey("underground_bunker");
	public static final ResourceKey<StructureSet> WATER_TOWER = createKey("water_tower");

	private static ResourceKey<StructureSet> createKey(String name) {
		return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}

	public static void bootstrap(BootstrapContext<StructureSet> context) {
		HolderGetter<Structure> structureHolderGetter = context.lookup(Registries.STRUCTURE);

		register(context, ABANDONED_FACTORY, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.ABANDONED_FACTORY), 1)
				),
				new RandomSpreadStructurePlacement(40, 20, RandomSpreadType.LINEAR, 959874384)
		));

		register(context, BATTLEFIELD_CAMP, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.BATTLEFIELD_CAMP), 1)
				),
				new RandomSpreadStructurePlacement(12, 5, RandomSpreadType.LINEAR, 458962175)
		));

		register(context, BATTLEFIELD_TOWN, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.BATTLEFIELD_TOWN), 1)
				),
				new RandomSpreadStructurePlacement(24, 8, RandomSpreadType.LINEAR, 176482913)
		));

		register(context, BEAR_TRAP, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.BEAR_TRAP), 1)
				),
				new RandomSpreadStructurePlacement(7, 2, RandomSpreadType.LINEAR, 794532168)
		));

		register(context, BIODOME, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.BIODOME), 1)
				),
				new RandomSpreadStructurePlacement(16, 10, RandomSpreadType.LINEAR, 497613544)
		));

		register(context, CAMPSITE, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.CAMPSITE), 1)
				),
				new RandomSpreadStructurePlacement(12, 5, RandomSpreadType.LINEAR, 671249835)
		));

		register(context, CELESTIAL_ASTEROID, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.CELESTIAL_ASTEROID), 1)
				),
				new RandomSpreadStructurePlacement(15, 5, RandomSpreadType.LINEAR, 549831672)
		));

		register(context, CHAMPION_TOWER, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.CHAMPION_TOWER), 1)
				),
				new RandomSpreadStructurePlacement(70, 40, RandomSpreadType.LINEAR, 268595857)
		));

		register(context, CLOUD_ISLAND, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.CLOUD_ISLAND), 1)
				),
				new RandomSpreadStructurePlacement(25, 12, RandomSpreadType.LINEAR, 349821657)
		));

		register(context, COMMANDER_OUTPOST, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.COMMANDER_OUTPOST), 1)
				),
				new RandomSpreadStructurePlacement(20, 10, RandomSpreadType.LINEAR, 548319557)
		));

		register(context, DESTROYED_HOUSE, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.DESTROYED_HOUSE), 1)
				),
				new RandomSpreadStructurePlacement(10, 5, RandomSpreadType.LINEAR, 615794356)
		));

		register(context, GRAVEYARD, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.GRAVEYARD), 1)
				),
				new RandomSpreadStructurePlacement(20, 15, RandomSpreadType.LINEAR, 346751289)
		));

		register(context, HANS_HUT, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.HANS_HUT), 1)
				),
				new RandomSpreadStructurePlacement(3, 2, RandomSpreadType.LINEAR, 597346851)
		));

		register(context, LANDMINE_TRAP, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.LANDMINE_TRAP), 1)
				),
				new RandomSpreadStructurePlacement(7, 4, RandomSpreadType.LINEAR, 959874384)
		));

		register(context, PITFALL_TRAP, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.PITFALL_TRAP), 1)
				),
				new RandomSpreadStructurePlacement(6, 2, RandomSpreadType.LINEAR, 875412395)
		));

		register(context, SPACE_OBSERVATORY, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.SPACE_OBSERVATORY), 1)
				),
				new RandomSpreadStructurePlacement(20, 14, RandomSpreadType.LINEAR, 468512124)
		));

		register(context, UNDERGROUND_BUNKER, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.UNDERGROUND_BUNKER), 1)
				),
				new RandomSpreadStructurePlacement(15, 7, RandomSpreadType.LINEAR, 548796135)
		));

		register(context, WATER_TOWER, new StructureSet(
				List.of(
						StructureSet.entry(structureHolderGetter.getOrThrow(StructureGenerator.WATER_TOWER), 1)
				),
				new RandomSpreadStructurePlacement(20, 12, RandomSpreadType.LINEAR, 246975135)
		));
	}

	private static void register(BootstrapContext<StructureSet> context, ResourceKey<StructureSet> key, StructureSet structureSet) {
		context.register(key, structureSet);
	}
}