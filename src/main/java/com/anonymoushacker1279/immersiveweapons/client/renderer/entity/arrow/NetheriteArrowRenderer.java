package com.anonymoushacker1279.immersiveweapons.client.renderer.entity.arrow;

import com.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import com.anonymoushacker1279.immersiveweapons.entity.projectile.CustomArrowEntity.NetheriteArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class NetheriteArrowRenderer extends ArrowRenderer<NetheriteArrowEntity> implements EntityRendererProvider<NetheriteArrowEntity> {
	/**
	 * Constructor for NetheriteArrowRenderer.
	 * @param context a <code>Context</code> instance
	 */
	public NetheriteArrowRenderer(Context context) {
		super(context);
	}

	/**
	 * Get the texture location.
	 * @param entity the <code>NetheriteArrowEntity</code> instance
	 * @return ResourceLocation
	 */
	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull NetheriteArrowEntity entity) {
		return new ResourceLocation(ImmersiveWeapons.MOD_ID, "textures/entity/projectiles/netherite_arrow.png");
	}

	@Override
	public @NotNull EntityRenderer<NetheriteArrowEntity> create(@NotNull Context context) {
		return new NetheriteArrowRenderer(context);
	}
}