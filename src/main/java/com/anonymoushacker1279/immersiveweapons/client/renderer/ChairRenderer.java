package com.anonymoushacker1279.immersiveweapons.client.renderer;

import com.anonymoushacker1279.immersiveweapons.entity.misc.ChairEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ChairRenderer extends EntityRenderer<ChairEntity> {

	public ChairRenderer(EntityRendererManager manager) {
		super(manager);
	}

	@Override
	public ResourceLocation getTextureLocation(ChairEntity seatEntity) {
		return null;
	}
}