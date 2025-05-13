package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.equipment.EquipmentAsset;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.data.IWEquipmentAssets;

import java.util.function.BiConsumer;

public class IWEquipmentAssetsGenerator extends EquipmentAssetProvider {

	public IWEquipmentAssetsGenerator(PackOutput output) {
		super(output);
	}

	@Override
	protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
		output.accept(IWEquipmentAssets.MOLTEN, onlyHumanoid("immersiveweapons:molten"));
		output.accept(IWEquipmentAssets.COPPER, onlyHumanoid("immersiveweapons:copper"));
		output.accept(IWEquipmentAssets.TESLA, onlyHumanoid("immersiveweapons:tesla"));
		output.accept(IWEquipmentAssets.COBALT, onlyHumanoid("immersiveweapons:cobalt"));
		output.accept(IWEquipmentAssets.VENTUS, onlyHumanoid("immersiveweapons:ventus"));
		output.accept(IWEquipmentAssets.ASTRAL, onlyHumanoid("immersiveweapons:astral"));
		output.accept(IWEquipmentAssets.STARSTORM, onlyHumanoid("immersiveweapons:starstorm"));
		output.accept(IWEquipmentAssets.PADDED_LEATHER, EquipmentClientInfo.builder()
				.addHumanoidLayers(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "padded_leather"), true)
				.addHumanoidLayers(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "padded_leather_overlay"), false)
				.build());
		output.accept(IWEquipmentAssets.VOID, onlyHumanoid("immersiveweapons:void"));
	}
}