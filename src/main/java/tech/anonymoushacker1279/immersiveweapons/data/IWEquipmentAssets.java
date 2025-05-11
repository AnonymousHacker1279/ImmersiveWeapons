package tech.anonymoushacker1279.immersiveweapons.data;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentAsset;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWEquipmentAssets {

	private static final ResourceKey<? extends Registry<EquipmentAsset>> ROOT_ID = ResourceKey.createRegistryKey(
			ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "equipment_asset"));

	public static final ResourceKey<EquipmentAsset> MOLTEN = create("molten");
	public static final ResourceKey<EquipmentAsset> COPPER = create("copper");
	public static final ResourceKey<EquipmentAsset> TESLA = create("tesla");
	public static final ResourceKey<EquipmentAsset> COBALT = create("cobalt");
	public static final ResourceKey<EquipmentAsset> VENTUS = create("ventus");
	public static final ResourceKey<EquipmentAsset> ASTRAL = create("astral");
	public static final ResourceKey<EquipmentAsset> STARSTORM = create("starstorm");
	public static final ResourceKey<EquipmentAsset> PADDED_LEATHER = create("padded_leather");
	public static final ResourceKey<EquipmentAsset> VOID = create("void");

	private static ResourceKey<EquipmentAsset> create(String name) {
		return ResourceKey.create(ROOT_ID, ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}
}