package tech.anonymoushacker1279.immersiveweapons.item.tool.molten;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class MoltenHoe extends HoeItem implements HitEffectUtils {

	public MoltenHoe() {
		super(CustomTiers.MOLTEN,
				new Properties()
						.attributes(createAttributes(
								CustomTiers.MOLTEN, -5, 0.0f)
						)
						.fireResistant()
		);
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addMoltenEffects(target, attacker);
		return super.hurtEnemy(itemStack, target, attacker);
	}
}