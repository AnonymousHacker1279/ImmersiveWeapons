package com.anonymoushacker1279.immersiveweapons.data.tags;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fmllegacy.RegistryObject;

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
		for (Block block : blocks) {
			if (block == DeferredRegistryHandler.SMALL_PARTS_TABLE.get()) {
				tagStage = 1;
			} else if (block == DeferredRegistryHandler.PUNJI_STICKS.get()) {
				tagStage = 2;
			} else if (block == DeferredRegistryHandler.BULLETPROOF_GLASS.get()) {
				tagStage = -1;
			}

			switch (tagStage) {
				case 0 -> tag(BlockTags.bind(BlockTags.MINEABLE_WITH_PICKAXE.getName().toString())).add(block);
				case 1 -> tag(BlockTags.bind(BlockTags.MINEABLE_WITH_AXE.getName().toString())).add(block);
				case 2 -> tag(BlockTags.bind(BlockTags.MINEABLE_WITH_SHOVEL.getName().toString())).add(block);
			}
		}
	}
}