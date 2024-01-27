package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.config.ClientConfig;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SmokeGrenadePayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class SmokeGrenadeEntity extends ThrowableItemProjectile {

	private int color;
	private boolean stopMoving = false;
	@Nullable
	private BlockState inBlockState;
	public float randomRotation;
	private int ticksInGround;

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
		int configValue = CommonConfig.forceSmokeGrenadeParticles;
		int particles = configValue == -1
				? ClientConfig.smokeGrenadeParticles
				: configValue;

		if (ClientConfig.fancySmokeGrenadeParticles) {
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
		if (!stopMoving) {
			double x = getDeltaMovement().x();
			double y = getDeltaMovement().y();
			double z = getDeltaMovement().z();

			if (hitResult.getType() == HitResult.Type.BLOCK) {
				BlockHitResult blockHitResult = (BlockHitResult) hitResult;
				switch (blockHitResult.getDirection()) {
					case UP:
					case DOWN:
						y = -y * 0.8d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
						break;
					case NORTH:
					case SOUTH:
						z = -z * 0.8d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
						break;
					case EAST:
					case WEST:
						x = -x * 0.8d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
						break;
				}
			} else if (hitResult.getType() == Type.ENTITY) {
				x = -x * 0.6d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
				y = -y * 0.6d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
				z = -z * 0.6d + GeneralUtilities.getRandomNumber(-0.1d, 0.1d);
			}

			setDeltaMovement(x, y, z);

			level().playLocalSound(this, SoundEvents.METAL_HIT, SoundSource.NEUTRAL, 1.0f, 0.6F / (GeneralUtilities.getRandomNumber(0.2f, 0.6f) + 0.8F));

			// Spawn particles when it slows down enough
			if (getDeltaMovement().lengthSqr() < 0.1d) {
				stopMoving = true;
				setDeltaMovement(Vec3.ZERO);
				setNoGravity(true);
				inBlockState = level().getBlockState(blockPosition());

				if (!level().isClientSide) {
					PacketDistributor.TRACKING_CHUNK.with(level().getChunkAt(blockPosition()))
							.send(new SmokeGrenadePayload(getX(), getY(), getZ(), color));
				}
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (inBlockState != null) {
			if (inBlockState.isAir()) {
				if (stopMoving) {
					// Keep falling until it hits the ground
					setDeltaMovement(getDeltaMovement().x(), getDeltaMovement().y() - getGravity(), getDeltaMovement().z());
					inBlockState = level().getBlockState(blockPosition());
				}
			} else {
				setDeltaMovement(0, 0, 0);
				setNoGravity(true);
				ticksInGround++;
			}

			if (inBlockState != level().getBlockState(blockPosition())) {
				inBlockState = null;
				stopMoving = false;
				setNoGravity(false);
			}
		}

		if (ticksInGround > 300) {
			kill();
		}
	}
}