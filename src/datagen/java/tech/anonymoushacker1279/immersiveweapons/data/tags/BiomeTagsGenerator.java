package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonWorldGenTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWWorldGenTagGroups;

import java.util.concurrent.CompletableFuture;

public class BiomeTagsGenerator extends BiomeTagsProvider {

	public BiomeTagsGenerator(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider, ImmersiveWeapons.MOD_ID);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void addTags(Provider provider) {

		// Biome tags
		tag(CommonWorldGenTagGroups.IS_WET_CAVE)
				.add(Biomes.DRIPSTONE_CAVES, Biomes.LUSH_CAVES);

		tag(CommonWorldGenTagGroups.IS_LUSH_CAVE)
				.add(Biomes.LUSH_CAVES);

		tag(BiomeTags.IS_OVERWORLD)
				.add(IWBiomes.BATTLEFIELD);

		tag(IWWorldGenTagGroups.IS_BATTLEFIELD)
				.add(IWBiomes.BATTLEFIELD);

		tag(IWWorldGenTagGroups.IS_TILTROS_WASTES)
				.add(IWBiomes.TILTROS_WASTES);

		tag(IWWorldGenTagGroups.IS_STARLIGHT_PLAINS)
				.add(IWBiomes.STARLIGHT_PLAINS);

		tag(IWWorldGenTagGroups.IS_DEADMANS_DESERT)
				.add(IWBiomes.DEADMANS_DESERT);

		tag(IWWorldGenTagGroups.IS_TILTROS)
				.add(IWBiomes.TILTROS_WASTES)
				.add(IWBiomes.STARLIGHT_PLAINS)
				.add(IWBiomes.DEADMANS_DESERT);

		// Structure tags
		tag(IWWorldGenTagGroups.HAS_ABANDONED_FACTORY)
				.addTags(Tags.Biomes.IS_PLAINS, Tags.Biomes.IS_SNOWY_PLAINS, BiomeTags.IS_FOREST);

		tag(IWWorldGenTagGroups.HAS_PITFALL_TRAP)
				.addTag(BiomeTags.IS_JUNGLE);

		tag(IWWorldGenTagGroups.HAS_BEAR_TRAP)
				.addTags(Tags.Biomes.IS_PLAINS, Tags.Biomes.IS_SNOWY_PLAINS, BiomeTags.IS_FOREST);

		tag(IWWorldGenTagGroups.HAS_LANDMINE_TRAP)
				.add(Biomes.DESERT);

		tag(IWWorldGenTagGroups.HAS_UNDERGROUND_BUNKER)
				.addTags(Tags.Biomes.IS_PLAINS, Tags.Biomes.IS_SNOWY_PLAINS, BiomeTags.IS_FOREST);

		tag(IWWorldGenTagGroups.HAS_CLOUD_ISLAND)
				.addTag(BiomeTags.IS_TAIGA);

		tag(IWWorldGenTagGroups.HAS_CAMPSITE)
				.addTags(Tags.Biomes.IS_PLAINS, Tags.Biomes.IS_SNOWY_PLAINS)
				.add(Biomes.DESERT);

		tag(IWWorldGenTagGroups.HAS_WATER_TOWER)
				.addTags(Tags.Biomes.IS_PLAINS, Tags.Biomes.IS_SNOWY_PLAINS);

		tag(IWWorldGenTagGroups.HAS_HANS_HUT)
				.add(Biomes.LUSH_CAVES)
				.add(Biomes.DRIPSTONE_CAVES);

		tag(IWWorldGenTagGroups.HAS_DESTROYED_HOUSE)
				.add(IWBiomes.BATTLEFIELD);

		tag(IWWorldGenTagGroups.HAS_BATTLEFIELD_CAMP)
				.add(IWBiomes.BATTLEFIELD);

		tag(IWWorldGenTagGroups.HAS_GRAVEYARD)
				.add(IWBiomes.BATTLEFIELD);

		tag(IWWorldGenTagGroups.HAS_BATTLEFIELD_TOWN)
				.add(IWBiomes.BATTLEFIELD);

		tag(IWWorldGenTagGroups.HAS_CELESTIAL_ASTEROID)
				.add(IWBiomes.DEADMANS_DESERT);

		tag(IWWorldGenTagGroups.HAS_BIODOME)
				.add(IWBiomes.DEADMANS_DESERT);

		tag(IWWorldGenTagGroups.HAS_SPACE_OBSERVATORY)
				.add(IWBiomes.STARLIGHT_PLAINS);

		tag(IWWorldGenTagGroups.HAS_CHAMPION_TOWER)
				.addTags(Tags.Biomes.IS_PLAINS, Tags.Biomes.IS_SNOWY_PLAINS);

		tag(IWWorldGenTagGroups.HAS_COMMANDER_OUTPOST)
				.add(IWBiomes.BATTLEFIELD);
	}
}