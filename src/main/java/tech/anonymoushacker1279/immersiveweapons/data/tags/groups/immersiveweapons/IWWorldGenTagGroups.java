package tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWWorldGenTagGroups {

	// Biome tags
	public static final TagKey<Biome> IS_BATTLEFIELD = createBiomeTag("is_battlefield");
	public static final TagKey<Biome> IS_TILTROS = createBiomeTag("is_tiltros");
	public static final TagKey<Biome> IS_TILTROS_WASTES = createBiomeTag("is_tiltros_wastes");
	public static final TagKey<Biome> IS_STARLIGHT_PLAINS = createBiomeTag("is_starlight_plains");
	public static final TagKey<Biome> IS_DEADMANS_DESERT = createBiomeTag("is_deadmans_desert");

	// Structure Tags
	public static final TagKey<Biome> HAS_ABANDONED_FACTORY = createStructureTag("abandoned_factory");
	public static final TagKey<Biome> HAS_PITFALL_TRAP = createStructureTag("pitfall_trap");
	public static final TagKey<Biome> HAS_BEAR_TRAP = createStructureTag("bear_trap");
	public static final TagKey<Biome> HAS_LANDMINE_TRAP = createStructureTag("landmine_trap");
	public static final TagKey<Biome> HAS_UNDERGROUND_BUNKER = createStructureTag("underground_bunker");
	public static final TagKey<Biome> HAS_CLOUD_ISLAND = createStructureTag("cloud_island");
	public static final TagKey<Biome> HAS_CAMPSITE = createStructureTag("campsite");
	public static final TagKey<Biome> HAS_WATER_TOWER = createStructureTag("water_tower");
	public static final TagKey<Biome> HAS_HANS_HUT = createStructureTag("hans_hut");
	public static final TagKey<Biome> HAS_DESTROYED_HOUSE = createStructureTag("destroyed_house");
	public static final TagKey<Biome> HAS_BATTLEFIELD_CAMP = createStructureTag("battlefield_camp");
	public static final TagKey<Biome> HAS_GRAVEYARD = createStructureTag("graveyard");
	public static final TagKey<Biome> HAS_BATTLEFIELD_TOWN = createStructureTag("battlefield_town");
	public static final TagKey<Biome> HAS_CELESTIAL_ASTEROID = createStructureTag("celestial_asteroid");
	public static final TagKey<Biome> HAS_BIODOME = createStructureTag("biodome");
	public static final TagKey<Biome> HAS_SPACE_OBSERVATORY = createStructureTag("space_observatory");

	/**
	 * Helper method for creating a biome tag for containing structures.
	 *
	 * @param tag a string to be used for the tag
	 */
	private static TagKey<Biome> createStructureTag(String tag) {
		return createBiomeTagInternal(ImmersiveWeapons.MOD_ID + ":has_structure/" + tag);
	}

	/**
	 * Helper method for creating a biome tag for containing biomes.
	 *
	 * @param tag a string to be used for the tag
	 */
	private static TagKey<Biome> createBiomeTag(String tag) {
		return createBiomeTagInternal(ImmersiveWeapons.MOD_ID + ":" + tag);
	}

	private static TagKey<Biome> createBiomeTagInternal(String pName) {
		return TagKey.create(Registries.BIOME, new ResourceLocation(pName));
	}
}