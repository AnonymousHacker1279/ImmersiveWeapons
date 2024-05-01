package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaAxe extends AxeItem implements HitEffectUtils {

	public TeslaAxe() {
		super(CustomTiers.TESLA,
				new Properties()
						.attributes(createAttributes(
								CustomTiers.TESLA, 5, -3.0f)
						)
		);
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity pAttacker) {
		addTeslaEffects(target);
		return super.hurtEnemy(itemStack, target, pAttacker);
	}
}