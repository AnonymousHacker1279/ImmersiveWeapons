package tech.anonymoushacker1279.immersiveweapons.item.gauntlet;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class MoltenGauntletItem extends GauntletItem implements HitEffectUtils {

	public MoltenGauntletItem(Tier tier, Properties properties, float bleedChance, int bleedLevel, Ingredient repairIngredient) {
		super(tier, properties, bleedChance, bleedLevel, repairIngredient);
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