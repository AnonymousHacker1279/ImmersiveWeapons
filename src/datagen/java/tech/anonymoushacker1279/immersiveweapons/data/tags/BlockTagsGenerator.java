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
				BlockRegistry.BURNED_OAK_LOG.key(),
				BlockRegistry.BURNED_OAK_WOOD.key(),
				BlockRegistry.STRIPPED_BURNED_OAK_LOG.key(),
				BlockRegistry.STRIPPED_BURNED_OAK_WOOD.key());
		tag(IWBlockTagGroups.STARDUST_LOGS).add(
				BlockRegistry.STARDUST_LOG.key(),
				BlockRegistry.STARDUST_WOOD.key(),
				BlockRegistry.STRIPPED_STARDUST_LOG.key(),
				BlockRegistry.STRIPPED_STARDUST_WOOD.key());
		tag(IWBlockTagGroups.TESLA_ORES).add(
				BlockRegistry.DORMANT_TESLA_ORE.key(),
				BlockRegistry.ACTIVE_TESLA_ORE.key());
		tag(IWBlockTagGroups.MOLTEN_ORES).add(
				BlockRegistry.MOLTEN_ORE.key());
		tag(IWBlockTagGroups.VENTUS_ORES).add(
				BlockRegistry.VENTUS_ORE.key());
		tag(IWBlockTagGroups.ASTRAL_ORES).add(
				BlockRegistry.ASTRAL_ORE.key());
		tag(IWBlockTagGroups.VOID_ORES).add(
				BlockRegistry.VOID_ORE.key());
	}

	/// Add tags under the Forge namespace
	@SuppressWarnings("unchecked")
	private void addCommonTags() {
		// Bulletproof glass tag
		tag(CommonBlockTagGroups.BULLETPROOF_GLASS).add(
				BlockRegistry.BULLETPROOF_GLASS.key(),
				BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS.key(),
				BlockRegistry.RED_STAINED_BULLETPROOF_GLASS.key());
		tag(CommonBlockTagGroups.BULLETPROOF_GLASS_PANES).add(
				BlockRegistry.BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS_PANE.key(),
				BlockRegistry.RED_STAINED_BULLETPROOF_GLASS_PANE.key());

		// Ores
		tag(CommonBlockTagGroups.COBALT_ORES).add(
				BlockRegistry.COBALT_ORE.key(),
				BlockRegistry.DEEPSLATE_COBALT_ORE.key());


		tag(Tags.Blocks.ORES).addTags(
				CommonBlockTagGroups.COBALT_ORES,
				IWBlockTagGroups.MOLTEN_ORES,
				IWBlockTagGroups.TESLA_ORES,
				IWBlockTagGroups.VENTUS_ORES,
				IWBlockTagGroups.ASTRAL_ORES,
				IWBlockTagGroups.VOID_ORES);

		// Glass tag
		tag(Blocks.GLASS_BLOCKS).addTag(CommonBlockTagGroups.BULLETPROOF_GLASS);
		tag(Blocks.GLASS_BLOCKS_COLORLESS).add(BlockRegistry.BULLETPROOF_GLASS.key());
		tag(Blocks.GLASS_PANES).addTag(CommonBlockTagGroups.BULLETPROOF_GLASS_PANES);
		tag(Blocks.GLASS_PANES_COLORLESS).add(BlockRegistry.BULLETPROOF_GLASS_PANE.key());

		tag(Blocks.NATURAL_LOGS).add(
				BlockRegistry.BURNED_OAK_LOG.key(),
				BlockRegistry.STARDUST_LOG.key());
	}

	/// Add tags under the Minecraft namespace
	@SuppressWarnings("unchecked")
	private void addMinecraftTags() {
		tag(BlockTags.STANDING_SIGNS).add(
				BlockRegistry.BURNED_OAK_SIGN.key(),
				BlockRegistry.STARDUST_SIGN.key());

		tag(BlockTags.WALL_SIGNS).add(
				BlockRegistry.BURNED_OAK_SIGN.key(),
				BlockRegistry.STARDUST_SIGN.key());

		tag(BlockTags.TRIGGERS_AMBIENT_DESERT_SAND_BLOCK_SOUNDS).add(
				BlockRegistry.BLOOD_SAND.key());

		tag(BlockTags.BEACON_BASE_BLOCKS).add(
				BlockRegistry.COBALT_BLOCK.key(),
				BlockRegistry.MOLTEN_BLOCK.key(),
				BlockRegistry.TESLA_BLOCK.key(),
				BlockRegistry.ASTRAL_BLOCK.key(),
				BlockRegistry.STARSTORM_BLOCK.key());

		tag(BlockTags.FENCES).add(
				BlockRegistry.BARBED_WIRE_FENCE.key());

		tag(BlockTags.WOODEN_FENCES).add(
				BlockRegistry.BURNED_OAK_FENCE.key(),
				BlockRegistry.STARDUST_FENCE.key());

		tag(BlockTags.FENCE_GATES).add(
				BlockRegistry.BURNED_OAK_FENCE_GATE.key(),
				BlockRegistry.STARDUST_FENCE_GATE.key());

		tag(BlockTags.PLANKS).add(
				BlockRegistry.BURNED_OAK_PLANKS.key(),
				BlockRegistry.STARDUST_PLANKS.key());

		tag(BlockTags.SLABS).add(
				BlockRegistry.CLOUD_MARBLE_BRICK_SLAB.key(),
				BlockRegistry.BLOOD_SANDSTONE_SLAB.key(),
				BlockRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB.key());

		tag(BlockTags.WOODEN_SLABS).add(
				BlockRegistry.BURNED_OAK_SLAB.key(),
				BlockRegistry.STARDUST_SLAB.key());

		tag(BlockTags.STAIRS).add(
				BlockRegistry.CLOUD_MARBLE_BRICK_STAIRS.key(),
				BlockRegistry.BLOOD_SANDSTONE_STAIRS.key(),
				BlockRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS.key());

		tag(BlockTags.WOODEN_STAIRS).add(
				BlockRegistry.BURNED_OAK_STAIRS.key(),
				BlockRegistry.STARDUST_STAIRS.key());

		tag(BlockTags.WOODEN_BUTTONS).add(
				BlockRegistry.BURNED_OAK_BUTTON.key(),
				BlockRegistry.STARDUST_BUTTON.key());

		tag(BlockTags.WOODEN_DOORS).add(
				BlockRegistry.BURNED_OAK_DOOR.key(),
				BlockRegistry.STARDUST_DOOR.key());

		tag(BlockTags.WOODEN_TRAPDOORS).add(
				BlockRegistry.BURNED_OAK_TRAPDOOR.key(),
				BlockRegistry.STARDUST_TRAPDOOR.key());

		tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
				BlockRegistry.BURNED_OAK_PRESSURE_PLATE.key(),
				BlockRegistry.STARDUST_PRESSURE_PLATE.key());

		tag(BlockTags.SMALL_FLOWERS).add(
				BlockRegistry.MOONGLOW.key(),
				BlockRegistry.DEATHWEED.key());

		tag(BlockTags.LEAVES).add(
				BlockRegistry.STARDUST_LEAVES.key());

		tag(BlockTags.SAND).add(
				BlockRegistry.BLOOD_SAND.key());

		tag(BlockTags.WALLS).add(
				BlockRegistry.CLOUD_MARBLE_BRICK_WALL.key(),
				BlockRegistry.BLOOD_SANDSTONE_WALL.key());

		tag(BlockTags.LOGS).addTags(
				IWBlockTagGroups.BURNED_OAK_LOGS,
				IWBlockTagGroups.STARDUST_LOGS);

		tag(LOGS_THAT_BURN).addTags(
				IWBlockTagGroups.BURNED_OAK_LOGS,
				IWBlockTagGroups.STARDUST_LOGS);

		tag(SMELTS_TO_GLASS).add(
				BlockRegistry.BLOOD_SAND.key());
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
				BlockRegistry.STARDUST_SAPLING.key());
	}

	/// Add block tags for mining with tools
	private void addMiningBlockTags() {
		List<ResourceKey<Block>> blocks = new ArrayList<>(250);
		BlockRegistry.BLOCKS.getEntries()
				.stream()
				.map(DeferredHolder::key)
				.forEach(blocks::add);

		int tagStage = 0;
		int tier = 0;
		for (ResourceKey<Block> block : blocks) {
			if (block == BlockRegistry.SMALL_PARTS_TABLE.key()) {
				tagStage = 1;
			} else if (block == BlockRegistry.SANDBAG.key()) {
				tagStage = 2;
			} else if (block == BlockRegistry.STARDUST_LEAVES.key()) {
				tagStage = 3;
			} else if (block == BlockRegistry.BULLETPROOF_GLASS.key()) {
				tagStage = 4;
			}

			if (block == BlockRegistry.BULLETPROOF_GLASS.key()
					|| block == BlockRegistry.SMALL_PARTS_TABLE.key()
					|| block == BlockRegistry.SANDBAG.key()
					|| block == BlockRegistry.STARDUST_LEAVES.key()) {

				tier = 0;
			} else if (block == BlockRegistry.SPOTLIGHT.key()
					|| block == BlockRegistry.WOODEN_SPIKES.key()
					|| block == BlockRegistry.PUNJI_STICKS.key()) {

				tier = 1;
			} else if (block == BlockRegistry.BARBED_WIRE_FENCE.key()) {
				tier = 2;
			} else if (block == BlockRegistry.MOLTEN_ORE.key()) {
				tier = 3;
			} else if (block == BlockRegistry.ASTRAL_ORE.key()) {
				tier = 4;
			} else if (block == BlockRegistry.VOID_ORE.key()) {
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
					case 1 -> tag(BlockTags.NEEDS_STONE_TOOL).add(block);
					case 2 -> tag(BlockTags.NEEDS_IRON_TOOL).add(block);
					case 3 -> tag(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
					case 4 -> tag(Blocks.NEEDS_NETHERITE_TOOL).add(block);
					case 5 -> tag(IWBlockTagGroups.NEEDS_ASTRAL_STARSTORM_TOOL).add(block);
				}
			}
		}
	}
}