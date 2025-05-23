package tech.anonymoushacker1279.immersiveweapons.item.tool;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.IWToolMaterials;

public class TheSword extends Item implements HitEffectUtils {

	public TheSword(Properties properties) {
		super(properties.sword(IWToolMaterials.HANSIUM, 3, -1.3f).fireResistant());
	}

	@Override
	public void hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addMoltenEffects(target, attacker);
		addTeslaEffects(target);
		addVentusEffects(target);
	}
}