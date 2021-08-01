package com.anonymoushacker1279.immersiveweapons.client.particle;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Locale;

public class SmokeBombParticleData implements ParticleOptions {

	static final Codec<SmokeBombParticleData> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codec.INT.fieldOf("tint").forGetter(d -> d.tint.getRGB()),
					Codec.DOUBLE.fieldOf("diameter").forGetter(d -> d.diameter)
			).apply(instance, SmokeBombParticleData::new)
	);
	static final Deserializer<SmokeBombParticleData> DESERIALIZER = new Deserializer<>() {

		/**
		 * Parse information for spawning particles with commands.
		 *
		 * @param type   a <code>ParticleType</code> instance extending SmokeBombParticleData
		 * @param reader a <code>StringReader</code> instance
		 * @return SmokeBombParticleData
		 * @throws CommandSyntaxException occurs when improper command syntax is provided
		 */
		@Override
		public @NotNull SmokeBombParticleData fromCommand(@Nonnull ParticleType<SmokeBombParticleData> type, @Nonnull StringReader reader) throws CommandSyntaxException {
			reader.expect(' ');
			double diameter = constrainDiameterToValidRange(reader.readDouble());

			final int MIN_COLOR = 0;
			final int MAX_COLOR = 255;
			reader.expect(' ');
			int red = Mth.clamp(reader.readInt(), MIN_COLOR, MAX_COLOR);
			reader.expect(' ');
			int green = Mth.clamp(reader.readInt(), MIN_COLOR, MAX_COLOR);
			reader.expect(' ');
			int blue = Mth.clamp(reader.readInt(), MIN_COLOR, MAX_COLOR);
			Color color = new Color(red, green, blue);

			return new SmokeBombParticleData(color, diameter);
		}

		/**
		 * Read particle data from a packet.
		 *
		 * @param type a <code>ParticleType</code> instance extending SmokeBombParticleData
		 * @param buf  a <code>PacketBuffer</code> containing packet data
		 * @return SmokeBombParticleData
		 */
		@Override
		public @NotNull SmokeBombParticleData fromNetwork(@Nonnull ParticleType<SmokeBombParticleData> type, FriendlyByteBuf buf) {
			final int MIN_COLOR = 0;
			final int MAX_COLOR = 255;
			int red = Mth.clamp(buf.readInt(), MIN_COLOR, MAX_COLOR);
			int green = Mth.clamp(buf.readInt(), MIN_COLOR, MAX_COLOR);
			int blue = Mth.clamp(buf.readInt(), MIN_COLOR, MAX_COLOR);
			Color color = new Color(red, green, blue);
			double diameter = constrainDiameterToValidRange(buf.readDouble());

			return new SmokeBombParticleData(color, diameter);
		}
	};

	private final Color tint;
	private final double diameter;

	/**
	 * Constructor for SmokeBombParticleData.
	 * @param tint a <code>Color</code> instance
	 * @param diameter the particle diameter
	 */
	public SmokeBombParticleData(Color tint, double diameter) {
		this.tint = tint;
		this.diameter = constrainDiameterToValidRange(diameter);
	}

	/**
	 * Constructor for SmokeBombParticleData.
	 * @param tintRGB an integer for RGB tinting
	 * @param diameter the particle diameter
	 */
	private SmokeBombParticleData(int tintRGB, double diameter) {
		tint = new Color(tintRGB);
		this.diameter = constrainDiameterToValidRange(diameter);
	}

	/**
	 * Clamps diameters to a valid range.
	 * @param diameter the particle diameter
	 * @return double
	 */
	private static double constrainDiameterToValidRange(double diameter) {
		final double MIN_DIAMETER = 0.05;
		final double MAX_DIAMETER = 5.5;
		return Mth.clamp(diameter, MIN_DIAMETER, MAX_DIAMETER);
	}

	/**
	 * Get the tint of the particle.
	 * @return Color
	 */
	public Color getTint() {
		return tint;
	}

	/**
	 * Get the diameter of the particle.
	 * @return double
	 */
	double getDiameter() {
		return diameter;
	}

	/**
	 * Get the particle type.
	 * @return ParticleType extending SmokeBombParticleData
	 */
	@Override
	public @NotNull ParticleType<SmokeBombParticleData> getType() {
		return DeferredRegistryHandler.SMOKE_BOMB_PARTICLE_TYPE.get();
	}

	/**
	 * Write particle information to a PacketBuffer.
	 * @param buf a <code>PacketBuffer</code> instance
	 */
	@Override
	public void writeToNetwork(FriendlyByteBuf buf) {
		buf.writeInt(tint.getRed());
		buf.writeInt(tint.getGreen());
		buf.writeInt(tint.getBlue());
		buf.writeDouble(diameter);
	}

	/**
	 * For debugging: Write information to a readable format
	 * @return String
	 */
	@Nonnull
	@Override
	public String writeToString() {
		return String.format(Locale.ROOT, "%s %.2f %d %d %d", getType().getRegistryName(), diameter, tint.getRed(), tint.getGreen(), tint.getBlue());
	}
}