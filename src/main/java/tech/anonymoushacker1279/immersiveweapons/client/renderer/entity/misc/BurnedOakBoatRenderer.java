package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.misc;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

public class BurnedOakBoatRenderer extends BoatRenderer {

	final BoatModel model;
	final ResourceLocation textureLocation;

	public BurnedOakBoatRenderer(Context context, boolean hasChest) {
		super(context, hasChest);
		shadowRadius = 0.8F;

		ModelLayerLocation modelLayerLocation = hasChest
				? new ModelLayerLocation(new ResourceLocation("minecraft", "chest_boat/oak"), "main")
				: new ModelLayerLocation(new ResourceLocation("minecraft", "boat/oak"), "main");
		model = new BoatModel(context.bakeLayer(modelLayerLocation), hasChest);

		textureLocation = hasChest
				? new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/chest_boat/burned_oak.png")
				: new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/boat/burned_oak.png");
	}

	@Override
	public @NotNull Pair<ResourceLocation, BoatModel> getModelWithLocation(@NotNull Boat boat) {
		return Pair.of(textureLocation, model);
	}
}