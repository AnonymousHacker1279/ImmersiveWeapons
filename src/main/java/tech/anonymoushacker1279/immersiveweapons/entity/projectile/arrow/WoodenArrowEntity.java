package tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.ItemRegistry;
import tech.anonymoushacker1279.immersiveweapons.util.GeneralUtilities;

public class WoodenArrowEntity extends CustomArrowEntity {

	public WoodenArrowEntity(EntityType<? extends AbstractArrow> type, Level level) {
		super(type, level);
	}

	public WoodenArrowEntity(LivingEntity shooter, Level level) {
		super(EntityRegistry.WOODEN_ARROW_ENTITY.get(), shooter, level);
	}

	public WoodenArrowEntity(Level level, double x, double y, double z) {
		super(EntityRegistry.WOODEN_ARROW_ENTITY.get(), level, x, y, z);
	}

	/**
	 * Fire the entity from a position with a velocity and inaccuracy.
	 *
	 * @param x          the X position
	 * @param y          the Y position
	 * @param z          the Z position
	 * @param velocity   the velocity
	 * @param inaccuracy the inaccuracy modifier
	 */
	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		super.shoot(x, y, z, velocity, (inaccuracy + GeneralUtilities.getRandomNumber(5.8f, 7.2f)));
	}

	@Override
	public Item getReferenceItem() {
		return ItemRegistry.WOODEN_ARROW.get();
	}
}