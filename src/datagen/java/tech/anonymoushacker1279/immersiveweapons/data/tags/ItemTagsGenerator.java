package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class ItemTagsGenerator extends BlockTagCopyingItemTagProvider {

	public static final TagKey<Item> SLABS = createItemTag("slabs");
	public static final TagKey<Item> STAIRS = createItemTag("stairs");
	public static final TagKey<Item> SMALL_FLOWERS = createItemTag("small_flowers");

	public ItemTagsGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
		super(output, lookupProvider, blockTags, ImmersiveWeapons.MOD_ID);
	}

	private static TagKey<Item> createItemTag(String tag) {
		return TagKey.create(Registries.ITEM, Identifier.withDefaultNamespace(tag));
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		addCommonTags();
		addImmersiveWeaponsTags();
		addMinecraftTags();
	}

	/// Add tags under the Forge namespace
	@SuppressWarnings("unchecked")
	private void addCommonTags() {
		// Ingot tags
		tag(CommonItemTagGroups.COBALT_INGOTS).add(ItemRegistry.COBALT_INGOT.getKey());
		tag(CommonItemTagGroups.METAL_INGOTS).addTags(
				CommonItemTagGroups.COBALT_INGOTS,
				Tags.Items.INGOTS_COPPER,
				Tags.Items.INGOTS_IRON,
				Tags.Items.INGOTS_GOLD);
		tag(Tags.Items.INGOTS).addTag(CommonItemTagGroups.METAL_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.MOLTEN_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.TESLA_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.STARSTORM_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.ASTRAL_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.VOID_INGOTS);
		tag(Tags.Items.INGOTS).addTag(IWItemTagGroups.HANSIUM_INGOTS);

		// Nugget tags
		tag(CommonItemTagGroups.COBALT_NUGGETS).add(ItemRegistry.COBALT_NUGGET.getKey());
		tag(CommonItemTagGroups.METAL_NUGGETS).addTags(
				CommonItemTagGroups.COBALT_NUGGETS,
				CommonItemTagGroups.COPPER_NUGGETS,
				Tags.Items.NUGGETS_IRON,
				Tags.Items.NUGGETS_GOLD);
		tag(Tags.Items.NUGGETS).addTag(CommonItemTagGroups.METAL_NUGGETS);
		tag(Tags.Items.NUGGETS).addTag(IWItemTagGroups.TESLA_NUGGETS);
		tag(Tags.Items.NUGGETS).addTag(IWItemTagGroups.ASTRAL_NUGGETS);

		// Dust tags
		tag(CommonItemTagGroups.SULFUR_DUSTS).add(ItemRegistry.SULFUR_DUST.getKey());

		tag(Tags.Items.TOOLS_BOW).add(
				ItemRegistry.ICE_BOW.getKey(),
				ItemRegistry.DRAGONS_BREATH_BOW.getKey(),
				ItemRegistry.AURORA_BOW.getKey());

		// Food tags
		tag(Tags.Items.FOODS).add(
				ItemRegistry.MRE.getKey(),
				ItemRegistry.CHOCOLATE_BAR.getKey(),
				ItemRegistry.MOLDY_BREAD.getKey());
		tag(Tags.Items.FOODS_CANDY).add(ItemRegistry.CHOCOLATE_BAR.getKey());
		tag(Tags.Items.FOODS_BREAD).add(ItemRegistry.MOLDY_BREAD.getKey());
		tag(Tags.Items.FOODS_FOOD_POISONING).add(ItemRegistry.MOLDY_BREAD.getKey());

		copy(CommonBlockTagGroups.COBALT_ORES, CommonItemTagGroups.COBALT_ORES);
		copy(CommonBlockTagGroups.SULFUR_ORES, CommonItemTagGroups.SULFUR_ORES);
		copy(CommonBlockTagGroups.POTASSIUM_NITRATE_ORES, CommonItemTagGroups.POTASSIUM_NITRATE_ORES);
		copy(Tags.Blocks.ORES, Tags.Items.ORES);
		copy(Tags.Blocks.NATURAL_LOGS, Tags.Items.NATURAL_LOGS);
		copy(BlockTags.FENCES, Tags.Items.FENCES);
		copy(BlockTags.SMALL_FLOWERS, Tags.Items.FLOWERS_SMALL);
	}

	/// Add tags under the Immersive Weapons namespace
	@SuppressWarnings("unchecked")
	private void addImmersiveWeaponsTags() {
		// Projectile tags
		tag(IWItemTagGroups.FLARES).add(ItemRegistry.FLARE.getKey());
		tag(IWItemTagGroups.MUSKET_BALLS).add(
				ItemRegistry.WOODEN_MUSKET_BALL.getKey(),
				ItemRegistry.STONE_MUSKET_BALL.getKey(),
				ItemRegistry.GOLDEN_MUSKET_BALL.getKey(),
				ItemRegistry.COPPER_MUSKET_BALL.getKey(),
				ItemRegistry.IRON_MUSKET_BALL.getKey(),
				ItemRegistry.COBALT_MUSKET_BALL.getKey(),
				ItemRegistry.DIAMOND_MUSKET_BALL.getKey(),
				ItemRegistry.NETHERITE_MUSKET_BALL.getKey(),
				ItemRegistry.MOLTEN_MUSKET_BALL.getKey(),
				ItemRegistry.TESLA_MUSKET_BALL.getKey(),
				ItemRegistry.VENTUS_MUSKET_BALL.getKey(),
				ItemRegistry.ASTRAL_MUSKET_BALL.getKey(),
				ItemRegistry.STARSTORM_MUSKET_BALL.getKey(),
				ItemRegistry.VOID_MUSKET_BALL.getKey());
		tag(IWItemTagGroups.CANNONBALLS).add(
				ItemRegistry.CANNONBALL.getKey(),
				ItemRegistry.EXPLOSIVE_CANNONBALL.getKey());
		tag(IWItemTagGroups.DRAGON_FIREBALLS).add(ItemRegistry.DRAGON_FIREBALL.getKey());

		// Ingot tags
		tag(IWItemTagGroups.MOLTEN_INGOTS).add(ItemRegistry.MOLTEN_INGOT.getKey());
		tag(IWItemTagGroups.TESLA_INGOTS).add(ItemRegistry.TESLA_INGOT.getKey());
		tag(IWItemTagGroups.ASTRAL_INGOTS).add(ItemRegistry.ASTRAL_INGOT.getKey());
		tag(IWItemTagGroups.STARSTORM_INGOTS).add(ItemRegistry.STARSTORM_INGOT.getKey());
		tag(IWItemTagGroups.VOID_INGOTS).add(ItemRegistry.VOID_INGOT.getKey());
		tag(IWItemTagGroups.HANSIUM_INGOTS).add(ItemRegistry.HANSIUM_INGOT.getKey());

		// Shard tags
		tag(IWItemTagGroups.MOLTEN_SHARDS).add(ItemRegistry.MOLTEN_SHARD.getKey());
		tag(IWItemTagGroups.VENTUS_SHARDS).add(ItemRegistry.VENTUS_SHARD.getKey());
		tag(IWItemTagGroups.DIAMOND_SHARDS).add(ItemRegistry.DIAMOND_SHARD.getKey());
		tag(IWItemTagGroups.STONE_SHARDS).add(ItemRegistry.STONE_SHARD.getKey());
		tag(IWItemTagGroups.WOODEN_SHARDS).add(ItemRegistry.WOODEN_SHARD.getKey());
		tag(IWItemTagGroups.STARSTORM_SHARDS).add(ItemRegistry.STARSTORM_SHARD.getKey());
		tag(IWItemTagGroups.SHARDS).addTags(
				IWItemTagGroups.MOLTEN_SHARDS,
				IWItemTagGroups.VENTUS_SHARDS,
				IWItemTagGroups.DIAMOND_SHARDS,
				IWItemTagGroups.STONE_SHARDS,
				IWItemTagGroups.WOODEN_SHARDS,
				IWItemTagGroups.STARSTORM_SHARDS);

		// Nugget tags
		tag(IWItemTagGroups.TESLA_NUGGETS).add(ItemRegistry.TESLA_NUGGET.getKey());
		tag(IWItemTagGroups.ASTRAL_NUGGETS).add(ItemRegistry.ASTRAL_NUGGET.getKey());

		// Rod tags
		tag(IWItemTagGroups.OBSIDIAN_RODS).add(ItemRegistry.OBSIDIAN_ROD.getKey());

		// Accessory tags
		tag(IWItemTagGroups.ACCESSORIES).add(
				ItemRegistry.SATCHEL.getKey(),
				ItemRegistry.POWDER_HORN.getKey(),
				ItemRegistry.BERSERKERS_AMULET.getKey(),
				ItemRegistry.HANS_BLESSING.getKey(),
				ItemRegistry.CELESTIAL_SPIRIT.getKey(),
				ItemRegistry.BLADEMASTER_EMBLEM.getKey(),
				ItemRegistry.DEADEYE_PENDANT.getKey(),
				ItemRegistry.BLOATED_HEART.getKey(),
				ItemRegistry.NETHERITE_SHIELD.getKey(),
				ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.getKey(),
				ItemRegistry.IRON_FIST.getKey(),
				ItemRegistry.GLOVE_OF_RAPID_SWINGING.getKey(),
				ItemRegistry.HAND_OF_DOOM.getKey(),
				ItemRegistry.COPPER_RING.getKey(),
				ItemRegistry.IRON_RING.getKey(),
				ItemRegistry.COBALT_RING.getKey(),
				ItemRegistry.GOLDEN_RING.getKey(),
				ItemRegistry.AMETHYST_RING.getKey(),
				ItemRegistry.EMERALD_RING.getKey(),
				ItemRegistry.DIAMOND_RING.getKey(),
				ItemRegistry.NETHERITE_RING.getKey(),
				ItemRegistry.DEATH_GEM_RING.getKey(),
				ItemRegistry.MEDAL_OF_ADEQUACY.getKey(),
				ItemRegistry.DEPTH_CHARM.getKey(),
				ItemRegistry.REINFORCED_DEPTH_CHARM.getKey(),
				ItemRegistry.INSOMNIA_AMULET.getKey(),
				ItemRegistry.GOGGLES.getKey(),
				ItemRegistry.LAVA_GOGGLES.getKey(),
				ItemRegistry.NIGHT_VISION_GOGGLES.getKey(),
				ItemRegistry.AGILITY_BRACELET.getKey(),
				ItemRegistry.BLOODY_CLOTH.getKey(),
				ItemRegistry.ANCIENT_SCROLL.getKey(),
				ItemRegistry.HOLY_MANTLE.getKey(),
				ItemRegistry.VENSTRAL_JAR.getKey(),
				ItemRegistry.SUPER_BLANKET_CAPE.getKey(),
				ItemRegistry.MEDAL_OF_HONOR.getKey(),
				ItemRegistry.MEDAL_OF_DISHONOR.getKey());

		// Smoke grenade tags
		tag(IWItemTagGroups.SMOKE_GRENADES).add(
				ItemRegistry.SMOKE_GRENADE.getKey(),
				ItemRegistry.SMOKE_GRENADE_RED.getKey(),
				ItemRegistry.SMOKE_GRENADE_GREEN.getKey(),
				ItemRegistry.SMOKE_GRENADE_BLUE.getKey(),
				ItemRegistry.SMOKE_GRENADE_PURPLE.getKey(),
				ItemRegistry.SMOKE_GRENADE_YELLOW.getKey());

		// Tool tags
		tag(IWItemTagGroups.MOLTEN_TOOLS).add(
				ItemRegistry.MOLTEN_SWORD.getKey(),
				ItemRegistry.MOLTEN_PICKAXE.getKey(),
				ItemRegistry.MOLTEN_AXE.getKey(),
				ItemRegistry.MOLTEN_SHOVEL.getKey(),
				ItemRegistry.MOLTEN_HOE.getKey(),
				ItemRegistry.MOLTEN_SPEAR.getKey());

		tag(IWItemTagGroups.TESLA_TOOLS).add(
				ItemRegistry.TESLA_SWORD.getKey(),
				ItemRegistry.TESLA_PICKAXE.getKey(),
				ItemRegistry.TESLA_AXE.getKey(),
				ItemRegistry.TESLA_SHOVEL.getKey(),
				ItemRegistry.TESLA_HOE.getKey(),
				ItemRegistry.TESLA_SPEAR.getKey());

		tag(IWItemTagGroups.VENTUS_TOOLS).add(
				ItemRegistry.VENTUS_SWORD.getKey(),
				ItemRegistry.VENTUS_PICKAXE.getKey(),
				ItemRegistry.VENTUS_AXE.getKey(),
				ItemRegistry.VENTUS_SHOVEL.getKey(),
				ItemRegistry.VENTUS_HOE.getKey(),
				ItemRegistry.VENTUS_SPEAR.getKey());

		tag(IWItemTagGroups.ASTRAL_TOOLS).add(
				ItemRegistry.ASTRAL_SWORD.getKey(),
				ItemRegistry.ASTRAL_PICKAXE.getKey(),
				ItemRegistry.ASTRAL_AXE.getKey(),
				ItemRegistry.ASTRAL_SHOVEL.getKey(),
				ItemRegistry.ASTRAL_HOE.getKey(),
				ItemRegistry.ASTRAL_SPEAR.getKey());

		tag(IWItemTagGroups.STARSTORM_TOOLS).add(
				ItemRegistry.STARSTORM_SWORD.getKey(),
				ItemRegistry.STARSTORM_PICKAXE.getKey(),
				ItemRegistry.STARSTORM_AXE.getKey(),
				ItemRegistry.STARSTORM_SHOVEL.getKey(),
				ItemRegistry.STARSTORM_HOE.getKey(),
				ItemRegistry.STARSTORM_SPEAR.getKey());

		tag(IWItemTagGroups.VOID_TOOLS).add(
				ItemRegistry.VOID_SWORD.getKey(),
				ItemRegistry.VOID_PICKAXE.getKey(),
				ItemRegistry.VOID_AXE.getKey(),
				ItemRegistry.VOID_SHOVEL.getKey(),
				ItemRegistry.VOID_HOE.getKey(),
				ItemRegistry.VOID_SPEAR.getKey());

		// Gauntlet tags
		tag(IWItemTagGroups.GAUNTLETS).add(
				ItemRegistry.WOODEN_GAUNTLET.getKey(),
				ItemRegistry.STONE_GAUNTLET.getKey(),
				ItemRegistry.GOLDEN_GAUNTLET.getKey(),
				ItemRegistry.COPPER_GAUNTLET.getKey(),
				ItemRegistry.IRON_GAUNTLET.getKey(),
				ItemRegistry.COBALT_GAUNTLET.getKey(),
				ItemRegistry.DIAMOND_GAUNTLET.getKey(),
				ItemRegistry.NETHERITE_GAUNTLET.getKey(),
				ItemRegistry.MOLTEN_GAUNTLET.getKey(),
				ItemRegistry.TESLA_GAUNTLET.getKey(),
				ItemRegistry.VENTUS_GAUNTLET.getKey(),
				ItemRegistry.ASTRAL_GAUNTLET.getKey(),
				ItemRegistry.STARSTORM_GAUNTLET.getKey(),
				ItemRegistry.VOID_GAUNTLET.getKey());

		// Maul tags
		tag(IWItemTagGroups.MAULS).add(
				ItemRegistry.WOODEN_MAUL.getKey(),
				ItemRegistry.STONE_MAUL.getKey(),
				ItemRegistry.GOLDEN_MAUL.getKey(),
				ItemRegistry.IRON_MAUL.getKey(),
				ItemRegistry.COBALT_MAUL.getKey(),
				ItemRegistry.DIAMOND_MAUL.getKey(),
				ItemRegistry.NETHERITE_MAUL.getKey(),
				ItemRegistry.MOLTEN_MAUL.getKey(),
				ItemRegistry.TESLA_MAUL.getKey(),
				ItemRegistry.VENTUS_MAUL.getKey(),
				ItemRegistry.ASTRAL_MAUL.getKey(),
				ItemRegistry.STARSTORM_MAUL.getKey(),
				ItemRegistry.VOID_MAUL.getKey());

		// Commander Pedestal Augment tags
		tag(IWItemTagGroups.COMMANDER_PEDESTAL_AUGMENTS).add(
				ItemRegistry.PEDESTAL_AUGMENT_SPEED.getKey(),
				ItemRegistry.PEDESTAL_AUGMENT_ARMOR.getKey(),
				ItemRegistry.PEDESTAL_AUGMENT_ENCHANTMENT.getKey(),
				ItemRegistry.PEDESTAL_AUGMENT_CAPACITY.getKey());

		// Firearm tags
		tag(IWItemTagGroups.FIREARMS).add(
				ItemRegistry.FLINTLOCK_PISTOL.getKey(),
				ItemRegistry.BLUNDERBUSS.getKey(),
				ItemRegistry.MUSKET.getKey(),
				ItemRegistry.FLARE_GUN.getKey(),
				ItemRegistry.HAND_CANNON.getKey(),
				ItemRegistry.DRAGONS_BREATH_CANNON.getKey());

		// Ranged weapon tags
		tag(IWItemTagGroups.RANGED_WEAPONS)
				.addTag(IWItemTagGroups.FIREARMS)
				.addTag(Tags.Items.TOOLS_BOW);

		// Weapon and tools tags
		tag(IWItemTagGroups.WEAPONS_AND_TOOLS)
				.addTag(Tags.Items.TOOLS)
				.addTags(IWItemTagGroups.GAUNTLETS)
				.addTags(IWItemTagGroups.MAULS)
				.addTag(IWItemTagGroups.RANGED_WEAPONS);

		// Staff tags
		tag(IWItemTagGroups.METEOR_STAFFS).add(ItemRegistry.METEOR_STAFF.getKey());
		tag(IWItemTagGroups.CURSED_SIGHT_STAFFS).add(ItemRegistry.CURSED_SIGHT_STAFF.getKey());
		tag(IWItemTagGroups.SCULK_STAFFS).add(ItemRegistry.SCULK_STAFF.getKey());
		tag(IWItemTagGroups.RECOVERY_STAFFS).add(ItemRegistry.RECOVERY_STAFF.getKey());
		tag(IWItemTagGroups.VENTUS_STAFFS).add(ItemRegistry.VENTUS_STAFF.getKey());
		tag(IWItemTagGroups.STAFFS)
				.addTag(IWItemTagGroups.METEOR_STAFFS)
				.addTag(IWItemTagGroups.CURSED_SIGHT_STAFFS)
				.addTag(IWItemTagGroups.SCULK_STAFFS)
				.addTag(IWItemTagGroups.RECOVERY_STAFFS)
				.addTag(IWItemTagGroups.VENTUS_STAFFS);

		// Armor tags
		tag(IWItemTagGroups.MOLTEN_ARMOR).add(
				ItemRegistry.MOLTEN_HELMET.getKey(),
				ItemRegistry.MOLTEN_CHESTPLATE.getKey(),
				ItemRegistry.MOLTEN_LEGGINGS.getKey(),
				ItemRegistry.MOLTEN_BOOTS.getKey());

		tag(IWItemTagGroups.TESLA_ARMOR).add(
				ItemRegistry.TESLA_HELMET.getKey(),
				ItemRegistry.TESLA_CHESTPLATE.getKey(),
				ItemRegistry.TESLA_LEGGINGS.getKey(),
				ItemRegistry.TESLA_BOOTS.getKey());

		tag(IWItemTagGroups.VENTUS_ARMOR).add(
				ItemRegistry.VENTUS_HELMET.getKey(),
				ItemRegistry.VENTUS_CHESTPLATE.getKey(),
				ItemRegistry.VENTUS_LEGGINGS.getKey(),
				ItemRegistry.VENTUS_BOOTS.getKey());

		tag(IWItemTagGroups.ASTRAL_ARMOR).add(
				ItemRegistry.ASTRAL_HELMET.getKey(),
				ItemRegistry.ASTRAL_CHESTPLATE.getKey(),
				ItemRegistry.ASTRAL_LEGGINGS.getKey(),
				ItemRegistry.ASTRAL_BOOTS.getKey());

		tag(IWItemTagGroups.STARSTORM_ARMOR).add(
				ItemRegistry.STARSTORM_HELMET.getKey(),
				ItemRegistry.STARSTORM_CHESTPLATE.getKey(),
				ItemRegistry.STARSTORM_LEGGINGS.getKey(),
				ItemRegistry.STARSTORM_BOOTS.getKey());

		tag(IWItemTagGroups.PADDED_LEATHER).add(
				ItemRegistry.PADDED_LEATHER_HELMET.getKey(),
				ItemRegistry.PADDED_LEATHER_CHESTPLATE.getKey(),
				ItemRegistry.PADDED_LEATHER_LEGGINGS.getKey(),
				ItemRegistry.PADDED_LEATHER_BOOTS.getKey());

		tag(IWItemTagGroups.VOID_ARMOR).add(
				ItemRegistry.VOID_HELMET.getKey(),
				ItemRegistry.VOID_CHESTPLATE.getKey(),
				ItemRegistry.VOID_LEGGINGS.getKey(),
				ItemRegistry.VOID_BOOTS.getKey());

		copy(IWBlockTagGroups.BURNED_OAK_LOGS, IWItemTagGroups.BURNED_OAK_LOGS);
		copy(IWBlockTagGroups.STARDUST_LOGS, IWItemTagGroups.STARDUST_LOGS);
		copy(IWBlockTagGroups.TESLA_ORES, IWItemTagGroups.TESLA_ORES);
		copy(IWBlockTagGroups.MOLTEN_ORES, IWItemTagGroups.MOLTEN_ORES);
		copy(IWBlockTagGroups.VENTUS_ORES, IWItemTagGroups.VENTUS_ORES);
		copy(IWBlockTagGroups.ASTRAL_ORES, IWItemTagGroups.ASTRAL_ORES);
		copy(IWBlockTagGroups.VOID_ORES, IWItemTagGroups.VOID_ORES);
	}

	/// Add tags under the Minecraft namespace
	@SuppressWarnings("unchecked")
	private void addMinecraftTags() {
		// Sign tags
		tag(ItemTags.SIGNS).add(BlockItemRegistry.BURNED_OAK_SIGN_ITEM.getKey(),
				BlockItemRegistry.STARDUST_SIGN_ITEM.getKey());

		// Arrow tags
		tag(ItemTags.ARROWS).add(
				ItemRegistry.WOODEN_ARROW.getKey(),
				ItemRegistry.STONE_ARROW.getKey(),
				ItemRegistry.GOLDEN_ARROW.getKey(),
				ItemRegistry.COPPER_ARROW.getKey(),
				ItemRegistry.IRON_ARROW.getKey(),
				ItemRegistry.COBALT_ARROW.getKey(),
				ItemRegistry.DIAMOND_ARROW.getKey(),
				ItemRegistry.NETHERITE_ARROW.getKey(),
				ItemRegistry.MOLTEN_ARROW.getKey(),
				ItemRegistry.TESLA_ARROW.getKey(),
				ItemRegistry.VENTUS_ARROW.getKey(),
				ItemRegistry.ASTRAL_ARROW.getKey(),
				ItemRegistry.STARSTORM_ARROW.getKey(),
				ItemRegistry.VOID_ARROW.getKey());

		// Boat tags
		tag(ItemTags.BOATS).add(ItemRegistry.BURNED_OAK_BOAT.getKey());
		tag(ItemTags.BOATS).add(ItemRegistry.STARDUST_BOAT.getKey());
		tag(ItemTags.CHEST_BOATS).add(ItemRegistry.BURNED_OAK_CHEST_BOAT.getKey());
		tag(ItemTags.CHEST_BOATS).add(ItemRegistry.STARDUST_CHEST_BOAT.getKey());

		// Non-flammable wood tag
		tag(ItemTags.NON_FLAMMABLE_WOOD).add(
				BlockItemRegistry.WARPED_TABLE_ITEM.getKey(),
				BlockItemRegistry.CRIMSON_TABLE_ITEM.getKey());

		// Trimmable armor tag
		tag(ItemTags.TRIMMABLE_ARMOR).addTags(
				IWItemTagGroups.MOLTEN_ARMOR,
				IWItemTagGroups.TESLA_ARMOR,
				IWItemTagGroups.VENTUS_ARMOR,
				IWItemTagGroups.ASTRAL_ARMOR,
				IWItemTagGroups.STARSTORM_ARMOR,
				IWItemTagGroups.VOID_ARMOR
		);

		// Beacon payment tag
		tag(ItemTags.BEACON_PAYMENT_ITEMS).add(
				ItemRegistry.COBALT_INGOT.getKey(),
				ItemRegistry.MOLTEN_INGOT.getKey(),
				ItemRegistry.TESLA_INGOT.getKey(),
				ItemRegistry.VENTUS_SHARD.getKey(),
				ItemRegistry.ASTRAL_INGOT.getKey(),
				ItemRegistry.STARSTORM_INGOT.getKey());

		// Cauldron remove dye tag
		tag(ItemTags.CAULDRON_CAN_REMOVE_DYE).add(
				ItemRegistry.PADDED_LEATHER_HELMET.getKey(),
				ItemRegistry.PADDED_LEATHER_CHESTPLATE.getKey(),
				ItemRegistry.PADDED_LEATHER_LEGGINGS.getKey(),
				ItemRegistry.PADDED_LEATHER_BOOTS.getKey()
		);

		// Freeze immune tag
		tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(
				ItemRegistry.PADDED_LEATHER_HELMET.getKey(),
				ItemRegistry.PADDED_LEATHER_CHESTPLATE.getKey(),
				ItemRegistry.PADDED_LEATHER_LEGGINGS.getKey(),
				ItemRegistry.PADDED_LEATHER_BOOTS.getKey()
		);

		// Enchantable items tag
		tag(ItemTags.BOW_ENCHANTABLE).addTag(Tags.Items.TOOLS_BOW);
		tag(ItemTags.DURABILITY_ENCHANTABLE).addTags(
				IWItemTagGroups.GAUNTLETS,
				IWItemTagGroups.MAULS,
				IWItemTagGroups.FIREARMS,
				IWItemTagGroups.STAFFS,
				Tags.Items.TOOLS_BOW
		);
		tag(ItemTags.WEAPON_ENCHANTABLE).addTags(
				IWItemTagGroups.GAUNTLETS,
				IWItemTagGroups.MAULS,
				IWItemTagGroups.FIREARMS,
				IWItemTagGroups.STAFFS
		);

		// Head tags
		tag(ItemTags.SKULLS).add(
				BlockItemRegistry.MINUTEMAN_HEAD_ITEM.getKey(),
				BlockItemRegistry.FIELD_MEDIC_HEAD_ITEM.getKey(),
				BlockItemRegistry.DYING_SOLDIER_HEAD_ITEM.getKey(),
				BlockItemRegistry.WANDERING_WARRIOR_HEAD_ITEM.getKey(),
				BlockItemRegistry.HANS_HEAD_ITEM.getKey(),
				BlockItemRegistry.STORM_CREEPER_HEAD_ITEM.getKey());

		// Spear tags
		tag(ItemTags.SPEARS).add(
				ItemRegistry.COBALT_SPEAR.getKey(),
				ItemRegistry.MOLTEN_SPEAR.getKey(),
				ItemRegistry.TESLA_SPEAR.getKey(),
				ItemRegistry.VENTUS_SPEAR.getKey(),
				ItemRegistry.ASTRAL_SPEAR.getKey(),
				ItemRegistry.STARSTORM_SPEAR.getKey(),
				ItemRegistry.VOID_SPEAR.getKey());

		tag(ItemTags.SWORDS).add(
				ItemRegistry.COBALT_SWORD.getKey(),
				ItemRegistry.MOLTEN_SWORD.getKey(),
				ItemRegistry.TESLA_SWORD.getKey(),
				ItemRegistry.VENTUS_SWORD.getKey(),
				ItemRegistry.ASTRAL_SWORD.getKey(),
				ItemRegistry.STARSTORM_SWORD.getKey(),
				ItemRegistry.VOID_SWORD.getKey(),
				ItemRegistry.THE_SWORD.getKey());
		tag(ItemTags.PICKAXES).add(
				ItemRegistry.COBALT_PICKAXE.getKey(),
				ItemRegistry.MOLTEN_PICKAXE.getKey(),
				ItemRegistry.TESLA_PICKAXE.getKey(),
				ItemRegistry.VENTUS_PICKAXE.getKey(),
				ItemRegistry.ASTRAL_PICKAXE.getKey(),
				ItemRegistry.STARSTORM_PICKAXE.getKey(),
				ItemRegistry.VOID_PICKAXE.getKey());
		tag(ItemTags.AXES).add(
				ItemRegistry.COBALT_AXE.getKey(),
				ItemRegistry.MOLTEN_AXE.getKey(),
				ItemRegistry.TESLA_AXE.getKey(),
				ItemRegistry.VENTUS_AXE.getKey(),
				ItemRegistry.ASTRAL_AXE.getKey(),
				ItemRegistry.STARSTORM_AXE.getKey(),
				ItemRegistry.VOID_AXE.getKey());
		tag(ItemTags.SHOVELS).add(
				ItemRegistry.COBALT_SHOVEL.getKey(),
				ItemRegistry.MOLTEN_SHOVEL.getKey(),
				ItemRegistry.TESLA_SHOVEL.getKey(),
				ItemRegistry.VENTUS_SHOVEL.getKey(),
				ItemRegistry.ASTRAL_SHOVEL.getKey(),
				ItemRegistry.STARSTORM_SHOVEL.getKey(),
				ItemRegistry.VOID_SHOVEL.getKey());
		tag(ItemTags.HOES).add(
				ItemRegistry.COBALT_HOE.getKey(),
				ItemRegistry.MOLTEN_HOE.getKey(),
				ItemRegistry.TESLA_HOE.getKey(),
				ItemRegistry.VENTUS_HOE.getKey(),
				ItemRegistry.ASTRAL_HOE.getKey(),
				ItemRegistry.STARSTORM_HOE.getKey(),
				ItemRegistry.VOID_HOE.getKey());
		tag(ItemTags.HEAD_ARMOR).add(
				ItemRegistry.COBALT_HELMET.getKey(),
				ItemRegistry.MOLTEN_HELMET.getKey(),
				ItemRegistry.TESLA_HELMET.getKey(),
				ItemRegistry.VENTUS_HELMET.getKey(),
				ItemRegistry.ASTRAL_HELMET.getKey(),
				ItemRegistry.STARSTORM_HELMET.getKey(),
				ItemRegistry.VOID_HELMET.getKey());
		tag(ItemTags.CHEST_ARMOR).add(
				ItemRegistry.COBALT_CHESTPLATE.getKey(),
				ItemRegistry.MOLTEN_CHESTPLATE.getKey(),
				ItemRegistry.TESLA_CHESTPLATE.getKey(),
				ItemRegistry.VENTUS_CHESTPLATE.getKey(),
				ItemRegistry.ASTRAL_CHESTPLATE.getKey(),
				ItemRegistry.STARSTORM_CHESTPLATE.getKey(),
				ItemRegistry.VOID_CHESTPLATE.getKey());
		tag(ItemTags.LEG_ARMOR).add(
				ItemRegistry.COBALT_LEGGINGS.getKey(),
				ItemRegistry.MOLTEN_LEGGINGS.getKey(),
				ItemRegistry.TESLA_LEGGINGS.getKey(),
				ItemRegistry.VENTUS_LEGGINGS.getKey(),
				ItemRegistry.ASTRAL_LEGGINGS.getKey(),
				ItemRegistry.STARSTORM_LEGGINGS.getKey(),
				ItemRegistry.VOID_LEGGINGS.getKey());
		tag(ItemTags.FOOT_ARMOR).add(
				ItemRegistry.COBALT_BOOTS.getKey(),
				ItemRegistry.MOLTEN_BOOTS.getKey(),
				ItemRegistry.TESLA_BOOTS.getKey(),
				ItemRegistry.VENTUS_BOOTS.getKey(),
				ItemRegistry.ASTRAL_BOOTS.getKey(),
				ItemRegistry.STARSTORM_BOOTS.getKey(),
				ItemRegistry.VOID_BOOTS.getKey());

		tag(SLABS).add(
				BlockItemRegistry.CLOUD_MARBLE_BRICK_SLAB_ITEM.getKey(),
				BlockItemRegistry.BLOOD_SANDSTONE_SLAB_ITEM.getKey(),
				BlockItemRegistry.CUT_BLOOD_SANDSTONE_SLAB_ITEM.getKey(),
				BlockItemRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB_ITEM.getKey(),
				BlockItemRegistry.HARDENED_MUD_SLAB_ITEM.getKey());

		tag(STAIRS).add(
				BlockItemRegistry.CLOUD_MARBLE_BRICK_STAIRS_ITEM.getKey(),
				BlockItemRegistry.BLOOD_SANDSTONE_STAIRS_ITEM.getKey(),
				BlockItemRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS_ITEM.getKey(),
				BlockItemRegistry.HARDENED_MUD_STAIRS_ITEM.getKey());

		copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
		copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
		copy(BlockTags.PLANKS, ItemTags.PLANKS);
		copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
		copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
		copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
		copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
		copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
		copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
		copy(BlockTags.LEAVES, ItemTags.LEAVES);
		copy(BlockTags.SAND, ItemTags.SAND);
		copy(BlockTags.WALLS, ItemTags.WALLS);
		copy(BlockTagsGenerator.SAPLINGS, ItemTags.SAPLINGS);
		copy(BlockTags.LOGS, ItemTags.LOGS);
		copy(BlockTagsGenerator.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
		copy(BlockTagsGenerator.SMELTS_TO_GLASS, ItemTags.SMELTS_TO_GLASS);
		copy(BlockTags.SMALL_FLOWERS, SMALL_FLOWERS);
	}
}