package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.RegistryObject;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.lists.ItemLists;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ItemTagsGenerator extends ItemTagsProvider {

	public ItemTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider, BlockTagsProvider blocks, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blocks.contentsGetter(), ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider) {
		addForgeTags();
		addImmersiveWeaponsTags();
		addMinecraftTags();
	}

	/**
	 * Add tags under the Forge namespace
	 */
	@SuppressWarnings("unchecked")
	private void addForgeTags() {
		List<Item> items = new ArrayList<>(250);

		ItemRegistry.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(items::add);

		// Copy item tags from blockLocation tags
		copy(Blocks.STAINED_GLASS, Tags.Items.STAINED_GLASS);
		copy(ForgeBlockTagGroups.COBALT_ORES, ForgeItemTagGroups.COBALT_ORES);
		copy(ForgeBlockTagGroups.SULFUR_ORES, ForgeItemTagGroups.SULFUR_ORES);
		copy(Blocks.ORES, Tags.Items.ORES);

		// Ingot tags
		tag(ForgeItemTagGroups.COBALT_INGOTS).add(ItemRegistry.COBALT_INGOT.get());
		tag(Tags.Items.INGOTS_COPPER).add(Items.COPPER_INGOT);
		tag(ForgeItemTagGroups.METAL_INGOTS).addTags(
				ForgeItemTagGroups.COBALT_INGOTS,
				Tags.Items.INGOTS_COPPER,
				Tags.Items.INGOTS_IRON,
				Tags.Items.INGOTS_GOLD);
		tag(Tags.Items.INGOTS).addTag(ForgeItemTagGroups.METAL_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.MOLTEN_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.ELECTRIC_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.TESLA_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.STARSTORM_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.ASTRAL_INGOTS);

		// Nugget tags
		tag(ForgeItemTagGroups.COBALT_NUGGETS).add(ItemRegistry.COBALT_NUGGET.get());
		tag(ForgeItemTagGroups.COPPER_NUGGETS).add(ItemRegistry.COPPER_NUGGET.get());
		tag(ForgeItemTagGroups.METAL_NUGGETS).addTags(
				ForgeItemTagGroups.COBALT_NUGGETS,
				ForgeItemTagGroups.COPPER_NUGGETS,
				Tags.Items.NUGGETS_IRON,
				Tags.Items.NUGGETS_GOLD);
		tag(Tags.Items.NUGGETS).addTag(ForgeItemTagGroups.METAL_NUGGETS);
		tag(Tags.Items.NUGGETS).addTag(IWItemTagGroups.TESLA_NUGGETS);
		tag(Tags.Items.NUGGETS).addTag(IWItemTagGroups.ASTRAL_NUGGETS);

		// Dust tags
		tag(ForgeItemTagGroups.SULFUR_DUSTS).add(ItemRegistry.SULFUR_DUST.get());

		// Loop through the registry and add groups of items to a tag
		for (Item item : items) {
			if (item.getDescriptionId().contains("sword")) {
				tag(ItemTags.SWORDS).add(item);
			} else if (item.getDescriptionId().contains("pickaxe")) {
				tag(ItemTags.PICKAXES).add(item);
			} else if (item.getDescriptionId().contains("axe")) {
				tag(ItemTags.AXES).add(item);
			} else if (item.getDescriptionId().contains("shovel")) {
				tag(ItemTags.SHOVELS).add(item);
			} else if (item.getDescriptionId().contains("hoe")) {
				tag(ItemTags.HOES).add(item);
			} else if (item.getDescriptionId().contains("helmet")) {
				tag(Tags.Items.ARMORS_HELMETS).add(item);
			} else if (item.getDescriptionId().contains("chestplate")) {
				tag(Tags.Items.ARMORS_CHESTPLATES).add(item);
			} else if (item.getDescriptionId().contains("leggings")) {
				tag(Tags.Items.ARMORS_LEGGINGS).add(item);
			} else if (item.getDescriptionId().contains("boots")) {
				tag(Tags.Items.ARMORS_BOOTS).add(item);
			}
		}

		tag(Tags.Items.TOOLS_BOWS).add(
				ItemRegistry.ICE_BOW.get(),
				ItemRegistry.DRAGONS_BREATH_BOW.get(),
				ItemRegistry.AURORA_BOW.get());

		// Head tags
		tag(Tags.Items.HEADS).add(
				BlockItemRegistry.MINUTEMAN_HEAD_ITEM.get(),
				BlockItemRegistry.FIELD_MEDIC_HEAD_ITEM.get(),
				BlockItemRegistry.DYING_SOLDIER_HEAD_ITEM.get(),
				BlockItemRegistry.WANDERING_WARRIOR_HEAD_ITEM.get(),
				BlockItemRegistry.HANS_HEAD_ITEM.get(),
				BlockItemRegistry.STORM_CREEPER_HEAD_ITEM.get());
	}

	/**
	 * Add tags under the Immersive Weapons namespace
	 */
	@SuppressWarnings("unchecked")
	private void addImmersiveWeaponsTags() {
		// Copy item tags from blockLocation tags
		copy(IWBlockTagGroups.BURNED_OAK_LOGS, IWItemTagGroups.BURNED_OAK_LOGS);
		copy(IWBlockTagGroups.STARDUST_LOGS, IWItemTagGroups.STARDUST_LOGS);

		// Projectile tags
		tag(IWItemTagGroups.FLARES).add(ItemRegistry.FLARE.get());
		for (Item item : ItemLists.MUSKET_BALL_ITEMS) {
			tag(IWItemTagGroups.MUSKET_BALLS).add(item);
		}
		tag(IWItemTagGroups.CANNONBALLS).add(
				ItemRegistry.CANNONBALL.get(),
				ItemRegistry.EXPLOSIVE_CANNONBALL.get());

		// Ingot tags
		tag(IWItemTagGroups.MOLTEN_INGOTS).add(ItemRegistry.MOLTEN_INGOT.get());
		tag(IWItemTagGroups.ELECTRIC_INGOTS).add(ItemRegistry.ELECTRIC_INGOT.get());
		tag(IWItemTagGroups.TESLA_INGOTS).add(ItemRegistry.TESLA_INGOT.get());
		tag(IWItemTagGroups.ASTRAL_INGOTS).add(ItemRegistry.ASTRAL_INGOT.get());
		tag(IWItemTagGroups.STARSTORM_INGOTS).add(ItemRegistry.STARSTORM_INGOT.get());

		// Shard tags
		tag(IWItemTagGroups.MOLTEN_SHARDS).add(ItemRegistry.MOLTEN_SHARD.get());
		tag(IWItemTagGroups.VENTUS_SHARDS).add(ItemRegistry.VENTUS_SHARD.get());
		tag(IWItemTagGroups.DIAMOND_SHARDS).add(ItemRegistry.DIAMOND_SHARD.get());
		tag(IWItemTagGroups.STONE_SHARDS).add(ItemRegistry.STONE_SHARD.get());
		tag(IWItemTagGroups.WOODEN_SHARDS).add(ItemRegistry.WOODEN_SHARD.get());
		tag(IWItemTagGroups.STARSTORM_SHARDS).add(ItemRegistry.STARSTORM_SHARD.get());
		tag(IWItemTagGroups.SHARDS).addTags(
				IWItemTagGroups.MOLTEN_SHARDS,
				IWItemTagGroups.VENTUS_SHARDS,
				IWItemTagGroups.DIAMOND_SHARDS,
				IWItemTagGroups.STONE_SHARDS,
				IWItemTagGroups.WOODEN_SHARDS,
				IWItemTagGroups.STARSTORM_SHARDS);

		// Nugget tags
		tag(IWItemTagGroups.TESLA_NUGGETS).add(ItemRegistry.TESLA_NUGGET.get());
		tag(IWItemTagGroups.ASTRAL_NUGGETS).add(ItemRegistry.ASTRAL_NUGGET.get());

		// Rod tags
		tag(IWItemTagGroups.OBSIDIAN_RODS).add(ItemRegistry.OBSIDIAN_ROD.get());

		// Accessory tags
		for (RegistryObject<Item> item : ItemRegistry.ITEMS.getEntries()) {
			if (item.get() instanceof AccessoryItem accessory) {
				tag(IWItemTagGroups.ACCESSORIES).add(accessory);
			}
		}
	}

	/**
	 * Add tags under the Minecraft namespace
	 */
	private void addMinecraftTags() {
		// Copy item tags from blockLocation tags
		copy(BlockTags.FENCES, ItemTags.FENCES);
		copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
		copy(BlockTags.PLANKS, ItemTags.PLANKS);
		copy(BlockTags.SLABS, ItemTags.SLABS);
		copy(BlockTags.STAIRS, ItemTags.STAIRS);
		copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
		copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
		copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
		copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
		copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
		copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
		copy(BlockTags.LEAVES, ItemTags.LEAVES);
		copy(BlockTags.SAND, ItemTags.SAND);
		copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
		copy(BlockTags.WALLS, ItemTags.WALLS);
		copy(BlockTags.SMELTS_TO_GLASS, ItemTags.SMELTS_TO_GLASS);

		// Sign tags
		tag(ItemTags.SIGNS).add(BlockItemRegistry.BURNED_OAK_SIGN_ITEM.get());

		// Arrow tags
		for (Item item : ItemLists.ARROW_ITEMS) {
			tag(ItemTags.ARROWS).add(item);
		}

		// Boat tags
		tag(ItemTags.BOATS).add(ItemRegistry.BURNED_OAK_BOAT.get());
		tag(ItemTags.BOATS).add(ItemRegistry.STARDUST_BOAT.get());
		tag(ItemTags.CHEST_BOATS).add(ItemRegistry.BURNED_OAK_CHEST_BOAT.get());
		tag(ItemTags.CHEST_BOATS).add(ItemRegistry.STARDUST_CHEST_BOAT.get());

		// Non-flammable wood tag
		tag(ItemTags.NON_FLAMMABLE_WOOD).add(
				BlockItemRegistry.WARPED_TABLE_ITEM.get(),
				BlockItemRegistry.CRIMSON_TABLE_ITEM.get());

		// Trimmable armor tag
		for (RegistryObject<Item> item : ItemRegistry.ITEMS.getEntries()) {
			if (item.get() instanceof ArmorItem armor) {
				tag(ItemTags.TRIMMABLE_ARMOR).add(armor);
			}
		}

		// Beacon payment tag
		tag(ItemTags.BEACON_PAYMENT_ITEMS).add(
				ItemRegistry.COBALT_INGOT.get(),
				ItemRegistry.MOLTEN_INGOT.get(),
				ItemRegistry.TESLA_INGOT.get(),
				ItemRegistry.VENTUS_SHARD.get(),
				ItemRegistry.ASTRAL_INGOT.get(),
				ItemRegistry.STARSTORM_INGOT.get());
	}
}