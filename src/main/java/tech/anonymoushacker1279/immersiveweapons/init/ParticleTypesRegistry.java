package tech.anonymoushacker1279.immersiveweapons.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact.BulletImpactParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticleOptions;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class ParticleTypesRegistry {

	// Particle Type Register
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, ImmersiveWeapons.MOD_ID);

	// Particles
	public static final Supplier<ParticleType<SmokeGrenadeParticleOptions>> SMOKE_GRENADE_PARTICLE = PARTICLE_TYPES.register("smoke_grenade", () -> new ParticleType<>(false, SmokeGrenadeParticleOptions.DESERIALIZER) {
		final Function<ParticleType<SmokeGrenadeParticleOptions>, Codec<SmokeGrenadeParticleOptions>> codec = (type) -> SmokeGrenadeParticleOptions.CODEC;

		@Override
		public Codec<SmokeGrenadeParticleOptions> codec() {
			return codec.apply(this);
		}
	});
	public static final Supplier<SimpleParticleType> BLOOD_PARTICLE = PARTICLE_TYPES.register("blood", () -> new SimpleParticleType(false));
	public static final Supplier<ParticleType<BulletImpactParticleOptions>> BULLET_IMPACT_PARTICLE = PARTICLE_TYPES.register("bullet_impact", () -> new ParticleType<>(false, BulletImpactParticleOptions.DESERIALIZER) {
		final Function<ParticleType<BulletImpactParticleOptions>, Codec<BulletImpactParticleOptions>> codec = (type) -> BulletImpactParticleOptions.CODEC;

		@Override
		public Codec<BulletImpactParticleOptions> codec() {
			return codec.apply(this);
		}
	});
	public static final Supplier<SimpleParticleType> MUZZLE_FLASH_PARTICLE = PARTICLE_TYPES.register("muzzle_flash", () -> new SimpleParticleType(false));
	public static final Supplier<SimpleParticleType> MOONGLOW_PARTICLE = PARTICLE_TYPES.register("moonglow", () -> new SimpleParticleType(false));
	public static final Supplier<SimpleParticleType> STARDUST_LEAVES_PARTICLE = PARTICLE_TYPES.register("stardust_leaves", () -> new SimpleParticleType(false));
	public static final Supplier<SimpleParticleType> DEADMANS_DESERT_AMBIENT_PARTICLE = PARTICLE_TYPES.register("deadmans_desert_ambient", () -> new SimpleParticleType(false));
}