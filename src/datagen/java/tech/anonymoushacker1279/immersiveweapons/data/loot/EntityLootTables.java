package tech.anonymoushacker1279.immersiveweapons.data.loot;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.predicates.NbtPredicate;
import net.minecraft.advancements.predicates.entity.EntityFlagsPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction.Target;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.neoforged.neoforge.registries.DeferredHolder;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.PotionRegistry;
import tech.anonymoushacker1279.immersiveweapons.world.level.loot.number_providers.EntityKillersValue;

import java.util.stream.Stream;

public class EntityLootTables extends EntityLootSubProvider {

	protected static final EntityPredicate.Builder ENTITY_ON_FIRE = EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true));

	private final HolderGetter<EntityType<?>> entityTypeGetter;

	protected EntityLootTables(LootTableSubProvider.Context output) {
		super(FeatureFlags.REGISTRY.allFlags(), output);

		entityTypeGetter = entityTypes;
	}

	@Override
	protected Stream<EntityType<?>> getKnownEntityTypes() {
		return EntityRegistry.ENTITY_TYPES.getEntries().stream().map(DeferredHolder::get);
	}

	@Override
	public void generate() {
		add(EntityRegistry.ROCK_SPIDER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("string")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.STRING)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))))
				.withPool(LootPool.lootPool()
						.name("stone")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.STONE)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 1)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))))
				.withPool(LootPool.lootPool()
						.name("spider_eye")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.SPIDER_EYE)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(-1, 1)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));

		add(EntityRegistry.LAVA_REVENANT_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("sulfur")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.SULFUR_DUST.get())
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 8)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(2.0F, 4.0F)))))
				.withPool(LootPool.lootPool()
						.name("coal")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.COAL)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(1.0F, 2.0F)))))
				.withPool(LootPool.lootPool()
						.name("charcoal")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.CHARCOAL)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(1.0F, 2.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));

		add(EntityRegistry.DYING_SOLDIER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("blackpowder")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.BLACKPOWDER.get())
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 6)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(1.0F, 2.0F)))))
				.withPool(LootPool.lootPool()
						.name("iron_musket_ball")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.IRON_MUSKET_BALL.get())
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 4)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("medal_of_dishonor")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.MEDAL_OF_DISHONOR.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.010F, 0.025F)))
				.withPool(LootPool.lootPool()
						.name("foods")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.CHOCOLATE_BAR.get())
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.CARROT)
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.POTATO)
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.apply(SmeltItemFunction.smelted()
								.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.035F, 0.02F))));

		add(EntityRegistry.THE_COMMANDER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("commander_pedestal")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(BlockItemRegistry.COMMANDER_PEDESTAL.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("gunpowder")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.GUNPOWDER))
						.apply(SetItemCountFunction.setCount(ContextIntProviders.between(12, 24)))
						.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(3.0F, 8.0F))))
				.withPool(LootPool.lootPool()
						.name("cannonball")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.CANNONBALL.get())
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(10, 20)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(1.0F, 2.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("medal_of_dishonor")
						.setRolls(EntityKillersValue.create())
						.add(LootItem.lootTableItem(ItemRegistry.MEDAL_OF_DISHONOR.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));

		CompoundTag tag = new CompoundTag();
		tag.putBoolean("isBerserk", true);
		add(EntityRegistry.WANDERING_WARRIOR_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("leather")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.LEATHER)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 3)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 2.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("berserkers_amulet")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.BERSERKERS_AMULET.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.1F, 0.05F))
						.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
								// Check for isBerserk being true in the NBT
								EntityPredicate.Builder.entity().nbt(
										new NbtPredicate(tag)
								).build()))));

		add(EntityRegistry.HANS_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("iron_sword")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.IRON_SWORD)
								.apply(SetNameFunction.setName(Component.translatable("loot.immersiveweapons.entity.hans.iron_sword")
												.withStyle(ChatFormatting.DARK_PURPLE),
										Target.ITEM_NAME)))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.035F, 0.01F)))
				.withPool(LootPool.lootPool()
						.name("kill_counter")
						.setRolls(EntityKillersValue.create())
						.add(LootItem.lootTableItem(ItemRegistry.KILL_COUNTER.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.025F, 0.02F)))
				.withPool(LootPool.lootPool()
						.name("hans_blessing")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.HANS_BLESSING.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.05F, 0.02F))));

		add(EntityRegistry.MINUTEMAN_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("blackpowder")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.BLACKPOWDER.get())
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 6)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(1.0F, 2.0F)))))
				.withPool(LootPool.lootPool()
						.name("copper_musket_ball")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.COPPER_MUSKET_BALL.get())
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 4)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("medal_of_honor")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.MEDAL_OF_HONOR.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.010F, 0.025F)))
				.withPool(LootPool.lootPool()
						.name("foods")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.CHOCOLATE_BAR.get())
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.CARROT)
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.POTATO)
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.apply(SmeltItemFunction.smelted()
								.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.045F, 0.02F))));

		add(EntityRegistry.FIELD_MEDIC_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("used_syringe")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.USED_SYRINGE.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.015F, 0.03F))));

		add(EntityRegistry.CELESTIAL_TOWER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("celestial_fragment")
						.setRolls(EntityKillersValue.create())
						.add(LootItem.lootTableItem(ItemRegistry.CELESTIAL_FRAGMENT.get())
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 2.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("celestial_spirit")
						.setRolls(EntityKillersValue.create())
						.add(LootItem.lootTableItem(ItemRegistry.CELESTIAL_SPIRIT.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.05F, 0.02F))));

		add(EntityRegistry.STORM_CREEPER_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("main")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.GUNPOWDER)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(ItemRegistry.SULFUR_DUST.get())
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 4)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 2.0F)))))
				.withPool(LootPool.lootPool()
						.name("music_discs")
						.add(TagEntry.expandTag(items.getOrThrow(ItemTags.CREEPER_DROP_MUSIC_DISCS)))
						.when(LootItemEntityPropertyCondition.hasProperties(EntityTarget.ATTACKER,
								EntityPredicate.Builder.entity().of(entityTypeGetter, EntityTypeTags.SKELETONS)))));

		add(EntityRegistry.EVIL_EYE_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("main")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.BROKEN_LENS.get())
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 1)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.ENDER_EYE)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 1)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.add(LootItem.lootTableItem(Items.ENDER_PEARL)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 1)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));

		add(EntityRegistry.SUPER_HANS_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("hans_blessing")
						.setRolls(EntityKillersValue.create())
						.add(LootItem.lootTableItem(ItemRegistry.HANS_BLESSING.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("melee_gloves")
						.setRolls(EntityKillersValue.create())
						.add(LootItem.lootTableItem(ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get())
								.setWeight(1))
						.add(LootItem.lootTableItem(ItemRegistry.IRON_FIST.get())
								.setWeight(1))
						.add(LootItem.lootTableItem(ItemRegistry.GLOVE_OF_RAPID_SWINGING.get())
								.setWeight(1))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("other_items")
						.setRolls(EntityKillersValue.create())
						.add(LootItem.lootTableItem(ItemRegistry.HOLY_MANTLE.get())
								.setWeight(1))
						.add(LootItem.lootTableItem(ItemRegistry.VENSTRAL_JAR.get())
								.setWeight(1))
						.add(LootItem.lootTableItem(ItemRegistry.DEATH_GEM_RING.get())
								.setWeight(1))
						.add(LootItem.lootTableItem(ItemRegistry.SUPER_BLANKET_CAPE.get())
								.setWeight(1))
						.add(LootItem.lootTableItem(ItemRegistry.HANSIUM_INGOT.get())
								.setWeight(1))
						.when(LootItemKilledByPlayerCondition.killedByPlayer()))
				.withPool(LootPool.lootPool()
						.name("kill_counter")
						.setRolls(EntityKillersValue.create())
						.add(LootItem.lootTableItem(ItemRegistry.KILL_COUNTER.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.enchantments, 0.1F, 0.02F)))
				.withPool(LootPool.lootPool()
						.name("healing_equipment")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(Items.POTION)
								.setWeight(35)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 0.33F)))
								.apply(SetNameFunction.setName(Component.translatable("loot.immersiveweapons.entity.super_hans.super_healing_potion")
												.withStyle(ChatFormatting.GOLD),
										Target.ITEM_NAME))
								.apply(SetPotionFunction.setPotion(PotionRegistry.SUPER_HEALING_POTION)))
						.add(LootItem.lootTableItem(Items.POTION)
								.setWeight(35)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 0.33F)))
								.apply(SetNameFunction.setName(Component.translatable("loot.immersiveweapons.entity.super_hans.super_regeneration_potion")
												.withStyle(ChatFormatting.GOLD),
										Target.ITEM_NAME))
								.apply(SetPotionFunction.setPotion(PotionRegistry.SUPER_REGENERATION_POTION)))
						.add(LootItem.lootTableItem(Items.POTION)
								.setWeight(15)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 0.33F)))
								.apply(SetNameFunction.setName(Component.translatable("loot.immersiveweapons.entity.super_hans.ultra_healing_potion")
												.withStyle(ChatFormatting.GOLD),
										Target.ITEM_NAME))
								.apply(SetPotionFunction.setPotion(PotionRegistry.ULTRA_HEALING_POTION)))
						.add(LootItem.lootTableItem(Items.POTION)
								.setWeight(15)
								.apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 0.33F)))
								.apply(SetNameFunction.setName(Component.translatable("loot.immersiveweapons.entity.super_hans.ultra_regeneration_potion")
												.withStyle(ChatFormatting.GOLD),
										Target.ITEM_NAME))
								.apply(SetPotionFunction.setPotion(PotionRegistry.ULTRA_REGENERATION_POTION)))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
				));

		add(EntityRegistry.STARMITE_ENTITY.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("starstorm_shard")
						.setRolls(ContextIntProviders.exactly(1))
						.add(LootItem.lootTableItem(ItemRegistry.STARSTORM_SHARD.get())
								.apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 0.25F))))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())));

		add(EntityRegistry.MOOGLOW_ENTITY.get(), LootTable.lootTable()
				.withPool(
						LootPool.lootPool()
								.setRolls(ContextIntProviders.exactly(1))
								.add(
										LootItem.lootTableItem(Items.LEATHER)
												.apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2)))
												.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
								)
				)
				.withPool(
						LootPool.lootPool()
								.setRolls(ContextIntProviders.exactly(1))
								.add(
										LootItem.lootTableItem(Items.BEEF)
												.apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 3)))
												.apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
												.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.enchantments, ContextFloatProviders.between(0.0F, 1.0F)))
								)
				));

		add(EntityRegistry.STAR_WOLF_ENTITY.get(), LootTable.lootTable());
		add(EntityRegistry.SKYGAZER_ENTITY.get(), LootTable.lootTable());
		add(EntityRegistry.SKELETON_MERCHANT_ENTITY.get(), LootTable.lootTable());
	}
}