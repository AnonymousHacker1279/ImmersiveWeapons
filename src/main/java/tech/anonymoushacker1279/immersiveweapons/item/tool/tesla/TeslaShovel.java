package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaShovel extends ShovelItem implements HitEffectUtils {

	public TeslaShovel(Properties properties) {
		super(IWToolMaterials.TESLA, -1.5f, -3.0f, properties);
	}

	@Override
	public void hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addTeslaEffects(target);
	}
}