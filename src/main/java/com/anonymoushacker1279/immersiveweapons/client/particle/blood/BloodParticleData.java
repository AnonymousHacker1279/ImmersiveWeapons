package com.anonymoushacker1279.immersiveweapons.client.particle.blood;

import com.anonymoushacker1279.immersiveweapons.client.particle.AbstractParticleData;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;

public class BloodParticleData extends AbstractParticleData {

	static final Codec<BloodParticleData> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codec.INT.fieldOf("tint").forGetter(d -> d.tint.getRGB()),
					Codec.DOUBLE.fieldOf("diameter").forGetter(d -> d.diameter)
			).apply(instance, BloodParticleData::new)
	);
	@SuppressWarnings("deprecation")
	static final Deserializer<BloodParticleData> DESERIALIZER = new Deserializer<>() {

		/**
		 * Parse information for spawning particles with commands.
		 *
		 * @param type   a <code>ParticleType</code> instance extending BloodParticleData
		 * @param reader a <code>StringReader</code> instance
		 * @return BloodParticleData
		 * @throws CommandSyntaxException occurs when improper command syntax is provided
		 */
		@Override
		public @NotNull BloodParticleData fromCommand(@Nonnull ParticleType<BloodParticleData> type, @Nonnull StringReader reader) throws CommandSyntaxException {
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

			return new BloodParticleData(color, diameter);
		}

		/**
		 * Read particle data from a packet.
		 *
		 * @param type a <code>ParticleType</code> instance extending BloodParticleData
		 * @param buf  a <code>PacketBuffer</code> containing packet data
		 * @return BloodParticleData
		 */
		@Override
		public @NotNull BloodParticleData fromNetwork(@Nonnull ParticleType<BloodParticleData> type, FriendlyByteBuf buf) {
			final int MIN_COLOR = 0;
			final int MAX_COLOR = 255;
			int red = Mth.clamp(buf.readInt(), MIN_COLOR, MAX_COLOR);
			int green = Mth.clamp(buf.readInt(), MIN_COLOR, MAX_COLOR);
			int blue = Mth.clamp(buf.readInt(), MIN_COLOR, MAX_COLOR);
			Color color = new Color(red, green, blue);
			double diameter = constrainDiameterToValidRange(buf.readDouble());

			return new BloodParticleData(color, diameter);
		}
	};

	private final Color tint;
	private final double diameter;

	/**
	 * Constructor for BloodParticleData.
	 *
	 * @param tint     a <code>Color</code> instance
	 * @param diameter the particle diameter
	 */
	public BloodParticleData(Color tint, double diameter) {
		super(tint, diameter);
		this.tint = tint;
		this.diameter = constrainDiameterToValidRange(diameter);
	}

	/**
	 * Constructor for BloodParticleData.
	 *
	 * @param tintRGB  an integer for RGB tinting
	 * @param diameter the particle diameter
	 */
	private BloodParticleData(int tintRGB, double diameter) {
		super(tintRGB, diameter);
		tint = new Color(tintRGB);
		this.diameter = constrainDiameterToValidRange(diameter);
	}

	/**
	 * Get the particle type.
	 *
	 * @return ParticleType extending BloodParticleData
	 */
	@Override
	public @NotNull ParticleType<BloodParticleData> getType() {
		return DeferredRegistryHandler.BLOOD_PARTICLE_TYPE.get();
	}
}