package tech.anonymoushacker1279.immersiveweapons.data.models;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;

public class IWModelTemplates {

	public static final ModelTemplate TRIVIAL_CUBE_CUTOUT_MIPPED = ExtendedModelTemplateBuilder.of(ModelTemplates.CUBE_ALL)
			.renderType(ResourceLocation.withDefaultNamespace("cutout_mipped"))
			.build();
	public static final ModelTemplate TRIVIAL_CUBE_TRANSLUCENT = ExtendedModelTemplateBuilder.of(ModelTemplates.CUBE_ALL)
			.renderType(ResourceLocation.withDefaultNamespace("translucent"))
			.build();
	public static final ModelTemplate STARDUST_LEAVES = ExtendedModelTemplateBuilder.of(ModelTemplates.CUBE_ALL)
			.renderType(ResourceLocation.withDefaultNamespace("cutout_mipped"))
			.element(builder -> {
				builder.emissivity(15, 3);
			})
			.build();
	public static final ModelTemplate ORE = ExtendedModelTemplateBuilder.of(ModelTemplates.CUBE_ALL)
			.renderType(ResourceLocation.withDefaultNamespace("cutout_mipped"))
			.build();
	public static final ModelTemplate TABLE = ExtendedModelTemplateBuilder.of(ModelTemplates.create("table", TextureSlot.ALL))
			.renderType(ResourceLocation.withDefaultNamespace("cutout_mipped"))
			.build();
}