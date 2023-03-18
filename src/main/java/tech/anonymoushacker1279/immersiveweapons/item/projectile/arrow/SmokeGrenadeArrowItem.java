package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.arrow.SmokeGrenadeArrowEntity;

public class SmokeGrenadeArrowItem extends AbstractArrowItem {

	private final int color;

	public SmokeGrenadeArrowItem(Properties properties, double damage, int color) {
		super(properties, damage);
		this.damage = damage;
		this.color = color;
	}

	@Override
	public SmokeGrenadeArrowEntity createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		SmokeGrenadeArrowEntity arrowEntity = new SmokeGrenadeArrowEntity(shooter, level);
		arrowEntity.setBaseDamage(damage);
		arrowEntity.pickup = Pickup.DISALLOWED;
		arrowEntity.setColor(color);
		return arrowEntity;
	}
}