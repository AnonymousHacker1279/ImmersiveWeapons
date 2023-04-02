package tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class AstralMusketBallEntity extends BulletEntity implements HitEffectUtils {

	public AstralMusketBallEntity(EntityType<AstralMusketBallEntity> entityType, Level level, int knockbackStrength) {
		super(entityType, level);
		this.knockbackStrength = knockbackStrength;
	}

	public AstralMusketBallEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.ASTRAL_MUSKET_BALL_ENTITY.get(), shooter, level);
	}

	@Override
	protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
		return new Vec3(x, y, z)
				.normalize()
				.add(random.triangle(0d, 0.001d * inaccuracy * GeneralUtilities.getRandomNumber(0.1d, 0.2d)),
						random.triangle(0d, 0.001d * inaccuracy * GeneralUtilities.getRandomNumber(0.1d, 0.2d)),
						random.triangle(0d, 0.001d * inaccuracy * GeneralUtilities.getRandomNumber(0.1d, 0.2d)))
				.scale(velocity);
	}

	@Override
	public double getGravityModifier() {
		return 0.002d;
	}

	@Override
	public @Nullable Item getReferenceItem() {
		return ItemRegistry.ASTRAL_MUSKET_BALL.get();
	}
}