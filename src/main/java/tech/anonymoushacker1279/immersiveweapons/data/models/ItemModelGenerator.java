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
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.DataGenUtils;
import tech.anonymoushacker1279.immersiveweapons.data.lists.ItemLists;
import tech.anonymoushacker1279.immersiveweapons.init.BlockItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.gauntlet.GauntletItem;
import tech.anonymoushacker1279.immersiveweapons.item.pike.PikeItem;
import tech.anonymoushacker1279.immersiveweapons.item.projectile.BulletItem;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ItemModelGenerator extends ItemModelProvider implements DataGenUtils {

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
				.texture("layer0", getItemLocation(item));
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
				.parent(new ModelFile.UncheckedModelFile(getBlockItemLocation(item)));
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
			getBuilder(getItemLocation(item).getPath())
					.parent(new ModelFile.UncheckedModelFile("item/generated"))
					.texture("layer0", getItemLocation(item))
					.override().predicate(new ResourceLocation("trim_type"), 0.1f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.QUARTZ)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.2f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.IRON)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.3f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.NETHERITE)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.4f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.REDSTONE)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.5f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.COPPER)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.6f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.GOLD)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.7f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.EMERALD)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.8f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.DIAMOND)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.9f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.LAPIS)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 1.0f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.AMETHYST)))
					.end();
		} else {
			getBuilder(getItemLocation(item).getPath())
					.parent(new ModelFile.UncheckedModelFile("item/generated"))
					.texture("layer0", getItemLocation(item))
					.texture("layer1", overlay)
					.override().predicate(new ResourceLocation("trim_type"), 0.1f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.QUARTZ)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.2f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.IRON)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.3f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.NETHERITE)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.4f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.REDSTONE)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.5f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.COPPER)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.6f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.GOLD)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.7f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.EMERALD)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.8f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.DIAMOND)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 0.9f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.LAPIS)))
					.end()
					.override().predicate(new ResourceLocation("trim_type"), 1.0f)
					.model(new ModelFile.UncheckedModelFile(getArmorTrimLocation(item, TrimMaterials.AMETHYST)))
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
					.texture("layer0", getItemLocation(item))
					.texture("layer1", trimPath);
		}
	}

	/**
	 * Generate a bow item, including all the predicates for pulling the bow back.
	 *
	 * @param item the <code>Item</code> to generate a model for
	 */
	private void bowItem(Item item) {

		ResourceLocation baseLayer = getItemLocation(item);

		// Main model
		getBuilder(baseLayer.getPath())
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
				.override().predicate(getBowPullingPredicateLocation(), 1)
				.model(new ModelFile.UncheckedModelFile(getBowPullingLocation(item, 0)))
				.end()
				.override().predicate(getBowPullingPredicateLocation(), 1)
				.predicate(new ResourceLocation(ImmersiveWeapons.MOD_ID, "pull"), 0.65f)
				.model(new ModelFile.UncheckedModelFile(getBowPullingLocation(item, 1)))
				.end()
				.override().predicate(getBowPullingPredicateLocation(), 1)
				.predicate(new ResourceLocation(ImmersiveWeapons.MOD_ID, "pull"), 0.9f)
				.model(new ModelFile.UncheckedModelFile(getBowPullingLocation(item, 2)))
				.end();

		// Pulling models
		getBuilder(getBowPullingLocation(item, 0).getPath())
				.parent(new ModelFile.UncheckedModelFile(baseLayer))
				.texture("layer0", getBowPullingLocation(item, 0));
		getBuilder(getBowPullingLocation(item, 1).getPath())
				.parent(new ModelFile.UncheckedModelFile(baseLayer))
				.texture("layer0", getBowPullingLocation(item, 1));
		getBuilder(getBowPullingLocation(item, 2).getPath())
				.parent(new ModelFile.UncheckedModelFile(baseLayer))
				.texture("layer0", getBowPullingLocation(item, 2));
	}

	@Override
	protected void registerModels() {
		List<Item> items = new ArrayList<>(250);

		ItemRegistry.ITEMS.getEntries().stream().map(Supplier::get)
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
								.texture("handle", new ResourceLocation("block/spruce_planks"))
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
							getBuilder(getItemLocation(item).getPath())
									.parent(new ModelFile.UncheckedModelFile(new ResourceLocation(ImmersiveWeapons.MOD_ID,
											"item/musket_ball")))
									.texture("all", getItemLocation(item));
						} else {
							basicItem(item);
						}
					} else if (!isAtSpawnEggItems && item instanceof ArmorItem armorItem) {
						if (armorItem instanceof DyeableArmorItem) {
							armorItem(armorItem, getItemLocation(item).withSuffix("_overlay"));
						} else {
							armorItem(armorItem, null);
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
				} else if (item == BlockItemRegistry.BURNED_OAK_HANGING_SIGN_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "burned_oak_hanging_sign"));
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
				} else if (item == BlockItemRegistry.STARDUST_HANGING_SIGN_ITEM.get()) {
					basicItem(new ResourceLocation(ImmersiveWeapons.MOD_ID, "stardust_hanging_sign"));
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