package tech.anonymoushacker1279.immersiveweapons.data.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import tech.anonymoushacker1279.immersiveweapons.block.BarbedWireBlock;
import tech.anonymoushacker1279.immersiveweapons.block.SandbagBlock;
import tech.anonymoushacker1279.immersiveweapons.block.WoodenSpikesBlock;
import tech.anonymoushacker1279.immersiveweapons.block.WoodenTableBlock;
import tech.anonymoushacker1279.immersiveweapons.block.skull.CustomSkullBlock;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class BlockLootTables extends BlockLootSubProvider {

	protected BlockLootTables(Provider registries) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return BlockRegistry.BLOCKS.getEntries().stream()
				.map(supplier -> (Block) supplier.get())
				::iterator;
	}

	@Override
	protected void generate() {
		List<Block> blocks = new ArrayList<>(250);
		BlockRegistry.BLOCKS.getEntries().stream().map(Supplier::get).forEach(blocks::add);

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
		dropSelf(BlockRegistry.BURNED_OAK_WALL_SIGN.get());
		dropSelf(BlockRegistry.BURNED_OAK_HANGING_SIGN.get());
		dropSelf(BlockRegistry.BURNED_OAK_WALL_HANGING_SIGN.get());
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
		dropSelf(BlockRegistry.RAW_SULFUR_BLOCK.get());
		dropSelf(BlockRegistry.SMALL_PARTS_TABLE.get());
		dropSelf(BlockRegistry.AMMUNITION_TABLE.get());
		dropSelf(BlockRegistry.SPIKE_TRAP.get());
		dropSelf(BlockRegistry.SPOTLIGHT.get());
		dropSelf(BlockRegistry.STRIPPED_BURNED_OAK_LOG.get());
		dropSelf(BlockRegistry.STRIPPED_BURNED_OAK_WOOD.get());
		dropSelf(BlockRegistry.TESLA_BLOCK.get());
		dropSelf(BlockRegistry.TESLA_SYNTHESIZER.get());
		dropSelf(BlockRegistry.TROLL_FLAG.get());
		dropSelf(BlockRegistry.WALL_SHELF.get());
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
		dropSelf(BlockRegistry.STARDUST_WALL_SIGN.get());
		dropSelf(BlockRegistry.STARDUST_HANGING_SIGN.get());
		dropSelf(BlockRegistry.STARDUST_WALL_HANGING_SIGN.get());
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
		dropSelf(BlockRegistry.CHAMPION_BRICKS.get());
		dropSelf(BlockRegistry.CHAMPION_BASE.get());
		dropSelf(BlockRegistry.CHAMPION_KEYCARD_BRICKS.get());
		dropSelf(BlockRegistry.LANDMINE.get());
		dropSelf(BlockRegistry.STAR_FORGE_BRICKS.get());
		dropSelf(BlockRegistry.STAR_FORGE_CONTROLLER.get());
		dropSelf(BlockRegistry.SOLAR_LENS.get());
		dropSelf(BlockRegistry.TILTROS_PORTAL_FRAME.get());
		dropSelf(BlockRegistry.COMMANDER_PEDESTAL.get());
		dropSelf(BlockRegistry.CELESTIAL_ALTAR.get());
		dropSelf(BlockRegistry.VOID_ORE.get());
		dropSelf(BlockRegistry.TELEPORTER.get());

		blocks.stream().filter(WoodenTableBlock.class::isInstance).forEach(this::dropSelf);
		blocks.stream().filter(CustomSkullBlock.class::isInstance).forEach(this::dropSelf);
		blocks.stream().filter(block -> block.getDescriptionId().contains("bulletproof_glass")).forEach(this::dropSelf);

		add(BlockRegistry.CLOUD.get(), noDrop());
		add(BlockRegistry.POTTED_DEATHWEED.get(), noDrop());
		add(BlockRegistry.POTTED_MOONGLOW.get(), noDrop());

		// Complex block drops
		add(BlockRegistry.BURNED_OAK_DOOR.get(), this::createDoorTable);
		add(BlockRegistry.STARDUST_DOOR.get(), this::createDoorTable);
		add(BlockRegistry.STARDUST_LEAVES.get(), (block) -> createLeavesDrops(block, BlockRegistry.STARDUST_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
		add(BlockRegistry.BURNED_OAK_BRANCH.get(), this::createSilkTouchOnlyTable);
		add(BlockRegistry.COBALT_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.RAW_COBALT.get()));
		add(BlockRegistry.DEEPSLATE_COBALT_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.RAW_COBALT.get()));
		add(BlockRegistry.MOLTEN_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.MOLTEN_SHARD.get(), 3, 6));
		add(BlockRegistry.SULFUR_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.SULFUR.get(), 2, 4));
		add(BlockRegistry.DEEPSLATE_SULFUR_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.SULFUR.get(), 2, 4));
		add(BlockRegistry.NETHER_SULFUR_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.SULFUR.get(), 2, 4));
		add(BlockRegistry.POTASSIUM_NITRATE_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.POTASSIUM_NITRATE.get(), 4, 6));
		add(BlockRegistry.ASTRAL_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.RAW_ASTRAL.get(), 1, 3));
		add(BlockRegistry.PITFALL.get(), this::createSilkTouchOnlyTable);
		add(BlockRegistry.VENTUS_ORE.get(), (block) -> createOreDrop(block, ItemRegistry.VENTUS_SHARD.get(), 2, 5));
		add(BlockRegistry.WOODEN_SPIKES.get(), (block) -> LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("main")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(block,
								LootItem.lootTableItem(BlockItemRegistry.WOODEN_SPIKES_ITEM.get())
										.when(ExplosionCondition.survivesExplosion())
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(WoodenSpikesBlock.DAMAGE_STAGE, 0)))
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))));
		add(BlockRegistry.BARBED_WIRE.get(), (block) -> LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("main")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(block,
								LootItem.lootTableItem(BlockItemRegistry.BARBED_WIRE_ITEM.get())
										.when(ExplosionCondition.survivesExplosion())
										.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
												.setProperties(StatePropertiesPredicate.Builder.properties()
														.hasProperty(BarbedWireBlock.DAMAGE_STAGE, 0)))
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))));
		add(BlockRegistry.SANDBAG.get(), (block) -> LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.name("main")
						.setRolls(ConstantValue.exactly(1.0F))
						.add(applyExplosionDecay(block,
								LootItem.lootTableItem(BlockItemRegistry.SANDBAG_ITEM.get())
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
	}

	protected LootTable.Builder createOreDrop(Block block, Item item, int min, int max) {
		HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

		return this.createSilkTouchDispatchTable(
				block,
				this.applyExplosionDecay(
						block, LootItem.lootTableItem(item)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
								.apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
				)
		);
	}
}