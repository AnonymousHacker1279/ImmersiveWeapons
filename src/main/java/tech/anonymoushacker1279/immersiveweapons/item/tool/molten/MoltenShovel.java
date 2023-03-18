package tech.anonymoushacker1279.immersiveweapons.item.tool.molten;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class MoltenShovel extends ShovelItem implements HitEffectUtils {

	/**
	 * Constructor for MoltenShovel.
	 *
	 * @param tier           the <code>Tier</code>
	 * @param attackDamageIn attack damage
	 * @param attackSpeedIn  attack speed
	 * @param properties     the <code>Properties</code> for the item
	 */
	public MoltenShovel(Tier tier, float attackDamageIn, float attackSpeedIn, Properties properties) {
		super(tier, attackDamageIn, attackSpeedIn, properties);
	}

	/**
	 * Runs when an entity is hit.
	 *
	 * @param itemStack the <code>ItemStack</code> instance
	 * @param target    the <code>LivingEntity</code> attacking
	 * @param attacker  the <code>LivingEntity</code> being hit
	 * @return boolean
	 */
	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addMoltenEffects(target);
		return super.hurtEnemy(itemStack, target, attacker);
	}
}