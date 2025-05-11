package tech.anonymoushacker1279.immersiveweapons.item.projectile;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.DragonFireballBulletEntity;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

import java.util.List;
import java.util.function.Supplier;

public class DragonFireballItem extends BulletItem<DragonFireballBulletEntity> {

	public DragonFireballItem(Properties properties, double damage, Supplier<EntityType<DragonFireballBulletEntity>> bulletEntity, int pierceLevel,
	                          boolean canBeInfinite, float misfireChance, double gravityModifier, List<Double> shootingVectorInputs,
	                          int knockbackStrength, HitEffectUtils.HitEffect hitEffect, boolean isExplosive) {

		super(properties, damage, bulletEntity, pierceLevel, canBeInfinite, misfireChance, gravityModifier,
				shootingVectorInputs, knockbackStrength, hitEffect, isExplosive);
	}

	public static DragonFireballItem createFromBulletBuilder(BulletBuilder<DragonFireballBulletEntity> builder) {
		return new DragonFireballItem(builder.properties, builder.damage, builder.bulletEntity, builder.pierceLevel,
				builder.canBeInfinite, builder.misfireChance, builder.gravityModifier, builder.shootingVectorInputs,
				builder.knockbackStrength, builder.hitEffect, builder.isExplosive);
	}

	@Override
	public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
		RandomSource random = level.getRandom();
		double x = random.triangle(direction.getStepX(), 0.11485);
		double y = random.triangle(direction.getStepY(), 0.11485);
		double z = random.triangle(direction.getStepZ(), 0.11485);
		Vec3 movement = new Vec3(x, y, z);
		DragonFireball fireball = new DragonFireball(EntityType.DRAGON_FIREBALL, level);
		fireball.addDeltaMovement(movement.normalize());
		fireball.snapTo(pos.x() + direction.getStepX(), pos.y() + direction.getStepY(), pos.z() + direction.getStepZ(), 0.0F, 0.0F);
		return fireball;
	}

	@Override
	public ProjectileItem.DispenseConfig createDispenseConfig() {
		return ProjectileItem.DispenseConfig.builder()
				.positionFunction((source, direction) -> DispenserBlock.getDispensePosition(source, 1.0, Vec3.ZERO))
				.uncertainty(6.65F)
				.power(1.0F)
				.overrideDispenseEvent(1018)
				.build();
	}
}