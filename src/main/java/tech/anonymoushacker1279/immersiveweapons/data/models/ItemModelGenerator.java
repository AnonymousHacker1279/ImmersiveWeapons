package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.models.lists.ItemLists;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ItemModelGenerator extends ItemModelProvider {

	public ItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	/**
	 * Generate a handheld item.
	 *
	 * @param item the <code>Item</code> to generate a model for
	 */
	private void handheldItem(Item item) {
		getBuilder(item.toString())
				.parent(new ModelFile.UncheckedModelFile("item/handheld"))
				.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
						"item/" + item));
	}

	/**
	 * Generate a spawn egg item.
	 *
	 * @param item the <code>Item</code> to generate a model for
	 */
	private void spawnEggItem(Item item) {
		getBuilder(item.toString())
				.parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
	}

	/**
	 * Generate a skull item.
	 *
	 * @param item the <code>Item</code> to generate a model for
	 */
	private void entitySkull(Item item) {
		getBuilder(item.toString())
				.parent(new ModelFile.UncheckedModelFile("item/template_skull"));
	}

	/**
	 * Generate a block item.
	 *
	 * @param item the <code>Item</code> to generate a model for
	 */
	private void blockItem(Item item) {
		getBuilder(item.toString())
				.parent(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":block/" + item));
	}

	@Override
	protected void registerModels() {
		List<Item> items = new ArrayList<>(250);

		DeferredRegistryHandler.ITEMS.getEntries().stream().map(RegistryObject::get)
				.filter(Predicate.not(ItemLists.modelGeneratorIgnoredItems::contains)).forEach(items::add);

		boolean isAtBlockItems = false;
		boolean isPastToolItems = false;
		boolean isAtSpawnEggItems = false;

		for (Item item : items) {
			if (item == DeferredRegistryHandler.MOLTEN_ORE_ITEM.get()) {
				isAtBlockItems = true;
			} else if (item == DeferredRegistryHandler.STONE_SHARD.get()) {
				isPastToolItems = true;
			} else if (item == DeferredRegistryHandler.DYING_SOLDIER_SPAWN_EGG.get()) {
				isAtSpawnEggItems = true;
			}

			if (!isAtBlockItems) {
				if (!isPastToolItems) {
					handheldItem(item);
				} else {
					if (!isAtSpawnEggItems) {
						basicItem(item);
					} else {
						spawnEggItem(item);
					}
				}
			} else {
				// Some block items are special
				if (item == DeferredRegistryHandler.BURNED_OAK_FENCE_ITEM.get()) {
					fenceInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
				} else if (item == DeferredRegistryHandler.BURNED_OAK_TRAPDOOR_ITEM.get()) {
					trapdoorBottom(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_trapdoor"));
				} else if (item == DeferredRegistryHandler.BURNED_OAK_DOOR_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_door"));
				} else if (item == DeferredRegistryHandler.BURNED_OAK_SIGN_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_sign"));
				} else if (item == DeferredRegistryHandler.BURNED_OAK_BUTTON_ITEM.get()) {
					buttonInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
				} else if (item == DeferredRegistryHandler.AZUL_STAINED_ORCHID_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/azul_stained_orchid"));
				} else if (item == DeferredRegistryHandler.MOONGLOW_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/moonglow"));
				} else if (item == DeferredRegistryHandler.STARDUST_SAPLING_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/stardust_sapling"));
				} else if (item == DeferredRegistryHandler.BEAR_TRAP_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/bear_trap_closed")));
				} else if (item == DeferredRegistryHandler.LANDMINE_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/landmine_disarmed")));
				} else if (item == DeferredRegistryHandler.SPIKE_TRAP_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/spike_trap_activated")));
				} else if (item == DeferredRegistryHandler.SANDBAG_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/sandbag_0")));
				} else if (item == DeferredRegistryHandler.SPOTLIGHT_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/spotlight_wall_unlit")));
				} else if (item == DeferredRegistryHandler.MORTAR_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/mortar_0_unloaded")));
				} else if (item == DeferredRegistryHandler.STARDUST_FENCE_ITEM.get()) {
					fenceInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));
				} else if (item == DeferredRegistryHandler.STARDUST_TRAPDOOR_ITEM.get()) {
					trapdoorBottom(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_trapdoor"));
				} else if (item == DeferredRegistryHandler.STARDUST_DOOR_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_door"));
				} else if (item == DeferredRegistryHandler.STARDUST_SIGN_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_sign"));
				} else if (item == DeferredRegistryHandler.STARDUST_BUTTON_ITEM.get()) {
					buttonInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));
				} else if (ItemLists.headItems.contains(item)) {
					entitySkull(item);
				} else {
					blockItem(item);
				}
			}
		}
	}
}