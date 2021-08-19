package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.misc;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Boat.Type;
import org.jetbrains.annotations.NotNull;

public class BurnedOakBoatRenderer extends BoatRenderer {
	private final Pair<ResourceLocation, BoatModel> boatResources;

	public BurnedOakBoatRenderer(Context context) {
		super(context);
		shadowRadius = 0.8F;
		boatResources = Pair.of(new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/boat/burned_oak.png"), new BoatModel(context.bakeLayer(ModelLayers.createBoatModelName(Type.OAK))));
	}

	@Override
	public @NotNull Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) {
		return boatResources;
	}
}