package tech.anonymoushacker1279.immersiveweapons.item.tool.molten;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import tech.anonymoushacker1279.immersiveweapons.item.materials.CustomTiers;
import tech.anonymoushacker1279.immersiveweapons.item.tool.HitEffectUtils;

public class MoltenPickaxe extends PickaxeItem implements HitEffectUtils {

	public MoltenPickaxe() {
		super(CustomTiers.MOLTEN,
				new Properties()
						.attributes(createAttributes(
								CustomTiers.MOLTEN, 1, -2.8f)
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