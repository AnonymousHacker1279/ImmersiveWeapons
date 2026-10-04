package tech.anonymoushacker1279.immersiveweapons.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWTrimMaterials {

	public static final ResourceKey<TrimMaterial> COBALT = create("cobalt");
	public static final ResourceKey<TrimMaterial> MOLTEN = create("molten");
	public static final ResourceKey<TrimMaterial> VENTUS = create("ventus");
	public static final ResourceKey<TrimMaterial> TESLA = create("tesla");
	public static final ResourceKey<TrimMaterial> ASTRAL = create("astral");
	public static final ResourceKey<TrimMaterial> STARSTORM = create("starstorm");
	public static final ResourceKey<TrimMaterial> VOID = create("void");

	/// Palette IDs for a material. The palette files are prefixed with `iw_` since palette names are shared across mods.
	public static Identifier palette(ResourceKey<TrimMaterial> material) {
		return Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "trim/iw_" + material.identifier().getPath());
	}

	public static Identifier darkerPalette(ResourceKey<TrimMaterial> material) {
		return palette(material).withSuffix("_darker");
	}

	private static ResourceKey<TrimMaterial> create(String name) {
		return ResourceKey.create(Registries.TRIM_MATERIAL, Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, name));
	}
}
