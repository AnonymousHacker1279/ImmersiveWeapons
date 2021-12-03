package com.anonymoushacker1279.immersiveweapons.data.tags;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class TagsGenerator extends BlockTagsProvider {

	/**
	 * Constructor for TagGenerator.
	 *
	 * @param gen                the <code>DataGenerator</code> instance
	 * @param existingFileHelper the <code>ExistingFileHelper</code> instance
	 */
	public TagsGenerator(DataGenerator gen, ExistingFileHelper existingFileHelper) {
		super(gen, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	/**
	 * Add tags to data generation.
	 */
	@Override
	protected void addTags() {
		List<Block> blocks = new ArrayList<>(1);
		DeferredRegistryHandler.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(blocks::add);

		// Handle adding block tags that involve mining with tools.
		int tagStage = 0;
		int tier = 0;
		for (Block block : blocks) {
			if (block == DeferredRegistryHandler.SMALL_PARTS_TABLE.get()) {
				tagStage = 1;
			} else if (block == DeferredRegistryHandler.SANDBAG.get()) {
				tagStage = 2;
			} else if (block == DeferredRegistryHandler.BULLETPROOF_GLASS.get()) {
				tagStage = 3;
			}

			if (block == DeferredRegistryHandler.BULLETPROOF_GLASS.get() || block == DeferredRegistryHandler.SMALL_PARTS_TABLE.get() || block == DeferredRegistryHandler.SANDBAG.get()) {
				tier = 0;
			} else if (block == DeferredRegistryHandler.SPOTLIGHT.get() || block == DeferredRegistryHandler.WOODEN_SPIKES.get() || block == DeferredRegistryHandler.PUNJI_STICKS.get()) {
				tier = 1;
			} else if (block == DeferredRegistryHandler.BARBED_WIRE_FENCE.get()) {
				tier = 2;
			} else if (block == DeferredRegistryHandler.MOLTEN_ORE.get()) {
				tier = 3;
			}

			if (tagStage != 3) {
				switch (tagStage) {
					case 1 -> tag(BlockTags.bind(BlockTags.MINEABLE_WITH_AXE.getName().toString())).add(block);
					case 2 -> tag(BlockTags.bind(BlockTags.MINEABLE_WITH_SHOVEL.getName().toString())).add(block);
					default -> tag(BlockTags.bind(BlockTags.MINEABLE_WITH_PICKAXE.getName().toString())).add(block);
				}
			}

			if (tier != 0) {
				switch (tier) {
					case 2 -> tag(BlockTags.bind(BlockTags.NEEDS_IRON_TOOL.getName().toString())).add(block);
					case 3 -> tag(BlockTags.bind(BlockTags.NEEDS_DIAMOND_TOOL.getName().toString())).add(block);
					default -> tag(BlockTags.bind(BlockTags.NEEDS_STONE_TOOL.getName().toString())).add(block);
				}
			}
		}
	}
}