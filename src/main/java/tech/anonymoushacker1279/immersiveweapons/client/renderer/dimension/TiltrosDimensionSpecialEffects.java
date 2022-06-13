package tech.anonymoushacker1279.immersiveweapons.client.renderer.dimension;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TiltrosDimensionSpecialEffects extends DimensionSpecialEffects {

	public TiltrosDimensionSpecialEffects() {
		super(Float.NaN, true, SkyType.NORMAL, true, false);
		setSkyRenderHandler(new TiltrosSkyRenderHandler());
	}

	@Override
	public @NotNull Vec3 getBrightnessDependentFogColor(Vec3 scale, float pBrightness) {
		return scale.scale(0.15F);
	}

	@Override
	public boolean isFoggyAt(int pX, int pY) {
		return false;
	}

	@Override
	@Nullable
	public float[] getSunriseColor(float pTimeOfDay, float pPartialTicks) {
		return null;
	}
}