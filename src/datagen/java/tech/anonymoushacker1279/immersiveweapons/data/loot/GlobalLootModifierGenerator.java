package tech.anonymoushacker1279.immersiveweapons.data.loot;

import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GlobalLootModifierGenerator extends GlobalLootModifierProvider {

	private final Provider registries;

	public GlobalLootModifierGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID);

		Provider provider;
		try {
			provider = lookupProvider.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}

		registries = provider;
	}

	@Override
	protected void start() {
		add("ancient_scroll", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.STRONGHOLD_LIBRARY),
				1, 1, 0.3f,
				ItemRegistry.ANCIENT_SCROLL.get().getDefaultInstance()));

		add("aurora_bow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.END_CITY_TREASURE),
				1, 1, 0.3f,
				ItemRegistry.AURORA_BOW.get().getDefaultInstance(), 30, true));

		add("azul_keystone_fragment", new SimpleChestModifierHandler(
				multipleLootTablesCondition(
						BuiltInLootTables.SIMPLE_DUNGEON,
						BuiltInLootTables.ABANDONED_MINESHAFT,
						BuiltInLootTables.BURIED_TREASURE,
						BuiltInLootTables.JUNGLE_TEMPLE,
						BuiltInLootTables.SHIPWRECK_TREASURE,
						BuiltInLootTables.STRONGHOLD_CORRIDOR,
						BuiltInLootTables.STRONGHOLD_CROSSING,
						BuiltInLootTables.STRONGHOLD_LIBRARY,
						BuiltInLootTables.UNDERWATER_RUIN_BIG,
						BuiltInLootTables.UNDERWATER_RUIN_SMALL,
						BuiltInLootTables.WOODLAND_MANSION,
						BuiltInLootTables.ANCIENT_CITY,
						BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE,
						BuiltInLootTables.TRIAL_CHAMBERS_SUPPLY,
						BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY,
						BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY
				),
				1, 2, 0.25f,
				ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get().getDefaultInstance()));

		add("depth_charm", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.ANCIENT_CITY),
				1, 1, 0.2f,
				ItemRegistry.DEPTH_CHARM.get().getDefaultInstance()));

		add("dragons_breath_bow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.NETHER_BRIDGE),
				1, 1, 0.2f,
				ItemRegistry.AURORA_BOW.get().getDefaultInstance(), 25, true));

		add("ice_bow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.IGLOO_CHEST),
				1, 1, 0.25f,
				ItemRegistry.ICE_BOW.get().getDefaultInstance(), 20, true));

		add("insomnia_amulet", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.PHANTOM, 0.05f, 0.02f),
				ItemRegistry.INSOMNIA_AMULET.get().getDefaultInstance()));

		add("medal_of_adequacy", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.ENDER_DRAGON),
				ItemRegistry.MEDAL_OF_ADEQUACY.get().getDefaultInstance()));

		add("night_vision_goggles", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.ENDERMAN, 0.01f, 0.02f),
				ItemRegistry.NIGHT_VISION_GOGGLES.get().getDefaultInstance()));

		add("warden_heart", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.WARDEN),
				ItemRegistry.WARDEN_HEART.get().getDefaultInstance()));

		add("copper_ring", new SimpleDropModifierHandler(
				simpleDropCondition(0.05f, 0.02f),
				ItemRegistry.COPPER_RING.get().getDefaultInstance(),
				Optional.of(EntityTypeTags.UNDEAD)));

		add("log_shards", new LogShardsLootModifierHandler(
				matchToolCondition(ItemTags.PICKAXES),
				ItemTags.LOGS, 2, 5,
				ItemRegistry.WOODEN_SHARD.get().getDefaultInstance()));

		add("molten_tool_smelting", new ToolSmeltingModifierHandler(
				simpleEntityCondition(EntityType.PLAYER),
				IWItemTagGroups.MOLTEN_TOOLS
		));

		add("music_disc_starlight_plains_theme_1", new SimpleChestModifierHandler(
				inBiomeDungeonCondition(IWBiomes.STARLIGHT_PLAINS),
				1, 1, 0.2f,
				ItemRegistry.MUSIC_DISC_STARLIGHT_PLAINS_THEME_1.get().getDefaultInstance()));

		add("music_disc_starlight_plains_theme_2", new SimpleChestModifierHandler(
				inBiomeDungeonCondition(IWBiomes.STARLIGHT_PLAINS),
				1, 1, 0.2f,
				ItemRegistry.MUSIC_DISC_STARLIGHT_PLAINS_THEME_2.get().getDefaultInstance()));

		add("music_disc_tiltros_wastes", new SimpleChestModifierHandler(
				inBiomeDungeonCondition(IWBiomes.TILTROS_WASTES),
				1, 1, 0.4f,
				ItemRegistry.MUSIC_DISC_TILTROS_WASTES_THEME.get().getDefaultInstance()));

		add("music_disc_deadmans_desert_theme_1", new SimpleChestModifierHandler(
				inBiomeDungeonCondition(IWBiomes.DEADMANS_DESERT),
				1, 1, 0.2f,
				ItemRegistry.MUSIC_DISC_DEADMANS_DESERT_THEME_1.get().getDefaultInstance()));

		add("music_disc_deadmans_desert_theme_2", new SimpleChestModifierHandler(
				inBiomeDungeonCondition(IWBiomes.DEADMANS_DESERT),
				1, 1, 0.2f,
				ItemRegistry.MUSIC_DISC_DEADMANS_DESERT_THEME_2.get().getDefaultInstance()));

		add("golden_ring", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				1, 1, 0.05f,
				ItemRegistry.GOLDEN_RING.get().getDefaultInstance()));

		add("cobalt_ring", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				1, 1, 0.06f,
				ItemRegistry.COBALT_RING.get().getDefaultInstance()));

		add("iron_ring", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				1, 1, 0.07f,
				ItemRegistry.IRON_RING.get().getDefaultInstance()));

		add("iron_arrow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				2, 6, 0.25f,
				ItemRegistry.IRON_ARROW.get().getDefaultInstance()));

		add("cobalt_arrow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				2, 4, 0.2f,
				ItemRegistry.COBALT_ARROW.get().getDefaultInstance()));

		add("agility_bracelet", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				1, 1, 0.1f,
				ItemRegistry.AGILITY_BRACELET.get().getDefaultInstance()));

		add("azul_keystone", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS),
				1, 1, 0.2f,
				ItemRegistry.AZUL_KEYSTONE.get().getDefaultInstance()));

		add("diamond_arrow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS),
				4, 10, 0.25f,
				ItemRegistry.DIAMOND_ARROW.get().getDefaultInstance()));

		add("kill_counter", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS),
				1, 1, 0.1f,
				ItemRegistry.KILL_COUNTER.get().getDefaultInstance()));

		add("ender_essence_enderman", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.ENDERMAN, 0.005f, 0.001f),
				ItemRegistry.ENDER_ESSENCE.get().getDefaultInstance()));

		add("ender_essence_shulker", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.SHULKER, 0.03f, 0.005f),
				ItemRegistry.ENDER_ESSENCE.get().getDefaultInstance(),
				1, 2));

		add("ender_essence_ender_dragon", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.ENDER_DRAGON),
				ItemRegistry.ENDER_ESSENCE.get().getDefaultInstance(),
				8, 12));

		add("ventus_shard", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.BREEZE, 0.05f, 0.01f),
				1, 3,
				ItemRegistry.VENTUS_SHARD.get().getDefaultInstance()));
	}

	/**
	 * Create a loot condition that has a single loot table as criteria.
	 *
	 * @param lootTable the loot table to use
	 * @return the loot item condition
	 */
	private LootItemCondition[] singleLootTableCondition(ResourceKey<LootTable> lootTable) {
		return new LootItemCondition[]{LootTableIdCondition.builder(lootTable.location()).build()};
	}

	/**
	 * Create a loot condition that has multiple loot tables as criteria.
	 *
	 * @param lootTables the loot tables to use
	 * @return the loot item condition
	 */
	@SafeVarargs
	private LootItemCondition[] multipleLootTablesCondition(ResourceKey<LootTable>... lootTables) {
		LootItemCondition.Builder[] builders = new LootItemCondition.Builder[lootTables.length];

		for (int i = 0; i < lootTables.length; i++) {
			builders[i] = LootTableIdCondition.builder(lootTables[i].location());
		}

		return new LootItemCondition[]{AnyOfCondition.anyOf(builders).build()};
	}

	/**
	 * Create a loot condition that only has a random chance and looting multiplier as criteria.
	 *
	 * @param chance            the chance of the item dropping
	 * @param lootingMultiplier the looting multiplier
	 */
	private LootItemCondition[] simpleDropCondition(float chance, float lootingMultiplier) {
		return new LootItemCondition[]{
				LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, chance, lootingMultiplier)
						.and(LootItemKilledByPlayerCondition.killedByPlayer())
						.build()};
	}

	/**
	 * Create a loot condition that has a single entity type and being killed by a player as criteria.
	 *
	 * @param entityType the entity type to use
	 * @return the loot item condition
	 */
	private LootItemCondition[] simpleEntityDropCondition(EntityType<?> entityType) {
		return new LootItemCondition[]{
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS,
								EntityPredicate.Builder.entity()
										.of(entityType)
										.build())
						.and(LootItemKilledByPlayerCondition.killedByPlayer())
						.build()};
	}

	/**
	 * Similar to {@link #simpleEntityDropCondition(EntityType)} but with a random chance and looting multiplier.
	 *
	 * @param entityType        the entity type to use
	 * @param chance            the chance of the item dropping
	 * @param lootingMultiplier the looting multiplier
	 * @return the loot item condition
	 */
	private LootItemCondition[] simpleEntityDropCondition(EntityType<?> entityType, float chance, float lootingMultiplier) {
		return new LootItemCondition[]{
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS,
								EntityPredicate.Builder.entity()
										.of(entityType)
										.build())
						.and(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, chance, lootingMultiplier))
						.and(LootItemKilledByPlayerCondition.killedByPlayer())
						.build()};
	}

	/**
	 * Create a loot condition that has a single entity type as criteria.
	 *
	 * @param entityType the entity type to use
	 * @return the loot item condition
	 */
	private LootItemCondition[] simpleEntityCondition(EntityType<?> entityType) {
		return new LootItemCondition[]{
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS,
						EntityPredicate.Builder.entity()
								.of(entityType)
								.build())
						.build()};
	}

	private LootItemCondition[] matchToolCondition(TagKey<Item> tagKey) {
		return new LootItemCondition[]{
				MatchTool.toolMatches(ItemPredicate.Builder.item().of(tagKey)).build()};
	}

	/**
	 * Create a loot condition that applies to {@link BuiltInLootTables#SIMPLE_DUNGEON} loot tables in a given biome.
	 *
	 * @param biome the biome to use
	 * @return the loot item condition
	 */
	private LootItemCondition[] inBiomeDungeonCondition(ResourceKey<Biome> biome) {
		HolderGetter<Biome> holderGetter = registries.lookupOrThrow(Registries.BIOME);

		return new LootItemCondition[]{LootTableIdCondition.builder(BuiltInLootTables.SIMPLE_DUNGEON.location())
				.and(LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(holderGetter.getOrThrow(biome))))
				.build()};
	}
}