package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.misc;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;

public class CustomBoatRenderer extends BoatRenderer {

	final BoatModel model;
	final ResourceLocation textureLocation;

	public CustomBoatRenderer(Context context, boolean hasChest, ResourceLocation textureLocation) {
		super(context, hasChest);
		shadowRadius = 0.8F;

		ModelLayerLocation modelLayerLocation = hasChest
				? new ModelLayerLocation(new ResourceLocation("minecraft", "chest_boat/oak"), "main")
				: new ModelLayerLocation(new ResourceLocation("minecraft", "boat/oak"), "main");
		model = new BoatModel(context.bakeLayer(modelLayerLocation), hasChest);

		this.textureLocation = textureLocation;
	}

	@Override
	public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) {
		return Pair.of(textureLocation, model);
	}
}