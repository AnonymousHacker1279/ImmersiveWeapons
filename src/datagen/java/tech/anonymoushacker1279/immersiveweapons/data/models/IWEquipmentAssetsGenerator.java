package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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
		output.accept(IWEquipmentAssets.MOLTEN, onlyHumanoid("immersiveweapons:molten").build());
		output.accept(IWEquipmentAssets.TESLA, onlyHumanoid("immersiveweapons:tesla").build());
		output.accept(IWEquipmentAssets.COBALT, onlyHumanoid("immersiveweapons:cobalt").build());
		output.accept(IWEquipmentAssets.VENTUS, onlyHumanoid("immersiveweapons:ventus").build());
		output.accept(IWEquipmentAssets.ASTRAL, onlyHumanoid("immersiveweapons:astral").build());
		output.accept(IWEquipmentAssets.STARSTORM, onlyHumanoid("immersiveweapons:starstorm").build());
		output.accept(IWEquipmentAssets.PADDED_LEATHER, EquipmentClientInfo.builder()
				.addHumanoidLayers(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "padded_leather"), true)
				.addHumanoidLayers(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "padded_leather_overlay"), false)
				.build());
		output.accept(IWEquipmentAssets.VOID, onlyHumanoid("immersiveweapons:void").build());
	}
}