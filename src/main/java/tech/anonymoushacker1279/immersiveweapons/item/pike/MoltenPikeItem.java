package tech.anonymoushacker1279.immersiveweapons.item.pike;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class MoltenPikeItem extends PikeItem implements HitEffectUtils {

	public MoltenPikeItem(Tier tier, Properties properties, double damageBonus, double attackSpeed, Ingredient repairIngredient) {
		super(tier, properties, damageBonus, attackSpeed, repairIngredient);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (super.hurtEnemy(stack, target, attacker)) {
			addMoltenEffects(target, attacker);
			return true;
		}
		return false;
	}
}