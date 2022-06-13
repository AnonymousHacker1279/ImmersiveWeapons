package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

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
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.*;
import net.minecraftforge.network.NetworkEvent.Context;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;
import tech.anonymoushacker1279.immersiveweapons.init.PacketHandler;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.function.Supplier;

public class SmokeGrenadeEntity extends ThrowableItemProjectile {

	private static final byte VANILLA_IMPACT_STATUS_ID = 3;
	private static int color;
	private final int configMaxParticles = CommonConfig.MAX_SMOKE_GRENADE_PARTICLES.get();

	/**
	 * Constructor for SmokeBombEntity.
	 *
	 * @param entityType the <code>EntityType</code> instance; must extend SmokeGrenadeEntity
	 * @param world      the <code>World</code> the entity is in
	 */
	public SmokeGrenadeEntity(EntityType<? extends SmokeGrenadeEntity> entityType, Level world) {
		super(entityType, world);
	}

	/**
	 * Constructor for SmokeGrenadeEntity.
	 *
	 * @param world        the <code>World</code> the entity is in
	 * @param livingEntity the <code>LivingEntity</code> throwing the entity
	 */
	public SmokeGrenadeEntity(Level world, LivingEntity livingEntity) {
		super(DeferredRegistryHandler.SMOKE_GRENADE_ENTITY.get(), livingEntity, world);
	}

	/**
	 * Constructor for SmokeGrenadeEntity.
	 *
	 * @param world the <code>World</code> the entity is in
	 * @param x     the X position
	 * @param y     the Y position
	 * @param z     the Z position
	 */
	public SmokeGrenadeEntity(Level world, double x, double y, double z) {
		super(DeferredRegistryHandler.SMOKE_GRENADE_ENTITY.get(), x, y, z, world);
	}

	/**
	 * Set the particle color.
	 *
	 * @param color a color ID
	 */
	public static void setColor(int color) {
		SmokeGrenadeEntity.color = color;
	}

	/**
	 * Get the entity spawn packet.
	 *
	 * @return IPacket
	 */
	@Override
	public @NotNull Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	/**
	 * ProjectileItemEntity::setItem uses this to save storage space.
	 * It only stores the itemStack if the itemStack is not the default item.
	 *
	 * @return Item
	 */
	@Override
	protected @NotNull Item getDefaultItem() {
		return DeferredRegistryHandler.SMOKE_GRENADE.get();
	}

	/**
	 * Runs when an entity/block is hit.
	 *
	 * @param rayTraceResult the <code>RayTraceResult</code> instance
	 */
	@Override
	protected void onHit(@NotNull HitResult rayTraceResult) {
		super.onHit(rayTraceResult);
		if (!level.isClientSide) {
			// Inform the client of the smoke bomb color
			PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(blockPosition())),
					new SmokeGrenadeEntityPacketHandler(color));

			level.broadcastEntityEvent(this, VANILLA_IMPACT_STATUS_ID);
			kill();
		}
	}

	/**
	 * Handle entity events.
	 *
	 * @param statusID the <code>byte</code> containing status ID
	 */
	@Override
	public void handleEntityEvent(byte statusID) {
		if (statusID == VANILLA_IMPACT_STATUS_ID) {
			double x = getX();
			double y = getY();
			double z = getZ();

			// Spawn smoke particles
			for (int i = 0; i < configMaxParticles; ++i) {
				level.addParticle(SmokeGrenadeParticleOptions.getParticleByColor(color),
						true, x, y, z,
						GeneralUtilities.getRandomNumber(-0.1d, 0.1d),
						GeneralUtilities.getRandomNumber(-0.1d, 0.1d),
						GeneralUtilities.getRandomNumber(-0.1d, 0.1d));
			}

			// Play a hissing sound
			level.playLocalSound(x, y, z, DeferredRegistryHandler.SMOKE_GRENADE_HISS.get(),
					SoundSource.NEUTRAL, 0.2f, 0.6f, true);

			kill();
		}
	}

	public record SmokeGrenadeEntityPacketHandler(int color) {

		/**
		 * Constructor for SmokeGrenadeEntityPacketHandler.
		 *
		 * @param color the color ID
		 */
		public SmokeGrenadeEntityPacketHandler {
		}

		/**
		 * Encodes a packet
		 *
		 * @param msg          the <code>SmokeGrenadeEntityPacketHandler</code> message being sent
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 */
		public static void encode(SmokeGrenadeEntityPacketHandler msg, FriendlyByteBuf packetBuffer) {
			packetBuffer.writeInt(msg.color);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return SmokeGrenadeEntityPacketHandler
		 */
		public static SmokeGrenadeEntityPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new SmokeGrenadeEntityPacketHandler(packetBuffer.readInt());
		}

		/**
		 * Handles an incoming packet, by sending it to the client/server
		 *
		 * @param msg             the <code>SmokeGrenadeEntityPacketHandler</code> message being sent
		 * @param contextSupplier the <code>Supplier</code> providing context
		 */
		public static void handle(SmokeGrenadeEntityPacketHandler msg, Supplier<Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleOnClient(msg)));
			context.setPacketHandled(true);
		}

		/**
		 * Runs specifically on the client, when a packet is received
		 *
		 * @param msg the <code>SmokeGrenadeEntityPacketHandler</code> message being sent
		 */
		private static void handleOnClient(SmokeGrenadeEntityPacketHandler msg) {
			SmokeGrenadeEntity.setColor(msg.color);
		}
	}
}