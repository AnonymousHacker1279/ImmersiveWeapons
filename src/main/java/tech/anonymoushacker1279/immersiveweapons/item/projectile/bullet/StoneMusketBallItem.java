package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntities;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.BulletEntities.StoneMusketBallEntity;
import tech.anonymoushacker1279.immersiveweapons.init.DeferredRegistryHandler;

public class StoneMusketBallItem extends AbstractBulletItem {

	/**
	 * Constructor for StoneBulletItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 * @param damageIn   the damage to deal on impact
	 */
	public StoneMusketBallItem(Properties properties, double damageIn) {
		super(properties, damageIn);
		damage = damageIn;
	}

	/**
	 * Create a bullet item.
	 *
	 * @param level   the <code>Level</code> the shooter is in
	 * @param stack   the <code>ItemStack</code> being shot
	 * @param shooter the <code>LivingEntity</code> shooting the arrow
	 * @return StoneBulletItem
	 */
	@Override
	public @NotNull BulletEntities.StoneMusketBallEntity createBullet(@NotNull Level level, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
		StoneMusketBallEntity bulletEntity = new StoneMusketBallEntity(shooter, level, referenceItem.get().asItem());
		bulletEntity.setBaseDamage(damage);
		bulletEntity.pickup = Pickup.DISALLOWED;
		bulletEntity.setSoundEvent(DeferredRegistryHandler.BULLET_WHIZZ.get());
		return bulletEntity;
	}
}