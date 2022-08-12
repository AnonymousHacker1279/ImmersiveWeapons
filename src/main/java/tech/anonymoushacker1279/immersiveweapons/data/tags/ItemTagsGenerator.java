package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.minecraft.MinecraftBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.minecraft.MinecraftItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.lists.ItemTagLists;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import javax.annotation.Nullable;

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
		tag(ForgeItemTagGroups.COBALT_INGOTS).add(DeferredRegistryHandler.COBALT_INGOT.get());
		tag(Tags.Items.INGOTS_COPPER).add(Items.COPPER_INGOT);
		tag(ForgeItemTagGroups.METAL_INGOTS).addTags(ForgeItemTagGroups.COBALT_INGOTS, Tags.Items.INGOTS_COPPER,
				Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_GOLD);
		tag(Tags.Items.INGOTS).addTag(ForgeItemTagGroups.METAL_INGOTS);

		// Nugget tags
		tag(ForgeItemTagGroups.COBALT_NUGGETS).add(DeferredRegistryHandler.COBALT_NUGGET.get());
		tag(ForgeItemTagGroups.COPPER_NUGGETS).add(DeferredRegistryHandler.COPPER_NUGGET.get());
		tag(ForgeItemTagGroups.METAL_NUGGETS).addTags(ForgeItemTagGroups.COBALT_NUGGETS, ForgeItemTagGroups.COPPER_NUGGETS,
				Tags.Items.NUGGETS_IRON, Tags.Items.NUGGETS_GOLD);
		tag(Tags.Items.NUGGETS).addTag(ForgeItemTagGroups.METAL_NUGGETS);

		// Dust tags
		tag(ForgeItemTagGroups.SULFUR_DUSTS).add(DeferredRegistryHandler.SULFUR.get());

		// Tool tags

		// Sword
		tag(Tags.Items.TOOLS_SWORDS).add(DeferredRegistryHandler.COPPER_SWORD.get(),
				DeferredRegistryHandler.COBALT_SWORD.get(),
				DeferredRegistryHandler.MOLTEN_SWORD.get(),
				DeferredRegistryHandler.TESLA_SWORD.get(),
				DeferredRegistryHandler.VENTUS_SWORD.get());
		// Pickaxe
		tag(Tags.Items.TOOLS_PICKAXES).add(DeferredRegistryHandler.COPPER_PICKAXE.get(),
				DeferredRegistryHandler.COBALT_PICKAXE.get(),
				DeferredRegistryHandler.MOLTEN_PICKAXE.get(),
				DeferredRegistryHandler.TESLA_PICKAXE.get(),
				DeferredRegistryHandler.VENTUS_PICKAXE.get());
		// Axe
		tag(Tags.Items.TOOLS_AXES).add(DeferredRegistryHandler.COPPER_AXE.get(),
				DeferredRegistryHandler.COBALT_AXE.get(),
				DeferredRegistryHandler.MOLTEN_AXE.get(),
				DeferredRegistryHandler.TESLA_AXE.get(),
				DeferredRegistryHandler.VENTUS_AXE.get());
		// Shovel
		tag(Tags.Items.TOOLS_SHOVELS).add(DeferredRegistryHandler.COPPER_SHOVEL.get(),
				DeferredRegistryHandler.COBALT_SHOVEL.get(),
				DeferredRegistryHandler.MOLTEN_SHOVEL.get(),
				DeferredRegistryHandler.TESLA_SHOVEL.get(),
				DeferredRegistryHandler.VENTUS_SHOVEL.get());
		// Hoe
		tag(Tags.Items.TOOLS_HOES).add(DeferredRegistryHandler.COPPER_HOE.get(),
				DeferredRegistryHandler.COBALT_HOE.get(),
				DeferredRegistryHandler.MOLTEN_HOE.get(),
				DeferredRegistryHandler.TESLA_HOE.get(),
				DeferredRegistryHandler.VENTUS_HOE.get());

		// Armor tags

		// Helmet
		tag(Tags.Items.ARMORS_HELMETS).add(DeferredRegistryHandler.COPPER_HELMET.get(),
				DeferredRegistryHandler.COBALT_HELMET.get(),
				DeferredRegistryHandler.MOLTEN_HELMET.get(),
				DeferredRegistryHandler.TESLA_HELMET.get(),
				DeferredRegistryHandler.VENTUS_HELMET.get());
		// Chestplate
		tag(Tags.Items.ARMORS_CHESTPLATES).add(DeferredRegistryHandler.COPPER_CHESTPLATE.get(),
				DeferredRegistryHandler.COBALT_CHESTPLATE.get(),
				DeferredRegistryHandler.MOLTEN_CHESTPLATE.get(),
				DeferredRegistryHandler.TESLA_CHESTPLATE.get(),
				DeferredRegistryHandler.VENTUS_CHESTPLATE.get());
		// Leggings
		tag(Tags.Items.ARMORS_LEGGINGS).add(DeferredRegistryHandler.COPPER_LEGGINGS.get(),
				DeferredRegistryHandler.COBALT_LEGGINGS.get(),
				DeferredRegistryHandler.MOLTEN_LEGGINGS.get(),
				DeferredRegistryHandler.TESLA_LEGGINGS.get(),
				DeferredRegistryHandler.VENTUS_LEGGINGS.get());
		// Boots
		tag(Tags.Items.ARMORS_BOOTS).add(DeferredRegistryHandler.COPPER_BOOTS.get(),
				DeferredRegistryHandler.COBALT_BOOTS.get(),
				DeferredRegistryHandler.MOLTEN_BOOTS.get(),
				DeferredRegistryHandler.TESLA_BOOTS.get(),
				DeferredRegistryHandler.VENTUS_BOOTS.get());

		// Head tags
		tag(Tags.Items.HEADS).add(DeferredRegistryHandler.MINUTEMAN_HEAD_ITEM.get(),
				DeferredRegistryHandler.FIELD_MEDIC_HEAD_ITEM.get(),
				DeferredRegistryHandler.DYING_SOLDIER_HEAD_ITEM.get(),
				DeferredRegistryHandler.WANDERING_WARRIOR_HEAD_ITEM.get(),
				DeferredRegistryHandler.HANS_HEAD_ITEM.get());
	}

	/**
	 * Add tags under the Immersive Weapons namespace
	 */
	private void addImmersiveWeaponsTags() {
		// Copy item tags from block tags
		copy(ImmersiveWeaponsBlockTagGroups.BURNED_OAK_LOGS, ImmersiveWeaponsItemTagGroups.BURNED_OAK_LOGS);
		copy(ImmersiveWeaponsBlockTagGroups.STARDUST_LOGS, ImmersiveWeaponsItemTagGroups.STARDUST_LOGS);

		// Projectile tags
		tag(ImmersiveWeaponsItemTagGroups.FLARES).add(DeferredRegistryHandler.FLARE.get());
		for (Item item : ItemTagLists.MUSKET_BALLS) {
			tag(ImmersiveWeaponsItemTagGroups.MUSKET_BALLS).add(item);
		}

		// Ingot tags
		tag(ImmersiveWeaponsItemTagGroups.MOLTEN_INGOTS).add(DeferredRegistryHandler.MOLTEN_INGOT.get());
		tag(ImmersiveWeaponsItemTagGroups.ELECTRIC_INGOTS).add(DeferredRegistryHandler.ELECTRIC_INGOT.get());
		tag(ImmersiveWeaponsItemTagGroups.TESLA_INGOTS).add(DeferredRegistryHandler.TESLA_INGOT.get());

		// Shard tags
		tag(ImmersiveWeaponsItemTagGroups.MOLTEN_SHARDS).add(DeferredRegistryHandler.MOLTEN_SHARD.get());
		tag(ImmersiveWeaponsItemTagGroups.VENTUS_SHARDS).add(DeferredRegistryHandler.VENTUS_SHARD.get());
		tag(ImmersiveWeaponsItemTagGroups.DIAMOND_SHARDS).add(DeferredRegistryHandler.DIAMOND_SHARD.get());
		tag(ImmersiveWeaponsItemTagGroups.STONE_SHARDS).add(DeferredRegistryHandler.STONE_SHARD.get());
		tag(ImmersiveWeaponsItemTagGroups.WOODEN_SHARDS).add(DeferredRegistryHandler.WOODEN_SHARD.get());

		// Rod tags
		tag(ImmersiveWeaponsItemTagGroups.OBSIDIAN_RODS).add(DeferredRegistryHandler.OBSIDIAN_ROD.get());
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
		tag(MinecraftItemTagGroups.SIGNS).add(DeferredRegistryHandler.BURNED_OAK_SIGN_ITEM.get());

		// Arrow tags
		for (Item item : ItemTagLists.ARROWS) {
			tag(MinecraftItemTagGroups.ARROWS).add(item);
		}

		// Boat tags
		tag(MinecraftItemTagGroups.BOATS).add(DeferredRegistryHandler.BURNED_OAK_BOAT.get());
	}
}