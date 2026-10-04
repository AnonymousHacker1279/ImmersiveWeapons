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
		tag(CommonItemTagGroups.COBALT_INGOTS).add(ItemRegistry.COBALT_INGOT.key());
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
		tag(CommonItemTagGroups.COBALT_NUGGETS).add(ItemRegistry.COBALT_NUGGET.key());
		tag(CommonItemTagGroups.METAL_NUGGETS).addTags(
				CommonItemTagGroups.COBALT_NUGGETS,
				CommonItemTagGroups.COPPER_NUGGETS,
				Tags.Items.NUGGETS_IRON,
				Tags.Items.NUGGETS_GOLD);
		tag(Tags.Items.NUGGETS).addTag(CommonItemTagGroups.METAL_NUGGETS);
		tag(Tags.Items.NUGGETS).addTag(IWItemTagGroups.TESLA_NUGGETS);
		tag(Tags.Items.NUGGETS).addTag(IWItemTagGroups.ASTRAL_NUGGETS);

		// Dust tags
		tag(CommonItemTagGroups.SULFUR_DUSTS).add(ItemRegistry.SULFUR_DUST.key());

		tag(Tags.Items.TOOLS_BOW).add(
				ItemRegistry.ICE_BOW.key(),
				ItemRegistry.DRAGONS_BREATH_BOW.key(),
				ItemRegistry.AURORA_BOW.key());

		// Food tags
		tag(Tags.Items.FOODS).add(
				ItemRegistry.MRE.key(),
				ItemRegistry.CHOCOLATE_BAR.key(),
				ItemRegistry.MOLDY_BREAD.key());
		tag(Tags.Items.FOODS_CANDY).add(ItemRegistry.CHOCOLATE_BAR.key());
		tag(Tags.Items.FOODS_BREAD).add(ItemRegistry.MOLDY_BREAD.key());
		tag(Tags.Items.FOODS_FOOD_POISONING).add(ItemRegistry.MOLDY_BREAD.key());

		copy(CommonBlockTagGroups.COBALT_ORES, CommonItemTagGroups.COBALT_ORES);
		copy(Tags.Blocks.ORES, Tags.Items.ORES);
		copy(Tags.Blocks.NATURAL_LOGS, Tags.Items.NATURAL_LOGS);
		copy(BlockTags.FENCES, Tags.Items.FENCES);
		copy(BlockTags.SMALL_FLOWERS, Tags.Items.FLOWERS_SMALL);
	}

	/// Add tags under the Immersive Weapons namespace
	@SuppressWarnings("unchecked")
	private void addImmersiveWeaponsTags() {
		// Projectile tags
		tag(IWItemTagGroups.FLARES).add(ItemRegistry.FLARE.key());
		tag(IWItemTagGroups.MUSKET_BALLS).add(
				ItemRegistry.WOODEN_MUSKET_BALL.key(),
				ItemRegistry.STONE_MUSKET_BALL.key(),
				ItemRegistry.GOLDEN_MUSKET_BALL.key(),
				ItemRegistry.COPPER_MUSKET_BALL.key(),
				ItemRegistry.IRON_MUSKET_BALL.key(),
				ItemRegistry.COBALT_MUSKET_BALL.key(),
				ItemRegistry.DIAMOND_MUSKET_BALL.key(),
				ItemRegistry.NETHERITE_MUSKET_BALL.key(),
				ItemRegistry.MOLTEN_MUSKET_BALL.key(),
				ItemRegistry.TESLA_MUSKET_BALL.key(),
				ItemRegistry.VENTUS_MUSKET_BALL.key(),
				ItemRegistry.ASTRAL_MUSKET_BALL.key(),
				ItemRegistry.STARSTORM_MUSKET_BALL.key(),
				ItemRegistry.VOID_MUSKET_BALL.key());
		tag(IWItemTagGroups.CANNONBALLS).add(
				ItemRegistry.CANNONBALL.key(),
				ItemRegistry.EXPLOSIVE_CANNONBALL.key());
		tag(IWItemTagGroups.DRAGON_FIREBALLS).add(ItemRegistry.DRAGON_FIREBALL.key());

		// Ingot tags
		tag(IWItemTagGroups.MOLTEN_INGOTS).add(ItemRegistry.MOLTEN_INGOT.key());
		tag(IWItemTagGroups.TESLA_INGOTS).add(ItemRegistry.TESLA_INGOT.key());
		tag(IWItemTagGroups.ASTRAL_INGOTS).add(ItemRegistry.ASTRAL_INGOT.key());
		tag(IWItemTagGroups.STARSTORM_INGOTS).add(ItemRegistry.STARSTORM_INGOT.key());
		tag(IWItemTagGroups.VOID_INGOTS).add(ItemRegistry.VOID_INGOT.key());
		tag(IWItemTagGroups.HANSIUM_INGOTS).add(ItemRegistry.HANSIUM_INGOT.key());

		// Shard tags
		tag(IWItemTagGroups.MOLTEN_SHARDS).add(ItemRegistry.MOLTEN_SHARD.key());
		tag(IWItemTagGroups.VENTUS_SHARDS).add(ItemRegistry.VENTUS_SHARD.key());
		tag(IWItemTagGroups.DIAMOND_SHARDS).add(ItemRegistry.DIAMOND_SHARD.key());
		tag(IWItemTagGroups.STONE_SHARDS).add(ItemRegistry.STONE_SHARD.key());
		tag(IWItemTagGroups.WOODEN_SHARDS).add(ItemRegistry.WOODEN_SHARD.key());
		tag(IWItemTagGroups.STARSTORM_SHARDS).add(ItemRegistry.STARSTORM_SHARD.key());
		tag(IWItemTagGroups.SHARDS).addTags(
				IWItemTagGroups.MOLTEN_SHARDS,
				IWItemTagGroups.VENTUS_SHARDS,
				IWItemTagGroups.DIAMOND_SHARDS,
				IWItemTagGroups.STONE_SHARDS,
				IWItemTagGroups.WOODEN_SHARDS,
				IWItemTagGroups.STARSTORM_SHARDS);

		// Nugget tags
		tag(IWItemTagGroups.TESLA_NUGGETS).add(ItemRegistry.TESLA_NUGGET.key());
		tag(IWItemTagGroups.ASTRAL_NUGGETS).add(ItemRegistry.ASTRAL_NUGGET.key());

		// Rod tags
		tag(IWItemTagGroups.OBSIDIAN_RODS).add(ItemRegistry.OBSIDIAN_ROD.key());

		// Accessory tags
		tag(IWItemTagGroups.ACCESSORIES).add(
				ItemRegistry.SATCHEL.key(),
				ItemRegistry.POWDER_HORN.key(),
				ItemRegistry.BERSERKERS_AMULET.key(),
				ItemRegistry.HANS_BLESSING.key(),
				ItemRegistry.CELESTIAL_SPIRIT.key(),
				ItemRegistry.BLADEMASTER_EMBLEM.key(),
				ItemRegistry.DEADEYE_PENDANT.key(),
				ItemRegistry.BLOATED_HEART.key(),
				ItemRegistry.NETHERITE_SHIELD.key(),
				ItemRegistry.MELEE_MASTERS_MOLTEN_GLOVE.key(),
				ItemRegistry.IRON_FIST.key(),
				ItemRegistry.GLOVE_OF_RAPID_SWINGING.key(),
				ItemRegistry.HAND_OF_DOOM.key(),
				ItemRegistry.COPPER_RING.key(),
				ItemRegistry.IRON_RING.key(),
				ItemRegistry.COBALT_RING.key(),
				ItemRegistry.GOLDEN_RING.key(),
				ItemRegistry.AMETHYST_RING.key(),
				ItemRegistry.EMERALD_RING.key(),
				ItemRegistry.DIAMOND_RING.key(),
				ItemRegistry.NETHERITE_RING.key(),
				ItemRegistry.DEATH_GEM_RING.key(),
				ItemRegistry.MEDAL_OF_ADEQUACY.key(),
				ItemRegistry.DEPTH_CHARM.key(),
				ItemRegistry.REINFORCED_DEPTH_CHARM.key(),
				ItemRegistry.INSOMNIA_AMULET.key(),
				ItemRegistry.GOGGLES.key(),
				ItemRegistry.LAVA_GOGGLES.key(),
				ItemRegistry.NIGHT_VISION_GOGGLES.key(),
				ItemRegistry.AGILITY_BRACELET.key(),
				ItemRegistry.BLOODY_CLOTH.key(),
				ItemRegistry.ANCIENT_SCROLL.key(),
				ItemRegistry.HOLY_MANTLE.key(),
				ItemRegistry.VENSTRAL_JAR.key(),
				ItemRegistry.SUPER_BLANKET_CAPE.key(),
				ItemRegistry.MEDAL_OF_HONOR.key(),
				ItemRegistry.MEDAL_OF_DISHONOR.key());

		// Smoke grenade tags
		tag(IWItemTagGroups.SMOKE_GRENADES).add(
				ItemRegistry.SMOKE_GRENADE.key(),
				ItemRegistry.SMOKE_GRENADE_RED.key(),
				ItemRegistry.SMOKE_GRENADE_GREEN.key(),
				ItemRegistry.SMOKE_GRENADE_BLUE.key(),
				ItemRegistry.SMOKE_GRENADE_PURPLE.key(),
				ItemRegistry.SMOKE_GRENADE_YELLOW.key());

		// Tool tags
		tag(IWItemTagGroups.MOLTEN_TOOLS).add(
				ItemRegistry.MOLTEN_SWORD.key(),
				ItemRegistry.MOLTEN_PICKAXE.key(),
				ItemRegistry.MOLTEN_AXE.key(),
				ItemRegistry.MOLTEN_SHOVEL.key(),
				ItemRegistry.MOLTEN_HOE.key(),
				ItemRegistry.MOLTEN_SPEAR.key());

		tag(IWItemTagGroups.TESLA_TOOLS).add(
				ItemRegistry.TESLA_SWORD.key(),
				ItemRegistry.TESLA_PICKAXE.key(),
				ItemRegistry.TESLA_AXE.key(),
				ItemRegistry.TESLA_SHOVEL.key(),
				ItemRegistry.TESLA_HOE.key(),
				ItemRegistry.TESLA_SPEAR.key());

		tag(IWItemTagGroups.VENTUS_TOOLS).add(
				ItemRegistry.VENTUS_SWORD.key(),
				ItemRegistry.VENTUS_PICKAXE.key(),
				ItemRegistry.VENTUS_AXE.key(),
				ItemRegistry.VENTUS_SHOVEL.key(),
				ItemRegistry.VENTUS_HOE.key(),
				ItemRegistry.VENTUS_SPEAR.key());

		tag(IWItemTagGroups.ASTRAL_TOOLS).add(
				ItemRegistry.ASTRAL_SWORD.key(),
				ItemRegistry.ASTRAL_PICKAXE.key(),
				ItemRegistry.ASTRAL_AXE.key(),
				ItemRegistry.ASTRAL_SHOVEL.key(),
				ItemRegistry.ASTRAL_HOE.key(),
				ItemRegistry.ASTRAL_SPEAR.key());

		tag(IWItemTagGroups.STARSTORM_TOOLS).add(
				ItemRegistry.STARSTORM_SWORD.key(),
				ItemRegistry.STARSTORM_PICKAXE.key(),
				ItemRegistry.STARSTORM_AXE.key(),
				ItemRegistry.STARSTORM_SHOVEL.key(),
				ItemRegistry.STARSTORM_HOE.key(),
				ItemRegistry.STARSTORM_SPEAR.key());

		tag(IWItemTagGroups.VOID_TOOLS).add(
				ItemRegistry.VOID_SWORD.key(),
				ItemRegistry.VOID_PICKAXE.key(),
				ItemRegistry.VOID_AXE.key(),
				ItemRegistry.VOID_SHOVEL.key(),
				ItemRegistry.VOID_HOE.key(),
				ItemRegistry.VOID_SPEAR.key());

		// Gauntlet tags
		tag(IWItemTagGroups.GAUNTLETS).add(
				ItemRegistry.WOODEN_GAUNTLET.key(),
				ItemRegistry.STONE_GAUNTLET.key(),
				ItemRegistry.GOLDEN_GAUNTLET.key(),
				ItemRegistry.COPPER_GAUNTLET.key(),
				ItemRegistry.IRON_GAUNTLET.key(),
				ItemRegistry.COBALT_GAUNTLET.key(),
				ItemRegistry.DIAMOND_GAUNTLET.key(),
				ItemRegistry.NETHERITE_GAUNTLET.key(),
				ItemRegistry.MOLTEN_GAUNTLET.key(),
				ItemRegistry.TESLA_GAUNTLET.key(),
				ItemRegistry.VENTUS_GAUNTLET.key(),
				ItemRegistry.ASTRAL_GAUNTLET.key(),
				ItemRegistry.STARSTORM_GAUNTLET.key(),
				ItemRegistry.VOID_GAUNTLET.key());

		// Maul tags
		tag(IWItemTagGroups.MAULS).add(
				ItemRegistry.WOODEN_MAUL.key(),
				ItemRegistry.STONE_MAUL.key(),
				ItemRegistry.GOLDEN_MAUL.key(),
				ItemRegistry.IRON_MAUL.key(),
				ItemRegistry.COBALT_MAUL.key(),
				ItemRegistry.DIAMOND_MAUL.key(),
				ItemRegistry.NETHERITE_MAUL.key(),
				ItemRegistry.MOLTEN_MAUL.key(),
				ItemRegistry.TESLA_MAUL.key(),
				ItemRegistry.VENTUS_MAUL.key(),
				ItemRegistry.ASTRAL_MAUL.key(),
				ItemRegistry.STARSTORM_MAUL.key(),
				ItemRegistry.VOID_MAUL.key());

		// Commander Pedestal Augment tags
		tag(IWItemTagGroups.COMMANDER_PEDESTAL_AUGMENTS).add(
				ItemRegistry.PEDESTAL_AUGMENT_SPEED.key(),
				ItemRegistry.PEDESTAL_AUGMENT_ARMOR.key(),
				ItemRegistry.PEDESTAL_AUGMENT_ENCHANTMENT.key(),
				ItemRegistry.PEDESTAL_AUGMENT_CAPACITY.key());

		// Firearm tags
		tag(IWItemTagGroups.FIREARMS).add(
				ItemRegistry.FLINTLOCK_PISTOL.key(),
				ItemRegistry.BLUNDERBUSS.key(),
				ItemRegistry.MUSKET.key(),
				ItemRegistry.FLARE_GUN.key(),
				ItemRegistry.HAND_CANNON.key(),
				ItemRegistry.DRAGONS_BREATH_CANNON.key());

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
		tag(IWItemTagGroups.METEOR_STAFFS).add(ItemRegistry.METEOR_STAFF.key());
		tag(IWItemTagGroups.CURSED_SIGHT_STAFFS).add(ItemRegistry.CURSED_SIGHT_STAFF.key());
		tag(IWItemTagGroups.SCULK_STAFFS).add(ItemRegistry.SCULK_STAFF.key());
		tag(IWItemTagGroups.RECOVERY_STAFFS).add(ItemRegistry.RECOVERY_STAFF.key());
		tag(IWItemTagGroups.VENTUS_STAFFS).add(ItemRegistry.VENTUS_STAFF.key());
		tag(IWItemTagGroups.STAFFS)
				.addTag(IWItemTagGroups.METEOR_STAFFS)
				.addTag(IWItemTagGroups.CURSED_SIGHT_STAFFS)
				.addTag(IWItemTagGroups.SCULK_STAFFS)
				.addTag(IWItemTagGroups.RECOVERY_STAFFS)
				.addTag(IWItemTagGroups.VENTUS_STAFFS);

		// Armor tags
		tag(IWItemTagGroups.MOLTEN_ARMOR).add(
				ItemRegistry.MOLTEN_HELMET.key(),
				ItemRegistry.MOLTEN_CHESTPLATE.key(),
				ItemRegistry.MOLTEN_LEGGINGS.key(),
				ItemRegistry.MOLTEN_BOOTS.key());

		tag(IWItemTagGroups.TESLA_ARMOR).add(
				ItemRegistry.TESLA_HELMET.key(),
				ItemRegistry.TESLA_CHESTPLATE.key(),
				ItemRegistry.TESLA_LEGGINGS.key(),
				ItemRegistry.TESLA_BOOTS.key());

		tag(IWItemTagGroups.VENTUS_ARMOR).add(
				ItemRegistry.VENTUS_HELMET.key(),
				ItemRegistry.VENTUS_CHESTPLATE.key(),
				ItemRegistry.VENTUS_LEGGINGS.key(),
				ItemRegistry.VENTUS_BOOTS.key());

		tag(IWItemTagGroups.ASTRAL_ARMOR).add(
				ItemRegistry.ASTRAL_HELMET.key(),
				ItemRegistry.ASTRAL_CHESTPLATE.key(),
				ItemRegistry.ASTRAL_LEGGINGS.key(),
				ItemRegistry.ASTRAL_BOOTS.key());

		tag(IWItemTagGroups.STARSTORM_ARMOR).add(
				ItemRegistry.STARSTORM_HELMET.key(),
				ItemRegistry.STARSTORM_CHESTPLATE.key(),
				ItemRegistry.STARSTORM_LEGGINGS.key(),
				ItemRegistry.STARSTORM_BOOTS.key());

		tag(IWItemTagGroups.PADDED_LEATHER).add(
				ItemRegistry.PADDED_LEATHER_HELMET.key(),
				ItemRegistry.PADDED_LEATHER_CHESTPLATE.key(),
				ItemRegistry.PADDED_LEATHER_LEGGINGS.key(),
				ItemRegistry.PADDED_LEATHER_BOOTS.key());

		tag(IWItemTagGroups.VOID_ARMOR).add(
				ItemRegistry.VOID_HELMET.key(),
				ItemRegistry.VOID_CHESTPLATE.key(),
				ItemRegistry.VOID_LEGGINGS.key(),
				ItemRegistry.VOID_BOOTS.key());

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
		tag(ItemTags.SIGNS).add(BlockItemRegistry.BURNED_OAK_SIGN_ITEM.key(),
				BlockItemRegistry.STARDUST_SIGN_ITEM.key());

		// Arrow tags
		tag(ItemTags.TRIM_MATERIALS).add(
				ItemRegistry.COBALT_INGOT.key(),
				ItemRegistry.MOLTEN_INGOT.key(),
				ItemRegistry.VENTUS_SHARD.key(),
				ItemRegistry.TESLA_INGOT.key(),
				ItemRegistry.ASTRAL_INGOT.key(),
				ItemRegistry.STARSTORM_INGOT.key(),
				ItemRegistry.VOID_INGOT.key());
		tag(ItemTags.ARROWS).add(
				ItemRegistry.WOODEN_ARROW.key(),
				ItemRegistry.STONE_ARROW.key(),
				ItemRegistry.GOLDEN_ARROW.key(),
				ItemRegistry.COPPER_ARROW.key(),
				ItemRegistry.IRON_ARROW.key(),
				ItemRegistry.COBALT_ARROW.key(),
				ItemRegistry.DIAMOND_ARROW.key(),
				ItemRegistry.NETHERITE_ARROW.key(),
				ItemRegistry.MOLTEN_ARROW.key(),
				ItemRegistry.TESLA_ARROW.key(),
				ItemRegistry.VENTUS_ARROW.key(),
				ItemRegistry.ASTRAL_ARROW.key(),
				ItemRegistry.STARSTORM_ARROW.key(),
				ItemRegistry.VOID_ARROW.key());

		// Boat tags
		tag(ItemTags.BOATS).add(ItemRegistry.BURNED_OAK_BOAT.key());
		tag(ItemTags.BOATS).add(ItemRegistry.STARDUST_BOAT.key());
		tag(ItemTags.CHEST_BOATS).add(ItemRegistry.BURNED_OAK_CHEST_BOAT.key());
		tag(ItemTags.CHEST_BOATS).add(ItemRegistry.STARDUST_CHEST_BOAT.key());

		// Non-flammable wood tag
		tag(ItemTags.NON_FLAMMABLE_WOOD).add(
				BlockItemRegistry.WARPED_TABLE_ITEM.key(),
				BlockItemRegistry.CRIMSON_TABLE_ITEM.key());

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
				ItemRegistry.COBALT_INGOT.key(),
				ItemRegistry.MOLTEN_INGOT.key(),
				ItemRegistry.TESLA_INGOT.key(),
				ItemRegistry.VENTUS_SHARD.key(),
				ItemRegistry.ASTRAL_INGOT.key(),
				ItemRegistry.STARSTORM_INGOT.key());

		// Cauldron remove dye tag
		tag(ItemTags.CAULDRON_CAN_REMOVE_DYE).add(
				ItemRegistry.PADDED_LEATHER_HELMET.key(),
				ItemRegistry.PADDED_LEATHER_CHESTPLATE.key(),
				ItemRegistry.PADDED_LEATHER_LEGGINGS.key(),
				ItemRegistry.PADDED_LEATHER_BOOTS.key()
		);

		// Freeze immune tag
		tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(
				ItemRegistry.PADDED_LEATHER_HELMET.key(),
				ItemRegistry.PADDED_LEATHER_CHESTPLATE.key(),
				ItemRegistry.PADDED_LEATHER_LEGGINGS.key(),
				ItemRegistry.PADDED_LEATHER_BOOTS.key()
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
				BlockItemRegistry.MINUTEMAN_HEAD_ITEM.key(),
				BlockItemRegistry.FIELD_MEDIC_HEAD_ITEM.key(),
				BlockItemRegistry.DYING_SOLDIER_HEAD_ITEM.key(),
				BlockItemRegistry.WANDERING_WARRIOR_HEAD_ITEM.key(),
				BlockItemRegistry.HANS_HEAD_ITEM.key(),
				BlockItemRegistry.STORM_CREEPER_HEAD_ITEM.key());

		tag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_FLAT)
				.addTag(CommonItemTagGroups.COBALT_ORES)
				.add(BlockItemRegistry.COBALT_BLOCK_ITEM.key(),
						BlockItemRegistry.RAW_COBALT_BLOCK_ITEM.key(),
						BlockItemRegistry.RUSTED_IRON_BLOCK_ITEM.key());

		tag(ItemTags.SULFUR_CUBE_ARCHETYPE_FAST_FLAT)
				.addTag(IWItemTagGroups.ASTRAL_ORES)
				.add(BlockItemRegistry.ASTRAL_BLOCK_ITEM.key(),
						BlockItemRegistry.STARSTORM_BLOCK_ITEM.key());

		tag(ItemTags.SULFUR_CUBE_ARCHETYPE_HIGH_RESISTANCE)
				.addTags(IWItemTagGroups.MOLTEN_ORES,
						IWItemTagGroups.TESLA_ORES)
				.add(BlockItemRegistry.MOLTEN_BLOCK_ITEM.key(),
						BlockItemRegistry.TESLA_BLOCK_ITEM.key());

		tag(ItemTags.SULFUR_CUBE_ARCHETYPE_LIGHT).add(
				BlockItemRegistry.VENTUS_ORE_ITEM.key());

		// Spear tags
		tag(ItemTags.SPEARS).add(
				ItemRegistry.COBALT_SPEAR.key(),
				ItemRegistry.MOLTEN_SPEAR.key(),
				ItemRegistry.TESLA_SPEAR.key(),
				ItemRegistry.VENTUS_SPEAR.key(),
				ItemRegistry.ASTRAL_SPEAR.key(),
				ItemRegistry.STARSTORM_SPEAR.key(),
				ItemRegistry.VOID_SPEAR.key());

		tag(ItemTags.SWORDS).add(
				ItemRegistry.COBALT_SWORD.key(),
				ItemRegistry.MOLTEN_SWORD.key(),
				ItemRegistry.TESLA_SWORD.key(),
				ItemRegistry.VENTUS_SWORD.key(),
				ItemRegistry.ASTRAL_SWORD.key(),
				ItemRegistry.STARSTORM_SWORD.key(),
				ItemRegistry.VOID_SWORD.key(),
				ItemRegistry.THE_SWORD.key());
		tag(ItemTags.PICKAXES).add(
				ItemRegistry.COBALT_PICKAXE.key(),
				ItemRegistry.MOLTEN_PICKAXE.key(),
				ItemRegistry.TESLA_PICKAXE.key(),
				ItemRegistry.VENTUS_PICKAXE.key(),
				ItemRegistry.ASTRAL_PICKAXE.key(),
				ItemRegistry.STARSTORM_PICKAXE.key(),
				ItemRegistry.VOID_PICKAXE.key());
		tag(ItemTags.AXES).add(
				ItemRegistry.COBALT_AXE.key(),
				ItemRegistry.MOLTEN_AXE.key(),
				ItemRegistry.TESLA_AXE.key(),
				ItemRegistry.VENTUS_AXE.key(),
				ItemRegistry.ASTRAL_AXE.key(),
				ItemRegistry.STARSTORM_AXE.key(),
				ItemRegistry.VOID_AXE.key());
		tag(ItemTags.SHOVELS).add(
				ItemRegistry.COBALT_SHOVEL.key(),
				ItemRegistry.MOLTEN_SHOVEL.key(),
				ItemRegistry.TESLA_SHOVEL.key(),
				ItemRegistry.VENTUS_SHOVEL.key(),
				ItemRegistry.ASTRAL_SHOVEL.key(),
				ItemRegistry.STARSTORM_SHOVEL.key(),
				ItemRegistry.VOID_SHOVEL.key());
		tag(ItemTags.HOES).add(
				ItemRegistry.COBALT_HOE.key(),
				ItemRegistry.MOLTEN_HOE.key(),
				ItemRegistry.TESLA_HOE.key(),
				ItemRegistry.VENTUS_HOE.key(),
				ItemRegistry.ASTRAL_HOE.key(),
				ItemRegistry.STARSTORM_HOE.key(),
				ItemRegistry.VOID_HOE.key());
		tag(ItemTags.HEAD_ARMOR).add(
				ItemRegistry.COBALT_HELMET.key(),
				ItemRegistry.MOLTEN_HELMET.key(),
				ItemRegistry.TESLA_HELMET.key(),
				ItemRegistry.VENTUS_HELMET.key(),
				ItemRegistry.ASTRAL_HELMET.key(),
				ItemRegistry.STARSTORM_HELMET.key(),
				ItemRegistry.VOID_HELMET.key());
		tag(ItemTags.CHEST_ARMOR).add(
				ItemRegistry.COBALT_CHESTPLATE.key(),
				ItemRegistry.MOLTEN_CHESTPLATE.key(),
				ItemRegistry.TESLA_CHESTPLATE.key(),
				ItemRegistry.VENTUS_CHESTPLATE.key(),
				ItemRegistry.ASTRAL_CHESTPLATE.key(),
				ItemRegistry.STARSTORM_CHESTPLATE.key(),
				ItemRegistry.VOID_CHESTPLATE.key());
		tag(ItemTags.LEG_ARMOR).add(
				ItemRegistry.COBALT_LEGGINGS.key(),
				ItemRegistry.MOLTEN_LEGGINGS.key(),
				ItemRegistry.TESLA_LEGGINGS.key(),
				ItemRegistry.VENTUS_LEGGINGS.key(),
				ItemRegistry.ASTRAL_LEGGINGS.key(),
				ItemRegistry.STARSTORM_LEGGINGS.key(),
				ItemRegistry.VOID_LEGGINGS.key());
		tag(ItemTags.FOOT_ARMOR).add(
				ItemRegistry.COBALT_BOOTS.key(),
				ItemRegistry.MOLTEN_BOOTS.key(),
				ItemRegistry.TESLA_BOOTS.key(),
				ItemRegistry.VENTUS_BOOTS.key(),
				ItemRegistry.ASTRAL_BOOTS.key(),
				ItemRegistry.STARSTORM_BOOTS.key(),
				ItemRegistry.VOID_BOOTS.key());

		tag(SLABS).add(
				BlockItemRegistry.CLOUD_MARBLE_BRICK_SLAB_ITEM.key(),
				BlockItemRegistry.BLOOD_SANDSTONE_SLAB_ITEM.key(),
				BlockItemRegistry.CUT_BLOOD_SANDSTONE_SLAB_ITEM.key(),
				BlockItemRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB_ITEM.key());

		tag(STAIRS).add(
				BlockItemRegistry.CLOUD_MARBLE_BRICK_STAIRS_ITEM.key(),
				BlockItemRegistry.BLOOD_SANDSTONE_STAIRS_ITEM.key(),
				BlockItemRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS_ITEM.key());

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