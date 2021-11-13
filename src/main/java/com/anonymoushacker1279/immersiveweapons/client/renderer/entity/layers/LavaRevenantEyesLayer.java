package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.layers;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.model.LavaRevenantModel;
import com.anonymoushacker1279.immersiveweapons.entity.monster.LavaRevenantEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LavaRevenantEyesLayer<T extends LavaRevenantEntity> extends EyesLayer<T, LavaRevenantModel<T>> {
	private static final RenderType EYES = RenderType.eyes(new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/lava_revenant/lava_revenant.png"));

	public LavaRevenantEyesLayer(RenderLayerParent<T, LavaRevenantModel<T>> layerParent) {
		super(layerParent);
	}

	@Override
	public @NotNull RenderType renderType() {
		return EYES;
	}
}