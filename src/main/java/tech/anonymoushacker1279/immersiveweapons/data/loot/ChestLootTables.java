package tech.anonymoushacker1279.immersiveweapons.data.loot;

import net.minecraft.ChatFormatting;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.function.BiConsumer;

public class ChestLootTables implements LootTableSubProvider {

	@Override
	public void generate(BiConsumer<ResourceLocation, Builder> out) {
		out.accept(LootTableLocations.ABANDONED_FACTORY,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.name("main")
								.setRolls(UniformGenerator.between(3.0F, 6.0F))
								.add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(12)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(ItemRegistry.COPPER_NUGGET.get()).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
								.add(LootItem.lootTableItem(Items.STONE).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 12.0F))))
								.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(8)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(ItemRegistry.COPPER_PICKAXE.get()).setWeight(5)
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(5.0F, 15.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.OBSIDIAN_SHARD.get()).setWeight(4))
								.add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
								.add(LootItem.lootTableItem(ItemRegistry.ELECTRIC_INGOT.get()).setWeight(1))));

		out.accept(LootTableLocations.BATTLEFIELD_CAMP,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.name("main")
								.setRolls(UniformGenerator.between(4.0F, 7.0F))
								.add(LootItem.lootTableItem(ItemRegistry.IRON_MUSKET_BALL.get()).setWeight(8)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
								.add(LootItem.lootTableItem(Items.BREAD).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
								.add(LootItem.lootTableItem(Items.APPLE).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
								.add(LootItem.lootTableItem(ItemRegistry.COPPER_SHOVEL.get()).setWeight(6)
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(3.0F, 10.0F)
										)))
								.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(Items.EMERALD).setWeight(3))
								.add(LootItem.lootTableItem(ItemRegistry.FLINTLOCK_PISTOL.get()).setWeight(1))));

		out.accept(LootTableLocations.CAMPSITE,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.name("main")
								.setRolls(UniformGenerator.between(3.0F, 7.0F))
								.add(LootItem.lootTableItem(ItemRegistry.COPPER_MUSKET_BALL.get()).setWeight(8)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
								.add(LootItem.lootTableItem(Items.BREAD).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
								.add(LootItem.lootTableItem(Items.APPLE).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(ItemRegistry.CLOTH_SCRAP.get()).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
								.add(LootItem.lootTableItem(ItemRegistry.COPPER_AXE.get()).setWeight(6)
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(3.0F, 10.0F)
										)))
								.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(ItemRegistry.FLARE.get()).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 4.0f))))
								.add(LootItem.lootTableItem(ItemRegistry.FLARE_GUN.get()).setWeight(1))
								.add(LootItem.lootTableItem(ItemRegistry.AGILITY_BRACELET.get()).setWeight(1))));

		out.accept(LootTableLocations.UNDERGROUND_BUNKER,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.name("main")
								.setRolls(UniformGenerator.between(4.0F, 6.0F))
								.add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(8)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(Items.BREAD).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
								.add(LootItem.lootTableItem(Items.APPLE).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
								.add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(6)
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(5.0F, 15.0F)
										)))
								.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(ItemRegistry.FLINTLOCK_PISTOL.get()).setWeight(2))
								.add(LootItem.lootTableItem(ItemRegistry.BLUNDERBUSS.get()).setWeight(1))));

		out.accept(LootTableLocations.BATTLEFIELD_VILLAGE_MEDIC_STATION,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.name("main")
								.setRolls(UniformGenerator.between(3.0F, 5.0F))
								.add(LootItem.lootTableItem(ItemRegistry.BANDAGE.get()).setWeight(8)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
								.add(LootItem.lootTableItem(ItemRegistry.CHOCOLATE_BAR.get()).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(Items.APPLE).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(4)
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(10.0F, 20.0F)
										)).apply(SetNameFunction
												.setName(Component.translatable("loot.immersiveweapons.chest.village.battlefield.medic_station.iron_axe")
														.withStyle(ChatFormatting.RED))))
								.add(LootItem.lootTableItem(ItemRegistry.USED_SYRINGE.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(ItemRegistry.SYRINGE.get()).setWeight(2)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f))))
								.add(LootItem.lootTableItem(ItemRegistry.MORPHINE.get()).setWeight(1))));

		out.accept(LootTableLocations.HANS_HUT,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.name("main")
								.setRolls(UniformGenerator.between(4.0F, 6.0F))
								.add(LootItem.lootTableItem(Items.SALMON).setWeight(5)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
								.add(LootItem.lootTableItem(Items.COD).setWeight(5)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
								.add(LootItem.lootTableItem(ItemRegistry.BOTTLE_OF_ALCOHOL.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(ItemRegistry.BOTTLE_OF_WINE.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(Items.IRON_SWORD).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 1.0f)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 25.0F)
										)))
								.add(LootItem.lootTableItem(Items.IRON_HELMET).setWeight(2)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 1.0f)))
										.apply(EnchantRandomlyFunction.randomApplicableEnchantment()))));

		out.accept(LootTableLocations.HANS_HUT_CASK,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.name("main")
								.setRolls(UniformGenerator.between(6.0F, 9.0F))
								.add(LootItem.lootTableItem(Items.WHEAT).setWeight(2)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 32.0F))))
								.add(LootItem.lootTableItem(Items.SWEET_BERRIES).setWeight(1)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0f, 16.0f))))));

		out.accept(LootTableLocations.BIODOME_CHEST,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.name("main")
								.setRolls(UniformGenerator.between(3.0F, 5.0F))
								.add(LootItem.lootTableItem(ItemRegistry.COBALT_SWORD.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.COBALT_PICKAXE.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.COBALT_SHOVEL.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.COBALT_AXE.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.COBALT_HOE.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.COBALT_HELMET.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.COBALT_CHESTPLATE.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.COBALT_LEGGINGS.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.COBALT_BOOTS.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(Items.BOW).setWeight(3)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.COBALT_ARROW.get()).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0f, 32.0f))))
								.add(LootItem.lootTableItem(Items.CROSSBOW).setWeight(3)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.FLINTLOCK_PISTOL.get()).setWeight(2)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.MUSKET.get()).setWeight(2)
										.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
										.apply(EnchantWithLevelsFunction.enchantWithLevels(
												UniformGenerator.between(15.0F, 20.0F)
										)))
								.add(LootItem.lootTableItem(ItemRegistry.COBALT_INGOT.get()).setWeight(6)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0f, 12.0f))))
						));

		out.accept(LootTableLocations.BIODOME_MEDICINE_BARREL,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.name("main")
								.setRolls(UniformGenerator.between(3.0f, 6.0f))
								.add(LootItem.lootTableItem(Items.BREAD).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(Items.COOKED_BEEF).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(Items.COOKED_CHICKEN).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(Items.COOKED_COD).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(Items.COOKED_MUTTON).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(Items.COOKED_PORKCHOP).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(Items.COOKED_RABBIT).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(Items.COOKED_SALMON).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(ItemRegistry.FIRST_AID_KIT.get()).setWeight(2)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f))))
								.add(LootItem.lootTableItem(ItemRegistry.BANDAGE.get()).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
						));
	}
}