package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaShovel extends ShovelItem implements HitEffectUtils {

	public TeslaShovel() {
		super(CustomTiers.TESLA,
				new Properties()
						.attributes(createAttributes(
								CustomTiers.TESLA, 1.5f, -3.0f)
						)
		);
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addTeslaEffects(target);
		return super.hurtEnemy(itemStack, target, attacker);
	}
}