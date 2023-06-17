package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.IWItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.BlockRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.utility.CustomBoatItem;

import java.util.Collection;
import java.util.function.Supplier;

public record WoodFamilies(Supplier<? extends Block> planks,
                           Supplier<? extends SlabBlock> slab,
                           Supplier<? extends StairBlock> stairs,
                           Supplier<? extends PressurePlateBlock> pressurePlate,
                           Supplier<? extends ButtonBlock> button,
                           Supplier<? extends FenceBlock> fence,
                           Supplier<? extends FenceGateBlock> fenceGate,
                           Supplier<? extends DoorBlock> door,
                           Supplier<? extends TrapDoorBlock> trapdoor,
                           Supplier<? extends StandingSignBlock> standingSign,
                           Supplier<? extends WallSignBlock> wallSign,
                           Supplier<? extends CeilingHangingSignBlock> ceilingHangingSign,
                           Supplier<? extends WallHangingSignBlock> wallHangingSign,
                           Supplier<? extends RotatedPillarBlock> log,
                           Supplier<? extends RotatedPillarBlock> strippedLog,
                           Supplier<? extends RotatedPillarBlock> wood,
                           Supplier<? extends RotatedPillarBlock> strippedWood,
                           @Nullable Supplier<? extends LeavesBlock> leaves,
                           @Nullable Supplier<? extends SaplingBlock> sapling,
                           Supplier<? extends CustomBoatItem> boat,
                           Supplier<? extends CustomBoatItem> chestBoat,
                           TagKey<Block> logsBlockTag,
                           TagKey<Item> logsItemTag) {

	public static final WoodFamilies BURNED_OAK = new WoodFamilies(
			BlockRegistry.BURNED_OAK_PLANKS,
			BlockRegistry.BURNED_OAK_SLAB,
			BlockRegistry.BURNED_OAK_STAIRS,
			BlockRegistry.BURNED_OAK_PRESSURE_PLATE,
			BlockRegistry.BURNED_OAK_BUTTON,
			BlockRegistry.BURNED_OAK_FENCE,
			BlockRegistry.BURNED_OAK_FENCE_GATE,
			BlockRegistry.BURNED_OAK_DOOR,
			BlockRegistry.BURNED_OAK_TRAPDOOR,
			BlockRegistry.BURNED_OAK_SIGN,
			BlockRegistry.BURNED_OAK_WALL_SIGN,
			BlockRegistry.BURNED_OAK_HANGING_SIGN,
			BlockRegistry.BURNED_OAK_WALL_HANGING_SIGN,
			BlockRegistry.BURNED_OAK_LOG,
			BlockRegistry.STRIPPED_BURNED_OAK_LOG,
			BlockRegistry.BURNED_OAK_WOOD,
			BlockRegistry.STRIPPED_BURNED_OAK_WOOD,
			null,
			null,
			ItemRegistry.BURNED_OAK_BOAT,
			ItemRegistry.BURNED_OAK_CHEST_BOAT,
			IWBlockTagGroups.BURNED_OAK_LOGS,
			IWItemTagGroups.BURNED_OAK_LOGS
	);

	public static final WoodFamilies STARDUST = new WoodFamilies(
			BlockRegistry.STARDUST_PLANKS,
			BlockRegistry.STARDUST_SLAB,
			BlockRegistry.STARDUST_STAIRS,
			BlockRegistry.STARDUST_PRESSURE_PLATE,
			BlockRegistry.STARDUST_BUTTON,
			BlockRegistry.STARDUST_FENCE,
			BlockRegistry.STARDUST_FENCE_GATE,
			BlockRegistry.STARDUST_DOOR,
			BlockRegistry.STARDUST_TRAPDOOR,
			BlockRegistry.STARDUST_SIGN,
			BlockRegistry.STARDUST_WALL_SIGN,
			BlockRegistry.STARDUST_HANGING_SIGN,
			BlockRegistry.STARDUST_WALL_HANGING_SIGN,
			BlockRegistry.STARDUST_LOG,
			BlockRegistry.STRIPPED_STARDUST_LOG,
			BlockRegistry.STARDUST_WOOD,
			BlockRegistry.STRIPPED_STARDUST_WOOD,
			BlockRegistry.STARDUST_LEAVES,
			BlockRegistry.STARDUST_SAPLING,
			ItemRegistry.STARDUST_BOAT,
			ItemRegistry.STARDUST_CHEST_BOAT,
			IWBlockTagGroups.STARDUST_LOGS,
			IWItemTagGroups.STARDUST_LOGS
	);

	public static final Collection<WoodFamilies> FAMILIES = ImmutableList.of(BURNED_OAK, STARDUST);
}