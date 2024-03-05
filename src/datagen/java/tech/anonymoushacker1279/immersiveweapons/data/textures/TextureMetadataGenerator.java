package tech.anonymoushacker1279.immersiveweapons.data.textures;

import net.minecraft.data.PackOutput;

import java.util.List;

public class TextureMetadataGenerator extends TextureMetadataProvider {

	public TextureMetadataGenerator(PackOutput output) {
		super(output);
	}

	@Override
	protected void addTextures() {
		ItemMetadataBuilder("molten_sword", 2).build();
		ItemMetadataBuilder("molten_pickaxe", 5).build();
		ItemMetadataBuilder("molten_axe", 5).build();
		ItemMetadataBuilder("molten_shovel", 5).build();
		ItemMetadataBuilder("molten_hoe", 5).build();
		ItemMetadataBuilder("molten_shard", 6).build();
		ItemMetadataBuilder("sculk_staff", 3).build();
		createStarstormItems();
		ItemMetadataBuilder("tesla_sword", 2).build();
		ItemMetadataBuilder("tesla_pickaxe", 2).build();
		ItemMetadataBuilder("tesla_axe", 2).build();
		ItemMetadataBuilder("tesla_shovel", 3).build();
		ItemMetadataBuilder("tesla_hoe", 3).build();
		ItemMetadataBuilder("tesla_ingot", 1).build();
		createVentusTools();
		ItemMetadataBuilder("ventus_staff", 5).setFrameOrder(0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1).build();

		BlockMetadataBuilder("champion_base", 8).interpolate().build();
		BlockMetadataBuilder("molten_block", 12).interpolate().build();
		BlockMetadataBuilder("molten_ore", 12).interpolate().build();
		BlockMetadataBuilder("starstorm_block", 24).interpolate().setFrameOrder(0, 1, 2, 3, 2, 1).build();
		BlockMetadataBuilder("telsa_block", 2).interpolate().build();
	}

	private void createStarstormItems() {
		List<String> items = List.of("sword", "pickaxe", "axe", "shovel", "hoe", "helmet", "chestplate", "leggings", "boots", "ingot");

		for (String item : items) {
			ItemMetadataBuilder("starstorm_" + item, 4)
					.interpolate()
					.setFrameOrder(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
					.build();
		}
	}

	private void createVentusTools() {
		List<String> items = List.of("sword", "pickaxe", "axe", "shovel", "hoe");

		for (String item : items) {
			ItemMetadataBuilder("ventus_" + item, 5).build();
		}
	}
}