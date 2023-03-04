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

public class WoodenMusketBallEntity extends BulletEntity {

	public WoodenMusketBallEntity(EntityType<WoodenMusketBallEntity> entityType, Level level, int knockbackStrength) {
		super(entityType, level);
		this.knockbackStrength = knockbackStrength;
	}

	public WoodenMusketBallEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.WOODEN_MUSKET_BALL_ENTITY.get(), shooter, level);
	}

	@Override
	protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
		return new Vec3(x, y, z)
				.normalize()
				.add(random.triangle(0d, 0.0175d * inaccuracy * GeneralUtilities.getRandomNumber(3.2f, 5.1f)),
						random.triangle(0d, 0.0175d * inaccuracy * GeneralUtilities.getRandomNumber(3.2f, 5.1f)),
						random.triangle(0d, 0.0175d * inaccuracy * GeneralUtilities.getRandomNumber(3.2f, 5.1f)))
				.scale(velocity);
	}

	@Override
	public double getGravityModifier() {
		return 0.055d;
	}

	@Override
	public @Nullable Item getReferenceItem() {
		return ItemRegistry.WOODEN_MUSKET_BALL.get();
	}
}