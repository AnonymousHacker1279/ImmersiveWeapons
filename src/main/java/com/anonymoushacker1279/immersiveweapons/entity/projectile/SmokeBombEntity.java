package com.anonymoushacker1279.immersiveweapons.entity.projectile;

import com.anonymoushacker1279.immersiveweapons.client.particle.SmokeBombParticleData;
import com.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import com.anonymoushacker1279.immersiveweapons.util.Config;
import com.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;
import com.anonymoushacker1279.immersiveweapons.util.PacketHandler;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.function.Supplier;

public class SmokeBombEntity extends ThrowableItemProjectile {

	private static int color;
	private final int configMaxParticles = Config.MAX_SMOKE_BOMB_PARTICLES.get();
	private static final byte VANILLA_IMPACT_STATUS_ID = 3;

	/**
	 * Constructor for SmokeBombEntity.
	 * @param entityType the <code>EntityType</code> instance; must extend SmokeBombEntity
	 * @param world the <code>World</code> the entity is in
	 */
	public SmokeBombEntity(EntityType<? extends SmokeBombEntity> entityType, Level world) {
		super(entityType, world);
	}

	/**
	 * Constructor for SmokeBombEntity.
	 * @param world the <code>World</code> the entity is in
	 * @param livingEntity the <code>LivingEntity</code> throwing the entity
	 */
	public SmokeBombEntity(Level world, LivingEntity livingEntity) {
		super(DeferredRegistryHandler.SMOKE_BOMB_ENTITY.get(), livingEntity, world);
	}

	/**
	 * Constructor for SmokeBombEntity.
	 * @param world the <code>World</code> the entity is in
	 * @param x the X position
	 * @param y the Y position
	 * @param z the Z position
	 */
	public SmokeBombEntity(Level world, double x, double y, double z) {
		super(DeferredRegistryHandler.SMOKE_BOMB_ENTITY.get(), x, y, z, world);
	}

	/**
	 * Set the particle color.
	 * @param color a color ID
	 */
	public static void setColor(int color) {
		SmokeBombEntity.color = color;
	}

	/**
	 * Get the entity spawn packet.
	 * @return IPacket
	 */
	@Override
	public @NotNull Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	/**
	 * ProjectileItemEntity::setItem uses this to save storage space.
	 * It only stores the itemStack if the itemStack is not the default item.
	 * @return Item
	 */
	@Override
	protected @NotNull Item getDefaultItem() {
		return DeferredRegistryHandler.SMOKE_BOMB.get();
	}

	/**
	 * Runs when an entity/block is hit.
	 * @param rayTraceResult the <code>RayTraceResult</code> instance
	 */
	@Override
	protected void onHit(@NotNull HitResult rayTraceResult) {
		super.onHit(rayTraceResult);
		if (!level.isClientSide) {
			PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockPosition())), new SmokeBombEntityPacketHandler(color));
			level.broadcastEntityEvent(this, VANILLA_IMPACT_STATUS_ID);  // calls handleStatusUpdate which tells the client to render particles
			kill();
		}
	}

	/**
	 * Handle entity events.
	 * @param statusID the <code>byte</code> containing status ID
	 */
	@Override
	public void handleEntityEvent(byte statusID) {
		if (statusID == VANILLA_IMPACT_STATUS_ID) {
			ParticleOptions particleData = makeParticle();

			for (int i = 0; i < configMaxParticles; ++i) {
				level.addParticle(particleData, true, getX(), getY(), getZ(), GeneralUtilities.getRandomNumber(-0.03, 0.03d), GeneralUtilities.getRandomNumber(-0.02d, 0.02d), GeneralUtilities.getRandomNumber(-0.03d, 0.03d));
			}
			level.playLocalSound(getX(), getY(), getZ(), DeferredRegistryHandler.SMOKE_BOMB_HISS.get(), SoundSource.NEUTRAL, 1f, 1f, false);
			kill();
		}
	}

	/**
	 * Create a particle.
	 * @return IParticleData
	 */
	private static ParticleOptions makeParticle() {
		Color tint = getTint(GeneralUtilities.getRandomNumber(0, 2));
		double diameter = getDiameter(GeneralUtilities.getRandomNumber(1.0d, 5.5d));

		return new SmokeBombParticleData(tint, diameter);
	}

	/**
	 * Get the particle diameter.
	 * @param random a random number
	 * @return double
	 */
	private static double getDiameter(double random){
		final double MIN_DIAMETER = 0.5;
		final double MAX_DIAMETER = 5.5;
		return MIN_DIAMETER + (MAX_DIAMETER - MIN_DIAMETER) * random;
	}

	/**
	 * Tint a particle.
	 * @param random a random number
	 * @return Color
	 */
	private static Color getTint(int random){
		Color[] tints = {
				new Color(1.00f, 1.00f, 1.00f),  // no tint (white)
				new Color(1.00f, 0.97f, 1.00f),  // off-white
				new Color(1.00f, 1.00f, 0.97f),  // off-white 2
		};
		Color[] tintsRed = {
				new Color(1.00f, 0.25f, 0.25f),  // tint (red)
				new Color(1.00f, 0.30f, 0.25f),  // off-red
				new Color(1.00f, 0.25f, 0.30f),  // off-red 2
		};
		Color[] tintsGreen = {
				new Color(0.25f, 1.00f, 0.25f),  // tint (green)
				new Color(0.30f, 1.00f, 0.25f),  // off-green
				new Color(0.25f, 1.00f, 0.30f),  // off-green 2
		};
		Color[] tintsBlue = {
				new Color(0.25f, 0.25f, 1.00f),  // tint (blue)
				new Color(0.30f, 0.25f, 1.00f),  // off-blue
				new Color(0.25f, 0.30f, 1.00f),  // off-blue 2
		};
		Color[] tintsPurple = {
				new Color(1.00f, 0.25f, 1.00f),  // tint (purple)
				new Color(1.00f, 0.30f, 1.00f),  // off-purple
				new Color(1.00f, 0.35f, 1.00f),  // off-purple 2
		};
		Color[] tintsYellow = {
				new Color(1.00f, 1.00f, 0.25f),  // tint (yellow)
				new Color(1.00f, 1.00f, 0.30f),  // off-yellow
				new Color(1.00f, 1.00f, 0.35f),  // off-yellow 2
		};

		return switch (color) {
			case 1 -> tintsRed[random];
			case 2 -> tintsGreen[random];
			case 3 -> tintsBlue[random];
			case 4 -> tintsPurple[random];
			case 5 -> tintsYellow[random];
			default -> tints[random];
		};
	}

	public record SmokeBombEntityPacketHandler(int color) {

		/**
		 * Constructor for SmokeBombEntityPacketHandler.
		 *
		 * @param color the color ID
		 */
		public SmokeBombEntityPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>SmokeBombEntityPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(SmokeBombEntityPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeInt(msg.color);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return SmokeBombEntityPacketHandler
		 */
		public static SmokeBombEntityPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new SmokeBombEntityPacketHandler(packetBuffer.readInt());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param msg             the <code>SmokeBombEntityPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(SmokeBombEntityPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the client, when a packet is received
		 *
		 * @param msg the <code>SmokeBombEntityPacketHandler</code> message being sent
		 */
		@OnlyIn(Dist.CLIENT)
		private static void handleOnClient(SmokeBombEntityPacketHandler msg) {
			SmokeBombEntity.setColor(msg.color);
		}
	}
}