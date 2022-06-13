package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

public class TeslaShovel extends ShovelItem {

	/**
	 * Constructor for TeslaShovel.
	 *
	 * @param tier           the <code>IItemTier</code>
	 * @param attackDamageIn attack damage
	 * @param attackSpeedIn  attack speed
	 * @param properties     the <code>Properties</code> for the item
	 */
	public TeslaShovel(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
		super(tier, attackDamageIn, attackSpeedIn, properties);
	}

	/**
	 * Runs when an entity is hit.
	 *
	 * @param itemStack     the <code>ItemStack</code> instance
	 * @param livingEntity  the <code>LivingEntity</code> attacking
	 * @param livingEntity1 the <code>LivingEntity</code> being hit
	 * @return boolean
	 */
	@Override
	public boolean hurtEnemy(@NotNull ItemStack itemStack, LivingEntity livingEntity, @NotNull LivingEntity livingEntity1) {
		livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 140, 1, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 1, false, false));
		livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 140, 1, false, false));
		return super.hurtEnemy(itemStack, livingEntity, livingEntity1);
	}
}