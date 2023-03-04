package tech.anonymoushacker1279.immersiveweapons.item.projectile.arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public abstract class AbstractArrowItem extends ArrowItem {

	public double damage;

	protected AbstractArrowItem(Properties properties, double damageIn) {
		super(properties);
		damage = damageIn;
	}

	@Override
	public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
		AbstractArrow arrowEntity = new Arrow(level, shooter);
		arrowEntity.setBaseDamage(damage);
		arrowEntity.setOwner(shooter);
		return arrowEntity;
	}

	/**
	 * Check if the arrow is infinite. A more flexible check than Vanilla provides.
	 *
	 * @param stack  the <code>ItemStack</code> being checked
	 * @param bow    the <code>ItemStack</code> containing the bow that's firing
	 * @param player the <code>Player</code> firing the bow
	 * @return boolean
	 */
	@Override
	public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
		int enchant = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
		return enchant > 0;
	}
}