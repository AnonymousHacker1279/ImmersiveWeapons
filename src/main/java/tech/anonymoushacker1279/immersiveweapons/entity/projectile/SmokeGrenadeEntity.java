package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.client.particle.smoke_grenade.SmokeGrenadeParticleOptions;
import tech.anonymoushacker1279.immersiveweapons.config.ClientConfig;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.network.payload.SmokeGrenadePayload;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class SmokeGrenadeEntity extends AdvancedThrowableItemProjectile {

	private int color;

	public SmokeGrenadeEntity(EntityType<? extends SmokeGrenadeEntity> entityType, Level level) {
		super(entityType, level);
	}

	public SmokeGrenadeEntity(Level level, LivingEntity livingEntity) {
		super(EntityRegistry.SMOKE_GRENADE_ENTITY.get(), livingEntity, level);
	}

	public SmokeGrenadeEntity(Level level, double x, double y, double z) {
		super(EntityRegistry.SMOKE_GRENADE_ENTITY.get(), level, x, y, z);
	}

	/**
	 * Set the particle color. Color IDs can be found in {@link SmokeGrenadeParticleOptions.SmokeGrenadeColors}.
	 *
	 * @param color a color ID
	 */
	public void setColor(int color) {
		this.color = color;
	}

	public static void runOnClientImpact(double x, double y, double z, int color, Level level, int forcedParticleCount) {
		int particles = forcedParticleCount == -1 ? ClientConfig.smokeGrenadeParticles : forcedParticleCount;

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
	protected Item getDefaultItem() {
		return ItemRegistry.SMOKE_GRENADE.get();
	}

	@Override
	protected void onActivate() {
		if (level() instanceof ServerLevel serverLevel) {
			PacketDistributor.sendToPlayersTrackingChunk(serverLevel, serverLevel.getChunkAt(blockPosition()).getPos(),
					new SmokeGrenadePayload(getX(), getY(), getZ(), color, CommonConfig.forceSmokeGrenadeParticles));
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (ticksInGround > 0) {
			if (level() instanceof ServerLevel serverLevel) {
				if (tickCount % 2 == 0) {
					serverLevel.getEntities(this, getBoundingBox().inflate(CommonConfig.smokeGrenadeEffectRange))
							.stream()
							.filter(entity -> !entity.isSpectator())
							.forEach(entity -> {
								if (entity instanceof Mob mob && !mob.getType().is(EntityTypes.BOSSES)) {
									if (canSee(mob, this, false)) {
										mob.setTarget(null);
									}
								}
							});
				}

				if (ticksInGround % 10 == 0) {
					gameEvent(GameEventRegistry.SMOKE_GRENADE_HISS, getOwner());
				}
			}
		}
	}
}