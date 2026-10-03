package tech.anonymoushacker1279.iwcompatbridge.data.model;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import tech.anonymoushacker1279.iwcompatbridge.IWCompatBridge;
import tech.anonymoushacker1279.iwcompatbridge.init.IWCBItemRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class IWCBModelProvider extends ModelProvider {

	public IWCBModelProvider(PackOutput output) {
		super(output, IWCompatBridge.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		List<Item> items = new ArrayList<>(100);

		IWCBItemRegistry.ITEMS.getEntries().stream().map(Supplier::get).forEach(items::add);

		for (Item item : items) {
			itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
		}
	}
}