package tech.anonymoushacker1279.immersiveweapons.item.gauntlet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class VentusGauntletItem extends GauntletItem implements HitEffectUtils {

	public VentusGauntletItem(Tier tier, int damageBonus, float attackSpeed, Properties properties, float bleedChance, int bleedLevel, Ingredient repairIngredient) {
		super(tier, damageBonus, attackSpeed, properties, bleedChance, bleedLevel, repairIngredient);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (super.hurtEnemy(stack, target, attacker)) {
			addVentusEffects(target);
			return true;
		}
		return false;
	}
}