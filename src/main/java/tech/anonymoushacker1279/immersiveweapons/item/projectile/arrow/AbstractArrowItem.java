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

	protected AbstractArrowItem(Properties properties, double damage) {
		super(properties);
		this.damage = damage;
	}

	public boolean canBeInfinite() {
		return true;
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
	 * Restricts the ability to lower level arrows, for balance.
	 *
	 * @param arrow  the arrow being checked
	 * @param bow    the bow firing the arrow
	 * @param player the player firing the bow
	 * @return boolean
	 */
	@Override
	public boolean isInfinite(ItemStack arrow, ItemStack bow, Player player) {
		int enchant = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, bow);
		return canBeInfinite() && enchant > 0;
	}
}