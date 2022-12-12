package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.minecraft.MinecraftBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.minecraft.MinecraftItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.lists.ItemTagLists;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class ItemTagsGenerator extends ItemTagsProvider {

	/**
	 * Constructor for ItemTagsGenerator.
	 *
	 * @param gen                the <code>DataGenerator</code> instance
	 * @param blockTagsGenerator the <code>BlockTagsGenerator</code> instance
	 * @param existingFileHelper the <code>ExistingFileHelper</code> instance
	 */
	public ItemTagsGenerator(DataGenerator gen, BlockTagsGenerator blockTagsGenerator, @Nullable ExistingFileHelper existingFileHelper) {
		super(gen, blockTagsGenerator, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		ItemTagLists.init();

		addForgeTags();
		addImmersiveWeaponsTags();
		addMinecraftTags();
	}

	/**
	 * Add tags under the Forge namespace
	 */
	@SuppressWarnings("unchecked")
	private void addForgeTags() {
		// Copy item tags from block tags
		copy(Blocks.STAINED_GLASS, Tags.Items.STAINED_GLASS);
		copy(ForgeBlockTagGroups.COBALT_ORES, ForgeItemTagGroups.COBALT_ORES);
		copy(Blocks.ORES, Tags.Items.ORES);

		// Ingot tags
		tag(ForgeItemTagGroups.COBALT_INGOTS).add(ItemRegistry.COBALT_INGOT.get());
		tag(Tags.Items.INGOTS_COPPER).add(Items.COPPER_INGOT);
		tag(ForgeItemTagGroups.METAL_INGOTS).addTags(ForgeItemTagGroups.COBALT_INGOTS, Tags.Items.INGOTS_COPPER,
				Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_GOLD);
		tag(Tags.Items.INGOTS).addTag(ForgeItemTagGroups.METAL_INGOTS);

		// Nugget tags
		tag(ForgeItemTagGroups.COBALT_NUGGETS).add(ItemRegistry.COBALT_NUGGET.get());
		tag(ForgeItemTagGroups.COPPER_NUGGETS).add(ItemRegistry.COPPER_NUGGET.get());
		tag(ForgeItemTagGroups.METAL_NUGGETS).addTags(ForgeItemTagGroups.COBALT_NUGGETS, ForgeItemTagGroups.COPPER_NUGGETS,
				Tags.Items.NUGGETS_IRON, Tags.Items.NUGGETS_GOLD);
		tag(Tags.Items.NUGGETS).addTag(ForgeItemTagGroups.METAL_NUGGETS);

		// Dust tags
		tag(ForgeItemTagGroups.SULFUR_DUSTS).add(ItemRegistry.SULFUR.get());

		// Tool tags

		// Sword
		tag(Tags.Items.TOOLS_SWORDS).add(ItemRegistry.COPPER_SWORD.get(),
				ItemRegistry.COBALT_SWORD.get(),
				ItemRegistry.MOLTEN_SWORD.get(),
				ItemRegistry.TESLA_SWORD.get(),
				ItemRegistry.VENTUS_SWORD.get(),
				ItemRegistry.ASTRAL_SWORD.get());
		// Pickaxe
		tag(Tags.Items.TOOLS_PICKAXES).add(ItemRegistry.COPPER_PICKAXE.get(),
				ItemRegistry.COBALT_PICKAXE.get(),
				ItemRegistry.MOLTEN_PICKAXE.get(),
				ItemRegistry.TESLA_PICKAXE.get(),
				ItemRegistry.VENTUS_PICKAXE.get(),
				ItemRegistry.ASTRAL_PICKAXE.get());
		// Axe
		tag(Tags.Items.TOOLS_AXES).add(ItemRegistry.COPPER_AXE.get(),
				ItemRegistry.COBALT_AXE.get(),
				ItemRegistry.MOLTEN_AXE.get(),
				ItemRegistry.TESLA_AXE.get(),
				ItemRegistry.VENTUS_AXE.get(),
				ItemRegistry.ASTRAL_AXE.get());
		// Shovel
		tag(Tags.Items.TOOLS_SHOVELS).add(ItemRegistry.COPPER_SHOVEL.get(),
				ItemRegistry.COBALT_SHOVEL.get(),
				ItemRegistry.MOLTEN_SHOVEL.get(),
				ItemRegistry.TESLA_SHOVEL.get(),
				ItemRegistry.VENTUS_SHOVEL.get(),
				ItemRegistry.ASTRAL_SHOVEL.get());
		// Hoe
		tag(Tags.Items.TOOLS_HOES).add(ItemRegistry.COPPER_HOE.get(),
				ItemRegistry.COBALT_HOE.get(),
				ItemRegistry.MOLTEN_HOE.get(),
				ItemRegistry.TESLA_HOE.get(),
				ItemRegistry.VENTUS_HOE.get(),
				ItemRegistry.ASTRAL_HOE.get());

		// Armor tags

		// Helmet
		tag(Tags.Items.ARMORS_HELMETS).add(ItemRegistry.COPPER_HELMET.get(),
				ItemRegistry.COBALT_HELMET.get(),
				ItemRegistry.MOLTEN_HELMET.get(),
				ItemRegistry.TESLA_HELMET.get(),
				ItemRegistry.VENTUS_HELMET.get());
		// Chestplate
		tag(Tags.Items.ARMORS_CHESTPLATES).add(ItemRegistry.COPPER_CHESTPLATE.get(),
				ItemRegistry.COBALT_CHESTPLATE.get(),
				ItemRegistry.MOLTEN_CHESTPLATE.get(),
				ItemRegistry.TESLA_CHESTPLATE.get(),
				ItemRegistry.VENTUS_CHESTPLATE.get());
		// Leggings
		tag(Tags.Items.ARMORS_LEGGINGS).add(ItemRegistry.COPPER_LEGGINGS.get(),
				ItemRegistry.COBALT_LEGGINGS.get(),
				ItemRegistry.MOLTEN_LEGGINGS.get(),
				ItemRegistry.TESLA_LEGGINGS.get(),
				ItemRegistry.VENTUS_LEGGINGS.get());
		// Boots
		tag(Tags.Items.ARMORS_BOOTS).add(ItemRegistry.COPPER_BOOTS.get(),
				ItemRegistry.COBALT_BOOTS.get(),
				ItemRegistry.MOLTEN_BOOTS.get(),
				ItemRegistry.TESLA_BOOTS.get(),
				ItemRegistry.VENTUS_BOOTS.get());

		// Head tags
		tag(Tags.Items.HEADS).add(BlockItemRegistry.MINUTEMAN_HEAD_ITEM.get(),
				BlockItemRegistry.FIELD_MEDIC_HEAD_ITEM.get(),
				BlockItemRegistry.DYING_SOLDIER_HEAD_ITEM.get(),
				BlockItemRegistry.WANDERING_WARRIOR_HEAD_ITEM.get(),
				BlockItemRegistry.HANS_HEAD_ITEM.get());
	}

	/**
	 * Add tags under the Immersive Weapons namespace
	 */
	private void addImmersiveWeaponsTags() {
		// Copy item tags from block tags
		copy(ImmersiveWeaponsBlockTagGroups.BURNED_OAK_LOGS, ImmersiveWeaponsItemTagGroups.BURNED_OAK_LOGS);
		copy(ImmersiveWeaponsBlockTagGroups.STARDUST_LOGS, ImmersiveWeaponsItemTagGroups.STARDUST_LOGS);

		// Projectile tags
		tag(ImmersiveWeaponsItemTagGroups.FLARES).add(ItemRegistry.FLARE.get());
		for (Item item : ItemTagLists.MUSKET_BALLS) {
			tag(ImmersiveWeaponsItemTagGroups.MUSKET_BALLS).add(item);
		}

		// Ingot tags
		tag(ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS).add(ItemRegistry.MOLTEN_INGOT.get());
		tag(ImmersiveWeaponsItemTagGroups.ELECTRIC_INGOTS).add(ItemRegistry.ELECTRIC_INGOT.get());
		tag(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS).add(ItemRegistry.TESLA_INGOT.get());
		tag(ImmersiveWeaponsItemTagGroups.ASTRAL_INGOTS).add(ItemRegistry.ASTRAL_INGOT.get());
		tag(ImmersiveWeaponsItemTagGroups.STARSTORM_INGOTS).add(ItemRegistry.STARSTORM_INGOT.get());

		// Shard tags
		tag(ImmersiveWeaponsItemTagGroups.MOLTEN_SHARDS).add(ItemRegistry.MOLTEN_SHARD.get());
		tag(ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS).add(ItemRegistry.VENTUS_SHARD.get());
		tag(ImmersiveWeaponsItemTagGroups.DIAMOND_SHARDS).add(ItemRegistry.DIAMOND_SHARD.get());
		tag(ImmersiveWeaponsItemTagGroups.STONE_SHARDS).add(ItemRegistry.STONE_SHARD.get());
		tag(ImmersiveWeaponsItemTagGroups.WOODEN_SHARDS).add(ItemRegistry.WOODEN_SHARD.get());

		// Rod tags
		tag(ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS).add(ItemRegistry.OBSIDIAN_ROD.get());
	}

	/**
	 * Add tags under the Minecraft namespace
	 */
	private void addMinecraftTags() {
		// Copy item tags from block tags
		copy(MinecraftBlockTagGroups.FENCES, MinecraftItemTagGroups.FENCES);
		copy(MinecraftBlockTagGroups.LOGS_THAT_BURN, MinecraftItemTagGroups.LOGS_THAT_BURN);
		copy(MinecraftBlockTagGroups.PLANKS, MinecraftItemTagGroups.PLANKS);
		copy(MinecraftBlockTagGroups.SLABS, MinecraftItemTagGroups.SLABS);
		copy(MinecraftBlockTagGroups.STAIRS, MinecraftItemTagGroups.STAIRS);
		copy(MinecraftBlockTagGroups.WOODEN_BUTTONS, MinecraftItemTagGroups.WOODEN_BUTTONS);
		copy(MinecraftBlockTagGroups.WOODEN_DOORS, MinecraftItemTagGroups.WOODEN_DOORS);
		copy(MinecraftBlockTagGroups.WOODEN_FENCES, MinecraftItemTagGroups.WOODEN_FENCES);
		copy(MinecraftBlockTagGroups.WOODEN_PRESSURE_PLATES, MinecraftItemTagGroups.WOODEN_PRESSURE_PLATES);
		copy(MinecraftBlockTagGroups.WOODEN_SLABS, MinecraftItemTagGroups.WOODEN_SLABS);
		copy(MinecraftBlockTagGroups.WOODEN_STAIRS, MinecraftItemTagGroups.WOODEN_STAIRS);
		copy(MinecraftBlockTagGroups.WOODEN_TRAPDOORS, MinecraftItemTagGroups.WOODEN_TRAPDOORS);
		copy(MinecraftBlockTagGroups.SMALL_FLOWERS, MinecraftItemTagGroups.SMALL_FLOWERS);
		copy(MinecraftBlockTagGroups.LEAVES, MinecraftItemTagGroups.LEAVES);
		copy(MinecraftBlockTagGroups.SAND, MinecraftItemTagGroups.SAND);
		copy(MinecraftBlockTagGroups.SAPLINGS, MinecraftItemTagGroups.SAPLINGS);
		copy(MinecraftBlockTagGroups.WALLS, MinecraftItemTagGroups.WALLS);

		// Sign tags
		tag(MinecraftItemTagGroups.SIGNS).add(BlockItemRegistry.BURNED_OAK_SIGN_ITEM.get());

		// Arrow tags
		for (Item item : ItemTagLists.ARROWS) {
			tag(MinecraftItemTagGroups.ARROWS).add(item);
		}

		// Boat tags
		tag(MinecraftItemTagGroups.BOATS).add(ItemRegistry.BURNED_OAK_BOAT.get());
	}
}