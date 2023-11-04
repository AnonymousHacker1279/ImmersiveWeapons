package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.lists.ItemLists;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;

import java.util.*;
import java.util.function.Predicate;

public class ItemModelGenerator extends ItemModelProvider {

	final Map<PikeItem, ResourceLocation> pikeMaterialMap = new HashMap<>(15);
	final Map<GauntletItem, ResourceLocation> gauntletMaterialMap = new HashMap<>(15);
	final ArrayList<ResourceKey<TrimMaterial>> trimMaterials = new ArrayList<>(15);

	public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, ImmersiveWeapons.MOD_ID, existingFileHelper);
		fillMaterialMaps();
	}

	private void fillMaterialMaps() {
		// Make a map of pikes to material textures
		pikeMaterialMap.put(ItemRegistry.WOODEN_PIKE.get(),
				new ResourceLocation("blockLocation/stripped_oak_log"));
		pikeMaterialMap.put(ItemRegistry.STONE_PIKE.get(),
				new ResourceLocation("blockLocation/stone"));
		pikeMaterialMap.put(ItemRegistry.GOLDEN_PIKE.get(),
				new ResourceLocation("blockLocation/gold_block"));
		pikeMaterialMap.put(ItemRegistry.COPPER_PIKE.get(),
				new ResourceLocation("blockLocation/copper_block"));
		pikeMaterialMap.put(ItemRegistry.IRON_PIKE.get(),
				new ResourceLocation("blockLocation/iron_block"));
		pikeMaterialMap.put(ItemRegistry.COBALT_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "blockLocation/cobalt_block"));
		pikeMaterialMap.put(ItemRegistry.DIAMOND_PIKE.get(),
				new ResourceLocation("blockLocation/diamond_block"));
		pikeMaterialMap.put(ItemRegistry.NETHERITE_PIKE.get(),
				new ResourceLocation("blockLocation/netherite_block"));
		pikeMaterialMap.put(ItemRegistry.MOLTEN_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/custom/molten"));
		pikeMaterialMap.put(ItemRegistry.TESLA_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/custom/tesla"));
		pikeMaterialMap.put(ItemRegistry.VENTUS_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/custom/ventus"));
		pikeMaterialMap.put(ItemRegistry.ASTRAL_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/custom/astral"));
		pikeMaterialMap.put(ItemRegistry.STARSTORM_PIKE.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/custom/starstorm"));

		// Make a map of gauntlets to material textures

		gauntletMaterialMap.put(ItemRegistry.WOODEN_GAUNTLET.get(),
				new ResourceLocation("blockLocation/stripped_oak_log"));
		gauntletMaterialMap.put(ItemRegistry.STONE_GAUNTLET.get(),
				new ResourceLocation("blockLocation/stone"));
		gauntletMaterialMap.put(ItemRegistry.GOLDEN_GAUNTLET.get(),
				new ResourceLocation("blockLocation/gold_block"));
		gauntletMaterialMap.put(ItemRegistry.COPPER_GAUNTLET.get(),
				new ResourceLocation("blockLocation/copper_block"));
		gauntletMaterialMap.put(ItemRegistry.IRON_GAUNTLET.get(),
				new ResourceLocation("blockLocation/iron_block"));
		gauntletMaterialMap.put(ItemRegistry.COBALT_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "blockLocation/cobalt_block"));
		gauntletMaterialMap.put(ItemRegistry.DIAMOND_GAUNTLET.get(),
				new ResourceLocation("blockLocation/diamond_block"));
		gauntletMaterialMap.put(ItemRegistry.NETHERITE_GAUNTLET.get(),
				new ResourceLocation("blockLocation/netherite_block"));
		gauntletMaterialMap.put(ItemRegistry.MOLTEN_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/custom/molten"));
		gauntletMaterialMap.put(ItemRegistry.TESLA_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/custom/tesla"));
		gauntletMaterialMap.put(ItemRegistry.VENTUS_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/custom/ventus"));
		gauntletMaterialMap.put(ItemRegistry.ASTRAL_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/custom/astral"));
		gauntletMaterialMap.put(ItemRegistry.STARSTORM_GAUNTLET.get(),
				new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/custom/starstorm"));

		// Make a list of trim materials
		trimMaterials.add(TrimMaterials.QUARTZ);
		trimMaterials.add(TrimMaterials.IRON);
		trimMaterials.add(TrimMaterials.NETHERITE);
		trimMaterials.add(TrimMaterials.REDSTONE);
		trimMaterials.add(TrimMaterials.COPPER);
		trimMaterials.add(TrimMaterials.GOLD);
		trimMaterials.add(TrimMaterials.EMERALD);
		trimMaterials.add(TrimMaterials.DIAMOND);
		trimMaterials.add(TrimMaterials.LAPIS);
		trimMaterials.add(TrimMaterials.AMETHYST);
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
	 * Generate a blockLocation item.
	 *
	 * @param item the <code>Item</code> to generate a model for
	 */
	private void blockItem(Item item) {
		getBuilder(item.toString())
				.parent(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":blockLocation/" + item));
	}

	/**
	 * Generate an armor item. Automatically adds the proper trim_type predicates.
	 *
	 * @param item    the <code>Item</code> to generate a model for
	 * @param overlay an overlay texture location, or null for no overlay
	 */
	private void armorItem(ArmorItem item, @Nullable ResourceLocation overlay) {
		// Create base item, with references to trim variants
		if (overlay == null) {
			getBuilder(item.toString())
					.parent(new ModelFile.UncheckedModelFile("item/generated"))
					.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/" + item))
					.override().predicate(new ResourceLocation("trim_type"), 0.1f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.QUARTZ.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.2f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.IRON.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.3f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.NETHERITE.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.4f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.REDSTONE.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.5f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.COPPER.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.6f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.GOLD.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.7f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.EMERALD.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.8f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.DIAMOND.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.9f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.LAPIS.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 1.0f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.AMETHYST.location().getPath() + "_trim"))
					.end();
		} else {
			getBuilder(item.toString())
					.parent(new ModelFile.UncheckedModelFile("item/generated"))
					.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/" + item))
					.texture("layer1", overlay)
					.override().predicate(new ResourceLocation("trim_type"), 0.1f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.QUARTZ.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.2f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.IRON.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.3f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.NETHERITE.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.4f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.REDSTONE.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.5f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.COPPER.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.6f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.GOLD.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.7f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.EMERALD.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.8f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.DIAMOND.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.9f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.LAPIS.location().getPath() + "_trim"))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 1.0f)
					.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_"
							+ TrimMaterials.AMETHYST.location().getPath() + "_trim"))
					.end();
		}

		// Create trimmed variants
		for (ResourceKey<TrimMaterial> trimMaterial : trimMaterials) {
			String armorType = switch (item.getEquipmentSlot()) {
				case HEAD -> "helmet";
				case CHEST -> "chestplate";
				case LEGS -> "leggings";
				case FEET -> "boots";
				default -> "";
			};

			ResourceLocation trimPath = new ResourceLocation("trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath());

			existingFileHelper.trackGenerated(trimPath, PackType.CLIENT_RESOURCES, ".png", "textures");

			getBuilder(item + "_" + trimMaterial.location().getPath() + "_trim")
					.parent(new ModelFile.UncheckedModelFile("item/generated"))
					.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/" + item))
					.texture("layer1", trimPath);
		}
	}

	/**
	 * Generate a bow item, including all the predicates for pulling the bow back.
	 *
	 * @param item the <code>Item</code> to generate a model for
	 */
	private void bowItem(Item item) {

		ResourceLocation baseLayer = new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/" + item);

		// Main model
		getBuilder(item.toString())
				.parent(new ModelFile.UncheckedModelFile("item/generated"))
				.transforms()
				.transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
				.rotation(-80, 260, -40)
				.translation(-1, -2, 2.5f)
				.scale(0.9f)
				.end()
				.transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND)
				.rotation(-80, -280, 40)
				.translation(-1, -2, 2.5f)
				.scale(0.9f)
				.end()
				.transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
				.rotation(0, -90, 25)
				.translation(1.13f, 3.2f, 1.13f)
				.scale(0.68f)
				.end()
				.transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
				.rotation(0, 90, -25)
				.translation(1.13f, 3.2f, 1.13f)
				.scale(0.68f)
				.end()
				.end()
				.texture("layer0", baseLayer)
				.override().predicate(new ResourceLocation(ImmersiveWeapons.MOD_ID, "pulling"), 1)
				.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_pulling_0"))
				.end()
				.override().predicate(new ResourceLocation(ImmersiveWeapons.MOD_ID, "pulling"), 1)
				.predicate(new ResourceLocation(ImmersiveWeapons.MOD_ID, "pull"), 0.65f)
				.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_pulling_1"))
				.end()
				.override().predicate(new ResourceLocation(ImmersiveWeapons.MOD_ID, "pulling"), 1)
				.predicate(new ResourceLocation(ImmersiveWeapons.MOD_ID, "pull"), 0.9f)
				.model(new ModelFile.UncheckedModelFile(ImmersiveWeapons.MOD_ID + ":item/" + item + "_pulling_2"))
				.end();

		// Pulling models
		getBuilder(item + "_pulling_0")
				.parent(new ModelFile.UncheckedModelFile(baseLayer))
				.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/" + item + "_pulling_0"));
		getBuilder(item + "_pulling_1")
				.parent(new ModelFile.UncheckedModelFile(baseLayer))
				.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/" + item + "_pulling_1"));
		getBuilder(item + "_pulling_2")
				.parent(new ModelFile.UncheckedModelFile(baseLayer))
				.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/" + item + "_pulling_2"));
	}

	@Override
	protected void registerModels() {
		List<Item> items = new ArrayList<>(250);

		ItemRegistry.ITEMS.getEntries().stream().map(RegistryObject::get)
				.filter(Predicate.not(ItemLists.MODEL_GENERATOR_IGNORED_ITEMS::contains)).forEach(items::add);

		boolean isAtBlockItems = false;
		boolean isPastToolItems = false;
		boolean isAtArmorItems = false;
		boolean isAtSpawnEggItems = false;

		for (Item item : items) {
			if (item == BlockItemRegistry.MOLTEN_ORE_ITEM.get()) {
				isAtBlockItems = true;
			} else if (item == ItemRegistry.WOODEN_SHARD.get()) {
				isPastToolItems = true;
			} else if (item == ItemRegistry.MOLTEN_HELMET.get()) {
				isAtArmorItems = true;
			} else if (item == ItemRegistry.DYING_SOLDIER_SPAWN_EGG.get()) {
				isAtSpawnEggItems = true;
			}

			if (!isAtBlockItems) {
				if (!isPastToolItems) {
					if (item instanceof PikeItem) {
						getBuilder(item.toString())
								.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
										"item/pike")))
								.texture("handle", new ResourceLocation("blockLocation/spruce_planks"))
								.texture("material", pikeMaterialMap.get(item));
					} else if (item instanceof GauntletItem) {
						if (item == ItemRegistry.IRON_GAUNTLET.get()) {
							getBuilder(item.toString())
									.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
											"item/gauntlet")))
									.texture("material", gauntletMaterialMap.get(item))
									.override()
									.predicate(new ResourceLocation(ImmersiveWeapons.MOD_ID, "gunslinger"), 1)
									.model(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
											"item/iron_gauntlet_alt")));
						}
						getBuilder(item.toString())
								.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
										"item/gauntlet")))
								.texture("material", gauntletMaterialMap.get(item));
					} else if (item == ItemRegistry.ICE_BOW.get() || item == ItemRegistry.DRAGONS_BREATH_BOW.get() || item == ItemRegistry.AURORA_BOW.get()) {
						bowItem(item);
					} else {
						handheldItem(item);
					}
				} else {
					if (!isAtArmorItems) {
						if (item instanceof BulletItem<?>) {
							getBuilder(item.toString())
									.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
											"item/musket_ball")))
									.texture("all", new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/" + item));
						} else {
							basicItem(item);
						}
					} else if (!isAtSpawnEggItems && item instanceof ArmorItem armorItem) {
						if (armorItem instanceof DyeableArmorItem) {
							armorItem(armorItem, new ResourceLocation(ImmersiveWeapons.MOD_ID, "item/" + item + "_overlay"));
						} else {
							armorItem(armorItem, null);
						}
					} else {
						spawnEggItem(item);
					}
				}
			} else {
				// Some blockLocation items are special
				if (item == BlockItemRegistry.BURNED_OAK_FENCE_ITEM.get()) {
					fenceInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "blockLocation/burned_oak_planks"));
				} else if (item == BlockItemRegistry.BURNED_OAK_TRAPDOOR_ITEM.get()) {
					trapdoorBottom(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "blockLocation/burned_oak_trapdoor"));
				} else if (item == BlockItemRegistry.BURNED_OAK_DOOR_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_door"));
				} else if (item == BlockItemRegistry.BURNED_OAK_SIGN_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_sign"));
				} else if (item == BlockItemRegistry.BURNED_OAK_HANGING_SIGN_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_hanging_sign"));
				} else if (item == BlockItemRegistry.BURNED_OAK_BUTTON_ITEM.get()) {
					buttonInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "blockLocation/burned_oak_planks"));
				} else if (item == BlockItemRegistry.AZUL_STAINED_ORCHID_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/azul_stained_orchid"));
				} else if (item == BlockItemRegistry.MOONGLOW_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/moonglow"));
				} else if (item == BlockItemRegistry.STARDUST_SAPLING_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/stardust_sapling"));
				} else if (item == BlockItemRegistry.DEATHWEED_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/deathweed"));
				} else if (item == BlockItemRegistry.BEAR_TRAP_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/bear_trap_closed")));
				} else if (item == BlockItemRegistry.LANDMINE_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/landmine_disarmed")));
				} else if (item == BlockItemRegistry.SPIKE_TRAP_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/spike_trap_activated")));
				} else if (item == BlockItemRegistry.SANDBAG_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/sandbag_0")));
				} else if (item == BlockItemRegistry.SPOTLIGHT_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/spotlight_wall_unlit")));
				} else if (item == BlockItemRegistry.WOODEN_SPIKES_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/wooden_spikes_0")));
				} else if (item == BlockItemRegistry.BARBED_WIRE_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/barbed_wire_0")));
				} else if (item == BlockItemRegistry.MORTAR_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/mortar_0_unloaded")));
				} else if (item == BlockItemRegistry.STARDUST_FENCE_ITEM.get()) {
					fenceInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "blockLocation/stardust_planks"));
				} else if (item == BlockItemRegistry.STARDUST_TRAPDOOR_ITEM.get()) {
					trapdoorBottom(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "blockLocation/stardust_trapdoor"));
				} else if (item == BlockItemRegistry.STARDUST_DOOR_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_door"));
				} else if (item == BlockItemRegistry.STARDUST_SIGN_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_sign"));
				} else if (item == BlockItemRegistry.STARDUST_HANGING_SIGN_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_hanging_sign"));
				} else if (item == BlockItemRegistry.STARDUST_BUTTON_ITEM.get()) {
					buttonInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "blockLocation/stardust_planks"));
				} else if (item == BlockItemRegistry.CLOUD_MARBLE_BRICK_WALL_ITEM.get()) {
					wallInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "blockLocation/cloud_marble_bricks"));
				} else if (item == BlockItemRegistry.BLOOD_SANDSTONE_WALL_ITEM.get()) {
					wallInventory(item.toString(),
							new ResourceLocation(ImmersiveWeapons.MOD_ID, "blockLocation/blood_sandstone"));
				} else if (item == BlockItemRegistry.ASTRAL_CRYSTAL_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/astral_crystal"));
				} else if (item == BlockItemRegistry.STARSTORM_CRYSTAL_ITEM.get()) {
					getBuilder(item.toString())
							.parent(new ModelFile.UncheckedModelFile("item/generated"))
							.texture("layer0", new ResourceLocation(ImmersiveWeapons.MOD_ID,
									"blockLocation/starstorm_crystal"));
				} else if (ItemLists.HEAD_ITEMS.contains(item)) {
					entitySkull(item);
				} else {
					blockItem(item);
				}
			}
		}
	}
}