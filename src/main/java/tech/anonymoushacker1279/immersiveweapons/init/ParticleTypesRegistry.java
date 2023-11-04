package tech.anonymoushacker1279.immersiveweapons.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.neoforge.registries.*;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.particle.bullet_impact.BulletImpactParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticleOptions;

import java.util.function.Function;

@SuppressWarnings({"unused"})
public class ParticleTypesRegistry {

	// Particle Type Register
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ImmersiveWeapons.MOD_ID);

	// Particles
	public static final RegistryObject<ParticleType<SmokeGrenadeParticleOptions>> SMOKE_GRENADE_PARTICLE = PARTICLE_TYPES.register("smoke_grenade", () -> new ParticleType<>(false, SmokeGrenadeParticleOptions.DESERIALIZER) {
		final Function<ParticleType<SmokeGrenadeParticleOptions>, Codec<SmokeGrenadeParticleOptions>> codec = (type) -> SmokeGrenadeParticleOptions.CODEC;

		@Override
		public Codec<SmokeGrenadeParticleOptions> codec() {
			return codec.apply(this);
		}
	});
	public static final RegistryObject<SimpleParticleType> BLOOD_PARTICLE = PARTICLE_TYPES.register("blood", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<BulletImpactParticleOptions>> BULLET_IMPACT_PARTICLE = PARTICLE_TYPES.register("bullet_impact", () -> new ParticleType<>(false, BulletImpactParticleOptions.DESERIALIZER) {
		final Function<ParticleType<BulletImpactParticleOptions>, Codec<BulletImpactParticleOptions>> codec = (type) -> BulletImpactParticleOptions.CODEC;

		@Override
		public Codec<BulletImpactParticleOptions> codec() {
			return codec.apply(this);
		}
	});
	public static final RegistryObject<SimpleParticleType> MUZZLE_FLASH_PARTICLE = PARTICLE_TYPES.register("muzzle_flash", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> MOONGLOW_PARTICLE = PARTICLE_TYPES.register("moonglow", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> STARDUST_LEAVES_PARTICLE = PARTICLE_TYPES.register("stardust_leaves", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> DEADMANS_DESERT_AMBIENT_PARTICLE = PARTICLE_TYPES.register("deadmans_desert_ambient", () -> new SimpleParticleType(false));
}