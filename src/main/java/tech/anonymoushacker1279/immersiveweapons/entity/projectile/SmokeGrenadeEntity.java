package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

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
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.config.ClientConfig;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SmokeGrenadePayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

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
	 * Set the particle color. Color IDs can be found in {@link SmokeGrenadeParticleOptions.SmokeGrenadeColors}.
	 *
	 * @param color a color ID
	 */
	public void setColor(int color) {
		this.color = color;
	}

	public static void runOnClientImpact(double x, double y, double z, int color, Level level) {
		int configValue = ImmersiveWeapons.COMMON_CONFIG.forceSmokeGrenadeParticles().get();
		int particles = configValue == -1
				? ClientConfig.SMOKE_GRENADE_PARTICLES.get()
				: configValue;

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

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	@Override
	protected Item getDefaultItem() {
		return ItemRegistry.SMOKE_GRENADE.get();
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!level().isClientSide) {
			PacketDistributor.TRACKING_CHUNK.with(level().getChunkAt(blockPosition()))
					.send(new SmokeGrenadePayload(getX(), getY(), getZ(), color));
		}
		kill();
	}
}