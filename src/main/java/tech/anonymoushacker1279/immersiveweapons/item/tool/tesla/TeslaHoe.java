package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaHoe extends HoeItem implements HitEffectUtils {

	public TeslaHoe(Properties properties) {
		super(IWToolMaterials.TESLA, -7, 0.0f, properties);
	}

	@Override
	public void hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addTeslaEffects(target);
	}
}