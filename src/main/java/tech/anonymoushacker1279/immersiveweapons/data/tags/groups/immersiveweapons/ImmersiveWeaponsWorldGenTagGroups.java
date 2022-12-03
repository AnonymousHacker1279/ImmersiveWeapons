package tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class ImmersiveWeaponsWorldGenTagGroups {

	// Biome tags
	public static final TagKey<Biome> IS_BATTLEFIELD = GeneralUtilities.createBiomeTag("is_battlefield");
	public static final TagKey<Biome> IS_TILTROS = GeneralUtilities.createBiomeTag("is_tiltros");
	public static final TagKey<Biome> IS_TILTROS_WASTES = GeneralUtilities.createBiomeTag("is_tiltros_wastes");
	public static final TagKey<Biome> IS_STARLIGHT_PLAINS = GeneralUtilities.createBiomeTag("is_starlight_plains");
	public static final TagKey<Biome> IS_DEADMANS_DESERT = GeneralUtilities.createBiomeTag("is_deadmans_desert");

	// Structure Tags
	public static final TagKey<Biome> HAS_ABANDONED_FACTORY = GeneralUtilities.createStructureTag("abandoned_factory");
	public static final TagKey<Biome> HAS_PITFALL_TRAP = GeneralUtilities.createStructureTag("pitfall_trap");
	public static final TagKey<Biome> HAS_BEAR_TRAP = GeneralUtilities.createStructureTag("bear_trap");
	public static final TagKey<Biome> HAS_LANDMINE_TRAP = GeneralUtilities.createStructureTag("landmine_trap");
	public static final TagKey<Biome> HAS_UNDERGROUND_BUNKER = GeneralUtilities.createStructureTag("underground_bunker");
	public static final TagKey<Biome> HAS_CLOUD_ISLAND = GeneralUtilities.createStructureTag("cloud_island");
	public static final TagKey<Biome> HAS_CAMPSITE = GeneralUtilities.createStructureTag("campsite");
	public static final TagKey<Biome> HAS_WATER_TOWER = GeneralUtilities.createStructureTag("water_tower");
	public static final TagKey<Biome> HAS_HANS_HUT = GeneralUtilities.createStructureTag("hans_hut");
	public static final TagKey<Biome> HAS_DESTROYED_HOUSE = GeneralUtilities.createStructureTag("destroyed_house");
	public static final TagKey<Biome> HAS_BATTLEFIELD_CAMP = GeneralUtilities.createStructureTag("battlefield_camp");
	public static final TagKey<Biome> HAS_GRAVEYARD = GeneralUtilities.createStructureTag("graveyard");
	public static final TagKey<Biome> HAS_BATTLEFIELD_TOWN = GeneralUtilities.createStructureTag("battlefield_town");
	public static final TagKey<Biome> HAS_CELESTIAL_ASTEROID = GeneralUtilities.createStructureTag("celestial_asteroid");

}