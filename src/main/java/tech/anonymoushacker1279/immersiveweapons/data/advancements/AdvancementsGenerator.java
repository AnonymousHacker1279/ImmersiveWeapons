package tech.anonymoushacker1279.immersiveweapons.data.advancements;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.*;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.advancement.EntityDiscoveredTrigger;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.data.dimensions.IWDimensions;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementsGenerator extends AdvancementProvider {

	public AdvancementsGenerator(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper exFileHelper) {
		super(output, provider, exFileHelper, List.of((lookup, consumer, existingFileHelper) -> registerAdvancements(consumer)));
	}

	/**
	 * Build advancement trees.
	 *
	 * @param consumer the <code>Consumer</code> extending Advancement
	 */
	public static void registerAdvancements(Consumer<AdvancementHolder> consumer) {
		// Root advancement
		AdvancementHolder root = Builder.advancement()
				.display(ItemRegistry.TESLA_SWORD.get(),
						Component.translatable("advancements.immersiveweapons.root.title")
								.withStyle(ChatFormatting.RED),
						Component.translatable("advancements.immersiveweapons.root.description"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/block/red_stained_bulletproof_glass.png"),
						AdvancementType.TASK, false, false, false)
				.addCriterion("exist",
						PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inDimension(Level.OVERWORLD)))
				.save(consumer, "immersiveweapons:root");


		// Molten advancements
		AdvancementHolder obtainMoltenShard = Builder.advancement().parent(root)
				.display(ItemRegistry.MOLTEN_SHARD.get(),
						Component.translatable("advancements.immersiveweapons.molten_shard.title"),
						Component.translatable("advancements.immersiveweapons.molten_shard.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_SHARD.get()))
				.save(consumer, "immersiveweapons:molten_shard");

		AdvancementHolder smeltMoltenIngot = Builder.advancement().parent(obtainMoltenShard)
				.display(ItemRegistry.MOLTEN_INGOT.get(),
						Component.translatable("advancements.immersiveweapons.molten_ingot.title"),
						Component.translatable("advancements.immersiveweapons.molten_ingot.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_INGOT.get()))
				.save(consumer, "immersiveweapons:molten_ingot");

		Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_SWORD.get(),
						Component.translatable("advancements.immersiveweapons.molten_sword.title"),
						Component.translatable("advancements.immersiveweapons.molten_sword.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_SWORD.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:molten_sword");

		Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_PICKAXE.get(),
						Component.translatable("advancements.immersiveweapons.molten_pickaxe.title"),
						Component.translatable("advancements.immersiveweapons.molten_pickaxe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_PICKAXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:molten_pickaxe");

		Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_AXE.get(),
						Component.translatable("advancements.immersiveweapons.molten_axe.title"),
						Component.translatable("advancements.immersiveweapons.molten_axe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_AXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:molten_axe");

		Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_SHOVEL.get(),
						Component.translatable("advancements.immersiveweapons.molten_shovel.title"),
						Component.translatable("advancements.immersiveweapons.molten_shovel.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_SHOVEL.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:molten_shovel");

		Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_HOE.get(),
						Component.translatable("advancements.immersiveweapons.molten_hoe.title"),
						Component.translatable("advancements.immersiveweapons.molten_hoe.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_HOE.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, "immersiveweapons:molten_hoe");

		Builder.advancement().parent(smeltMoltenIngot)
				.display(BlockItemRegistry.MOLTEN_BLOCK_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.molten_tools.title"),
						Component.translatable("advancements.immersiveweapons.molten_tools.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "molten_sword"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "molten_pickaxe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "molten_axe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "molten_shovel"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "molten_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, "immersiveweapons:molten_tools");

		AdvancementHolder molten_armor = Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_HELMET.get(),
						Component.translatable("advancements.immersiveweapons.molten_armor.title"),
						Component.translatable("advancements.immersiveweapons.molten_armor.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_HELMET.get(),
								ItemRegistry.MOLTEN_CHESTPLATE.get(), ItemRegistry.MOLTEN_LEGGINGS.get(),
								ItemRegistry.MOLTEN_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, "immersiveweapons:molten_armor");

		Builder.advancement().parent(molten_armor)
				.display(Items.LAVA_BUCKET,
						Component.translatable("advancements.immersiveweapons.swim_in_lava.title"),
						Component.translatable("advancements.immersiveweapons.swim_in_lava.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_HELMET.get(),
								ItemRegistry.MOLTEN_CHESTPLATE.get(), ItemRegistry.MOLTEN_LEGGINGS.get(),
								ItemRegistry.MOLTEN_BOOTS.get()))
				.addCriterion("swim", EnterBlockTrigger.TriggerInstance.entersBlock(Blocks.LAVA))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:swim_in_lava");

		// Tesla Advancements
		AdvancementHolder craftConductiveAlloy = Builder.advancement().parent(root)
				.display(ItemRegistry.CONDUCTIVE_ALLOY.get(),
						Component.translatable("advancements.immersiveweapons.conductive_alloy.title"),
						Component.translatable("advancements.immersiveweapons.conductive_alloy.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CONDUCTIVE_ALLOY.get()))
				.save(consumer, "immersiveweapons:conductive_alloy");

		AdvancementHolder obtainElectricIngot = Builder.advancement().parent(craftConductiveAlloy)
				.display(ItemRegistry.ELECTRIC_INGOT.get(),
						Component.translatable("advancements.immersiveweapons.electric_ingot.title"),
						Component.translatable("advancements.immersiveweapons.electric_ingot.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ELECTRIC_INGOT.get()))
				.save(consumer, "immersiveweapons:electric_ingot");

		AdvancementHolder craftTeslaIngot = Builder.advancement().parent(obtainElectricIngot)
				.display(ItemRegistry.TESLA_INGOT.get(),
						Component.translatable("advancements.immersiveweapons.tesla_ingot.title"),
						Component.translatable("advancements.immersiveweapons.tesla_ingot.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_INGOT.get()))
				.save(consumer, "immersiveweapons:tesla_ingot");

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_SWORD.get(),
						Component.translatable("advancements.immersiveweapons.tesla_sword.title"),
						Component.translatable("advancements.immersiveweapons.tesla_sword.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_SWORD.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:tesla_sword");

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_PICKAXE.get(),
						Component.translatable("advancements.immersiveweapons.tesla_pickaxe.title"),
						Component.translatable("advancements.immersiveweapons.tesla_pickaxe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_PICKAXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:tesla_pickaxe");

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_AXE.get(),
						Component.translatable("advancements.immersiveweapons.tesla_axe.title"),
						Component.translatable("advancements.immersiveweapons.tesla_axe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_AXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:tesla_axe");

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_SHOVEL.get(),
						Component.translatable("advancements.immersiveweapons.tesla_shovel.title"),
						Component.translatable("advancements.immersiveweapons.tesla_shovel.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_SHOVEL.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:tesla_shovel");

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_HOE.get(),
						Component.translatable("advancements.immersiveweapons.tesla_hoe.title"),
						Component.translatable("advancements.immersiveweapons.tesla_hoe.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_HOE.get()))
				.rewards(AdvancementRewards.Builder.experience(65))
				.save(consumer, "immersiveweapons:tesla_hoe");

		Builder.advancement().parent(craftTeslaIngot)
				.display(BlockItemRegistry.TESLA_BLOCK_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.tesla_tools.title"),
						Component.translatable("advancements.immersiveweapons.tesla_tools.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_sword"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_pickaxe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_axe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_shovel"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "tesla_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, "immersiveweapons:tesla_tools");

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_HELMET.get(),
						Component.translatable("advancements.immersiveweapons.tesla_armor.title"),
						Component.translatable("advancements.immersiveweapons.tesla_armor.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_HELMET.get(),
								ItemRegistry.TESLA_CHESTPLATE.get(), ItemRegistry.TESLA_LEGGINGS.get(),
								ItemRegistry.TESLA_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, "immersiveweapons:tesla_armor");

		Builder.advancement().parent(craftTeslaIngot)
				.display(BlockRegistry.TESLA_SYNTHESIZER.get(),
						Component.translatable("advancements.immersiveweapons.tesla_synthesizer.title"),
						Component.translatable("advancements.immersiveweapons.tesla_synthesizer.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.TESLA_SYNTHESIZER.get()))
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, "immersiveweapons:tesla_synthesizer");

		// Ventus Advancements
		AdvancementHolder obtainVentusShard = Builder.advancement().parent(root)
				.display(ItemRegistry.VENTUS_SHARD.get(),
						Component.translatable("advancements.immersiveweapons.ventus_shard.title"),
						Component.translatable("advancements.immersiveweapons.ventus_shard.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_SHARD.get()))
				.save(consumer, "immersiveweapons:ventus_shard");

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_SWORD.get(),
						Component.translatable("advancements.immersiveweapons.ventus_sword.title"),
						Component.translatable("advancements.immersiveweapons.ventus_sword.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_SWORD.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:ventus_sword");

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_PICKAXE.get(),
						Component.translatable("advancements.immersiveweapons.ventus_pickaxe.title"),
						Component.translatable("advancements.immersiveweapons.ventus_pickaxe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_PICKAXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:ventus_pickaxe");

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_AXE.get(),
						Component.translatable("advancements.immersiveweapons.ventus_axe.title"),
						Component.translatable("advancements.immersiveweapons.ventus_axe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_AXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:ventus_axe");

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_SHOVEL.get(),
						Component.translatable("advancements.immersiveweapons.ventus_shovel.title"),
						Component.translatable("advancements.immersiveweapons.ventus_shovel.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_SHOVEL.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:ventus_shovel");

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_HOE.get(),
						Component.translatable("advancements.immersiveweapons.ventus_hoe.title"),
						Component.translatable("advancements.immersiveweapons.ventus_hoe.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_HOE.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, "immersiveweapons:ventus_hoe");

		Builder.advancement().parent(obtainVentusShard)
				.display(BlockItemRegistry.VENTUS_ORE_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.ventus_tools.title"),
						Component.translatable("advancements.immersiveweapons.ventus_tools.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "ventus_sword"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "ventus_pickaxe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "ventus_axe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "ventus_shovel"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "ventus_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, "immersiveweapons:ventus_tools");

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_HELMET.get(),
						Component.translatable("advancements.immersiveweapons.ventus_armor.title"),
						Component.translatable("advancements.immersiveweapons.ventus_armor.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_HELMET.get(),
								ItemRegistry.VENTUS_CHESTPLATE.get(), ItemRegistry.VENTUS_LEGGINGS.get(),
								ItemRegistry.VENTUS_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, "immersiveweapons:ventus_armor");

		AdvancementHolder craftVentusStaffCore = Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_STAFF_CORE.get(),
						Component.translatable("advancements.immersiveweapons.ventus_staff_core.title"),
						Component.translatable("advancements.immersiveweapons.ventus_staff_core.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_STAFF_CORE.get()))
				.save(consumer, "immersiveweapons:ventus_staff_core");

		Builder.advancement().parent(craftVentusStaffCore)
				.display(ItemRegistry.VENTUS_STAFF.get(),
						Component.translatable("advancements.immersiveweapons.ventus_staff.title"),
						Component.translatable("advancements.immersiveweapons.ventus_staff.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_STAFF.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:ventus_staff");

		// Astral advancements
		AdvancementHolder obtainAstralCrystal = Builder.advancement().parent(root)
				.display(BlockRegistry.ASTRAL_CRYSTAL.get(),
						Component.translatable("advancements.immersiveweapons.astral_crystal.title"),
						Component.translatable("advancements.immersiveweapons.astral_crystal.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.ASTRAL_CRYSTAL.get()))
				.save(consumer, "immersiveweapons:astral_crystal");

		AdvancementHolder obtainAstralIngot = Builder.advancement().parent(obtainAstralCrystal)
				.display(ItemRegistry.ASTRAL_INGOT.get(),
						Component.translatable("advancements.immersiveweapons.astral_ingot.title"),
						Component.translatable("advancements.immersiveweapons.astral_ingot.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_INGOT.get()))
				.save(consumer, "immersiveweapons:astral_ingot");

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_SWORD.get(),
						Component.translatable("advancements.immersiveweapons.astral_sword.title"),
						Component.translatable("advancements.immersiveweapons.astral_sword.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_SWORD.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:astral_sword");

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_PICKAXE.get(),
						Component.translatable("advancements.immersiveweapons.astral_pickaxe.title"),
						Component.translatable("advancements.immersiveweapons.astral_pickaxe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_PICKAXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:astral_pickaxe");

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_AXE.get(),
						Component.translatable("advancements.immersiveweapons.astral_axe.title"),
						Component.translatable("advancements.immersiveweapons.astral_axe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_AXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:astral_axe");

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_SHOVEL.get(),
						Component.translatable("advancements.immersiveweapons.astral_shovel.title"),
						Component.translatable("advancements.immersiveweapons.astral_shovel.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_SHOVEL.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:astral_shovel");

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_HOE.get(),
						Component.translatable("advancements.immersiveweapons.astral_hoe.title"),
						Component.translatable("advancements.immersiveweapons.astral_hoe.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_HOE.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, "immersiveweapons:astral_hoe");

		Builder.advancement().parent(obtainAstralIngot)
				.display(BlockItemRegistry.ASTRAL_BLOCK_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.astral_tools.title"),
						Component.translatable("advancements.immersiveweapons.astral_tools.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "astral_sword"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "astral_pickaxe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "astral_axe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "astral_shovel"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "astral_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, "immersiveweapons:astral_tools");

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_HELMET.get(),
						Component.translatable("advancements.immersiveweapons.astral_armor.title"),
						Component.translatable("advancements.immersiveweapons.astral_armor.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_HELMET.get(),
								ItemRegistry.ASTRAL_CHESTPLATE.get(), ItemRegistry.ASTRAL_LEGGINGS.get(),
								ItemRegistry.ASTRAL_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, "immersiveweapons:astral_armor");

		// Starstorm advancements
		AdvancementHolder obtainStarstormCrystal = Builder.advancement().parent(root)
				.display(BlockRegistry.STARSTORM_CRYSTAL.get(),
						Component.translatable("advancements.immersiveweapons.starstorm_crystal.title"),
						Component.translatable("advancements.immersiveweapons.starstorm_crystal.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.STARSTORM_CRYSTAL.get()))
				.save(consumer, "immersiveweapons:starstorm_crystal");

		AdvancementHolder obtainStarstormIngot = Builder.advancement().parent(obtainStarstormCrystal)
				.display(ItemRegistry.STARSTORM_INGOT.get(),
						Component.translatable("advancements.immersiveweapons.starstorm_ingot.title"),
						Component.translatable("advancements.immersiveweapons.starstorm_ingot.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_INGOT.get()))
				.save(consumer, "immersiveweapons:starstorm_ingot");

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_SWORD.get(),
						Component.translatable("advancements.immersiveweapons.starstorm_sword.title"),
						Component.translatable("advancements.immersiveweapons.starstorm_sword.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_SWORD.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:starstorm_sword");

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_PICKAXE.get(),
						Component.translatable("advancements.immersiveweapons.starstorm_pickaxe.title"),
						Component.translatable("advancements.immersiveweapons.starstorm_pickaxe.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_PICKAXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:starstorm_pickaxe");

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_AXE.get(),
						Component.translatable("advancements.immersiveweapons.starstorm_axe.title"),
						Component.translatable("advancements.immersiveweapons.starstorm_axe.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_AXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:starstorm_axe");

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_SHOVEL.get(),
						Component.translatable("advancements.immersiveweapons.starstorm_shovel.title"),
						Component.translatable("advancements.immersiveweapons.starstorm_shovel.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_SHOVEL.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:starstorm_shovel");

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_HOE.get(),
						Component.translatable("advancements.immersiveweapons.starstorm_hoe.title"),
						Component.translatable("advancements.immersiveweapons.starstorm_hoe.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_HOE.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, "immersiveweapons:starstorm_hoe");

		Builder.advancement().parent(obtainStarstormIngot)
				.display(BlockItemRegistry.STARSTORM_BLOCK_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.starstorm_tools.title"),
						Component.translatable("advancements.immersiveweapons.starstorm_tools.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "starstorm_sword"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "starstorm_pickaxe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "starstorm_axe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "starstorm_shovel"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "starstorm_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, "immersiveweapons:starstorm_tools");

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_HELMET.get(),
						Component.translatable("advancements.immersiveweapons.starstorm_armor.title"),
						Component.translatable("advancements.immersiveweapons.starstorm_armor.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_HELMET.get(),
								ItemRegistry.STARSTORM_CHESTPLATE.get(), ItemRegistry.STARSTORM_LEGGINGS.get(),
								ItemRegistry.STARSTORM_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, "immersiveweapons:starstorm_armor");

		// Padded Leather advancements
		Builder.advancement().parent(root)
				.display(ItemRegistry.PADDED_LEATHER_HELMET.get(),
						Component.translatable("advancements.immersiveweapons.padded_leather_armor.title"),
						Component.translatable("advancements.immersiveweapons.padded_leather_armor.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.PADDED_LEATHER_HELMET.get(),
								ItemRegistry.PADDED_LEATHER_CHESTPLATE.get(), ItemRegistry.PADDED_LEATHER_LEGGINGS.get(),
								ItemRegistry.PADDED_LEATHER_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, "immersiveweapons:padded_leather_armor");

		// Tool advancements
		AdvancementHolder craftToolRod = Builder.advancement().parent(root)
				.display(ItemRegistry.WOODEN_TOOL_ROD.get(),
						Component.translatable("advancements.immersiveweapons.tool_rod.title"),
						Component.translatable("advancements.immersiveweapons.tool_rod.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WOODEN_TOOL_ROD.get()))
				.save(consumer, "immersiveweapons:tool_rod");

		Builder.advancement().parent(craftToolRod)
				.display(ItemRegistry.IRON_PIKE.get(),
						Component.translatable("advancements.immersiveweapons.pike.title"),
						Component.translatable("advancements.immersiveweapons.pike.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WOODEN_PIKE.get()))
				.addCriterion("hold1",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STONE_PIKE.get()))
				.addCriterion("hold2",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.GOLDEN_PIKE.get()))
				.addCriterion("hold3",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_PIKE.get()))
				.addCriterion("hold4",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.IRON_PIKE.get()))
				.addCriterion("hold5",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_PIKE.get()))
				.addCriterion("hold6",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.DIAMOND_PIKE.get()))
				.addCriterion("hold7",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.NETHERITE_PIKE.get()))
				.addCriterion("hold8",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_PIKE.get()))
				.addCriterion("hold9",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_PIKE.get()))
				.addCriterion("hold10",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_PIKE.get()))
				.addCriterion("hold11",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_PIKE.get()))
				.addCriterion("hold12",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_PIKE.get()))
				.requirements(Strategy.OR)
				.save(consumer, "immersiveweapons:pike");

		AdvancementHolder shards = Builder.advancement().parent(root)
				.display(ItemRegistry.STONE_SHARD.get(),
						Component.translatable("advancements.immersiveweapons.shards.title"),
						Component.translatable("advancements.immersiveweapons.shards.description"),
						null, AdvancementType.TASK, false, false, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_SHARD.get()))
				.addCriterion("hold1",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.OBSIDIAN_SHARD.get()))
				.addCriterion("hold2",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STONE_SHARD.get()))
				.addCriterion("hold3",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WOODEN_SHARD.get()))
				.addCriterion("hold4",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.DIAMOND_SHARD.get()))
				.addCriterion("hold5",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_SHARD.get()))
				.addCriterion("hold6",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_SHARD.get()))
				.requirements(Strategy.OR)
				.save(consumer, "immersiveweapons:shards");

		Builder.advancement().parent(shards)
				.display(ItemRegistry.WOODEN_SHARD.get(),
						Component.translatable("advancements.immersiveweapons.wooden_shard.title"),
						Component.translatable("advancements.immersiveweapons.wooden_shard.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WOODEN_SHARD.get()))
				.save(consumer, "immersiveweapons:wooden_shard");

		Builder.advancement().parent(shards)
				.display(ItemRegistry.STONE_SHARD.get(),
						Component.translatable("advancements.immersiveweapons.stone_shard.title"),
						Component.translatable("advancements.immersiveweapons.stone_shard.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STONE_SHARD.get()))
				.save(consumer, "immersiveweapons:stone_shard");

		Builder.advancement().parent(shards)
				.display(ItemRegistry.DIAMOND_SHARD.get(),
						Component.translatable("advancements.immersiveweapons.diamond_shard.title"),
						Component.translatable("advancements.immersiveweapons.diamond_shard.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.DIAMOND_SHARD.get()))
				.save(consumer, "immersiveweapons:diamond_shard");

		Builder.advancement().parent(shards)
				.display(ItemRegistry.OBSIDIAN_SHARD.get(),
						Component.translatable("advancements.immersiveweapons.obsidian_shard.title"),
						Component.translatable("advancements.immersiveweapons.obsidian_shard.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.OBSIDIAN_SHARD.get()))
				.save(consumer, "immersiveweapons:obsidian_shard");

		Builder.advancement().parent(root)
				.display(ItemRegistry.NETHERITE_ARROW.get(),
						Component.translatable("advancements.immersiveweapons.netherite_projectile.title"),
						Component.translatable("advancements.immersiveweapons.netherite_projectile.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.NETHERITE_ARROW.get()))
				.addCriterion("hold1",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.NETHERITE_MUSKET_BALL.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, "immersiveweapons:netherite_projectile");

		Builder.advancement().parent(root)
				.display(ItemRegistry.GOLDEN_MUSKET_BALL.get(),
						Component.translatable("advancements.immersiveweapons.musket_ball.title"),
						Component.translatable("advancements.immersiveweapons.musket_ball.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(IWItemTagGroups.MUSKET_BALLS).build()))
				.save(consumer, "immersiveweapons:musket_ball");

		Builder.advancement().parent(root)
				.display(BlockRegistry.AMMUNITION_TABLE.get(),
						Component.translatable("advancements.immersiveweapons.ammunition_table.title"),
						Component.translatable("advancements.immersiveweapons.ammunition_table.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.AMMUNITION_TABLE.get()))
				.save(consumer, "immersiveweapons:ammunition_table");

		Builder.advancement().parent(root)
				.display(BlockRegistry.STAR_FORGE_CONTROLLER.get(),
						Component.translatable("advancements.immersiveweapons.star_forge.title"),
						Component.translatable("advancements.immersiveweapons.star_forge.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(
								BlockRegistry.STAR_FORGE_CONTROLLER.get(),
								BlockRegistry.STAR_FORGE_BRICKS.get(),
								BlockRegistry.SOLAR_LENS.get(),
								Items.IRON_BARS
						))
				.save(consumer, "immersiveweapons:star_forge");

		AdvancementHolder smallPartsTable = Builder.advancement().parent(root)
				.display(BlockRegistry.SMALL_PARTS_TABLE.get(),
						Component.translatable("advancements.immersiveweapons.small_parts_table.title"),
						Component.translatable("advancements.immersiveweapons.small_parts_table.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.SMALL_PARTS_TABLE.get()))
				.save(consumer, "immersiveweapons:small_parts_table");

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.FLINTLOCK_PISTOL.get(),
						Component.translatable("advancements.immersiveweapons.flintlock_pistol.title"),
						Component.translatable("advancements.immersiveweapons.flintlock_pistol.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FLINTLOCK_PISTOL.get()))
				.save(consumer, "immersiveweapons:flintlock_pistol");

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.BLUNDERBUSS.get(),
						Component.translatable("advancements.immersiveweapons.blunderbuss.title"),
						Component.translatable("advancements.immersiveweapons.blunderbuss.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BLUNDERBUSS.get()))
				.save(consumer, "immersiveweapons:blunderbuss");

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.FLARE_GUN.get(),
						Component.translatable("advancements.immersiveweapons.flare_gun.title"),
						Component.translatable("advancements.immersiveweapons.flare_gun.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FLARE_GUN.get()))
				.save(consumer, "immersiveweapons:flare_gun");

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.MUSKET.get(),
						Component.translatable("advancements.immersiveweapons.musket.title"),
						Component.translatable("advancements.immersiveweapons.musket.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MUSKET.get()))
				.addCriterion("hold1",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MUSKET_SCOPE.get()))
				.requirements(Strategy.OR)
				.save(consumer, "immersiveweapons:musket");

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.HAND_CANNON.get(),
						Component.translatable("advancements.immersiveweapons.hand_cannon.title"),
						Component.translatable("advancements.immersiveweapons.hand_cannon.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.HAND_CANNON.get()))
				.save(consumer, "immersiveweapons:hand_cannon");

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.SMOKE_GRENADE.get(),
						Component.translatable("advancements.immersiveweapons.smoke_grenade.title"),
						Component.translatable("advancements.immersiveweapons.smoke_grenade.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SMOKE_GRENADE_GREEN.get()))
				.save(consumer, "immersiveweapons:smoke_grenade");

		AdvancementHolder craftAlcohol = Builder.advancement().parent(root)
				.display(ItemRegistry.BOTTLE_OF_ALCOHOL.get(),
						Component.translatable("advancements.immersiveweapons.bottle_of_alcohol.title"),
						Component.translatable("advancements.immersiveweapons.bottle_of_alcohol.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BOTTLE_OF_ALCOHOL.get()))
				.save(consumer, "immersiveweapons:bottle_of_alcohol");
		Builder.advancement().parent(craftAlcohol)
				.display(ItemRegistry.MOLOTOV_COCKTAIL.get(),
						Component.translatable("advancements.immersiveweapons.molotov_cocktail.title"),
						Component.translatable("advancements.immersiveweapons.molotov_cocktail.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLOTOV_COCKTAIL.get()))
				.save(consumer, "immersiveweapons:molotov_cocktail");


		AdvancementHolder craftBandage = Builder.advancement().parent(root)
				.display(ItemRegistry.BANDAGE.get(),
						Component.translatable("advancements.immersiveweapons.bandage.title"),
						Component.translatable("advancements.immersiveweapons.bandage.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BANDAGE.get()))
				.save(consumer, "immersiveweapons:bandage");
		Builder.advancement().parent(craftBandage)
				.display(ItemRegistry.FIRST_AID_KIT.get(),
						Component.translatable("advancements.immersiveweapons.first_aid_kit.title"),
						Component.translatable("advancements.immersiveweapons.first_aid_kit.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIRST_AID_KIT.get()))
				.save(consumer, "immersiveweapons:first_aid_kit");


		Builder.advancement().parent(root)
				.display(ItemRegistry.IRON_GAUNTLET.get(),
						Component.translatable("advancements.immersiveweapons.gauntlet.title"),
						Component.translatable("advancements.immersiveweapons.gauntlet.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WOODEN_GAUNTLET.get()))
				.addCriterion("hold1",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STONE_GAUNTLET.get()))
				.addCriterion("hold2",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.GOLDEN_GAUNTLET.get()))
				.addCriterion("hold3",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_GAUNTLET.get()))
				.addCriterion("hold4",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.IRON_GAUNTLET.get()))
				.addCriterion("hold5",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_GAUNTLET.get()))
				.addCriterion("hold6",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.DIAMOND_GAUNTLET.get()))
				.addCriterion("hold7",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.NETHERITE_GAUNTLET.get()))
				.addCriterion("hold8",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_GAUNTLET.get()))
				.addCriterion("hold9",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_GAUNTLET.get()))
				.addCriterion("hold10",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_GAUNTLET.get()))
				.addCriterion("hold11",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_GAUNTLET.get()))
				.addCriterion("hold12",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_GAUNTLET.get()))
				.requirements(Strategy.OR)
				.save(consumer, "immersiveweapons:gauntlet");

		// General ingot advancements
		AdvancementHolder ingots = Builder.advancement().parent(root)
				.display(Items.IRON_INGOT,
						Component.translatable("advancements.immersiveweapons.ingots.title"),
						Component.translatable("advancements.immersiveweapons.ingots.description"),
						null, AdvancementType.TASK, false, false, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(
								ItemPredicate.Builder.item().of(Tags.Items.INGOTS).build())
				)
				.save(consumer, "immersiveweapons:ingots");

		Builder.advancement().parent(root)
				.display(Items.GOLD_NUGGET,
						Component.translatable("advancements.immersiveweapons.nuggets.title"),
						Component.translatable("advancements.immersiveweapons.nuggets.description"),
						null, AdvancementType.TASK, false, false, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(
								ItemPredicate.Builder.item().of(Tags.Items.NUGGETS).build())
				)
				.save(consumer, "immersiveweapons:nuggets");

		// Copper advancements
		AdvancementHolder copperIngot = Builder.advancement().parent(ingots)
				.display(Items.COPPER_INGOT,
						Component.translatable("advancements.immersiveweapons.copper_ingot.title"),
						Component.translatable("advancements.immersiveweapons.copper_ingot.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
				.save(consumer, "immersiveweapons:copper_ingot");

		Builder.advancement().parent(copperIngot)
				.display(ItemRegistry.COPPER_SWORD.get(),
						Component.translatable("advancements.immersiveweapons.copper_sword.title"),
						Component.translatable("advancements.immersiveweapons.copper_sword.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_SWORD.get()))
				.save(consumer, "immersiveweapons:copper_sword");

		Builder.advancement().parent(copperIngot)
				.display(ItemRegistry.COPPER_PICKAXE.get(),
						Component.translatable("advancements.immersiveweapons.copper_pickaxe.title"),
						Component.translatable("advancements.immersiveweapons.copper_pickaxe.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_PICKAXE.get()))
				.save(consumer, "immersiveweapons:copper_pickaxe");

		Builder.advancement().parent(copperIngot)
				.display(ItemRegistry.COPPER_AXE.get(),
						Component.translatable("advancements.immersiveweapons.copper_axe.title"),
						Component.translatable("advancements.immersiveweapons.copper_axe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_AXE.get()))
				.save(consumer, "immersiveweapons:copper_axe");

		Builder.advancement().parent(copperIngot)
				.display(ItemRegistry.COPPER_SHOVEL.get(),
						Component.translatable("advancements.immersiveweapons.copper_shovel.title"),
						Component.translatable("advancements.immersiveweapons.copper_shovel.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_SHOVEL.get()))
				.save(consumer, "immersiveweapons:copper_shovel");

		Builder.advancement().parent(copperIngot)
				.display(ItemRegistry.COPPER_HOE.get(),
						Component.translatable("advancements.immersiveweapons.copper_hoe.title"),
						Component.translatable("advancements.immersiveweapons.copper_hoe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_HOE.get()))
				.save(consumer, "immersiveweapons:copper_hoe");

		Builder.advancement().parent(copperIngot)
				.display(Items.COPPER_BLOCK,
						Component.translatable("advancements.immersiveweapons.copper_tools.title"),
						Component.translatable("advancements.immersiveweapons.copper_tools.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_sword"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_pickaxe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_axe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_shovel"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "copper_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(25))
				.save(consumer, "immersiveweapons:copper_tools");

		// Cobalt advancements
		AdvancementHolder cobaltIngot = Builder.advancement().parent(ingots)
				.display(ItemRegistry.COBALT_INGOT.get(),
						Component.translatable("advancements.immersiveweapons.cobalt_ingot.title"),
						Component.translatable("advancements.immersiveweapons.cobalt_ingot.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_INGOT.get()))
				.save(consumer, "immersiveweapons:cobalt_ingot");

		Builder.advancement().parent(cobaltIngot)
				.display(ItemRegistry.COBALT_SWORD.get(),
						Component.translatable("advancements.immersiveweapons.cobalt_sword.title"),
						Component.translatable("advancements.immersiveweapons.cobalt_sword.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_SWORD.get()))
				.save(consumer, "immersiveweapons:cobalt_sword");

		Builder.advancement().parent(cobaltIngot)
				.display(ItemRegistry.COBALT_PICKAXE.get(),
						Component.translatable("advancements.immersiveweapons.cobalt_pickaxe.title"),
						Component.translatable("advancements.immersiveweapons.cobalt_pickaxe.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_PICKAXE.get()))
				.save(consumer, "immersiveweapons:cobalt_pickaxe");

		Builder.advancement().parent(cobaltIngot)
				.display(ItemRegistry.COBALT_AXE.get(),
						Component.translatable("advancements.immersiveweapons.cobalt_axe.title"),
						Component.translatable("advancements.immersiveweapons.cobalt_axe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_AXE.get()))
				.save(consumer, "immersiveweapons:cobalt_axe");

		Builder.advancement().parent(cobaltIngot)
				.display(ItemRegistry.COBALT_SHOVEL.get(),
						Component.translatable("advancements.immersiveweapons.cobalt_shovel.title"),
						Component.translatable("advancements.immersiveweapons.cobalt_shovel.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_SHOVEL.get()))
				.save(consumer, "immersiveweapons:cobalt_shovel");

		Builder.advancement().parent(cobaltIngot)
				.display(ItemRegistry.COBALT_HOE.get(),
						Component.translatable("advancements.immersiveweapons.cobalt_hoe.title"),
						Component.translatable("advancements.immersiveweapons.cobalt_hoe.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_HOE.get()))
				.save(consumer, "immersiveweapons:cobalt_hoe");

		Builder.advancement().parent(cobaltIngot)
				.display(BlockItemRegistry.COBALT_BLOCK_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.cobalt_tools.title"),
						Component.translatable("advancements.immersiveweapons.cobalt_tools.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "cobalt_sword"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "cobalt_pickaxe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "cobalt_axe"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "cobalt_shovel"), true
										).checkAdvancementDone(
												new ResourceLocation(ImmersiveWeapons.MOD_ID, "cobalt_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, "immersiveweapons:cobalt_tools");

		// Other ingots, without families
		Builder.advancement().parent(ingots)
				.display(Items.GOLD_INGOT,
						Component.translatable("advancements.immersiveweapons.gold_ingot.title"),
						Component.translatable("advancements.immersiveweapons.gold_ingot.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
				.save(consumer, "immersiveweapons:gold_ingot");

		Builder.advancement().parent(ingots)
				.display(Items.NETHERITE_INGOT,
						Component.translatable("advancements.immersiveweapons.netherite_ingot.title"),
						Component.translatable("advancements.immersiveweapons.netherite_ingot.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
				.save(consumer, "immersiveweapons:netherite_ingot");

		// Entity discovery advancements
		AdvancementHolder entity_discovery = Builder.advancement().parent(root)
				.display(Items.CREEPER_HEAD,
						Component.translatable("advancements.immersiveweapons.entity_discovery.title"),
						Component.translatable("advancements.immersiveweapons.entity_discovery.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("discover_minuteman", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.MINUTEMAN_ENTITY.get()))
				.addCriterion("discover_field_medic", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.FIELD_MEDIC_ENTITY.get()))
				.addCriterion("discover_dying_soldier", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.DYING_SOLDIER_ENTITY.get()))
				.addCriterion("discover_wandering_warrior", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.WANDERING_WARRIOR_ENTITY.get()))
				.addCriterion("discover_hans", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.HANS_ENTITY.get()))
				.addCriterion("discover_super_hans", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.SUPER_HANS_ENTITY.get()))
				.addCriterion("discover_lava_revenant", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.LAVA_REVENANT_ENTITY.get()))
				.addCriterion("discover_rock_spider", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.ROCK_SPIDER_ENTITY.get()))
				.addCriterion("discover_celestial_tower", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.CELESTIAL_TOWER_ENTITY.get()))
				.addCriterion("discover_starmite", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.STARMITE_ENTITY.get()))
				.addCriterion("discover_storm_creeper", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.STORM_CREEPER_ENTITY.get()))
				.addCriterion("discover_evil_eye", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.EVIL_EYE_ENTITY.get()))
				.addCriterion("discover_star_wolf", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.STAR_WOLF_ENTITY.get()))
				.addCriterion("discover_skygazer", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.SKYGAZER_ENTITY.get()))
				.addCriterion("discover_skeleton_merchant", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.SKELETON_MERCHANT_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, "immersiveweapons:entity_discovery");

		Builder.advancement().parent(entity_discovery)
				.display(BlockItemRegistry.MINUTEMAN_HEAD_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.discover_minuteman.title"),
						Component.translatable("advancements.immersiveweapons.discover_minuteman.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.MINUTEMAN_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_minuteman");

		Builder.advancement().parent(entity_discovery)
				.display(BlockItemRegistry.FIELD_MEDIC_HEAD_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.discover_field_medic.title"),
						Component.translatable("advancements.immersiveweapons.discover_field_medic.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.FIELD_MEDIC_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_field_medic");

		Builder.advancement().parent(entity_discovery)
				.display(BlockItemRegistry.DYING_SOLDIER_HEAD_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.discover_dying_soldier.title"),
						Component.translatable("advancements.immersiveweapons.discover_dying_soldier.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.DYING_SOLDIER_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_dying_soldier");

		Builder.advancement().parent(entity_discovery)
				.display(BlockItemRegistry.WANDERING_WARRIOR_HEAD_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.discover_wandering_warrior.title"),
						Component.translatable("advancements.immersiveweapons.discover_wandering_warrior.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.WANDERING_WARRIOR_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_wandering_warrior");

		Builder.advancement().parent(entity_discovery)
				.display(BlockItemRegistry.HANS_HEAD_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.discover_hans.title"),
						Component.translatable("advancements.immersiveweapons.discover_hans.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.HANS_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_hans");

		Builder.advancement().parent(entity_discovery)
				.display(BlockItemRegistry.HANS_HEAD_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.discover_super_hans.title"),
						Component.translatable("advancements.immersiveweapons.discover_super_hans.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.SUPER_HANS_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_super_hans");

		Builder.advancement().parent(entity_discovery)
				.display(ItemRegistry.SULFUR.get(),
						Component.translatable("advancements.immersiveweapons.discover_lava_revenant.title"),
						Component.translatable("advancements.immersiveweapons.discover_lava_revenant.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.LAVA_REVENANT_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_lava_revenant");

		Builder.advancement().parent(entity_discovery)
				.display(Items.SPIDER_EYE,
						Component.translatable("advancements.immersiveweapons.discover_rock_spider.title"),
						Component.translatable("advancements.immersiveweapons.discover_rock_spider.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.ROCK_SPIDER_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_rock_spider");

		Builder.advancement().parent(entity_discovery)
				.display(BlockRegistry.STARSTORM_CRYSTAL.get(),
						Component.translatable("advancements.immersiveweapons.discover_starmite.title"),
						Component.translatable("advancements.immersiveweapons.discover_starmite.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.STARMITE_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_starmite");

		Builder.advancement().parent(entity_discovery)
				.display(BlockItemRegistry.STORM_CREEPER_HEAD_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.discover_storm_creeper.title"),
						Component.translatable("advancements.immersiveweapons.discover_storm_creeper.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.STORM_CREEPER_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_storm_creeper");

		Builder.advancement().parent(entity_discovery)
				.display(ItemRegistry.BROKEN_LENS.get(),
						Component.translatable("advancements.immersiveweapons.discover_evil_eye.title"),
						Component.translatable("advancements.immersiveweapons.discover_evil_eye.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.EVIL_EYE_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_evil_eye");

		Builder.advancement().parent(entity_discovery)
				.display(Items.BONE,
						Component.translatable("advancements.immersiveweapons.discover_star_wolf.title"),
						Component.translatable("advancements.immersiveweapons.discover_star_wolf.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.STAR_WOLF_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_star_wolf");

		Builder.advancement().parent(entity_discovery)
				.display(Items.BOOK,
						Component.translatable("advancements.immersiveweapons.discover_skygazer.title"),
						Component.translatable("advancements.immersiveweapons.discover_skygazer.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.SKYGAZER_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_skygazer");

		Builder.advancement().parent(entity_discovery)
				.display(BlockItemRegistry.SKELETON_MERCHANT_HEAD_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.discover_skeleton_merchant.title"),
						Component.translatable("advancements.immersiveweapons.discover_skeleton_merchant.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.SKELETON_MERCHANT_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_skeleton_merchant");


		AdvancementHolder celestial_tower_discovery = Builder.advancement().parent(entity_discovery)
				.display(ItemRegistry.CELESTIAL_FRAGMENT.get(),
						Component.translatable("advancements.immersiveweapons.discover_celestial_tower.title"),
						Component.translatable("advancements.immersiveweapons.discover_celestial_tower.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.CELESTIAL_TOWER_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, "immersiveweapons:discover_celestial_tower");

		Builder.advancement().parent(celestial_tower_discovery)
				.display(BlockItemRegistry.CELESTIAL_LANTERN_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.tiltros.celestial_lantern.title"),
						Component.translatable("advancements.immersiveweapons.tiltros.celestial_lantern.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockItemRegistry.CELESTIAL_LANTERN_ITEM.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, "immersiveweapons:celestial_lantern");

		Builder.advancement().parent(celestial_tower_discovery)
				.display(ItemRegistry.METEOR_STAFF.get(),
						Component.translatable("advancements.immersiveweapons.meteor_staff.title"),
						Component.translatable("advancements.immersiveweapons.meteor_staff.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.METEOR_STAFF.get()))
				.rewards(AdvancementRewards.Builder.experience(75))
				.save(consumer, "immersiveweapons:meteor_staff");

		Builder.advancement().parent(celestial_tower_discovery)
				.display(ItemRegistry.CURSED_SIGHT_STAFF.get(),
						Component.translatable("advancements.immersiveweapons.cursed_sight_staff.title"),
						Component.translatable("advancements.immersiveweapons.cursed_sight_staff.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CURSED_SIGHT_STAFF.get()))
				.rewards(AdvancementRewards.Builder.experience(75))
				.save(consumer, "immersiveweapons:cursed_sight_staff");

		// Accessory advancements
		Builder.advancement().parent(root)
				.display(ItemRegistry.SATCHEL.get(),
						Component.translatable("advancements.immersiveweapons.accessories.title"),
						Component.translatable("advancements.immersiveweapons.accessories.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(
								ItemPredicate.Builder.item().of(IWItemTagGroups.ACCESSORIES).build()))
				.rewards(AdvancementRewards.Builder.experience(15))
				.save(consumer, "immersiveweapons:accessories");
		Builder.advancement().parent(root)
				.display(ItemRegistry.BLOODY_SACRIFICE.get(),
						Component.translatable("advancements.immersiveweapons.bloody_sacrifice.title"),
						Component.translatable("advancements.immersiveweapons.bloody_sacrifice.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BLOODY_SACRIFICE.get()))
				.rewards(AdvancementRewards.Builder.experience(15))
				.save(consumer, "immersiveweapons:bloody_sacrifice");

		// Other advancements
		Builder.advancement().parent(root)
				.display(ItemRegistry.USED_SYRINGE.get(),
						Component.translatable("advancements.immersiveweapons.used_syringe.title"),
						Component.translatable("advancements.immersiveweapons.used_syringe.description"),
						null, AdvancementType.CHALLENGE, true, true, true)
				.addCriterion("hold",
						KilledTrigger.TriggerInstance.entityKilledPlayer(EntityPredicate.Builder.entity(),
								DamageSourcePredicate.Builder.damageType()
										.source(EntityPredicate.Builder.entity()
												.equipment(EntityEquipmentPredicate.Builder.equipment()
														.mainhand(ItemPredicate.Builder.item()
																.of(ItemRegistry.USED_SYRINGE.get()))
														.build()))))
				.save(consumer, "immersiveweapons:used_syringe");

		Builder.advancement().parent(root)
				.display(ItemRegistry.KILL_COUNTER.get(),
						Component.translatable("advancements.immersiveweapons.kill_counter.title"),
						Component.translatable("advancements.immersiveweapons.kill_counter.description"),
						null, AdvancementType.CHALLENGE, true, true, true)
				.addCriterion("", CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance()))
				.save(consumer, "immersiveweapons:kill_counter");

		Builder.advancement().parent(root)
				.display(ItemRegistry.STARSTORM_ARROW.get(),
						Component.translatable("advancements.immersiveweapons.overkill.title"),
						Component.translatable("advancements.immersiveweapons.overkill.description"),
						null, AdvancementType.CHALLENGE, true, true, true)
				.addCriterion("", CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, "immersiveweapons:overkill");

		Builder.advancement().parent(root)
				.display(BlockRegistry.BEAR_TRAP.get(),
						Component.translatable("advancements.immersiveweapons.traps.title"),
						Component.translatable("advancements.immersiveweapons.traps.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.BEAR_TRAP.get()))
				.addCriterion("hold1",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.PUNJI_STICKS.get()))
				.addCriterion("hold2",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.LANDMINE.get()))
				.addCriterion("hold3",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.BEAR_TRAP.get()))
				.addCriterion("hold4",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.BARBED_WIRE.get()))
				.addCriterion("hold5",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.SPIKE_TRAP.get()))
				.addCriterion("hold6",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.PITFALL.get()))
				.addCriterion("hold7",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.WOODEN_SPIKES.get()))
				.requirements(Strategy.OR)
				.save(consumer, "immersiveweapons:traps");

		Builder.advancement().parent(root)
				.display(Items.OAK_PLANKS,
						Component.translatable("advancements.immersiveweapons.planks.title"),
						Component.translatable("advancements.immersiveweapons.planks.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
								.of(ItemTags.PLANKS).build()))
				.save(consumer, "immersiveweapons:planks");

		Builder.advancement().parent(root)
				.display(BlockItemRegistry.MUD_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.mud.title"),
						Component.translatable("advancements.immersiveweapons.mud.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
								.of(BlockItemRegistry.MUD_ITEM.get()).build()))
				.save(consumer, "immersiveweapons:mud");

		Builder.advancement().parent(root)
				.display(Items.BAMBOO,
						Component.translatable("advancements.immersiveweapons.bamboo.title"),
						Component.translatable("advancements.immersiveweapons.bamboo.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
								.of(Items.BAMBOO).build()))
				.save(consumer, "immersiveweapons:bamboo");

		Builder.advancement().parent(root)
				.display(BlockItemRegistry.CLOUD_MARBLE_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.cloud_marble.title"),
						Component.translatable("advancements.immersiveweapons.cloud_marble.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
								.of(BlockItemRegistry.CLOUD_MARBLE_ITEM.get()).build()))
				.save(consumer, "immersiveweapons:cloud_marble");

		Builder.advancement().parent(root)
				.display(BlockItemRegistry.BIOHAZARD_BOX_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.biohazard_box.title"),
						Component.translatable("advancements.immersiveweapons.biohazard_box.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
								.of(BlockItemRegistry.BIOHAZARD_BOX_ITEM.get()).build()))
				.save(consumer, "immersiveweapons:biohazard_box");

		Builder.advancement().parent(root)
				.display(BlockItemRegistry.CLOUD_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.cloud.title"),
						Component.translatable("advancements.immersiveweapons.cloud.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						PlayerTrigger.TriggerInstance.walkOnBlockWithEquipment(BlockRegistry.CLOUD.get(),
								Items.AIR))
				.save(consumer, "immersiveweapons:cloud");

		Builder.advancement().parent(root)
				.display(ItemRegistry.SCULK_STAFF.get(),
						Component.translatable("advancements.immersiveweapons.sculk_staff.title"),
						Component.translatable("advancements.immersiveweapons.sculk_staff.description"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SCULK_STAFF.get()))
				.rewards(AdvancementRewards.Builder.experience(75))
				.save(consumer, "immersiveweapons:sculk_staff");

		// Battlefield advancements
		AdvancementHolder discover_battlefield = Builder.advancement().parent(root)
				.display(Blocks.SKELETON_SKULL,
						Component.translatable("advancements.immersiveweapons.battlefield.title"),
						Component.translatable("advancements.immersiveweapons.battlefield.description"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("visit",
						PlayerTrigger.TriggerInstance.located(
								LocationPredicate.Builder.inBiome(IWBiomes.BATTLEFIELD)))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, "immersiveweapons:battlefield");

		Builder.advancement().parent(discover_battlefield)
				.display(BlockItemRegistry.MINUTEMAN_STATUE_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.minuteman_statue.title"),
						Component.translatable("advancements.immersiveweapons.minuteman_statue.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold", InventoryChangeTrigger.TriggerInstance.hasItems(
						BlockItemRegistry.MINUTEMAN_STATUE_ITEM.get()))
				.save(consumer, "immersiveweapons:minuteman_statue");

		Builder.advancement().parent(discover_battlefield)
				.display(BlockItemRegistry.MEDIC_STATUE_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.medic_statue.title"),
						Component.translatable("advancements.immersiveweapons.medic_statue.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold", InventoryChangeTrigger.TriggerInstance.hasItems(
						BlockItemRegistry.MEDIC_STATUE_ITEM.get()))
				.save(consumer, "immersiveweapons:medic_statue");

		// Tiltros advancements
		AdvancementHolder tiltros_portal = Builder.advancement().parent(root)
				.display(BlockRegistry.TILTROS_PORTAL_FRAME.get(),
						Component.translatable("advancements.immersiveweapons.tiltros.tiltros_portal.title"),
						Component.translatable("advancements.immersiveweapons.tiltros.tiltros_portal.description"),
						null, AdvancementType.TASK, true, true, true)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.TILTROS_PORTAL_FRAME.get()))
				.requirements(Strategy.AND)
				.save(consumer, "immersiveweapons:warrior_statue");

		AdvancementHolder azul_keystone = Builder.advancement().parent(tiltros_portal)
				.display(ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get(),
						Component.translatable("advancements.immersiveweapons.tiltros.azul_keystone.title"),
						Component.translatable("advancements.immersiveweapons.tiltros.azul_keystone.description"),
						null, AdvancementType.TASK, true, true, true)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.AZUL_KEYSTONE.get()))
				.save(consumer, "immersiveweapons:azul_keystone");

		Builder.advancement().parent(azul_keystone)
				.display(BlockItemRegistry.MOONGLOW_ITEM.get(),
						Component.translatable("advancements.immersiveweapons.tiltros.biome.title"),
						Component.translatable("advancements.immersiveweapons.tiltros.biome.description"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("visit",
						PlayerTrigger.TriggerInstance.located(
								LocationPredicate.Builder.inDimension(IWDimensions.TILTROS)))
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, "immersiveweapons:tiltros");
	}
}