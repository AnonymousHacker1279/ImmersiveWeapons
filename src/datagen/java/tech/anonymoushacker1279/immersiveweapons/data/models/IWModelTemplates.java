package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;

public class IWModelTemplates {

	public static final ModelTemplate PIKE = ModelTemplates.createItem("immersiveweapons:pike", Slots.HANDLE, Slots.MATERIAL);
	public static final ModelTemplate GAUNTLET = ModelTemplates.createItem("immersiveweapons:gauntlet", Slots.MATERIAL);
	public static final ModelTemplate MUSKET_BALL = ModelTemplates.createItem("immersiveweapons:musket_ball", TextureSlot.ALL);
	public static final ModelTemplate TABLE = ModelTemplates.create("immersiveweapons:table", TextureSlot.ALL)
			.extend()
			.renderType("cutout_mipped")
			.build();
	public static final ModelTemplate FLAG = ModelTemplates.create("immersiveweapons:flag", Slots.FLAG);

	static class Slots {
		public static final TextureSlot HANDLE = TextureSlot.create("handle");
		public static final TextureSlot MATERIAL = TextureSlot.create("material");
		public static final TextureSlot FLAG = TextureSlot.create("flag");
	}
}