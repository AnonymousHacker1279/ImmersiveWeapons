package tech.anonymoushacker1279.immersiveweapons.data.loot;

import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

import java.util.Collections;
import java.util.Set;

public class LootTableLocations {

	private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
	private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);

	public static final ResourceLocation ABANDONED_FACTORY = register("chests/abandoned_factory");
	public static final ResourceLocation BATTLEFIELD_CAMP = register("chests/battlefield_camp");
	public static final ResourceLocation CAMPSITE = register("chests/campsite");
	public static final ResourceLocation UNDERGROUND_BUNKER = register("chests/underground_bunker");
	public static final ResourceLocation BATTLEFIELD_VILLAGE_MEDIC_STATION = register("chests/village/battlefield/medic_station");

	public static final ResourceLocation HANS_HUT = register("chests/hans_hut");
	public static final ResourceLocation HANS_HUT_CASK = register("chests/hans_hut_cask");

	public static final ResourceLocation BIODOME_MEDICINE_BARREL = register("chests/biodome/medicine_barrel");
	public static final ResourceLocation BIODOME_CHEST = register("chests/biodome/chest");

	private static ResourceLocation register(String pId) {
		return register(new ResourceLocation(ImmersiveWeapons.MOD_ID, pId));
	}

	private static ResourceLocation register(ResourceLocation pId) {
		if (LOCATIONS.add(pId)) {
			return pId;
		} else {
			throw new IllegalArgumentException(pId + " is already a registered built-in loot table");
		}
	}

	public static Set<ResourceLocation> all() {
		return IMMUTABLE_LOCATIONS;
	}
}