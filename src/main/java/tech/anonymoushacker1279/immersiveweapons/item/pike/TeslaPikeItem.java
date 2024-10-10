package tech.anonymoushacker1279.immersiveweapons.item.pike;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaPikeItem extends PikeItem implements HitEffectUtils {

	public TeslaPikeItem(Tier tier, Properties properties, Ingredient repairIngredient) {
		super(tier, properties, repairIngredient);
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