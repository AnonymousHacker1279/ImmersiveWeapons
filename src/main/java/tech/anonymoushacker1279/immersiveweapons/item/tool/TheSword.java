package tech.anonymoushacker1279.immersiveweapons.item.tool;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;

public class TheSword extends SwordItem implements HitEffectUtils {

	public TheSword(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addMoltenEffects(target);
		addTeslaEffects(target);
		addVentusEffects(target);
		return super.hurtEnemy(itemStack, target, attacker);
	}
}