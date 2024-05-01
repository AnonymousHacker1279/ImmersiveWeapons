package tech.anonymoushacker1279.immersiveweapons.item.tool.molten;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class MoltenShovel extends ShovelItem implements HitEffectUtils {

	public MoltenShovel() {
		super(CustomTiers.MOLTEN,
				new Properties()
						.attributes(createAttributes(
								CustomTiers.MOLTEN, 1.5f, -3.0f)
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