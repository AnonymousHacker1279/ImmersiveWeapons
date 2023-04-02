package tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class TeslaArrowEntity extends CustomArrowEntity implements HitEffectUtils {

	public TeslaArrowEntity(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	public TeslaArrowEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.TESLA_ARROW_ENTITY.get(), shooter, level);
	}

	public TeslaArrowEntity(Level level, double x, double y, double z) {
		super(EntityRegistry.TESLA_ARROW_ENTITY.get(), level, x, y, z);
	}

	@Override
	protected Vec3 getShootingVector(double x, double y, double z, float velocity, float inaccuracy) {
		return new Vec3(x, y, z)
				.normalize()
				.add(random.triangle(0d, 0.0025d * inaccuracy * GeneralUtilities.getRandomNumber(0.2f, 0.9f)),
						random.triangle(0d, 0.0025d * inaccuracy * GeneralUtilities.getRandomNumber(0.2f, 0.9f)),
						random.triangle(0d, 0.0025d * inaccuracy * GeneralUtilities.getRandomNumber(0.2f, 0.9f)))
				.scale(velocity);
	}

	/**
	 * Get the movement modifier.
	 *
	 * @return double
	 */
	@Override
	public double getMovementModifier() {
		return 0.0355d;
	}

	@Override
	public @Nullable Item getReferenceItem() {
		return ItemRegistry.TESLA_ARROW.get();
	}

	@Override
	protected void doWhenHitEntity(Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			addTeslaEffects(livingEntity);
		}
	}
}