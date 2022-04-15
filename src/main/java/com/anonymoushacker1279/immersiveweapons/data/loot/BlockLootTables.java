package com.anonymoushacker1279.immersiveweapons.data.loot;

import com.anonymoushacker1279.immersiveweapons.block.base.SandbagBlock;
import com.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue.WarriorStatueTorso;
import com.anonymoushacker1279.immersiveweapons.block.trap.LandmineBlock;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class BlockLootTables implements Consumer<BiConsumer<ResourceLocation, Builder>> {

	private final Map<ResourceLocation, Builder> map = Maps.newHashMap();
	private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
	private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
	private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
	private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
	private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> resourceLocationBuilderBiConsumer) {
		addTables();
		Set<ResourceLocation> set = Sets.newHashSet();

		List<RegistryObject<Block>> knownBlocks = getKnownBlocks();

		for (RegistryObject<Block> blockRegistryObject : knownBlocks) {
			Block block = blockRegistryObject.get();
			ResourceLocation lootTable = block.getLootTable();
			if (lootTable != BuiltInLootTables.EMPTY && set.add(lootTable)) {
				LootTable.Builder builder = map.remove(lootTable);
				if (builder != null) {
					resourceLocationBuilderBiConsumer.accept(lootTable, builder);
				}
			}
		}

		if (!map.isEmpty()) {
			throw new IllegalStateException("Created block loot tables for non-blocks: " + map.keySet());
		}
	}

	private void addTables() {
		// Simple block drops
		dropSelf(DeferredRegistryHandler.AMERICAN_FLAG.get());
		dropSelf(DeferredRegistryHandler.BARBED_WIRE.get());
		dropSelf(DeferredRegistryHandler.BARBED_WIRE_FENCE.get());
		dropSelf(DeferredRegistryHandler.BARREL_TAP.get());
		dropSelf(DeferredRegistryHandler.BEAR_TRAP.get());
		dropSelf(DeferredRegistryHandler.BIOHAZARD_BOX.get());
		dropSelf(DeferredRegistryHandler.BRITISH_FLAG.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_BUTTON.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_DOOR.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_FENCE.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_FENCE_GATE.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_LOG.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_PLANKS.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_PRESSURE_PLATE.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_SIGN.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_SLAB.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_STAIRS.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_TRAPDOOR.get());
		dropSelf(DeferredRegistryHandler.BURNED_OAK_WOOD.get());
		dropSelf(DeferredRegistryHandler.CAMP_CHAIR.get());
		dropSelf(DeferredRegistryHandler.CANADIAN_FLAG.get());
		dropSelf(DeferredRegistryHandler.CELESTIAL_LANTERN.get());
		dropSelf(DeferredRegistryHandler.CLOUD_MARBLE.get());
		dropSelf(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_SLAB.get());
		dropSelf(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_STAIRS.get());
		dropSelf(DeferredRegistryHandler.CLOUD_MARBLE_BRICKS.get());
		dropSelf(DeferredRegistryHandler.CLOUD_MARBLE_PILLAR.get());
		dropSelf(DeferredRegistryHandler.COBALT_BLOCK.get());
		dropSelf(DeferredRegistryHandler.CORRUGATED_IRON_PANEL.get());
		dropSelf(DeferredRegistryHandler.CORRUGATED_IRON_PANEL_BARS.get());
		dropSelf(DeferredRegistryHandler.CORRUGATED_IRON_PANEL_FLAT.get());
		dropSelf(DeferredRegistryHandler.CORRUGATED_IRON_PANEL_FLAT_BARS.get());
		dropSelf(DeferredRegistryHandler.DRIED_MUD.get());
		dropSelf(DeferredRegistryHandler.ELECTRIC_ORE.get());
		dropSelf(DeferredRegistryHandler.FLAG_POLE.get());
		dropSelf(DeferredRegistryHandler.GADSDEN_FLAG.get());
		dropSelf(DeferredRegistryHandler.HARDENED_MUD.get());
		dropSelf(DeferredRegistryHandler.HARDENED_MUD_SLAB.get());
		dropSelf(DeferredRegistryHandler.HARDENED_MUD_STAIRS.get());
		dropSelf(DeferredRegistryHandler.HARDENED_MUD_WINDOW.get());
		dropSelf(DeferredRegistryHandler.IMMERSIVE_WEAPONS_FLAG.get());
		dropSelf(DeferredRegistryHandler.MEDIC_STATUE.get());
		dropSelf(DeferredRegistryHandler.MEXICAN_FLAG.get());
		dropSelf(DeferredRegistryHandler.MINUTEMAN_STATUE.get());
		dropSelf(DeferredRegistryHandler.MOLTEN_BLOCK.get());
		dropSelf(DeferredRegistryHandler.MORTAR.get());
		dropSelf(DeferredRegistryHandler.MUD.get());
		dropSelf(DeferredRegistryHandler.PANIC_ALARM.get());
		dropSelf(DeferredRegistryHandler.PUNJI_STICKS.get());
		dropSelf(DeferredRegistryHandler.RAW_COBALT_BLOCK.get());
		dropSelf(DeferredRegistryHandler.SMALL_PARTS_TABLE.get());
		dropSelf(DeferredRegistryHandler.SPIKE_TRAP.get());
		dropSelf(DeferredRegistryHandler.SPOTLIGHT.get());
		dropSelf(DeferredRegistryHandler.STRIPPED_BURNED_OAK_LOG.get());
		dropSelf(DeferredRegistryHandler.STRIPPED_BURNED_OAK_WOOD.get());
		dropSelf(DeferredRegistryHandler.TESLA_BLOCK.get());
		dropSelf(DeferredRegistryHandler.TESLA_SYNTHESIZER.get());
		dropSelf(DeferredRegistryHandler.TROLL_FLAG.get());
		dropSelf(DeferredRegistryHandler.WALL_SHELF.get());
		dropSelf(DeferredRegistryHandler.WARRIOR_STATUE_BASE.get());
		dropSelf(DeferredRegistryHandler.WARRIOR_STATUE_HEAD.get());
		dropSelf(DeferredRegistryHandler.WOODEN_SPIKES.get());
		dropSelf(DeferredRegistryHandler.OAK_TABLE.get());

		// Complex block drops
		add(DeferredRegistryHandler.BURNED_OAK_BRANCH.get(), (leafLikeDrop) -> createLeafLikeDrop(leafLikeDrop, Items.STICK, NORMAL_LEAVES_SAPLING_CHANCES));
		add(DeferredRegistryHandler.COBALT_ORE.get(), (block) -> createOreDrop(block, DeferredRegistryHandler.RAW_COBALT.get()));
		add(DeferredRegistryHandler.DEEPSLATE_COBALT_ORE.get(), (block) -> createOreDrop(block, DeferredRegistryHandler.RAW_COBALT.get()));
		add(DeferredRegistryHandler.MOLTEN_ORE.get(), (block) -> createOreDrop(block, DeferredRegistryHandler.MOLTEN_SHARD.get()));
		add(DeferredRegistryHandler.SULFUR_ORE.get(), (block) -> createOreDrop(block, DeferredRegistryHandler.SULFUR.get(), 2, 4));
		add(DeferredRegistryHandler.DEEPSLATE_SULFUR_ORE.get(), (block) -> createOreDrop(block, DeferredRegistryHandler.SULFUR.get(), 2, 4));
		add(DeferredRegistryHandler.NETHER_SULFUR_ORE.get(), (block) -> createOreDrop(block, DeferredRegistryHandler.SULFUR.get(), 2, 4));
		add(DeferredRegistryHandler.PITFALL.get(), (leafLikeDrop) -> createLeafLikeDrop(leafLikeDrop, Items.STICK, NORMAL_LEAVES_SAPLING_CHANCES));
		add(DeferredRegistryHandler.VENTUS_ORE.get(), (block) -> createOreDrop(block, DeferredRegistryHandler.VENTUS_SHARD.get()));
		add(DeferredRegistryHandler.LANDMINE.get(), (block) -> LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(DeferredRegistryHandler.LANDMINE_ITEM.get())
								.when(ExplosionCondition.survivesExplosion().invert())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(LandmineBlock.ARMED, false))))))));
		add(DeferredRegistryHandler.SANDBAG.get(), (block) -> LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(DeferredRegistryHandler.SANDBAG_ITEM.get())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(SandbagBlock.BAGS, 1))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(SandbagBlock.BAGS, 2))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F))
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(SandbagBlock.BAGS, 3))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(SandbagBlock.BAGS, 4))))))));
		add(DeferredRegistryHandler.WARRIOR_STATUE_TORSO.get(), (block) -> LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(DeferredRegistryHandler.AZUL_KEYSTONE.get())
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(WarriorStatueTorso.POWERED, true))))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(DeferredRegistryHandler.WARRIOR_STATUE_TORSO.get())
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(WarriorStatueTorso.POWERED, true))))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(DeferredRegistryHandler.WARRIOR_STATUE_TORSO.get())
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(WarriorStatueTorso.POWERED, false)))))));
	}

	protected List<RegistryObject<Block>> getKnownBlocks() {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(DeferredRegistryHandler.BLOCKS.getEntries().stream().iterator(), 0),
				false).collect(Collectors.toList());
	}

	protected static LootTable.Builder createLeafLikeDrop(Block block, Item altDrop, float... pChances) {
		return createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(LootItem.lootTableItem(altDrop))
				.when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, pChances)))
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
						.when(HAS_NO_SHEARS_OR_SILK_TOUCH)
						.add(applyExplosionDecay(LootItem.lootTableItem(Items.STICK)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
								.when(BonusLevelTableCondition
										.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
	}

	protected static LootTable.Builder createSilkTouchOrShearsDispatchTable(Block pBlock, LootPoolEntryContainer.Builder<?> pAlternativeEntryBuilder) {
		return createSelfDropDispatchTable(pBlock, HAS_SHEARS_OR_SILK_TOUCH, pAlternativeEntryBuilder);
	}

	protected static LootTable.Builder createSelfDropDispatchTable(Block pBlock, LootItemCondition.Builder pConditionBuilder, LootPoolEntryContainer.Builder<?> pAlternativeEntryBuilder) {
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(pBlock).when(pConditionBuilder).otherwise(pAlternativeEntryBuilder)));
	}

	protected static LootTable.Builder createOreDrop(Block pBlock, Item pItem) {
		return createSilkTouchDispatchTable(pBlock, applyExplosionDecay(LootItem.lootTableItem(pItem).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}

	protected static LootTable.Builder createOreDrop(Block pBlock, Item pItem, int baseCount, int maxCount) {
		return createSilkTouchDispatchTable(pBlock, applyExplosionDecay(LootItem.lootTableItem(pItem).apply(SetItemCountFunction.setCount(UniformGenerator.between(baseCount, maxCount))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}

	protected static LootTable.Builder createSilkTouchDispatchTable(Block pBlock, LootPoolEntryContainer.Builder<?> pAlternativeEntryBuilder) {
		return createSelfDropDispatchTable(pBlock, HAS_SILK_TOUCH, pAlternativeEntryBuilder);
	}

	public void dropSelf(Block pBlock) {
		dropOther(pBlock, pBlock);
	}

	public void dropOther(Block pBlock, ItemLike pDrop) {
		add(pBlock, createSingleItemTable(pDrop));
	}

	protected void add(Block pBlock, LootTable.Builder pLootTableBuilder) {
		map.put(pBlock.getLootTable(), pLootTableBuilder);
	}

	protected void add(Block pBlock, Function<Block, Builder> pFactory) {
		add(pBlock, pFactory.apply(pBlock));
	}

	protected static LootTable.Builder createSingleItemTable(ItemLike itemLike) {
		return LootTable.lootTable().withPool(applyExplosionCondition(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(itemLike))));
	}

	protected static <T> T applyExplosionCondition(ConditionUserBuilder<T> pCondition) {
		return pCondition.when(ExplosionCondition.survivesExplosion());
	}

	protected static <T> T applyExplosionDecay(FunctionUserBuilder<T> pFunction) {
		return pFunction.apply(ApplyExplosionDecay.explosionDecay());
	}
}