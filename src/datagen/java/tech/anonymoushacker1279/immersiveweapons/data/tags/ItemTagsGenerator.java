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
import net.neoforged.neoforge.registries.DeferredHolder;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.CustomDataGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.AccessoryItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;

import java.util.concurrent.CompletableFuture;

public class ItemTagsGenerator extends ItemTagsProvider {

	public ItemTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider, BlockTagsProvider blocks, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, blocks.contentsGetter(), ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(Provider provider) {
		addCommonTags();
		addImmersiveWeaponsTags();
		addMinecraftTags();
	}

	/**
	 * Add tags under the Forge namespace
	 */
	@SuppressWarnings("unchecked")
	private void addCommonTags() {
		// Copy item tags from block tags
		copy(CommonBlockTagGroups.COBALT_ORES, CommonItemTagGroups.COBALT_ORES);
		copy(CommonBlockTagGroups.SULFUR_ORES, CommonItemTagGroups.SULFUR_ORES);
		copy(Blocks.ORES, Tags.Items.ORES);

		// Ingot tags
		tag(CommonItemTagGroups.COBALT_INGOTS).add(ItemRegistry.COBALT_INGOT.get());
		tag(Tags.Items.INGOTS_COPPER).add(Items.COPPER_INGOT);
		tag(CommonItemTagGroups.METAL_INGOTS).addTags(
				CommonItemTagGroups.COBALT_INGOTS,
				Tags.Items.INGOTS_COPPER,
				Tags.Items.INGOTS_IRON,
				Tags.Items.INGOTS_GOLD);
		tag(Tags.Items.INGOTS).addTag(CommonItemTagGroups.METAL_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.MOLTEN_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.ELECTRIC_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.TESLA_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.STARSTORM_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.ASTRAL_INGOTS);

		// Nugget tags
		tag(CommonItemTagGroups.COBALT_NUGGETS).add(ItemRegistry.COBALT_NUGGET.get());
		tag(CommonItemTagGroups.COPPER_NUGGETS).add(ItemRegistry.COPPER_NUGGET.get());
		tag(CommonItemTagGroups.METAL_NUGGETS).addTags(
				CommonItemTagGroups.COBALT_NUGGETS,
				CommonItemTagGroups.COPPER_NUGGETS,
				Tags.Items.NUGGETS_IRON,
				Tags.Items.NUGGETS_GOLD);
		tag(Tags.Items.NUGGETS).addTag(CommonItemTagGroups.METAL_NUGGETS);
		tag(Tags.Items.NUGGETS).addTag(IWItemTagGroups.TESLA_NUGGETS);
		tag(Tags.Items.NUGGETS).addTag(IWItemTagGroups.ASTRAL_NUGGETS);

		// Dust tags
		tag(CommonItemTagGroups.SULFUR_DUSTS).add(ItemRegistry.SULFUR_DUST.get());

		// Loop through the registry and add groups of items to a tag
		for (Item item : CustomDataGenerator.ALL_ITEMS) {
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
				tag(ItemTags.HEAD_ARMOR).add(item);
			} else if (item.getDescriptionId().contains("chestplate")) {
				tag(ItemTags.CHEST_ARMOR).add(item);
			} else if (item.getDescriptionId().contains("leggings")) {
				tag(ItemTags.LEG_ARMOR).add(item);
			} else if (item.getDescriptionId().contains("boots")) {
				tag(ItemTags.FOOT_ARMOR).add(item);
			}
		}

		tag(Tags.Items.TOOLS_BOWS).add(
				ItemRegistry.ICE_BOW.get(),
				ItemRegistry.DRAGONS_BREATH_BOW.get(),
				ItemRegistry.AURORA_BOW.get());

		// Head tags
		tag(ItemTags.SKULLS).add(
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
		// Copy item tags from block tags
		copy(IWBlockTagGroups.BURNED_OAK_LOGS, IWItemTagGroups.BURNED_OAK_LOGS);
		copy(IWBlockTagGroups.STARDUST_LOGS, IWItemTagGroups.STARDUST_LOGS);

		// Projectile tags
		tag(IWItemTagGroups.FLARES).add(ItemRegistry.FLARE.get());

		for (Item item : CustomDataGenerator.ALL_ITEMS) {
			if (item.getDescriptionId().contains("musket_ball")) {
				tag(IWItemTagGroups.MUSKET_BALLS).add(item);
			}
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
		for (DeferredHolder<Item, ? extends Item> item : ItemRegistry.ITEMS.getEntries()) {
			if (item.get() instanceof AccessoryItem accessory) {
				tag(IWItemTagGroups.ACCESSORIES).add(accessory);
			}
		}

		// Smoke grenade tags
		for (Item item : CustomDataGenerator.ALL_ITEMS) {
			if (item.getDescriptionId().contains("smoke_grenade") && !item.getDescriptionId().contains("arrow")) {
				tag(IWItemTagGroups.SMOKE_GRENADES).add(item);
			}
		}

		// Tool tags
		tag(IWItemTagGroups.MOLTEN_TOOLS).add(
				ItemRegistry.MOLTEN_SWORD.get(),
				ItemRegistry.MOLTEN_PICKAXE.get(),
				ItemRegistry.MOLTEN_AXE.get(),
				ItemRegistry.MOLTEN_SHOVEL.get(),
				ItemRegistry.MOLTEN_HOE.get());

		tag(IWItemTagGroups.TESLA_TOOLS).add(
				ItemRegistry.TESLA_SWORD.get(),
				ItemRegistry.TESLA_PICKAXE.get(),
				ItemRegistry.TESLA_AXE.get(),
				ItemRegistry.TESLA_SHOVEL.get(),
				ItemRegistry.TESLA_HOE.get());

		tag(IWItemTagGroups.VENTUS_TOOLS).add(
				ItemRegistry.VENTUS_SWORD.get(),
				ItemRegistry.VENTUS_PICKAXE.get(),
				ItemRegistry.VENTUS_AXE.get(),
				ItemRegistry.VENTUS_SHOVEL.get(),
				ItemRegistry.VENTUS_HOE.get());

		tag(IWItemTagGroups.ASTRAL_TOOLS).add(
				ItemRegistry.ASTRAL_SWORD.get(),
				ItemRegistry.ASTRAL_PICKAXE.get(),
				ItemRegistry.ASTRAL_AXE.get(),
				ItemRegistry.ASTRAL_SHOVEL.get(),
				ItemRegistry.ASTRAL_HOE.get());

		tag(IWItemTagGroups.STARSTORM_TOOLS).add(
				ItemRegistry.STARSTORM_SWORD.get(),
				ItemRegistry.STARSTORM_PICKAXE.get(),
				ItemRegistry.STARSTORM_AXE.get(),
				ItemRegistry.STARSTORM_SHOVEL.get(),
				ItemRegistry.STARSTORM_HOE.get());

		// Pike tags
		tag(IWItemTagGroups.PIKES).add(
				ItemRegistry.WOODEN_PIKE.get(),
				ItemRegistry.STONE_PIKE.get(),
				ItemRegistry.GOLDEN_PIKE.get(),
				ItemRegistry.COPPER_PIKE.get(),
				ItemRegistry.IRON_PIKE.get(),
				ItemRegistry.COBALT_PIKE.get(),
				ItemRegistry.DIAMOND_PIKE.get(),
				ItemRegistry.NETHERITE_PIKE.get(),
				ItemRegistry.MOLTEN_PIKE.get(),
				ItemRegistry.TESLA_PIKE.get(),
				ItemRegistry.VENTUS_PIKE.get(),
				ItemRegistry.ASTRAL_PIKE.get(),
				ItemRegistry.STARSTORM_PIKE.get());

		// Gauntlet tags
		tag(IWItemTagGroups.GAUNTLETS).add(
				ItemRegistry.WOODEN_GAUNTLET.get(),
				ItemRegistry.STONE_GAUNTLET.get(),
				ItemRegistry.GOLDEN_GAUNTLET.get(),
				ItemRegistry.COPPER_GAUNTLET.get(),
				ItemRegistry.IRON_GAUNTLET.get(),
				ItemRegistry.COBALT_GAUNTLET.get(),
				ItemRegistry.DIAMOND_GAUNTLET.get(),
				ItemRegistry.NETHERITE_GAUNTLET.get(),
				ItemRegistry.MOLTEN_GAUNTLET.get(),
				ItemRegistry.TESLA_GAUNTLET.get(),
				ItemRegistry.VENTUS_GAUNTLET.get(),
				ItemRegistry.ASTRAL_GAUNTLET.get(),
				ItemRegistry.STARSTORM_GAUNTLET.get());

		// Commander Pedestal Augment tags
		tag(IWItemTagGroups.COMMANDER_PEDESTAL_AUGMENTS).add(
				ItemRegistry.PEDESTAL_AUGMENT_SPEED.get(),
				ItemRegistry.PEDESTAL_AUGMENT_ARMOR.get(),
				ItemRegistry.PEDESTAL_AUGMENT_ENCHANTMENT.get(),
				ItemRegistry.PEDESTAL_AUGMENT_CAPACITY.get());

		// Firearm tags
		for (DeferredHolder<Item, ? extends Item> item : ItemRegistry.ITEMS.getEntries()) {
			if (item.get() instanceof AbstractGunItem gun) {
				tag(IWItemTagGroups.FIREARMS).add(gun);
			}
		}

		// Ranged weapon tags
		tag(IWItemTagGroups.RANGED_WEAPONS)
				.addTag(IWItemTagGroups.FIREARMS)
				.add(Items.BOW, Items.CROSSBOW);

		// Weapon and tools tags
		tag(IWItemTagGroups.WEAPONS_AND_TOOLS)
				.addTag(Tags.Items.TOOLS)
				.addTag(IWItemTagGroups.RANGED_WEAPONS);

		// Staff tags
		tag(IWItemTagGroups.METEOR_STAFFS).add(ItemRegistry.METEOR_STAFF.get());
		tag(IWItemTagGroups.CURSED_SIGHT_STAFFS).add(ItemRegistry.CURSED_SIGHT_STAFF.get());

		// Armor tags
		tag(IWItemTagGroups.PADDED_LEATHER).add(
				ItemRegistry.PADDED_LEATHER_HELMET.get(),
				ItemRegistry.PADDED_LEATHER_CHESTPLATE.get(),
				ItemRegistry.PADDED_LEATHER_LEGGINGS.get(),
				ItemRegistry.PADDED_LEATHER_BOOTS.get());
	}

	/**
	 * Add tags under the Minecraft namespace
	 */
	private void addMinecraftTags() {
		// Copy item tags from block tags
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
		for (Item item : CustomDataGenerator.ALL_ITEMS) {
			if (item.getDescriptionId().contains("arrow")) {
				tag(ItemTags.ARROWS).add(item);
			}
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
		for (DeferredHolder<Item, ? extends Item> item : ItemRegistry.ITEMS.getEntries()) {
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

		// Music discs tag
		for (DeferredHolder<Item, ? extends Item> item : ItemRegistry.ITEMS.getEntries()) {
			if (item.get() instanceof RecordItem record) {
				tag(ItemTags.MUSIC_DISCS).add(record);
			}
		}

		// Dyeable items tag
		tag(ItemTags.DYEABLE).add(
				ItemRegistry.PADDED_LEATHER_HELMET.get(),
				ItemRegistry.PADDED_LEATHER_CHESTPLATE.get(),
				ItemRegistry.PADDED_LEATHER_LEGGINGS.get(),
				ItemRegistry.PADDED_LEATHER_BOOTS.get()
		);
	}
}