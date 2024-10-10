package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.CustomDataGenerator;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class BlockTagsGenerator extends BlockTagsProvider {

	public BlockTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	/**
	 * Add tags to data generation.
	 */
	@Override
	protected void addTags(Provider provider) {
		addCommonTags();
		addImmersiveWeaponsTags();
		addMinecraftTags();
		addMiningBlockTags();
	}

	/**
	 * Add tags under the Forge namespace
	 */
	@SuppressWarnings("unchecked")
	private void addCommonTags() {
		// Bulletproof glass tag
		for (Block block : CustomDataGenerator.ALL_BLOCKS) {
			if (block.getDescriptionId().contains("bulletproof_glass")) {
				tag(CommonBlockTagGroups.BULLETPROOF_GLASS).add(block);
			}
		}

		// Glass tag
		tag(Blocks.GLASS_BLOCKS).addTag(CommonBlockTagGroups.BULLETPROOF_GLASS);

		// Stained glass tag
		for (Block block : CustomDataGenerator.ALL_BLOCKS) {
			if (block.getDescriptionId().contains("stained_bulletproof_glass")) {
				tag(CommonBlockTagGroups.STAINED_GLASS).add(block);
			}
		}

		// Ore tags
		tag(CommonBlockTagGroups.COBALT_ORES).add(
				BlockRegistry.COBALT_ORE.get(),
				BlockRegistry.DEEPSLATE_COBALT_ORE.get());
		tag(CommonBlockTagGroups.SULFUR_ORES).add(
				BlockRegistry.SULFUR_ORE.get(),
				BlockRegistry.DEEPSLATE_SULFUR_ORE.get(),
				BlockRegistry.NETHER_SULFUR_ORE.get());
		tag(Blocks.ORES).addTags(
				CommonBlockTagGroups.COBALT_ORES,
				CommonBlockTagGroups.SULFUR_ORES);
	}

	/**
	 * Add tags under the Immersive Weapons namespace
	 */
	private void addImmersiveWeaponsTags() {
		// Burned oak logs tag
		tag(IWBlockTagGroups.BURNED_OAK_LOGS).add(
				BlockRegistry.BURNED_OAK_LOG.get(),
				BlockRegistry.BURNED_OAK_WOOD.get(),
				BlockRegistry.STRIPPED_BURNED_OAK_LOG.get(),
				BlockRegistry.STRIPPED_BURNED_OAK_WOOD.get());

		// Stardust logs tag
		tag(IWBlockTagGroups.STARDUST_LOGS).add(
				BlockRegistry.STARDUST_LOG.get(),
				BlockRegistry.STARDUST_WOOD.get(),
				BlockRegistry.STRIPPED_STARDUST_LOG.get(),
				BlockRegistry.STRIPPED_STARDUST_WOOD.get());

		// Tool tags
		tag(IWBlockTagGroups.INCORRECT_FOR_COPPER_TOOL)
				.addTag(BlockTags.NEEDS_DIAMOND_TOOL)
				.addTag(Blocks.NEEDS_NETHERITE_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_COBALT_TOOL)
				.addTag(BlockTags.NEEDS_DIAMOND_TOOL)
				.addTag(Blocks.NEEDS_NETHERITE_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_MOLTEN_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_TESLA_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_VENTUS_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_STARSTORM_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_ASTRAL_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_HANSIUM_TOOL);

		tag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
	}

	/**
	 * Add tags under the Minecraft namespace
	 */
	@SuppressWarnings("unchecked")
	private void addMinecraftTags() {
		// Fence tag
		tag(BlockTags.FENCES).add(BlockRegistry.BARBED_WIRE_FENCE.get());

		// Burnable logs tag
		tag(BlockTags.LOGS_THAT_BURN).addTags(
				IWBlockTagGroups.BURNED_OAK_LOGS,
				IWBlockTagGroups.STARDUST_LOGS);

		// Planks tag
		tag(BlockTags.PLANKS).add(
				BlockRegistry.BURNED_OAK_PLANKS.get(),
				BlockRegistry.STARDUST_PLANKS.get());

		// Slabs tag
		tag(BlockTags.SLABS).add(
				BlockRegistry.CLOUD_MARBLE_BRICK_SLAB.get(),
				BlockRegistry.BLOOD_SANDSTONE_SLAB.get(),
				BlockRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB.get());

		// Stairs tag
		tag(BlockTags.STAIRS).add(
				BlockRegistry.CLOUD_MARBLE_BRICK_STAIRS.get(),
				BlockRegistry.BLOOD_SANDSTONE_STAIRS.get(),
				BlockRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS.get());

		// Standing signs tag
		tag(BlockTags.STANDING_SIGNS).add(BlockRegistry.BURNED_OAK_SIGN.get());

		// Wall signs tag
		tag(BlockTags.WALL_SIGNS).add(BlockRegistry.BURNED_OAK_SIGN.get());

		// Wooden buttons tag
		tag(BlockTags.WOODEN_BUTTONS).add(
				BlockRegistry.BURNED_OAK_BUTTON.get(),
				BlockRegistry.STARDUST_BUTTON.get());

		// Wooden doors tag
		tag(BlockTags.WOODEN_DOORS).add(
				BlockRegistry.BURNED_OAK_DOOR.get(),
				BlockRegistry.STARDUST_DOOR.get());

		// Wooden fences tag
		tag(BlockTags.WOODEN_FENCES).add(
				BlockRegistry.BURNED_OAK_FENCE.get(),
				BlockRegistry.STARDUST_FENCE.get());

		// Wooden pressure plates tag
		tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
				BlockRegistry.BURNED_OAK_PRESSURE_PLATE.get(),
				BlockRegistry.STARDUST_PRESSURE_PLATE.get());

		// Wooden slabs tag
		tag(BlockTags.WOODEN_SLABS).add(
				BlockRegistry.BURNED_OAK_SLAB.get(),
				BlockRegistry.STARDUST_SLAB.get());

		// Wooden stairs tag
		tag(BlockTags.WOODEN_STAIRS).add(
				BlockRegistry.BURNED_OAK_STAIRS.get(),
				BlockRegistry.STARDUST_STAIRS.get());

		// Wooden trapdoors tag
		tag(BlockTags.WOODEN_TRAPDOORS).add(
				BlockRegistry.BURNED_OAK_TRAPDOOR.get(),
				BlockRegistry.STARDUST_TRAPDOOR.get());

		// Small flowers tag
		tag(BlockTags.SMALL_FLOWERS).add(BlockRegistry.MOONGLOW.get());

		// Leaves tag
		tag(BlockTags.LEAVES).add(BlockRegistry.STARDUST_LEAVES.get());

		// Sand tag
		tag(BlockTags.SAND).add(BlockRegistry.BLOOD_SAND.get());

		// Saplings tag
		tag(BlockTags.SAPLINGS).add(BlockRegistry.STARDUST_SAPLING.get());

		// Walls tag
		tag(BlockTags.WALLS).add(
				BlockRegistry.CLOUD_MARBLE_BRICK_WALL.get(),
				BlockRegistry.BLOOD_SANDSTONE_WALL.get());

		// Beacon base tag
		tag(BlockTags.BEACON_BASE_BLOCKS)
				.add(BlockRegistry.COBALT_BLOCK.get())
				.add(BlockRegistry.MOLTEN_BLOCK.get())
				.add(BlockRegistry.TESLA_BLOCK.get())
				.add(BlockRegistry.ASTRAL_BLOCK.get())
				.add(BlockRegistry.STARSTORM_BLOCK.get());

		// Smelts-to-glass tag
		tag(BlockTags.SMELTS_TO_GLASS).add(BlockRegistry.BLOOD_SAND.get());

		// Incorrect for X tool tags
		tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
				.addTag(Blocks.NEEDS_NETHERITE_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
		tag(BlockTags.INCORRECT_FOR_STONE_TOOL)
				.addTag(Blocks.NEEDS_NETHERITE_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
		tag(BlockTags.INCORRECT_FOR_GOLD_TOOL)
				.addTag(Blocks.NEEDS_NETHERITE_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
		tag(BlockTags.INCORRECT_FOR_IRON_TOOL)
				.addTag(Blocks.NEEDS_NETHERITE_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
		tag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
				.addTag(Blocks.NEEDS_NETHERITE_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
		tag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
	}

	/**
	 * Add block tags for mining with tools
	 */
	private void addMiningBlockTags() {
		List<Block> blocks = new ArrayList<>(250);
		BlockRegistry.BLOCKS.getEntries().stream().map(Supplier::get).forEach(blocks::add);

		int tagStage = 0;
		int tier = 0;
		for (Block block : blocks) {
			if (block == BlockRegistry.SMALL_PARTS_TABLE.get()) {
				tagStage = 1;
			} else if (block == BlockRegistry.SANDBAG.get()) {
				tagStage = 2;
			} else if (block == BlockRegistry.STARDUST_LEAVES.get()) {
				tagStage = 3;
			} else if (block == BlockRegistry.BULLETPROOF_GLASS.get()) {
				tagStage = 4;
			}

			if (block == BlockRegistry.BULLETPROOF_GLASS.get()
					|| block == BlockRegistry.SMALL_PARTS_TABLE.get()
					|| block == BlockRegistry.SANDBAG.get()) {

				tier = 0;
			} else if (block == BlockRegistry.SPOTLIGHT.get()
					|| block == BlockRegistry.WOODEN_SPIKES.get()
					|| block == BlockRegistry.PUNJI_STICKS.get()) {

				tier = 1;
			} else if (block == BlockRegistry.BARBED_WIRE_FENCE.get()) {
				tier = 2;
			} else if (block == BlockRegistry.MOLTEN_ORE.get()) {
				tier = 3;
			} else if (block == BlockRegistry.ASTRAL_ORE.get()) {
				tier = 4;
			}

			if (tagStage != 4) {
				switch (tagStage) {
					case 1 -> tag(BlockTags.MINEABLE_WITH_AXE).add(block);
					case 2 -> tag(BlockTags.MINEABLE_WITH_SHOVEL).add(block);
					case 3 -> tag(BlockTags.MINEABLE_WITH_HOE).add(block);
					default -> tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
				}
			}

			if (tier != 0) {
				switch (tier) {
					case 2 -> tag(BlockTags.NEEDS_IRON_TOOL).add(block);
					case 3 -> tag(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
					case 4 -> tag(Blocks.NEEDS_NETHERITE_TOOL).add(block);
					default -> tag(BlockTags.NEEDS_STONE_TOOL).add(block);
				}
			}
		}
	}
}