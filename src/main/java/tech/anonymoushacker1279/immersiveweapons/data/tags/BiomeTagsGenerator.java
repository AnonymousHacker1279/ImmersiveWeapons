package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ExistingFileHelper.ResourceType;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeWorldGenTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsWorldGenTagGroups;
import tech.anonymoushacker1279.immersiveweapons.world.level.levelgen.biomes.BiomesAndDimensions;

public class BiomeTagsGenerator extends TagsProvider<Biome> {

	public BiomeTagsGenerator(DataGenerator generator, ExistingFileHelper fileHelper) {
		super(generator, BuiltinRegistries.BIOME, ImmersiveWeapons.MOD_ID, fileHelper);

		fileHelper.trackGenerated(new ResourceLocation(ImmersiveWeapons.MOD_ID, "battlefield"),
				new ResourceType(PackType.SERVER_DATA, ".json", TagManager.getTagDir(Registry.BIOME_REGISTRY)));
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void addTags() {
		// Biome tags
		tag(ForgeWorldGenTagGroups.IS_PLAINS)
				.add(Biomes.PLAINS, Biomes.SNOWY_PLAINS, Biomes.SUNFLOWER_PLAINS);

		tag(ForgeWorldGenTagGroups.IS_WET_CAVE)
				.add(Biomes.DRIPSTONE_CAVES, Biomes.LUSH_CAVES);

		tag(BiomeTags.IS_OVERWORLD)
				.add(BiomesAndDimensions.BATTLEFIELD);

		tag(ImmersiveWeaponsWorldGenTagGroups.IS_BATTLEFIELD)
				.add(BiomesAndDimensions.BATTLEFIELD);

		tag(ImmersiveWeaponsWorldGenTagGroups.IS_TILTROS_WASTES)
				.add(BiomesAndDimensions.TILTROS_WASTES);

		tag(ImmersiveWeaponsWorldGenTagGroups.IS_STARLIGHT_PLAINS)
				.add(BiomesAndDimensions.STARLIGHT_PLAINS);

		tag(ImmersiveWeaponsWorldGenTagGroups.IS_DEADMANS_DESERT)
				.add(BiomesAndDimensions.DEADMANS_DESERT);

		tag(ImmersiveWeaponsWorldGenTagGroups.IS_TILTROS)
				.add(BiomesAndDimensions.TILTROS_WASTES)
				.add(BiomesAndDimensions.STARLIGHT_PLAINS)
				.add(BiomesAndDimensions.DEADMANS_DESERT);

		// Structure tags
		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_ABANDONED_FACTORY)
				.addTags(ForgeWorldGenTagGroups.IS_PLAINS, BiomeTags.IS_FOREST);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_PITFALL_TRAP)
				.addTag(BiomeTags.IS_JUNGLE);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_BEAR_TRAP)
				.addTags(ForgeWorldGenTagGroups.IS_PLAINS, BiomeTags.IS_FOREST);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_LANDMINE_TRAP)
				.add(Biomes.DESERT);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_UNDERGROUND_BUNKER)
				.addTags(ForgeWorldGenTagGroups.IS_PLAINS, BiomeTags.IS_FOREST);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_CLOUD_ISLAND)
				.addTag(BiomeTags.IS_TAIGA);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_CAMPSITE)
				.addTag(ForgeWorldGenTagGroups.IS_PLAINS)
				.add(Biomes.DESERT);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_WATER_TOWER)
				.addTag(ForgeWorldGenTagGroups.IS_PLAINS);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_HANS_HUT)
				.add(Biomes.LUSH_CAVES)
				.add(Biomes.DRIPSTONE_CAVES);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_DESTROYED_HOUSE)
				.add(BiomesAndDimensions.BATTLEFIELD);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_BATTLEFIELD_CAMP)
				.add(BiomesAndDimensions.BATTLEFIELD);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_GRAVEYARD)
				.add(BiomesAndDimensions.BATTLEFIELD);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_BATTLEFIELD_TOWN)
				.add(BiomesAndDimensions.BATTLEFIELD);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_CELESTIAL_ASTEROID)
				.add(BiomesAndDimensions.DEADMANS_DESERT);

		tag(ImmersiveWeaponsWorldGenTagGroups.HAS_BIODOME)
				.add(BiomesAndDimensions.DEADMANS_DESERT);
	}

	@Override
	public String getName() {
		return "Biome Tags";
	}
}