package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaAxe extends AxeItem implements HitEffectUtils {

	public TeslaAxe(Properties properties) {
		super(IWToolMaterials.TESLA, 5, -3.0f, properties);
	}

	@Override
	public void hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity pAttacker) {
		addTeslaEffects(target);
	}
}