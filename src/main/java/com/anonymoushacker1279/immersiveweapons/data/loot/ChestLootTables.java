package com.anonymoushacker1279.immersiveweapons.data.loot;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ChestLootTables implements Consumer<BiConsumer<ResourceLocation, Builder>> {
	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> resourceLocationBuilderBiConsumer) {
		resourceLocationBuilderBiConsumer.accept(LootTableLocations.ABANDONED_FACTORY,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.setRolls(UniformGenerator.between(3.0F, 6.0F))
								.add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(12)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.COPPER_NUGGET.get()).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
								.add(LootItem.lootTableItem(Items.STONE).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 12.0F))))
								.add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(8)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.COPPER_PICKAXE.get()).setWeight(5)
										.apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.OBSIDIAN_SHARD.get()).setWeight(4))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.ELECTRIC_INGOT.get()).setWeight(3))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.TESLA_INGOT.get()).setWeight(1))));
		resourceLocationBuilderBiConsumer.accept(LootTableLocations.BATTLEFIELD_CAMP,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.setRolls(UniformGenerator.between(4.0F, 7.0F))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.IRON_MUSKET_BALL.get()).setWeight(8)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
								.add(LootItem.lootTableItem(Items.BREAD).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
								.add(LootItem.lootTableItem(Items.APPLE).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.COPPER_SHOVEL.get()).setWeight(6)
										.apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
								.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(Items.EMERALD).setWeight(3))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.FLINTLOCK_PISTOL.get()).setWeight(1))));
		resourceLocationBuilderBiConsumer.accept(LootTableLocations.CAMPSITE,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.setRolls(UniformGenerator.between(3.0F, 7.0F))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.COPPER_MUSKET_BALL.get()).setWeight(8)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
								.add(LootItem.lootTableItem(Items.BREAD).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
								.add(LootItem.lootTableItem(Items.APPLE).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.CLOTH_SCRAP.get()).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.COPPER_AXE.get()).setWeight(6)
										.apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
								.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.FLARE.get()).setWeight(3)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 4.0f))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.FLARE_GUN.get()).setWeight(1))));
		resourceLocationBuilderBiConsumer.accept(LootTableLocations.UNDERGROUND_BUNKER,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
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
										.apply(EnchantRandomlyFunction.randomApplicableEnchantment()))
								.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.FLINTLOCK_PISTOL.get()).setWeight(2))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.BLUNDERBUSS.get()).setWeight(1))));
		resourceLocationBuilderBiConsumer.accept(LootTableLocations.BATTLEFIELD_VILLAGE_MEDIC_STATION,
				LootTable.lootTable()
						.withPool(LootPool.lootPool()
								.setRolls(UniformGenerator.between(3.0F, 5.0F))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.BANDAGE.get()).setWeight(8)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.CHOCOLATE_BAR.get()).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(Items.APPLE).setWeight(7)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(Items.IRON_AXE).setWeight(4)
										.apply(EnchantRandomlyFunction.randomApplicableEnchantment())
										.apply(SetNameFunction.setName(new TranslatableComponent("loot.immersiveweapons.chest.village.battlefield.medic_station.iron_axe").withStyle(ChatFormatting.RED))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.USED_SYRINGE.get()).setWeight(4)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.SYRINGE.get()).setWeight(2)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f))))
								.add(LootItem.lootTableItem(DeferredRegistryHandler.MORPHINE.get()).setWeight(1))));
	}
}