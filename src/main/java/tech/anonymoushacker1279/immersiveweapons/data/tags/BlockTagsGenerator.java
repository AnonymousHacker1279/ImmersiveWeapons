package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.forge.ForgeBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.minecraft.MinecraftBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.lists.BlockTagLists;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

import java.util.ArrayList;
import java.util.List;

public class BlockTagsGenerator extends BlockTagsProvider {

	/**
	 * Constructor for BlockTagsGenerator.
	 *
	 * @param gen                the <code>DataGenerator</code> instance
	 * @param existingFileHelper the <code>ExistingFileHelper</code> instance
	 */
	public BlockTagsGenerator(DataGenerator gen, ExistingFileHelper existingFileHelper) {
		super(gen, ImmersiveWeapons.MOD_ID, existingFileHelper);
	}

	/**
	 * Add tags to data generation.
	 */
	@Override
	protected void addTags() {
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
		tag(ForgeBlockTagGroups.COBALT_ORES).add(DeferredRegistryHandler.COBALT_ORE.get())
				.add(DeferredRegistryHandler.DEEPSLATE_COBALT_ORE.get());
		tag(Blocks.ORES).addTag(ForgeBlockTagGroups.COBALT_ORES);
	}

	/**
	 * Add tags under the Immersive Weapons namespace
	 */
	private void addImmersiveWeaponsTags() {
		// Burned oak logs tag
		for (Block block : BlockTagLists.BURNED_OAK_LOGS) {
			tag(ImmersiveWeaponsBlockTagGroups.BURNED_OAK_LOGS).add(block);
		}

		// Stardust logs tag
		for (Block block : BlockTagLists.STARDUST_LOGS) {
			tag(ImmersiveWeaponsBlockTagGroups.STARDUST_LOGS).add(block);
		}
	}

	/**
	 * Add tags under the Minecraft namespace
	 */
	private void addMinecraftTags() {
		// Fence tag
		tag(MinecraftBlockTagGroups.FENCES).add(DeferredRegistryHandler.BARBED_WIRE_FENCE.get());

		// Burnable logs tag
		tag(MinecraftBlockTagGroups.LOGS_THAT_BURN).addTags(ImmersiveWeaponsBlockTagGroups.BURNED_OAK_LOGS,
				ImmersiveWeaponsBlockTagGroups.STARDUST_LOGS);

		// Planks tag
		tag(MinecraftBlockTagGroups.PLANKS).add(DeferredRegistryHandler.BURNED_OAK_PLANKS.get(),
				DeferredRegistryHandler.STARDUST_PLANKS.get());

		// Slabs tag
		tag(MinecraftBlockTagGroups.SLABS).add(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_SLAB.get());

		// Stairs tag
		tag(MinecraftBlockTagGroups.STAIRS).add(DeferredRegistryHandler.CLOUD_MARBLE_BRICK_STAIRS.get());

		// Standing signs tag
		tag(MinecraftBlockTagGroups.STANDING_SIGNS).add(DeferredRegistryHandler.BURNED_OAK_SIGN.get());

		// Wall signs tag
		tag(MinecraftBlockTagGroups.WALL_SIGNS).add(DeferredRegistryHandler.BURNED_OAK_SIGN.get());

		// Wooden buttons tag
		tag(MinecraftBlockTagGroups.WOODEN_BUTTONS).add(DeferredRegistryHandler.BURNED_OAK_BUTTON.get(),
				DeferredRegistryHandler.STARDUST_BUTTON.get());

		// Wooden doors tag
		tag(MinecraftBlockTagGroups.WOODEN_DOORS).add(DeferredRegistryHandler.BURNED_OAK_DOOR.get(),
				DeferredRegistryHandler.STARDUST_DOOR.get());

		// Wooden fences tag
		tag(MinecraftBlockTagGroups.WOODEN_FENCES).add(DeferredRegistryHandler.BURNED_OAK_FENCE.get(),
				DeferredRegistryHandler.STARDUST_FENCE.get());

		// Wooden pressure plates tag
		tag(MinecraftBlockTagGroups.WOODEN_PRESSURE_PLATES).add(DeferredRegistryHandler.BURNED_OAK_PRESSURE_PLATE.get(),
				DeferredRegistryHandler.STARDUST_PRESSURE_PLATE.get());

		// Wooden slabs tag
		tag(MinecraftBlockTagGroups.WOODEN_SLABS).add(DeferredRegistryHandler.BURNED_OAK_SLAB.get(),
				DeferredRegistryHandler.STARDUST_SLAB.get());

		// Wooden stairs tag
		tag(MinecraftBlockTagGroups.WOODEN_STAIRS).add(DeferredRegistryHandler.BURNED_OAK_STAIRS.get(),
				DeferredRegistryHandler.STARDUST_STAIRS.get());

		// Wooden trapdoors tag
		tag(MinecraftBlockTagGroups.WOODEN_TRAPDOORS).add(DeferredRegistryHandler.BURNED_OAK_TRAPDOOR.get(),
				DeferredRegistryHandler.STARDUST_TRAPDOOR.get());

		// Non-flammable wood tag
		tag(MinecraftBlockTagGroups.NON_FLAMMABLE_WOOD).add(DeferredRegistryHandler.WARPED_TABLE.get(),
				DeferredRegistryHandler.CRIMSON_TABLE.get());

		// Small flowers tag
		tag(MinecraftBlockTagGroups.SMALL_FLOWERS).add(DeferredRegistryHandler.AZUL_STAINED_ORCHID.get(),
				DeferredRegistryHandler.MOONGLOW.get());

		// Leaves tag
		tag(MinecraftBlockTagGroups.LEAVES).add(DeferredRegistryHandler.STARDUST_LEAVES.get());
	}

	/**
	 * Add block tags for mining with tools
	 */
	private void addMiningBlockTags() {
		List<Block> blocks = new ArrayList<>(250);
		DeferredRegistryHandler.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(blocks::add);

		int tagStage = 0;
		int tier = 0;
		for (Block block : blocks) {
			if (block == DeferredRegistryHandler.SMALL_PARTS_TABLE.get()) {
				tagStage = 1;
			} else if (block == DeferredRegistryHandler.SANDBAG.get()) {
				tagStage = 2;
			} else if (block == DeferredRegistryHandler.STARDUST_LEAVES.get()) {
				tagStage = 3;
			} else if (block == DeferredRegistryHandler.BULLETPROOF_GLASS.get()) {
				tagStage = 4;
			}

			if (block == DeferredRegistryHandler.BULLETPROOF_GLASS.get()
					|| block == DeferredRegistryHandler.SMALL_PARTS_TABLE.get()
					|| block == DeferredRegistryHandler.SANDBAG.get()) {

				tier = 0;
			} else if (block == DeferredRegistryHandler.SPOTLIGHT.get()
					|| block == DeferredRegistryHandler.WOODEN_SPIKES.get()
					|| block == DeferredRegistryHandler.PUNJI_STICKS.get()) {

				tier = 1;
			} else if (block == DeferredRegistryHandler.BARBED_WIRE_FENCE.get()) {
				tier = 2;
			} else if (block == DeferredRegistryHandler.MOLTEN_ORE.get()) {
				tier = 3;
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
					default -> tag(BlockTags.NEEDS_STONE_TOOL).add(block);
				}
			}
		}
	}
}