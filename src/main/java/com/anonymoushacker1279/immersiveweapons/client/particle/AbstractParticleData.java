package com.anonymoushacker1279.immersiveweapons.client.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Locale;

public abstract class AbstractParticleData implements ParticleOptions {

	private final Color tint;
	private final double diameter;

	/**
	 * Constructor for AbstractParticleData.
	 * @param tint a <code>Color</code> instance
	 * @param diameter the particle diameter
	 */
	public AbstractParticleData(Color tint, double diameter) {
		this.tint = tint;
		this.diameter = constrainDiameterToValidRange(diameter);
	}

	/**
	 * Constructor for AbstractParticleData.
	 * @param tintRGB an integer for RGB tinting
	 * @param diameter the particle diameter
	 */
	public AbstractParticleData(int tintRGB, double diameter) {
		tint = new Color(tintRGB);
		this.diameter = constrainDiameterToValidRange(diameter);
	}

	/**
	 * Clamps diameters to a valid range.
	 * @param diameter the particle diameter
	 * @return double
	 */
	protected static double constrainDiameterToValidRange(double diameter) {
		final double MIN_DIAMETER = 0.001;
		final double MAX_DIAMETER = 10;
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
	public double getDiameter() {
		return diameter;
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