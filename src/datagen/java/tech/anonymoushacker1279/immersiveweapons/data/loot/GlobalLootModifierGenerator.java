package tech.anonymoushacker1279.immersiveweapons.data.loot;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.*;

public class GlobalLootModifierGenerator extends GlobalLootModifierProvider {

	public GlobalLootModifierGenerator(PackOutput output) {
		super(output, ImmersiveWeapons.MOD_ID);
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
						BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE
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
				"undead"));

		add("log_shards", new LogShardsLootModifierHandler(
				matchToolCondition(ItemTags.PICKAXES),
				ItemTags.LOGS, 2, 5,
				ItemRegistry.WOODEN_SHARD.get().getDefaultInstance()));

		add("molten_tool_smelting", new ToolSmeltingModifierHandler(
				simpleEntityCondition(EntityType.PLAYER),
				IWItemTagGroups.MOLTEN_TOOLS
		));
	}

	/**
	 * Create a loot condition that has a single loot table as criteria.
	 *
	 * @param lootTable the loot table to use
	 * @return the loot item condition
	 */
	private LootItemCondition[] singleLootTableCondition(ResourceLocation lootTable) {
		return new LootItemCondition[]{LootTableIdCondition.builder(lootTable).build()};
	}

	/**
	 * Create a loot condition that has multiple loot tables as criteria.
	 *
	 * @param lootTables the loot tables to use
	 * @return the loot item condition
	 */
	private LootItemCondition[] multipleLootTablesCondition(ResourceLocation... lootTables) {
		LootItemCondition[] conditions = new LootItemCondition[lootTables.length];
		for (int i = 0; i < lootTables.length; i++) {
			conditions[i] = LootTableIdCondition.builder(lootTables[i]).build();
		}
		return conditions;
	}

	/**
	 * Create a loot condition that only has a random chance and looting multiplier as criteria.
	 *
	 * @param chance            the chance of the item dropping
	 * @param lootingMultiplier the looting multiplier
	 */
	private LootItemCondition[] simpleDropCondition(float chance, float lootingMultiplier) {
		return new LootItemCondition[]{
				LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(chance, lootingMultiplier)
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
						.and(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(chance, lootingMultiplier))
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
}