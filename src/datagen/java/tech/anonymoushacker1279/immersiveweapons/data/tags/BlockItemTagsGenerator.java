package tech.anonymoushacker1279.immersiveweapons.data.tags;

import net.minecraft.data.tags.BlockItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;

public abstract class BlockItemTagsGenerator extends BlockItemTagsProvider {

	@Override
	@SuppressWarnings("unchecked")
	protected void run() {
		tag(IWBlockTagGroups.BURNED_OAK_LOGS, IWItemTagGroups.BURNED_OAK_LOGS)
				.add(BlockRegistry.BURNED_OAK_LOG.get(),
						BlockRegistry.BURNED_OAK_WOOD.get(),
						BlockRegistry.STRIPPED_BURNED_OAK_LOG.get(),
						BlockRegistry.STRIPPED_BURNED_OAK_WOOD.get());
		tag(IWBlockTagGroups.STARDUST_LOGS, IWItemTagGroups.STARDUST_LOGS)
				.add(BlockRegistry.STARDUST_LOG.get(),
						BlockRegistry.STARDUST_WOOD.get(),
						BlockRegistry.STRIPPED_STARDUST_LOG.get(),
						BlockRegistry.STRIPPED_STARDUST_WOOD.get());
		tag(IWBlockTagGroups.ELECTRIC_ORES, IWItemTagGroups.ELECTRIC_ORES)
				.add(BlockRegistry.ELECTRIC_ORE.get());
		tag(IWBlockTagGroups.MOLTEN_ORES, IWItemTagGroups.MOLTEN_ORES)
				.add(BlockRegistry.MOLTEN_ORE.get());
		tag(IWBlockTagGroups.VENTUS_ORES, IWItemTagGroups.VENTUS_ORES)
				.add(BlockRegistry.VENTUS_ORE.get());
		tag(IWBlockTagGroups.ASTRAL_ORES, IWItemTagGroups.ASTRAL_ORES)
				.add(BlockRegistry.ASTRAL_ORE.get());
		tag(IWBlockTagGroups.VOID_ORES, IWItemTagGroups.VOID_ORES)
				.add(BlockRegistry.VOID_ORE.get());
		tag(CommonBlockTagGroups.COBALT_ORES, CommonItemTagGroups.COBALT_ORES)
				.add(BlockRegistry.COBALT_ORE.get(),
						BlockRegistry.DEEPSLATE_COBALT_ORE.get());
		tag(CommonBlockTagGroups.SULFUR_ORES, CommonItemTagGroups.SULFUR_ORES)
				.add(BlockRegistry.SULFUR_ORE.get(),
						BlockRegistry.DEEPSLATE_SULFUR_ORE.get(),
						BlockRegistry.NETHER_SULFUR_ORE.get());
		tag(CommonBlockTagGroups.POTASSIUM_NITRATE_ORES, CommonItemTagGroups.POTASSIUM_NITRATE_ORES)
				.add(BlockRegistry.POTASSIUM_NITRATE_ORE.get());
		tag(Tags.Blocks.ORES, Tags.Items.ORES).addTags(
				CommonBlockTagGroups.COBALT_ORES,
				CommonBlockTagGroups.SULFUR_ORES,
				CommonBlockTagGroups.POTASSIUM_NITRATE_ORES,
				IWBlockTagGroups.ELECTRIC_ORES,
				IWBlockTagGroups.MOLTEN_ORES,
				IWBlockTagGroups.VENTUS_ORES,
				IWBlockTagGroups.ASTRAL_ORES,
				IWBlockTagGroups.VOID_ORES);
		tag(BlockTags.FENCES, ItemTags.FENCES)
				.add(BlockRegistry.BARBED_WIRE_FENCE.get());
		tag(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN)
				.addTags(IWBlockTagGroups.BURNED_OAK_LOGS, IWBlockTagGroups.STARDUST_LOGS);
		tag(BlockTags.PLANKS, ItemTags.PLANKS)
				.add(BlockRegistry.BURNED_OAK_PLANKS.get(),
						BlockRegistry.STARDUST_PLANKS.get());
		tag(BlockTags.SLABS, ItemTags.SLABS)
				.add(BlockRegistry.CLOUD_MARBLE_BRICK_SLAB.get(),
						BlockRegistry.BLOOD_SANDSTONE_SLAB.get(),
						BlockRegistry.SMOOTH_BLOOD_SANDSTONE_SLAB.get());
		tag(BlockTags.STAIRS, ItemTags.STAIRS)
				.add(BlockRegistry.CLOUD_MARBLE_BRICK_STAIRS.get(),
						BlockRegistry.BLOOD_SANDSTONE_STAIRS.get(),
						BlockRegistry.SMOOTH_BLOOD_SANDSTONE_STAIRS.get());
		tag(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS)
				.add(BlockRegistry.BURNED_OAK_BUTTON.get(),
						BlockRegistry.STARDUST_BUTTON.get());
		tag(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS)
				.add(BlockRegistry.BURNED_OAK_DOOR.get(),
						BlockRegistry.STARDUST_DOOR.get());
		tag(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES)
				.add(BlockRegistry.BURNED_OAK_FENCE.get(),
						BlockRegistry.STARDUST_FENCE.get());
		tag(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES)
				.add(BlockRegistry.BURNED_OAK_PRESSURE_PLATE.get(),
						BlockRegistry.STARDUST_PRESSURE_PLATE.get());
		tag(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS)
				.add(BlockRegistry.BURNED_OAK_SLAB.get(),
						BlockRegistry.STARDUST_SLAB.get());
		tag(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS)
				.add(BlockRegistry.BURNED_OAK_STAIRS.get(),
						BlockRegistry.STARDUST_STAIRS.get());
		tag(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS)
				.add(BlockRegistry.BURNED_OAK_TRAPDOOR.get(),
						BlockRegistry.STARDUST_TRAPDOOR.get());
		tag(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS)
				.add(BlockRegistry.MOONGLOW.get(),
						BlockRegistry.DEATHWEED.get());
		tag(BlockTags.LEAVES, ItemTags.LEAVES)
				.add(BlockRegistry.STARDUST_LEAVES.get());
		tag(BlockTags.SAND, ItemTags.SAND)
				.add(BlockRegistry.BLOOD_SAND.get());
		tag(BlockTags.SAPLINGS, ItemTags.SAPLINGS)
				.add(BlockRegistry.STARDUST_SAPLING.get());
		tag(BlockTags.WALLS, ItemTags.WALLS)
				.add(BlockRegistry.CLOUD_MARBLE_BRICK_WALL.get(),
						BlockRegistry.BLOOD_SANDSTONE_WALL.get());
		tag(BlockTags.SMELTS_TO_GLASS, ItemTags.SMELTS_TO_GLASS)
				.add(BlockRegistry.BLOOD_SAND.get());
	}
}