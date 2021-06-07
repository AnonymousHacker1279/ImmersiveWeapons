package com.anonymoushacker1279.immersiveweapons.client.renderer.entity;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.client.renderer.entity.model.BasicPlayerModel;
import com.anonymoushacker1279.immersiveweapons.entity.passive.AbstractFieldMedicEntity;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.util.ResourceLocation;

public class FieldMedicRenderer extends BipedRenderer<AbstractFieldMedicEntity, BasicPlayerModel<AbstractFieldMedicEntity>> {

	private static final ResourceLocation FIELD_MEDIC = new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/field_medic/field_medic.png");

	public FieldMedicRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BasicPlayerModel<>(1.0f, false), 0.5F);
		this.addLayer(new BipedArmorLayer<>(this, new BasicPlayerModel<>(0.5F, false), new BasicPlayerModel<>(1.0F, false)));
	}

	@Override
	public ResourceLocation getTextureLocation(AbstractFieldMedicEntity entity) {
		return FIELD_MEDIC;
	}
}