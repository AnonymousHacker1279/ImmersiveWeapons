package tech.anonymoushacker1279.immersiveweapons.data.loot;

import net.minecraft.advancements.critereon.*;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.block.*;
import tech.anonymoushacker1279.immersiveweapons.block.barbed_wire.BarbedWireBlock;
import tech.anonymoushacker1279.immersiveweapons.block.misc.warrior_statue.WarriorStatueTorso;
import tech.anonymoushacker1279.immersiveweapons.data.lists.BlockLists;
import tech.anonymoushacker1279.immersiveweapons.data.tags.lists.BlockTagLists;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class BlockLootTables implements LootTableSubProvider {

	@Nullable
	private BiConsumer<ResourceLocation, LootTable.Builder> out;

	private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
	private static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
	private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
	private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
	private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

	@Override
	public void generate(BiConsumer<ResourceLocation, Builder> out) {
		this.out = out;

		// Simple block drops
		dropSelf(BlockRegistry.AMERICAN_FLAG.get());
		dropSelf(BlockRegistry.BARBED_WIRE_FENCE.get());
		dropSelf(BlockRegistry.BARREL_TAP.get());
		dropSelf(BlockRegistry.BEAR_TRAP.get());
		dropSelf(BlockRegistry.BIOHAZARD_BOX.get());
		dropSelf(BlockRegistry.BRITISH_FLAG.get());
		dropSelf(BlockRegistry.BURNED_OAK_BUTTON.get());
		dropSelf(BlockRegistry.BURNED_OAK_FENCE.get());
		dropSelf(BlockRegistry.BURNED_OAK_FENCE_GATE.get());
		dropSelf(BlockRegistry.BURNED_OAK_LOG.get());
		dropSelf(BlockRegistry.BURNED_OAK_PLANKS.get());
		dropSelf(BlockRegistry.BURNED_OAK_PRESSURE_PLATE.get());
		dropSelf(BlockRegistry.BURNED_OAK_SIGN.get());
		dropSelf(BlockRegistry.BURNED_OAK_SLAB.get());
		dropSelf(BlockRegistry.BURNED_OAK_STAIRS.get());
		dropSelf(BlockRegistry.BURNED_OAK_TRAPDOOR.get());
		dropSelf(BlockRegistry.BURNED_OAK_WOOD.get());
		dropSelf(BlockRegistry.CAMP_CHAIR.get());
		dropSelf(BlockRegistry.CANADIAN_FLAG.get());
		dropSelf(BlockRegistry.CELESTIAL_LANTERN.get());
		dropSelf(BlockRegistry.CLOUD_MARBLE.get());
		dropSelf(BlockRegistry.CLOUD_MARBLE_BRICK_SLAB.get());
		dropSelf(BlockRegistry.CLOUD_MARBLE_BRICK_STAIRS.get());
		dropSelf(BlockRegistry.CLOUD_MARBLE_BRICKS.get());
		dropSelf(BlockRegistry.CLOUD_MARBLE_PILLAR.get());
		dropSelf(BlockRegistry.CLOUD_MARBLE_BRICK_WALL.get());
		dropSelf(BlockRegistry.COBALT_BLOCK.get());
		dropSelf(BlockRegistry.IRON_PANEL.get());
		dropSelf(BlockRegistry.IRON_PANEL_BARS.get());
		dropSelf(BlockRegistry.DRIED_MUD.get());
		dropSelf(BlockRegistry.ELECTRIC_ORE.get());
		dropSelf(BlockRegistry.FLAG_POLE.get());
		dropSelf(BlockRegistry.GADSDEN_FLAG.get());
		dropSelf(BlockRegistry.HARDENED_MUD.get());
		dropSelf(BlockRegistry.HARDENED_MUD_SLAB.get());
		dropSelf(BlockRegistry.HARDENED_MUD_STAIRS.get());
		dropSelf(BlockRegistry.HARDENED_MUD_WINDOW.get());
		dropSelf(BlockRegistry.IMMERSIVE_WEAPONS_FLAG.get());
		dropSelf(BlockRegistry.MEDIC_STATUE.get());
		dropSelf(BlockRegistry.MEXICAN_FLAG.get());
		dropSelf(BlockRegistry.MINUTEMAN_STATUE.get());
		dropSelf(BlockRegistry.MOLTEN_BLOCK.get());
		dropSelf(BlockRegistry.MORTAR.get());
		dropSelf(BlockRegistry.MUD.get());
		dropSelf(BlockRegistry.PANIC_ALARM.get());
		dropSelf(BlockRegistry.PUNJI_STICKS.get());
		dropSelf(BlockRegistry.RAW_COBALT_BLOCK.get());
		dropSelf(BlockRegistry.SMALL_PARTS_TABLE.get());
		dropSelf(BlockRegistry.SPIKE_TRAP.get());
		dropSelf(BlockRegistry.SPOTLIGHT.get());
		dropSelf(BlockRegistry.STRIPPED_BURNED_OAK_LOG.get());
		dropSelf(BlockRegistry.STRIPPED_BURNED_OAK_WOOD.get());
		dropSelf(BlockRegistry.TESLA_BLOCK.get());
		dropSelf(BlockRegistry.TESLA_SYNTHESIZER.get());
		dropSelf(BlockRegistry.TROLL_FLAG.get());
		dropSelf(BlockRegistry.WALL_SHELF.get());
		dropSelf(BlockRegistry.WARRIOR_STATUE_BASE.get());
		dropSelf(BlockRegistry.WARRIOR_STATUE_HEAD.get());
		dropSelf(BlockRegistry.MOONGLOW.get());
		dropSelf(BlockRegistry.STARDUST_LOG.get());
		dropSelf(BlockRegistry.STARDUST_WOOD.get());
		dropSelf(BlockRegistry.STARDUST_PLANKS.get());
		dropSelf(BlockRegistry.STARDUST_SLAB.get());
		dropSelf(BlockRegistry.STARDUST_STAIRS.get());
		dropSelf(BlockRegistry.STARDUST_FENCE.get());
		dropSelf(BlockRegistry.STARDUST_FENCE_GATE.get());
		dropSelf(BlockRegistry.STARDUST_PRESSURE_PLATE.get());
		dropSelf(BlockRegistry.STARDUST_BUTTON.get());
		dropSelf(BlockRegistry.STARDUST_SIGN.get());
		dropSelf(BlockRegistry.STARDUST_TRAPDOOR.get());
		dropSelf(BlockRegistry.STRIPPED_STARDUST_LOG.get());
		dropSelf(BlockRegistry.STRIPPED_STARDUST_WOOD.get());
		dropSelf(BlockRegistry.STARDUST_SAPLING.get());
		dropSelf(BlockRegistry.BLOOD_SAND.get());
		dropSelf(BlockRegistry.BLOOD_SANDSTONE.get());
		dropSelf(BlockRegistry.BLOOD_SANDSTONE_SLAB.get());
		dropSelf(BlockRegistry.BLOOD_SANDSTONE_STAIRS.get());
		dropSelf(BlockRegistry.BLOOD_SANDSTONE_WALL.get());
		dropSelf(BlockRegistry.CHISELED_BLOOD_SANDSTONE.get());
		dropSelf(BlockRegistry.CUT_BLOOD_SANDSTONE.get());
		dropSelf(BlockRegistry.CUT_BLOOD_SANDSTONE_SLAB.get());
		dropSelf(BlockRegistry.SMOOTH_BLOOD_SANDSTONE.get());
		dropSelf(BlockRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB.get());
		dropSelf(BlockRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS.get());
		dropSelf(BlockRegistry.DEATHWEED.get());
		dropSelf(BlockRegistry.ASTRAL_CRYSTAL.get());
		dropSelf(BlockRegistry.ASTRAL_BLOCK.get());
		dropSelf(BlockRegistry.STARSTORM_CRYSTAL.get());
		dropSelf(BlockRegistry.STARSTORM_BLOCK.get());
		dropSelf(BlockRegistry.BIODOME_LIFE_SUPPORT_UNIT.get());
		dropSelf(BlockRegistry.RUSTED_IRON_BLOCK.get());

		for (Block block : BlockTagLists.TABLES) {
			dropSelf(block);
		}

		for (Block block : BlockLists.headBlocks) {
			dropSelf(block);
		}

		// Complex block drops
		add(BlockRegistry.BURNED_OAK_DOOR.get(), BlockLootTables::createDoor);
		add(BlockRegistry.STARDUST_DOOR.get(), BlockLootTables::createDoor);
		add(BlockRegistry.STARDUST_LEAVES.get(), (leafLikeDrop) -> createLeafLikeDrop(leafLikeDrop, BlockItemRegistry.STARDUST_SAPLING_ITEM.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(BlockRegistry.BURNED_OAK_BRANCH.get(), (leafLikeDrop) -> createLeafLikeDrop(leafLikeDrop, Items.STICK, NORMAL_LEAVES_SAPLING_CHANCES));
		add(BlockRegistry.COBALT_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.RAW_COBALT.get()));
		add(BlockRegistry.DEEPSLATE_COBALT_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.RAW_COBALT.get()));
		add(BlockRegistry.MOLTEN_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.MOLTEN_SHARD.get(), 3, 6));
		add(BlockRegistry.SULFUR_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.SULFUR.get(), 2, 4));
		add(BlockRegistry.DEEPSLATE_SULFUR_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.SULFUR.get(), 2, 4));
		add(BlockRegistry.NETHER_SULFUR_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.SULFUR.get(), 2, 4));
		add(BlockRegistry.ASTRAL_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.RAW_ASTRAL.get(), 1, 3));
		add(BlockRegistry.PITFALL.get(), (leafLikeDrop) -> createLeafLikeDrop(leafLikeDrop, Items.STICK, NORMAL_LEAVES_SAPLING_CHANCES));
		add(BlockRegistry.VENTUS_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.VENTUS_SHARD.get(), 2, 5));
		add(BlockRegistry.LANDMINE.get(), (block) -> LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(BlockItemRegistry.LANDMINE_ITEM.get())
								.when(ExplosionCondition.survivesExplosion().invert())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(LandmineBlock.ARMED, false))))))));
		add(BlockRegistry.WOODEN_SPIKES.get(), (block) -> LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(BlockItemRegistry.WOODEN_SPIKES_ITEM.get())
								.when(ExplosionCondition.survivesExplosion())
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(WoodenSpikesBlock.DAMAGE_STAGE, 0)))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))));
		add(BlockRegistry.BARBED_WIRE.get(), (block) -> LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(BlockItemRegistry.BARBED_WIRE_ITEM.get())
								.when(ExplosionCondition.survivesExplosion())
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(BarbedWireBlock.DAMAGE_STAGE, 0)))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))));
		add(BlockRegistry.SANDBAG.get(), (block) -> LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(BlockItemRegistry.SANDBAG_ITEM.get())
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(SandbagBlock.BAGS, 0))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(SandbagBlock.BAGS, 1))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F))
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(SandbagBlock.BAGS, 2))))
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0F))
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(SandbagBlock.BAGS, 3))))))));
		add(BlockRegistry.WARRIOR_STATUE_TORSO.get(), (block) -> LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(ItemRegistry.AZUL_KEYSTONE.get())
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(WarriorStatueTorso.POWERED, true))))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(BlockRegistry.WARRIOR_STATUE_TORSO.get())
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(WarriorStatueTorso.POWERED, true))))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(LootItem.lootTableItem(BlockRegistry.WARRIOR_STATUE_TORSO.get())
								.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
										.setProperties(StatePropertiesPredicate.Builder.properties()
												.hasProperty(WarriorStatueTorso.POWERED, false)))))));
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

	protected static LootTable.Builder createDoor(Block block) {
		return createSinglePropConditionTable(block, DoorBlock.HALF, DoubleBlockHalf.LOWER);
	}

	protected static <T extends Comparable<T> & StringRepresentable> LootTable.Builder createSinglePropConditionTable(Block block, Property<T> property, T t) {
		return LootTable.lootTable().withPool(applyExplosionCondition(LootPool.lootPool()
				.setRolls(ConstantValue.exactly(1.0F))
				.add(LootItem.lootTableItem(block)
						.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
								.setProperties(StatePropertiesPredicate.Builder.properties()
										.hasProperty(property, t))))));
	}

	protected void add(Block pBlock, LootTable.Builder pLootTableBuilder) {
		if (out != null) {
			out.accept(pBlock.getLootTable(), pLootTableBuilder.setParamSet(LootContextParamSets.BLOCK));
		}
	}

	protected void add(Block pBlock, Function<Block, Builder> pFactory) {
		add(pBlock, pFactory.apply(pBlock));
	}

	protected static LootTable.Builder createSingleItemTable(ItemLike itemLike) {
		return LootTable.lootTable().withPool(applyExplosionCondition(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(itemLike))));
	}

	protected static <T extends ConditionUserBuilder<T>> T applyExplosionCondition(ConditionUserBuilder<T> pCondition) {
		return pCondition.when(ExplosionCondition.survivesExplosion());
	}

	protected static <T extends FunctionUserBuilder<T>> T applyExplosionDecay(FunctionUserBuilder<T> pFunction) {
		return pFunction.apply(ApplyExplosionDecay.explosionDecay());
	}
}