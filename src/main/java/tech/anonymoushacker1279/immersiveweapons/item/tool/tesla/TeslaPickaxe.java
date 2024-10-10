package tech.anonymoushacker1279.immersiveweapons.item.tool.tesla;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class TeslaPickaxe extends PickaxeItem implements HitEffectUtils {

	public TeslaPickaxe() {
		super(CustomTiers.TESLA,
				new Properties()
						.attributes(createAttributes(
								CustomTiers.TESLA, 1, -2.8f)
						)
		);
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addTeslaEffects(target);
		return super.hurtEnemy(itemStack, target, attacker);
	}
}