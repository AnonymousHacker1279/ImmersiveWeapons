package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class MudBallEntity extends AdvancedThrowableItemProjectile {

	public MudBallEntity(EntityType<? extends MudBallEntity> entityType, Level level) {
		super(entityType, level);
	}

	public MudBallEntity(Level level, LivingEntity livingEntity) {
		super(EntityRegistry.MUD_BALL_ENTITY.get(), livingEntity, level, ItemRegistry.MUD_BALL.get().getDefaultInstance());
	}

	public MudBallEntity(Level level, double x, double y, double z) {
		super(EntityRegistry.MUD_BALL_ENTITY.get(), level, x, y, z, ItemRegistry.MUD_BALL.get().getDefaultInstance());
	}

	@Override
	protected Item getDefaultItem() {
		return ItemRegistry.MUD_BALL.get();
	}

	private ParticleOptions getParticle() {
		return new ItemParticleOption(ParticleTypes.ITEM, getItem());
	}

	@Override
	public void handleEntityEvent(byte pId) {
		if (pId == 3) {
			ParticleOptions particleOptions = getParticle();
			for (int i = 0; i < 8; ++i) {
				level().addParticle(particleOptions, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
			}
		}

	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		Entity entity = hitResult.getEntity();
		int damage = entity instanceof Blaze ? 3 : 0;
		entity.hurt(damageSources().thrown(this, getOwner()), (float) damage);
	}

	@Override
	protected void onHit(HitResult pResult) {
		super.onHit(pResult);
		if (!level().isClientSide) {
			level().broadcastEntityEvent(this, (byte) 3);
			discard();
		}
	}
}