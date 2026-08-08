package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockTagsGenerator extends BlockTagsProvider {

	public static final TagKey<Block> SAPLINGS = createBlockTag("saplings");
	public static final TagKey<Block> LOGS_THAT_BURN = createBlockTag("logs_that_burn");
	public static final TagKey<Block> SMELTS_TO_GLASS = createBlockTag("smelts_to_glass");

	public BlockTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID);
	}

	private static TagKey<Block> createBlockTag(String tag) {
		return TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace(tag));
	}

	/// Add tags to data generation.
	@Override
	protected void addTags(Provider provider) {
		addIWTags();
		addCommonTags();
		addMinecraftTags();
		addToolTags();
		addMiningBlockTags();
	}

	@SuppressWarnings("unchecked")
	private void addIWTags() {
		tag(IWBlockTagGroups.BURNED_OAK_LOGS).add(
				BlockRegistry.BURNED_OAK_LOG.getKey(),
				BlockRegistry.BURNED_OAK_WOOD.getKey(),
				BlockRegistry.STRIPPED_BURNED_OAK_LOG.getKey(),
				BlockRegistry.STRIPPED_BURNED_OAK_WOOD.getKey());
		tag(IWBlockTagGroups.STARDUST_LOGS).add(
				BlockRegistry.STARDUST_LOG.getKey(),
				BlockRegistry.STARDUST_WOOD.getKey(),
				BlockRegistry.STRIPPED_STARDUST_LOG.getKey(),
				BlockRegistry.STRIPPED_STARDUST_WOOD.getKey());
		tag(IWBlockTagGroups.TESLA_ORES).add(
				BlockRegistry.DORMANT_TESLA_ORE.getKey(),
				BlockRegistry.ACTIVE_TESLA_ORE.getKey());
		tag(IWBlockTagGroups.MOLTEN_ORES).add(
				BlockRegistry.MOLTEN_ORE.getKey());
		tag(IWBlockTagGroups.VENTUS_ORES).add(
				BlockRegistry.VENTUS_ORE.getKey());
		tag(IWBlockTagGroups.ASTRAL_ORES).add(
				BlockRegistry.ASTRAL_ORE.getKey());
		tag(IWBlockTagGroups.VOID_ORES).add(
				BlockRegistry.VOID_ORE.getKey());
	}

	/// Add tags under the Forge namespace
	@SuppressWarnings("unchecked")
	private void addCommonTags() {
		// Bulletproof glass tag
		tag(CommonBlockTagGroups.BULLETPROOF_GLASS).add(
				BlockRegistry.BULLETPROOF_GLASS.getKey(),
				BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS.getKey(),
				BlockRegistry.RED_STAINED_BULLETPROOF_GLASS.getKey());
		tag(CommonBlockTagGroups.BULLETPROOF_GLASS_PANES).add(
				BlockRegistry.BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS_PANE.getKey(),
				BlockRegistry.RED_STAINED_BULLETPROOF_GLASS_PANE.getKey());

		// Ores
		tag(CommonBlockTagGroups.COBALT_ORES).add(
				BlockRegistry.COBALT_ORE.getKey(),
				BlockRegistry.DEEPSLATE_COBALT_ORE.getKey());
		tag(CommonBlockTagGroups.SULFUR_ORES).add(
				BlockRegistry.SULFUR_ORE.getKey(),
				BlockRegistry.DEEPSLATE_SULFUR_ORE.getKey(),
				BlockRegistry.NETHER_SULFUR_ORE.getKey());
		tag(CommonBlockTagGroups.POTASSIUM_NITRATE_ORES).add(
				BlockRegistry.POTASSIUM_NITRATE_ORE.getKey());


		tag(Tags.Blocks.ORES).addTags(
				CommonBlockTagGroups.COBALT_ORES,
				CommonBlockTagGroups.SULFUR_ORES,
				CommonBlockTagGroups.POTASSIUM_NITRATE_ORES,
				IWBlockTagGroups.MOLTEN_ORES,
				IWBlockTagGroups.TESLA_ORES,
				IWBlockTagGroups.VENTUS_ORES,
				IWBlockTagGroups.ASTRAL_ORES,
				IWBlockTagGroups.VOID_ORES);

		// Glass tag
		tag(Blocks.GLASS_BLOCKS).addTag(CommonBlockTagGroups.BULLETPROOF_GLASS);
		tag(Blocks.GLASS_BLOCKS_COLORLESS).add(BlockRegistry.BULLETPROOF_GLASS.getKey());
		tag(Blocks.GLASS_PANES).addTag(CommonBlockTagGroups.BULLETPROOF_GLASS_PANES);
		tag(Blocks.GLASS_PANES_COLORLESS).add(BlockRegistry.BULLETPROOF_GLASS_PANE.getKey());

		tag(Blocks.NATURAL_LOGS).add(
				BlockRegistry.BURNED_OAK_LOG.getKey(),
				BlockRegistry.STARDUST_LOG.getKey());
	}

	/// Add tags under the Minecraft namespace
	@SuppressWarnings("unchecked")
	private void addMinecraftTags() {
		tag(BlockTags.STANDING_SIGNS).add(
				BlockRegistry.BURNED_OAK_SIGN.getKey(),
				BlockRegistry.STARDUST_SIGN.getKey());

		tag(BlockTags.WALL_SIGNS).add(
				BlockRegistry.BURNED_OAK_SIGN.getKey(),
				BlockRegistry.STARDUST_SIGN.getKey());

		tag(BlockTags.TRIGGERS_AMBIENT_DESERT_SAND_BLOCK_SOUNDS).add(
				BlockRegistry.BLOOD_SAND.getKey());

		tag(BlockTags.BEACON_BASE_BLOCKS).add(
				BlockRegistry.COBALT_BLOCK.getKey(),
				BlockRegistry.MOLTEN_BLOCK.getKey(),
				BlockRegistry.TESLA_BLOCK.getKey(),
				BlockRegistry.ASTRAL_BLOCK.getKey(),
				BlockRegistry.STARSTORM_BLOCK.getKey());

		tag(BlockTags.FENCES).add(
				BlockRegistry.BARBED_WIRE_FENCE.getKey());

		tag(BlockTags.WOODEN_FENCES).add(
				BlockRegistry.BURNED_OAK_FENCE.getKey(),
				BlockRegistry.STARDUST_FENCE.getKey());

		tag(BlockTags.FENCE_GATES).add(
				BlockRegistry.BURNED_OAK_FENCE_GATE.getKey(),
				BlockRegistry.STARDUST_FENCE_GATE.getKey());

		tag(BlockTags.PLANKS).add(
				BlockRegistry.BURNED_OAK_PLANKS.getKey(),
				BlockRegistry.STARDUST_PLANKS.getKey());

		tag(BlockTags.SLABS).add(
				BlockRegistry.CLOUD_MARBLE_BRICK_SLAB.getKey(),
				BlockRegistry.BLOOD_SANDSTONE_SLAB.getKey(),
				BlockRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB.getKey());

		tag(BlockTags.WOODEN_SLABS).add(
				BlockRegistry.BURNED_OAK_SLAB.getKey(),
				BlockRegistry.STARDUST_SLAB.getKey());

		tag(BlockTags.STAIRS).add(
				BlockRegistry.CLOUD_MARBLE_BRICK_STAIRS.getKey(),
				BlockRegistry.BLOOD_SANDSTONE_STAIRS.getKey(),
				BlockRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS.getKey());

		tag(BlockTags.WOODEN_STAIRS).add(
				BlockRegistry.BURNED_OAK_STAIRS.getKey(),
				BlockRegistry.STARDUST_STAIRS.getKey());

		tag(BlockTags.WOODEN_BUTTONS).add(
				BlockRegistry.BURNED_OAK_BUTTON.getKey(),
				BlockRegistry.STARDUST_BUTTON.getKey());

		tag(BlockTags.WOODEN_DOORS).add(
				BlockRegistry.BURNED_OAK_DOOR.getKey(),
				BlockRegistry.STARDUST_DOOR.getKey());

		tag(BlockTags.WOODEN_TRAPDOORS).add(
				BlockRegistry.BURNED_OAK_TRAPDOOR.getKey(),
				BlockRegistry.STARDUST_TRAPDOOR.getKey());

		tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
				BlockRegistry.BURNED_OAK_PRESSURE_PLATE.getKey(),
				BlockRegistry.STARDUST_PRESSURE_PLATE.getKey());

		tag(BlockTags.SMALL_FLOWERS).add(
				BlockRegistry.MOONGLOW.getKey(),
				BlockRegistry.DEATHWEED.getKey());

		tag(BlockTags.LEAVES).add(
				BlockRegistry.STARDUST_LEAVES.getKey());

		tag(BlockTags.SAND).add(
				BlockRegistry.BLOOD_SAND.getKey());

		tag(BlockTags.WALLS).add(
				BlockRegistry.CLOUD_MARBLE_BRICK_WALL.getKey(),
				BlockRegistry.BLOOD_SANDSTONE_WALL.getKey());

		tag(BlockTags.LOGS).add(
				BlockRegistry.BURNED_OAK_LOG.getKey(),
				BlockRegistry.STARDUST_LOG.getKey());

		tag(LOGS_THAT_BURN).add(
				BlockRegistry.BURNED_OAK_LOG.getKey(),
				BlockRegistry.STARDUST_LOG.getKey());

		tag(SMELTS_TO_GLASS).add(
				BlockRegistry.BLOOD_SAND.getKey());
	}

	private void addToolTags() {
		tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(BlockTags.INCORRECT_FOR_STONE_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(BlockTags.INCORRECT_FOR_GOLD_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(BlockTags.INCORRECT_FOR_COPPER_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(BlockTags.INCORRECT_FOR_IRON_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_COBALT_TOOL)
				.addTag(BlockTags.NEEDS_DIAMOND_TOOL)
				.addTag(Blocks.NEEDS_NETHERITE_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(BlockTags.INCORRECT_FOR_NETHERITE_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_MOLTEN_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_TESLA_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_VENTUS_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_STARSTORM_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_ASTRAL_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_VOID_TOOL)
				.addTag(IWBlockTagGroups.NEEDS_HANSIUM_TOOL);
		tag(IWBlockTagGroups.INCORRECT_FOR_HANSIUM_TOOL);

		tag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL);
		tag(IWBlockTagGroups.NEEDS_VOID_TOOL);
		tag(IWBlockTagGroups.NEEDS_HANSIUM_TOOL);

		tag(SAPLINGS).add(
				BlockRegistry.STARDUST_SAPLING.getKey());
	}

	/// Add block tags for mining with tools
	private void addMiningBlockTags() {
		List<ResourceKey<Block>> blocks = new ArrayList<>(250);
		BlockRegistry.BLOCKS.getEntries()
				.stream()
				.map(DeferredHolder::getKey)
				.forEach(blocks::add);

		int tagStage = 0;
		int tier = 0;
		for (ResourceKey<Block> block : blocks) {
			if (block == BlockRegistry.SMALL_PARTS_TABLE.getKey()) {
				tagStage = 1;
			} else if (block == BlockRegistry.SANDBAG.getKey()) {
				tagStage = 2;
			} else if (block == BlockRegistry.STARDUST_LEAVES.getKey()) {
				tagStage = 3;
			} else if (block == BlockRegistry.BULLETPROOF_GLASS.getKey()) {
				tagStage = 4;
			}

			if (block == BlockRegistry.BULLETPROOF_GLASS.getKey()
					|| block == BlockRegistry.SMALL_PARTS_TABLE.getKey()
					|| block == BlockRegistry.SANDBAG.getKey()) {

				tier = 0;
			} else if (block == BlockRegistry.SPOTLIGHT.getKey()
					|| block == BlockRegistry.WOODEN_SPIKES.getKey()
					|| block == BlockRegistry.PUNJI_STICKS.getKey()) {

				tier = 1;
			} else if (block == BlockRegistry.BARBED_WIRE_FENCE.getKey()) {
				tier = 2;
			} else if (block == BlockRegistry.MOLTEN_ORE.getKey()) {
				tier = 3;
			} else if (block == BlockRegistry.ASTRAL_ORE.getKey()) {
				tier = 4;
			} else if (block == BlockRegistry.VOID_ORE.getKey()) {
				tier = 5;
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
					case 5 -> tag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL).add(block);
					default -> tag(BlockTags.NEEDS_STONE_TOOL).add(block);
				}
			}
		}
	}
}