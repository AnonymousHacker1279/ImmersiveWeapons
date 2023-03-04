package tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class GoldenMusketBallEntity extends BulletEntity {

	public GoldenMusketBallEntity(EntityType<GoldenMusketBallEntity> entityType, Level level, int knockbackStrength) {
		super(entityType, level);
		this.knockbackStrength = knockbackStrength;
	}

	public GoldenMusketBallEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.GOLDEN_MUSKET_BALL_ENTITY.get(), shooter, level);
	}

	@Override
	public double getGravityModifier() {
		return 0.03d;
	}

	@Override
	public @Nullable Item getReferenceItem() {
		return ItemRegistry.GOLDEN_MUSKET_BALL.get();
	}
}