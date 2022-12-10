package tech.anonymoushacker1279.immersiveweapons.item.tool.ventus;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;

public class VentusShovel extends ShovelItem {

	/**
	 * Constructor for VentusShovel.
	 *
	 * @param tier           the <code>Tier</code>
	 * @param attackDamageIn attack damage
	 * @param attackSpeedIn  attack speed
	 * @param properties     the <code>Properties</code> for the item
	 */
	public VentusShovel(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
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
		target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60, 2, false, false));
		return super.hurtEnemy(itemStack, target, attacker);
	}
}