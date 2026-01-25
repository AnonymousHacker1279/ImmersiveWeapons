package tech.anonymoushacker1279.immersiveweapons.client.renderer.dimension;

import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.client.renderer.state.SkyRenderState;
import net.neoforged.neoforge.client.CustomSkyboxRenderer;
import org.joml.Matrix4f;

public class TiltrosSkyRenderer implements CustomSkyboxRenderer {

	private final TiltrosDimensionRenderer renderer;

	public TiltrosSkyRenderer() {
		this.renderer = new TiltrosDimensionRenderer();
	}

	@Override
	public boolean renderSky(LevelRenderState levelRenderState, SkyRenderState skyRenderState, Matrix4f modelViewMatrix, Runnable setupFog) {
		setupFog.run();
		renderer.renderSky();
		return true;
	}
}