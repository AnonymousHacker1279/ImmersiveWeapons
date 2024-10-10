package tech.anonymoushacker1279.immersiveweapons.item.tool.ventus;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class VentusHoe extends HoeItem implements HitEffectUtils {

	public VentusHoe() {
		super(CustomTiers.VENTUS,
				new Properties()
						.attributes(createAttributes(
								CustomTiers.VENTUS, -5, 0.2f)
						)
		);
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addVentusEffects(target);
		return super.hurtEnemy(itemStack, target, attacker);
	}
}