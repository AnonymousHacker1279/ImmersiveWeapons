package tech.anonymoushacker1279.immersiveweapons.item.tool;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;

public class TheSword extends SwordItem implements HitEffectUtils {

	public TheSword() {
		super(CustomTiers.HANSIUM,
				new Properties()
						.attributes(createAttributes(
								CustomTiers.HANSIUM, 3, -1.3f)
						)
						.fireResistant()
		);
	}

	@Override
	public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
		addMoltenEffects(target, attacker);
		addTeslaEffects(target);
		addVentusEffects(target);
		return super.hurtEnemy(itemStack, target, attacker);
	}
}