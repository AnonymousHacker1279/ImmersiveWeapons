package tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet;

import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class TeslaMusketBallEntity extends BulletEntity implements HitEffectUtils {

	public TeslaMusketBallEntity(EntityType<TeslaMusketBallEntity> entityType, Level level, int knockbackStrength) {
		super(entityType, level);
		this.knockbackStrength = knockbackStrength;
	}

	public TeslaMusketBallEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.TESLA_MUSKET_BALL_ENTITY.get(), shooter, level);
	}

	@Override
	protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
		return new Vec3(x, y, z)
				.normalize()
				.add(random.triangle(0d, 0.002d * inaccuracy * GeneralUtilities.getRandomNumber(0.2d, 0.5d)),
						random.triangle(0d, 0.002d * inaccuracy * GeneralUtilities.getRandomNumber(0.2d, 0.5d)),
						random.triangle(0d, 0.002d * inaccuracy * GeneralUtilities.getRandomNumber(0.2d, 0.5d)))
				.scale(velocity);
	}

	@Override
	public double getGravityModifier() {
		return 0.004d;
	}

	@Override
	public @Nullable Item getReferenceItem() {
		return ItemRegistry.TESLA_MUSKET_BALL.get();
	}

	@Override
	protected void doWhenHitEntity(Entity entity) {
		super.doWhenHitEntity(entity);

		if (entity instanceof LivingEntity livingEntity) {
			addTeslaEffects(livingEntity);
		}
	}
}