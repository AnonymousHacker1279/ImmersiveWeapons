package tech.anonymoushacker1279.immersiveweapons.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class DragonFireballBulletEntity extends BulletEntity implements ItemSupplier {

	public DragonFireballBulletEntity(EntityType<? extends Arrow> entityType, Level level) {
		super(entityType, level);
	}

	public DragonFireballBulletEntity(LivingEntity shooter, Level level, @Nullable ItemStack firedFromWeapon) {
		super(EntityRegistry.DRAGON_FIREBALL_ENTITY.get(), shooter, level, firedFromWeapon);
	}

	@Override
	public ItemStack getItem() {
		return new ItemStack(ItemRegistry.DRAGON_FIREBALL.get());
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		super.onHitBlock(hitResult);

		discard();
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);

		AreaEffectCloud cloud = new AreaEffectCloud(level(), getX(), getY() - 0.5f, getZ());

		if (getOwner() instanceof LivingEntity livingEntity) {
			cloud.setOwner(livingEntity);
		}

		cloud.setParticle(ParticleTypes.DRAGON_BREATH);
		cloud.setRadius(3.0f);
		cloud.setDuration(120);
		cloud.setRadiusPerTick((3.0f - cloud.getRadius()) / (float) cloud.getDuration());
		cloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 2));
		level().addFreshEntity(cloud);

		level().playSound(null, blockPosition(), SoundEvents.DRAGON_FIREBALL_EXPLODE, getSoundSource(), 1.0f, Mth.randomBetween(getRandom(), 0.8f, 1.1f));

		explode(3.0f);
	}

	@Override
	protected void doWhileTicking() {
		super.doWhileTicking();

		if (!inGround && level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(
					ParticleTypes.DRAGON_BREATH,
					position().x, position().y, position().z,
					12,
					random.nextGaussian() * 0.25,
					random.nextGaussian() * 0.25,
					random.nextGaussian() * 0.25,
					random.nextGaussian() * 0.075);
		}

		// Explode once it slows down enough
		if (getDeltaMovement().lengthSqr() < 0.1) {
			explode(3.0f);
			discard();
		}
	}
}