package tech.anonymoushacker1279.immersiveweapons.item.projectile.bullet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import tech.anonymoushacker1279.immersiveweapons.ImmersiveWeapons;
import tech.anonymoushacker1279.immersiveweapons.entity.projectile.bullet.BulletEntity;
import tech.anonymoushacker1279.immersiveweapons.init.EnchantmentRegistry;
import tech.anonymoushacker1279.immersiveweapons.init.EntityRegistry;

public abstract class AbstractBulletItem extends ArrowItem {

	public double damage;

	/**
	 * Constructor for AbstractBulletItem.
	 *
	 * @param properties the <code>Properties</code> for the item
	 */
	protected AbstractBulletItem(Properties properties, double damage) {
		super(properties);
		this.damage = damage;
	}

	public boolean canBeInfinite() {
		return true;
	}

	public float misfireChance() {
		return 0.0f;
	}

	public BulletEntity createBullet(Level level, LivingEntity shooter) {
		BulletEntity bulletEntity = new BulletEntity(EntityRegistry.IRON_MUSKET_BALL_ENTITY.get(), shooter, level);
		bulletEntity.setBaseDamage(damage);
		bulletEntity.setOwner(shooter);
		return bulletEntity;
	}

	/**
	 * Check if the bullet is infinite. A more flexible check than Vanilla provides.
	 * Restricts the ability to lower level bullets, for balance.
	 *
	 * @param bullet the bullet being checked
	 * @param gun    the gun firing the bullet
	 * @param player the player firing the gun
	 * @return boolean
	 */
	@Override
	public boolean isInfinite(ItemStack bullet, ItemStack gun, Player player) {
		int enchant = EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegistry.ENDLESS_MUSKET_POUCH.get(), gun);
		boolean canBeInfinite = ImmersiveWeapons.COMMON_CONFIG.allowInfiniteAmmoOnAllTiers().get() || canBeInfinite();
		return canBeInfinite && enchant > 0;
	}
}