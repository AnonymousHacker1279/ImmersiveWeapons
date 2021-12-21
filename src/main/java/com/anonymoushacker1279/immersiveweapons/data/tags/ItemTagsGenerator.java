package com.anonymoushacker1279.immersiveweapons.data.tags;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeBlockTagGroups;
import com.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeItemTagGroups;
import com.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsBlockTagGroups;
import com.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import com.anonymoushacker1279.immersiveweapons.data.tags.groups.minecraft.MinecraftBlockTagGroups;
import com.anonymoushacker1279.immersiveweapons.data.tags.groups.minecraft.MinecraftItemTagGroups;
import com.anonymoushacker1279.immersiveweapons.data.tags.lists.ItemTagLists;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked")
public class ItemTagsGenerator extends ItemTagsProvider {

	/**
	 * Constructor for ItemTagsGenerator.
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
	private void addForgeTags() {
		// Copy item tags from block tags
		copy(ForgeBlockTagGroups.STAINED_GLASS, ForgeItemTagGroups.STAINED_GLASS);
		copy(ForgeBlockTagGroups.COBALT_ORE, ForgeItemTagGroups.COBALT_ORE);
		copy(ForgeBlockTagGroups.ORES, ForgeItemTagGroups.ORES);

		// Ingot tags
		tag(ForgeItemTagGroups.COBALT_INGOT).add(DeferredRegistryHandler.COBALT_INGOT.get());
		tag(ForgeItemTagGroups.COPPER_INGOT).add(Items.COPPER_INGOT);
		tag(ForgeItemTagGroups.METAL_INGOTS).addTags(ForgeItemTagGroups.COBALT_INGOT, ForgeItemTagGroups.COPPER_INGOT);
		tag(ForgeItemTagGroups.INGOTS).addTag(ForgeItemTagGroups.METAL_INGOTS);

		// Nugget tags
		tag(ForgeItemTagGroups.COBALT_NUGGET).add(DeferredRegistryHandler.COBALT_NUGGET.get());
		tag(ForgeItemTagGroups.COPPER_NUGGET).add(DeferredRegistryHandler.COPPER_NUGGET.get());
		tag(ForgeItemTagGroups.METAL_NUGGETS).addTags(ForgeItemTagGroups.COBALT_NUGGET, ForgeItemTagGroups.COPPER_NUGGET);
		tag(ForgeItemTagGroups.NUGGETS).addTag(ForgeItemTagGroups.METAL_NUGGETS);
	}

	/**
	 * Add tags under the Immersive Weapons namespace
	 */
	private void addImmersiveWeaponsTags() {
		// Copy item tags from block tags
		copy(ImmersiveWeaponsBlockTagGroups.BURNED_OAK_LOGS, ImmersiveWeaponsItemTagGroups.BURNED_OAK_LOGS);

		// Projectile tags
		tag(ImmersiveWeaponsItemTagGroups.FLARES).add(DeferredRegistryHandler.FLARE.get());
		for (Item item : ItemTagLists.MUSKET_BALLS) {
			tag(ImmersiveWeaponsItemTagGroups.MUSKET_BALLS).add(item);
		}
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