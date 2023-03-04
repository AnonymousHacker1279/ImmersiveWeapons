package tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class CobaltMusketBallEntity extends BulletEntity {

	public CobaltMusketBallEntity(EntityType<CobaltMusketBallEntity> entityType, Level level, int knockbackStrength) {
		super(entityType, level);
		this.knockbackStrength = knockbackStrength;
	}

	public CobaltMusketBallEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.COBALT_MUSKET_BALL_ENTITY.get(), shooter, level);
	}

	@Override
	public double getGravityModifier() {
		return 0.035d;
	}

	@Override
	public @Nullable Item getReferenceItem() {
		return ItemRegistry.COBALT_MUSKET_BALL.get();
	}
}