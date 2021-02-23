package com.anonymoushacker1279.immersiveweapons.client.particle;

import java.awt.Color;
import java.util.Locale;

import javax.annotation.Nonnull;

import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;

public class SmokeBombParticleData implements IParticleData {
	
	public SmokeBombParticleData(Color tint, double diameter) {
	    this.tint = tint;
	    this.diameter = constrainDiameterToValidRange(diameter);
	  }

	  public Color getTint() {
	    return tint;
	  }

	  public double getDiameter() {
	    return diameter;
	  }

	  @Nonnull
	  @Override
	  public ParticleType<SmokeBombParticleData> getType() {
	    return DeferredRegistryHandler.SMOKE_BOMB_PARTICLE_TYPE.get();
	  }

	  // write the particle information to a PacketBuffer, ready for transmission to a client
	  @Override
	  public void write(PacketBuffer buf) {
	    buf.writeInt(tint.getRed());
	    buf.writeInt(tint.getGreen());
	    buf.writeInt(tint.getBlue());
	    buf.writeDouble(diameter);
	  }

	  // used for debugging I think; prints the data in human-readable format
	  @Nonnull
	  @Override
	  public String getParameters() {
	    return String.format(Locale.ROOT, "%s %.2f %i %i %i",
	            this.getType().getRegistryName(), diameter, tint.getRed(), tint.getGreen(), tint.getBlue());
	  }

	  private static double constrainDiameterToValidRange(double diameter) {
	    final double MIN_DIAMETER = 0.05;
	    final double MAX_DIAMETER = 5.5;
	    return MathHelper.clamp(diameter, MIN_DIAMETER, MAX_DIAMETER);
	  }

	  private Color tint;
	  private double diameter;

	  public static final Codec<SmokeBombParticleData> CODEC = RecordCodecBuilder.create(
	            instance -> instance.group(
	              Codec.INT.fieldOf("tint").forGetter(d -> d.tint.getRGB()),
	              Codec.DOUBLE.fieldOf("diameter").forGetter(d -> d.diameter)
	            ).apply(instance, SmokeBombParticleData::new)
	          );

	  private SmokeBombParticleData(int tintRGB, double diameter) {
	    this.tint = new Color(tintRGB);
	    this.diameter = constrainDiameterToValidRange(diameter);
	  }

	  public static final IDeserializer<SmokeBombParticleData> DESERIALIZER = new IDeserializer<SmokeBombParticleData>() {

		  // parse the parameters for this particle from a /particle command
		  @Nonnull
		  @Override
		  public SmokeBombParticleData deserialize(@Nonnull ParticleType<SmokeBombParticleData> type, @Nonnull StringReader reader) throws CommandSyntaxException {
			  reader.expect(' ');
		      double diameter = constrainDiameterToValidRange(reader.readDouble());
	
		      final int MIN_COLOR = 0;
		      final int MAX_COLOR = 255;
		      reader.expect(' ');
		      int red = MathHelper.clamp(reader.readInt(), MIN_COLOR, MAX_COLOR);
		      reader.expect(' ');
		      int green = MathHelper.clamp(reader.readInt(), MIN_COLOR, MAX_COLOR);
		      reader.expect(' ');
		      int blue = MathHelper.clamp(reader.readInt(), MIN_COLOR, MAX_COLOR);
		      Color color = new Color(red, green, blue);
	
		      return new SmokeBombParticleData(color, diameter);
		  }

		  // read the particle information from a PacketBuffer after the client has received it from the server
		  @Override
		  public SmokeBombParticleData read(@Nonnull ParticleType<SmokeBombParticleData> type, PacketBuffer buf) {
		      // warning! never trust the data read in from a packet buffer.
	
		      final int MIN_COLOR = 0;
		      final int MAX_COLOR = 255;
		      int red = MathHelper.clamp(buf.readInt(), MIN_COLOR, MAX_COLOR);
		      int green = MathHelper.clamp(buf.readInt(), MIN_COLOR, MAX_COLOR);
		      int blue = MathHelper.clamp(buf.readInt(), MIN_COLOR, MAX_COLOR);
		      Color color = new Color(red, green, blue);
	
		      double diameter = constrainDiameterToValidRange(buf.readDouble());
	
		      return new SmokeBombParticleData(color, diameter);
		  }
	  };
}
