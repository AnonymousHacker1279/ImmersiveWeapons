package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;

public class IWModelTemplates {

	public static final ModelTemplate GAUNTLET = ModelTemplates.createItem("immersiveweapons:gauntlet", Slots.MATERIAL);
	public static final ModelTemplate MUSKET_BALL = ModelTemplates.createItem("immersiveweapons:musket_ball", TextureSlot.ALL);
	public static final ModelTemplate TABLE = ModelTemplates.create("immersiveweapons:table", TextureSlot.ALL);
	public static final ModelTemplate FLAG = ModelTemplates.create("immersiveweapons:flag", Slots.FLAG);
	public static final ModelTemplate MAUL = ModelTemplates.createItem("immersiveweapons:maul", Slots.MATERIAL, Slots.MATERIAL_2);

	static class Slots {
		public static final TextureSlot MATERIAL = TextureSlot.create("material");
		public static final TextureSlot MATERIAL_2 = TextureSlot.create("material2");
		public static final TextureSlot FLAG = TextureSlot.create("flag");
	}
}