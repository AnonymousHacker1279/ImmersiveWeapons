package tech.anonymoushacker1279.immersiveweapons.item.tool.ventus;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class VentusPickaxe extends PickaxeItem implements HitEffectUtils {

	public VentusPickaxe() {
		super(CustomTiers.VENTUS,
				new Properties()
						.attributes(createAttributes(
								CustomTiers.VENTUS, 1, -2.4f)
						)
		);
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addVentusEffects(target);
		return super.hurtEnemy(itemStack, target, attacker);
	}
}