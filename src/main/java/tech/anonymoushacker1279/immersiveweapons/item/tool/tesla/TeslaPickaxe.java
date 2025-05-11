package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaPickaxe extends Item implements HitEffectUtils {

	public TeslaPickaxe(Properties properties) {
		super(properties.pickaxe(IWToolMaterials.TESLA, 1, -2.8f));
	}

	@Override
	public void hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addTeslaEffects(target);
	}
}