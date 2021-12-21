package com.anonymoushacker1279.immersiveweapons.data.tags;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.data.models.lists.BlockTagLists;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag.Named;
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
		BlockTagLists.init();

		addForgeTags();
		addImmersiveWeaponsTags();
		addMinecraftTags();
		addMiningBlockTags();
	}

	/**
	 * Add tags under the Forge namespace
	 */
	private void addForgeTags() {
		// Bulletproof glass tag
		Named<Block> bulletproofGlass = BlockTags.bind("forge:bulletproof_glass");
		for (Block block : BlockTagLists.BULLETPROOF_GLASS) {
			tag(bulletproofGlass).add(block);
		}

		// Glass tag
		tag(BlockTags.bind("forge:glass")).addTag(bulletproofGlass);

		// Stained glass tag
		Named<Block> stainedGlass = BlockTags.bind("forge:stained_glass");
		for (Block block : BlockTagLists.STAINED_GLASS) {
			tag(stainedGlass).add(block);
		}

		// Ore tags
		Named<Block> cobaltOre = BlockTags.bind("forge:ores/cobalt");
		tag(cobaltOre).add(DeferredRegistryHandler.COBALT_ORE.get());

		tag(BlockTags.bind("forge:ores")).addTag(cobaltOre);
	}

	/**
	 * Add tags under the Immersive Weapons namespace
	 */
	private void addImmersiveWeaponsTags() {
		// Burned oak logs tag
		Named<Block> burnedOakLogs = BlockTags.bind("immersiveweapons:burned_oak_logs");
		for (Block block : BlockTagLists.BURNED_OAK_LOGS) {
			tag(burnedOakLogs).add(block);
		}
	}

	/**
	 * Add tags under the Minecraft namespace
	 */
	private void addMinecraftTags() {
		// Fence tag
		tag(BlockTags.bind("minecraft:fences")).add(DeferredRegistryHandler.BARBED_WIRE_FENCE.get());

		// Burnable logs tag
		tag(BlockTags.bind("minecraft:logs_that_burn")).addTag(BlockTags.bind("immersiveweapons:burned_oak_logs"));

		// Planks tag
		tag(BlockTags.bind("minecraft:planks")).add(DeferredRegistryHandler.BURNED_OAK_PLANKS.get());

		// Slabs tag
		tag(BlockTags.bind("minecraft:slabs")).add(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_SLAB.get());

		// Stairs tag
		tag(BlockTags.bind("minecraft:stairs")).add(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_STAIRS.get());

		// Standing signs tag
		tag(BlockTags.bind("minecraft:standing_signs")).add(DeferredRegistryHandler.BURNED_OAK_SIGN.get());

		// Wall signs tag
		tag(BlockTags.bind("minecraft:wall_signs")).add(DeferredRegistryHandler.BURNED_OAK_SIGN.get());

		// Wooden buttons tag
		tag(BlockTags.bind("minecraft:wooden_buttons")).add(DeferredRegistryHandler.BURNED_OAK_BUTTON.get());

		// Wooden doors tag
		tag(BlockTags.bind("minecraft:wooden_doors")).add(DeferredRegistryHandler.BURNED_OAK_DOOR.get());

		// Wooden fences tag
		tag(BlockTags.bind("minecraft:wooden_fences")).add(DeferredRegistryHandler.BURNED_OAK_FENCE.get());

		// Wooden pressure plates tag
		tag(BlockTags.bind("minecraft:wooden_pressure_plates")).add(DeferredRegistryHandler.BURNED_OAK_PRESSURE_PLATE.get());

		// Wooden slabs tag
		tag(BlockTags.bind("minecraft:wooden_slabs")).add(DeferredRegistryHandler.BURNED_OAK_SLAB.get());

		// Wooden stairs tag
		tag(BlockTags.bind("minecraft:wooden_stairs")).add(DeferredRegistryHandler.BURNED_OAK_STAIRS.get());

		// Wooden trapdoors tag
		tag(BlockTags.bind("minecraft:wooden_trapdoors")).add(DeferredRegistryHandler.BURNED_OAK_TRAPDOOR.get());
	}

	/**
	 * Add block tags for mining with tools
	 */
	private void addMiningBlockTags() {
		List<Block> blocks = new ArrayList<>(1);
		DeferredRegistryHandler.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(blocks::add);

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