package tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;
import tech.anonymoushacker1279.immersiveweapons.init.ParticleTypesRegistry;

import java.util.Locale;

public class SmokeGrenadeParticleOptions implements ParticleOptions {

	public static final Codec<SmokeGrenadeParticleOptions> CODEC = RecordCodecBuilder.create((particleOptionsInstance)
			-> particleOptionsInstance
			.group(ExtraCodecs.VECTOR3F.fieldOf("color").forGetter((particleOptions) -> particleOptions.color),
					Codec.FLOAT.fieldOf("scale").forGetter((particleOptions) -> particleOptions.scale))
			.apply(particleOptionsInstance, SmokeGrenadeParticleOptions::new));

	protected final Vector3f color;
	protected final float scale;

	public static final ParticleOptions.Deserializer<SmokeGrenadeParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>() {
		@Override
		public SmokeGrenadeParticleOptions fromCommand(ParticleType<SmokeGrenadeParticleOptions> particleType,
		                                               StringReader reader) throws CommandSyntaxException {

			Vector3f vector3f = SmokeGrenadeParticleOptions.readVector3f(reader);
			reader.expect(' ');
			float vibrancy = reader.readFloat();
			return new SmokeGrenadeParticleOptions(vector3f, vibrancy);
		}

		@Override
		public SmokeGrenadeParticleOptions fromNetwork(ParticleType<SmokeGrenadeParticleOptions> particleType,
		                                               FriendlyByteBuf byteBuf) {

			return new SmokeGrenadeParticleOptions(SmokeGrenadeParticleOptions.readVector3f(byteBuf), byteBuf.readFloat());
		}
	};

	public SmokeGrenadeParticleOptions(Vector3f vector3f, float vibrancy) {
		color = vector3f;
		scale = Mth.clamp(vibrancy, 0.001F, 100.0F);
	}

	public static Vector3f readVector3f(StringReader reader) throws CommandSyntaxException {
		reader.expect(' ');
		float f = reader.readFloat();
		reader.expect(' ');
		float f1 = reader.readFloat();
		reader.expect(' ');
		float f2 = reader.readFloat();
		return new Vector3f(f, f1, f2);
	}

	public static Vector3f readVector3f(FriendlyByteBuf byteBuf) {
		return new Vector3f(byteBuf.readFloat(), byteBuf.readFloat(), byteBuf.readFloat());
	}

	@Override
	public void writeToNetwork(FriendlyByteBuf byteBuf) {
		byteBuf.writeFloat(color.x());
		byteBuf.writeFloat(color.y());
		byteBuf.writeFloat(color.z());
		byteBuf.writeFloat(scale);
	}

	@Override
	public String writeToString() {
		return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", ForgeRegistries.PARTICLE_TYPES
				.getKey(getType()), color.x(), color.y(), color.z(), scale);
	}

	public Vector3f getColor() {
		return color;
	}

	@Override
	public ParticleType<SmokeGrenadeParticleOptions> getType() {
		return ParticleTypesRegistry.SMOKE_GRENADE_PARTICLE.get();
	}

	public static class SmokeGrenadeColors {
		public static final Vector3f GRAY = Vec3.fromRGB24(16777215).toVector3f();
		public static final Vector3f RED = Vec3.fromRGB24(16711680).toVector3f();
		public static final Vector3f GREEN = Vec3.fromRGB24(5294200).toVector3f();
		public static final Vector3f BLUE = Vec3.fromRGB24(1644912).toVector3f();
		public static final Vector3f PURPLE = Vec3.fromRGB24(5046349).toVector3f();
		public static final Vector3f YELLOW = Vec3.fromRGB24(16318253).toVector3f();
	}

	/**
	 * Utility for getting particle colors from resources spawning smoke grenade particles.
	 *
	 * @param colorID the ID representing the color to be selected.
	 *                <br>0 -> Gray
	 *                <br>1 -> Red
	 *                <br>2 -> Green
	 *                <br>3 -> Blue
	 *                <br>4 -> Purple
	 *                <br>5 -> Yellow
	 * @return SmokeGrenadeParticleOptions
	 */
	public static SmokeGrenadeParticleOptions getParticleByColor(int colorID) {
		return switch (colorID) {
			case 1 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.RED, 1.0F);
			case 2 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.GREEN, 1.0F);
			case 3 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.BLUE, 1.0F);
			case 4 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.PURPLE, 1.0F);
			case 5 -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.YELLOW, 1.0F);
			default -> new SmokeGrenadeParticleOptions(SmokeGrenadeColors.GRAY, 1.0F);
		};
	}
}