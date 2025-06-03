package tech.anonymoushacker1279.immersiveweapons.block.core;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.world.level.CustomBlockSetTypes;

public class WoodTypes {

	public static final WoodType BURNED_OAK = WoodType.register(new WoodType(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "burned_oak").toString(), CustomBlockSetTypes.BURNED_OAK));
	public static final WoodType STARDUST = WoodType.register(new WoodType(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "stardust").toString(), CustomBlockSetTypes.STARDUST));

	public static void init() {
	}
}