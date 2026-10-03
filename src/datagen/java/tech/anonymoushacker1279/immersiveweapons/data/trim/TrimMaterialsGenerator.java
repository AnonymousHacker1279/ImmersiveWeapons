package tech.anonymoushacker1279.immersiveweapons.data.trim;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import tech.anonymoushacker1279.immersiveweapons.data.IWTrimMaterials;

public class TrimMaterialsGenerator {

	public static void bootstrap(BootstrapContext<TrimMaterial> context) {
		register(context, IWTrimMaterials.COBALT, 0x1F7FBF);
		register(context, IWTrimMaterials.MOLTEN, 0xF2A218);
		register(context, IWTrimMaterials.VENTUS, 0xD2D0C4);
		register(context, IWTrimMaterials.TESLA, 0x3FB0E8);
		register(context, IWTrimMaterials.ASTRAL, 0xE5B07A);
		register(context, IWTrimMaterials.STARSTORM, 0xF5A010);
		register(context, IWTrimMaterials.VOID, 0x6B4A8C);
	}

	private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> key, int color) {
		Component description = Component.translatable(Util.makeDescriptionId("trim_material", key.identifier()))
				.withStyle(Style.EMPTY.withColor(color));
		context.register(key, new TrimMaterial(IWTrimMaterials.palette(key), description));
	}
}
