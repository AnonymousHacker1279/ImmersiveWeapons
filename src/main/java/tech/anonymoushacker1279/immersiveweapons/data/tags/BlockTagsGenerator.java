package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.lists.BlockTagLists;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BlockTagsGenerator extends BlockTagsProvider {

	public BlockTagsGenerator(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	/**
	 * Add tags to data generation.
	 */
	@Override
	protected void addTags(Provider provider) {
		addForgeTags();
		addImmersiveWeaponsTags();
		addMinecraftTags();
		addMiningBlockTags();
	}

	/**
	 * Add tags under the Forge namespace
	 */
	@SuppressWarnings("unchecked")
	private void addForgeTags() {
		// Bulletproof glass tag
		for (Block block : BlockTagLists.BULLETPROOF_GLASS) {
			tag(ForgeBlockTagGroups.BULLETPROOF_GLASS).add(block);
		}

		// Glass tag
		tag(Blocks.GLASS).addTag(ForgeBlockTagGroups.BULLETPROOF_GLASS);

		// Stained glass tag
		for (Block block : BlockTagLists.STAINED_GLASS) {
			tag(ForgeBlockTagGroups.STAINED_GLASS).add(block);
		}

		// Ore tags
		tag(ForgeBlockTagGroups.COBALT_ORES).add(
				BlockRegistry.COBALT_ORE.get(),
				BlockRegistry.DEEPSLATE_COBALT_ORE.get());
		tag(ForgeBlockTagGroups.SULFUR_ORES).add(
				BlockRegistry.SULFUR_ORE.get(),
				BlockRegistry.DEEPSLATE_SULFUR_ORE.get(),
				BlockRegistry.NETHER_SULFUR_ORE.get());
		tag(Blocks.ORES).addTags(
				ForgeBlockTagGroups.COBALT_ORES,
				ForgeBlockTagGroups.SULFUR_ORES);
	}

	/**
	 * Add tags under the Immersive Weapons namespace
	 */
	private void addImmersiveWeaponsTags() {
		// Burned oak logs tag
		for (Block block : BlockTagLists.BURNED_OAK_LOGS) {
			tag(IWBlockTagGroups.BURNED_OAK_LOGS).add(block);
		}

		// Stardust logs tag
		for (Block block : BlockTagLists.STARDUST_LOGS) {
			tag(IWBlockTagGroups.STARDUST_LOGS).add(block);
		}
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
		tag(BlockTags.SMALL_FLOWERS).add(
				BlockRegistry.AZUL_STAINED_ORCHID.get(),
				BlockRegistry.MOONGLOW.get());

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
	}

	/**
	 * Add block tags for mining with tools
	 */
	private void addMiningBlockTags() {
		List<Block> blocks = new ArrayList<>(250);
		BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(blocks::add);

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