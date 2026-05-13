package tech.anonymoushacker1279.immersiveweapons.data.loot;

import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
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
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.LogShardsLootModifierHandler;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.SimpleChestModifierHandler;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.SimpleDropModifierHandler;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.ToolSmeltingModifierHandler;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class GlobalLootModifierGenerator extends GlobalLootModifierProvider {

	private final Provider registries;
	private final HolderGetter<EntityType<?>> entityTypeGetter;
	private final HolderGetter<Item> itemGetter;

	public GlobalLootModifierGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID);

		Provider provider;
		try {
			provider = lookupProvider.get();
			entityTypeGetter = provider.lookupOrThrow(Registries.ENTITY_TYPE);
			itemGetter = provider.lookupOrThrow(Registries.ITEM);
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
				new ItemStackTemplate(ItemRegistry.ANCIENT_SCROLL.get())));

		add("aurora_bow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.END_CITY_TREASURE),
				1000,
				1, 1, 0.3f,
				new ItemStackTemplate(ItemRegistry.AURORA_BOW.get()), 30, true));

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
				new ItemStackTemplate(ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get())));

		add("depth_charm", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.ANCIENT_CITY),
				1, 1, 0.2f,
				new ItemStackTemplate(ItemRegistry.DEPTH_CHARM.get())));

		add("dragons_breath_bow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.NETHER_BRIDGE),
				1000,
				1, 1, 0.2f,
				new ItemStackTemplate(ItemRegistry.AURORA_BOW.get()), 25, true));

		add("ice_bow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.IGLOO_CHEST),
				1000,
				1, 1, 0.25f,
				new ItemStackTemplate(ItemRegistry.ICE_BOW.get()), 20, true));

		add("insomnia_amulet", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.PHANTOM, 0.05f, 0.02f),
				new ItemStackTemplate(ItemRegistry.INSOMNIA_AMULET.get())));

		add("medal_of_adequacy", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.ENDER_DRAGON),
				new ItemStackTemplate(ItemRegistry.MEDAL_OF_ADEQUACY.get())));

		add("night_vision_goggles", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.ENDERMAN, 0.01f, 0.02f),
				new ItemStackTemplate(ItemRegistry.NIGHT_VISION_GOGGLES.get())));

		add("warden_heart", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.WARDEN),
				new ItemStackTemplate(ItemRegistry.WARDEN_HEART.get())));

		add("copper_ring", new SimpleDropModifierHandler(
				simpleDropCondition(0.05f, 0.02f),
				new ItemStackTemplate(ItemRegistry.COPPER_RING.get()),
				Optional.of(EntityTypeTags.UNDEAD)));

		add("log_shards", new LogShardsLootModifierHandler(
				matchToolCondition(ItemTags.PICKAXES),
				1000,
				ItemTags.LOGS, 2, 5,
				new ItemStackTemplate(ItemRegistry.WOODEN_SHARD.get())));

		add("molten_tool_smelting", new ToolSmeltingModifierHandler(
				simpleEntityCondition(EntityType.PLAYER),
				1000,
				IWItemTagGroups.MOLTEN_TOOLS
		));

		add("music_disc_starlight_plains_theme_1", new SimpleChestModifierHandler(
				inBiomeDungeonCondition(IWBiomes.STARLIGHT_PLAINS),
				1, 1, 0.2f,
				new ItemStackTemplate(ItemRegistry.MUSIC_DISC_STARLIGHT_PLAINS_THEME_1.get())));

		add("music_disc_starlight_plains_theme_2", new SimpleChestModifierHandler(
				inBiomeDungeonCondition(IWBiomes.STARLIGHT_PLAINS),
				1, 1, 0.2f,
				new ItemStackTemplate(ItemRegistry.MUSIC_DISC_STARLIGHT_PLAINS_THEME_2.get())));

		add("music_disc_tiltros_wastes", new SimpleChestModifierHandler(
				inBiomeDungeonCondition(IWBiomes.TILTROS_WASTES),
				1, 1, 0.4f,
				new ItemStackTemplate(ItemRegistry.MUSIC_DISC_TILTROS_WASTES_THEME.get())));

		add("music_disc_deadmans_desert_theme_1", new SimpleChestModifierHandler(
				inBiomeDungeonCondition(IWBiomes.DEADMANS_DESERT),
				1, 1, 0.2f,
				new ItemStackTemplate(ItemRegistry.MUSIC_DISC_DEADMANS_DESERT_THEME_1.get())));

		add("music_disc_deadmans_desert_theme_2", new SimpleChestModifierHandler(
				inBiomeDungeonCondition(IWBiomes.DEADMANS_DESERT),
				1, 1, 0.2f,
				new ItemStackTemplate(ItemRegistry.MUSIC_DISC_DEADMANS_DESERT_THEME_2.get())));

		add("golden_ring", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				1, 1, 0.05f,
				new ItemStackTemplate(ItemRegistry.GOLDEN_RING.get())));

		add("cobalt_ring", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				1, 1, 0.06f,
				new ItemStackTemplate(ItemRegistry.COBALT_RING.get())));

		add("iron_ring", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				1, 1, 0.07f,
				new ItemStackTemplate(ItemRegistry.IRON_RING.get())));

		add("iron_arrow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				2, 6, 0.25f,
				new ItemStackTemplate(ItemRegistry.IRON_ARROW.get())));

		add("cobalt_arrow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				2, 4, 0.2f,
				new ItemStackTemplate(ItemRegistry.COBALT_ARROW.get())));

		add("agility_bracelet", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD),
				1, 1, 0.1f,
				new ItemStackTemplate(ItemRegistry.AGILITY_BRACELET.get())));

		add("azul_keystone", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS),
				1, 1, 0.2f,
				new ItemStackTemplate(ItemRegistry.AZUL_KEYSTONE.get())));

		add("diamond_arrow", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS),
				4, 10, 0.25f,
				new ItemStackTemplate(ItemRegistry.DIAMOND_ARROW.get())));

		add("kill_counter", new SimpleChestModifierHandler(
				singleLootTableCondition(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS),
				1, 1, 0.1f,
				new ItemStackTemplate(ItemRegistry.KILL_COUNTER.get())));

		add("ender_essence_enderman", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.ENDERMAN, 0.005f, 0.001f),
				new ItemStackTemplate(ItemRegistry.ENDER_ESSENCE.get())));

		add("ender_essence_shulker", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.SHULKER, 0.03f, 0.005f),
				1, 2,
				new ItemStackTemplate(ItemRegistry.ENDER_ESSENCE.get())));

		add("ender_essence_ender_dragon", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.ENDER_DRAGON),
				8, 12,
				new ItemStackTemplate(ItemRegistry.ENDER_ESSENCE.get())));

		add("ventus_shard", new SimpleDropModifierHandler(
				simpleEntityDropCondition(EntityType.BREEZE, 0.05f, 0.01f),
				1, 3,
				new ItemStackTemplate(ItemRegistry.VENTUS_SHARD.get())));
	}

	/// Create a loot condition that has a single loot table as criteria.
	///
	/// @param lootTable the loot table to use
	/// @return the loot item condition
	private LootItemCondition[] singleLootTableCondition(ResourceKey<LootTable> lootTable) {
		return new LootItemCondition[]{LootTableIdCondition.builder(lootTable.identifier()).build()};
	}

	/// Create a loot condition that has multiple loot tables as criteria.
	///
	/// @param lootTables the loot tables to use
	/// @return the loot item condition
	@SafeVarargs
	private LootItemCondition[] multipleLootTablesCondition(ResourceKey<LootTable>... lootTables) {
		LootItemCondition.Builder[] builders = new LootItemCondition.Builder[lootTables.length];

		for (int i = 0; i < lootTables.length; i++) {
			builders[i] = LootTableIdCondition.builder(lootTables[i].identifier());
		}

		return new LootItemCondition[]{AnyOfCondition.anyOf(builders).build()};
	}

	/// Create a loot condition that only has a random chance and looting multiplier as criteria.
	///
	/// @param chance            the chance of the item dropping
	/// @param lootingMultiplier the looting multiplier
	private LootItemCondition[] simpleDropCondition(float chance, float lootingMultiplier) {
		return new LootItemCondition[]{
				LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, chance, lootingMultiplier)
						.and(LootItemKilledByPlayerCondition.killedByPlayer())
						.build()};
	}

	/// Create a loot condition that has a single entity type and being killed by a player as criteria.
	///
	/// @param entityType the entity type to use
	/// @return the loot item condition
	private LootItemCondition[] simpleEntityDropCondition(EntityType<?> entityType) {
		return new LootItemCondition[]{
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS,
								EntityPredicate.Builder.entity()
										.of(entityTypeGetter, entityType)
										.build())
						.and(LootItemKilledByPlayerCondition.killedByPlayer())
						.build()};
	}

	/// Similar to [#simpleEntityDropCondition(EntityType)] but with a random chance and looting multiplier.
	///
	/// @param entityType        the entity type to use
	/// @param chance            the chance of the item dropping
	/// @param lootingMultiplier the looting multiplier
	/// @return the loot item condition
	private LootItemCondition[] simpleEntityDropCondition(EntityType<?> entityType, float chance, float lootingMultiplier) {
		return new LootItemCondition[]{
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS,
								EntityPredicate.Builder.entity()
										.of(entityTypeGetter, entityType)
										.build())
						.and(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, chance, lootingMultiplier))
						.and(LootItemKilledByPlayerCondition.killedByPlayer())
						.build()};
	}

	/// Create a loot condition that has a single entity type as criteria.
	///
	/// @param entityType the entity type to use
	/// @return the loot item condition
	private LootItemCondition[] simpleEntityCondition(EntityType<?> entityType) {
		return new LootItemCondition[]{
				LootItemEntityPropertyCondition.hasProperties(EntityTarget.THIS,
						EntityPredicate.Builder.entity()
								.of(entityTypeGetter, entityType)
								.build())
						.build()};
	}

	private LootItemCondition[] matchToolCondition(TagKey<Item> tagKey) {
		return new LootItemCondition[]{
				MatchTool.toolMatches(ItemPredicate.Builder.item().of(itemGetter, tagKey)).build()};
	}

	/// Create a loot condition that applies to [BuiltInLootTables#SIMPLE_DUNGEON] loot tables in a given biome.
	///
	/// @param biome the biome to use
	/// @return the loot item condition
	private LootItemCondition[] inBiomeDungeonCondition(ResourceKey<Biome> biome) {
		HolderGetter<Biome> holderGetter = registries.lookupOrThrow(Registries.BIOME);

		return new LootItemCondition[]{LootTableIdCondition.builder(BuiltInLootTables.SIMPLE_DUNGEON.identifier())
				.and(LocationCheck.checkLocation(LocationPredicate.Builder.inBiome(holderGetter.getOrThrow(biome))))
				.build()};
	}
}