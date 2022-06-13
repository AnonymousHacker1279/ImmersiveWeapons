package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.particles.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class MudBallEntity extends ThrowableItemProjectile {

	public MudBallEntity(EntityType<? extends MudBallEntity> entityType, Level level) {
		super(entityType, level);
	}

	public MudBallEntity(Level level, LivingEntity livingEntity) {
		super(DeferredRegistryHandler.MUD_BALL_ENTITY.get(), livingEntity, level);
	}

	@Override
	protected @NotNull Item getDefaultItem() {
		return DeferredRegistryHandler.MUD_BALL.get();
	}

	private ParticleOptions getParticle() {
		return new ItemParticleOption(ParticleTypes.ITEM, getItem());
	}

	@Override
	public void handleEntityEvent(byte pId) {
		if (pId == 3) {
			ParticleOptions particleoptions = getParticle();
			for (int i = 0; i < 8; ++i) {
				level.addParticle(particleoptions, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
			}
		}

	}

	@Override
	protected void onHitEntity(@NotNull EntityHitResult pResult) {
		super.onHitEntity(pResult);
		Entity entity = pResult.getEntity();
		int i = entity instanceof Blaze ? 3 : 0;
		entity.hurt(DamageSource.thrown(this, getOwner()), (float) i);
	}

	@Override
	protected void onHit(@NotNull HitResult pResult) {
		super.onHit(pResult);
		if (!level.isClientSide) {
			level.broadcastEntityEvent(this, (byte) 3);
			discard();
		}
	}
}