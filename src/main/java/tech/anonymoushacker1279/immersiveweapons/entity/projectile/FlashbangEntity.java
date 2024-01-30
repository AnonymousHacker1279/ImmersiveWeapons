package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.init.*;

public class FlashbangEntity extends AdvancedThrowableItemProjectile {

	public FlashbangEntity(EntityType<? extends FlashbangEntity> entityType, Level level) {
		super(entityType, level);
	}

	public FlashbangEntity(Level level, LivingEntity livingEntity) {
		super(EntityRegistry.FLASHBANG_ENTITY.get(), livingEntity, level);
	}

	public FlashbangEntity(Level level, double x, double y, double z) {
		super(EntityRegistry.FLASHBANG_ENTITY.get(), level, x, y, z);
	}

	@Override
	protected Item getDefaultItem() {
		return ItemRegistry.FLASHBANG.get();
	}

	@Override
	protected void onActivate() {
		if (level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
					ParticleTypes.EXPLOSION_EMITTER,
					getX(), getY(), getZ(),
					1,
					0.0D, 0.0D, 0.0D,
					0.0D
			);
		}

		level().playLocalSound(getX(), getY(), getZ(), SoundEventRegistry.FLASHBANG_EXPLODE.get(),
				SoundSource.NEUTRAL, 1.0f, 1.0f, true);

		level().getEntities(this, getBoundingBox().inflate(16.0D))
				.stream()
				.filter(entity -> !entity.isSpectator() && entity instanceof Player)
				.forEach(entity -> {
					Player player = (Player) entity;

					if (canSee(player, this)) {
						player.playSound(SoundEventRegistry.FLASHBANG_RINGING.get(), 1.0f, 1.0f);
						player.addEffect(new MobEffectInstance(EffectRegistry.FLASHBANG_EFFECT.get(),
								200, 0, true, false, false));
						player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
								200, 0, true, false, false));
					}
				});
	}

	private boolean canSee(Player player, Entity entity) {
		if (player.hasLineOfSight(entity)) {
			Vec3 lookVec = player.getLookAngle();
			Vec3 toEntity = entity.position().subtract(player.position()).normalize();

			double dotProduct = lookVec.dot(toEntity);
			double maxCosine = Math.cos(Math.toRadians(90));
			double angleToGround = 180 - (Math.acos(lookVec.y) * (180.0 / Math.PI));
			return dotProduct > maxCosine || angleToGround < 30;
		}

		return false;
	}
}