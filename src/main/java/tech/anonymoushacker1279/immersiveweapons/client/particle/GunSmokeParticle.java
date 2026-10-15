package tech.anonymoushacker1279.immersiveweapons.client.particle;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.renderpearl.api.pipeline.BlendFunction;
import com.mojang.renderpearl.api.pipeline.ColorTargetState;
import com.mojang.renderpearl.api.pipeline.CompareOp;
import com.mojang.renderpearl.api.pipeline.DepthStencilState;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.oit.OitPipelineSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;

/// Gunpowder smoke, drawn with a custom shader (`shaders/core/gun_smoke`) that builds the puff procedurally from noise
/// instead of sampling a sprite.
public class GunSmokeParticle extends SingleQuadParticle {

	private static final Identifier SHADER = Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "core/gun_smoke");

	/// The same setup as the vanilla particle pipelines, but with our shaders.
	private static final RenderPipeline.Builder PIPELINE_BUILDER = RenderPipeline.builder()
			.withVertexShader(SHADER)
			.withFragmentShader(SHADER)
			.withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER2)
			.withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
			.withBindGroupLayout(BindGroupLayouts.FOG)
			.withVertexBinding(0, DefaultVertexFormat.PARTICLE)
			.withPrimitiveTopology(PrimitiveTopology.QUADS)
			.withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false));

	public static final OitPipelineSet OIT_PIPELINES = OitPipelineSet.builder("gun_smoke", PIPELINE_BUILDER).build();

	/// Used when order-independent transparency is not active.
	public static final RenderPipeline PIPELINE = RenderPipeline.builder()
			.withLocation(Identifier.fromNamespaceAndPath(ImmersiveWeapons.MOD_ID, "pipeline/gun_smoke"))
			.withVertexShader(SHADER)
			.withFragmentShader(SHADER)
			.withBindGroupLayout(BindGroupLayouts.GLOBALS)
			.withBindGroupLayout(BindGroupLayouts.PROJECTION)
			.withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
			.withBindGroupLayout(BindGroupLayouts.FOG)
			.withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER2)
			.withVertexBinding(0, DefaultVertexFormat.PARTICLE)
			.withPrimitiveTopology(PrimitiveTopology.QUADS)
			.withDepthStencilState(DepthStencilState.DEFAULT)
			.withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
			.build();

	private static final Layer LAYER = new Layer(true, TextureAtlas.LOCATION_PARTICLES, PIPELINE, OIT_PIPELINES);

	private final float startSize;

	protected GunSmokeParticle(ClientLevel level, double x, double y, double z,
	                           double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
		super(level, x, y, z, sprites.first());

		xd = xSpeed * 0.1d;
		yd = ySpeed * 0.1d + 0.01d;
		zd = zSpeed * 0.1d;

		friction = 0.9f;
		gravity = -0.01f;
		hasPhysics = false;
		lifetime = 30 + random.nextInt(20);
		startSize = 0.3f + random.nextFloat() * 0.2f;
		quadSize = startSize;
		// Keep the wisps upright so they rise instead of curling sideways
		roll = (random.nextFloat() - 0.5f) * 0.6f;
		oRoll = roll;

		float gray = 0.55f + random.nextFloat() * 0.25f;
		// The shader has no other per-puff data, so it gets a seed hidden in the low bits of the color channels
		int seed = random.nextInt(ParticleSeedPacking.SEED_RANGE);
		rCol = ParticleSeedPacking.pack(gray, seed, 0);
		gCol = ParticleSeedPacking.pack(gray, seed, 1);
		bCol = ParticleSeedPacking.pack(gray, seed, 2);
		alpha = 1.0f;
	}

	/// Size and fade are computed per frame from the partial tick instead of per tick, which would make the puff
	/// visibly step as it expands.
	@Override
	public void extract(QuadParticleRenderState state, Camera camera, float partialTick) {
		float lifeProgress = Math.min((age + partialTick) / (float) lifetime, 1.0f);
		quadSize = startSize * (1.0f + lifeProgress * 2.5f);
		alpha = 1.0f - lifeProgress;

		super.extract(state, camera, partialTick);
	}

	@Override
	protected Layer getLayer() {
		return LAYER;
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {

		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z,
		                               double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
			return new GunSmokeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
		}
	}
}
