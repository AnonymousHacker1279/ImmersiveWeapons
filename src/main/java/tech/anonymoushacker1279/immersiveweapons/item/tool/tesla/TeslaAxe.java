package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaAxe extends Item implements HitEffectUtils {

	public TeslaAxe(Properties properties) {
		super(properties.axe(IWToolMaterials.TESLA, 5, -3.0f));
	}

	@Override
	public void hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity pAttacker) {
		addTeslaEffects(target);
	}
}