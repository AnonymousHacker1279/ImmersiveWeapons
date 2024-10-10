package tech.anonymoushacker1279.immersiveweapons.item.tool.molten;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class MoltenSword extends SwordItem implements HitEffectUtils {

	public MoltenSword() {
		super(CustomTiers.MOLTEN,
				new Properties()
						.attributes(createAttributes(
								CustomTiers.MOLTEN, 3, -2.4f)
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