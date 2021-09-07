package com.anonymoushacker1279.immersiveweapons.potion;

import com.anonymoushacker1279.immersiveweapons.client.particle.blood.BloodParticleData;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Supplier;

public class BleedingEffect extends MobEffect {

	private int cooldownTicks = 0;
	public final DamageSource damageSource = new DamageSource("immersiveweapons.bleeding").bypassArmor();

	/**
	 * Constructor for BleedingEffect.
	 *
	 * @param typeIn        the <code>EffectType</code> instance
	 * @param liquidColorIn the liquid color
	 */
	public BleedingEffect(MobEffectCategory typeIn, int liquidColorIn) {
		super(typeIn, liquidColorIn);
	}

	/**
	 * Runs once each tick while the effect is active.
	 *
	 * @param livingEntity the <code>LivingEntity</code> with the effect
	 * @param amplifier    the effect amplifier
	 */
	@Override
	public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
		if (!livingEntity.level.isClientSide) {
			if (cooldownTicks <= 0) {
				cooldownTicks = 59 - (amplifier >= 1 ? amplifier * 10 : 0);
				livingEntity.hurt(damageSource, 1.0f);
			} else {
				cooldownTicks--;
			}
			PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> livingEntity.level.getChunkAt(livingEntity.blockPosition())), new BleedingEffectPacketHandler(livingEntity.blockPosition(), livingEntity.position(), livingEntity.getEyeHeight()));
		}
	}

	/**
	 * Check if the duration effect is ticking.
	 *
	 * @param duration  the duration
	 * @param amplifier the effect amplifier
	 * @return boolean
	 */
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	public record BleedingEffectPacketHandler(BlockPos blockPos,
	                                          Vec3 vector3d, float eyeLevel) {

		/**
		 * Constructor for BleedingEffectPacketHandler.
		 *
		 * @param blockPos the <code>BlockPos</code> the packet came from
		 * @param vector3d the <code>Vector3d</code> of the entity position
		 * @param eyeLevel the eye level of the entity
		 */
		public BleedingEffectPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>BleedingEffectPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(BleedingEffectPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeBlockPos(msg.blockPos);
			packetBuffer.writeDouble(msg.vector3d.x).writeDouble(msg.vector3d.y).writeDouble(msg.vector3d.z);
			packetBuffer.writeFloat(msg.eyeLevel);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return BleedingEffectPacketHandler
		 */
		public static BleedingEffectPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new BleedingEffectPacketHandler(packetBuffer.readBlockPos(), new Vec3(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble()), packetBuffer.readFloat());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param msg             the <code>BleedingEffectPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(BleedingEffectPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the client, when a packet is received
		 *
		 * @param msg the <code>BleedingEffectPacketHandler</code> message being sent
		 */
		@OnlyIn(Dist.CLIENT)
		private static void handleOnClient(BleedingEffectPacketHandler msg) {
			Minecraft minecraft = Minecraft.getInstance();
			if (minecraft.level != null) {
				minecraft.level.addParticle(makeParticle(), msg.vector3d.x, msg.vector3d.y + GeneralUtilities.getRandomNumber(0.3d, msg.eyeLevel), msg.vector3d.z, GeneralUtilities.getRandomNumber(-0.03d, 0.03d), GeneralUtilities.getRandomNumber(-0.1d, -0.08d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
			}
		}

		/**
		 * Create a particle.
		 *
		 * @return IParticleData
		 */
		private static ParticleOptions makeParticle() {
			Color tint = getTint(GeneralUtilities.getRandomNumber(0, 2));
			double diameter = getDiameter(GeneralUtilities.getRandomNumber(0.07d, 0.1d));

			return new BloodParticleData(tint, diameter);
		}

		/**
		 * Get the particle diameter.
		 *
		 * @param random a random number
		 * @return double
		 */
		private static double getDiameter(double random) {
			final double MIN_DIAMETER = 0.07;
			final double MAX_DIAMETER = 0.1;
			return MIN_DIAMETER + (MAX_DIAMETER - MIN_DIAMETER) * random;
		}

		/**
		 * Tint a particle.
		 *
		 * @param random a random number
		 * @return Color
		 */
		private static Color getTint(int random) {
			Color[] tints = {
					new Color(1.00f, 0.25f, 0.25f),  // tint (red)
					new Color(1.00f, 0.30f, 0.25f),  // off-red
					new Color(1.00f, 0.25f, 0.30f),  // off-red 2
			};

			return tints[random];
		}
	}
}