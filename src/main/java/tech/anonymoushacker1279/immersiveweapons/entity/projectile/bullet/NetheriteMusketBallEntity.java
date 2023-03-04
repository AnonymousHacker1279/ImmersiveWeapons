package tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class NetheriteMusketBallEntity extends BulletEntity {

	public NetheriteMusketBallEntity(EntityType<NetheriteMusketBallEntity> entityType, Level level, int knockbackStrength) {
		super(entityType, level);
		this.knockbackStrength = knockbackStrength;
	}

	public NetheriteMusketBallEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.NETHERITE_MUSKET_BALL_ENTITY.get(), shooter, level);
	}

	@Override
	protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
		return new Vec3(x, y, z)
				.normalize()
				.add(random.triangle(0d, 0.002d * inaccuracy * GeneralUtilities.getRandomNumber(0.2d, 0.7d)),
						random.triangle(0d, 0.002d * inaccuracy * GeneralUtilities.getRandomNumber(0.2d, 0.7d)),
						random.triangle(0d, 0.002d * inaccuracy * GeneralUtilities.getRandomNumber(0.2d, 0.7d)))
				.scale(velocity);
	}

	/**
	 * Get the movement modifier.
	 *
	 * @return double
	 */
	@Override
	public double getGravityModifier() {
		return 0.005d;
	}

	@Override
	public @Nullable Item getReferenceItem() {
		return ItemRegistry.NETHERITE_MUSKET_BALL.get();
	}
}