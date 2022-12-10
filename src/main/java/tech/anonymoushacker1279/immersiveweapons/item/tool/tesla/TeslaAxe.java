package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaAxe extends AxeItem implements HitEffectUtils {

	/**
	 * Constructor for TeslaAxe.
	 *
	 * @param tier           the <code>Tier</code>
	 * @param attackDamageIn attack damage
	 * @param attackSpeedIn  attack speed
	 * @param properties     the <code>Properties</code> for the item
	 */
	public TeslaAxe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
		super(tier, attackDamageIn, attackSpeedIn, properties);
	}

	/**
	 * Runs when an entity is hit.
	 *
	 * @param itemStack the <code>ItemStack</code> instance
	 * @param target    the <code>LivingEntity</code> attacking
	 * @param pAttacker the <code>LivingEntity</code> being hit
	 * @return boolean
	 */
	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity pAttacker) {
		addTeslaEffects(target);
		return super.hurtEnemy(itemStack, target, pAttacker);
	}
}