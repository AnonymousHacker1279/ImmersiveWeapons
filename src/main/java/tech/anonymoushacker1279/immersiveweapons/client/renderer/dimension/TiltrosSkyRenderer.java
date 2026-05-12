package tech.anonymoushacker1279.immersiveweapons.client.renderer.dimension;

import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.state.level.SkyRenderState;
import net.neoforged.neoforge.client.CustomSkyboxRenderer;
import org.joml.Matrix4fc;

public class TiltrosSkyRenderer implements CustomSkyboxRenderer {

	private final TiltrosDimensionRenderer renderer;

	public TiltrosSkyRenderer() {
		this.renderer = new TiltrosDimensionRenderer();
	}

	@Override
	public boolean renderSky(LevelRenderState levelRenderState, SkyRenderState skyRenderState, Matrix4fc modelViewMatrix, Runnable setupFog) {
		setupFog.run();
		renderer.renderSky();
		return true;
	}
}