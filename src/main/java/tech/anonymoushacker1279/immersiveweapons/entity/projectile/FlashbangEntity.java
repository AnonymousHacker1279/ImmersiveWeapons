package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags.EntityTypes;
import net.neoforged.neoforge.network.PacketDistributor;
import tech.anonymoushacker1279.immersiveweapons.config.CommonConfig;
import tech.anonymoushacker1279.immersiveweapons.entity.ai.goal.DisorientedWanderingGoal;
import tech.anonymoushacker1279.immersiveweapons.init.*;
import tech.anonymoushacker1279.immersiveweapons.network.payload.PlayerSoundPayload;

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

			gameEvent(GameEventRegistry.FLASHBANG_EXPLODE.get(), getOwner());

			serverLevel.getEntities(this, getBoundingBox().inflate(CommonConfig.flashbangEffectRange))
					.stream()
					.filter(entity -> !entity.isSpectator())
					.forEach(entity -> {
						if (entity instanceof ServerPlayer player) {
							if (canSee(player, this, false)) {
								PacketDistributor.PLAYER.with(player)
										.send(new PlayerSoundPayload(SoundEventRegistry.FLASHBANG_RINGING.get().getLocation(),
												1.0f, level().getRandom().nextFloat() * 0.05f + 1.0f));

								if (canSee(player, this, true)) {
									player.addEffect(new MobEffectInstance(EffectRegistry.FLASHBANG_EFFECT.get(),
											200, 0, true, false, false));
									player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
											200, 0, true, false, false));
								}
							}
						} else if (entity instanceof Mob mob && !mob.getType().is(EntityTypes.BOSSES)) {
							if (canSee(mob, this, false)) {
								mob.goalSelector.addGoal(1, new DisorientedWanderingGoal(mob, CommonConfig.flashbangDisorientTime * 20));
								mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
										200, 0, true, false, false));
							}
						}
					});
		}

		level().playLocalSound(getX(), getY(), getZ(), SoundEventRegistry.FLASHBANG_EXPLODE.get(),
				SoundSource.NEUTRAL, 1.0f, 1.0f, true);
	}
}