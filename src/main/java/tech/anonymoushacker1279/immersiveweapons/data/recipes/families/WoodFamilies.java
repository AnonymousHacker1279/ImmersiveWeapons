package tech.anonymoushacker1279.immersiveweapons.data.recipes.families;

import com.google.common.collect.ImmutableList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.tags.groups.immersiveweapons.ImmersiveWeaponsItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
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
			DeferredRegistryHandler.BURNED_OAK_PLANKS,
			DeferredRegistryHandler.BURNED_OAK_SLAB,
			DeferredRegistryHandler.BURNED_OAK_STAIRS,
			DeferredRegistryHandler.BURNED_OAK_PRESSURE_PLATE,
			DeferredRegistryHandler.BURNED_OAK_BUTTON,
			DeferredRegistryHandler.BURNED_OAK_FENCE,
			DeferredRegistryHandler.BURNED_OAK_FENCE_GATE,
			DeferredRegistryHandler.BURNED_OAK_DOOR,
			DeferredRegistryHandler.BURNED_OAK_TRAPDOOR,
			DeferredRegistryHandler.BURNED_OAK_SIGN,
			DeferredRegistryHandler.BURNED_OAK_WALL_SIGN,
			DeferredRegistryHandler.BURNED_OAK_LOG,
			DeferredRegistryHandler.STRIPPED_BURNED_OAK_LOG,
			DeferredRegistryHandler.BURNED_OAK_WOOD,
			DeferredRegistryHandler.STRIPPED_BURNED_OAK_WOOD,
			null,
			null,
			DeferredRegistryHandler.BURNED_OAK_BOAT,
			DeferredRegistryHandler.BURNED_OAK_CHEST_BOAT,
			ImmersiveWeaponsBlockTagGroups.BURNED_OAK_LOGS,
			ImmersiveWeaponsItemTagGroups.BURNED_OAK_LOGS
	);

	public static final WoodFamilies STARDUST = new WoodFamilies(
			DeferredRegistryHandler.STARDUST_PLANKS,
			DeferredRegistryHandler.STARDUST_SLAB,
			DeferredRegistryHandler.STARDUST_STAIRS,
			DeferredRegistryHandler.STARDUST_PRESSURE_PLATE,
			DeferredRegistryHandler.STARDUST_BUTTON,
			DeferredRegistryHandler.STARDUST_FENCE,
			DeferredRegistryHandler.STARDUST_FENCE_GATE,
			DeferredRegistryHandler.STARDUST_DOOR,
			DeferredRegistryHandler.STARDUST_TRAPDOOR,
			DeferredRegistryHandler.STARDUST_SIGN,
			DeferredRegistryHandler.STARDUST_WALL_SIGN,
			DeferredRegistryHandler.STARDUST_LOG,
			DeferredRegistryHandler.STRIPPED_STARDUST_LOG,
			DeferredRegistryHandler.STARDUST_WOOD,
			DeferredRegistryHandler.STRIPPED_STARDUST_WOOD,
			DeferredRegistryHandler.STARDUST_LEAVES,
			DeferredRegistryHandler.STARDUST_SAPLING,
			DeferredRegistryHandler.STARDUST_BOAT,
			DeferredRegistryHandler.STARDUST_CHEST_BOAT,
			ImmersiveWeaponsBlockTagGroups.STARDUST_LOGS,
			ImmersiveWeaponsItemTagGroups.STARDUST_LOGS
	);

	public static final Collection<WoodFamilies> FAMILIES = ImmutableList.of(BURNED_OAK, STARDUST);
}