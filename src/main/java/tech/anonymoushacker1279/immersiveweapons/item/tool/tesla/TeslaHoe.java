package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaHoe extends HoeItem implements HitEffectUtils {

	public TeslaHoe() {
		super(CustomTiers.TESLA,
				new Properties()
						.attributes(createAttributes(
								CustomTiers.TESLA, -6, 0.0f)
						)
		);
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addTeslaEffects(target);
		return super.hurtEnemy(itemStack, target, attacker);
	}
}