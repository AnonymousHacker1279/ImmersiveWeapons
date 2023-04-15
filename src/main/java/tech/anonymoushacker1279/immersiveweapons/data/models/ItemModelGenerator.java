package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.lists.ItemLists;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet.AbstractBulletItem;

import java.util.*;
import java.util.function.Predicate;

public class ItemModelGenerator extends ItemModelProvider {

	final Map<PikeItem, ResourceLocation> pikeMaterialMap = new HashMap<>(15);
	final Map<GauntletItem, ResourceLocation> gauntletMaterialMap = new HashMap<>(15);

	public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, ImmersiveWeapons.MOD_ID, existingFileHelper);
		fillMaterialMaps();
	}

	private void fillMaterialMaps() {
		// Make a map of pikes to material textures
		pikeMaterialMap.put(ItemRegistry.WOODEN_PIKE.get(),
				new ResourceLocation("block/stripped_oak_log"));
		pikeMaterialMap.put(ItemRegistry.STONE_PIKE.get(),
				new ResourceLocation("block/stone"));
		pikeMaterialMap.put(ItemRegistry.GOLDEN_PIKE.get(),
				new ResourceLocation("block/gold_block"));
		pikeMaterialMap.put(ItemRegistry.COPPER_PIKE.get(),
				new ResourceLocation("block/copper_block"));
		pikeMaterialMap.put(ItemRegistry.IRON_PIKE.get(),
				new ResourceLocation("block/iron_block"));
		pikeMaterialMap.put(ItemRegistry.COBALT_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cobalt_block"));
		pikeMaterialMap.put(ItemRegistry.DIAMOND_PIKE.get(),
				new ResourceLocation("block/diamond_block"));
		pikeMaterialMap.put(ItemRegistry.NETHERITE_PIKE.get(),
				new ResourceLocation("block/netherite_block"));
		pikeMaterialMap.put(ItemRegistry.MOLTEN_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/molten_block"));
		pikeMaterialMap.put(ItemRegistry.TESLA_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/tesla_block"));
		pikeMaterialMap.put(ItemRegistry.VENTUS_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cloud"));
		pikeMaterialMap.put(ItemRegistry.ASTRAL_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/astral_block"));
		pikeMaterialMap.put(ItemRegistry.STARSTORM_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/starstorm_block"));

		// Make a map of gauntlets to material textures
		gauntletMaterialMap.put(ItemRegistry.WOODEN_GAUNTLET.get(),
				new ResourceLocation("block/stripped_oak_log"));
		gauntletMaterialMap.put(ItemRegistry.STONE_GAUNTLET.get(),
				new ResourceLocation("block/stone"));
		gauntletMaterialMap.put(ItemRegistry.GOLDEN_GAUNTLET.get(),
				new ResourceLocation("block/gold_block"));
		gauntletMaterialMap.put(ItemRegistry.COPPER_GAUNTLET.get(),
				new ResourceLocation("block/copper_block"));
		gauntletMaterialMap.put(ItemRegistry.IRON_GAUNTLET.get(),
				new ResourceLocation("block/iron_block"));
		gauntletMaterialMap.put(ItemRegistry.COBALT_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cobalt_block"));
		gauntletMaterialMap.put(ItemRegistry.DIAMOND_GAUNTLET.get(),
				new ResourceLocation("block/diamond_block"));
		gauntletMaterialMap.put(ItemRegistry.NETHERITE_GAUNTLET.get(),
				new ResourceLocation("block/netherite_block"));
		gauntletMaterialMap.put(ItemRegistry.MOLTEN_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/molten_block"));
		gauntletMaterialMap.put(ItemRegistry.TESLA_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/tesla_block"));
		gauntletMaterialMap.put(ItemRegistry.VENTUS_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cloud"));
		gauntletMaterialMap.put(ItemRegistry.ASTRAL_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/astral_block"));
		gauntletMaterialMap.put(ItemRegistry.STARSTORM_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/starstorm_block"));
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

		ItemRegistry.ITEMS.getEntries().stream().map(RegistryObject::get)
				.filter(Predicate.not(ItemLists.MODEL_GENERATOR_IGNORED_ITEMS::contains)).forEach(items::add);

		boolean isAtBlockItems = false;
		boolean isPastToolItems = false;
		boolean isAtSpawnEggItems = false;

		for (Item item : items) {
			if (item == BlockItemRegistry.MOLTEN_ORE_ITEM.get()) {
				isAtBlockItems = true;
			} else if (item == ItemRegistry.WOODEN_SHARD.get()) {
				isPastToolItems = true;
			} else if (item == ItemRegistry.DYING_SOLDIER_SPAWN_EGG.get()) {
				isAtSpawnEggItems = true;
			}

			if (!isAtBlockItems) {
				if (!isPastToolItems) {
					if (item instanceof PikeItem) {
						getBuilder(item.toString())
								.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
										"item/pike")))
								.texture("handle", new ResourceLocation("block/spruce_planks"))
								.texture("material", pikeMaterialMap.get(item));
					} else if (item instanceof GauntletItem) {
						getBuilder(item.toString())
								.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
										"item/gauntlet")))
								.texture("material", gauntletMaterialMap.get(item));
					} else {
						handheldItem(item);
					}
				} else {
					if (!isAtSpawnEggItems) {
						if (item instanceof AbstractBulletItem) {
							getBuilder(item.toString())
									.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
											"item/musket_ball")))
									.texture("all", new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/" + item));
						} else {
							basicItem(item);
						}
					} else {
						spawnEggItem(item);
					}
				}
			} else {
				// Some block items are special
				if (item == BlockItemRegistry.BURNED_OAK_FENCE_ITEM.get()) {
					fenceInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
				} else if (item == BlockItemRegistry.BURNED_OAK_TRAPDOOR_ITEM.get()) {
					trapdoorBottom(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_trapdoor"));
				} else if (item == BlockItemRegistry.BURNED_OAK_DOOR_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_door"));
				} else if (item == BlockItemRegistry.BURNED_OAK_SIGN_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_sign"));
				} else if (item == BlockItemRegistry.BURNED_OAK_BUTTON_ITEM.get()) {
					buttonInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/burned_oak_planks"));
				} else if (item == BlockItemRegistry.AZUL_STAINED_ORCHID_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/azul_stained_orchid"));
				} else if (item == BlockItemRegistry.MOONGLOW_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/moonglow"));
				} else if (item == BlockItemRegistry.STARDUST_SAPLING_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/stardust_sapling"));
				} else if (item == BlockItemRegistry.DEATHWEED_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/deathweed"));
				} else if (item == BlockItemRegistry.BEAR_TRAP_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/bear_trap_closed")));
				} else if (item == BlockItemRegistry.LANDMINE_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/landmine_disarmed")));
				} else if (item == BlockItemRegistry.SPIKE_TRAP_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/spike_trap_activated")));
				} else if (item == BlockItemRegistry.SANDBAG_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/sandbag_0")));
				} else if (item == BlockItemRegistry.SPOTLIGHT_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/spotlight_wall_unlit")));
				} else if (item == BlockItemRegistry.WOODEN_SPIKES_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/wooden_spikes_0")));
				} else if (item == BlockItemRegistry.BARBED_WIRE_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/barbed_wire_0")));
				} else if (item == BlockItemRegistry.MORTAR_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/mortar_0_unloaded")));
				} else if (item == BlockItemRegistry.STARDUST_FENCE_ITEM.get()) {
					fenceInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));
				} else if (item == BlockItemRegistry.STARDUST_TRAPDOOR_ITEM.get()) {
					trapdoorBottom(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_trapdoor"));
				} else if (item == BlockItemRegistry.STARDUST_DOOR_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_door"));
				} else if (item == BlockItemRegistry.STARDUST_SIGN_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_sign"));
				} else if (item == BlockItemRegistry.STARDUST_BUTTON_ITEM.get()) {
					buttonInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/stardust_planks"));
				} else if (item == BlockItemRegistry.CLOUD_MARBLE_BRICK_WALL_ITEM.get()) {
					wallInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/cloud_marble_bricks"));
				} else if (item == BlockItemRegistry.BLOOD_SANDSTONE_WALL_ITEM.get()) {
					wallInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "block/blood_sandstone"));
				} else if (item == BlockItemRegistry.ASTRAL_CRYSTAL_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/astral_crystal"));
				} else if (item == BlockItemRegistry.STARSTORM_CRYSTAL_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"block/starstorm_crystal"));
				} else if (ItemLists.HEAD_ITEMS.contains(item)) {
					entitySkull(item);
				} else {
					blockItem(item);
				}
			}
		}
	}
}