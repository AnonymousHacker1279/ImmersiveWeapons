package tech.anonymoushacker1279.immersiveweapons.client.renderer.entity.misc;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.Boat;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.CustomBoatEntity;
import tech.anonymoushacker1279.immersiveweapons.entity.vehicle.CustomBoatType;

import java.util.Map;
import java.util.stream.Stream;

public class CustomBoatRenderer extends BoatRenderer {

	private final Map<CustomBoatType, Pair<ResourceLocation, ListModel<Boat>>> boatResources;

	public CustomBoatRenderer(Context context, boolean hasChest) {
		super(context, hasChest);
		shadowRadius = 0.8F;

		boatResources = Stream.of(CustomBoatType.values())
				.collect(
						ImmutableMap.toImmutableMap(
								type -> type,
								boatType -> Pair.of(getTextureLocation(boatType, hasChest), createBoatModel(context, boatType, hasChest))
						)
				);
	}

	@Override
	public Pair<ResourceLocation, ListModel<Boat>> getModelWithLocation(Boat boat) {
		if (boat instanceof CustomBoatEntity customBoatEntity) {
			return boatResources.get(customBoatEntity.getCustomBoatType());
		} else {
			return boatResources.get(CustomBoatType.BURNED_OAK);
		}
	}

	private static ResourceLocation getTextureLocation(CustomBoatType type, boolean hasChest) {
		String path = hasChest ? "textures/entity/chest_boat/" + type.getName() + ".png" : "textures/entity/boat/" + type.getName() + ".png";
		return ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, path);
	}

	private ListModel<Boat> createBoatModel(EntityRendererProvider.Context context, CustomBoatType type, boolean hasChest) {
		ModelLayerLocation layerLocation = hasChest ? createChestBoatModelName(type) : createBoatModelName(type);
		ModelPart part = context.bakeLayer(layerLocation);

		return hasChest ? new ChestBoatModel(part) : new BoatModel(part);
	}

	public static ModelLayerLocation createBoatModelName(CustomBoatType type) {
		return createLocation("boat/" + type.getName());
	}

	public static ModelLayerLocation createChestBoatModelName(CustomBoatType type) {
		return createLocation("chest_boat/" + type.getName());
	}

	private static ModelLayerLocation createLocation(String path) {
		return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, path), "main");
	}
}