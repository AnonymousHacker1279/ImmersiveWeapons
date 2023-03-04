package tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;

public class CopperMusketBallEntity extends BulletEntity {

	public CopperMusketBallEntity(EntityType<CopperMusketBallEntity> entityType, Level level, int knockbackStrength) {
		super(entityType, level);
		this.knockbackStrength = knockbackStrength;
	}

	public CopperMusketBallEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.COPPER_MUSKET_BALL_ENTITY.get(), shooter, level);
	}

	@Override
	public double getGravityModifier() {
		return 0.035d;
	}

	@Override
	public @Nullable Item getReferenceItem() {
		return ItemRegistry.COPPER_MUSKET_BALL.get();
	}
}