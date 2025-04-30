package tech.anonymoushacker1279.immersiveweapons.item.materials;

import net.minecraft.world.item.ToolMaterial;
import net.neoforged.neoforge.common.Tags;
import tech.anonymoushacker1279.immersiveweapons.data.groups.common.CommonItemTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWBlockTagGroups;
import tech.anonymoushacker1279.immersiveweapons.data.groups.immersiveweapons.IWItemTagGroups;

public class IWToolMaterials {

	public static final ToolMaterial COPPER = new ToolMaterial(
			IWBlockTagGroups.INCORRECT_FOR_COPPER_TOOL,
			180,
			5.9F,
			1.0F,
			12,
			Tags.Items.INGOTS_COPPER
	);

	public static final ToolMaterial COBALT = new ToolMaterial(
			IWBlockTagGroups.INCORRECT_FOR_COBALT_TOOL,
			300,
			6.2F,
			2.0F,
			15,
			CommonItemTagGroups.COBALT_INGOTS
	);

	public static final ToolMaterial MOLTEN = new ToolMaterial(
			IWBlockTagGroups.INCORRECT_FOR_MOLTEN_TOOL,
			1900,
			10.2F,
			5.0F,
			17,
			IWItemTagGroups.MOLTEN_INGOTS
	);

	public static final ToolMaterial TESLA = new ToolMaterial(
			IWBlockTagGroups.INCORRECT_FOR_TESLA_TOOL,
			2700,
			20.0F,
			7.0F,
			20,
			IWItemTagGroups.TESLA_INGOTS
	);

	public static final ToolMaterial VENTUS = new ToolMaterial(
			IWBlockTagGroups.INCORRECT_FOR_VENTUS_TOOL,
			1900,
			12.6F,
			5.0F,
			16,
			IWItemTagGroups.VENTUS_SHARDS
	);

	public static final ToolMaterial ASTRAL = new ToolMaterial(
			IWBlockTagGroups.INCORRECT_FOR_ASTRAL_TOOL,
			600,
			24.0F,
			4.0F,
			22,
			IWItemTagGroups.ASTRAL_INGOTS
	);

	public static final ToolMaterial STARSTORM = new ToolMaterial(
			IWBlockTagGroups.INCORRECT_FOR_STARSTORM_TOOL,
			1800,
			14.0F,
			7.0F,
			20,
			IWItemTagGroups.STARSTORM_INGOTS
	);

	public static final ToolMaterial VOID = new ToolMaterial(
			IWBlockTagGroups.INCORRECT_FOR_VOID_TOOL,
			2500,
			28.0F,
			9.0F,
			24,
			IWItemTagGroups.VOID_INGOTS
	);

	public static final ToolMaterial HANSIUM = new ToolMaterial(
			IWBlockTagGroups.INCORRECT_FOR_HANSIUM_TOOL,
			3000,
			28.0F,
			20.0F,
			24,
			IWItemTagGroups.HANSIUM_INGOTS
	);
}