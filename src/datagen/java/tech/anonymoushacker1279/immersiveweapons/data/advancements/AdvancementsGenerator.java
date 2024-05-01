package tech.anonymoushacker1279.immersiveweapons.data.advancements;

import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement.Builder;
import net.minecraft.advancements.*;
import net.minecraft.advancements.AdvancementRequirements.Strategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.advancement.EntityDiscoveredTrigger;
import tech.anonymoushacker1279.immersiveweapons.data.biomes.IWBiomes;
import tech.anonymoushacker1279.immersiveweapons.data.dimensions.DimensionGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class AdvancementsGenerator extends AdvancementProvider {

	static Provider PROVIDER;

	public AdvancementsGenerator(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper exFileHelper) {
		super(output, provider, exFileHelper, List.of((lookup, consumer, existingFileHelper) -> registerAdvancements(consumer)));

		try {
			PROVIDER = provider.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	public static void registerAdvancements(Consumer<AdvancementHolder> consumer) {
		// Root advancement
		AdvancementHolder root = Builder.advancement()
				.display(ItemRegistry.TESLA_SWORD.get(),
						createTitle("root").withStyle(ChatFormatting.RED),
						createDescription("root"),
						new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/block/red_stained_bulletproof_glass.png"),
						AdvancementType.TASK, false, false, false)
				.addCriterion("exist",
						PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inDimension(Level.OVERWORLD)))
				.save(consumer, prefixString("root"));

		// Molten advancements
		AdvancementHolder obtainMoltenShard = Builder.advancement().parent(root)
				.display(ItemRegistry.MOLTEN_SHARD.get(),
						createTitle("molten_shard"),
						createDescription("molten_shard"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_SHARD.get()))
				.save(consumer, prefixString("molten_shard"));

		AdvancementHolder smeltMoltenIngot = Builder.advancement().parent(obtainMoltenShard)
				.display(ItemRegistry.MOLTEN_INGOT.get(),
						createTitle("molten_ingot"),
						createDescription("molten_ingot"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_INGOT.get()))
				.save(consumer, prefixString("molten_ingot"));

		Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_SWORD.get(),
						createTitle("molten_sword"),
						createDescription("molten_sword"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_SWORD.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("molten_sword"));

		Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_PICKAXE.get(),
						createTitle("molten_pickaxe"),
						createDescription("molten_pickaxe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_PICKAXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("molten_pickaxe"));

		Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_AXE.get(),
						createTitle("molten_axe"),
						createDescription("molten_axe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_AXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("molten_axe"));

		Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_SHOVEL.get(),
						createTitle("molten_shovel"),
						createDescription("molten_shovel"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_SHOVEL.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("molten_shovel"));

		Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_HOE.get(),
						createTitle("molten_hoe"),
						createDescription("molten_hoe"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_HOE.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, prefixString("molten_hoe"));

		Builder.advancement().parent(smeltMoltenIngot)
				.display(BlockItemRegistry.MOLTEN_BLOCK_ITEM.get(),
						createTitle("molten_tools"),
						createDescription("molten_tools"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												prefixRL("molten_sword"), true
										).checkAdvancementDone(
												prefixRL("molten_pickaxe"), true
										).checkAdvancementDone(
												prefixRL("molten_axe"), true
										).checkAdvancementDone(
												prefixRL("molten_shovel"), true
										).checkAdvancementDone(
												prefixRL("molten_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, prefixString("molten_tools"));

		AdvancementHolder moltenArmor = Builder.advancement().parent(smeltMoltenIngot)
				.display(ItemRegistry.MOLTEN_HELMET.get(),
						createTitle("molten_armor"),
						createDescription("molten_armor"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_HELMET.get(),
								ItemRegistry.MOLTEN_CHESTPLATE.get(), ItemRegistry.MOLTEN_LEGGINGS.get(),
								ItemRegistry.MOLTEN_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, prefixString("molten_armor"));

		Builder.advancement().parent(moltenArmor)
				.display(Items.LAVA_BUCKET,
						createTitle("swim_in_lava"),
						createDescription("swim_in_lava"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLTEN_HELMET.get(),
								ItemRegistry.MOLTEN_CHESTPLATE.get(), ItemRegistry.MOLTEN_LEGGINGS.get(),
								ItemRegistry.MOLTEN_BOOTS.get()))
				.addCriterion("swim", EnterBlockTrigger.TriggerInstance.entersBlock(Blocks.LAVA))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("swim_in_lava"));

		// Tesla Advancements
		AdvancementHolder craftConductiveAlloy = Builder.advancement().parent(root)
				.display(ItemRegistry.CONDUCTIVE_ALLOY.get(),
						createTitle("conductive_alloy"),
						createDescription("conductive_alloy"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CONDUCTIVE_ALLOY.get()))
				.save(consumer, prefixString("conductive_alloy"));

		AdvancementHolder obtainElectricIngot = Builder.advancement().parent(craftConductiveAlloy)
				.display(ItemRegistry.ELECTRIC_INGOT.get(),
						createTitle("electric_ingot"),
						createDescription("electric_ingot"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ELECTRIC_INGOT.get()))
				.save(consumer, prefixString("electric_ingot"));

		AdvancementHolder craftTeslaIngot = Builder.advancement().parent(obtainElectricIngot)
				.display(ItemRegistry.TESLA_INGOT.get(),
						createTitle("tesla_ingot"),
						createDescription("tesla_ingot"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_INGOT.get()))
				.save(consumer, prefixString("tesla_ingot"));

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_SWORD.get(),
						createTitle("tesla_sword"),
						createDescription("tesla_sword"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_SWORD.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("tesla_sword"));

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_PICKAXE.get(),
						createTitle("tesla_pickaxe"),
						createDescription("tesla_pickaxe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_PICKAXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("tesla_pickaxe"));

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_AXE.get(),
						createTitle("tesla_axe"),
						createDescription("tesla_axe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_AXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("tesla_axe"));

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_SHOVEL.get(),
						createTitle("tesla_shovel"),
						createDescription("tesla_shovel"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_SHOVEL.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("tesla_shovel"));

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_HOE.get(),
						createTitle("tesla_hoe"),
						createDescription("tesla_hoe"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_HOE.get()))
				.rewards(AdvancementRewards.Builder.experience(65))
				.save(consumer, prefixString("tesla_hoe"));

		Builder.advancement().parent(craftTeslaIngot)
				.display(BlockItemRegistry.TESLA_BLOCK_ITEM.get(),
						createTitle("tesla_tools"),
						createDescription("tesla_tools"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												prefixRL("tesla_sword"), true
										).checkAdvancementDone(
												prefixRL("tesla_pickaxe"), true
										).checkAdvancementDone(
												prefixRL("tesla_axe"), true
										).checkAdvancementDone(
												prefixRL("tesla_shovel"), true
										).checkAdvancementDone(
												prefixRL("tesla_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, prefixString("tesla_tools"));

		Builder.advancement().parent(craftTeslaIngot)
				.display(ItemRegistry.TESLA_HELMET.get(),
						createTitle("tesla_armor"),
						createDescription("tesla_armor"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.TESLA_HELMET.get(),
								ItemRegistry.TESLA_CHESTPLATE.get(), ItemRegistry.TESLA_LEGGINGS.get(),
								ItemRegistry.TESLA_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, prefixString("tesla_armor"));

		Builder.advancement().parent(craftTeslaIngot)
				.display(BlockRegistry.TESLA_SYNTHESIZER.get(),
						createTitle("tesla_synthesizer"),
						createDescription("tesla_synthesizer"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.TESLA_SYNTHESIZER.get()))
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, prefixString("tesla_synthesizer"));

		// Ventus Advancements
		AdvancementHolder obtainVentusShard = Builder.advancement().parent(root)
				.display(ItemRegistry.VENTUS_SHARD.get(),
						createTitle("ventus_shard"),
						createDescription("ventus_shard"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_SHARD.get()))
				.save(consumer, prefixString("ventus_shard"));

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_SWORD.get(),
						createTitle("ventus_sword"),
						createDescription("ventus_sword"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_SWORD.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("ventus_sword"));

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_PICKAXE.get(),
						createTitle("ventus_pickaxe"),
						createDescription("ventus_pickaxe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_PICKAXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("ventus_pickaxe"));

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_AXE.get(),
						createTitle("ventus_axe"),
						createDescription("ventus_axe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_AXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("ventus_axe"));

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_SHOVEL.get(),
						createTitle("ventus_shovel"),
						createDescription("ventus_shovel"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_SHOVEL.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("ventus_shovel"));

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_HOE.get(),
						createTitle("ventus_hoe"),
						createDescription("ventus_hoe"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_HOE.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, prefixString("ventus_hoe"));

		Builder.advancement().parent(obtainVentusShard)
				.display(BlockItemRegistry.VENTUS_ORE_ITEM.get(),
						createTitle("ventus_tools"),
						createDescription("ventus_tools"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												prefixRL("ventus_sword"), true
										).checkAdvancementDone(
												prefixRL("ventus_pickaxe"), true
										).checkAdvancementDone(
												prefixRL("ventus_axe"), true
										).checkAdvancementDone(
												prefixRL("ventus_shovel"), true
										).checkAdvancementDone(
												prefixRL("ventus_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, prefixString("ventus_tools"));

		Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_HELMET.get(),
						createTitle("ventus_armor"),
						createDescription("ventus_armor"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_HELMET.get(),
								ItemRegistry.VENTUS_CHESTPLATE.get(), ItemRegistry.VENTUS_LEGGINGS.get(),
								ItemRegistry.VENTUS_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, prefixString("ventus_armor"));

		AdvancementHolder craftVentusStaffCore = Builder.advancement().parent(obtainVentusShard)
				.display(ItemRegistry.VENTUS_STAFF_CORE.get(),
						createTitle("ventus_staff_core"),
						createDescription("ventus_staff_core"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_STAFF_CORE.get()))
				.save(consumer, prefixString("ventus_staff_core"));

		Builder.advancement().parent(craftVentusStaffCore)
				.display(ItemRegistry.VENTUS_STAFF.get(),
						createTitle("ventus_staff"),
						createDescription("ventus_staff"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.VENTUS_STAFF.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("ventus_staff"));

		// Astral advancements
		AdvancementHolder obtainAstralCrystal = Builder.advancement().parent(root)
				.display(BlockRegistry.ASTRAL_CRYSTAL.get(),
						createTitle("astral_crystal"),
						createDescription("astral_crystal"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.ASTRAL_CRYSTAL.get()))
				.save(consumer, prefixString("astral_crystal"));

		AdvancementHolder obtainAstralIngot = Builder.advancement().parent(obtainAstralCrystal)
				.display(ItemRegistry.ASTRAL_INGOT.get(),
						createTitle("astral_ingot"),
						createDescription("astral_ingot"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_INGOT.get()))
				.save(consumer, prefixString("astral_ingot"));

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_SWORD.get(),
						createTitle("astral_sword"),
						createDescription("astral_sword"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_SWORD.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("astral_sword"));

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_PICKAXE.get(),
						createTitle("astral_pickaxe"),
						createDescription("astral_pickaxe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_PICKAXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("astral_pickaxe"));

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_AXE.get(),
						createTitle("astral_axe"),
						createDescription("astral_axe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_AXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("astral_axe"));

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_SHOVEL.get(),
						createTitle("astral_shovel"),
						createDescription("astral_shovel"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_SHOVEL.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("astral_shovel"));

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_HOE.get(),
						createTitle("astral_hoe"),
						createDescription("astral_hoe"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_HOE.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, prefixString("astral_hoe"));

		Builder.advancement().parent(obtainAstralIngot)
				.display(BlockItemRegistry.ASTRAL_BLOCK_ITEM.get(),
						createTitle("astral_tools"),
						createDescription("astral_tools"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												prefixRL("astral_sword"), true
										).checkAdvancementDone(
												prefixRL("astral_pickaxe"), true
										).checkAdvancementDone(
												prefixRL("astral_axe"), true
										).checkAdvancementDone(
												prefixRL("astral_shovel"), true
										).checkAdvancementDone(
												prefixRL("astral_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, prefixString("astral_tools"));

		Builder.advancement().parent(obtainAstralIngot)
				.display(ItemRegistry.ASTRAL_HELMET.get(),
						createTitle("astral_armor"),
						createDescription("astral_armor"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.ASTRAL_HELMET.get(),
								ItemRegistry.ASTRAL_CHESTPLATE.get(), ItemRegistry.ASTRAL_LEGGINGS.get(),
								ItemRegistry.ASTRAL_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, prefixString("astral_armor"));

		// Starstorm advancements
		AdvancementHolder obtainStarstormCrystal = Builder.advancement().parent(root)
				.display(BlockRegistry.STARSTORM_CRYSTAL.get(),
						createTitle("starstorm_crystal"),
						createDescription("starstorm_crystal"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.STARSTORM_CRYSTAL.get()))
				.save(consumer, prefixString("starstorm_crystal"));

		AdvancementHolder obtainStarstormIngot = Builder.advancement().parent(obtainStarstormCrystal)
				.display(ItemRegistry.STARSTORM_INGOT.get(),
						createTitle("starstorm_ingot"),
						createDescription("starstorm_ingot"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_INGOT.get()))
				.save(consumer, prefixString("starstorm_ingot"));

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_SWORD.get(),
						createTitle("starstorm_sword"),
						createDescription("starstorm_sword"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_SWORD.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("starstorm_sword"));

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_PICKAXE.get(),
						createTitle("starstorm_pickaxe"),
						createDescription("starstorm_pickaxe"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_PICKAXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("starstorm_pickaxe"));

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_AXE.get(),
						createTitle("starstorm_axe"),
						createDescription("starstorm_axe"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_AXE.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("starstorm_axe"));

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_SHOVEL.get(),
						createTitle("starstorm_shovel"),
						createDescription("starstorm_shovel"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_SHOVEL.get()))
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("starstorm_shovel"));

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_HOE.get(),
						createTitle("starstorm_hoe"),
						createDescription("starstorm_hoe"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_HOE.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, prefixString("starstorm_hoe"));

		Builder.advancement().parent(obtainStarstormIngot)
				.display(BlockItemRegistry.STARSTORM_BLOCK_ITEM.get(),
						createTitle("starstorm_tools"),
						createDescription("starstorm_tools"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												prefixRL("starstorm_sword"), true
										).checkAdvancementDone(
												prefixRL("starstorm_pickaxe"), true
										).checkAdvancementDone(
												prefixRL("starstorm_axe"), true
										).checkAdvancementDone(
												prefixRL("starstorm_shovel"), true
										).checkAdvancementDone(
												prefixRL("starstorm_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, prefixString("starstorm_tools"));

		Builder.advancement().parent(obtainStarstormIngot)
				.display(ItemRegistry.STARSTORM_HELMET.get(),
						createTitle("starstorm_armor"),
						createDescription("starstorm_armor"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STARSTORM_HELMET.get(),
								ItemRegistry.STARSTORM_CHESTPLATE.get(), ItemRegistry.STARSTORM_LEGGINGS.get(),
								ItemRegistry.STARSTORM_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, prefixString("starstorm_armor"));

		// Padded Leather advancements
		Builder.advancement().parent(root)
				.display(ItemRegistry.PADDED_LEATHER_HELMET.get(),
						createTitle("padded_leather_armor"),
						createDescription("padded_leather_armor"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.PADDED_LEATHER_HELMET.get(),
								ItemRegistry.PADDED_LEATHER_CHESTPLATE.get(), ItemRegistry.PADDED_LEATHER_LEGGINGS.get(),
								ItemRegistry.PADDED_LEATHER_BOOTS.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, prefixString("padded_leather_armor"));

		// Tool advancements
		AdvancementHolder craftToolRod = Builder.advancement().parent(root)
				.display(ItemRegistry.WOODEN_TOOL_ROD.get(),
						createTitle("tool_rod"),
						createDescription("tool_rod"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WOODEN_TOOL_ROD.get()))
				.save(consumer, prefixString("tool_rod"));

		Builder.advancement().parent(craftToolRod)
				.display(ItemRegistry.IRON_PIKE.get(),
						createTitle("pike"),
						createDescription("pike"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(IWItemTagGroups.PIKES).build()))
				.requirements(Strategy.OR)
				.save(consumer, prefixString("pike"));

		AdvancementHolder shards = Builder.advancement().parent(root)
				.display(ItemRegistry.STONE_SHARD.get(),
						createTitle("shards"),
						createDescription("shards"),
						null, AdvancementType.TASK, false, false, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(IWItemTagGroups.SHARDS).build()))
				.requirements(Strategy.OR)
				.save(consumer, prefixString("shards"));

		Builder.advancement().parent(shards)
				.display(ItemRegistry.WOODEN_SHARD.get(),
						createTitle("wooden_shard"),
						createDescription("wooden_shard"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WOODEN_SHARD.get()))
				.save(consumer, prefixString("wooden_shard"));

		Builder.advancement().parent(shards)
				.display(ItemRegistry.STONE_SHARD.get(),
						createTitle("stone_shard"),
						createDescription("stone_shard"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.STONE_SHARD.get()))
				.save(consumer, prefixString("stone_shard"));

		Builder.advancement().parent(shards)
				.display(ItemRegistry.DIAMOND_SHARD.get(),
						createTitle("diamond_shard"),
						createDescription("diamond_shard"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.DIAMOND_SHARD.get()))
				.save(consumer, prefixString("diamond_shard"));

		Builder.advancement().parent(shards)
				.display(ItemRegistry.OBSIDIAN_SHARD.get(),
						createTitle("obsidian_shard"),
						createDescription("obsidian_shard"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.OBSIDIAN_SHARD.get()))
				.save(consumer, prefixString("obsidian_shard"));

		Builder.advancement().parent(root)
				.display(ItemRegistry.NETHERITE_ARROW.get(),
						createTitle("netherite_projectile"),
						createDescription("netherite_projectile"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.NETHERITE_ARROW.get()))
				.addCriterion("hold1",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.NETHERITE_MUSKET_BALL.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, prefixString("netherite_projectile"));

		Builder.advancement().parent(root)
				.display(ItemRegistry.GOLDEN_MUSKET_BALL.get(),
						createTitle("musket_ball"),
						createDescription("musket_ball"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(IWItemTagGroups.MUSKET_BALLS).build()))
				.save(consumer, prefixString("musket_ball"));

		Builder.advancement().parent(root)
				.display(BlockRegistry.AMMUNITION_TABLE.get(),
						createTitle("ammunition_table"),
						createDescription("ammunition_table"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.AMMUNITION_TABLE.get()))
				.save(consumer, prefixString("ammunition_table"));

		Builder.advancement().parent(root)
				.display(BlockRegistry.STAR_FORGE_CONTROLLER.get(),
						createTitle("star_forge"),
						createDescription("star_forge"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(
								BlockRegistry.STAR_FORGE_CONTROLLER.get(),
								BlockRegistry.STAR_FORGE_BRICKS.get(),
								BlockRegistry.SOLAR_LENS.get(),
								Items.IRON_BARS
						))
				.save(consumer, prefixString("star_forge"));

		AdvancementHolder smallPartsTable = Builder.advancement().parent(root)
				.display(BlockRegistry.SMALL_PARTS_TABLE.get(),
						createTitle("small_parts_table"),
						createDescription("small_parts_table"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.SMALL_PARTS_TABLE.get()))
				.save(consumer, prefixString("small_parts_table"));

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.FLINTLOCK_PISTOL.get(),
						createTitle("flintlock_pistol"),
						createDescription("flintlock_pistol"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FLINTLOCK_PISTOL.get()))
				.save(consumer, prefixString("flintlock_pistol"));

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.BLUNDERBUSS.get(),
						createTitle("blunderbuss"),
						createDescription("blunderbuss"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BLUNDERBUSS.get()))
				.save(consumer, prefixString("blunderbuss"));

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.FLARE_GUN.get(),
						createTitle("flare_gun"),
						createDescription("flare_gun"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FLARE_GUN.get()))
				.save(consumer, prefixString("flare_gun"));

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.MUSKET.get(),
						createTitle("musket"),
						createDescription("musket"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MUSKET.get()))
				.addCriterion("hold1",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MUSKET_SCOPE.get()))
				.requirements(Strategy.OR)
				.save(consumer, prefixString("musket"));

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.HAND_CANNON.get(),
						createTitle("hand_cannon"),
						createDescription("hand_cannon"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.HAND_CANNON.get()))
				.save(consumer, prefixString("hand_cannon"));

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.SMOKE_GRENADE.get(),
						createTitle("smoke_grenade"),
						createDescription("smoke_grenade"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(IWItemTagGroups.SMOKE_GRENADES)))
				.save(consumer, prefixString("smoke_grenade"));

		Builder.advancement().parent(smallPartsTable)
				.display(ItemRegistry.FLASHBANG.get(),
						createTitle("flashbang"),
						createDescription("flashbang"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FLASHBANG.get()))
				.save(consumer, prefixString("flashbang"));

		AdvancementHolder craftAlcohol = Builder.advancement().parent(root)
				.display(ItemRegistry.BOTTLE_OF_ALCOHOL.get(),
						createTitle("bottle_of_alcohol"),
						createDescription("bottle_of_alcohol"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BOTTLE_OF_ALCOHOL.get()))
				.save(consumer, prefixString("bottle_of_alcohol"));
		Builder.advancement().parent(craftAlcohol)
				.display(ItemRegistry.MOLOTOV_COCKTAIL.get(),
						createTitle("molotov_cocktail"),
						createDescription("molotov_cocktail"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.MOLOTOV_COCKTAIL.get()))
				.save(consumer, prefixString("molotov_cocktail"));


		AdvancementHolder craftBandage = Builder.advancement().parent(root)
				.display(ItemRegistry.BANDAGE.get(),
						createTitle("bandage"),
						createDescription("bandage"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BANDAGE.get()))
				.save(consumer, prefixString("bandage"));
		Builder.advancement().parent(craftBandage)
				.display(ItemRegistry.FIRST_AID_KIT.get(),
						createTitle("first_aid_kit"),
						createDescription("first_aid_kit"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.FIRST_AID_KIT.get()))
				.save(consumer, prefixString("first_aid_kit"));


		Builder.advancement().parent(root)
				.display(ItemRegistry.IRON_GAUNTLET.get(),
						createTitle("gauntlet"),
						createDescription("gauntlet"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(IWItemTagGroups.GAUNTLETS).build()))
				.requirements(Strategy.OR)
				.save(consumer, prefixString("gauntlet"));

		// General ingot advancements
		AdvancementHolder ingots = Builder.advancement().parent(root)
				.display(Items.IRON_INGOT,
						createTitle("ingots"),
						createDescription("ingots"),
						null, AdvancementType.TASK, false, false, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(
								ItemPredicate.Builder.item().of(Tags.Items.INGOTS).build())
				)
				.save(consumer, prefixString("ingots"));

		Builder.advancement().parent(root)
				.display(Items.GOLD_NUGGET,
						createTitle("nuggets"),
						createDescription("nuggets"),
						null, AdvancementType.TASK, false, false, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(
								ItemPredicate.Builder.item().of(Tags.Items.NUGGETS).build())
				)
				.save(consumer, prefixString("nuggets"));

		// Copper advancements
		AdvancementHolder copperIngot = Builder.advancement().parent(ingots)
				.display(Items.COPPER_INGOT,
						createTitle("copper_ingot"),
						createDescription("copper_ingot"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(Items.COPPER_INGOT))
				.save(consumer, prefixString("copper_ingot"));

		Builder.advancement().parent(copperIngot)
				.display(ItemRegistry.COPPER_SWORD.get(),
						createTitle("copper_sword"),
						createDescription("copper_sword"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_SWORD.get()))
				.save(consumer, prefixString("copper_sword"));

		Builder.advancement().parent(copperIngot)
				.display(ItemRegistry.COPPER_PICKAXE.get(),
						createTitle("copper_pickaxe"),
						createDescription("copper_pickaxe"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_PICKAXE.get()))
				.save(consumer, prefixString("copper_pickaxe"));

		Builder.advancement().parent(copperIngot)
				.display(ItemRegistry.COPPER_AXE.get(),
						createTitle("copper_axe"),
						createDescription("copper_axe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_AXE.get()))
				.save(consumer, prefixString("copper_axe"));

		Builder.advancement().parent(copperIngot)
				.display(ItemRegistry.COPPER_SHOVEL.get(),
						createTitle("copper_shovel"),
						createDescription("copper_shovel"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_SHOVEL.get()))
				.save(consumer, prefixString("copper_shovel"));

		Builder.advancement().parent(copperIngot)
				.display(ItemRegistry.COPPER_HOE.get(),
						createTitle("copper_hoe"),
						createDescription("copper_hoe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COPPER_HOE.get()))
				.save(consumer, prefixString("copper_hoe"));

		Builder.advancement().parent(copperIngot)
				.display(Items.COPPER_BLOCK,
						createTitle("copper_tools"),
						createDescription("copper_tools"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												prefixRL("copper_sword"), true
										).checkAdvancementDone(
												prefixRL("copper_pickaxe"), true
										).checkAdvancementDone(
												prefixRL("copper_axe"), true
										).checkAdvancementDone(
												prefixRL("copper_shovel"), true
										).checkAdvancementDone(
												prefixRL("copper_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(25))
				.save(consumer, prefixString("copper_tools"));

		// Cobalt advancements
		AdvancementHolder cobaltIngot = Builder.advancement().parent(ingots)
				.display(ItemRegistry.COBALT_INGOT.get(),
						createTitle("cobalt_ingot"),
						createDescription("cobalt_ingot"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_INGOT.get()))
				.save(consumer, prefixString("cobalt_ingot"));

		Builder.advancement().parent(cobaltIngot)
				.display(ItemRegistry.COBALT_SWORD.get(),
						createTitle("cobalt_sword"),
						createDescription("cobalt_sword"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_SWORD.get()))
				.save(consumer, prefixString("cobalt_sword"));

		Builder.advancement().parent(cobaltIngot)
				.display(ItemRegistry.COBALT_PICKAXE.get(),
						createTitle("cobalt_pickaxe"),
						createDescription("cobalt_pickaxe"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_PICKAXE.get()))
				.save(consumer, prefixString("cobalt_pickaxe"));

		Builder.advancement().parent(cobaltIngot)
				.display(ItemRegistry.COBALT_AXE.get(),
						createTitle("cobalt_axe"),
						createDescription("cobalt_axe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_AXE.get()))
				.save(consumer, prefixString("cobalt_axe"));

		Builder.advancement().parent(cobaltIngot)
				.display(ItemRegistry.COBALT_SHOVEL.get(),
						createTitle("cobalt_shovel"),
						createDescription("cobalt_shovel"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_SHOVEL.get()))
				.save(consumer, prefixString("cobalt_shovel"));

		Builder.advancement().parent(cobaltIngot)
				.display(ItemRegistry.COBALT_HOE.get(),
						createTitle("cobalt_hoe"),
						createDescription("cobalt_hoe"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.COBALT_HOE.get()))
				.save(consumer, prefixString("cobalt_hoe"));

		Builder.advancement().parent(cobaltIngot)
				.display(BlockItemRegistry.COBALT_BLOCK_ITEM.get(),
						createTitle("cobalt_tools"),
						createDescription("cobalt_tools"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("have_advancements",
						PlayerTrigger.TriggerInstance.located(
								EntityPredicate.Builder.entity().subPredicate(
										PlayerPredicate.Builder.player().checkAdvancementDone(
												prefixRL("cobalt_sword"), true
										).checkAdvancementDone(
												prefixRL("cobalt_pickaxe"), true
										).checkAdvancementDone(
												prefixRL("cobalt_axe"), true
										).checkAdvancementDone(
												prefixRL("cobalt_shovel"), true
										).checkAdvancementDone(
												prefixRL("cobalt_hoe"), true
										).build()
								)
						)
				)
				.rewards(AdvancementRewards.Builder.experience(35))
				.save(consumer, prefixString("cobalt_tools"));

		// Other ingots, without families
		Builder.advancement().parent(ingots)
				.display(Items.GOLD_INGOT,
						createTitle("gold_ingot"),
						createDescription("gold_ingot"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_INGOT))
				.save(consumer, prefixString("gold_ingot"));

		Builder.advancement().parent(ingots)
				.display(Items.NETHERITE_INGOT,
						createTitle("netherite_ingot"),
						createDescription("netherite_ingot"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_INGOT))
				.save(consumer, prefixString("netherite_ingot"));

		// Entity discovery advancements
		AdvancementHolder entityDiscovery = Builder.advancement().parent(root)
				.display(Items.CREEPER_HEAD,
						createTitle("entity_discovery"),
						createDescription("entity_discovery"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("discover_minuteman", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.MINUTEMAN_ENTITY.get()))
				.addCriterion("discover_field_medic", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.FIELD_MEDIC_ENTITY.get()))
				.addCriterion("discover_dying_soldier", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.DYING_SOLDIER_ENTITY.get()))
				.addCriterion("discover_the_commander", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.THE_COMMANDER_ENTITY.get()))
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
				.save(consumer, prefixString("entity_discovery"));

		Builder.advancement().parent(entityDiscovery)
				.display(BlockItemRegistry.MINUTEMAN_HEAD_ITEM.get(),
						createTitle("discover_minuteman"),
						createDescription("discover_minuteman"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.MINUTEMAN_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_minuteman"));

		Builder.advancement().parent(entityDiscovery)
				.display(BlockItemRegistry.FIELD_MEDIC_HEAD_ITEM.get(),
						createTitle("discover_field_medic"),
						createDescription("discover_field_medic"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.FIELD_MEDIC_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_field_medic"));

		Builder.advancement().parent(entityDiscovery)
				.display(BlockItemRegistry.DYING_SOLDIER_HEAD_ITEM.get(),
						createTitle("discover_dying_soldier"),
						createDescription("discover_dying_soldier"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.DYING_SOLDIER_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_dying_soldier"));

		AdvancementHolder theCommanderDiscovery = Builder.advancement().parent(entityDiscovery)
				.display(BlockItemRegistry.THE_COMMANDER_HEAD_ITEM.get(),
						createTitle("discover_the_commander"),
						createDescription("discover_the_commander"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.THE_COMMANDER_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_the_commander"));

		Builder.advancement().parent(entityDiscovery)
				.display(BlockItemRegistry.WANDERING_WARRIOR_HEAD_ITEM.get(),
						createTitle("discover_wandering_warrior"),
						createDescription("discover_wandering_warrior"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.WANDERING_WARRIOR_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_wandering_warrior"));

		Builder.advancement().parent(entityDiscovery)
				.display(BlockItemRegistry.HANS_HEAD_ITEM.get(),
						createTitle("discover_hans"),
						createDescription("discover_hans"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.HANS_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_hans"));

		Builder.advancement().parent(entityDiscovery)
				.display(BlockItemRegistry.HANS_HEAD_ITEM.get(),
						createTitle("discover_super_hans"),
						createDescription("discover_super_hans"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.SUPER_HANS_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_super_hans"));

		Builder.advancement().parent(entityDiscovery)
				.display(ItemRegistry.SULFUR.get(),
						createTitle("discover_lava_revenant"),
						createDescription("discover_lava_revenant"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.LAVA_REVENANT_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_lava_revenant"));

		Builder.advancement().parent(entityDiscovery)
				.display(Items.SPIDER_EYE,
						createTitle("discover_rock_spider"),
						createDescription("discover_rock_spider"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.ROCK_SPIDER_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_rock_spider"));

		Builder.advancement().parent(entityDiscovery)
				.display(BlockRegistry.STARSTORM_CRYSTAL.get(),
						createTitle("discover_starmite"),
						createDescription("discover_starmite"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.STARMITE_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_starmite"));

		Builder.advancement().parent(entityDiscovery)
				.display(BlockItemRegistry.STORM_CREEPER_HEAD_ITEM.get(),
						createTitle("discover_storm_creeper"),
						createDescription("discover_storm_creeper"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.STORM_CREEPER_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_storm_creeper"));

		Builder.advancement().parent(entityDiscovery)
				.display(ItemRegistry.BROKEN_LENS.get(),
						createTitle("discover_evil_eye"),
						createDescription("discover_evil_eye"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.EVIL_EYE_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_evil_eye"));

		Builder.advancement().parent(entityDiscovery)
				.display(Items.BONE,
						createTitle("discover_star_wolf"),
						createDescription("discover_star_wolf"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.STAR_WOLF_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_star_wolf"));

		Builder.advancement().parent(entityDiscovery)
				.display(Items.BOOK,
						createTitle("discover_skygazer"),
						createDescription("discover_skygazer"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.SKYGAZER_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_skygazer"));

		Builder.advancement().parent(entityDiscovery)
				.display(BlockItemRegistry.SKELETON_MERCHANT_HEAD_ITEM.get(),
						createTitle("discover_skeleton_merchant"),
						createDescription("discover_skeleton_merchant"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.SKELETON_MERCHANT_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_skeleton_merchant"));


		AdvancementHolder celestialTowerDiscovery = Builder.advancement().parent(entityDiscovery)
				.display(ItemRegistry.CELESTIAL_FRAGMENT.get(),
						createTitle("discover_celestial_tower"),
						createDescription("discover_celestial_tower"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("discover", EntityDiscoveredTrigger.TriggerInstance.discoveredEntity(EntityRegistry.CELESTIAL_TOWER_ENTITY.get()))
				.rewards(AdvancementRewards.Builder.experience(20))
				.save(consumer, prefixString("discover_celestial_tower"));

		Builder.advancement().parent(celestialTowerDiscovery)
				.display(BlockItemRegistry.CELESTIAL_LANTERN_ITEM.get(),
						createTitle("celestial_lantern"),
						createDescription("celestial_lantern"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockItemRegistry.CELESTIAL_LANTERN_ITEM.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, prefixString("celestial_lantern"));

		Builder.advancement().parent(celestialTowerDiscovery)
				.display(ItemRegistry.METEOR_STAFF.get(),
						createTitle("meteor_staff"),
						createDescription("meteor_staff"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.METEOR_STAFF.get()))
				.rewards(AdvancementRewards.Builder.experience(75))
				.save(consumer, prefixString("meteor_staff"));

		Builder.advancement().parent(celestialTowerDiscovery)
				.display(ItemRegistry.CURSED_SIGHT_STAFF.get(),
						createTitle("cursed_sight_staff"),
						createDescription("cursed_sight_staff"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.CURSED_SIGHT_STAFF.get()))
				.rewards(AdvancementRewards.Builder.experience(75))
				.save(consumer, prefixString("cursed_sight_staff"));

		// Accessory advancements
		Builder.advancement().parent(root)
				.display(ItemRegistry.SATCHEL.get(),
						createTitle("accessories"),
						createDescription("accessories"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(
								ItemPredicate.Builder.item().of(IWItemTagGroups.ACCESSORIES).build()))
				.rewards(AdvancementRewards.Builder.experience(15))
				.save(consumer, prefixString("accessories"));
		Builder.advancement().parent(root)
				.display(ItemRegistry.BLOODY_SACRIFICE.get(),
						createTitle("bloody_sacrifice"),
						createDescription("bloody_sacrifice"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.BLOODY_SACRIFICE.get()))
				.rewards(AdvancementRewards.Builder.experience(15))
				.save(consumer, prefixString("bloody_sacrifice"));

		// Other advancements
		Builder.advancement().parent(root)
				.display(ItemRegistry.USED_SYRINGE.get(),
						createTitle("used_syringe"),
						createDescription("used_syringe"),
						null, AdvancementType.CHALLENGE, true, true, true)
				.addCriterion("hold",
						KilledTrigger.TriggerInstance.entityKilledPlayer(EntityPredicate.Builder.entity(),
								DamageSourcePredicate.Builder.damageType()
										.source(EntityPredicate.Builder.entity()
												.equipment(EntityEquipmentPredicate.Builder.equipment()
														.mainhand(ItemPredicate.Builder.item()
																.of(ItemRegistry.USED_SYRINGE.get()))
														.build()))))
				.save(consumer, prefixString("used_syringe"));

		Builder.advancement().parent(root)
				.display(ItemRegistry.KILL_COUNTER.get(),
						createTitle("kill_counter"),
						createDescription("kill_counter"),
						null, AdvancementType.CHALLENGE, true, true, true)
				.addCriterion("", CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance()))
				.save(consumer, prefixString("kill_counter"));

		Builder.advancement().parent(root)
				.display(ItemRegistry.STARSTORM_ARROW.get(),
						createTitle("overkill"),
						createDescription("overkill"),
						null, AdvancementType.CHALLENGE, true, true, true)
				.addCriterion("", CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, prefixString("overkill"));

		Builder.advancement().parent(root)
				.display(ItemRegistry.ASTRAL_MUSKET_BALL.get(),
						createTitle("firearm_long_range"),
						createDescription("firearm_long_range"),
						null, AdvancementType.CHALLENGE, true, true, true)
				.addCriterion("", CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, prefixString("firearm_long_range"));

		Builder.advancement().parent(root)
				.display(ItemRegistry.MUD_BALL.get(),
						createTitle("mud_ball"),
						createDescription("mud_ball"),
						null, AdvancementType.GOAL, true, true, true)
				.addCriterion("", CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance()))
				.save(consumer, prefixString("mud_ball"));

		Builder.advancement().parent(root)
				.display(BlockRegistry.BEAR_TRAP.get(),
						createTitle("traps"),
						createDescription("traps"),
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
				.save(consumer, prefixString("traps"));

		Builder.advancement().parent(root)
				.display(Items.OAK_PLANKS,
						createTitle("planks"),
						createDescription("planks"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
								.of(ItemTags.PLANKS).build()))
				.save(consumer, prefixString("planks"));

		Builder.advancement().parent(root)
				.display(BlockItemRegistry.MUD_ITEM.get(),
						createTitle("mud"),
						createDescription("mud"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
								.of(BlockItemRegistry.MUD_ITEM.get()).build()))
				.save(consumer, prefixString("mud"));

		Builder.advancement().parent(root)
				.display(Items.BAMBOO,
						createTitle("bamboo"),
						createDescription("bamboo"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
								.of(Items.BAMBOO).build()))
				.save(consumer, prefixString("bamboo"));

		Builder.advancement().parent(root)
				.display(BlockItemRegistry.CLOUD_MARBLE_ITEM.get(),
						createTitle("cloud_marble"),
						createDescription("cloud_marble"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
								.of(BlockItemRegistry.CLOUD_MARBLE_ITEM.get()).build()))
				.save(consumer, prefixString("cloud_marble"));

		Builder.advancement().parent(root)
				.display(BlockItemRegistry.BIOHAZARD_BOX_ITEM.get(),
						createTitle("biohazard_box"),
						createDescription("biohazard_box"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item()
								.of(BlockItemRegistry.BIOHAZARD_BOX_ITEM.get()).build()))
				.save(consumer, prefixString("biohazard_box"));

		Builder.advancement().parent(root)
				.display(BlockItemRegistry.CLOUD_ITEM.get(),
						createTitle("cloud"),
						createDescription("cloud"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						PlayerTrigger.TriggerInstance.walkOnBlockWithEquipment(BlockRegistry.CLOUD.get(),
								Items.AIR))
				.save(consumer, prefixString("cloud"));

		// Warden Advancements
		AdvancementHolder wardenHeart = Builder.advancement().parent(root)
				.display(ItemRegistry.WARDEN_HEART.get(),
						createTitle("warden_heart"),
						createDescription("warden_heart"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.WARDEN_HEART.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, prefixString("warden_heart"));

		Builder.advancement().parent(wardenHeart)
				.display(ItemRegistry.SCULK_STAFF.get(),
						createTitle("sculk_staff"),
						createDescription("sculk_staff"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.SCULK_STAFF.get()))
				.rewards(AdvancementRewards.Builder.experience(75))
				.save(consumer, prefixString("sculk_staff"));

		Builder.advancement().parent(wardenHeart)
				.display(ItemRegistry.REINFORCED_DEPTH_CHARM.get(),
						createTitle("reinforced_depth_charm"),
						createDescription("reinforced_depth_charm"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.REINFORCED_DEPTH_CHARM.get()))
				.rewards(AdvancementRewards.Builder.experience(75))
				.save(consumer, prefixString("reinforced_depth_charm"));

		// Super Hans advancements
		AdvancementHolder hansiumIngot = Builder.advancement().parent(root)
				.display(ItemRegistry.HANSIUM_INGOT.get(),
						createTitle("hansium_ingot"),
						createDescription("hansium_ingot"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.HANSIUM_INGOT.get()))
				.rewards(AdvancementRewards.Builder.experience(100))
				.save(consumer, prefixString("hansium_ingot"));

		Builder.advancement().parent(hansiumIngot)
				.display(ItemRegistry.THE_SWORD.get(),
						createTitle("the_sword"),
						createDescription("the_sword"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.THE_SWORD.get()))
				.rewards(AdvancementRewards.Builder.experience(250))
				.save(consumer, prefixString("the_sword"));

		// Battlefield advancements
		HolderGetter<Biome> holderGetter = PROVIDER.lookupOrThrow(Registries.BIOME);
		AdvancementHolder discoverBattlefield = Builder.advancement().parent(root)
				.display(Blocks.SKELETON_SKULL,
						createTitle("battlefield"),
						createDescription("battlefield"),
						null, AdvancementType.TASK, true, true, false)
				.addCriterion("visit",
						PlayerTrigger.TriggerInstance.located(
								LocationPredicate.Builder.inBiome(holderGetter.getOrThrow(IWBiomes.BATTLEFIELD))))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, prefixString("battlefield"));

		Builder.advancement().parent(discoverBattlefield)
				.display(BlockItemRegistry.MINUTEMAN_STATUE_ITEM.get(),
						createTitle("minuteman_statue"),
						createDescription("minuteman_statue"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold", InventoryChangeTrigger.TriggerInstance.hasItems(
						BlockItemRegistry.MINUTEMAN_STATUE_ITEM.get()))
				.save(consumer, prefixString("minuteman_statue"));

		Builder.advancement().parent(discoverBattlefield)
				.display(BlockItemRegistry.MEDIC_STATUE_ITEM.get(),
						createTitle("medic_statue"),
						createDescription("medic_statue"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold", InventoryChangeTrigger.TriggerInstance.hasItems(
						BlockItemRegistry.MEDIC_STATUE_ITEM.get()))
				.save(consumer, prefixString("medic_statue"));

		AdvancementHolder commanderPedestal = Builder.advancement().parent(theCommanderDiscovery)
				.display(BlockItemRegistry.COMMANDER_PEDESTAL.get(),
						createTitle("commander_pedestal"),
						createDescription("commander_pedestal"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("hold", InventoryChangeTrigger.TriggerInstance.hasItems(
						BlockItemRegistry.COMMANDER_PEDESTAL.get()))
				.rewards(AdvancementRewards.Builder.experience(50))
				.save(consumer, prefixString("commander_pedestal"));

		Builder.advancement().parent(commanderPedestal)
				.display(ItemRegistry.PEDESTAL_AUGMENT_SPEED.get(),
						createTitle("pedestal_augment"),
						createDescription("pedestal_augment"),
						null, AdvancementType.CHALLENGE, true, true, false)
				.addCriterion("hold", InventoryChangeTrigger.TriggerInstance.hasItems(
						ItemPredicate.Builder.item().of(IWItemTagGroups.COMMANDER_PEDESTAL_AUGMENTS).build()))
				.rewards(AdvancementRewards.Builder.experience(75))
				.save(consumer, prefixString("pedestal_augment"));

		// Tiltros advancements
		AdvancementHolder tiltrosPortal = Builder.advancement().parent(root)
				.display(BlockRegistry.TILTROS_PORTAL_FRAME.get(),
						createTitle("tiltros_portal"),
						createDescription("tiltros_portal"),
						null, AdvancementType.TASK, true, true, true)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(BlockRegistry.TILTROS_PORTAL_FRAME.get()))
				.requirements(Strategy.AND)
				.save(consumer, prefixString("tiltros_portal"));

		AdvancementHolder azulKeystone = Builder.advancement().parent(tiltrosPortal)
				.display(ItemRegistry.AZUL_KEYSTONE_FRAGMENT.get(),
						createTitle("azul_keystone"),
						createDescription("azul_keystone"),
						null, AdvancementType.TASK, true, true, true)
				.addCriterion("hold",
						InventoryChangeTrigger.TriggerInstance.hasItems(ItemRegistry.AZUL_KEYSTONE.get()))
				.save(consumer, prefixString("azul_keystone"));

		Builder.advancement().parent(azulKeystone)
				.display(BlockItemRegistry.MOONGLOW_ITEM.get(),
						createTitle("enter_tiltros"),
						createDescription("enter_tiltros"),
						null, AdvancementType.GOAL, true, true, false)
				.addCriterion("visit",
						PlayerTrigger.TriggerInstance.located(
								LocationPredicate.Builder.inDimension(DimensionGenerator.TILTROS_LEVEL)))
				.rewards(AdvancementRewards.Builder.experience(150))
				.save(consumer, prefixString("tiltros"));
	}

	private static ResourceLocation prefixRL(String string) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, string);
	}

	private static String prefixString(String string) {
		return ImmersiveWeapons.MOD_ID + ":" + string;
	}

	private static MutableComponent createTitle(String key) {
		return Component.translatable("advancements.immersiveweapons." + key + ".title");
	}

	private static MutableComponent createDescription(String key) {
		return Component.translatable("advancements.immersiveweapons." + key + ".description");
	}
}