package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.PackOutput;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class IWModelProvider extends ModelProvider {

	public IWModelProvider(PackOutput output) {
		super(output, ImmersiveWeapons.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		IWBlockModelGenerator.registerModels(blockModels);
		IWItemModelGenerator.registerModels(itemModels);
	}
}