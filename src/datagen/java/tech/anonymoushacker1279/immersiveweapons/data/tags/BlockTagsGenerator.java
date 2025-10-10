package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class BlockTagsGenerator extends BlockTagsProvider {

	public BlockTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID);
	}

	/**
	 * Add tags to data generation.
	 */
	@Override
	protected void addTags(Provider provider) {
		addCommonTags();
		addMinecraftTags();
		addToolTags();
		addMiningBlockTags();

		new BlockItemTagsGenerator() {
			@Override
			protected TagAppender<Block, Block> tag(TagKey<Block> blockTagKey, TagKey<Item> itemTagKey) {
				return BlockTagsGenerator.this.tag(blockTagKey);
			}
		}.run();
	}

	/**
	 * Add tags under the Forge namespace
	 */
	private void addCommonTags() {
		// Bulletproof glass tag
		tag(CommonBlockTagGroups.BULLETPROOF_GLASS).add(
				BlockRegistry.BULLETPROOF_GLASS.get(),
				BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS.get(),
				BlockRegistry.RED_STAINED_BULLETPROOF_GLASS.get());
		tag(CommonBlockTagGroups.BULLETPROOF_GLASS_PANES).add(
				BlockRegistry.BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.WHITE_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.LIGHT_GRAY_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.GRAY_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.BLACK_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.ORANGE_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.MAGENTA_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.LIGHT_BLUE_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.YELLOW_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.LIME_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.PINK_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.CYAN_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.PURPLE_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.BLUE_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.BROWN_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.GREEN_STAINED_BULLETPROOF_GLASS_PANE.get(),
				BlockRegistry.RED_STAINED_BULLETPROOF_GLASS_PANE.get());

		// Glass tag
		tag(Blocks.GLASS_BLOCKS).addTag(CommonBlockTagGroups.BULLETPROOF_GLASS);
		tag(Blocks.GLASS_BLOCKS_COLORLESS).add(BlockRegistry.BULLETPROOF_GLASS.get());
		tag(Blocks.GLASS_PANES).addTag(CommonBlockTagGroups.BULLETPROOF_GLASS_PANES);
		tag(Blocks.GLASS_PANES_COLORLESS).add(BlockRegistry.BULLETPROOF_GLASS_PANE.get());
	}

	/**
	 * Add tags under the Minecraft namespace
	 */
	private void addMinecraftTags() {
		tag(BlockTags.STANDING_SIGNS).add(BlockRegistry.BURNED_OAK_SIGN.get(),
				BlockRegistry.STARDUST_SIGN.get());

		tag(BlockTags.WALL_SIGNS).add(BlockRegistry.BURNED_OAK_SIGN.get(),
				BlockRegistry.STARDUST_SIGN.get());

		tag(BlockTags.TRIGGERS_AMBIENT_DESERT_SAND_BLOCK_SOUNDS).add(BlockRegistry.BLOOD_SAND.get());

		// Beacon base tag
		tag(BlockTags.BEACON_BASE_BLOCKS)
				.add(BlockRegistry.COBALT_BLOCK.get())
				.add(BlockRegistry.MOLTEN_BLOCK.get())
				.add(BlockRegistry.TESLA_BLOCK.get())
				.add(BlockRegistry.ASTRAL_BLOCK.get())
				.add(BlockRegistry.STARSTORM_BLOCK.get());
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
			} else if (block == BlockRegistry.VOID_ORE.get()) {
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