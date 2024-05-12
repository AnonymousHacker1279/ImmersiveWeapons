package tech.anonymoushacker1279.immersiveweapons.item.gauntlet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaGauntletItem extends GauntletItem implements HitEffectUtils {

	public TeslaGauntletItem(Tier tier, Properties properties, float bleedChance, int bleedLevel, Ingredient repairIngredient) {
		super(tier, properties, bleedChance, bleedLevel, repairIngredient);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (super.hurtEnemy(stack, target, attacker)) {
			addTeslaEffects(target);
			return true;
		}

		return false;
	}
}