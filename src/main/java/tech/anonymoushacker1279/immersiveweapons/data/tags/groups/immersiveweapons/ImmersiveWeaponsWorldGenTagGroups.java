package tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons;

import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class ImmersiveWeaponsWorldGenTagGroups {

	// Biome tags
	public static final TagKey<Biome> IS_BATTLEFIELD = createBiomeTag("is_battlefield");
	public static final TagKey<Biome> IS_TILTROS = createBiomeTag("is_tiltros");

	// Structure Tags
	public static final TagKey<Biome> HAS_ABANDONED_FACTORY = createStructureTag("has_abandoned_factory");
	public static final TagKey<Biome> HAS_PITFALL_TRAP = createStructureTag("has_pitfall_trap");
	public static final TagKey<Biome> HAS_BEAR_TRAP = createStructureTag("has_bear_trap");
	public static final TagKey<Biome> HAS_LANDMINE_TRAP = createStructureTag("has_landmine_trap");
	public static final TagKey<Biome> HAS_UNDERGROUND_BUNKER = createStructureTag("has_underground_bunker");
	public static final TagKey<Biome> HAS_CLOUD_ISLAND = createStructureTag("has_cloud_island");
	public static final TagKey<Biome> HAS_CAMPSITE = createStructureTag("has_campsite");
	public static final TagKey<Biome> HAS_WATER_TOWER = createStructureTag("has_water_tower");
	public static final TagKey<Biome> HAS_HANS_HUT = createStructureTag("has_hans_hut");
	public static final TagKey<Biome> HAS_DESTROYED_HOUSE = createStructureTag("has_destroyed_house");
	public static final TagKey<Biome> HAS_BATTLEFIELD_CAMP = createStructureTag("has_battlefield_camp");
	public static final TagKey<Biome> HAS_GRAVEYARD = createStructureTag("has_graveyard");
	public static final TagKey<Biome> HAS_BATTLEFIELD_TOWN = createStructureTag("has_battlefield_town");

	/**
	 * Helper method for creating a biome tag for containing biomes.
	 *
	 * @param tag a string to be used for the tag
	 */
	private static TagKey<Biome> createBiomeTag(String tag) {
		return BiomeTags.create(ImmersiveWeapons.MOD_ID + ":" + tag);
	}

	/**
	 * Helper method for creating a biome tag for containing structures.
	 *
	 * @param tag a string to be used for the tag
	 */
	private static TagKey<Biome> createStructureTag(String tag) {
		return BiomeTags.create(ImmersiveWeapons.MOD_ID + ":has_structure/" + tag);
	}
}