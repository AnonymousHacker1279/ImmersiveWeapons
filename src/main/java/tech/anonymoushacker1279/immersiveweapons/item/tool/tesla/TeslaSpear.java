package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaSpear extends Item implements HitEffectUtils {

	public TeslaSpear(Properties properties) {
		super(properties.spear(IWToolMaterials.TESLA,
				1.3F,
				1.375F,
				0.3F,
				2.4F,
				6.9F,
				5.4F,
				5F,
				7.5F,
				4.5F));
	}

	@Override
	public void hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addTeslaEffects(target);
	}
}