package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.misc;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Boat.Type;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Stream;

public class BurnedOakBoatRenderer extends BoatRenderer {

	Map<Type, Pair<ResourceLocation, BoatModel>> boatResources;

	public BurnedOakBoatRenderer(Context context, boolean hasChest) {
		super(context, hasChest);
		shadowRadius = 0.8F;
		boatResources = Stream.of(Type.values()).collect(ImmutableMap.toImmutableMap((type) -> type,
				(type) -> Pair.of(new ResourceLocation(getTextureLocation(type, hasChest)), createBoatModel(context, type, hasChest))));
	}

	private static String getTextureLocation(Boat.Type type, boolean hasChest) {
		return hasChest ? "immersiveweapons:textures/entity/boat/" + type.getName() + "_boat.png" : "immersiveweapons:textures/entity/boat/" + type.getName() + ".png";
	}

	private BoatModel createBoatModel(EntityRendererProvider.Context context, Boat.Type type, boolean hasChest) {
		ModelLayerLocation location = hasChest ? ModelLayers.createChestBoatModelName(type) : ModelLayers.createBoatModelName(type);
		return new BoatModel(context.bakeLayer(location), hasChest);
	}

	@Override
	public @NotNull Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) {
		return boatResources.get(boat.getBoatType());
	}
}