package tech.anonymoushacker1279.immersiveweapons.data.loot;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class LootTableLocations {
	public static final ResourceKey<LootTable> ABANDONED_FACTORY = register("chests/abandoned_factory");
	public static final ResourceKey<LootTable> BATTLEFIELD_CAMP = register("chests/battlefield_camp");
	public static final ResourceKey<LootTable> CAMPSITE = register("chests/campsite");
	public static final ResourceKey<LootTable> UNDERGROUND_BUNKER = register("chests/underground_bunker");
	public static final ResourceKey<LootTable> BATTLEFIELD_VILLAGE_MEDIC_STATION = register("chests/village/battlefield/medic_station");

	public static final ResourceKey<LootTable> HANS_HUT = register("chests/hans_hut");
	public static final ResourceKey<LootTable> HANS_HUT_CASK = register("chests/hans_hut_cask");

	public static final ResourceKey<LootTable> BIODOME_MEDICINE_BARREL = register("chests/biodome/medicine_barrel");
	public static final ResourceKey<LootTable> BIODOME_CHEST = register("chests/biodome/chest");

	public static final ResourceKey<LootTable> CHAMPION_TOWER_TIER_1 = register("chests/champion_tower/tier_1");
	public static final ResourceKey<LootTable> CHAMPION_TOWER_TIER_2 = register("chests/champion_tower/tier_2");

	private static ResourceKey<LootTable> register(String pId) {
		return ResourceKey.create(Registries.LOOT_TABLE, new ResourceLocation(ImmersiveWeapons.MOD_ID, pId));
	}
}