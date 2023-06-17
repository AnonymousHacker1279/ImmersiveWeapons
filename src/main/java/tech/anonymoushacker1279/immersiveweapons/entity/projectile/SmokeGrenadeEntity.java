package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.config.ClientConfig;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

import java.util.function.Supplier;

public class SmokeGrenadeEntity extends ThrowableItemProjectile {

	private int color;

	/**
	 * Constructor for SmokeBombEntity.
	 *
	 * @param entityType the <code>EntityType</code> instance; must extend SmokeGrenadeEntity
	 * @param level      the <code>Level</code> the entity is in
	 */
	public SmokeGrenadeEntity(EntityType<? extends SmokeGrenadeEntity> entityType, Level level) {
		super(entityType, level);
	}

	/**
	 * Constructor for SmokeGrenadeEntity.
	 *
	 * @param level        the <code>Level</code> the entity is in
	 * @param livingEntity the <code>LivingEntity</code> throwing the entity
	 */
	public SmokeGrenadeEntity(Level level, LivingEntity livingEntity) {
		super(EntityRegistry.SMOKE_GRENADE_ENTITY.get(), livingEntity, level);
	}

	/**
	 * Constructor for SmokeGrenadeEntity.
	 *
	 * @param level the <code>Level</code> the entity is in
	 * @param x     the X position
	 * @param y     the Y position
	 * @param z     the Z position
	 */
	public SmokeGrenadeEntity(Level level, double x, double y, double z) {
		super(EntityRegistry.SMOKE_GRENADE_ENTITY.get(), x, y, z, level);
	}

	/**
	 * Set the particle color.
	 *
	 * @param color a color ID
	 */
	public void setColor(int color) {
		this.color = color;
	}

	public static void runOnClientImpact(double x, double y, double z, int color, ClientLevel level) {
		int particles = CommonConfig.FORCE_SMOKE_GRENADE_PARTICLES.get() == -1
				? ClientConfig.SMOKE_GRENADE_PARTICLES.get()
				: CommonConfig.FORCE_SMOKE_GRENADE_PARTICLES.get();

		if (ClientConfig.FANCY_SMOKE_GRENADE_PARTICLES.get()) {
			particles *= 3;
		}

		for (int i = 0; i < particles; ++i) {
			level.addParticle(SmokeGrenadeParticleOptions.getParticleByColor(color),
					true, x, y, z,
					GeneralUtilities.getRandomNumber(-0.1d, 0.1d),
					GeneralUtilities.getRandomNumber(-0.1d, 0.1d),
					GeneralUtilities.getRandomNumber(-0.1d, 0.1d));
		}

		level.playLocalSound(x, y, z, SoundEventRegistry.SMOKE_GRENADE_HISS.get(),
				SoundSource.NEUTRAL, 0.2f, 0.6f, true);
	}

	/**
	 * Get the entity spawn packet.
	 *
	 * @return IPacket
	 */
	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	/**
	 * ProjectileItemEntity::setItem uses this to save storage space.
	 * It only stores the itemStack if the itemStack is not the default item.
	 *
	 * @return Item
	 */
	@Override
	protected Item getDefaultItem() {
		return ItemRegistry.SMOKE_GRENADE.get();
	}

	/**
	 * Runs when an entity/block is hit.
	 *
	 * @param hitResult the <code>HitResult</code> instance
	 */
	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!level().isClientSide) {
			// Inform the client of the smoke grenade color
			PacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level().getChunkAt(blockPosition())),
					new SmokeGrenadeEntityPacketHandler(getX(), getY(), getZ(), color));

		}
		kill();
	}

	public record SmokeGrenadeEntityPacketHandler(double x, double y, double z, int color) {

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
			packetBuffer.writeDouble(msg.x).writeDouble(msg.y).writeDouble(msg.z).writeInt(msg.color);
		}

		/**
		 * Decodes a packet
		 *
		 * @param packetBuffer the <code>PacketBuffer</code> containing packet data
		 * @return SmokeGrenadeEntityPacketHandler
		 */
		public static SmokeGrenadeEntityPacketHandler decode(FriendlyByteBuf packetBuffer) {
			return new SmokeGrenadeEntityPacketHandler(packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readDouble(), packetBuffer.readInt());
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
			ClientLevel level = Minecraft.getInstance().level;

			if (level != null) {
				runOnClientImpact(msg.x, msg.y, msg.z, msg.color, level);
			}
		}
	}
}