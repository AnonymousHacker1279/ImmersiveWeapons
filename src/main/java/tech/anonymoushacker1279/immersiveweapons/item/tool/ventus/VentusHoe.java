package tech.anonymoushacker1279.immersiveweapons.item.tool.ventus;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class VentusHoe extends HoeItem implements HitEffectUtils {

	public VentusHoe(Properties properties) {
		super(IWToolMaterials.VENTUS, -5, 0.2f, properties);
	}

	@Override
	public void hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addVentusEffects(target);
	}
}