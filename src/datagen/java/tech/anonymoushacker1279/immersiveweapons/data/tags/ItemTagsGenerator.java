package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.gun.AbstractGunItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;

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
		copy(CommonBlockTagGroups.POTASSIUM_NITRATE_ORES, CommonItemTagGroups.POTASSIUM_NITRATE_ORES);
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
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.VOID_INGOTS);

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

		tag(Tags.Items.TOOLS_BOW).add(
				ItemRegistry.ICE_BOW.get(),
				ItemRegistry.DRAGONS_BREATH_BOW.get(),
				ItemRegistry.AURORA_BOW.get());

		// Food tags
		tag(Tags.Items.FOODS).add(
				ItemRegistry.MRE.get(),
				ItemRegistry.CHOCOLATE_BAR.get(),
				ItemRegistry.EXPLOSIVE_CHOCOLATE_BAR.get(),
				ItemRegistry.MOLDY_BREAD.get());
		tag(Tags.Items.FOODS_CANDY).add(
				ItemRegistry.CHOCOLATE_BAR.get(),
				ItemRegistry.EXPLOSIVE_CHOCOLATE_BAR.get());
		tag(Tags.Items.FOODS_BREAD).add(ItemRegistry.MOLDY_BREAD.get());
		tag(Tags.Items.FOODS_FOOD_POISONING).add(ItemRegistry.MOLDY_BREAD.get());
	}

	/**
	 * Add tags under the Immersive Weapons namespace
	 */
	@SuppressWarnings("unchecked")
	private void addImmersiveWeaponsTags() {
		// Copy item tags from block tags
		copy(IWBlockTagGroups.BURNED_OAK_LOGS, IWItemTagGroups.BURNED_OAK_LOGS);
		copy(IWBlockTagGroups.STARDUST_LOGS, IWItemTagGroups.STARDUST_LOGS);
		copy(IWBlockTagGroups.ELECTRIC_ORES, IWItemTagGroups.ELECTRIC_ORES);
		copy(IWBlockTagGroups.MOLTEN_ORES, IWItemTagGroups.MOLTEN_ORES);
		copy(IWBlockTagGroups.VENTUS_ORES, IWItemTagGroups.VENTUS_ORES);
		copy(IWBlockTagGroups.ASTRAL_ORES, IWItemTagGroups.ASTRAL_ORES);
		copy(IWBlockTagGroups.VOID_ORES, IWItemTagGroups.VOID_ORES);
		copy(Blocks.ORES, Tags.Items.ORES);

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

		tag(IWItemTagGroups.DRAGON_FIREBALLS).add(ItemRegistry.DRAGON_FIREBALL.get());

		// Ingot tags
		tag(IWItemTagGroups.MOLTEN_INGOTS).add(ItemRegistry.MOLTEN_INGOT.get());
		tag(IWItemTagGroups.ELECTRIC_INGOTS).add(ItemRegistry.ELECTRIC_INGOT.get());
		tag(IWItemTagGroups.TESLA_INGOTS).add(ItemRegistry.TESLA_INGOT.get());
		tag(IWItemTagGroups.ASTRAL_INGOTS).add(ItemRegistry.ASTRAL_INGOT.get());
		tag(IWItemTagGroups.STARSTORM_INGOTS).add(ItemRegistry.STARSTORM_INGOT.get());
		tag(IWItemTagGroups.VOID_INGOTS).add(ItemRegistry.VOID_INGOT.get());

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
		tag(IWItemTagGroups.ACCESSORIES).add(
				ItemRegistry.SATCHEL.get(),
				ItemRegistry.POWDER_HORN.get(),
				ItemRegistry.BERSERKERS_AMULET.get(),
				ItemRegistry.HANS_BLESSING.get(),
				ItemRegistry.CELESTIAL_SPIRIT.get(),
				ItemRegistry.BLADEMASTER_EMBLEM.get(),
				ItemRegistry.DEADEYE_PENDANT.get(),
				ItemRegistry.BLOATED_HEART.get(),
				ItemRegistry.NETHERITE_SHIELD.get(),
				ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.get(),
				ItemRegistry.IRON_FIST.get(),
				ItemRegistry.GLOVE_OF_RAPID_SWINGING.get(),
				ItemRegistry.HAND_OF_DOOM.get(),
				ItemRegistry.COPPER_RING.get(),
				ItemRegistry.IRON_RING.get(),
				ItemRegistry.COBALT_RING.get(),
				ItemRegistry.GOLDEN_RING.get(),
				ItemRegistry.AMETHYST_RING.get(),
				ItemRegistry.EMERALD_RING.get(),
				ItemRegistry.DIAMOND_RING.get(),
				ItemRegistry.NETHERITE_RING.get(),
				ItemRegistry.DEATH_GEM_RING.get(),
				ItemRegistry.MEDAL_OF_ADEQUACY.get(),
				ItemRegistry.DEPTH_CHARM.get(),
				ItemRegistry.REINFORCED_DEPTH_CHARM.get(),
				ItemRegistry.INSOMNIA_AMULET.get(),
				ItemRegistry.GOGGLES.get(),
				ItemRegistry.LAVA_GOGGLES.get(),
				ItemRegistry.NIGHT_VISION_GOGGLES.get(),
				ItemRegistry.AGILITY_BRACELET.get(),
				ItemRegistry.BLOODY_CLOTH.get(),
				ItemRegistry.ANCIENT_SCROLL.get(),
				ItemRegistry.HOLY_MANTLE.get(),
				ItemRegistry.VENSTRAL_JAR.get(),
				ItemRegistry.SUPER_BLANKET_CAPE.get(),
				ItemRegistry.MEDAL_OF_HONOR.get(),
				ItemRegistry.MEDAL_OF_DISHONOR.get());

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

		tag(IWItemTagGroups.VOID_TOOLS).add(
				ItemRegistry.VOID_SWORD.get(),
				ItemRegistry.VOID_PICKAXE.get(),
				ItemRegistry.VOID_AXE.get(),
				ItemRegistry.VOID_SHOVEL.get(),
				ItemRegistry.VOID_HOE.get());

		// Pike tags
		for (DeferredHolder<Item, ? extends Item> item : ItemRegistry.ITEMS.getEntries()) {
			if (item.get() instanceof PikeItem pike) {
				tag(IWItemTagGroups.PIKES).add(pike);
			}
		}

		// Gauntlet tags
		for (DeferredHolder<Item, ? extends Item> item : ItemRegistry.ITEMS.getEntries()) {
			if (item.get() instanceof GauntletItem gauntlet) {
				tag(IWItemTagGroups.GAUNTLETS).add(gauntlet);
			}
		}

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
				.addTags(IWItemTagGroups.STAFFS)
				.addTag(Tags.Items.TOOLS_BOW);

		// Weapon and tools tags
		tag(IWItemTagGroups.WEAPONS_AND_TOOLS)
				.addTag(Tags.Items.TOOLS)
				.addTags(IWItemTagGroups.PIKES)
				.addTags(IWItemTagGroups.GAUNTLETS)
				.addTag(IWItemTagGroups.RANGED_WEAPONS);

		// Staff tags
		tag(IWItemTagGroups.METEOR_STAFFS).add(ItemRegistry.METEOR_STAFF.get());
		tag(IWItemTagGroups.CURSED_SIGHT_STAFFS).add(ItemRegistry.CURSED_SIGHT_STAFF.get());
		tag(IWItemTagGroups.SCULK_STAFFS).add(ItemRegistry.SCULK_STAFF.get());
		tag(IWItemTagGroups.RECOVERY_STAFFS).add(ItemRegistry.RECOVERY_STAFF.get());
		tag(IWItemTagGroups.VENTUS_STAFFS).add(ItemRegistry.VENTUS_STAFF.get());
		tag(IWItemTagGroups.STAFFS)
				.addTag(IWItemTagGroups.METEOR_STAFFS)
				.addTag(IWItemTagGroups.CURSED_SIGHT_STAFFS)
				.addTag(IWItemTagGroups.SCULK_STAFFS)
				.addTag(IWItemTagGroups.RECOVERY_STAFFS)
				.addTag(IWItemTagGroups.VENTUS_STAFFS);

		// Armor tags
		tag(IWItemTagGroups.MOLTEN_ARMOR).add(
				ItemRegistry.MOLTEN_HELMET.get(),
				ItemRegistry.MOLTEN_CHESTPLATE.get(),
				ItemRegistry.MOLTEN_LEGGINGS.get(),
				ItemRegistry.MOLTEN_BOOTS.get());

		tag(IWItemTagGroups.TESLA_ARMOR).add(
				ItemRegistry.TESLA_HELMET.get(),
				ItemRegistry.TESLA_CHESTPLATE.get(),
				ItemRegistry.TESLA_LEGGINGS.get(),
				ItemRegistry.TESLA_BOOTS.get());

		tag(IWItemTagGroups.VENTUS_ARMOR).add(
				ItemRegistry.VENTUS_HELMET.get(),
				ItemRegistry.VENTUS_CHESTPLATE.get(),
				ItemRegistry.VENTUS_LEGGINGS.get(),
				ItemRegistry.VENTUS_BOOTS.get());

		tag(IWItemTagGroups.ASTRAL_ARMOR).add(
				ItemRegistry.ASTRAL_HELMET.get(),
				ItemRegistry.ASTRAL_CHESTPLATE.get(),
				ItemRegistry.ASTRAL_LEGGINGS.get(),
				ItemRegistry.ASTRAL_BOOTS.get());

		tag(IWItemTagGroups.STARSTORM_ARMOR).add(
				ItemRegistry.STARSTORM_HELMET.get(),
				ItemRegistry.STARSTORM_CHESTPLATE.get(),
				ItemRegistry.STARSTORM_LEGGINGS.get(),
				ItemRegistry.STARSTORM_BOOTS.get());

		tag(IWItemTagGroups.PADDED_LEATHER).add(
				ItemRegistry.PADDED_LEATHER_HELMET.get(),
				ItemRegistry.PADDED_LEATHER_CHESTPLATE.get(),
				ItemRegistry.PADDED_LEATHER_LEGGINGS.get(),
				ItemRegistry.PADDED_LEATHER_BOOTS.get());

		tag(IWItemTagGroups.VOID_ARMOR).add(
				ItemRegistry.VOID_HELMET.get(),
				ItemRegistry.VOID_CHESTPLATE.get(),
				ItemRegistry.VOID_LEGGINGS.get(),
				ItemRegistry.VOID_BOOTS.get());
	}

	/**
	 * Add tags under the Minecraft namespace
	 */
	@SuppressWarnings("unchecked")
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

		// Dyeable items tag
		tag(ItemTags.DYEABLE).add(
				ItemRegistry.PADDED_LEATHER_HELMET.get(),
				ItemRegistry.PADDED_LEATHER_CHESTPLATE.get(),
				ItemRegistry.PADDED_LEATHER_LEGGINGS.get(),
				ItemRegistry.PADDED_LEATHER_BOOTS.get()
		);

		// Enchantable items tag
		tag(ItemTags.BOW_ENCHANTABLE).addTag(Tags.Items.TOOLS_BOW);
		tag(ItemTags.DURABILITY_ENCHANTABLE).addTags(
				IWItemTagGroups.PIKES,
				IWItemTagGroups.GAUNTLETS,
				IWItemTagGroups.FIREARMS,
				IWItemTagGroups.STAFFS
		);
		tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).addTag(IWItemTagGroups.PIKES);
		tag(ItemTags.WEAPON_ENCHANTABLE).addTags(
				IWItemTagGroups.PIKES,
				IWItemTagGroups.GAUNTLETS,
				IWItemTagGroups.FIREARMS,
				IWItemTagGroups.STAFFS
		);

		// Head tags
		tag(ItemTags.SKULLS).add(
				BlockItemRegistry.MINUTEMAN_HEAD_ITEM.get(),
				BlockItemRegistry.FIELD_MEDIC_HEAD_ITEM.get(),
				BlockItemRegistry.DYING_SOLDIER_HEAD_ITEM.get(),
				BlockItemRegistry.WANDERING_WARRIOR_HEAD_ITEM.get(),
				BlockItemRegistry.HANS_HEAD_ITEM.get(),
				BlockItemRegistry.STORM_CREEPER_HEAD_ITEM.get());

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
	}
}