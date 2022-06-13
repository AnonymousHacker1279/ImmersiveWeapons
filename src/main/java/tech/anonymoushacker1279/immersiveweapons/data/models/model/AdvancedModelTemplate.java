package tech.anonymoushacker1279.immersiveweapons.data.models.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class AdvancedModelTemplate extends ModelTemplate {

	public AdvancedModelTemplate(Optional<ResourceLocation> pModel, Optional<String> pSuffix, TextureSlot... pRequiredSlots) {
		super(pModel, pSuffix, pRequiredSlots);
	}

	public void createItem(ResourceLocation pModelLocation, BiConsumer<ResourceLocation, Supplier<JsonElement>> pModelOutput) {
		pModelOutput.accept(pModelLocation, () -> {
			JsonObject jsonObject = new JsonObject();
			model.ifPresent((resourceLocation) -> jsonObject.addProperty("parent", resourceLocation.toString()));

			return jsonObject;
		});
	}
}